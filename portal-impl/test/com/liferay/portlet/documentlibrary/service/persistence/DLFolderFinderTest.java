/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

/**
 * @author Shuyang Zhou
 */
public class DLFolderFinderTest extends BasePersistenceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		SessionFactory sessionFactory =
			(SessionFactory)PortalBeanLocatorUtil.locate(
				"liferaySessionFactory");

		_finderImpl = (DLFolderFinderImpl)PortalBeanLocatorUtil.locate(
			DLFolderFinder.class.getName());

		_finderImpl.setSessionFactory(
			new SQLRecorderSessionFactoryWrapper(sessionFactory));
	}

	public void testDoCountF_FE_FS_ByG_F_S() throws Exception {
		// status = STATUS_ANY, mimeTypes = null,
		// includeMountFolders = false, inlineSQLHelper= false
		String expectSQL1 = "(SELECT COUNT(DISTINCT folderId)AS COUNT_VALUE " +
			"FROM DLFolder WHERE(DLFolder.groupId = ?)AND" +
			"(DLFolder.mountPoint = ?)AND DLFolder.parentFolderId= ?)" +
			"UNION ALL(SELECT COUNT(DISTINCT DLFileEntry.fileEntryId)" +
			"AS COUNT_VALUE FROM DLFileEntry WHERE(DLFileEntry.groupId = ?)" +
			"AND DLFileEntry.folderId= ?)UNION ALL(SELECT " +
			"COUNT(DISTINCT fileShortcutId)AS COUNT_VALUE FROM " +
			"DLFileShortcut WHERE(DLFileShortcut.groupId = ?)AND" +
			"(DLFileShortcut.status = 0)AND DLFileShortcut.folderId= ?)";

		_finderImpl.doCountF_FE_FS_ByG_F_S(
			0, 0, WorkflowConstants.STATUS_ANY, null, false, false);

		String actualSQL1 = SQLRecorderThreadLocal.getSQL();
		actualSQL1 = SQLRecorderUtil.stripSQL(actualSQL1);

		assertEquals(expectSQL1, actualSQL1);

		// status = STATUS_APPROVED, mimeTypes = null
		// includeMountFolders = false, inlineSQLHelper= false
		String expectSQL2 = "(SELECT COUNT(DISTINCT folderId)AS COUNT_VALUE " +
			"FROM DLFolder WHERE(DLFolder.groupId = ?)AND" +
			"(DLFolder.mountPoint = ?)AND DLFolder.parentFolderId= ?)" +
			"UNION ALL(SELECT COUNT(DISTINCT DLFileVersion.fileEntryId)" +
			"AS COUNT_VALUE FROM DLFileVersion " +
			"WHERE(DLFileVersion.groupId = ?)AND(DLFileVersion.status = ?)" +
			"AND DLFileVersion.folderId= ?)UNION ALL(SELECT " +
			"COUNT(DISTINCT fileShortcutId)AS COUNT_VALUE FROM " +
			"DLFileShortcut WHERE(DLFileShortcut.groupId = ?)AND" +
			"(DLFileShortcut.status = 0)AND DLFileShortcut.folderId= ?)";

		_finderImpl.doCountF_FE_FS_ByG_F_S(
			0, 0, WorkflowConstants.STATUS_APPROVED, null, false, false);

		String actualSQL2 = SQLRecorderThreadLocal.getSQL();
		actualSQL2 = SQLRecorderUtil.stripSQL(actualSQL2);

		assertEquals(expectSQL2, actualSQL2);

		// status = STATUS_APPROVED, mimeTypes = {"image/jpeg", "text/css"}
		// includeMountFolders = false, inlineSQLHelper= false
		String expect3 = "(SELECT COUNT(DISTINCT folderId)AS COUNT_VALUE " +
			"FROM DLFolder WHERE(DLFolder.groupId = ?)AND" +
			"(DLFolder.mountPoint = ?)AND DLFolder.parentFolderId= ?)" +
			"UNION ALL(SELECT COUNT(DISTINCT DLFileVersion.fileEntryId)AS " +
			"COUNT_VALUE FROM DLFileVersion INNER JOIN DLFileEntry ON" +
			"(DLFileEntry.fileEntryId = DLFileVersion.fileEntryId)WHERE" +
			"(DLFileVersion.groupId = ?)AND(DLFileVersion.status = ?)AND " +
			"DLFileVersion.folderId= ? AND(DLFileEntry.mimeType = " +
			"'image/jpeg' OR DLFileEntry.mimeType = 'text/css'))UNION ALL" +
			"(SELECT COUNT(DISTINCT fileShortcutId)AS COUNT_VALUE FROM " +
			"DLFileShortcut INNER JOIN DLFileEntry ON" +
			"(DLFileEntry.fileEntryId = DLFileShortcut.toFileEntryId)WHERE" +
			"(DLFileShortcut.groupId = ?)AND(DLFileShortcut.status = 0)AND " +
			"DLFileShortcut.folderId= ? AND(DLFileEntry.mimeType = " +
			"'image/jpeg' OR DLFileEntry.mimeType = 'text/css'))";

		_finderImpl.doCountF_FE_FS_ByG_F_S(
			0, 0, WorkflowConstants.STATUS_APPROVED,
			new String[]{"image/jpeg", "text/css"}, false, false);

		String actualSQL3 = SQLRecorderThreadLocal.getSQL();
		actualSQL3 = SQLRecorderUtil.stripSQL(actualSQL3);

		assertEquals(expect3, actualSQL3);

		// status = STATUS_APPROVED, mimeTypes = {"image/jpeg", "text/css"}
		// includeMountFolders = true, inlineSQLHelper= false
		String expect4 = "(SELECT COUNT(DISTINCT folderId)AS COUNT_VALUE " +
			"FROM DLFolder WHERE(DLFolder.groupId = ?)AND " +
			"DLFolder.parentFolderId= ?)UNION ALL(SELECT COUNT(DISTINCT " +
			"DLFileVersion.fileEntryId)AS COUNT_VALUE FROM DLFileVersion " +
			"INNER JOIN DLFileEntry ON(DLFileEntry.fileEntryId = " +
			"DLFileVersion.fileEntryId)WHERE(DLFileVersion.groupId = ?)AND" +
			"(DLFileVersion.status = ?)AND DLFileVersion.folderId= ? AND" +
			"(DLFileEntry.mimeType = 'image/jpeg' OR DLFileEntry.mimeType = " +
			"'text/css'))UNION ALL(SELECT COUNT(DISTINCT fileShortcutId)AS " +
			"COUNT_VALUE FROM DLFileShortcut INNER JOIN DLFileEntry ON" +
			"(DLFileEntry.fileEntryId = DLFileShortcut.toFileEntryId)WHERE" +
			"(DLFileShortcut.groupId = ?)AND(DLFileShortcut.status = 0)AND " +
			"DLFileShortcut.folderId= ? AND(DLFileEntry.mimeType = " +
			"'image/jpeg' OR DLFileEntry.mimeType = 'text/css'))";

		_finderImpl.doCountF_FE_FS_ByG_F_S(
			0, 0, WorkflowConstants.STATUS_APPROVED,
			new String[]{"image/jpeg", "text/css"}, true, false);

		String actualSQL4 = SQLRecorderThreadLocal.getSQL();
		actualSQL4 = SQLRecorderUtil.stripSQL(actualSQL4);

		assertEquals(expect4, actualSQL4);

		// status = STATUS_APPROVED, mimeTypes = {"image/jpeg", "text/css"}
		// includeMountFolders = true, inlineSQLHelper= true
		String expect5 = "(SELECT COUNT(DISTINCT folderId)AS COUNT_VALUE " +
			"FROM DLFolder WHERE(DLFolder.groupId = ?)AND " +
			"DLFolder.parentFolderId= ?)UNION ALL(SELECT COUNT(DISTINCT " +
			"DLFileVersion.fileEntryId)AS COUNT_VALUE FROM DLFileVersion " +
			"INNER JOIN DLFileEntry ON(DLFileEntry.fileEntryId = " +
			"DLFileVersion.fileEntryId)WHERE(DLFileVersion.groupId = ?)AND" +
			"(DLFileVersion.status = ?)AND DLFileVersion.folderId= ? AND" +
			"(DLFileEntry.mimeType = 'image/jpeg' OR DLFileEntry.mimeType = " +
			"'text/css'))UNION ALL(SELECT COUNT(DISTINCT fileShortcutId)AS " +
			"COUNT_VALUE FROM DLFileShortcut INNER JOIN DLFileEntry ON" +
			"(DLFileEntry.fileEntryId = DLFileShortcut.toFileEntryId)WHERE" +
			"(DLFileShortcut.groupId = ?)AND(DLFileShortcut.status = 0)AND " +
			"DLFileShortcut.folderId= ? AND(DLFileEntry.mimeType = " +
			"'image/jpeg' OR DLFileEntry.mimeType = 'text/css'))";

		_finderImpl.doCountF_FE_FS_ByG_F_S(
			0, 0, WorkflowConstants.STATUS_APPROVED,
			new String[]{"image/jpeg", "text/css"}, true, true);

		String actualSQL5 = SQLRecorderThreadLocal.getSQL();
		actualSQL5 = SQLRecorderUtil.stripSQL(actualSQL5);

		assertEquals(expect5, actualSQL5);
	}

	public void testDoCountFE_FS_ByG_F_S() throws Exception {
		// status = STATUS_ANY, inlineSQLHelper = false
		String expectSQL1 = "(SELECT COUNT(DISTINCT DLFileEntry.fileEntryId)" +
			"AS COUNT_VALUE FROM DLFileEntry WHERE(DLFileEntry.groupId = ?)" +
			"AND DLFileEntry.folderId= ?)UNION ALL(SELECT COUNT(DISTINCT " +
			"fileShortcutId)AS COUNT_VALUE FROM DLFileShortcut WHERE(" +
			"DLFileShortcut.groupId = ?)AND(DLFileShortcut.status = 0)AND " +
			"DLFileShortcut.folderId= ?)";

		_finderImpl.doCountFE_FS_ByG_F_S(0, 0, WorkflowConstants.STATUS_ANY,
			false);

		String actualSQL1 = SQLRecorderThreadLocal.getSQL();
		actualSQL1 = SQLRecorderUtil.stripSQL(actualSQL1);

		assertEquals(expectSQL1, actualSQL1);

		// status = STATUS_APPROVED, inlineSQLHelper = false
		String expectSQL2 = "(SELECT COUNT(DISTINCT " +
			"DLFileVersion.fileEntryId)AS COUNT_VALUE FROM DLFileVersion " +
			"WHERE(DLFileVersion.groupId = ?)AND(DLFileVersion.status = ?)" +
			"AND DLFileVersion.folderId= ?)UNION ALL(SELECT COUNT(DISTINCT " +
			"fileShortcutId)AS COUNT_VALUE FROM DLFileShortcut WHERE" +
			"(DLFileShortcut.groupId = ?)AND(DLFileShortcut.status = 0)AND " +
			"DLFileShortcut.folderId= ?)";

		_finderImpl.doCountFE_FS_ByG_F_S(0, 0,
			WorkflowConstants.STATUS_APPROVED, false);

		String actualSQL2 = SQLRecorderThreadLocal.getSQL();
		actualSQL2 = SQLRecorderUtil.stripSQL(actualSQL2);

		assertEquals(expectSQL2, actualSQL2);

		// status = STATUS_APPROVED, inlineSQLHelper = true
		String expectSQL3 = "(SELECT COUNT(DISTINCT " +
			"DLFileVersion.fileEntryId)AS COUNT_VALUE FROM DLFileVersion " +
			"WHERE(DLFileVersion.groupId = ?)AND(DLFileVersion.status = ?)" +
			"AND DLFileVersion.folderId= ?)UNION ALL(SELECT COUNT(DISTINCT " +
			"fileShortcutId)AS COUNT_VALUE FROM DLFileShortcut WHERE" +
			"(DLFileShortcut.groupId = ?)AND(DLFileShortcut.status = 0)AND " +
			"DLFileShortcut.folderId= ?)";

		_finderImpl.doCountFE_FS_ByG_F_S(0, 0,
			WorkflowConstants.STATUS_APPROVED, true);

		String actualSQL3 = SQLRecorderThreadLocal.getSQL();
		actualSQL3 = SQLRecorderUtil.stripSQL(actualSQL3);

		assertEquals(expectSQL3, actualSQL3);
	}

	private DLFolderFinderImpl _finderImpl;

}