/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.upgrade.v4_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.GroupLocalService;
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
public class LayoutSEOEntryUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

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
			_connection, "LayoutSEOEntry", "openGraphImageFileEntryId", "LONG");

		_group = _groupLocalService.fetchGroup(TestPropsValues.getGroupId());
	}

	@After
	public void tearDown() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Test
	@TestInfo("LPD-74804")
	public void testUpgrade() throws Exception {
		DLFileEntry dlFileEntry1 = _addFileEntry(_group.getGroupId());

		LayoutSEOEntry layoutSEOEntry1 = _addLayoutSEOEntry(
			_group.getGroupId());

		_updateOpenGraphImageFileEntryId(
			layoutSEOEntry1.getCtCollectionId(),
			layoutSEOEntry1.getLayoutSEOEntryId(),
			dlFileEntry1.getFileEntryId(), null);

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		DLFileEntry dlFileEntry2 = _addFileEntry(companyGroup.getGroupId());

		LayoutSEOEntry layoutSEOEntry2 = _addLayoutSEOEntry(
			_group.getGroupId());

		_updateOpenGraphImageFileEntryId(
			layoutSEOEntry2.getCtCollectionId(),
			layoutSEOEntry2.getLayoutSEOEntryId(),
			dlFileEntry2.getFileEntryId(), null);

		runUpgrade();

		layoutSEOEntry1 = _layoutSEOEntryLocalService.fetchLayoutSEOEntry(
			layoutSEOEntry1.getLayoutSEOEntryId());

		Assert.assertEquals(
			dlFileEntry1.getExternalReferenceCode(),
			layoutSEOEntry1.getOpenGraphImageFileEntryERC());
		Assert.assertTrue(
			Validator.isNull(
				layoutSEOEntry1.getOpenGraphImageFileEntryScopeERC()));

		layoutSEOEntry2 = _layoutSEOEntryLocalService.fetchLayoutSEOEntry(
			layoutSEOEntry2.getLayoutSEOEntryId());

		Assert.assertEquals(
			dlFileEntry2.getExternalReferenceCode(),
			layoutSEOEntry2.getOpenGraphImageFileEntryERC());
		Assert.assertEquals(
			companyGroup.getExternalReferenceCode(),
			layoutSEOEntry2.getOpenGraphImageFileEntryScopeERC());

		Assert.assertTrue(
			_dbInspector.hasColumn(
				"LayoutSEOEntry", "openGraphImageFileEntryERC"));
		Assert.assertFalse(
			_dbInspector.hasColumn(
				"LayoutSEOEntry", "openGraphImageFileEntryId"));
		Assert.assertTrue(
			_dbInspector.hasColumn(
				"LayoutSEOEntry", "openGraphImageFileEntrySERC"));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		LayoutSEOEntry layoutSEOEntry = _addLayoutSEOEntry(_group.getGroupId());

		DLFileEntry dlFileEntry = _addFileEntry(_group.getGroupId());

		_updateOpenGraphImageFileEntryId(
			layoutSEOEntry.getCtCollectionId(),
			layoutSEOEntry.getLayoutSEOEntryId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getExternalReferenceCode());

		_entityCache.clearCache();
		_multiVMPool.clear();

		return _layoutSEOEntryLocalService.fetchLayoutSEOEntry(
			layoutSEOEntry.getLayoutSEOEntryId());
	}

	@Override
	protected void deleteCTModel(long primaryKey) throws Exception {
		_layoutSEOEntryLocalService.deleteLayoutSEOEntry(primaryKey);
	}

	@Override
	protected CTService<?> getCTService() {
		return _layoutSEOEntryLocalService;
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
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		LayoutSEOEntry layoutSEOEntry = (LayoutSEOEntry)ctModel;

		layoutSEOEntry = _layoutSEOEntryLocalService.updateLayoutSEOEntry(
			layoutSEOEntry);

		DLFileEntry dlFileEntry = _addFileEntry(_group.getGroupId());

		_updateOpenGraphImageFileEntryId(
			layoutSEOEntry.getCtCollectionId(),
			layoutSEOEntry.getLayoutSEOEntryId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getExternalReferenceCode());

		_entityCache.clearCache();
		_multiVMPool.clear();

		return _layoutSEOEntryLocalService.fetchLayoutSEOEntry(
			layoutSEOEntry.getLayoutSEOEntryId());
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

	private LayoutSEOEntry _addLayoutSEOEntry(long groupId) throws Exception {
		long layoutSEOEntryId = _counterLocalService.increment(
			LayoutSEOEntry.class.getName());

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.createLayoutSEOEntry(layoutSEOEntryId);

		layoutSEOEntry.setGroupId(groupId);
		layoutSEOEntry.setCompanyId(TestPropsValues.getCompanyId());
		layoutSEOEntry.setLayoutId(RandomTestUtil.randomLong());
		layoutSEOEntry.setOpenGraphDescription(StringPool.BLANK);
		layoutSEOEntry.setOpenGraphImageAlt(StringPool.BLANK);
		layoutSEOEntry.setOpenGraphImageFileEntryERC(StringPool.BLANK);
		layoutSEOEntry.setOpenGraphImageFileEntryScopeERC(StringPool.BLANK);
		layoutSEOEntry.setOpenGraphTitle(StringPool.BLANK);

		return _layoutSEOEntryLocalService.addLayoutSEOEntry(layoutSEOEntry);
	}

	private void _updateOpenGraphImageFileEntryId(
			long ctCollectionId, long layoutSEOEntryId,
			long openGraphImageFileEntryId, String openGraphImageFileEntryERC)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				StringBundler.concat(
					"update LayoutSEOEntry set openGraphImageFileEntryERC = ",
					"?, openGraphImageFileEntrySERC = null, ",
					"openGraphImageFileEntryId = ? where ctCollectionId = ? ",
					"and layoutSEOEntryId = ?"))) {

			preparedStatement.setString(1, openGraphImageFileEntryERC);
			preparedStatement.setLong(2, openGraphImageFileEntryId);
			preparedStatement.setLong(3, ctCollectionId);
			preparedStatement.setLong(4, layoutSEOEntryId);

			preparedStatement.executeUpdate();
		}
	}

	@Inject(
		filter = "(&(component.name=com.liferay.layout.seo.internal.upgrade.registry.LayoutSEOServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private Connection _connection;

	@Inject
	private CounterLocalService _counterLocalService;

	private DBInspector _dbInspector;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private EntityCache _entityCache;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}