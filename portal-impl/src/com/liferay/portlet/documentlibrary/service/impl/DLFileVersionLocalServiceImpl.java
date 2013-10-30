/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.base.DLFileVersionLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileVersionLocalServiceImpl
	extends DLFileVersionLocalServiceBaseImpl {

	@Override
	public DLFileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		return dlFileVersionPersistence.findByPrimaryKey(fileVersionId);
	}

	@Override
	public DLFileVersion getFileVersion(long fileEntryId, String version)
		throws PortalException, SystemException {

		return dlFileVersionPersistence.findByF_V(fileEntryId, version);
	}

	@Override
	public DLFileVersion getFileVersionByUuidAndGroupId(
			String uuid, long groupId)
		throws SystemException {

		return dlFileVersionPersistence.fetchByUUID_G(uuid, groupId);
	}

	@Override
	public List<DLFileVersion> getFileVersions(long fileEntryId, int status)
		throws SystemException {

		List<DLFileVersion> dlFileVersions = null;

		if (status == WorkflowConstants.STATUS_ANY) {
			dlFileVersions = dlFileVersionPersistence.findByFileEntryId(
				fileEntryId);
		}
		else {
			dlFileVersions = dlFileVersionPersistence.findByF_S(
				fileEntryId, status);
		}

		dlFileVersions = ListUtil.copy(dlFileVersions);

		Collections.sort(dlFileVersions, new FileVersionVersionComparator());

		return dlFileVersions;
	}

	@Override
	public int getFileVersionsCount(long fileEntryId, int status)
		throws SystemException {

		return dlFileVersionPersistence.countByF_S(fileEntryId, status);
	}

	@Override
	public DLFileVersion getLatestFileVersion(
			long fileEntryId, boolean excludeWorkingCopy)
		throws PortalException, SystemException {

		List<DLFileVersion> dlFileVersions =
			dlFileVersionPersistence.findByFileEntryId(fileEntryId);

		if (dlFileVersions.isEmpty()) {
			throw new NoSuchFileVersionException(
				"No file versions found for fileEntryId " + fileEntryId);
		}

		dlFileVersions = ListUtil.copy(dlFileVersions);

		Collections.sort(dlFileVersions, new FileVersionVersionComparator());

		DLFileVersion dlFileVersion = dlFileVersions.get(0);

		String version = dlFileVersion.getVersion();

		if (excludeWorkingCopy &&
			version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {

			return dlFileVersions.get(1);
		}

		return dlFileVersion;
	}

	@Override
	public DLFileVersion getLatestFileVersion(long userId, long fileEntryId)
		throws PortalException, SystemException {

		boolean excludeWorkingCopy = true;

		if (dlFileEntryLocalService.isFileEntryCheckedOut(fileEntryId)) {
			excludeWorkingCopy = !dlFileEntryLocalService.hasFileEntryLock(
				userId, fileEntryId);
		}

		return getLatestFileVersion(fileEntryId, excludeWorkingCopy);
	}

	@Override
	public void rebuildTree(long companyId)
		throws PortalException, SystemException {

		dlFolderLocalService.rebuildTree(companyId);

		Session session = dlFileVersionPersistence.openSession();

		try {
			String sql = CustomSQLUtil.get(_UPDATE_TREE_PATHS);

			sql = StringUtil.replace(
				sql, new String[] {"[$SET$]", "[$WHERE$]"},
				new String[] {_TREE_PATH_SET_SQL, StringPool.BLANK});

			SQLQuery sqlQuery = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(companyId);
			qPos.add(WorkflowConstants.STATUS_IN_TRASH);

			sqlQuery.executeUpdate();

			// Update root folder file versions

			sql = CustomSQLUtil.get(_UPDATE_TREE_PATHS);

			sql = StringUtil.replace(
				sql, new String[] {"[$SET$]", "[$WHERE$]"},
				new String[] {"treePath = \"/0/\"", "folderId = 0 AND"});

			sqlQuery = session.createSQLQuery(sql);

			qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(companyId);
			qPos.add(WorkflowConstants.STATUS_IN_TRASH);

			sqlQuery.executeUpdate();
		}
		finally {
			dlFileVersionPersistence.closeSession(session);

			dlFileVersionPersistence.clearCache();
		}
	}

	private static final String _TREE_PATH_SET_SQL =
		"treePath = (SELECT DLFolder.treePath FROM DLFolder " +
			"WHERE DLFolder.folderId = DLFileVersion.folderId)";

	private static final String _UPDATE_TREE_PATHS =
		DLFileVersionLocalServiceImpl.class.getName() + ".updateTreePaths";

}