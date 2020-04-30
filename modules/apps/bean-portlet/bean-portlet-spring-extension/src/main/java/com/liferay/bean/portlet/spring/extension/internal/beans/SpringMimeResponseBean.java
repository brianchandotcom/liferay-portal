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

package com.liferay.bean.portlet.spring.extension.internal.beans;

import com.liferay.bean.portlet.spring.extension.internal.scope.SpringScopedBeanManager;
import com.liferay.bean.portlet.spring.extension.internal.scope.SpringScopedBeanManagerThreadLocal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.Locale;

import javax.annotation.ManagedBean;
import javax.annotation.Priority;

import javax.portlet.ActionURL;
import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;
import javax.portlet.ResourceURL;
import javax.portlet.filter.MimeResponseWrapper;

import javax.servlet.http.Cookie;

import org.w3c.dom.Element;

/**
 * @author Neil Griffin
 */
@ManagedBean("mimeResponse")

// When the developer uses "@Inject MimeResponse", Spring must be able to
// disambiguate between MimeResponse, HeaderResponse, RenderResponse, and
// ResourceResponse. This is accomplished with @Priority. However, Spring only
// knows how to apply the @Priority annotation at the class-level for a class
// that represents a single bean. In other words, Spring does not know how to
// apply the @Priority annotation for a class like JSR362SpringBeanProducer that
// produces multiple types of beans via producer methods annotated with @Bean.

@Priority(2)

// In order to support unwrapping, it is necessary for this bean to extend
// MimeResponseWrapper. However, MimeResponseWrapper is designed in such a way
// that it requires the wrapped instance to be specified via the constructor.
// Since the instance is obtained from a request-based ThreadLocal, it is not
// possible to pass the instance via the constructor. Therefore each of the
// methods of PortletResponseWrapper and MimeResponseWrapper are overridden in
// this class.
public class SpringMimeResponseBean extends MimeResponseWrapper {

	public SpringMimeResponseBean() {

		// The superclass constructor requires a non-null instance or else
		// it will throw IllegalArgumentException.

		super(DummyMimeResponse.INSTANCE);
	}

	@Override
	public void addProperty(Cookie cookie) {
		PortletResponse portletResponse = getResponse();

		portletResponse.addProperty(cookie);
	}

	@Override
	public void addProperty(String key, Element element) {
		PortletResponse portletResponse = getResponse();

		portletResponse.addProperty(key, element);
	}

	@Override
	public void addProperty(String key, String value) {
		PortletResponse portletResponse = getResponse();

		portletResponse.addProperty(key, value);
	}

	@Override
	public <T extends PortletURL & ActionURL> T createActionURL() {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.createActionURL();
	}

	@Override
	public ActionURL createActionURL(Copy option) {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.createActionURL(option);
	}

	@Override
	public Element createElement(String tagName) {
		PortletResponse portletResponse = getResponse();

		return portletResponse.createElement(tagName);
	}

	@Override
	public <T extends PortletURL & RenderURL> T createRenderURL() {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.createRenderURL();
	}

	@Override
	public RenderURL createRenderURL(Copy option) {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.createRenderURL(option);
	}

	@Override
	public ResourceURL createResourceURL() {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.createResourceURL();
	}

	@Override
	public String encodeURL(String path) {
		PortletResponse portletResponse = getResponse();

		return portletResponse.encodeURL(path);
	}

	@Override
	public void flushBuffer() throws IOException {
		MimeResponse mimeResponse = getResponse();

		mimeResponse.flushBuffer();
	}

	@Override
	public int getBufferSize() {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.getBufferSize();
	}

	@Override
	public CacheControl getCacheControl() {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.getCacheControl();
	}

	@Override
	public String getCharacterEncoding() {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.getContentType();
	}

	@Override
	public Locale getLocale() {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.getLocale();
	}

	@Override
	public String getNamespace() {
		PortletResponse portletResponse = getResponse();

		return portletResponse.getNamespace();
	}

	@Override
	public OutputStream getPortletOutputStream() throws IOException {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.getPortletOutputStream();
	}

	@Override
	public String getProperty(String key) {
		PortletResponse portletResponse = getResponse();

		return portletResponse.getProperty(key);
	}

	@Override
	public Collection<String> getPropertyNames() {
		PortletResponse portletResponse = getResponse();

		return portletResponse.getPropertyNames();
	}

	@Override
	public Collection<String> getPropertyValues(String key) {
		PortletResponse portletResponse = getResponse();

		return portletResponse.getPropertyValues(key);
	}

	@Override
	public MimeResponse getResponse() {
		SpringScopedBeanManager springScopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		return (MimeResponse)springScopedBeanManager.getPortletResponse();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.getWriter();
	}

	@Override
	public boolean isCommitted() {
		MimeResponse mimeResponse = getResponse();

		return mimeResponse.isCommitted();
	}

	@Override
	public void reset() {
		MimeResponse mimeResponse = getResponse();

		mimeResponse.reset();
	}

	@Override
	public void resetBuffer() {
		MimeResponse mimeResponse = getResponse();

		mimeResponse.resetBuffer();
	}

	@Override
	public void setBufferSize(int size) {
		MimeResponse mimeResponse = getResponse();

		mimeResponse.setBufferSize(size);
	}

	@Override
	public void setProperty(String key, String value) {
		PortletResponse portletResponse = getResponse();

		portletResponse.setProperty(key, value);
	}

	@Override
	public void setResponse(MimeResponse mimeResponse) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setResponse(PortletResponse portletResponse) {
		throw new UnsupportedOperationException();
	}

}