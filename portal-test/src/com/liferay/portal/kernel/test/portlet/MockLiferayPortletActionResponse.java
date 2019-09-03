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
	public void addProperty(String s, Element element) {
	}

	@Override
	public void addProperty(String s, String s1) {
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
	public Element createElement(String s) throws DOMException {
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
		long plid, String portletName, String lifecycle, MimeResponse.Copy copy,
		boolean includeLinkToLayoutUuid) {

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
	public RenderURL createRedirectURL(MimeResponse.Copy copy)
		throws IllegalStateException {

		return null;
	}

	@Override
	public <T extends PortletURL & RenderURL> T createRenderURL() {
		return null;
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
	public String encodeURL(String s) {
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
	public PortletMode getPortletMode() {
		return null;
	}

	@Override
	public Map<String, String[]> getProperties() {
		return null;
	}

	@Override
	public String getProperty(String s) {
		return null;
	}

	@Override
	public Collection<String> getPropertyNames() {
		return null;
	}

	@Override
	public Collection<String> getPropertyValues(String s) {
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
	public void removePublicRenderParameter(String s) {
	}

	@Override
	public void sendRedirect(String s) throws IOException {
	}

	@Override
	public void sendRedirect(String s, String s1) throws IOException {
	}

	@Override
	public void setDateHeader(String name, long date) {
	}

	@Override
	public void setEvent(QName qName, Serializable serializable) {
	}

	@Override
	public void setEvent(String s, Serializable serializable) {
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
	public void setProperty(String s, String s1) {
	}

	@Override
	public void setRenderParameter(String s, String s1) {
	}

	@Override
	public void setRenderParameter(String s, String... strings) {
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

}