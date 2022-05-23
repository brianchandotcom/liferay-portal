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

package com.liferay.layout.internal.asset.model.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderRequest;

import java.io.Serializable;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Hong Vo
 */
@RunWith(Arquillian.class)
public class LayoutAssetRendererTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		_initThemeDisplay();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			_group.getGroupId(), Layout.class.getName(), 0, 0,
			"Single Approver@1");
	}

	@Test
	public void testGetURLViewInContext() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		AssetRendererFactory<Layout> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				Layout.class);

		AssetRenderer<Layout> assetRenderer =
			assetRendererFactory.getAssetRenderer(layout.getPlid(), 0);

		Assert.assertEquals(WorkflowConstants.STATUS_DRAFT, layout.getStatus());

		Assert.assertEquals(
			_portal.getLayoutFriendlyURL(layout, _themeDisplay),
			assetRenderer.getURLViewInContext(
				_getLiferayPortletRequest(_themeDisplay), null, null));

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				Layout.class.getName());

		Assert.assertNotNull(
			workflowHandler.getWorkflowDefinitionLink(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				layout.getPlid()));

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			TestPropsValues.getCompanyId(), _group.getGroupId(),
			TestPropsValues.getUserId(), Layout.class.getName(),
			layout.getPlid(), layout, _serviceContext, Collections.emptyMap());

		layout = _layoutLocalService.getLayout(layout.getPlid());

		assetRenderer = assetRendererFactory.getAssetRenderer(
			layout.getPlid(), 0);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, layout.getStatus());

		Assert.assertEquals(
			HttpComponentsUtil.addParameter(
				_portal.getLayoutFriendlyURL(
					layout.fetchDraftLayout(), _themeDisplay),
				"p_l_back_url", _themeDisplay.getURLCurrent()),
			assetRenderer.getURLViewInContext(
				_getLiferayPortletRequest(_themeDisplay), null, null));

		workflowHandler.updateStatus(
			WorkflowConstants.STATUS_DENIED,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
				String.valueOf(layout.getPlid())
			).put(
				WorkflowConstants.CONTEXT_USER_ID,
				String.valueOf(TestPropsValues.getUserId())
			).put(
				"serviceContext", _serviceContext
			).build());

		layout = _layoutLocalService.getLayout(layout.getPlid());

		assetRenderer = assetRendererFactory.getAssetRenderer(
			layout.getPlid(), 0);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DENIED, layout.getStatus());

		Assert.assertEquals(
			HttpComponentsUtil.addParameter(
				_portal.getLayoutFriendlyURL(
					layout.fetchDraftLayout(), _themeDisplay),
				"p_l_back_url", _themeDisplay.getURLCurrent()),
			assetRenderer.getURLViewInContext(
				_getLiferayPortletRequest(_themeDisplay), null, null));

		workflowHandler.updateStatus(
			WorkflowConstants.STATUS_APPROVED,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
				String.valueOf(layout.getPlid())
			).put(
				WorkflowConstants.CONTEXT_USER_ID,
				String.valueOf(TestPropsValues.getUserId())
			).put(
				"serviceContext", _serviceContext
			).build());

		layout = _layoutLocalService.getLayout(layout.getPlid());

		assetRenderer = assetRendererFactory.getAssetRenderer(
			layout.getPlid(), 0);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, layout.getStatus());

		Assert.assertEquals(
			_portal.getLayoutFriendlyURL(layout, _themeDisplay),
			assetRenderer.getURLViewInContext(
				_getLiferayPortletRequest(_themeDisplay), null, null));
	}

	private LiferayPortletRequest _getLiferayPortletRequest(
		ThemeDisplay themeDisplay) {

		MockRenderRequest renderRequest = new MockLiferayPortletRenderRequest();

		renderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return _portal.getLiferayPortletRequest(renderRequest);
	}

	private void _initThemeDisplay() throws Exception {
		_themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		_themeDisplay.setCompany(company);

		_themeDisplay.setLanguageId(_group.getDefaultLanguageId());
		_themeDisplay.setLayoutSet(_layoutSet);
		_themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
		_themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		_themeDisplay.setPortalDomain(company.getVirtualHostname());
		_themeDisplay.setPortalURL(company.getPortalURL(_group.getGroupId()));
		_themeDisplay.setRequest(new MockHttpServletRequest());
		_themeDisplay.setScopeGroupId(_group.getGroupId());
		_themeDisplay.setServerPort(8080);
		_themeDisplay.setSignedIn(true);
		_themeDisplay.setSiteGroupId(_group.getGroupId());
		_themeDisplay.setUser(TestPropsValues.getUser());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private LayoutSet _layoutSet;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;
	private ThemeDisplay _themeDisplay;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}