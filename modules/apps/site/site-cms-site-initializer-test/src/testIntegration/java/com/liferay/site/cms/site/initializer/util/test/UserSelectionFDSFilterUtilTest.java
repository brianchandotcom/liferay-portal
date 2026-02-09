/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;
import com.liferay.site.cms.site.initializer.util.UserSelectionFDSFilterUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class UserSelectionFDSFilterUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CMSTestUtil.getOrAddGroup(CMSDefaultPermissionUtilTest.class);

		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@After
	public void tearDown() {
		PrincipalThreadLocal.setName(_originalName);
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testGetUsers() throws Exception {
		DepotEntry depotEntry1 = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		User user1 = UserTestUtil.addUser();

		_setUser(user1);

		Assert.assertNull(UserSelectionFDSFilterUtil.getUsers());

		_userLocalService.addGroupUser(
			depotEntry1.getGroupId(), user1.getUserId());

		_assertUserIds(TestPropsValues.getUserId(), user1.getUserId());

		User user2 = UserTestUtil.addUser();

		_userLocalService.addGroupUser(
			depotEntry1.getGroupId(), user2.getUserId());

		_assertUserIds(
			TestPropsValues.getUserId(), user1.getUserId(), user2.getUserId());

		_setUser(user2);

		_assertUserIds(
			TestPropsValues.getUserId(), user1.getUserId(), user2.getUserId());

		DepotEntry depotEntry2 = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		User user3 = UserTestUtil.addUser();

		_userLocalService.addGroupUser(
			depotEntry2.getGroupId(), user3.getUserId());

		_setUser(user3);

		_assertUserIds(TestPropsValues.getUserId(), user3.getUserId());

		_depotEntryLocalService.deleteDepotEntry(depotEntry1);
		_depotEntryLocalService.deleteDepotEntry(depotEntry2);
	}

	private void _assertUserIds(long... expectedUserIds) {
		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToLongArray(
					UserSelectionFDSFilterUtil.getUsers(), User::getUserId),
				expectedUserIds));
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@Inject
	private UserLocalService _userLocalService;

}