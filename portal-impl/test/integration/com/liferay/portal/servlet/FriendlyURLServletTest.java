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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author László Csontos
 */
public class FriendlyURLServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_group = GroupTestUtil.addGroup();

		List<Locale> availableLocales = Arrays.asList(LocaleUtil.US);

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, LocaleUtil.US);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetRedirectWithExistentSite() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		testGetRedirect(
			getPath(_group, layout), Portal.PATH_MAIN,
			new Object[] {getURL(layout), false});
	}

	@Test
	public void testGetRedirectWithInvalidI18nPath() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		_mockHttpServletRequest.setAttribute(WebKeys.I18N_PATH, "/fr");
		_mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		String requestURI =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				getPath(_group, layout);

		_mockHttpServletRequest.setRequestURI(requestURI);

		testGetRedirect(
			_group.getFriendlyURL(), Portal.PATH_MAIN,
			new Object[] {"/en" + requestURI, true});
		testGetRedirect(
			getPath(_group, layout), Portal.PATH_MAIN,
			new Object[] {"/en" + requestURI, true});
	}

	@Test
	public void testGetRedirectWithInvalidPath() throws Exception {
		testGetRedirect(
			null, Portal.PATH_MAIN, new Object[] {Portal.PATH_MAIN, false});
		testGetRedirect(
			"test", Portal.PATH_MAIN, new Object[] {Portal.PATH_MAIN, false});
	}

	@Test(expected = NoSuchGroupException.class)
	public void testGetRedirectWithNonexistentSite() throws Exception {
		testGetRedirect("/nonexistent-site/home", Portal.PATH_MAIN, null);
	}

	protected String getPath(Group group, Layout layout) {
		return group.getFriendlyURL() + layout.getFriendlyURL();
	}

	protected String getURL(Layout layout) {
		return "/c/portal/layout?p_l_id=" + layout.getPlid() +
			"&p_v_l_s_g_id=0";
	}

	protected void testGetRedirect(
			String path, String mainPath, Object[] expectedRedirectArray)
		throws Exception {

		Object[] actualRedirectArray = _friendlyURLServlet.getRedirect(
			_mockHttpServletRequest, path, mainPath,
			Collections.<String, String[]>emptyMap());

		Assert.assertArrayEquals(actualRedirectArray, expectedRedirectArray);
	}

	private final FriendlyURLServlet _friendlyURLServlet =
		new FriendlyURLServlet();

	@DeleteAfterTestRun
	private Group _group;

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

}