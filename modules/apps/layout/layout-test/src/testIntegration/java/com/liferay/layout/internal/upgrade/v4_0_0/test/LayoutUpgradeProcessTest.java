/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v4_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.test.util.LayoutPageTemplateTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class LayoutUpgradeProcessTest extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);

		_db = DBManagerUtil.getDB();

		_db.alterTableAddColumn(
			_connection, "Layout", "masterLayoutPlid", "LONG");
	}

	@After
	public void tearDown() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Test
	@TestInfo("LPD-63478")
	public void testUpgrade() throws Exception {
		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				TestPropsValues.getGroupId(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				WorkflowConstants.STATUS_APPROVED);

		Group group = _groupLocalService.fetchGroup(
			TestPropsValues.getGroupId());

		Layout masterLayout1 = LayoutTestUtil.addTypeContentLayout(group);
		Layout masterLayout2 = LayoutTestUtil.addTypeContentLayout(group);
		Layout masterLayout3 = LayoutTestUtil.addTypeContentLayout(group);

		_updateLayout(
			masterLayoutPageTemplateEntry.getPlid(), masterLayout1.getPlid());
		_updateLayout(RandomTestUtil.nextLong(), masterLayout2.getPlid());

		runUpgrade();

		masterLayout1 = _layoutLocalService.fetchLayout(
			masterLayout1.getPlid());
		masterLayout2 = _layoutLocalService.fetchLayout(
			masterLayout2.getPlid());
		masterLayout3 = _layoutLocalService.fetchLayout(
			masterLayout3.getPlid());

		Assert.assertEquals(
			masterLayoutPageTemplateEntry.getExternalReferenceCode(),
			masterLayout1.getMasterLayoutPageTemplateEntryERC());
		Assert.assertTrue(
			Validator.isNull(
				masterLayout2.getMasterLayoutPageTemplateEntryERC()));
		Assert.assertTrue(
			Validator.isNull(
				masterLayout3.getMasterLayoutPageTemplateEntryERC()));

		Assert.assertFalse(
			_dbInspector.hasColumn("Layout", "masterLayoutPlid"));
		Assert.assertTrue(_dbInspector.hasColumn("Layout", "masterLPTEERC"));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return LayoutTestUtil.addTypeContentLayout(
			_groupLocalService.fetchGroup(TestPropsValues.getGroupId()));
	}

	@Override
	protected void deleteCTModel(long primaryKey) throws Exception {
		Layout layout = _layoutLocalService.fetchLayout(primaryKey);

		if (layout != null) {
			_layoutLocalService.deleteLayout(layout);
		}
	}

	@Override
	protected CTService<?> getCTService() {
		return _layoutLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(4, 0, 0));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			Class<?> upgradeProcessClass = upgradeProcess.getClass();

			Method getPostUpgradeStepsMethod =
				upgradeProcessClass.getDeclaredMethod("getPostUpgradeSteps");

			getPostUpgradeStepsMethod.setAccessible(true);

			UpgradeStep[] postUpgradeSteps =
				(UpgradeStep[])getPostUpgradeStepsMethod.invoke(upgradeProcess);

			upgradeProcess.upgrade();

			for (UpgradeStep postUpgradeStep : postUpgradeSteps) {
				postUpgradeStep.upgrade();
			}
		}

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) {
		return _layoutLocalService.updateLayout((Layout)ctModel);
	}

	private void _updateLayout(long masterLayoutPlid, long plid)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"update Layout set masterLayoutPlid = ? where plid = ?")) {

			preparedStatement.setLong(1, masterLayoutPlid);
			preparedStatement.setLong(2, plid);

			preparedStatement.executeUpdate();
		}
	}

	@Inject(
		filter = "(&(component.name=com.liferay.layout.internal.upgrade.registry.LayoutServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private Connection _connection;
	private DB _db;
	private DBInspector _dbInspector;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}