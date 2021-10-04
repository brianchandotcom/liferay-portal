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

package com.liferay.portal.kernel.test.portlet;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portlet.test.MockActionURL;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

import javax.portlet.ActionResponse;
import javax.portlet.ActionURL;
import javax.portlet.MimeResponse;
import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import javax.xml.namespace.QName;

import org.springframework.mock.web.MockHttpServletResponse;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * @author Jürgen Kappler
 */
public class MockLiferayPortletActionResponse
	implements ActionResponse, LiferayPortletResponse {

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
	public void addProperty(String name, Element element) {
	}

	@Override
	public void addProperty(String name, String value) {
	}

	@Override
	public <T extends PortletURL & ActionURL> T createActionURL() {
		return (T)new MockActionURL();
	}

	@Override
	public ActionURL createActionURL(MimeResponse.Copy copy) {
		return new MockActionURL();
	}

	@Override
	public LiferayPortletURL createActionURL(String portletName) {
		return new MockLiferayPortletURL();
	}

	@Override
	public LiferayPortletURL createActionURL(
		String portletName, MimeResponse.Copy copy) {

		return new MockLiferayPortletURL();
	}

	@Override
	public Element createElement(String tagName) throws DOMException {
		return null;
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle) {

		return new MockLiferayPortletURL();
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle,
		boolean includeLinkToLayoutUuid) {

		return new MockLiferayPortletURL();
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle,
		MimeResponse.Copy copy) {

		return new MockLiferayPortletURL();
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle, MimeResponse.Copy copy,
		boolean includeLinkToLayoutUuid) {

		return new MockLiferayPortletURL();
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(String lifecycle) {
		return new MockLiferayPortletURL();
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		String portletName, String lifecycle) {

		return new MockLiferayPortletURL();
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		String portletName, String lifecycle, MimeResponse.Copy copy) {

		return new MockLiferayPortletURL();
	}

	@Override
	public RenderURL createRedirectURL(MimeResponse.Copy copy)
		throws IllegalStateException {

		return null;
	}

	@Override
	public <T extends PortletURL & RenderURL> T createRenderURL() {
		return (T)new MockLiferayPortletURL();
	}

	@Override
	public RenderURL createRenderURL(MimeResponse.Copy copy) {
		return null;
	}

	@Override
	public LiferayPortletURL createRenderURL(String portletName) {
		return new MockLiferayPortletURL();
	}

	@Override
	public LiferayPortletURL createRenderURL(
		String portletName, MimeResponse.Copy copy) {

		return new MockLiferayPortletURL();
	}

	@Override
	public ResourceURL createResourceURL() {
		return new MockLiferayPortletURL();
	}

	@Override
	public LiferayPortletURL createResourceURL(String portletName) {
		return new MockLiferayPortletURL();
	}

	@Override
	public String encodeURL(String url) {
		return null;
	}

	@Override
	public HttpServletResponse getHttpServletResponse() {
		return _mockHttpServletResponse;
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
	public PortletMode getPortletMode() {
		return null;
	}

	@Override
	public Map<String, String[]> getProperties() {
		return null;
	}

	@Override
	public String getProperty(String name) {
		return null;
	}

	@Override
	public Collection<String> getPropertyNames() {
		return null;
	}

	@Override
	public Collection<String> getPropertyValues(String key) {
		return null;
	}

	@Override
	public Map<String, String[]> getRenderParameterMap() {
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
	public void sendRedirect(String location) throws IOException {
	}

	@Override
	public void sendRedirect(String location, String renderUrlParamName)
		throws IOException {
	}

	@Override
	public void setDateHeader(String name, long date) {
	}

	@Override
	public void setEvent(QName qName, Serializable serializable) {
	}

	@Override
	public void setEvent(String name, Serializable serializable) {
	}

	@Override
	public void setHeader(String name, String value) {
	}

	@Override
	public void setIntHeader(String name, int value) {
	}

	@Override
	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {
	}

	@Override
	public void setProperty(String key, String value) {
	}

	@Override
	public void setRenderParameter(String name, String value) {
	}

	@Override
	public void setRenderParameter(String name, String... values) {
	}

	@Override
	public void setRenderParameters(Map<String, String[]> map) {
	}

	@Override
	public void setURLEncoder(URLEncoder urlEncoder) {
	}

	@Override
	public void setWindowState(WindowState windowState)
		throws WindowStateException {
	}

	@Override
	public void transferHeaders(HttpServletResponse httpServletResponse) {
	}

	@Override
	public void transferMarkupHeadElements() {
	}

	private final MockHttpServletResponse _mockHttpServletResponse =
		new MockHttpServletResponse();

}