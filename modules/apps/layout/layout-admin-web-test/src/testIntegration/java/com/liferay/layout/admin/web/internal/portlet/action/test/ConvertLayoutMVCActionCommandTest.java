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
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RootLayoutStructureItem;
import com.liferay.layout.util.structure.RowLayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.List;

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

		_validateLayoutConversion(originalLayout);
	}

	@Test
	public void testConvertWidgetLayoutToContentLayoutWithExistingStructure()
		throws Exception {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column");

		Layout originalLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class.getName()),
			originalLayout.getPlid(), StringPool.BLANK, _serviceContext);

		ActionRequest actionRequest = _getMockActionRequest(
			originalLayout.getPlid());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "processAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, new MockActionResponse());

		_validateLayoutConversion(originalLayout);
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

	private void _validateLayoutConversion(Layout originalLayout)
		throws Exception {

		Layout persistedDraftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class.getName()),
			originalLayout.getPlid());

		Assert.assertNotNull(persistedDraftLayout);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					originalLayout.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					originalLayout.getPlid());

		Assert.assertNotNull(layoutPageTemplateStructure);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		Assert.assertNotNull(layoutStructure.getMainItemId());

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		Assert.assertNotNull(rootLayoutStructureItem);
		Assert.assertTrue(
			rootLayoutStructureItem instanceof RootLayoutStructureItem);
		Assert.assertEquals(
			rootLayoutStructureItem.getItemType(),
			LayoutDataItemTypeConstants.TYPE_ROOT);

		List<String> rootItemIds = rootLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(rootItemIds.toString(), 1, rootItemIds.size());

		LayoutStructureItem containerLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(rootItemIds.get(0));

		Assert.assertNotNull(containerLayoutStructureItem);
		Assert.assertTrue(
			containerLayoutStructureItem instanceof
				ContainerLayoutStructureItem);
		Assert.assertEquals(
			containerLayoutStructureItem.getItemType(),
			LayoutDataItemTypeConstants.TYPE_CONTAINER);

		List<String> containerItemIds =
			containerLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			containerItemIds.toString(), 1, containerItemIds.size());

		LayoutStructureItem rowLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(containerItemIds.get(0));

		Assert.assertNotNull(rowLayoutStructureItem);
		Assert.assertTrue(
			rowLayoutStructureItem instanceof RowLayoutStructureItem);
		Assert.assertEquals(
			rowLayoutStructureItem.getItemType(),
			LayoutDataItemTypeConstants.TYPE_ROW);

		List<String> rowItemIds = rowLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(rowItemIds.toString(), 1, rowItemIds.size());

		LayoutStructureItem columnLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(rowItemIds.get(0));

		Assert.assertNotNull(columnLayoutStructureItem);
		Assert.assertTrue(
			columnLayoutStructureItem instanceof ColumnLayoutStructureItem);
		Assert.assertEquals(
			columnLayoutStructureItem.getItemType(),
			LayoutDataItemTypeConstants.TYPE_COLUMN);

		List<String> columnItemIds =
			columnLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(columnItemIds.toString(), 0, columnItemIds.size());

		Layout persistedPublishedLayout = _layoutLocalService.getLayout(
			originalLayout.getPlid());

		Assert.assertNotNull(persistedPublishedLayout);

		Assert.assertEquals(
			originalLayout.getPlid(), persistedPublishedLayout.getPlid());
		Assert.assertEquals(
			originalLayout.getGroupId(), persistedPublishedLayout.getGroupId());
		Assert.assertEquals(
			originalLayout.getUserId(), persistedPublishedLayout.getUserId());
		Assert.assertEquals(
			originalLayout.getParentPlid(),
			persistedPublishedLayout.getParentPlid());
		Assert.assertEquals(
			originalLayout.isPrivateLayout(),
			persistedPublishedLayout.isPrivateLayout());
		Assert.assertEquals(
			originalLayout.getLayoutId(),
			persistedPublishedLayout.getLayoutId());
		Assert.assertEquals(
			originalLayout.getParentLayoutId(),
			persistedPublishedLayout.getParentLayoutId());
		Assert.assertEquals(
			originalLayout.getClassName(),
			persistedPublishedLayout.getClassName());
		Assert.assertEquals(
			originalLayout.getClassNameId(),
			persistedPublishedLayout.getClassNameId());
		Assert.assertEquals(
			originalLayout.getClassPK(), persistedPublishedLayout.getClassPK());
		Assert.assertEquals(
			originalLayout.getNameMap(), persistedPublishedLayout.getNameMap());
		Assert.assertEquals(
			originalLayout.getTitleMap(),
			persistedPublishedLayout.getTitleMap());
		Assert.assertEquals(
			originalLayout.getDescriptionMap(),
			persistedPublishedLayout.getDescriptionMap());
		Assert.assertEquals(
			originalLayout.getKeywordsMap(),
			persistedPublishedLayout.getKeywordsMap());
		Assert.assertEquals(
			originalLayout.getRobotsMap(),
			persistedPublishedLayout.getRobotsMap());
		Assert.assertEquals("content", persistedPublishedLayout.getType());
		Assert.assertEquals(
			originalLayout.getTypeSettings(),
			persistedPublishedLayout.getTypeSettings());
		Assert.assertEquals(
			originalLayout.isSystem(), persistedPublishedLayout.isSystem());
		Assert.assertEquals(
			originalLayout.getFriendlyURLMap(),
			persistedPublishedLayout.getFriendlyURLMap());
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(filter = "mvc.command.name=/layout/convert_layout")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

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