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

package com.liferay.portal.servlet.filters.absoluteredirects.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.AvailableLocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.filters.absoluteredirects.AbsoluteRedirectsFilter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Noemi Zamarripa
 */
@RunWith(Arquillian.class)
public class VirtualHostAbsoluteRedirectsFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws PortalException {
		_groupId = TestPropsValues.getGroupId();

		_availableLocales = LanguageUtil.getAvailableLocales(_groupId);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			_groupId, false);

		_layoutSetId = layoutSet.getLayoutSetId();

		_treeMap = new TreeMap<>();
	}

	@After
	public void tearDown() throws PortalException {
		for (String hostname : _treeMap.keySet()) {
			VirtualHost virtualHost = _virtualHostLocalService.getVirtualHost(
				hostname);

			_virtualHostLocalService.deleteVirtualHost(virtualHost);
		}
	}

	@Test
	public void testMultipleVirtualHostsWithLocale() throws Exception {
		_treeMap.put(_TESTHOST_ES, _LANGUAGE_ID_ES);
		_treeMap.put(_TESTHOST_FR, _LANGUAGE_ID_FR);

		_layoutSetLocalService.updateVirtualHosts(_groupId, false, _treeMap);

		for (Map.Entry<String, String> entry : _treeMap.entrySet()) {
			String hostname = entry.getKey();
			String languageId = entry.getValue();

			_setupRequest(hostname, languageId);

			_invokeFilter();

			VirtualHost virtualHost = _virtualHostLocalService.getVirtualHost(
				hostname);

			Assert.assertEquals(
				virtualHost.getLanguageId(),
				_mockHttpServletRequest.getAttribute(WebKeys.I18N_LANGUAGE_ID));

			LayoutSet layoutSet =
				(LayoutSet)_mockHttpServletRequest.getAttribute(
					WebKeys.VIRTUAL_HOST_LAYOUT_SET);

			Assert.assertEquals(_layoutSetId, layoutSet.getLayoutSetId());

			_cleanUpRequest();
		}
	}

	@Test
	public void testMultipleVirtualHostsWithoutLocale() throws Exception {
		_treeMap.put(_TESTHOST_ES, StringPool.BLANK);
		_treeMap.put(_TESTHOST_FR, StringPool.BLANK);

		_layoutSetLocalService.updateVirtualHosts(_groupId, false, _treeMap);

		for (Map.Entry<String, String> entry : _treeMap.entrySet()) {
			String hostname = entry.getKey();
			String languageId = entry.getValue();

			_setupRequest(hostname, languageId);

			_invokeFilter();

			LayoutSet layoutSet =
				(LayoutSet)_mockHttpServletRequest.getAttribute(
					WebKeys.VIRTUAL_HOST_LAYOUT_SET);

			Assert.assertEquals(_layoutSetId, layoutSet.getLayoutSetId());

			_cleanUpRequest();
		}
	}

	@Test
	public void testSingleVirtualHostWithLocale() throws Exception {
		_treeMap.put(_TESTHOST_DE, _LANGUAGE_ID_DE);

		_layoutSetLocalService.updateVirtualHosts(_groupId, false, _treeMap);

		String hostname = _treeMap.firstKey();

		_setupRequest(hostname, _treeMap.get(hostname));

		_invokeFilter();

		VirtualHost virtualHost = _virtualHostLocalService.getVirtualHost(
			hostname);

		Assert.assertEquals(
			virtualHost.getLanguageId(),
			_mockHttpServletRequest.getAttribute(WebKeys.I18N_LANGUAGE_ID));

		LayoutSet layoutSet = (LayoutSet)_mockHttpServletRequest.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		Assert.assertEquals(_layoutSetId, layoutSet.getLayoutSetId());
	}

	@Test
	public void testSingleVirtualHostWithoutLocale() throws Exception {
		_treeMap.put(_TESTHOST_DE, StringPool.BLANK);

		_layoutSetLocalService.updateVirtualHosts(_groupId, false, _treeMap);

		String hostname = _treeMap.firstKey();

		_setupRequest(hostname, _treeMap.get(hostname));

		_invokeFilter();

		LayoutSet layoutSet = (LayoutSet)_mockHttpServletRequest.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		Assert.assertEquals(_layoutSetId, layoutSet.getLayoutSetId());
	}

	private void _cleanUpRequest() {
		_mockHttpServletRequest.invalidate();
	}

	private void _invokeFilter() throws Exception {
		_absoluteRedirectsFilter.doFilterTry(
			_mockHttpServletRequest, new MockHttpServletResponse());
	}

	private void _setupRequest(String hostname, String languageId)
		throws AvailableLocaleException {

		_mockHttpServletRequest = new MockHttpServletRequest();

		if (Validator.isNotNull(languageId)) {
			List<Locale> locales = new ArrayList<>();

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			locales.add(locale);

			if (!_availableLocales.contains(locale)) {
				throw new AvailableLocaleException(languageId);
			}

			_mockHttpServletRequest.setPreferredLocales(locales);
		}

		_mockHttpServletRequest.setRemoteHost(hostname);
		_mockHttpServletRequest.setServerName(hostname);
		_mockHttpServletRequest.setServerPort(_SERVER_PORT);
		_mockHttpServletRequest.setRequestURI(_WEB_GUEST);
		_mockHttpServletRequest.addHeader(_HOST, hostname);
	}

	private static final String _HOST = "Host";

	private static final String _LANGUAGE_ID_DE = "de_DE";

	private static final String _LANGUAGE_ID_ES = "es_ES";

	private static final String _LANGUAGE_ID_FR = "fr_FR";

	private static final int _SERVER_PORT = 8080;

	private static final String _TESTHOST_DE = "testhost.de";

	private static final String _TESTHOST_ES = "testhost.es";

	private static final String _TESTHOST_FR = "testhost.fr";

	private static final String _WEB_GUEST = "/web/guest";

	private static Set<Locale> _availableLocales;
	private static long _groupId;
	private static long _layoutSetId;

	@Inject
	private static LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private static VirtualHostLocalService _virtualHostLocalService;

	private final AbsoluteRedirectsFilter _absoluteRedirectsFilter =
		new AbsoluteRedirectsFilter();
	private MockHttpServletRequest _mockHttpServletRequest;
	private TreeMap<String, String> _treeMap;

}