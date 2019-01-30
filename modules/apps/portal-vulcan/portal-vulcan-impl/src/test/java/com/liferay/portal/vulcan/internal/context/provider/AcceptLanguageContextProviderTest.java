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

package com.liferay.portal.vulcan.internal.context.provider;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.context.AcceptLanguage;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.HttpHeaders;

import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Destination;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rubén Pulido
 * @author Cristina González
 */
public class AcceptLanguageContextProviderTest {

	@Test
	public void testCreateContextWithMultipleLocalesAndAcceptLanguageHeader() {
		AcceptLanguageContextProvider acceptLanguageContextProvider =
			new AcceptLanguageContextProvider();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.ACCEPT_LANGUAGE, "someValue");

		List<Locale> locales = Arrays.asList(
			Locale.JAPAN, Locale.GERMAN, Locale.US);

		mockHttpServletRequest.setPreferredLocales(locales);

		AcceptLanguage acceptLanguage =
			acceptLanguageContextProvider.createContext(
				_getMessage(mockHttpServletRequest));

		Assert.assertEquals(
			LocaleUtil.JAPAN, acceptLanguage.getPreferredLocale());
	}

	@Test
	public void testCreateContextWithMultipleLocalesAndNoAcceptLanguageHeaderAndNoUser() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		AcceptLanguageContextProvider acceptLanguageContextProvider =
			new AcceptLanguageContextProvider();

		List<Locale> locales = Arrays.asList(
			Locale.JAPAN, Locale.GERMAN, Locale.US);

		mockHttpServletRequest.setPreferredLocales(locales);

		AcceptLanguage acceptLanguage =
			acceptLanguageContextProvider.createContext(
				_getMessage(mockHttpServletRequest));

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> acceptLanguage.getPreferredLocale()
		).isInstanceOf(
			InternalServerErrorException.class
		);

		exception.hasMessageStartingWith(
			"Unable to obtain preferred locale because");
	}

	private Message _getMessage(HttpServletRequest httpServletRequest) {
		return new Message() {

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
			public <T> T get(Class<T> aClass) {
				return null;
			}

			@Override
			public Object get(Object key) {
				return null;
			}

			@Override
			public Collection<Attachment> getAttachments() {
				return null;
			}

			@Override
			public <T> T getContent(Class<T> aClass) {
				return null;
			}

			@Override
			public Set<Class<?>> getContentFormats() {
				return null;
			}

			@Override
			public Object getContextualProperty(String contextProperty) {
				if (Objects.equals(contextProperty, "HTTP.REQUEST")) {
					return httpServletRequest;
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
			public <T> void put(Class<T> aClass, T t) {
			}

			@Override
			public Object put(String key, Object value) {
				return null;
			}

			@Override
			public void putAll(Map<? extends String, ?> m) {
			}

			@Override
			public Object remove(Object key) {
				return null;
			}

			@Override
			public <T> void removeContent(Class<T> aClass) {
			}

			@Override
			public void resetContextCache() {
			}

			@Override
			public void setAttachments(Collection<Attachment> collection) {
			}

			@Override
			public <T> void setContent(Class<T> aClass, Object o) {
			}

			@Override
			public void setContextualProperty(String s, Object o) {
			}

			@Override
			public void setExchange(Exchange exchange) {
			}

			@Override
			public void setId(String s) {
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

		};
	}

}