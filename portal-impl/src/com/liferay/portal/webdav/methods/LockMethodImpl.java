/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.NoSuchLockException;
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
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.util.xml.Dom4jUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 */
public class LockMethodImpl implements Method {

	@Override
	public int process(WebDAVRequest webDAVRequest) throws WebDAVException {
		try {
			return doProcess(webDAVRequest);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected int doProcess(WebDAVRequest webDAVRequest) throws Exception {
		WebDAVStorage storage = webDAVRequest.getWebDAVStorage();

		if (!storage.isSupportsClassTwo()) {
			return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		}

		HttpServletRequest request = webDAVRequest.getHttpServletRequest();
		HttpServletResponse response = webDAVRequest.getHttpServletResponse();

		Lock lock = null;
		Status status = null;

		String lockUuid = webDAVRequest.getLockUuid();
		long timeout = WebDAVUtil.getTimeout(request);

		if (Validator.isNull(lockUuid)) {

			// Create new lock

			String owner = null;
			String xml = new String(
				FileUtil.getBytes(request.getInputStream()));

			if (Validator.isNotNull(xml)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Request XML\n" + Dom4jUtil.toString(xml));
				}

				Document document = SAXReaderUtil.read(xml);

				Element rootElement = document.getRootElement();

				boolean exclusive = false;

				Element lockscopeElement = rootElement.element("lockscope");

				for (Element element : lockscopeElement.elements()) {
					String name = GetterUtil.getString(element.getName());

					if (name.equals("exclusive")) {
						exclusive = true;
					}
				}

				if (!exclusive) {
					return HttpServletResponse.SC_BAD_REQUEST;
				}

				Element ownerElement = rootElement.element("owner");

				owner = ownerElement.getTextTrim();

				if (Validator.isNull(owner)) {
					List<Element> hrefElements = ownerElement.elements("href");

					for (Element hrefElement : hrefElements) {
						owner =
							"<D:href>" + hrefElement.getTextTrim() +
								"</D:href>";
					}
				}
			}
			else {
				_log.error("Empty request XML");

				return HttpServletResponse.SC_PRECONDITION_FAILED;
			}

			status = storage.lockResource(webDAVRequest, owner, timeout);

			lock = (Lock)status.getObject();
		}
		else {
			try {

				// Refresh existing lock

				lock = storage.refreshResourceLock(
					webDAVRequest, lockUuid, timeout);

				status = new Status(HttpServletResponse.SC_OK);
			}
			catch (WebDAVException wdave) {
				if (wdave.getCause() instanceof NoSuchLockException) {
					return HttpServletResponse.SC_PRECONDITION_FAILED;
				}
				else {
					throw wdave;
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
		StringBundler sb = new StringBundler(21);

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
		sb.append("<D:timeout>");

		if (timeoutSecs > 0) {
			sb.append("Second-");
			sb.append(timeoutSecs);
		}
		else {
			sb.append("Infinite");
		}

		sb.append("</D:timeout>");
		sb.append("<D:locktoken><D:href>");
		sb.append(WebDAVUtil.TOKEN_PREFIX);
		sb.append(lock.getUuid());
		sb.append("</D:href></D:locktoken>");
		sb.append("</D:activelock>");
		sb.append("</D:lockdiscovery>");
		sb.append("</D:prop>");

		return Dom4jUtil.toString(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(LockMethodImpl.class);

}