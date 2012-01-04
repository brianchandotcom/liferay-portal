/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.webdav.methods;

import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.Status;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Lock;
import com.liferay.util.xml.XMLFormatter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 */
public class LockMethodImpl implements Method {

	public int process(WebDAVRequest webDavRequest) throws WebDAVException {
		try {
			return doProcess(webDavRequest);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected int doProcess(WebDAVRequest webDavRequest) throws Exception {
		WebDAVStorage storage = webDavRequest.getWebDAVStorage();

		if (!storage.isSupportsClassTwo()) {
			return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		}

		HttpServletRequest request = webDavRequest.getHttpServletRequest();
		HttpServletResponse response = webDavRequest.getHttpServletResponse();

		Lock lock = null;
		Status status = null;

		String lockUuid = webDavRequest.getLockUuid();
		long timeout = WebDAVUtil.getTimeout(request);

		if (Validator.isNull(lockUuid)) {

			// Create new lock

			String owner = null;
			String xml = new String(
				FileUtil.getBytes(request.getInputStream()));

			if (Validator.isNotNull(xml)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Request XML\n" + XMLFormatter.toString(xml));
				}

				Document doc = SAXReaderUtil.read(new UnsyncStringReader(xml));

				Element root = doc.getRootElement();

				boolean exclusive = false;

				List<Element> lockscopeEls = root.element(
					"lockscope").elements();

				for (Element scopeEl : lockscopeEls) {
					String name = GetterUtil.getString(scopeEl.getName());

					if (name.equals("exclusive")) {
						exclusive = true;
					}
				}

				if (!exclusive) {
					return HttpServletResponse.SC_BAD_REQUEST;
				}

				Element ownerEl = root.element("owner");

				owner = ownerEl.getTextTrim();

				if (Validator.isNull(owner)) {
					List<Element> childEls = ownerEl.elements("href");

					for (Element childEl : childEls) {
						owner =
							"<D:href>" + childEl.getTextTrim() + "</D:href>";
					}
				}
			}
			else {
				_log.error("Empty request XML");

				return HttpServletResponse.SC_PRECONDITION_FAILED;
			}

			status = storage.lockResource(webDavRequest, owner, timeout);

			lock = (Lock)status.getObject();
		}
		else {
			try {
				// Refresh existing lock

				lock = storage.refreshResourceLock(
					webDavRequest, lockUuid, timeout);

				status = new Status(HttpServletResponse.SC_OK);
			}
			catch (WebDAVException wde) {
				if (wde.getCause() instanceof NoSuchLockException) {
					return HttpServletResponse.SC_PRECONDITION_FAILED;
				}
				else {
					throw wde;
				}
			}
		}

		// Return lock details

		if (lock == null) {
			return status.getCode();
		}

		long depth = WebDAVUtil.getDepth(request);

		String xml = getResponseXML(lock, depth);

		if (_log.isDebugEnabled()) {
			_log.debug("Response XML\n" + xml);
		}

		String lockToken = "<" + WebDAVUtil.TOKEN_PREFIX + lock.getUuid() + ">";

		response.setContentType(ContentTypes.TEXT_XML_UTF8);
		response.setHeader("Lock-Token", lockToken);
		response.setStatus(status.getCode());

		if (_log.isDebugEnabled()) {
			_log.debug("Returning lock token " + lockToken);
		}

		try {
			ServletResponseUtil.write(response, xml);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		return status.getCode();
	}

	protected String getResponseXML(Lock lock, long depth) throws Exception {
		StringBundler sb = new StringBundler(20);

		long timeoutSecs = lock.getExpirationTime() / Time.SECOND;

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		sb.append("<D:prop xmlns:D=\"DAV:\">");
		sb.append("<D:lockdiscovery>");
		sb.append("<D:activelock>");
		sb.append("<D:locktype><D:write/></D:locktype>");
		sb.append("<D:lockscope><D:exclusive/></D:lockscope>");

		if (depth < 0) {
			sb.append("<D:depth>Infinity</D:depth>");
		}

		sb.append("<D:owner>");
		sb.append(lock.getOwner());
		sb.append("</D:owner>");
		sb.append("<D:timeout>Second-");
		sb.append(timeoutSecs);
		sb.append("</D:timeout>");
		sb.append("<D:locktoken><D:href>");
		sb.append(WebDAVUtil.TOKEN_PREFIX);
		sb.append(lock.getUuid());
		sb.append("</D:href></D:locktoken>");
		sb.append("</D:activelock>");
		sb.append("</D:lockdiscovery>");
		sb.append("</D:prop>");

		return XMLFormatter.toString(sb.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(LockMethodImpl.class);

}