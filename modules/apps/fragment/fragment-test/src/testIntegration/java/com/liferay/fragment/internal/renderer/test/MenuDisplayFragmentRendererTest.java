/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.renderer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.Definition;
import com.liferay.portal.struts.TilesUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

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
 * @author Javier Moral
 */
@RunWith(Arquillian.class)
public class MenuDisplayFragmentRendererTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = _groupLocalService.getGroup(TestPropsValues.getGroupId());

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			TestPropsValues.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	@TestInfo("LPD-66793")
	public void testRenderWithERCReference() throws Exception {
		_testRenderWithERCReference(_group, StringPool.BLANK);
	}

	@Test
	@TestInfo("LPD-66793")
	public void testRenderWithERCReferenceAndSiteNavigationMenuInCompanyGroup()
		throws Exception {

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		_testRenderWithERCReference(
			companyGroup, companyGroup.getExternalReferenceCode());
	}

	@Test
	@TestInfo("LPD-66793")
	public void testRenderWithIdReference() throws Exception {
		_testRenderWithIdReference(_group);
	}

	@Test
	@TestInfo("LPD-66793")
	public void testRenderWithIdReferenceAndSiteNavigationMenuInCompanyGroup()
		throws Exception {

		_testRenderWithIdReference(
			_groupLocalService.getCompanyGroup(TestPropsValues.getCompanyId()));
	}

	private FragmentEntryLink _addFragmentEntryLink(
			String editableValues, long plid)
		throws Exception {

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				plid),
			plid, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, editableValues, StringPool.BLANK, 0,
			"com.liferay.fragment.renderer.menu.display.internal." +
				"MenuDisplayFragmentRenderer",
			FragmentConstants.TYPE_COMPONENT, _serviceContext);
	}

	private SiteNavigationMenu _addSiteNavigationMenu(long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return _siteNavigationMenuLocalService.addSiteNavigationMenu(
			null, TestPropsValues.getUserId(), groupId,
			RandomTestUtil.randomString(), SiteNavigationConstants.TYPE_DEFAULT,
			true, serviceContext);
	}

	private SiteNavigationMenuItem _addURLSiteNavigationMenuItem(
			long groupId, String defaultLanguageId,
			long parentSiteNavigationMenuItemId,
			SiteNavigationMenu siteNavigationMenu)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return _siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			null, TestPropsValues.getUserId(), groupId,
			siteNavigationMenu.getSiteNavigationMenuId(),
			parentSiteNavigationMenuItemId,
			SiteNavigationMenuItemTypeConstants.URL,
			UnicodePropertiesBuilder.create(
				true
			).put(
				"url", url
			).buildString(),
			serviceContext);
	}

	private HttpServletRequest _getMockHttpServletRequest() throws Exception {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			TilesUtil.DEFINITION,
			new Definition(StringPool.BLANK, new HashMap<>()));
		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(httpServletRequest));

		_serviceContext.setRequest(httpServletRequest);

		return httpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setLocale(LocaleUtil.getSiteDefault());

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private String _render(FragmentEntryLink fragmentEntryLink)
		throws Exception {

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.VIEW);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_fragmentRenderer.render(
			defaultFragmentRendererContext, _getMockHttpServletRequest(),
			mockHttpServletResponse);

		return mockHttpServletResponse.getContentAsString();
	}

	private void _testRenderWithERCReference(
			Group group, String siteNavigationMenuScopeExternalReferenceCode)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu = _addSiteNavigationMenu(
			group.getGroupId());

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			_addURLSiteNavigationMenuItem(
				group.getGroupId(), _group.getDefaultLanguageId(), 0,
				siteNavigationMenu);

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			_addURLSiteNavigationMenuItem(
				group.getGroupId(), _group.getDefaultLanguageId(),
				siteNavigationMenuItem1.getSiteNavigationMenuItemId(),
				siteNavigationMenu);

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					"source",
					JSONUtil.put(
						"parentSiteNavigationMenuItemExternalReferenceCode",
						siteNavigationMenuItem1.getExternalReferenceCode()
					).put(
						"siteNavigationMenuExternalReferenceCode",
						siteNavigationMenu.getExternalReferenceCode()
					).put(
						"siteNavigationMenuScopeExternalReferenceCode",
						siteNavigationMenuScopeExternalReferenceCode
					))
			).toString(),
			_layout.getPlid());

		String content = _render(fragmentEntryLink);

		UnicodeProperties typeSettingsUnicodeProperties1 =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem1.getTypeSettings()
			).build();

		UnicodeProperties typeSettingsUnicodeProperties2 =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem2.getTypeSettings()
			).build();

		Assert.assertFalse(
			content.contains(typeSettingsUnicodeProperties1.get("url")));
		Assert.assertTrue(
			content.contains(typeSettingsUnicodeProperties2.get("url")));
	}

	private void _testRenderWithIdReference(Group group) throws Exception {
		SiteNavigationMenu siteNavigationMenu = _addSiteNavigationMenu(
			group.getGroupId());

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			_addURLSiteNavigationMenuItem(
				group.getGroupId(), _group.getDefaultLanguageId(), 0,
				siteNavigationMenu);

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			_addURLSiteNavigationMenuItem(
				group.getGroupId(), _group.getDefaultLanguageId(),
				siteNavigationMenuItem1.getSiteNavigationMenuItemId(),
				siteNavigationMenu);

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					"source",
					JSONUtil.put(
						"parentSiteNavigationMenuItemId",
						siteNavigationMenuItem1.getSiteNavigationMenuItemId()
					).put(
						"siteNavigationMenuId",
						String.valueOf(
							siteNavigationMenu.getSiteNavigationMenuId())
					))
			).toString(),
			_layout.getPlid());

		String content = _render(fragmentEntryLink);

		UnicodeProperties typeSettingsUnicodeProperties1 =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem1.getTypeSettings()
			).build();

		UnicodeProperties typeSettingsUnicodeProperties2 =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem2.getTypeSettings()
			).build();

		Assert.assertFalse(
			content.contains(typeSettingsUnicodeProperties1.get("url")));
		Assert.assertTrue(
			content.contains(typeSettingsUnicodeProperties2.get("url")));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject(
		filter = "component.name=com.liferay.fragment.renderer.menu.display.internal.MenuDisplayFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}