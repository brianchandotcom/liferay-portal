/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.upgrade.registry.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.After;
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
public class LayoutUtilityPageEntryUpgradeStepRegistratorTest {

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
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpgradeProcess() throws Exception {
		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				StringPool.BLANK, TestPropsValues.getUserId(),
				_group.getGroupId(), 0, 0, false, RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_STATUS, 0,
				_serviceContext);

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_classNameLocalService.getClassNameId(Layout.class),
			layoutUtilityPageEntry.getPlid());

		draftLayout.setPrivateLayout(true);
		draftLayout.setType(LayoutConstants.TYPE_CONTENT);

		draftLayout = _layoutLocalService.updateLayout(draftLayout);

		Layout layout = _layoutLocalService.fetchLayout(
			layoutUtilityPageEntry.getPlid());

		layout.setPrivateLayout(true);
		layout.setType(LayoutConstants.TYPE_CONTENT);

		layout = _layoutLocalService.updateLayout(layout);

		Layout sameLayoutIdLayout = LayoutTestUtil.addTypeContentLayout(_group);

		sameLayoutIdLayout.setLayoutId(layout.getLayoutId());

		sameLayoutIdLayout = _layoutLocalService.updateLayout(
			sameLayoutIdLayout);

		_runUpgrade();

		Layout updatedDraftLayout = _layoutLocalService.fetchLayout(
			draftLayout.getPlid());

		Assert.assertFalse(updatedDraftLayout.isPrivateLayout());
		Assert.assertTrue(updatedDraftLayout.isTypeUtility());

		Layout updatedLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNotEquals(
			updatedLayout.getLayoutId(), sameLayoutIdLayout.getLayoutId());
		Assert.assertFalse(updatedLayout.isPrivateLayout());
		Assert.assertTrue(updatedLayout.isTypeUtility());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(1, 4, 0));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		_multiVMPool.clear();
	}

	@Inject(
		filter = "(&(component.name=com.liferay.layout.utility.page.internal.upgrade.registry.LayoutUtilityPageEntryUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	private ServiceContext _serviceContext;

}