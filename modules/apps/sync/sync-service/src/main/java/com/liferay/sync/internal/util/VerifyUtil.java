/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.sync.internal.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.sync.constants.SyncDLObjectConstants;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.service.SyncDLObjectLocalService;
import com.liferay.sync.util.SyncHelper;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Dennis Ju
 * @deprecated As of 1.2.0, with no direct replacement
 */
@Component(immediate = true, service = VerifyUtil.class)
@Deprecated
public class VerifyUtil {

	public static void verify() throws Exception {
		VerifyUtil verifyUtil = new VerifyUtil();

		verifyUtil.doVerify();
	}

	public void doVerify() throws Exception {
		List<Group> groups = _groupLocalService.getGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			if (group.isStaged()) {
				continue;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Verifying group " + group.getGroupId());
			}

			verifyDLFileEntriesAndFolders(group.getGroupId());
			verifySyncDLObjects(group.getGroupId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Verification completed");
		}
	}

	protected void logCount(long count, long totalCount, String name) {
		if (_log.isDebugEnabled()) {
			if ((count % 1000) == 0) {
				_log.debug(
					StringBundler.concat(
						"Verified ", String.valueOf(count), "/",
						String.valueOf(totalCount), " ", name));
			}
		}
	}

	protected void verifyDLFileEntriesAndFolders(long groupId)
		throws Exception {

		_dlFoldersAndFileEntriesCount = 0;

		ActionableDynamicQuery dlFolderActionableDynamicQuery =
			_dlFolderLocalService.getActionableDynamicQuery();

		dlFolderActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property hiddenProperty = PropertyFactoryUtil.forName(
						"hidden");

					dynamicQuery.add(hiddenProperty.eq(false));

					Property mountPointProperty = PropertyFactoryUtil.forName(
						"mountPoint");

					dynamicQuery.add(mountPointProperty.eq(false));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					int[] workflowConstants = {
						WorkflowConstants.STATUS_APPROVED,
						WorkflowConstants.STATUS_IN_TRASH
					};

					dynamicQuery.add(statusProperty.in(workflowConstants));
				}

			});
		dlFolderActionableDynamicQuery.setGroupId(groupId);
		dlFolderActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DLFolder>() {

				@Override
				public void performAction(DLFolder dlFolder)
					throws PortalException {

					_dlFoldersAndFileEntriesCount++;

					logCount(
						_dlFoldersAndFileEntriesCount,
						_dlFoldersAndFileEntriesTotalCount,
						"DL folders and DL file entries");

					try {
						if (dlFolder.getStatus() ==
								WorkflowConstants.STATUS_APPROVED) {

							_syncHelper.addSyncDLObject(
								_syncHelper.toSyncDLObject(
									dlFolder, 0, StringPool.BLANK,
									SyncDLObjectConstants.EVENT_ADD),
								_syncDLObjectLocalService);
						}
						else {
							_syncHelper.addSyncDLObject(
								_syncHelper.toSyncDLObject(
									dlFolder, 0, StringPool.BLANK,
									SyncDLObjectConstants.EVENT_TRASH),
								_syncDLObjectLocalService);
						}
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}

			});

		ActionableDynamicQuery dlFileEntryActionableDynamicQuery =
			_dlFileEntryLocalService.getActionableDynamicQuery();

		dlFileEntryActionableDynamicQuery.setGroupId(groupId);
		dlFileEntryActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DLFileEntry>() {

				@Override
				public void performAction(DLFileEntry dlFileEntry)
					throws PortalException {

					_dlFoldersAndFileEntriesCount++;

					logCount(
						_dlFoldersAndFileEntriesCount,
						_dlFoldersAndFileEntriesTotalCount,
						"DL folders and DL file entries");

					if ((dlFileEntry.getStatus() !=
							WorkflowConstants.STATUS_APPROVED) &&
						!dlFileEntry.isInTrash()) {

						return;
					}

					try {
						SyncDLObject syncDLObject =
							_syncDLObjectLocalService.fetchSyncDLObject(
								SyncDLObjectConstants.TYPE_FILE,
								dlFileEntry.getFileEntryId());

						if (syncDLObject != null) {
							Date modifiedDate = dlFileEntry.getModifiedDate();

							if (syncDLObject.getModifiedTime() >=
									modifiedDate.getTime()) {

								return;
							}
						}

						String event = null;

						if (dlFileEntry.getStatus() ==
								WorkflowConstants.STATUS_APPROVED) {

							event = SyncDLObjectConstants.EVENT_ADD;
						}
						else {
							event = SyncDLObjectConstants.EVENT_TRASH;
						}

						if (dlFileEntry.isCheckedOut()) {
							SyncDLObject approvedFileEntrySyncDLObject =
								_syncHelper.toSyncDLObject(
									dlFileEntry, event,
									!dlFileEntry.isInTrash(), true);

							_syncHelper.addSyncDLObject(
								approvedFileEntrySyncDLObject,
								_syncDLObjectLocalService);
						}

						_syncHelper.addSyncDLObject(
							_syncHelper.toSyncDLObject(
								dlFileEntry, event, !dlFileEntry.isInTrash()),
							_syncDLObjectLocalService);
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}

			});

		_dlFoldersAndFileEntriesTotalCount =
			dlFolderActionableDynamicQuery.performCount() +
				dlFileEntryActionableDynamicQuery.performCount();

		dlFolderActionableDynamicQuery.performActions();

		dlFileEntryActionableDynamicQuery.performActions();

		logCount(
			_dlFoldersAndFileEntriesCount, _dlFoldersAndFileEntriesTotalCount,
			"DL folders and DL file entries");
	}

	protected void verifySyncDLObjects(final long groupId) throws Exception {
		_syncDLObjectsCount = 0;

		ActionableDynamicQuery actionableDynamicQuery =
			_syncDLObjectLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property eventProperty = PropertyFactoryUtil.forName(
						"event");

					dynamicQuery.add(
						eventProperty.ne(SyncDLObjectConstants.EVENT_DELETE));

					Property repositoryIdProperty = PropertyFactoryUtil.forName(
						"repositoryId");

					dynamicQuery.add(repositoryIdProperty.eq(groupId));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SyncDLObject>() {

				@Override
				public void performAction(SyncDLObject syncDLObject)
					throws PortalException {

					_syncDLObjectsCount++;

					logCount(
						_syncDLObjectsCount, _syncDLObjectsTotalCount,
						"Sync DL objects");

					String type = syncDLObject.getType();

					if (type.equals(SyncDLObjectConstants.TYPE_FILE)) {
						DLFileEntry dlFileEntry =
							_dlFileEntryLocalService.fetchDLFileEntry(
								syncDLObject.getTypePK());

						if (dlFileEntry == null) {
							syncDLObject.setEvent(
								SyncDLObjectConstants.EVENT_DELETE);
							syncDLObject.setModifiedTime(
								System.currentTimeMillis());

							_syncHelper.addSyncDLObject(
								syncDLObject, _syncDLObjectLocalService);
						}
					}
					else if (type.equals(SyncDLObjectConstants.TYPE_FOLDER)) {
						DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(
							syncDLObject.getTypePK());

						if (dlFolder == null) {
							syncDLObject.setEvent(
								SyncDLObjectConstants.EVENT_DELETE);
							syncDLObject.setModifiedTime(
								System.currentTimeMillis());

							_syncHelper.addSyncDLObject(
								syncDLObject, _syncDLObjectLocalService);
						}
					}
					else if (type.equals(
								SyncDLObjectConstants.
									TYPE_PRIVATE_WORKING_COPY)) {

						DLFileEntry dlFileEntry =
							_dlFileEntryLocalService.fetchDLFileEntry(
								syncDLObject.getTypePK());

						if ((dlFileEntry == null) ||
							!_dlFileEntryLocalService.isFileEntryCheckedOut(
								syncDLObject.getTypePK())) {

							_syncDLObjectLocalService.deleteSyncDLObject(
								syncDLObject);
						}
					}
				}

			});

		_syncDLObjectsTotalCount = actionableDynamicQuery.performCount();

		actionableDynamicQuery.performActions();

		logCount(
			_syncDLObjectsTotalCount, _syncDLObjectsTotalCount,
			"Sync DL objects");
	}

	private static final Log _log = LogFactoryUtil.getLog(VerifyUtil.class);

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	private long _dlFoldersAndFileEntriesCount;
	private long _dlFoldersAndFileEntriesTotalCount;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SyncDLObjectLocalService _syncDLObjectLocalService;

	private long _syncDLObjectsCount;
	private long _syncDLObjectsTotalCount;

	@Reference
	private SyncHelper _syncHelper;

}