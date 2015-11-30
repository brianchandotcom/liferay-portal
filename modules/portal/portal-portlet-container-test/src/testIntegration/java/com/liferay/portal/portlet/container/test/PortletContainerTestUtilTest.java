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

package com.liferay.portal.portlet.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.PortletContainerTestUtil;
import com.liferay.portal.util.test.PortletContainerTestUtil.Response;

import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class PortletContainerTestUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testGetHttpServletRequest() throws Exception {
		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(_group, _layout);

		Assert.assertSame(
			_layout, httpServletRequest.getAttribute(WebKeys.LAYOUT));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = CompanyLocalServiceUtil.getCompany(
			_layout.getCompanyId());

		Assert.assertEquals(company, themeDisplay.getCompany());
		Assert.assertEquals(
			_layout.getCompanyId(), themeDisplay.getCompanyId());
		Assert.assertSame(_layout, themeDisplay.getLayout());
		Assert.assertEquals(_layout.getPlid(), themeDisplay.getPlid());
		Assert.assertEquals(
			TestPropsValues.PORTAL_URL, themeDisplay.getPortalURL());
		Assert.assertEquals(httpServletRequest, themeDisplay.getRequest());
		Assert.assertEquals(
			_group.getGroupId(), themeDisplay.getScopeGroupId());
		Assert.assertEquals(_group.getGroupId(), themeDisplay.getSiteGroupId());
		Assert.assertEquals(TestPropsValues.getUser(), themeDisplay.getUser());
	}

	@Test
	public void testGetHttpServletRequestWithNullGroup() throws Exception {
		try {
			PortletContainerTestUtil.getHttpServletRequest(null, _layout);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testGetHttpServletRequestWithNullLayout() throws Exception {
		try {
			PortletContainerTestUtil.getHttpServletRequest(_group, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test(expected = IOException.class)
	public void testRequestFromUnknownURL() throws Exception {
		PortletContainerTestUtil.request("http://www.lifxxasdaeray.com");
	}

	@Test
	public void testRequestFromValidURLWithHeaders() throws Exception {
		Map<String, List<String>> headers = new HashMap<>();

		headers.put("key1", Collections.singletonList("value1"));
		headers.put(
			"Cookie", Collections.singletonList("JSSESSIONID=1234567890"));

		Response response = PortletContainerTestUtil.request(
			"http://www.google.com", headers);

		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(200, response.getCode());
	}

	@Test
	public void testRequestFromValidURLWithoutHeaders() throws Exception {
		Response response = PortletContainerTestUtil.request(
			"http://www.google.com");

		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(200, response.getCode());
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

}