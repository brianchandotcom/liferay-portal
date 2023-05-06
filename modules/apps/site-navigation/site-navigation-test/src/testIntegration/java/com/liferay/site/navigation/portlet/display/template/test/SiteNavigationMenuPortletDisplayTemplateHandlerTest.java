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

package com.liferay.site.navigation.portlet.display.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuMode;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuTag;
import com.liferay.site.navigation.util.SiteNavigationMenuTestUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuPortletDisplayTemplateHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetExpandoAttributes() throws Exception {
		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.addDefaultTable(
				PortalUtil.getDefaultCompanyId(),
				SiteNavigationMenuItem.class.getName());

		String expandoAttributeName = RandomTestUtil.randomString();

		ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), expandoAttributeName,
			ExpandoColumnConstants.STRING, StringPool.BLANK);

		try {
			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			SiteNavigationMenu siteNavigationMenu =
				SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			serviceContext.setExpandoBridgeAttributes(
				HashMapBuilder.<String, Serializable>put(
					expandoAttributeName, "ExpandoAttributeValue"
				).build());

			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				_group.getGroupId(),
				siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"groupId", String.valueOf(_group.getGroupId())
				).put(
					"layoutUuid", layout.getUuid()
				).put(
					"privateLayout", String.valueOf(layout.isPrivateLayout())
				).put(
					"title", layout.getName()
				).buildString(),
				serviceContext);

			String script = StringBundler.concat(
				"<#if entries?has_content><#list entries as navigationEntry>",
				"<#assign expandoAttributes = ",
				"navigationEntry.getExpandoAttributes() />",
				"${expandoAttributes[\"", expandoAttributeName, "\"]}</#list>",
				"</#if>");

			DDMTemplate ddmTemplate = _ddmTemplateLocalService.addTemplate(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_portal.getClassNameId(NavItem.class.getName()), 0,
				_portal.getClassNameId(_CLASS_NAME_PORTLET_DISPLAY_TEMPLATE),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
				TemplateConstants.LANG_TYPE_FTL, script, false, false, null,
				null, serviceContext);

			NavigationMenuTag navigationMenuTag = new NavigationMenuTag();

			navigationMenuTag.setDdmTemplateGroupId(ddmTemplate.getGroupId());
			navigationMenuTag.setDdmTemplateKey(ddmTemplate.getTemplateKey());
			navigationMenuTag.setNavigationMenuMode(
				NavigationMenuMode.PUBLIC_PAGES);
			navigationMenuTag.setRootItemType("absolute");
			navigationMenuTag.setSiteNavigationMenuId(
				siteNavigationMenu.getSiteNavigationMenuId());
			navigationMenuTag.setRootItemLevel(0);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			navigationMenuTag.doTag(
				_getMockHttpServletRequest(), mockHttpServletResponse);

			Assert.assertTrue(
				StringUtil.contains(
					"ExpandoAttributeValue",
					mockHttpServletResponse.getContentAsString()));
		}
		finally {
			ExpandoTableLocalServiceUtil.deleteExpandoTable(expandoTable);
		}
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));

		Locale locale = LocaleUtil.getSiteDefault();

		themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));

		themeDisplay.setLayout(LayoutTestUtil.addTypeContentLayout(_group));

		themeDisplay.setLocale(locale);

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLayoutSet(layoutSet);
		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private static final String _CLASS_NAME_PORTLET_DISPLAY_TEMPLATE =
		"com.liferay.portlet.display.template.PortletDisplayTemplate";

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

}