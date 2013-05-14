/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.WebDAVProps;
import com.liferay.portal.service.WebDAVPropsLocalServiceUtil;
import com.liferay.portal.webdav.InvalidRequestException;
import com.liferay.portal.webdav.LockException;
import com.liferay.util.xml.XMLFormatter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 */
public class ProppatchMethodImpl extends BasePropMethodImpl {

	@Override
	public int process(WebDAVRequest webDAVRequest) throws WebDAVException {
		try {
			Set<QName> props = processInstructions(webDAVRequest);

			return writeResponseXML(webDAVRequest, props);
		}
		catch (InvalidRequestException ire) {
			if (_log.isInfoEnabled()) {
				_log.info(ire.getMessage(), ire);
			}

			return HttpServletResponse.SC_BAD_REQUEST;
		}
		catch (LockException le) {
			return WebDAVUtil.SC_LOCKED;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected WebDAVProps getStoredProperties(WebDAVRequest webDAVRequest)
		throws PortalException, SystemException {

		WebDAVStorage storage = webDAVRequest.getWebDAVStorage();

		Resource resource = storage.getResource(webDAVRequest);

		WebDAVProps webDavProps = null;

		if (resource.getPrimaryKey() <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("There is no primary key set for resource");
			}

			throw new InvalidRequestException();
		}
		else if (resource.isLocked()) {
			Lock lock = resource.getLock();

			if ((lock == null) ||
				!lock.getUuid().equals(webDAVRequest.getLockUuid())) {

				throw new LockException();
			}
		}

		webDavProps = WebDAVPropsLocalServiceUtil.getWebDAVProps(
			webDAVRequest.getCompanyId(), resource.getClassName(),
			resource.getPrimaryKey());

		return webDavProps;
	}

	protected Set<QName> processInstructions(WebDAVRequest webDAVRequest)
		throws InvalidRequestException, LockException {

		try {
			Set<QName> newProps = new HashSet<QName>();

			HttpServletRequest request = webDAVRequest.getHttpServletRequest();

			WebDAVProps webDavProps = getStoredProperties(webDAVRequest);

			String xml = new String(
				FileUtil.getBytes(request.getInputStream()));

			if (Validator.isNull(xml)) {
				return newProps;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Request XML: \n" +
						XMLFormatter.toString(xml, StringPool.FOUR_SPACES));
			}

			Document document = SAXReaderUtil.read(xml);

			Element rootElement = document.getRootElement();

			List<Element> instructionElements = rootElement.elements();

			for (Element instructionElement : instructionElements) {
				List<Element> propElements = instructionElement.elements();

				if (propElements.size() != 1) {
					throw new InvalidRequestException(
						"There should only be one <prop /> per set or remove " +
							"instruction.");
				}

				Element propElement = propElements.get(0);

				if (!propElement.getName().equals("prop") ||
					!propElement.getNamespaceURI().equals(
						WebDAVUtil.DAV_URI.getURI())) {

					throw new InvalidRequestException(
						"Invalid <prop /> element " + propElement);
				}

				List<Element> customPropElements = propElement.elements();

				for (Element customPropElement : customPropElements) {
					String name = customPropElement.getName();
					String prefix = customPropElement.getNamespacePrefix();
					String uri = customPropElement.getNamespaceURI();
					String text = customPropElement.getText();

					Namespace namespace = WebDAVUtil.createNamespace(
						prefix, uri);

					if (instructionElement.getName().equals("set")) {
						if (Validator.isNull(text)) {
							webDavProps.addProp(name, prefix, uri);
						}
						else {
							webDavProps.addProp(name, prefix, uri, text);
						}

						newProps.add(
							SAXReaderUtil.createQName(
								customPropElement.getName(), namespace));
					}
					else if (instructionElement.getName().equals("remove")) {
						webDavProps.removeProp(name, prefix, uri);
					}
					else {
						throw new InvalidRequestException(
							"Instead of set/remove instruction, received " +
								instructionElement);
					}
				}
			}

			WebDAVPropsLocalServiceUtil.storeWebDAVProps(webDavProps);

			return newProps;
		}
		catch (LockException le) {
			throw le;
		}
		catch (Exception e) {
			throw new InvalidRequestException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ProppatchMethodImpl.class);

}