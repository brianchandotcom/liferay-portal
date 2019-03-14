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

package com.liferay.document.library.web.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.util.JavaConstants;

import java.io.Writer;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionParameters;
import javax.portlet.ActionURL;
import javax.portlet.MimeResponse;
import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderParameters;
import javax.portlet.RenderURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.annotations.PortletSerializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.xml.namespace.QName;

import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockPortletConfig;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
@Sync
public class DLAdminDisplayContextTest {

	private HttpServletRequest _getHttpServletRequest(
		HttpServletRequest httpServletRequest) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST,
			new MockLiferayPortletRequest(httpServletRequest));

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletresponse());

		return mockHttpServletRequest;
	}

	private static class MockLiferayPortletConfig
		extends MockPortletConfig implements LiferayPortletConfig {

		@Override
		public Portlet getPortlet() {
			return null;
		}

		@Override
		public String getPortletId() {
			return "testPortlet";
		}

		@Override
		public Enumeration<PortletMode> getPortletModes(String mimeType) {
			return null;
		}

		@Override
		public Map<String, QName> getPublicRenderParameterDefinitions() {
			return null;
		}

		@Override
		public Enumeration<WindowState> getWindowStates(String mimeType) {
			return null;
		}

		@Override
		public boolean isCopyRequestParameters() {
			return false;
		}

		@Override
		public boolean isWARFile() {
			return false;
		}

	}

	private static class MockLiferayPortletRequest
		extends MockActionRequest implements LiferayPortletRequest {

		public MockLiferayPortletRequest(
			HttpServletRequest httpServletRequest) {

			_httpServletRequest = httpServletRequest;
		}

		@Override
		public void addParameter(String name, String value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void cleanUp() {
		}

		@Override
		public Map<String, String[]> clearRenderParameters() {
			return null;
		}

		@Override
		public void defineObjects(
			PortletConfig portletConfig, PortletResponse portletResponse) {
		}

		@Override
		public ActionParameters getActionParameters() {
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			if (Objects.equals(name, JavaConstants.JAVAX_PORTLET_CONFIG)) {
				return new MockLiferayPortletConfig();
			}

			return super.getAttribute(name);
		}

		@Override
		public long getContentLengthLong() {
			return 0;
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return _httpServletRequest;
		}

		@Override
		public String getLifecycle() {
			return null;
		}

		@Override
		public HttpServletRequest getOriginalHttpServletRequest() {
			return _httpServletRequest;
		}

		@Override
		public Part getPart(String name) {
			return null;
		}

		@Override
		public Collection<Part> getParts() {
			return null;
		}

		@Override
		public long getPlid() {
			return 0;
		}

		@Override
		public Portlet getPortlet() {
			return null;
		}

		@Override
		public PortletContext getPortletContext() {
			return null;
		}

		@Override
		public String getPortletName() {
			return null;
		}

		@Override
		public HttpServletRequest getPortletRequestDispatcherRequest() {
			return null;
		}

		@Override
		public RenderParameters getRenderParameters() {
			return null;
		}

		@Override
		public String getUserAgent() {
			return null;
		}

		@Override
		public void invalidateSession() {
		}

		@Override
		public void setPortletRequestDispatcherRequest(
			HttpServletRequest request) {
		}

		private final HttpServletRequest _httpServletRequest;

	}

	private static class MockLiferayPortletresponse
		implements LiferayPortletResponse {

		@Override
		public void addDateHeader(String name, long date) {
		}

		@Override
		public void addHeader(String name, String value) {
		}

		@Override
		public void addIntHeader(String name, int value) {
		}

		@Override
		public void addProperty(Cookie cookie) {
		}

		@Override
		public void addProperty(String key, Element element) {
		}

		@Override
		public void addProperty(String key, String value) {
		}

		@Override
		public <T extends PortletURL & ActionURL> T createActionURL() {
			return null;
		}

		@Override
		public ActionURL createActionURL(MimeResponse.Copy copy) {
			return null;
		}

		@Override
		public LiferayPortletURL createActionURL(String portletName) {
			return null;
		}

		@Override
		public LiferayPortletURL createActionURL(
			String portletName, MimeResponse.Copy copy) {

			return null;
		}

		@Override
		public Element createElement(String tagName) throws DOMException {
			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			long plid, String portletName, String lifecycle) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			long plid, String portletName, String lifecycle,
			boolean includeLinkToLayoutUuid) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			long plid, String portletName, String lifecycle,
			MimeResponse.Copy copy) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			long plid, String portletName, String lifecycle,
			MimeResponse.Copy copy, boolean includeLinkToLayoutUuid) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(String lifecycle) {
			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			String portletName, String lifecycle) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			String portletName, String lifecycle, MimeResponse.Copy copy) {

			return null;
		}

		@Override
		public MockPortletURL createRenderURL() {
			return new MockPortletURL();
		}

		@Override
		public RenderURL createRenderURL(MimeResponse.Copy copy) {
			return null;
		}

		@Override
		public LiferayPortletURL createRenderURL(String portletName) {
			return null;
		}

		@Override
		public LiferayPortletURL createRenderURL(
			String portletName, MimeResponse.Copy copy) {

			return null;
		}

		@Override
		public ResourceURL createResourceURL() {
			return null;
		}

		@Override
		public LiferayPortletURL createResourceURL(String portletName) {
			return null;
		}

		@Override
		public String encodeURL(String path) {
			return null;
		}

		@Override
		public HttpServletResponse getHttpServletResponse() {
			return null;
		}

		@Override
		public String getLifecycle() {
			return null;
		}

		@Override
		public String getNamespace() {
			return null;
		}

		@Override
		public Portlet getPortlet() {
			return null;
		}

		@Override
		public Map<String, String[]> getProperties() {
			return null;
		}

		@Override
		public String getProperty(String key) {
			return null;
		}

		@Override
		public Collection<String> getPropertyNames() {
			return null;
		}

		@Override
		public Collection<String> getPropertyValues(String name) {
			return null;
		}

		@Override
		public void setDateHeader(String name, long date) {
		}

		@Override
		public void setHeader(String name, String value) {
		}

		@Override
		public void setIntHeader(String name, int value) {
		}

		@Override
		public void setProperty(String key, String value) {
		}

		@Override
		public void setURLEncoder(URLEncoder urlEncoder) {
		}

		@Override
		public void transferHeaders(HttpServletResponse response) {
		}

		@Override
		public void transferMarkupHeadElements() {
		}

	}

	private static class MockPortletURL implements PortletURL, RenderURL {

		@Override
		public void addProperty(String key, String value) {
		}

		@Override
		public Appendable append(Appendable appendable) {
			return null;
		}

		@Override
		public Appendable append(Appendable appendable, boolean escapeXML) {
			return null;
		}

		@Override
		public String getFragmentIdentifier() {
			return null;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return null;
		}

		@Override
		public PortletMode getPortletMode() {
			return null;
		}

		@Override
		public MutableRenderParameters getRenderParameters() {
			return null;
		}

		@Override
		public WindowState getWindowState() {
			return null;
		}

		@Override
		public void removePublicRenderParameter(String name) {
		}

		@Override
		public void setBeanParameter(PortletSerializable portletSerializable) {
		}

		@Override
		public void setFragmentIdentifier(String fragment) {
		}

		@Override
		public void setParameter(String name, String value) {
		}

		@Override
		public void setParameter(String name, String... values) {
		}

		@Override
		public void setParameters(Map<String, String[]> map) {
		}

		@Override
		public void setPortletMode(PortletMode portletMode) {
		}

		@Override
		public void setProperty(String key, String value) {
		}

		@Override
		public void setSecure(boolean secure) {
		}

		@Override
		public void setWindowState(WindowState windowState) {
		}

		@Override
		public void write(Writer writer) {
		}

		@Override
		public void write(Writer writer, boolean escapeXML) {
		}

	}

}