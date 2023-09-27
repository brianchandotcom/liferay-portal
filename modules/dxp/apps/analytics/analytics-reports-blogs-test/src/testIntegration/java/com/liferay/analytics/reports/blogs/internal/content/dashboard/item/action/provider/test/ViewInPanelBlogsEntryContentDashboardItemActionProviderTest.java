/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.blogs.internal.content.dashboard.item.action.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.LinkedAssetEntryIdsUtil;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class ViewInPanelBlogsEntryContentDashboardItemActionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_blogsEntry = _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group.getCreatorUserId(), _group.getGroupId(), 0,
				_portal.getClassNameId(BlogsEntry.class.getName()), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0, _serviceContext);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			_blogsEntry.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(BlogsEntry.class.getName()),
			_blogsEntry.getEntryId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT, _serviceContext);

		_layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetContentDashboardItemAction() throws Exception {
		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			TestPropsValues.getUser());

		ContentDashboardItemAction contentDashboardItemAction =
			_contentDashboardItemActionProvider.getContentDashboardItemAction(
				_blogsEntry, httpServletRequest);

		Assert.assertEquals(
			"View Metrics", contentDashboardItemAction.getLabel(LocaleUtil.US));
		Assert.assertEquals(
			"viewMetrics", contentDashboardItemAction.getName());
		Assert.assertEquals(
			ContentDashboardItemAction.Type.VIEW_IN_PANEL,
			contentDashboardItemAction.getType());

		LayoutDisplayPageObjectProvider<BlogsEntry>
			layoutDisplayPageObjectProvider =
				(LayoutDisplayPageObjectProvider<BlogsEntry>)
					httpServletRequest.getAttribute(
						LayoutDisplayPageWebKeys.
							LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		Assert.assertEquals(
			layoutDisplayPageObjectProvider.getClassName(),
			HttpComponentsUtil.getParameter(
				contentDashboardItemAction.getURL(),
				"_com_liferay_analytics_reports_web_internal_portlet_" +
					"AnalyticsReportsPortlet_className",
				false));
		Assert.assertEquals(
			String.valueOf(layoutDisplayPageObjectProvider.getClassPK()),
			HttpComponentsUtil.getParameter(
				contentDashboardItemAction.getURL(),
				"_com_liferay_analytics_reports_web_internal_portlet_" +
					"AnalyticsReportsPortlet_classPK",
				false));

		Assert.assertEquals(
			"%2Fanalytics_reports_panel.jsp",
			HttpComponentsUtil.getParameter(
				contentDashboardItemAction.getURL(),
				"_com_liferay_analytics_reports_web_internal_portlet_" +
					"AnalyticsReportsPortlet_mvcPath",
				false));
	}

	@Test
	public void testGetContentDashboardItemActionWithUserWithoutEditPermission()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		try {
			Assert.assertNull(
				_contentDashboardItemActionProvider.
					getContentDashboardItemAction(
						_blogsEntry, _getHttpServletRequest(user)));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testIsShow() throws Exception {
		Assert.assertTrue(
			_contentDashboardItemActionProvider.isShow(
				_blogsEntry,
				_getHttpServletRequest(TestPropsValues.getUser())));
	}

	@Test
	public void testIsShowContentDashboardItemActionWithUserWithoutEditPermission()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		try {
			Assert.assertFalse(
				_contentDashboardItemActionProvider.isShow(
					_blogsEntry, _getHttpServletRequest(user)));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			_userLocalService.deleteUser(user);
		}
	}

	private HttpServletRequest _getHttpServletRequest(User user)
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
			_layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					BlogsEntry.class.getName(), _blogsEntry.getEntryId())));

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			BlogsEntry.class.getName(), _blogsEntry.getEntryId());

		mockHttpServletRequest.setAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

		LinkedAssetEntryIdsUtil.addLinkedAssetEntryId(
			mockHttpServletRequest, assetEntry.getEntryId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			_getThemeDisplay(mockHttpServletRequest, user));

		_serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(
			HttpServletRequest httpServletRequest, User user)
		throws PortalException {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		return themeDisplay;
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	private BlogsEntry _blogsEntry;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.analytics.reports.blogs.internal.content.dashboard.item.action.provider.ViewInPanelBlogsEntryContentDashboardItemActionProvider"
	)
	private ContentDashboardItemActionProvider
		_contentDashboardItemActionProvider;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(
		filter = "component.name=com.liferay.blogs.web.internal.layout.display.page.BlogsLayoutDisplayPageProvider"
	)
	private LayoutDisplayPageProvider<BlogsEntry> _layoutDisplayPageProvider;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Inject
	private UserLocalService _userLocalService;

}