/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.item.layout.internal.type.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class LayoutSiteNavigationMenuItemTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				"Primary Menu", SiteNavigationConstants.TYPE_PRIMARY, true,
				_serviceContext);
	}

	@Test
	public void testAddToAutoMenuFalseToMenu() throws PortalException {
		_layoutService.addLayout(
			null, _group.getGroupId(), false, 0,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "welcome"
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_PORTLET,
			UnicodePropertiesBuilder.put(
				"addToAutoMenus", Boolean.FALSE.toString()
			).buildString(),
			false, new HashMap<>(), _serviceContext);

		Assert.assertEquals(
			0,
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItemsCount(
				_siteNavigationMenu.getSiteNavigationMenuId()));
	}

	@Test
	public void testAddToAutoMenuTrueToMenu() throws PortalException {
		SiteNavigationMenu autoSiteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				"Auto Menu", SiteNavigationConstants.TYPE_DEFAULT, true,
				_serviceContext);

		_layoutService.addLayout(
			null, _group.getGroupId(), false, 0,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "welcome"
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_PORTLET,
			UnicodePropertiesBuilder.put(
				"siteNavigationMenuId",
				StringUtil.merge(
					new long[] {
						autoSiteNavigationMenu.getSiteNavigationMenuId(),
						_siteNavigationMenu.getSiteNavigationMenuId()
					})
			).buildString(),
			false, new HashMap<>(), _serviceContext);

		Assert.assertEquals(
			1,
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItemsCount(
				autoSiteNavigationMenu.getSiteNavigationMenuId()));

		Assert.assertEquals(
			1,
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItemsCount(
				_siteNavigationMenu.getSiteNavigationMenuId()));
	}

	@Test
	public void testAddToAutoMenuTrueToPrimaryMenu() throws PortalException {
		_layoutService.addLayout(
			null, _group.getGroupId(), false, 0,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "welcome"
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_PORTLET,
			UnicodePropertiesBuilder.put(
				"siteNavigationMenuId",
				StringUtil.merge(
					new long[] {_siteNavigationMenu.getSiteNavigationMenuId()})
			).buildString(),
			false, new HashMap<>(), _serviceContext);

		Assert.assertEquals(
			1,
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItemsCount(
				_siteNavigationMenu.getSiteNavigationMenuId()));
	}

	@Test
	public void testGetLayout() throws Exception {
		_assertGetLayout(null);
		_assertGetLayout(LayoutTestUtil.addTypePortletLayout(_group));
	}

	private void _assertGetLayout(Layout layout) throws Exception {
		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				SiteNavigationMenuItemTypeConstants.LAYOUT);

		String layoutUuid = RandomTestUtil.randomString();

		if (layout != null) {
			layoutUuid = layout.getUuid();
		}

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"groupId", String.valueOf(_group.getGroupId())
				).put(
					"layoutUuid", layoutUuid
				).put(
					"privateLayout", false
				).put(
					"title", RandomTestUtil.randomString()
				).buildString(),
				_serviceContext);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.site.navigation.menu.item.layout.internal.type." +
					"LayoutSiteNavigationMenuItemType",
				LoggerTestUtil.WARN)) {

			Assert.assertEquals(
				layout,
				siteNavigationMenuItemType.getLayout(siteNavigationMenuItem));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			if (layout != null) {
				Assert.assertEquals(
					logEntries.toString(), 0, logEntries.size());

				return;
			}

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"No layout found for site navigation menu item ID ",
					siteNavigationMenuItem.getSiteNavigationMenuItemId(),
					" with layout UUID ", layoutUuid,
					" and private layout false"),
				logEntry.getMessage());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutService _layoutService;

	private ServiceContext _serviceContext;
	private SiteNavigationMenu _siteNavigationMenu;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Inject
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}