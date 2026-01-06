/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v6_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.version.Version;
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

		DB db = DBManagerUtil.getDB();

		db.alterTableAddColumn(
			_connection, "Layout", "layoutPrototypeUuid", "VARCHAR(75)");

		_group = _groupLocalService.fetchGroup(TestPropsValues.getGroupId());
	}

	@After
	public void tearDown() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Test
	@TestInfo("LPD-66560")
	public void testUpgrade() throws Exception {
		Group globalGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		LayoutPrototype globalLayoutPrototype = _addLayoutPrototype(
			globalGroup.getGroupId());

		LayoutPrototype layoutPrototype = _addLayoutPrototype(
			_group.getGroupId());

		Layout layout1 = LayoutTestUtil.addTypePortletLayout(_group);
		Layout layout2 = LayoutTestUtil.addTypePortletLayout(_group);

		_updateLayoutPrototypeUuid(
			layout1.getCtCollectionId(), globalLayoutPrototype.getUuid(),
			layout1.getPlid());
		_updateLayoutPrototypeUuid(
			layout2.getCtCollectionId(), layoutPrototype.getUuid(),
			layout2.getPlid());

		runUpgrade();

		layout1 = _layoutLocalService.fetchLayout(layout1.getPlid());
		layout2 = _layoutLocalService.fetchLayout(layout2.getPlid());

		LayoutPageTemplateEntry globalLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getFirstLayoutPageTemplateEntry(
					globalLayoutPrototype.getLayoutPrototypeId());

		Assert.assertEquals(
			globalLayoutPageTemplateEntry.getExternalReferenceCode(),
			layout1.getPortletLayoutPageTemplateEntryERC());

		Assert.assertEquals(
			globalGroup.getExternalReferenceCode(),
			layout1.getPortletLayoutPageTemplateEntryScopeERC());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getFirstLayoutPageTemplateEntry(
					layoutPrototype.getLayoutPrototypeId());

		Assert.assertEquals(
			layoutPageTemplateEntry.getExternalReferenceCode(),
			layout2.getPortletLayoutPageTemplateEntryERC());

		Assert.assertNull(layout2.getPortletLayoutPageTemplateEntryScopeERC());

		Assert.assertFalse(
			_dbInspector.hasColumn("Layout", "layoutPrototypeUuid"));
		Assert.assertTrue(_dbInspector.hasColumn("Layout", "portletLPTEERC"));
		Assert.assertTrue(_dbInspector.hasColumn("Layout", "portletLPTESERC"));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		LayoutPrototype layoutPrototype = _addLayoutPrototype(
			_group.getGroupId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getFirstLayoutPageTemplateEntry(
					layoutPrototype.getLayoutPrototypeId());

		layout.setPortletLayoutPageTemplateEntryERC(
			layoutPageTemplateEntry.getExternalReferenceCode());

		layout.setPortletLayoutPageTemplateEntryScopeERC(null);

		_updateLayoutPrototypeUuid(
			layout.getCtCollectionId(), layoutPrototype.getUuid(),
			layout.getPlid());

		return layout;
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
			_upgradeStepRegistrator, new Version(6, 0, 0));

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

	private LayoutPrototype _addLayoutPrototype(long groupId) throws Exception {
		return _layoutPrototypeLocalService.addLayoutPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), true,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	private void _updateLayoutPrototypeUuid(
			long ctCollectionId, String layoutPrototypeUuid, long plid)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"update Layout set layoutPrototypeUuid = ?, portletLPTEERC = " +
					"null, portletLPTESERC = null where ctCollectionId = ? " +
						"and plid = ?")) {

			preparedStatement.setString(1, layoutPrototypeUuid);
			preparedStatement.setLong(2, ctCollectionId);
			preparedStatement.setLong(3, plid);

			preparedStatement.executeUpdate();
		}
	}

	@Inject(
		filter = "(&(component.name=com.liferay.layout.internal.upgrade.registry.LayoutServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private Connection _connection;
	private DBInspector _dbInspector;

	@Inject
	private EntityCache _entityCache;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}