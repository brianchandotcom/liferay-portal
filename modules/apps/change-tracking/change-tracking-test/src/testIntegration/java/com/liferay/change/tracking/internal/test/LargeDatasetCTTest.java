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

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class LargeDatasetCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			LargeDatasetCTTest.class.getName(), null);

		_group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_httpServletRequest = _getHttpServletRequest(TestPropsValues.getUser());

		_themeDisplay = _getThemeDisplay(
			_httpServletRequest, TestPropsValues.getUser());

		_dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			if (_SITE_INITIALIZER) {
				SiteInitializer siteInitializer =
					_siteInitializerRegistry.getSiteInitializer(
						"com.liferay.site.initializer.masterclass");

				siteInitializer.initialize(_group.getGroupId());
			}

			for (int i = 0; i < _COUNT_DL_FILE_ENTRY; i++) {
				_dlFileEntry = DLTestUtil.addDLFileEntry(
					_dlFolder.getFolderId());
			}

			for (int i = 0; i < _COUNT_JOURNAL_ARTICLE; i++) {
				_journalArticle = JournalTestUtil.addArticle(
					_group.getGroupId(), RandomTestUtil.randomString(),
					RandomTestUtil.randomString());
			}

			for (int i = 0; i < _COUNT_LAYOUT_CONTENT; i++) {
				_layoutContent = LayoutTestUtil.addTypeContentLayout(_group);

				_addFragmentEntryLink(_layoutContent.getPlid());
			}

			for (int i = 0; i < _COUNT_LAYOUT_PORTLET; i++) {
				_portletLayout = LayoutTestUtil.addTypePortletLayout(_group);
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Ignore
	@Test
	public void testBuildSiteMap() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			StringBundler sb = new StringBundler();

			Layout layout = _layoutLocalService.fetchDefaultLayout(
				_group.getGroupId(), false);

			_themeDisplay.setLayout(layout);

			_httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, _themeDisplay);

			_buildSiteMap(
				1, 10, layout,
				_layoutLocalService.getLayouts(
					layout.getGroupId(), layout.isPrivateLayout(),
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID),
				sb, true, _themeDisplay, true);
		}
	}

	@Test
	public void testDiscardCTEntry() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionLocalService.discardCTEntry(
				_ctCollection.getCtCollectionId(),
				_portal.getClassNameId(Layout.class.getName()),
				_layoutContent.getPrimaryKey(), false);
		}
	}

	@Test
	public void testGetDiscardCTEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionLocalService.getDiscardCTEntries(
				_ctCollection.getCtCollectionId(),
				_portal.getClassNameId(Layout.class.getName()),
				_layoutContent.getPrimaryKey());
		}
	}

	@Ignore
	@Test
	public void testIncludeLayoutContent() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			Layout layout = _layoutLocalService.fetchDefaultLayout(
				_group.getGroupId(), false);

			_themeDisplay.setLayout(layout);

			_httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, _themeDisplay);

			LayoutTypeController layoutTypeController =
				LayoutTypeControllerTracker.getLayoutTypeController(
					layout.getType());

			layoutTypeController.includeLayoutContent(
				_httpServletRequest, new MockHttpServletResponse(), layout);

			StringBundler sb = (StringBundler)_httpServletRequest.getAttribute(
				WebKeys.LAYOUT_CONTENT);

			Assert.assertTrue(
				sb.toString(), Validator.isNotNull(sb.toString()));
		}
	}

	private void _addFragmentEntryLink(long plid) throws Exception {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		Layout draftLayout = layout.fetchDraftLayout();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FragmentCollectionContributor fragmentCollectionContributor =
			_fragmentCollectionContributorRegistry.
				getFragmentCollectionContributor("BASIC_COMPONENT");

		List<FragmentEntry> fragmentEntries =
			fragmentCollectionContributor.getFragmentEntries(
				_themeDisplay.getLocale());

		long defaultSegmentsExperienceId =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				draftLayout.getPlid());

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				0, fragmentEntry.getFragmentEntryId(),
				defaultSegmentsExperienceId, draftLayout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 1, StringPool.BLANK,
				fragmentEntry.getType(), serviceContext);
		}
	}

	private void _buildLayoutView(
			Layout layout, String cssClass, boolean useHtmlTitle,
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		sb.append("<a");

		LayoutType layoutType = layout.getLayoutType();

		if (layoutType.isBrowsable()) {
			sb.append(" href=\"");
			sb.append(PortalUtil.getLayoutURL(layout, themeDisplay));
			sb.append("\" ");
			sb.append(PortalUtil.getLayoutTarget(layout));
		}

		if (Validator.isNotNull(cssClass)) {
			sb.append(" class=\"");
			sb.append(cssClass);
			sb.append("\" ");
		}

		sb.append("> ");

		String layoutName = HtmlUtil.escape(
			layout.getName(themeDisplay.getLocale()));

		if (useHtmlTitle) {
			layoutName = HtmlUtil.escape(
				layout.getHTMLTitle(themeDisplay.getLocale()));
		}

		sb.append(layoutName);
		sb.append("</a>");
	}

	private void _buildSiteMap(
			int curDepth, int displayDepth, Layout layout, List<Layout> layouts,
			StringBundler sb, boolean showHiddenPages,
			ThemeDisplay themeDisplay, boolean useHtmlTitle)
		throws Exception {

		sb.append("<ul>");

		for (Layout curLayout : layouts) {
			if ((showHiddenPages || !curLayout.isHidden()) &&
				LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), curLayout,
					ActionKeys.VIEW)) {

				sb.append("<li>");

				String cssClass = StringPool.BLANK;

				if (curLayout.getPlid() == layout.getPlid()) {
					cssClass = "current";
				}

				_buildLayoutView(
					curLayout, cssClass, useHtmlTitle, themeDisplay, sb);

				if ((displayDepth == 0) || (displayDepth > curDepth)) {
					if (showHiddenPages) {
						_buildSiteMap(
							curDepth + 1, displayDepth, layout,
							curLayout.getChildren(), sb, showHiddenPages,
							themeDisplay, useHtmlTitle);
					}
					else {
						_buildSiteMap(
							curDepth + 1, displayDepth, layout,
							curLayout.getChildren(
								themeDisplay.getPermissionChecker()),
							sb, showHiddenPages, themeDisplay, useHtmlTitle);
					}
				}

				sb.append("</li>");
			}
		}

		sb.append("</ul>");
	}

	private HttpServletRequest _getHttpServletRequest(User user)
		throws Exception {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "http://localhost:8080/");

		UserTestUtil.setUser(user);

		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(httpServletRequest, user));

		return httpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(
			HttpServletRequest httpServletRequest, User user)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLanguageId(_group.getDefaultLanguageId());

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		themeDisplay.setLayoutSet(layoutSet);

		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
		themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setPortalDomain(company.getVirtualHostname());
		themeDisplay.setPortalURL(company.getPortalURL(_group.getGroupId()));
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerPort(8080);
		themeDisplay.setSignedIn(true);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		return themeDisplay;
	}

	private static final int _COUNT_DL_FILE_ENTRY = 1;

	private static final int _COUNT_JOURNAL_ARTICLE = 1;

	private static final int _COUNT_LAYOUT_CONTENT = 1;

	private static final int _COUNT_LAYOUT_PORTLET = 1;

	private static final boolean _SITE_INITIALIZER = false;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	@Inject
	private static LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private DLFileEntry _dlFileEntry;

	@DeleteAfterTestRun
	private DLFolder _dlFolder;

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@DeleteAfterTestRun
	private Group _group;

	private HttpServletRequest _httpServletRequest;

	@DeleteAfterTestRun
	private JournalArticle _journalArticle;

	@DeleteAfterTestRun
	private Layout _layoutContent;

	@Inject
	private LayoutCopyHelper _layoutCopyHelper;

	@Inject(
		filter = "mvc.command.name=/fragment/propagate_group_fragment_entry_changes"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	@DeleteAfterTestRun
	private Layout _portletLayout;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

	private ThemeDisplay _themeDisplay;

}