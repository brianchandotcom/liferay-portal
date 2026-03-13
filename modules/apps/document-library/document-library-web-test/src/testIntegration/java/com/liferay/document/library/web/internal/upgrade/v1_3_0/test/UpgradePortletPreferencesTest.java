/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.upgrade.v1_3_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
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

		_repository = DLAppTestUtil.addRepository(_group1.getGroupId());
	}

	@Test
	public void testUpgrade() throws Exception {
		_testUpgradeLayoutInDifferentGroup();
		_testUpgradeLayoutInSameGroup();
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

	private void _runUpgrade(
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

		_assertOriginalPortletPreferences(
			selectedGroupExternalReferenceCode,
			selectedRepositoryExternalReferenceCode,
			LayoutTestUtil.getPortletPreferences(layout, portletId));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_entityCache.clearCache();
			_multiVMPool.clear();
		}
	}

	private void _testUpgradeLayoutInDifferentGroup() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group2);

		String portletId = LayoutTestUtil.addPortletToLayout(
			layout, DLPortletKeys.DOCUMENT_LIBRARY);

		_runUpgrade(
			layout, portletId, _group1.getExternalReferenceCode(),
			_repository.getExternalReferenceCode());

		PortletPreferences portletPreferences =
			LayoutTestUtil.getPortletPreferences(layout, portletId);

		Assert.assertEquals(
			_group1.getExternalReferenceCode(),
			portletPreferences.getValue(
				"selectedGroupExternalReferenceCode", null));
		Assert.assertEquals(
			_repository.getExternalReferenceCode(),
			portletPreferences.getValue(
				"selectedRepositoryExternalReferenceCode", null));
	}

	private void _testUpgradeLayoutInSameGroup() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group1);

		String portletId = LayoutTestUtil.addPortletToLayout(
			layout, DLPortletKeys.DOCUMENT_LIBRARY);

		_runUpgrade(
			layout, portletId, _group1.getExternalReferenceCode(),
			_repository.getExternalReferenceCode());

		PortletPreferences portletPreferences =
			LayoutTestUtil.getPortletPreferences(layout, portletId);

		Map<String, String[]> map = portletPreferences.getMap();

		Assert.assertFalse(
			map.containsKey("selectedGroupExternalReferenceCode"));
		Assert.assertFalse(
			map.containsKey("selectedRepositoryExternalReferenceCode"));
	}

	private static final String _CLASS_NAME =
		"com.liferay.document.library.web.internal.upgrade.v1_3_0." +
			"UpgradePortletPreferences";

	@Inject
	private EntityCache _entityCache;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private MultiVMPool _multiVMPool;

	private Repository _repository;

	@Inject(
		filter = "(&(component.name=com.liferay.document.library.web.internal.upgrade.registry.DLWebUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}