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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.xml.Dom4jUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.NoSuchLockException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
		catch (Exception exception) {
			throw new WebDAVException(exception);
		}
	}

	protected int doProcess(WebDAVRequest webDAVRequest) throws Exception {
		WebDAVStorage storage = webDAVRequest.getWebDAVStorage();

		if (!storage.isSupportsClassTwo()) {
			return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		}

		HttpServletRequest httpServletRequest =
			webDAVRequest.getHttpServletRequest();

		Lock lock = null;
		Status status = null;

		String lockUuid = webDAVRequest.getLockUuid();
		long timeout = WebDAVUtil.getTimeout(httpServletRequest);

		if (Validator.isNull(lockUuid)) {

			// Create new lock

			String owner = null;
			String xml = new String(
				FileUtil.getBytes(httpServletRequest.getInputStream()));

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
			catch (WebDAVException webDAVException) {
				if (webDAVException.getCause() instanceof NoSuchLockException) {
					return HttpServletResponse.SC_PRECONDITION_FAILED;
				}

				throw webDAVException;
			}
		}

		// Return lock details

		if (lock == null) {
			return status.getCode();
		}

		String xml = getResponseXML(
			lock, WebDAVUtil.getDepth(httpServletRequest));

		if (_log.isDebugEnabled()) {
			_log.debug("Response XML\n" + xml);
		}

		HttpServletResponse httpServletResponse =
			webDAVRequest.getHttpServletResponse();

		String lockToken = StringBundler.concat(
			"<", WebDAVUtil.TOKEN_PREFIX, lock.getUuid(), ">");

		httpServletResponse.setContentType(ContentTypes.TEXT_XML_UTF8);
		httpServletResponse.setHeader("Lock-Token", lockToken);
		httpServletResponse.setStatus(status.getCode());

		if (_log.isDebugEnabled()) {
			_log.debug("Returning lock token " + lockToken);
		}

		try {
			ServletResponseUtil.write(httpServletResponse, xml);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return status.getCode();
	}

	protected String getResponseXML(Lock lock, long depth) throws Exception {
		Document document = SAXReaderUtil.createDocument();

		Element propElement = document.addElement("D:prop", "DAV:");

		Element lockDiscoveryElement = propElement.addElement(
			"D:lockdiscovery");

		Element activeLockElement = lockDiscoveryElement.addElement(
			"D:activelock");

		Element lockTypeElement = activeLockElement.addElement("D:locktype");

		lockTypeElement.addElement("D:write");

		Element lockScopeElement = activeLockElement.addElement("D:lockscope");

		lockScopeElement.addElement("D:exclusive");

		if (depth < 0) {
			Element depthElement = activeLockElement.addElement("D:depth");

			depthElement.addText("Infinity");
		}

		Element ownerElement = activeLockElement.addElement("D:owner");

		ownerElement.addText(lock.getOwner());

		Element timeoutElement = activeLockElement.addElement("D:timeout");

		long timeoutSecs = lock.getExpirationTime() / Time.SECOND;

		if (timeoutSecs > 0) {
			timeoutElement.addText("Second-" + timeoutSecs);
		}
		else {
			timeoutElement.addText("Infinite");
		}

		Element lockTokenElement = activeLockElement.addElement("D:locktoken");

		Element hrefElement = lockTokenElement.addElement("D:href");

		hrefElement.addText(WebDAVUtil.TOKEN_PREFIX + lock.getUuid());

		return document.formattedString();
	}

	private static final Log _log = LogFactoryUtil.getLog(LockMethodImpl.class);

}