/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.journal.internal.item.action.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class ViewInPanelJournalArticleContentDashboardItemActionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		DDMStructure ddmStructure = _journalArticle.getDDMStructure();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group.getCreatorUserId(), _group.getGroupId(), 0,
				_portal.getClassNameId(JournalArticle.class.getName()),
				ddmStructure.getStructureId(), RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0, serviceContext);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			_journalArticle.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			_journalArticle.getResourcePrimKey(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT, serviceContext);

		_layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());
	}

	@Test
	public void testGetContentDashboardItemAction() throws Exception {
		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			TestPropsValues.getUser());

		ContentDashboardItemAction contentDashboardItemAction =
			_contentDashboardItemActionProvider.getContentDashboardItemAction(
				_journalArticle, httpServletRequest);

		Assert.assertEquals(
			"View Metrics", contentDashboardItemAction.getLabel(LocaleUtil.US));
		Assert.assertEquals(
			"viewMetrics", contentDashboardItemAction.getName());
		Assert.assertEquals(
			ContentDashboardItemAction.Type.VIEW_IN_PANEL,
			contentDashboardItemAction.getType());

		InfoDisplayObjectProvider<JournalArticle> infoDisplayObjectProvider =
			(InfoDisplayObjectProvider<JournalArticle>)
				httpServletRequest.getAttribute(
					AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

		Assert.assertEquals(
			String.valueOf(infoDisplayObjectProvider.getClassNameId()),
			_http.getParameter(
				contentDashboardItemAction.getURL(),
				"_com_liferay_analytics_reports_web_internal_portlet_" +
					"AnalyticsReportsPortlet_classNameId",
				false));
		Assert.assertEquals(
			String.valueOf(infoDisplayObjectProvider.getClassPK()),
			_http.getParameter(
				contentDashboardItemAction.getURL(),
				"_com_liferay_analytics_reports_web_internal_portlet_" +
					"AnalyticsReportsPortlet_classPK",
				false));

		Assert.assertEquals(
			"%2Fanalytics_reports_panel.jsp",
			_http.getParameter(
				contentDashboardItemAction.getURL(),
				"_com_liferay_analytics_reports_web_internal_portlet_" +
					"AnalyticsReportsPortlet_mvcPath",
				false));
	}

	@Test
	public void testGetContentDashboardItemActionWithUserWithoutEditPermission()
		throws Exception {

		User user = UserTestUtil.addUser();

		try {
			Assert.assertNull(
				_contentDashboardItemActionProvider.
					getContentDashboardItemAction(
						_journalArticle, _getHttpServletRequest(user)));
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testIsShow() throws Exception {
		Assert.assertTrue(
			_contentDashboardItemActionProvider.isShow(
				_journalArticle,
				_getHttpServletRequest(TestPropsValues.getUser())));
	}

	@Test
	public void testIsShowContentDashboardItemActionWithUserWithoutEditPermission()
		throws Exception {

		User user = UserTestUtil.addUser();

		try {
			Assert.assertFalse(
				_contentDashboardItemActionProvider.isShow(
					_journalArticle, _getHttpServletRequest(user)));
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	private HttpServletRequest _getHttpServletRequest(User user)
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER,
			_infoDisplayContributor.getInfoDisplayObjectProvider(
				_journalArticle.getResourcePrimKey()));
		mockHttpServletRequest.setAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY,
			_assetEntryLocalService.getEntry(
				JournalArticle.class.getName(),
				_journalArticle.getResourcePrimKey()));
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			_getThemeDisplay(mockHttpServletRequest, user));

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

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		themeDisplay.setPermissionChecker(permissionChecker);

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

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.analytics.reports.journal.internal.item.action.provider.ViewInPanelJournalArticleContentDashboardItemActionProvider"
	)
	private ContentDashboardItemActionProvider
		_contentDashboardItemActionProvider;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Http _http;

	@Inject(filter = "component.name=*.JournalArticleInfoDisplayContributor")
	private InfoDisplayContributor<JournalArticle> _infoDisplayContributor;

	private JournalArticle _journalArticle;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private UserLocalService _userLocalService;

}