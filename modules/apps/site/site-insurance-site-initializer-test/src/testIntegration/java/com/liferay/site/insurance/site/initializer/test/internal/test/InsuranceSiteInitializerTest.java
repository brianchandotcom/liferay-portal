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

package com.liferay.site.insurance.site.initializer.test.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class InsuranceSiteInitializerTest {

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

		_user = UserTestUtil.addGroupAdminUser(_group);

		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());

		ServiceContextThreadLocal.pushServiceContext(
			_getServiceContext(_group, _user));
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testCreateInsuranceSite() throws InitializationException {
		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"site-insurance-site-initializer");

		siteInitializer.initialize(_group.getGroupId());

		Map<Integer, Map<String, LayoutPageTemplateEntry>>
			layoutPageTemplateEntriesMap = _getLayoutPageTemplateEntriesMap();

		Assert.assertTrue(MapUtil.isNotEmpty(layoutPageTemplateEntriesMap));

		Map<String, LayoutPageTemplateEntry> masterLayoutsMap =
			layoutPageTemplateEntriesMap.get(
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT);

		Assert.assertTrue(MapUtil.isNotEmpty(masterLayoutsMap));
		Assert.assertNotNull(masterLayoutsMap.get("Customer Portal"));
		Assert.assertNotNull(masterLayoutsMap.get("Public"));

		Map<String, LayoutPageTemplateEntry> displayPagesMap =
			layoutPageTemplateEntriesMap.get(
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		Assert.assertTrue(MapUtil.isNotEmpty(displayPagesMap));
		Assert.assertNotNull(displayPagesMap.get("Claim"));
		Assert.assertNotNull(displayPagesMap.get("Policy"));

		String[] privateLayouts = _getLayoutNames(_group.getGroupId(), true);

		Assert.assertTrue(
			ArrayUtil.containsAll(privateLayouts, _PRIVATE_LAYOUTS));

		String[] publicLayouts = _getLayoutNames(_group.getGroupId(), false);

		Assert.assertTrue(
			ArrayUtil.containsAll(publicLayouts, _PUBLIC_LAYOUTS));

		Assert.assertNotNull(
			_styleBookEntryLocalService.fetchStyleBookEntry(
				_group.getGroupId(), "raylife"));
	}

	private String[] _getLayoutNames(long groupId, boolean privateLayout) {
		List<Layout> layouts = _layoutLocalService.getLayouts(
			groupId, privateLayout);

		Stream<Layout> stream = layouts.stream();

		return stream.map(
			layout -> layout.getName(LocaleUtil.getSiteDefault())
		).collect(
			Collectors.toList()
		).toArray(
			new String[0]
		);
	}

	private Map<Integer, Map<String, LayoutPageTemplateEntry>>
		_getLayoutPageTemplateEntriesMap() {

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntries(
				_group.getGroupId());

		Stream<LayoutPageTemplateEntry> stream =
			layoutPageTemplateEntries.stream();

		return stream.collect(
			Collectors.groupingBy(
				LayoutPageTemplateEntry::getType,
				Collectors.toMap(
					layoutPageTemplateEntry ->
						layoutPageTemplateEntry.getName(),
					Function.identity())));
	}

	private ServiceContext _getServiceContext(Group group, User user)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, user.getUserId());

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletActionResponse());

		httpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);
		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		serviceContext.setRequest(httpServletRequest);

		return serviceContext;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);

		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private static final String[] _PRIVATE_LAYOUTS = {
		"Claims", "Dashboard", "Documents", "Policies"
	};

	private static final String[] _PUBLIC_LAYOUTS = {
		"Home", "Contact", "News", "Product", "Sign In", "Search"
	};

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@DeleteAfterTestRun
	private User _user;

}