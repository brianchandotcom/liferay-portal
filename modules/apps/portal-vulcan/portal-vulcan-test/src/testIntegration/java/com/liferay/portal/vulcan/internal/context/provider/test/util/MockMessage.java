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

package com.liferay.portal.vulcan.internal.context.provider.test.util;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Destination;

/**
 * @author Cristina González
 */
public class MockMessage implements Message {

	public MockMessage(HttpServletRequest httpServletRequest) {
		_method = null;
		_httpServletRequest = httpServletRequest;
	}

	public MockMessage(Method method, HttpServletRequest httpServletRequest) {
		_method = method;
		_httpServletRequest = httpServletRequest;
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return null;
	}

	@Override
	public <T> T get(Class<T> clazz) {
		return null;
	}

	@Override
	public Object get(Object key) {
		if ((key != null) && key.equals("org.apache.cxf.resource.method")) {
			return _method;
		}

		return null;
	}

	@Override
	public Collection<Attachment> getAttachments() {
		return null;
	}

	@Override
	public <T> T getContent(Class<T> clazz) {
		return null;
	}

	@Override
	public Set<Class<?>> getContentFormats() {
		return null;
	}

	@Override
	public Object getContextualProperty(String contextProperty) {
		if (Objects.equals(contextProperty, "HTTP.REQUEST")) {
			return _httpServletRequest;
		}

		return null;
	}

	@Override
	public Set<String> getContextualPropertyKeys() {
		return null;
	}

	@Override
	public Destination getDestination() {
		return null;
	}

	@Override
	public Exchange getExchange() {
		return null;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public InterceptorChain getInterceptorChain() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public <T> void put(Class<T> clazz, T t) {
	}

	@Override
	public Object put(String key, Object value) {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ?> map) {
	}

	@Override
	public <T> T remove(Class<T> clazz) {
		return null;
	}

	@Override
	public Object remove(Object key) {
		return null;
	}

	@Override
	public <T> void removeContent(Class<T> clazz) {
	}

	@Override
	public void resetContextCache() {
	}

	@Override
	public void setAttachments(Collection<Attachment> collection) {
	}

	@Override
	public <T> void setContent(Class<T> clazz, Object object) {
	}

	@Override
	public void setExchange(Exchange exchange) {
	}

	@Override
	public void setId(String id) {
	}

	@Override
	public void setInterceptorChain(InterceptorChain interceptorChain) {
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Collection<Object> values() {
		return null;
	}

	private final HttpServletRequest _httpServletRequest;
	private final Method _method;

}