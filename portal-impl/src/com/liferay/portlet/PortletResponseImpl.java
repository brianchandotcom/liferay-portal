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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.Writer;

import java.lang.reflect.Constructor;

import java.security.PrivilegedAction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.PortletURLGenerationListener;
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

	public void addDateHeader(String name, long date) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		Long[] values = (Long[])_headers.get(name);

		if (values == null) {
			setDateHeader(name, date);
		}
		else {
			values = ArrayUtil.append(values, new Long(date));

			_headers.put(name, values);
		}
	}

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

	public void addIntHeader(String name, int value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		Integer[] values = (Integer[])_headers.get(name);

		if (values == null) {
			setIntHeader(name, value);
		}
		else {
			values = ArrayUtil.append(values, new Integer(value));

			_headers.put(name, values);
		}
	}

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

	public void addProperty(String key, Element element) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		if (key.equalsIgnoreCase(MimeResponse.MARKUP_HEAD_ELEMENT)) {
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
					values = new ArrayList<Element>();

					values.add(element);

					_markupHeadElements.put(key, values);
				}
			}
		}
	}

	public void addProperty(String key, String value) {
		if (Validator.isNull(key)) {
			throw new IllegalArgumentException();
		}

		addHeader(key, value);
	}

	public PortletURL createActionURL() {
		return createActionURL(_portletName);
	}

	public LiferayPortletURL createActionURL(String portletName) {
		return createLiferayPortletURL(
			portletName, PortletRequest.ACTION_PHASE);
	}

	public Element createElement(String tagName) throws DOMException {
		if (_document == null) {
			try {
				DocumentBuilderFactory documentBuilderFactory =
					DocumentBuilderFactory.newInstance();

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

	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle) {

		return createLiferayPortletURL(plid, portletName, lifecycle, true);
	}

	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle,
		boolean includeLinkToLayoutUuid) {

		return DoPrivilegedUtil.wrap(
			new LiferayPortletURLPrivilegedAction(
				plid, portletName, lifecycle, includeLinkToLayoutUuid));
	}

	public LiferayPortletURL createLiferayPortletURL(String lifecycle) {
		return createLiferayPortletURL(_portletName, lifecycle);
	}

	public LiferayPortletURL createLiferayPortletURL(
		String portletName, String lifecycle) {

		return createLiferayPortletURL(_plid, portletName, lifecycle);
	}

	public PortletURL createRenderURL() {
		return createRenderURL(_portletName);
	}

	public LiferayPortletURL createRenderURL(String portletName) {
		return createLiferayPortletURL(
			portletName, PortletRequest.RENDER_PHASE);
	}

	public ResourceURL createResourceURL() {
		return createResourceURL(_portletName);
	}

	public LiferayPortletURL createResourceURL(String portletName) {
		return createLiferayPortletURL(
			portletName, PortletRequest.RESOURCE_PHASE);
	}

	public String encodeURL(String path) {
		if ((path == null) ||
			(!path.startsWith("#") && !path.startsWith("/") &&
			 !path.contains("://"))) {

			// Allow '#' as well to workaround a bug in Oracle ADF 10.1.3

			throw new IllegalArgumentException(
				"URL path must start with a '/' or include '://'");
		}

		if (_urlEncoder != null) {
			return _urlEncoder.encodeURL(_response, path);
		}
		else {
			return path;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _portletRequestImpl.getHttpServletRequest();
	}

	public HttpServletResponse getHttpServletResponse() {
		return _response;
	}

	public abstract String getLifecycle();

	public String getNamespace() {
		if (_wsrp) {
			return "wsrp_rewrite_";
		}

		if (_namespace == null) {
			_namespace = PortalUtil.getPortletNamespace(_portletName);
		}

		return _namespace;
	}

	public long getPlid() {
		return _plid;
	}

	public Portlet getPortlet() {
		if (_portlet == null) {
			try {
				_portlet = PortletLocalServiceUtil.getPortletById(
					_companyId, _portletName);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return _portlet;
	}

	public String getPortletName() {
		return _portletName;
	}

	public PortletRequestImpl getPortletRequest() {
		return _portletRequestImpl;
	}

	public Map<String, String[]> getProperties() {
		Map<String, String[]> properties =
			new LinkedHashMap<String, String[]>();

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

	public void setDateHeader(String name, long date) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (date <= 0) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new Long[] {new Long(date)});
		}
	}

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

	public void setIntHeader(String name, int value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (value <= 0) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new Integer[] {new Integer(value)});
		}
	}

	public void setPlid(long plid) {
		_plid = plid;

		if (_plid <= 0) {
			Layout layout = (Layout)_portletRequestImpl.getAttribute(
				WebKeys.LAYOUT);

			if (layout != null) {
				_plid = layout.getPlid();
			}
		}
	}

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
		for (Map.Entry<String, Object> entry : _headers.entrySet()) {
			String name = entry.getKey();
			Object values = entry.getValue();

			if (values instanceof Integer[]) {
				Integer[] intValues = (Integer[])values;

				for (int value : intValues) {
					if (response.containsHeader(name)) {
						response.addIntHeader(name, value);
					}
					else {
						response.setIntHeader(name, value);
					}
				}
			}
			else if (values instanceof Long[]) {
				Long[] dateValues = (Long[])values;

				for (long value : dateValues) {
					if (response.containsHeader(name)) {
						response.addDateHeader(name, value);
					}
					else {
						response.setDateHeader(name, value);
					}
				}
			}
			else if (values instanceof String[]) {
				String[] stringValues = (String[])values;

				for (String value : stringValues) {
					if (response.containsHeader(name)) {
						response.addHeader(name, value);
					}
					else {
						response.setHeader(name, value);
					}
				}
			}
			else if (values instanceof Cookie[]) {
				Cookie[] cookies = (Cookie[])values;

				for (Cookie cookie : cookies) {
					response.addCookie(cookie);
				}
			}
		}
	}

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
			markupHeadElements = new ArrayList<String>();

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
			Layout layout = (Layout)_portletRequestImpl.getAttribute(
				WebKeys.LAYOUT);

			if (layout == null) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_portletRequestImpl.getAttribute(
						WebKeys.THEME_DISPLAY);

				if (themeDisplay != null) {
					layout = themeDisplay.getLayout();
				}
			}

			if (_portletSetup == null) {
				_portletSetup =
					PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
						layout, _portletName);
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

		PortletURLImpl portletURLImpl = null;

		Portlet portlet = getPortlet();

		String portletURLClass = portlet.getPortletURLClass();

		if (portlet.getPortletId().equals(portletName) &&
			Validator.isNotNull(portletURLClass)) {

			try {
				Constructor<? extends PortletURLImpl> constructor =
					_constructors.get(portletURLClass);

				if (constructor == null) {
					Class<?> portletURLClassObj = Class.forName(
						portletURLClass);

					constructor = (Constructor<? extends PortletURLImpl>)
						portletURLClassObj.getConstructor(
							new Class[] {
								com.liferay.portlet.PortletResponseImpl.class,
								long.class, String.class
							});

					_constructors.put(portletURLClass, constructor);
				}

				portletURLImpl = constructor.newInstance(
					new Object[] {this, plid, lifecycle});
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (portletURLImpl == null) {
			portletURLImpl = new PortletURLImpl(
				_portletRequestImpl, portletName, plid, lifecycle);
		}

		PortletApp portletApp = portlet.getPortletApp();

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			try {
				PortletURLGenerationListener portletURLGenerationListener =
					PortletURLListenerFactory.create(portletURLListener);

				if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
					portletURLGenerationListener.filterActionURL(
						portletURLImpl);
				}
				else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
					portletURLGenerationListener.filterRenderURL(
						portletURLImpl);
				}
				else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
					portletURLGenerationListener.filterResourceURL(
						portletURLImpl);
				}
			}
			catch (PortletException pe) {
				_log.error(pe, pe);
			}
		}

		try {
			portletURLImpl.setWindowState(_portletRequestImpl.getWindowState());
		}
		catch (WindowStateException wse) {
			_log.error(wse.getMessage());
		}

		try {
			portletURLImpl.setPortletMode(_portletRequestImpl.getPortletMode());
		}
		catch (PortletModeException pme) {
			_log.error(pme.getMessage());
		}

		if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			portletURLImpl.setCopyCurrentRenderParameters(true);
		}

		return portletURLImpl;
	}

	protected void init(
		PortletRequestImpl portletRequestImpl, HttpServletResponse response,
		String portletName, long companyId, long plid) {

		_portletRequestImpl = portletRequestImpl;
		_response = response;
		_portletName = portletName;
		_companyId = companyId;
		_wsrp = ParamUtil.getBoolean(getHttpServletRequest(), "wsrp");

		setPlid(plid);
	}

	private static Log _log = LogFactoryUtil.getLog(PortletResponseImpl.class);

	private long _companyId;
	private Map<String, Constructor<? extends PortletURLImpl>> _constructors =
		new ConcurrentHashMap<String, Constructor<? extends PortletURLImpl>>();
	private Document _document;
	private Map<String, Object> _headers = new LinkedHashMap<String, Object>();
	private Map<String, List<Element>> _markupHeadElements =
		new LinkedHashMap<String, List<Element>>();
	private String _namespace;
	private long _plid;
	private Portlet _portlet;
	private String _portletName;
	private PortletRequestImpl _portletRequestImpl;
	private PortletPreferences _portletSetup;
	private HttpServletResponse _response;
	private URLEncoder _urlEncoder;
	private boolean _wsrp;

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

		public LiferayPortletURL run() {
			return doCreateLiferayPortletURL(
				_plid, _portletName, _lifecycle, _includeLinkToLayoutUuid);
		}

		private boolean _includeLinkToLayoutUuid;
		private String _lifecycle;
		private long _plid;
		private String _portletName;

	}

}