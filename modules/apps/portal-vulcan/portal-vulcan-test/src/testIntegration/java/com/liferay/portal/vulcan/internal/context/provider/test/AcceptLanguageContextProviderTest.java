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

package com.liferay.portal.vulcan.internal.context.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.context.AcceptLanguage;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.HttpHeaders;

import junit.framework.AssertionFailedError;

import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Destination;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class AcceptLanguageContextProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_availableLocales = LanguageUtil.getAvailableLocales();
		_defaultLocale = LocaleUtil.getDefault();

		_company = CompanyTestUtil.addCompany();

		CompanyTestUtil.resetCompanyLocales(
			_company.getCompanyId(), Collections.singletonList(Locale.TAIWAN),
			Locale.TAIWAN);

		_company = CompanyLocalServiceUtil.getCompany(_company.getCompanyId());

		_user = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(), 0L);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CompanyTestUtil.resetCompanyLocales(
			PortalUtil.getDefaultCompanyId(), _availableLocales,
			_defaultLocale);

		CompanyLocalServiceUtil.deleteCompany(_company.getCompanyId());
	}

	@Test
	public void testCreateContextWithAcceptLanguageAndNoUser()
		throws PortalException {

		MockLanguageHttpServletRequest mockLanguageHttpServletRequest =
			new MockLanguageHttpServletRequest(
				Collections.singletonList(Locale.JAPAN));

		AcceptLanguage acceptLanguage =
			_acceptLanguageContextProvider.createContext(
				_getMessage(mockLanguageHttpServletRequest));

		Assert.assertEquals(Locale.JAPAN, acceptLanguage.getPreferredLocale());
	}

	@Test
	public void testCreateContextWithMultipleAcceptLanguageAndNoUser()
		throws PortalException {

		AcceptLanguage acceptLanguage =
			_acceptLanguageContextProvider.createContext(
				_getMessage(
					new MockLanguageHttpServletRequest(
						Arrays.asList(
							Locale.GERMAN, Locale.JAPAN, Locale.US))));

		Assert.assertEquals(Locale.GERMAN, acceptLanguage.getPreferredLocale());
	}

	@Test
	public void testCreateContextWithNoAcceptLanguageAndNoUser()
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("Host", _company.getVirtualHostname());

		mockHttpServletRequest.setRemoteHost(
			_company.getPortalURL(_group.getGroupId()));

		AcceptLanguage acceptLanguage =
			_acceptLanguageContextProvider.createContext(
				_getMessage(mockHttpServletRequest));

		User defaultUser = _company.getDefaultUser();

		Assert.assertEquals(LocaleUtil.TAIWAN, defaultUser.getLocale());

		Assert.assertEquals(
			defaultUser.getLocale(), acceptLanguage.getPreferredLocale());
	}

	@Test
	public void testCreateContextWithNoAcceptLanguageAndUser()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setRemoteHost(
			_company.getPortalURL(_group.getGroupId()));

		User user = UserTestUtil.addUser(
			_group.getGroupId(), LocaleUtil.BRAZIL);

		mockHttpServletRequest.setAttribute(WebKeys.USER_ID, user.getUserId());

		AcceptLanguage acceptLanguage =
			_acceptLanguageContextProvider.createContext(
				_getMessage(mockHttpServletRequest));

		Assert.assertEquals(
			user.getLocale(), acceptLanguage.getPreferredLocale());
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

	private static Set<Locale> _availableLocales;
	private static Company _company;
	private static Locale _defaultLocale;
	private static Group _group;
	private static User _user;

	@Inject(
		filter = "component.name=com.liferay.portal.vulcan.internal.context.provider.AcceptLanguageContextProvider"
	)
	private ContextProvider<AcceptLanguage> _acceptLanguageContextProvider;

	private class MockLanguageHttpServletRequest
		extends MockHttpServletRequest {

		public MockLanguageHttpServletRequest(List<Locale> locales)
			throws PortalException {

			addHeader("Host", _company.getVirtualHostname());

			setRemoteHost(_company.getPortalURL(_group.getGroupId()));

			if (Objects.nonNull(locales) && !locales.isEmpty()) {
				addHeader(
					HttpHeaders.ACCEPT_LANGUAGE,
					Stream.of(
						LocaleUtil.toW3cLanguageIds(
							locales.toArray(new Locale[locales.size()]))
					).collect(
						Collectors.joining(StringPool.COMMA)
					)
				);

				setPreferredLocales(locales);
			}
			else {
				throw new AssertionFailedError(
					"Request preferred locales should be provided");
			}
		}

	}

}