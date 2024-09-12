/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.upgrade.v6_5_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class FriendlyURLUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, FriendlyURLUpgradeProcessTest.class.getSimpleName(), null);
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		MBCategory productionMBCategory = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			productionMBCategory = _mbCategoryLocalService.addCategory(
				null, TestPropsValues.getUserId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				StringUtil.randomString(), StringUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			productionMBCategory.setFriendlyURL(null);

			productionMBCategory = _mbCategoryLocalService.updateMBCategory(
				productionMBCategory);
		}

		MBCategory ctCollectionMBCategory = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			ctCollectionMBCategory = _mbCategoryLocalService.updateCategory(
				productionMBCategory.getCategoryId(),
				productionMBCategory.getParentCategoryId(),
				StringUtil.randomString(),
				productionMBCategory.getDescription(),
				productionMBCategory.getDisplayStyle(), null, null, null, 0,
				false, null, null, 0, null, false, null, 0, false, null, null,
				false, false, false,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			ctCollectionMBCategory.setFriendlyURL(null);

			ctCollectionMBCategory = _mbCategoryLocalService.updateMBCategory(
				ctCollectionMBCategory);
		}

		_runUpgrade();

		_multiVMPool.clear();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			ctCollectionMBCategory = _mbCategoryLocalService.fetchMBCategory(
				productionMBCategory.getCategoryId());
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			productionMBCategory = _mbCategoryLocalService.fetchMBCategory(
				productionMBCategory.getCategoryId());
		}

		Assert.assertEquals(
			StringUtil.toLowerCase(productionMBCategory.getName()),
			productionMBCategory.getFriendlyURL());
		Assert.assertEquals(
			StringUtil.toLowerCase(ctCollectionMBCategory.getName()),
			ctCollectionMBCategory.getFriendlyURL());
		Assert.assertNotEquals(
			StringUtil.toLowerCase(productionMBCategory.getName()),
			ctCollectionMBCategory.getFriendlyURL());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.message.boards.internal.upgrade.v6_5_0." +
				"FriendlyURLUpgradeProcess");

		upgradeProcess.upgrade();
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.message.boards.internal.upgrade.registry.MBServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MBCategoryLocalService _mbCategoryLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}