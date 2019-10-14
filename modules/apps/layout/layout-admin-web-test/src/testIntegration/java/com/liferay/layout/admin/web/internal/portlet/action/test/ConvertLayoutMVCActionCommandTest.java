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

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
@Sync
public class ConvertLayoutMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_serviceContext = _getServiceContext(
			_group, TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testConvertWidgetLayoutToContentLayout() throws Exception {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column");

		Layout originalLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		ActionRequest actionRequest = _getMockActionRequest(
			originalLayout.getPlid());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "processAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, new MockActionResponse());

		Layout persistedLayout = _layoutService.getLayoutByUuidAndGroupId(
			originalLayout.getUuid(), originalLayout.getGroupId(),
			originalLayout.isPrivateLayout());

		Assert.assertNotNull(persistedLayout);

		Assert.assertEquals(
			originalLayout.getGroupId(), persistedLayout.getGroupId());
		Assert.assertEquals(
			originalLayout.isPrivateLayout(),
			persistedLayout.isPrivateLayout());
		Assert.assertEquals(
			originalLayout.getLayoutId(), persistedLayout.getLayoutId());
		Assert.assertEquals(
			originalLayout.getClassName(), persistedLayout.getClassName());
		Assert.assertEquals(
			originalLayout.getClassNameId(), persistedLayout.getClassNameId());
		Assert.assertEquals(
			originalLayout.getDescriptionMap(),
			persistedLayout.getDescriptionMap());
		Assert.assertEquals(
			originalLayout.getFriendlyURLMap(),
			persistedLayout.getFriendlyURLMap());
		Assert.assertEquals(
			originalLayout.getKeywordsMap(), persistedLayout.getKeywordsMap());
		Assert.assertEquals(
			originalLayout.getNameMap(), persistedLayout.getNameMap());
		Assert.assertEquals(
			originalLayout.getParentLayoutId(),
			persistedLayout.getParentLayoutId());
		Assert.assertEquals(
			originalLayout.getPlid(), persistedLayout.getPlid());
		Assert.assertEquals(
			originalLayout.getRobotsMap(), persistedLayout.getRobotsMap());
		Assert.assertEquals(
			originalLayout.getTitleMap(), persistedLayout.getTitleMap());
		Assert.assertEquals("content", persistedLayout.getType());
		Assert.assertEquals(
			originalLayout.getTypeSettings(),
			persistedLayout.getTypeSettings());
		Assert.assertEquals(
			originalLayout.getUserId(), persistedLayout.getUserId());
		Assert.assertEquals(
			originalLayout.isSystem(), persistedLayout.isSystem());
	}

	private MockActionRequest _getMockActionRequest(long plid)
		throws PortalException {

		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockActionRequest.addParameter("selPlid", String.valueOf(plid));

		return mockActionRequest;
	}

	private ServiceContext _getServiceContext(Group group, long userId) {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, new MockActionResponse());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, userId);

		serviceContext.setRequest(httpServletRequest);

		return serviceContext;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutService _layoutService;

	@Inject(filter = "mvc.command.name=/layout/convert_layout")
	private MVCActionCommand _mvcActionCommand;

	private ServiceContext _serviceContext;

	private static class MockActionRequest
		extends MockLiferayPortletActionRequest {

		public MockActionRequest() {
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return new MockHttpServletRequest();
		}

	}

	private static class MockActionResponse
		extends MockLiferayPortletActionResponse {

		@Override
		public HttpServletResponse getHttpServletResponse() {
			return new MockHttpServletResponse();
		}

	}

}