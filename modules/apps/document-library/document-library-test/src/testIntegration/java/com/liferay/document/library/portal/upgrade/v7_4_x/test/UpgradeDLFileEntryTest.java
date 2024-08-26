/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradeDLFileEntry;

import java.sql.Connection;
import java.sql.Statement;

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
public class UpgradeDLFileEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, UpgradeDLFileEntryTest.class.getSimpleName(), null);
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		DLFileEntry productionDLFileEntry = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			productionDLFileEntry = _dlFileEntryLocalService.addFileEntry(
				StringUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), _group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				StringUtil.randomString(), StringUtil.randomString(),
				StringUtil.randomString(), null, StringUtil.randomString(),
				StringUtil.randomString(),
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
				null, null, new UnsyncByteArrayInputStream(new byte[0]), 0,
				null, null, null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		}

		DLFileEntry ctCollectionDLFileEntry = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			ctCollectionDLFileEntry = _dlFileEntryLocalService.getDLFileEntry(
				productionDLFileEntry.getFileEntryId());

			ctCollectionDLFileEntry =
				_dlFileEntryLocalService.updateDLFileEntry(
					ctCollectionDLFileEntry);

			_unsetExternalReferenceCode(ctCollectionDLFileEntry);
		}

		_runUpgrade();

		_multiVMPool.clear();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			productionDLFileEntry = _dlFileEntryLocalService.getDLFileEntry(
				productionDLFileEntry.getFileEntryId());
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			ctCollectionDLFileEntry = _dlFileEntryLocalService.getDLFileEntry(
				ctCollectionDLFileEntry.getFileEntryId());
		}

		Assert.assertNotNull(
			ctCollectionDLFileEntry.getExternalReferenceCode());
		Assert.assertEquals(
			String.valueOf(ctCollectionDLFileEntry.getFileEntryId()),
			ctCollectionDLFileEntry.getExternalReferenceCode());

		Assert.assertNotEquals(
			productionDLFileEntry.getExternalReferenceCode(),
			ctCollectionDLFileEntry.getExternalReferenceCode());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeDLFileEntry() {

			@Override
			protected void alterTableAddColumn(
					String tableName, String columnName, String columnType)
				throws Exception {

				if (tableName.equals("DLFileEntry") &&
					columnName.equals("externalReferenceCode") &&
					columnType.equals("VARCHAR(75)")) {

					return;
				}

				super.alterTableAddColumn(tableName, columnName, columnType);
			}

			@Override
			protected UpgradeStep[] getPostUpgradeSteps() {
				return new UpgradeStep[0];
			}

			@Override
			protected boolean hasColumn(String tableName, String columnName)
				throws Exception {

				if (tableName.equals("DLFileEntry") &&
					columnName.equals("externalReferenceCode")) {

					return false;
				}

				return super.hasColumn(tableName, columnName);
			}

		};

		upgradeProcess.upgrade();
	}

	private void _unsetExternalReferenceCode(DLFileEntry dlFileEntry)
		throws Exception {

		Connection connection = DataAccess.getConnection();

		Statement statement = connection.createStatement();

		int rowCount = statement.executeUpdate(
			StringBundler.concat(
				"update DLFileEntry set externalReferenceCode = '' where ",
				"ctCollectionId = ", dlFileEntry.getCtCollectionId(),
				" and fileEntryId = ", dlFileEntry.getFileEntryId()));

		Assert.assertEquals(1, rowCount);
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

}