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

package com.liferay.portal.servlet.filters.multihost.locale.test;

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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Noemi Zamarripa
 */
@RunWith(Arquillian.class)
public class MultiHostLocaleFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws PortalException {
		_groupId = TestPropsValues.getGroupId();
		_availableLocales = LanguageUtil.getAvailableLocales(_groupId);
		_layoutSetId = _layoutSetLocalService.getLayoutSet(
			_groupId, false).getLayoutSetId();
	}

	@After
	public void tearDown() throws PortalException {
		for (String hostname : _treeMap.keySet()) {
			VirtualHost host = _virtualHostLocalService.getVirtualHost(hostname);

			_virtualHostLocalService.deleteVirtualHost(host);
		}
	}

	@Test
	public void testMultiHostNameAndLocale1() throws Exception {

		_treeMap.put(_TESTHOST_FR, _FR_LANGUAGE_ID);
		_treeMap.put(_TESTHOST_JP, _JP_LANGUAGE_ID);

		_layoutSetLocalService.updateVirtualHosts(
			_groupId, false, _treeMap);

		for (String host : _treeMap.keySet()) {
			_setupRequest(host, _treeMap.get(host));

			_invokeRequestFilter();

			Assert.assertEquals(
				_mockHttpServletRequest.getAttribute(WebKeys.I18N_LANGUAGE_ID),
				_virtualHostLocalService.getVirtualHost(host).getLanguageId());

			Assert.assertEquals(((LayoutSet)
				_mockHttpServletRequest.getAttribute(
				WebKeys.VIRTUAL_HOST_LAYOUT_SET)).getLayoutSetId(), _layoutSetId);

			_cleanUpRequest();
		}
	}

	@Test
	public void testMultiHostNameAndLocale2() throws Exception {

		_treeMap.put(_TESTHOST_DE, _DE_LANGUAGE_ID);

		_layoutSetLocalService.updateVirtualHosts(
			_groupId, false, _treeMap);

		String hostname = _treeMap.firstKey();
		_setupRequest(hostname, _treeMap.get(hostname));

		_invokeRequestFilter();

		Assert.assertEquals(
			_mockHttpServletRequest.getAttribute(WebKeys.I18N_LANGUAGE_ID),
			_virtualHostLocalService.getVirtualHost(hostname).getLanguageId());

		Assert.assertEquals(((LayoutSet)
			_mockHttpServletRequest.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET)).getLayoutSetId(), _layoutSetId);
	}

	@Test
	public void testMultiHostName1() throws Exception{

		_treeMap.put(_TESTHOST_FR, StringPool.BLANK);
		_treeMap.put(_TESTHOST_JP, StringPool.BLANK);

		_layoutSetLocalService.updateVirtualHosts(
			_groupId, false, _treeMap);

		for (String host : _treeMap.keySet()) {
			_setupRequest(host, _treeMap.get(host));

			_invokeRequestFilter();

			Assert.assertEquals(((LayoutSet)
				_mockHttpServletRequest.getAttribute(
				WebKeys.VIRTUAL_HOST_LAYOUT_SET)).getLayoutSetId(), _layoutSetId);

			_cleanUpRequest();
		}
	}

	@Test
	public void testMultiHostName2() throws Exception{

		_treeMap.put(_TESTHOST_DE, StringPool.BLANK);

		_layoutSetLocalService.updateVirtualHosts(
			_groupId, false, _treeMap);

		String hostname = _treeMap.firstKey();
		_setupRequest(hostname, _treeMap.get(hostname));

		_invokeRequestFilter();

		Assert.assertEquals(((LayoutSet)
			_mockHttpServletRequest.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET)).getLayoutSetId(), _layoutSetId);
	}

	private void _invokeRequestFilter() throws Exception {
		_absoluteRedirectsFilter.doFilterTry(
			_mockHttpServletRequest, _mockHttpServletResponse);
	}

	private void _setupRequest(String hostname, String languageId)
		throws AvailableLocaleException {

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

	private void _cleanUpRequest() {
		_mockHttpServletRequest.invalidate();
		_mockHttpServletRequest = new MockHttpServletRequest();
	}

	private static final String _HOST = "Host";

	private static final String _WEB_GUEST = "/web/guest";

	private static final String _TESTHOST_DE = "testhost.de";

	private static final String _DE_LANGUAGE_ID = "de_DE";

	private static final String _TESTHOST_FR = "testhost.fr";

	private static final String _FR_LANGUAGE_ID = "fr_FR";

	private static final String _TESTHOST_JP = "testhost.jp";

	private static final String _JP_LANGUAGE_ID = "ja_JP";

	private static final int _SERVER_PORT = 8080;

	private static long _groupId;

	private static long _layoutSetId;

	private static Set<Locale> _availableLocales;

	private TreeMap<String, String> _treeMap = new TreeMap<>();

	private MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private final MockHttpServletResponse _mockHttpServletResponse =
		new MockHttpServletResponse();
	private final AbsoluteRedirectsFilter _absoluteRedirectsFilter =
		new AbsoluteRedirectsFilter();

	@Inject
	private static LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private static VirtualHostLocalService _virtualHostLocalService;
}
