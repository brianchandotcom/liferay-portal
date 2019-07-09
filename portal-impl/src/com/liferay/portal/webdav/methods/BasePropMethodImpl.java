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

import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.DocUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WebDAVProps;
import com.liferay.portal.kernel.service.WebDAVPropsLocalServiceUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 */
public abstract class BasePropMethodImpl implements Method {

	public static final QName ALLPROP = createQName("allprop");

	public static final QName CREATIONDATE = createQName("creationdate");

	public static final QName DISPLAYNAME = createQName("displayname");

	public static final QName GETCONTENTLENGTH = createQName(
		"getcontentlength");

	public static final QName GETCONTENTTYPE = createQName("getcontenttype");

	public static final QName GETLASTMODIFIED = createQName("getlastmodified");

	public static final QName ISREADONLY = createQName("isreadonly");

	public static final QName LOCKDISCOVERY = createQName("lockdiscovery");

	public static final QName RESOURCETYPE = createQName("resourcetype");

	protected static QName createQName(String name) {
		return SAXReaderUtil.createQName(name, WebDAVUtil.DAV_URI);
	}

	protected void addResponse(String href, Element multistatusElement)
		throws Exception {

		Element responseElement = DocUtil.add(
			multistatusElement, createQName("response"));

		DocUtil.add(responseElement, createQName("href"), href);

		Element propstatElement = DocUtil.add(
			responseElement, createQName("propstat"));

		DocUtil.add(
			propstatElement, createQName("status"), "HTTP/1.1 404 Not Found");
	}

	protected void addResponse(
			WebDAVRequest webDAVRequest, Resource resource, Set<QName> props,
			Element multistatus)
		throws Exception {

		// Make a deep copy of the props

		props = new HashSet<>(props);

		// Start building multistatus response

		Element responseElement = DocUtil.add(
			multistatus, createQName("response"));

		DocUtil.add(responseElement, createQName("href"), resource.getHREF());

		// Build success and failure propstat elements

		Element successStatElement = DocUtil.add(
			responseElement, createQName("propstat"));

		Element successPropElement = DocUtil.add(
			successStatElement, createQName("prop"));

		Element failureStatElement = DocUtil.add(
			responseElement, createQName("propstat"));

		Element failurePropElement = DocUtil.add(
			failureStatElement, createQName("prop"));

		boolean hasSuccess = false;
		boolean hasFailure = false;

		// Check DAV properties

		if (props.contains(ALLPROP)) {
			props.remove(ALLPROP);

			if (resource.isCollection()) {
				props.addAll(_allCollectionProps);
			}
			else {
				props.addAll(_allSimpleProps);
			}
		}

		if (props.contains(CREATIONDATE)) {
			props.remove(CREATIONDATE);

			DocUtil.add(
				successPropElement, CREATIONDATE, resource.getCreateDate());

			hasSuccess = true;
		}

		if (props.contains(DISPLAYNAME)) {
			props.remove(DISPLAYNAME);

			DocUtil.add(
				successPropElement, DISPLAYNAME, resource.getDisplayName());

			hasSuccess = true;
		}

		if (props.contains(GETLASTMODIFIED)) {
			props.remove(GETLASTMODIFIED);

			DocUtil.add(
				successPropElement, GETLASTMODIFIED,
				resource.getModifiedDate());

			hasSuccess = true;
		}

		if (props.contains(GETCONTENTTYPE)) {
			props.remove(GETCONTENTTYPE);

			DocUtil.add(
				successPropElement, GETCONTENTTYPE, resource.getContentType());

			hasSuccess = true;
		}

		if (props.contains(GETCONTENTLENGTH)) {
			props.remove(GETCONTENTLENGTH);

			if (!resource.isCollection()) {
				DocUtil.add(
					successPropElement, GETCONTENTLENGTH, resource.getSize());

				hasSuccess = true;
			}
			else {
				DocUtil.add(failurePropElement, GETCONTENTLENGTH);

				hasFailure = true;
			}
		}

		if (props.contains(ISREADONLY)) {
			props.remove(ISREADONLY);

			Lock lock = resource.getLock();

			if ((lock == null) || resource.isLocked()) {
				DocUtil.add(
					successPropElement, ISREADONLY, Boolean.FALSE.toString());
			}
			else {
				DocUtil.add(
					successPropElement, ISREADONLY, Boolean.TRUE.toString());
			}

			hasSuccess = true;
		}

		if (props.contains(LOCKDISCOVERY)) {
			props.remove(LOCKDISCOVERY);

			Lock lock = resource.getLock();

			if (lock != null) {
				Element lockDiscoveryElement = DocUtil.add(
					successPropElement, LOCKDISCOVERY);

				Element activeLockElement = DocUtil.add(
					lockDiscoveryElement, createQName("activelock"));

				Element lockTypeElement = DocUtil.add(
					activeLockElement, createQName("locktype"));

				DocUtil.add(lockTypeElement, createQName("write"));

				Element lockScopeElement = DocUtil.add(
					activeLockElement, createQName("lockscope"));

				DocUtil.add(lockScopeElement, createQName("exclusive"));

				if (resource.isCollection()) {
					DocUtil.add(
						activeLockElement, createQName("depth"), "Infinity");
				}

				DocUtil.add(
					activeLockElement, createQName("owner"), lock.getOwner());

				long timeRemaining = 0;

				Date expirationDate = lock.getExpirationDate();

				if (expirationDate != null) {
					long now = System.currentTimeMillis();

					timeRemaining =
						(expirationDate.getTime() - now) / Time.SECOND;

					if (timeRemaining <= 0) {
						timeRemaining = 1;
					}
				}

				if (timeRemaining > 0) {
					DocUtil.add(
						activeLockElement, createQName("timeout"),
						"Second-" + timeRemaining);
				}
				else {
					DocUtil.add(
						activeLockElement, createQName("timeout"), "Infinite");
				}

				if (webDAVRequest.getUserId() == lock.getUserId()) {
					Element lockTokenElement = DocUtil.add(
						activeLockElement, createQName("locktoken"));

					DocUtil.add(
						lockTokenElement, createQName("href"),
						"opaquelocktoken:" + lock.getUuid());
				}

				hasSuccess = true;
			}
			else {
				DocUtil.add(failurePropElement, LOCKDISCOVERY);

				hasFailure = true;
			}
		}

		if (props.contains(RESOURCETYPE)) {
			props.remove(RESOURCETYPE);

			Element resourceTypeElement = DocUtil.add(
				successPropElement, RESOURCETYPE);

			if (resource.isCollection()) {
				DocUtil.add(resourceTypeElement, createQName("collection"));
			}

			hasSuccess = true;
		}

		// Check remaining properties against custom properties

		WebDAVProps webDAVProps = WebDAVPropsLocalServiceUtil.getWebDAVProps(
			webDAVRequest.getCompanyId(), resource.getClassName(),
			resource.getPrimaryKey());

		Set<QName> customProps = webDAVProps.getPropsSet();

		for (QName qName : props) {
			if (customProps.contains(qName)) {
				Namespace namespace = qName.getNamespace();

				String text = webDAVProps.getText(
					qName.getName(), namespace.getPrefix(), namespace.getURI());

				DocUtil.add(successPropElement, qName, text);

				hasSuccess = true;
			}
			else {
				DocUtil.add(failurePropElement, qName);

				hasFailure = true;
			}
		}

		// Clean up propstats

		if (hasSuccess) {
			DocUtil.add(
				successStatElement, createQName("status"), "HTTP/1.1 200 OK");
		}
		else {
			responseElement.remove(successStatElement);
		}

		if (!hasSuccess && hasFailure) {
			DocUtil.add(
				failureStatElement, createQName("status"),
				"HTTP/1.1 404 Not Found");
		}
		else {
			responseElement.remove(failureStatElement);
		}
	}

	protected void addResponse(
			WebDAVStorage storage, WebDAVRequest webDAVRequest,
			Resource resource, Set<QName> props, Element multistatusElement,
			long depth)
		throws Exception {

		addResponse(webDAVRequest, resource, props, multistatusElement);

		if (resource.isCollection() && (depth != 0)) {
			List<Resource> storageResources = storage.getResources(
				webDAVRequest);

			for (Resource storageResource : storageResources) {
				addResponse(
					webDAVRequest, storageResource, props, multistatusElement);
			}
		}
	}

	protected int writeResponseXML(
			WebDAVRequest webDAVRequest, Set<QName> props)
		throws Exception {

		WebDAVStorage storage = webDAVRequest.getWebDAVStorage();

		Document document = SAXReaderUtil.createDocument();

		Element multistatusElement = SAXReaderUtil.createElement(
			createQName("multistatus"));

		document.setRootElement(multistatusElement);

		Resource resource = storage.getResource(webDAVRequest);

		if (resource != null) {
			long depth = WebDAVUtil.getDepth(
				webDAVRequest.getHttpServletRequest());

			addResponse(
				storage, webDAVRequest, resource, props, multistatusElement,
				depth);

			String xml = document.formattedString(StringPool.FOUR_SPACES);

			if (_log.isDebugEnabled()) {
				_log.debug("Response XML\n" + xml);
			}

			// Set the status prior to writing the XML

			int status = WebDAVUtil.SC_MULTI_STATUS;

			HttpServletResponse httpServletResponse =
				webDAVRequest.getHttpServletResponse();

			httpServletResponse.setContentType(ContentTypes.TEXT_XML_UTF8);
			httpServletResponse.setStatus(status);

			try {
				ServletResponseUtil.write(httpServletResponse, xml);

				httpServletResponse.flushBuffer();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			return status;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"No resource found for " + storage.getRootPath() +
					webDAVRequest.getPath());
		}

		return HttpServletResponse.SC_NOT_FOUND;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasePropMethodImpl.class);

	private static final List<QName> _allCollectionProps = Arrays.asList(
		CREATIONDATE, DISPLAYNAME, GETLASTMODIFIED, GETCONTENTTYPE,
		LOCKDISCOVERY, RESOURCETYPE);
	private static final List<QName> _allSimpleProps = Arrays.asList(
		CREATIONDATE, DISPLAYNAME, GETLASTMODIFIED, GETCONTENTTYPE,
		GETCONTENTLENGTH, ISREADONLY, LOCKDISCOVERY, RESOURCETYPE);

}