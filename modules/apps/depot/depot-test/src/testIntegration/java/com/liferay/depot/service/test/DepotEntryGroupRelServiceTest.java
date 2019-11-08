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

package com.liferay.depot.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryGroupRelService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DepotEntryGroupRelServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetDepotEntryGroupRelsWithoutPermissions()
		throws Exception {

		Group group = _groupLocalService.addGroup(
			TestPropsValues.getUserId(), 0, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			GroupConstants.TYPE_SITE_RESTRICTED, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			FriendlyURLNormalizerUtil.normalize(RandomTestUtil.randomString()),
			true, true, ServiceContextTestUtil.getServiceContext());

		DepotEntry depotEntry = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), group.getGroupId());

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				_permissionCheckerFactory.create(user));

			_depotEntryGroupRelService.getDepotEntryGroupRels(
				group.getGroupId(), 0, 20);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			String message = mhp.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + user.getUserId() +
						" must have VIEW permission for"));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
			_groupLocalService.deleteGroup(group);
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDepotEntryGroupRelsWithPermissions() throws Exception {
		Group group = _groupLocalService.addGroup(
			TestPropsValues.getUserId(), 0, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			FriendlyURLNormalizerUtil.normalize(RandomTestUtil.randomString()),
			true, true, ServiceContextTestUtil.getServiceContext());

		DepotEntry depotEntry = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), group.getGroupId());

		try {
			List<DepotEntryGroupRel> depotEntryGroupRels =
				_depotEntryGroupRelService.getDepotEntryGroupRels(
					group.getGroupId(), 0, 20);

			Assert.assertEquals(
				depotEntryGroupRels.toString(), 1, depotEntryGroupRels.size());

			DepotEntryGroupRel depotEntryGroupRel = depotEntryGroupRels.get(0);

			Assert.assertEquals(
				depotEntry.getDepotEntryId(),
				depotEntryGroupRel.getDepotEntryId());
			Assert.assertEquals(
				group.getGroupId(), depotEntryGroupRel.getToGroupId());
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	private DepotEntry _addDepotEntry() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());

		_depotEntries.add(depotEntry);

		return depotEntry;
	}

	@DeleteAfterTestRun
	private final List<DepotEntry> _depotEntries = new ArrayList<>();

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryGroupRelService _depotEntryGroupRelService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private UserLocalService _userLocalService;

}