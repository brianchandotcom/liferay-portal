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

package com.liferay.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.TransferHeadersHelperUtil;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.struts.StrutsActionPortletURL;
import com.liferay.portal.util.PropsValues;

import java.io.Writer;

import java.lang.reflect.Constructor;

import java.security.PrivilegedAction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.MimeResponse;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;
import javax.portlet.filter.PortletResponseWrapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class PortletResponseImpl implements LiferayPortletResponse {

	public static PortletResponseImpl getPortletResponseImpl(
		PortletResponse portletResponse) {

		while (!(portletResponse instanceof PortletResponseImpl)) {
			if (portletResponse instanceof PortletResponseWrapper) {
				PortletResponseWrapper portletResponseWrapper =
					(PortletResponseWrapper)portletResponse;

				portletResponse = portletResponseWrapper.getResponse();
			}
			else {
				throw new RuntimeException(
					"Unable to unwrap the portlet response from " +
						portletResponse.getClass());
			}
		}

		return (PortletResponseImpl)portletResponse;
	}

	@Override
	public void addDateHeader(String name, long date) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		Long[] values = (Long[])_headers.get(name);

		if (values == null) {
			setDateHeader(name, date);
		}
		else {
			values = ArrayUtil.append(values, Long.valueOf(date));

			_headers.put(name, values);
		}
	}

	@Override
	public void addHeader(String name, String value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		String[] values = (String[])_headers.get(name);

		if (values == null) {
			setHeader(name, value);
		}
		else {
			values = ArrayUtil.append(values, value);

			_headers.put(name, values);
		}
	}

	@Override
	public void addIntHeader(String name, int value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		Integer[] values = (Integer[])_headers.get(name);

		if (values == null) {
			setIntHeader(name, value);
		}
		else {
			values = ArrayUtil.append(values, Integer.valueOf(value));

			_headers.put(name, values);
		}
	}

	@Override
	public void addProperty(Cookie cookie) {
		if (cookie == null) {
			throw new IllegalArgumentException();
		}

		Cookie[] cookies = (Cookie[])_headers.get("cookies");

		if (cookies == null) {
			_headers.put("cookies", new Cookie[] {cookie});
		}
		else {
			cookies = ArrayUtil.append(cookies, cookie);

			_headers.put("cookies", cookies);
		}
	}

	@Override
	public void addProperty(String key, Element element) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		if (StringUtil.equalsIgnoreCase(
				key, MimeResponse.MARKUP_HEAD_ELEMENT)) {

			if (StringUtil.equalsIgnoreCase(element.getNodeName(), "script") &&
				!element.hasChildNodes()) {

				// LPS-77798

				element = (Element)element.cloneNode(true);

				element.appendChild(_document.createTextNode(StringPool.SPACE));
			}

			List<Element> values = _markupHeadElements.get(key);

			if (values != null) {
				if (element != null) {
					values.add(element);
				}
				else {
					_markupHeadElements.remove(key);
				}
			}
			else {
				if (element != null) {
					values = new ArrayList<>();

					values.add(element);

					_markupHeadElements.put(key, values);
				}
			}
		}
	}

	@Override
	public void addProperty(String key, String value) {
		if (Validator.isNull(key)) {
			throw new IllegalArgumentException();
		}

		addHeader(key, value);
	}

	@Override
	public PortletURL createActionURL() {
		return createActionURL(portletName);
	}

	@Override
	public LiferayPortletURL createActionURL(String portletName) {
		return createLiferayPortletURL(
			portletName, PortletRequest.ACTION_PHASE);
	}

	@Override
	public Element createElement(String tagName) throws DOMException {
		if (_document == null) {
			try {
				DocumentBuilderFactory documentBuilderFactory =
					SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

				DocumentBuilder documentBuilder =
					documentBuilderFactory.newDocumentBuilder();

				_document = documentBuilder.newDocument();
			}
			catch (ParserConfigurationException pce) {
				throw new DOMException(
					DOMException.INVALID_STATE_ERR, pce.getMessage());
			}
		}

		return _document.createElement(tagName);
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle) {

		return createLiferayPortletURL(plid, portletName, lifecycle, true);
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle,
		boolean includeLinkToLayoutUuid) {

		return DoPrivilegedUtil.wrap(
			new LiferayPortletURLPrivilegedAction(
				plid, portletName, lifecycle, includeLinkToLayoutUuid));
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(String lifecycle) {
		return createLiferayPortletURL(portletName, lifecycle);
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		String portletName, String lifecycle) {

		return createLiferayPortletURL(_plid, portletName, lifecycle);
	}

	@Override
	public PortletURL createRenderURL() {
		return createRenderURL(portletName);
	}

	@Override
	public LiferayPortletURL createRenderURL(String portletName) {
		return createLiferayPortletURL(
			portletName, PortletRequest.RENDER_PHASE);
	}

	@Override
	public ResourceURL createResourceURL() {
		return createResourceURL(portletName);
	}

	@Override
	public LiferayPortletURL createResourceURL(String portletName) {
		return createLiferayPortletURL(
			portletName, PortletRequest.RESOURCE_PHASE);
	}

	@Override
	public String encodeURL(String path) {
		if ((path == null) ||
			(!path.startsWith("#") && !path.startsWith("/") &&
			 !path.contains("://"))) {

			// Allow '#' as well to workaround a bug in Oracle ADF 10.1.3

			throw new IllegalArgumentException(
				"URL path must start with a '/' or include '://'");
		}

		if (_urlEncoder != null) {
			return _urlEncoder.encodeURL(response, path);
		}
		else {
			return path;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public HttpServletRequest getHttpServletRequest() {
		return portletRequestImpl.getHttpServletRequest();
	}

	@Override
	public HttpServletResponse getHttpServletResponse() {
		return response;
	}

	public abstract String getLifecycle();

	@Override
	public String getNamespace() {
		if (_namespace == null) {
			_namespace = PortalUtil.getPortletNamespace(portletName);
		}

		return _namespace;
	}

	public long getPlid() {
		return _plid;
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	public String getPortletName() {
		return portletName;
	}

	public PortletRequestImpl getPortletRequest() {
		return portletRequestImpl;
	}

	@Override
	public Map<String, String[]> getProperties() {
		Map<String, String[]> properties = new LinkedHashMap<>();

		for (Map.Entry<String, Object> entry : _headers.entrySet()) {
			String name = entry.getKey();
			Object[] values = (Object[])entry.getValue();

			String[] valuesString = new String[values.length];

			for (int i = 0; i < values.length; i++) {
				valuesString[i] = values[i].toString();
			}

			properties.put(name, valuesString);
		}

		return properties;
	}

	public URLEncoder getUrlEncoder() {
		return _urlEncoder;
	}

	@Override
	public void setDateHeader(String name, long date) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (date <= 0) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new Long[] {Long.valueOf(date)});
		}
	}

	@Override
	public void setHeader(String name, String value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (Validator.isNull(value)) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new String[] {value});
		}
	}

	@Override
	public void setIntHeader(String name, int value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (value <= 0) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new Integer[] {Integer.valueOf(value)});
		}
	}

	public void setPlid(long plid) {
		_plid = plid;

		if (_plid <= 0) {
			Layout layout = (Layout)portletRequestImpl.getAttribute(
				WebKeys.LAYOUT);

			if (layout != null) {
				_plid = layout.getPlid();
			}
		}
	}

	@Override
	public void setProperty(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		setHeader(key, value);
	}

	public void setURLEncoder(URLEncoder urlEncoder) {
		_urlEncoder = urlEncoder;
	}

	public void transferHeaders(HttpServletResponse response) {
		TransferHeadersHelperUtil.transferHeaders(_headers, response);
	}

	@Override
	public void transferMarkupHeadElements() {
		List<Element> elements = _markupHeadElements.get(
			MimeResponse.MARKUP_HEAD_ELEMENT);

		if ((elements == null) || elements.isEmpty()) {
			return;
		}

		HttpServletRequest request = getHttpServletRequest();

		List<String> markupHeadElements = (List<String>)request.getAttribute(
			MimeResponse.MARKUP_HEAD_ELEMENT);

		if (markupHeadElements == null) {
			markupHeadElements = new ArrayList<>();

			request.setAttribute(
				MimeResponse.MARKUP_HEAD_ELEMENT, markupHeadElements);
		}

		for (Element element : elements) {
			try {
				Writer writer = new UnsyncStringWriter();

				TransformerFactory transformerFactory =
					TransformerFactory.newInstance();

				Transformer transformer = transformerFactory.newTransformer();

				transformer.setOutputProperty(
					OutputKeys.OMIT_XML_DECLARATION, "yes");

				transformer.transform(
					new DOMSource(element), new StreamResult(writer));

				markupHeadElements.add(writer.toString());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	protected LiferayPortletURL doCreateLiferayPortletURL(
		long plid, String portletName, String lifecycle,
		boolean includeLinkToLayoutUuid) {

		try {
			Layout layout = (Layout)portletRequestImpl.getAttribute(
				WebKeys.LAYOUT);
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequestImpl.getAttribute(
					WebKeys.THEME_DISPLAY);

			if ((layout == null) && (themeDisplay != null)) {
				layout = themeDisplay.getLayout();
			}

			if (_portletSetup == null) {
				if (themeDisplay == null) {
					_portletSetup =
						PortletPreferencesFactoryUtil.
							getStrictLayoutPortletSetup(
								layout, this.portletName);
				}
				else {
					_portletSetup = themeDisplay.getStrictLayoutPortletSetup(
						layout, this.portletName);
				}
			}

			String linkToLayoutUuid = GetterUtil.getString(
				_portletSetup.getValue("portletSetupLinkToLayoutUuid", null));

			if (PropsValues.PORTLET_CROSS_LAYOUT_INVOCATION_MODE.equals(
					"render") &&
				!PortletRequest.RENDER_PHASE.equals(lifecycle)) {

				includeLinkToLayoutUuid = false;
			}

			if (Validator.isNotNull(linkToLayoutUuid) &&
				includeLinkToLayoutUuid) {

				try {
					Layout linkedLayout =
						LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
							linkToLayoutUuid, layout.getGroupId(),
							layout.isPrivateLayout());

					plid = linkedLayout.getPlid();
				}
				catch (PortalException pe) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(pe, pe);
					}
				}
			}
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(se);
			}
		}

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = _plid;
		}

		LiferayPortletURL portletURL = null;

		Portlet portlet = getPortlet();

		String portletURLClass = portlet.getPortletURLClass();

		String portletId = portlet.getPortletId();

		if (portletId.equals(portletName) &&
			Validator.isNotNull(portletURLClass)) {

			if (portletURLClass.equals(
					StrutsActionPortletURL.class.getName())) {

				portletURL = new StrutsActionPortletURL(this, plid, lifecycle);
			}
			else {
				try {
					Constructor<? extends PortletURLImpl> constructor =
						_constructors.get(portletURLClass);

					if (constructor == null) {
						Class<?> portletURLClassObj = Class.forName(
							portletURLClass);

						constructor =
							(Constructor<? extends PortletURLImpl>)
								portletURLClassObj.getConstructor(
									new Class<?>[] {
										PortletResponseImpl.class, long.class,
										String.class
									});

						_constructors.put(portletURLClass, constructor);
					}

					portletURL = constructor.newInstance(
						new Object[] {this, plid, lifecycle});
				}
				catch (Exception e) {
					_log.error("Unable to create portlet URL", e);
				}
			}
		}

		if (portletURL == null) {
			if (portletName.equals(portlet.getPortletId())) {
				portletURL = PortletURLFactoryUtil.create(
					portletRequestImpl, portlet, plid, lifecycle);
			}
			else {
				portletURL = PortletURLFactoryUtil.create(
					portletRequestImpl, portletName, plid, lifecycle);
			}
		}

		try {
			if (portlet.hasWindowState(
					portletRequestImpl.getResponseContentType(),
					portletRequestImpl.getWindowState())) {

				portletURL.setWindowState(portletRequestImpl.getWindowState());
			}
		}
		catch (WindowStateException wse) {
			_log.error(wse.getMessage());
		}

		try {
			if (portlet.hasPortletMode(
					portletRequestImpl.getResponseContentType(),
					portletRequestImpl.getPortletMode())) {

				portletURL.setPortletMode(portletRequestImpl.getPortletMode());
			}
		}
		catch (PortletModeException pme) {
			_log.error(pme.getMessage());
		}

		return portletURL;
	}

	protected void init(
		PortletRequestImpl portletRequestImpl, HttpServletResponse response) {

		this.portletRequestImpl = portletRequestImpl;
		this.response = response;

		_portlet = portletRequestImpl.getPortlet();

		portletName = _portlet.getPortletId();

		_companyId = _portlet.getCompanyId();

		setPlid(portletRequestImpl.getPlid());
	}

	protected String portletName;
	protected PortletRequestImpl portletRequestImpl;
	protected HttpServletResponse response;

	private static final Log _log = LogFactoryUtil.getLog(
		PortletResponseImpl.class);

	private long _companyId;
	private final Map<String, Constructor<? extends PortletURLImpl>>
		_constructors = new ConcurrentHashMap<>();
	private Document _document;
	private final Map<String, Object> _headers = new LinkedHashMap<>();
	private final Map<String, List<Element>> _markupHeadElements =
		new LinkedHashMap<>();
	private String _namespace;
	private long _plid;
	private Portlet _portlet;
	private PortletPreferences _portletSetup;
	private URLEncoder _urlEncoder;

	private class LiferayPortletURLPrivilegedAction
		implements PrivilegedAction<LiferayPortletURL> {

		public LiferayPortletURLPrivilegedAction(
			long plid, String portletName, String lifecycle,
			boolean includeLinkToLayoutUuid) {

			_plid = plid;
			_portletName = portletName;
			_lifecycle = lifecycle;
			_includeLinkToLayoutUuid = includeLinkToLayoutUuid;
		}

		@Override
		public LiferayPortletURL run() {
			return doCreateLiferayPortletURL(
				_plid, _portletName, _lifecycle, _includeLinkToLayoutUuid);
		}

		private final boolean _includeLinkToLayoutUuid;
		private final String _lifecycle;
		private long _plid;
		private final String _portletName;

	}

}