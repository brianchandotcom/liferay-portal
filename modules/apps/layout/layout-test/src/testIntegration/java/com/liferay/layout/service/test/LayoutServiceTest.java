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

package com.liferay.layout.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sites.kernel.util.Sites;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class LayoutServiceTest {

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
	public void testFetchLayout() throws Exception {
		Layout newLayout = LayoutTestUtil.addTypePortletLayout(_group);

		Layout layout = _layoutService.fetchLayout(
			0L, newLayout.isPrivateLayout(), newLayout.getLayoutId());

		Assert.assertNull(layout);

		layout = _layoutService.fetchLayout(
			_group.getGroupId(), !newLayout.isPrivateLayout(),
			newLayout.getLayoutId());

		Assert.assertNull(layout);

		layout = _layoutService.fetchLayout(
			_group.getGroupId(), newLayout.isPrivateLayout(), 0L);

		Assert.assertNull(layout);

		layout = _layoutService.fetchLayout(
			_group.getGroupId(), newLayout.isPrivateLayout(),
			newLayout.getLayoutId());

		Assert.assertNotNull(layout);

		Assert.assertEquals(layout.getPlid(), newLayout.getPlid());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testFetchLayoutWithoutPermissions() throws Exception {
		Layout newLayout = LayoutTestUtil.addTypePortletLayout(_group, true);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			User user = UserTestUtil.addUser();

			_roleLocalService.deleteUserRoles(
				user.getUserId(), user.getRoleIds());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			_layoutService.fetchLayout(
				_group.getGroupId(), newLayout.isPrivateLayout(),
				newLayout.getLayoutId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testUpdateFriendlyURLMap() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		long userId = layout.getUserId();

		layout.setUserId(-1);

		LayoutLocalServiceUtil.updateLayout(layout);

		Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

		friendlyURLMap.put(
			LocaleUtil.GERMANY,
			StringPool.SLASH + RandomTestUtil.randomString());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		LayoutLocalServiceUtil.updateLayout(
			_group.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getParentLayoutId(), layout.getNameMap(),
			layout.getTitleMap(), layout.getDescriptionMap(),
			layout.getKeywordsMap(), layout.getRobotsMap(), layout.getType(),
			layout.isHidden(), friendlyURLMap, layout.getIconImage(), null, 0,
			0, serviceContext);
	}

	@Test
	public void testUpdateLookAndFeel() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		layout = LayoutLocalServiceUtil.updateLookAndFeel(
			_group.getGroupId(), false, layout.getLayoutId(),
			"test_WAR_testtheme", "01", StringPool.BLANK);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			layout.getUserId(), "1_column", false);

		LayoutLocalServiceUtil.updateLayout(layout);
	}

	@Test
	public void testUpdateTypeSettings() throws Exception {
		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			RandomTestUtil.randomString());

		Layout layout = layoutPrototype.getLayout();

		LayoutLocalServiceUtil.updateLayout(layout);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(layout.getUserId());

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getParentLayoutId(), layout.getNameMap(),
			layout.getTitleMap(), layout.getDescriptionMap(),
			layout.getKeywordsMap(), layout.getRobotsMap(), layout.getType(),
			layout.isHidden(), layout.getFriendlyURLMap(),
			layout.getIconImage(), null, 0, 0, serviceContext);

		Layout updatedLayout = LayoutLocalServiceUtil.getLayout(
			layout.getPlid());

		UnicodeProperties typeSettingsUnicodeProperties =
			updatedLayout.getTypeSettingsProperties();

		Assert.assertFalse(
			"Updating layout prototype should not add property \"" +
				Sites.LAYOUT_UPDATEABLE + "\"",
			typeSettingsUnicodeProperties.containsKey(Sites.LAYOUT_UPDATEABLE));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutService _layoutService;

	@Inject
	private RoleLocalService _roleLocalService;

}