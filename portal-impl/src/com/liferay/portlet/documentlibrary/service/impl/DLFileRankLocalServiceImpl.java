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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.base.DLFileRankLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.FileRankCreateDateComparator;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileRankLocalServiceImpl extends DLFileRankLocalServiceBaseImpl {

	@Override
	public DLFileRank addFileRank(
			long groupId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long fileRankId = counterLocalService.increment();

		DLFileRank dlFileRank = dlFileRankPersistence.create(fileRankId);

		dlFileRank.setUuid(serviceContext.getUuid());
		dlFileRank.setGroupId(groupId);
		dlFileRank.setCompanyId(companyId);
		dlFileRank.setUserId(user.getUserId());
		dlFileRank.setUserName(user.getFullName());
		dlFileRank.setCreateDate(serviceContext.getCreateDate(now));
		dlFileRank.setModifiedDate(serviceContext.getModifiedDate(now));
		dlFileRank.setFileEntryId(fileEntryId);
		dlFileRank.setActive(true);

		try {
			dlFileRankPersistence.update(dlFileRank);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {companyId=" + companyId + ", userId=" +
						userId + ", fileEntryId=" + fileEntryId + "}");
			}

			dlFileRank = dlFileRankPersistence.fetchByC_U_F(
				companyId, userId, fileEntryId, false);

			if (dlFileRank == null) {
				throw se;
			}
		}

		return dlFileRank;
	}

	@Override
	public void checkFileRanks() throws SystemException {
		List<Object[]> staleFileRanks = dlFileRankFinder.findByStaleRanks(
			PropsValues.DL_FILE_RANK_MAX_SIZE);

		for (Object[] staleFileRank : staleFileRanks) {
			long groupId = (Long)staleFileRank[0];
			long userId = (Long)staleFileRank[1];

			List<DLFileRank> dlFileRanks = dlFileRankPersistence.findByG_U_A(
				groupId, userId, true, PropsValues.DL_FILE_RANK_MAX_SIZE,
				QueryUtil.ALL_POS, new FileRankCreateDateComparator());

			for (DLFileRank dlFileRank : dlFileRanks) {
				long fileRankId = dlFileRank.getFileRankId();

				try {
					dlFileRankPersistence.remove(dlFileRank);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to remove file rank " + fileRankId);
					}
				}
			}
		}
	}

	@Override
	public void deleteFileRank(DLFileRank dlFileRank) throws SystemException {
		dlFileRankPersistence.remove(dlFileRank);
	}

	@Override
	public void deleteFileRank(long fileRankId)
		throws PortalException, SystemException {

		DLFileRank dlFileRank = dlFileRankPersistence.findByPrimaryKey(
			fileRankId);

		deleteFileRank(dlFileRank);
	}

	@Override
	public void deleteFileRanksByFileEntryId(long fileEntryId)
		throws SystemException {

		List<DLFileRank> dlFileRanks = dlFileRankPersistence.findByFileEntryId(
			fileEntryId);

		for (DLFileRank dlFileRank : dlFileRanks) {
			deleteFileRank(dlFileRank);
		}
	}

	@Override
	public void deleteFileRanksByUserId(long userId) throws SystemException {
		List<DLFileRank> dlFileRanks = dlFileRankPersistence.findByUserId(
			userId);

		for (DLFileRank dlFileRank : dlFileRanks) {
			deleteFileRank(dlFileRank);
		}
	}

	@Override
	public void disableFileRanks(long fileEntryId) throws SystemException {
		List<DLFileRank> dlFileRanks = dlFileRankPersistence.findByFileEntryId(
			fileEntryId);

		for (DLFileRank dlFileRank : dlFileRanks) {
			dlFileRank.setActive(false);

			dlFileRankPersistence.update(dlFileRank);
		}
	}

	@Override
	public void disableFileRanksByFolderId(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getDLFolder(folderId);

		updateFileRanks(dlFolder, false);
	}

	@Override
	public void enableFileRanks(long fileEntryId) throws SystemException {
		List<DLFileRank> dlFileRanks = dlFileRankPersistence.findByFileEntryId(
			fileEntryId);

		for (DLFileRank dlFileRank : dlFileRanks) {
			dlFileRank.setActive(true);

			dlFileRankPersistence.update(dlFileRank);
		}
	}

	@Override
	public void enableFileRanksByFolderId(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getDLFolder(folderId);

		updateFileRanks(dlFolder, true);
	}

	@Override
	public List<DLFileRank> getFileRanks(long groupId, long userId)
		throws SystemException {

		return dlFileRankPersistence.findByG_U_A(
			groupId, userId, true, 0, PropsValues.DL_FILE_RANK_MAX_SIZE,
			new FileRankCreateDateComparator());
	}

	@Override
	public DLFileRank updateFileRank(
			long groupId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!PropsValues.DL_FILE_RANK_ENABLED) {
			return null;
		}

		DLFileRank dlFileRank = dlFileRankPersistence.fetchByC_U_F(
			companyId, userId, fileEntryId);

		if (dlFileRank != null) {
			dlFileRank.setModifiedDate(serviceContext.getModifiedDate(null));

			try {
				dlFileRankPersistence.update(dlFileRank);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Update failed, fetch {companyId=" + companyId +
							", userId=" + userId + ", fileEntryId=" +
								fileEntryId + "}");
				}
			}
		}
		else {
			dlFileRank = addFileRank(
				groupId, companyId, userId, fileEntryId, serviceContext);
		}

		return dlFileRank;
	}

	protected void updateFileRanks(DLFolder dlFolder, boolean active)
		throws SystemException {

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			dlFolder.getGroupId(), dlFolder.getFolderId());

		for (DLFolder curDLFolder : dlFolders) {
			updateFileRanks(curDLFolder, active);
		}

		List<DLFileRank> dlFileRanks = dlFileRankFinder.findByFolderId(
			dlFolder.getFolderId());

		for (DLFileRank dlFileRank : dlFileRanks) {
			dlFileRank.setActive(active);

			dlFileRankPersistence.update(dlFileRank);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFileRankLocalServiceImpl.class);

}