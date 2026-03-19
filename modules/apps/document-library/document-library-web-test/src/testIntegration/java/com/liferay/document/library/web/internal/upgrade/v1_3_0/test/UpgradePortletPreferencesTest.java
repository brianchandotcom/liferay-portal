/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.upgrade.v1_3_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import jakarta.portlet.PortletPreferences;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class UpgradePortletPreferencesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
		_group3 = GroupTestUtil.addGroup();
		_group4 = GroupTestUtil.addGroup();

		_group1Repository = DLAppTestUtil.addRepository(_group1.getGroupId());
		_group3Repository = DLAppTestUtil.addRepository(_group3.getGroupId());
	}

	@Test
	public void testUpgrade() throws Exception {
		_testUpgradeLayoutWithDifferentGroup();
		_testUpgradeLayoutWithSameGroup();
		_testUpgradeLayoutInStagedGroupWithDifferentGroup();
		_testUpgradeLayoutInStagedGroupWithSameGroup();
	}

	private void _assertActualPortletPreferences(
		PortletPreferences portletPreferences,
		String selectedRepositoryExternalReferenceCode) {

		Map<String, String[]> map = portletPreferences.getMap();

		Assert.assertFalse(
			map.containsKey("selectedGroupExternalReferenceCode"));
		Assert.assertTrue(
			map.containsKey("selectedRepositoryExternalReferenceCode"));

		Assert.assertEquals(
			selectedRepositoryExternalReferenceCode,
			portletPreferences.getValue(
				"selectedRepositoryExternalReferenceCode", null));
	}

	private void _assertOriginalPortletPreferences(
		String selectedGroupExternalReferenceCode,
		String selectedRepositoryExternalReferenceCode,
		PortletPreferences portletPreferences) {

		Map<String, String[]> map = portletPreferences.getMap();

		Assert.assertTrue(
			map.containsKey("selectedGroupExternalReferenceCode"));
		Assert.assertTrue(
			map.containsKey("selectedRepositoryExternalReferenceCode"));

		Assert.assertEquals(
			selectedGroupExternalReferenceCode,
			portletPreferences.getValue(
				"selectedGroupExternalReferenceCode", null));
		Assert.assertEquals(
			selectedRepositoryExternalReferenceCode,
			portletPreferences.getValue(
				"selectedRepositoryExternalReferenceCode", null));
	}

	private void _assertPortletPreferencesValues(
		PortletPreferences portletPreferences,
		String selectedGroupExternalReferenceCode,
		String selectedRepositoryExternalReferenceCode) {

		Assert.assertEquals(
			selectedGroupExternalReferenceCode,
			portletPreferences.getValue(
				"selectedGroupExternalReferenceCode", null));
		Assert.assertEquals(
			selectedRepositoryExternalReferenceCode,
			portletPreferences.getValue(
				"selectedRepositoryExternalReferenceCode", null));
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_entityCache.clearCache();
			_multiVMPool.clear();
		}
	}

	private void _testUpgradeLayoutInStagedGroupWithDifferentGroup()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group4);

		String portletId = LayoutTestUtil.addPortletToLayout(
			layout, DLPortletKeys.DOCUMENT_LIBRARY);

		_updateLayoutPortletPreferences(
			layout, portletId, _group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode());

		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), _group4, false, false,
			ServiceContextTestUtil.getServiceContext(_group4.getGroupId()));

		_assertOriginalPortletPreferences(
			_group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode(),
			LayoutTestUtil.getPortletPreferences(layout, portletId));

		_group4 = _groupLocalService.getGroup(_group4.getGroupId());

		Group stagingGroup = _group4.getStagingGroup();

		Assert.assertNotEquals(stagingGroup.getGroupId(), _group4.getGroupId());

		Layout stagingLayout = _layoutLocalService.getLayoutByFriendlyURL(
			stagingGroup.getGroupId(), layout.isPrivateLayout(),
			layout.getFriendlyURL());

		_assertOriginalPortletPreferences(
			_group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode(),
			LayoutTestUtil.getPortletPreferences(stagingLayout, portletId));

		_runUpgrade();

		PortletPreferences portletPreferences =
			LayoutTestUtil.getPortletPreferences(layout, portletId);

		_assertPortletPreferencesValues(
			portletPreferences, _group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode());

		portletPreferences = LayoutTestUtil.getPortletPreferences(
			stagingLayout, portletId);

		_assertPortletPreferencesValues(
			portletPreferences, _group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode());
	}

	private void _testUpgradeLayoutInStagedGroupWithSameGroup()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group3);

		String portletId = LayoutTestUtil.addPortletToLayout(
			layout, DLPortletKeys.DOCUMENT_LIBRARY);

		_updateLayoutPortletPreferences(
			layout, portletId, _group3.getExternalReferenceCode(),
			_group3Repository.getExternalReferenceCode());

		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), _group3, false, false,
			ServiceContextTestUtil.getServiceContext(_group3.getGroupId()));

		_assertOriginalPortletPreferences(
			_group3.getExternalReferenceCode(),
			_group3Repository.getExternalReferenceCode(),
			LayoutTestUtil.getPortletPreferences(layout, portletId));

		_group3 = _groupLocalService.getGroup(_group3.getGroupId());

		Group stagingGroup = _group3.getStagingGroup();

		Assert.assertNotEquals(stagingGroup.getGroupId(), _group3.getGroupId());

		Layout stagingLayout = _layoutLocalService.getLayoutByFriendlyURL(
			stagingGroup.getGroupId(), layout.isPrivateLayout(),
			layout.getFriendlyURL());

		_assertOriginalPortletPreferences(
			_group3.getExternalReferenceCode(),
			_group3Repository.getExternalReferenceCode(),
			LayoutTestUtil.getPortletPreferences(stagingLayout, portletId));

		_runUpgrade();

		PortletPreferences portletPreferences =
			LayoutTestUtil.getPortletPreferences(layout, portletId);

		_assertActualPortletPreferences(
			portletPreferences, _group3Repository.getExternalReferenceCode());

		portletPreferences = LayoutTestUtil.getPortletPreferences(
			stagingLayout, portletId);

		_assertActualPortletPreferences(
			portletPreferences, _group3Repository.getExternalReferenceCode());
	}

	private void _testUpgradeLayoutWithDifferentGroup() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group2);

		String portletId = LayoutTestUtil.addPortletToLayout(
			layout, DLPortletKeys.DOCUMENT_LIBRARY);

		_updateLayoutPortletPreferences(
			layout, portletId, _group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode());

		_assertOriginalPortletPreferences(
			_group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode(),
			LayoutTestUtil.getPortletPreferences(layout, portletId));

		_runUpgrade();

		_assertPortletPreferencesValues(
			LayoutTestUtil.getPortletPreferences(layout, portletId),
			_group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode());
	}

	private void _testUpgradeLayoutWithSameGroup() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group1);

		String portletId = LayoutTestUtil.addPortletToLayout(
			layout, DLPortletKeys.DOCUMENT_LIBRARY);

		_updateLayoutPortletPreferences(
			layout, portletId, _group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode());

		_assertOriginalPortletPreferences(
			_group1.getExternalReferenceCode(),
			_group1Repository.getExternalReferenceCode(),
			LayoutTestUtil.getPortletPreferences(layout, portletId));

		_runUpgrade();

		_assertActualPortletPreferences(
			LayoutTestUtil.getPortletPreferences(layout, portletId),
			_group1Repository.getExternalReferenceCode());
	}

	private void _updateLayoutPortletPreferences(
			Layout layout, String portletId,
			String selectedGroupExternalReferenceCode,
			String selectedRepositoryExternalReferenceCode)
		throws Exception {

		LayoutTestUtil.updateLayoutPortletPreferences(
			layout, portletId,
			HashMapBuilder.put(
				"selectedGroupExternalReferenceCode",
				selectedGroupExternalReferenceCode
			).put(
				"selectedRepositoryExternalReferenceCode",
				selectedRepositoryExternalReferenceCode
			).build());
	}

	private static final String _CLASS_NAME =
		"com.liferay.document.library.web.internal.upgrade.v1_3_0." +
			"UpgradePortletPreferences";

	@Inject
	private EntityCache _entityCache;

	@DeleteAfterTestRun
	private Group _group1;

	private Repository _group1Repository;

	@DeleteAfterTestRun
	private Group _group2;

	@DeleteAfterTestRun
	private Group _group3;

	private Repository _group3Repository;

	@DeleteAfterTestRun
	private Group _group4;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private StagingLocalService _stagingLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.document.library.web.internal.upgrade.registry.DLWebUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}