/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v5_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Georgel Pop
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
			_connection, "Layout", "faviconFileEntryId", "LONG");
	}

	@After
	public void tearDown() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Test
	@TestInfo("LPD-68134")
	public void testUpgrade() throws Exception {
		Group group = _groupLocalService.fetchGroup(
			TestPropsValues.getGroupId());
		Group globalGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		DLFileEntry dlFileEntry = _addFileEntry(group.getGroupId());
		DLFileEntry globalDLFileEntry = _addFileEntry(globalGroup.getGroupId());

		Layout layout1 = LayoutTestUtil.addTypeContentLayout(group);
		Layout layout2 = LayoutTestUtil.addTypeContentLayout(group);

		_updateFaviconFileEntryId(
			layout1.getCtCollectionId(), dlFileEntry.getFileEntryId(),
			layout1.getPlid());
		_updateFaviconFileEntryId(
			layout2.getCtCollectionId(), globalDLFileEntry.getFileEntryId(),
			layout2.getPlid());

		runUpgrade();

		layout1 = _layoutLocalService.fetchLayout(layout1.getPlid());
		layout2 = _layoutLocalService.fetchLayout(layout2.getPlid());

		Assert.assertEquals(
			dlFileEntry.getExternalReferenceCode(),
			layout1.getFaviconFileEntryERC());
		Assert.assertTrue(
			Validator.isNull(layout1.getFaviconFileEntryScopeERC()));

		Assert.assertEquals(
			globalDLFileEntry.getExternalReferenceCode(),
			layout2.getFaviconFileEntryERC());
		Assert.assertEquals(
			globalGroup.getExternalReferenceCode(),
			layout2.getFaviconFileEntryScopeERC());

		Assert.assertTrue(
			_dbInspector.hasColumn("Layout", "faviconFileEntryERC"));
		Assert.assertFalse(
			_dbInspector.hasColumn("Layout", "faviconFileEntryId"));
		Assert.assertTrue(
			_dbInspector.hasColumn("Layout", "faviconFileEntryScopeERC"));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		Group group = _groupLocalService.fetchGroup(
			TestPropsValues.getGroupId());

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		DLFileEntry dlFileEntry = _addFileEntry(group.getGroupId());

		layout.setFaviconFileEntryERC(dlFileEntry.getExternalReferenceCode());

		layout.setFaviconFileEntryScopeERC(null);

		_updateFaviconFileEntryId(
			layout.getCtCollectionId(), dlFileEntry.getFileEntryId(),
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
			_upgradeStepRegistrator, new Version(5, 0, 0));

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

	private DLFileEntry _addFileEntry(long groupId) throws Exception {
		return _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), groupId, groupId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.COMPANY_ID_BASIC_DOCUMENT,
			Collections.emptyMap(), null,
			new UnsyncByteArrayInputStream(new byte[0]), 0, null, null, null,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	private void _updateFaviconFileEntryId(
			long ctCollectionId, long faviconFileEntryId, long plid)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"update Layout set faviconFileEntryERC = null, " +
					"faviconFileEntryId = ?, faviconFileEntryScopeERC = null " +
						"where ctCollectionId = ? and plid = ?")) {

			preparedStatement.setLong(1, faviconFileEntryId);
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
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}