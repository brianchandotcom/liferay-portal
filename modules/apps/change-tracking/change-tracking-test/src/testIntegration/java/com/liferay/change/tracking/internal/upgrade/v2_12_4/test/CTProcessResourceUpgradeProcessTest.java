/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_12_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class CTProcessResourceUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.change.tracking.internal.upgrade.v2_12_4." +
				"CTProcessResourceUpgradeProcess");
	}

	@Test
	public void testUpgrade() throws Exception {
		long ctProcessId = RandomTestUtil.nextLong();

		CTProcess ctProcess = _ctProcessLocalService.createCTProcess(
			ctProcessId);

		ctProcess.setCompanyId(TestPropsValues.getCompanyId());
		ctProcess.setUserId(TestPropsValues.getUserId());
		ctProcess.setCreateDate(new Date());
		ctProcess.setCtCollectionId(RandomTestUtil.nextLong());

		ctProcess = _ctProcessLocalService.updateCTProcess(ctProcess);

		UserTestUtil.setUser(TestPropsValues.getUser());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Assert.assertFalse(
			permissionChecker.hasOwnerPermission(
				ctProcess.getCompanyId(), CTProcess.class.getName(),
				ctProcess.getCtProcessId(), ctProcess.getUserId(),
				ActionKeys.VIEW));

		_upgradeProcess.upgrade();

		Assert.assertTrue(
			permissionChecker.hasOwnerPermission(
				ctProcess.getCompanyId(), CTProcess.class.getName(),
				ctProcess.getCtProcessId(), ctProcess.getUserId(),
				ActionKeys.VIEW));
	}

	@Inject
	private CTProcessLocalService _ctProcessLocalService;

	@Inject
	private ResourceLocalService _resourceLocalService;

	private UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "(&(component.name=com.liferay.change.tracking.internal.upgrade.registry.ChangeTrackingServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}