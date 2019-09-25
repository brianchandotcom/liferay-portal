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

package com.liferay.sync.internal.messaging;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.sync.model.DLSyncEvent;
import com.liferay.document.library.sync.service.DLSyncEventLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.sync.constants.SyncDLObjectConstants;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.model.impl.SyncDLObjectImpl;
import com.liferay.sync.service.SyncDLObjectLocalService;
import com.liferay.sync.util.SyncHelper;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dennis Ju
 */
@Component(
	immediate = true,
	property = "destination.name=" + DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR,
	service = MessageListener.class
)
public class DLSyncEventMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		ActionableDynamicQuery actionableDynamicQuery =
			_dlSyncEventLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property modifiedTimeProperty = PropertyFactoryUtil.forName(
					"modifiedTime");

				dynamicQuery.add(
					modifiedTimeProperty.gt(
						_syncDLObjectLocalService.getLatestModifiedTime()));
			});

		actionableDynamicQuery.setPerformActionMethod(
			(DLSyncEvent dlSyncEvent) -> {
				try {
					processDLSyncEvent(
						dlSyncEvent.getModifiedTime(), dlSyncEvent.getEvent(),
						dlSyncEvent.getType(), dlSyncEvent.getTypePK(), false);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String event = message.getString("event");
		long modifiedTime = message.getLong("modifiedTime");
		String type = message.getString("type");
		long typePK = message.getLong("typePK");

		try {
			processDLSyncEvent(modifiedTime, event, type, typePK, true);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void processDLSyncEvent(
			long modifiedTime, String event, String type, long typePK,
			boolean calculateChecksum)
		throws Exception {

		SyncDLObject syncDLObject = null;

		if (event.equals(SyncDLObjectConstants.EVENT_DELETE)) {
			syncDLObject = new SyncDLObjectImpl();

			setUser(syncDLObject);

			syncDLObject.setModifiedTime(System.currentTimeMillis());
			syncDLObject.setEvent(event);
			syncDLObject.setType(type);
			syncDLObject.setTypePK(typePK);
		}
		else if (type.equals(SyncDLObjectConstants.TYPE_FILE)) {
			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
				typePK);

			if (dlFileEntry == null) {
				return;
			}

			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			if (dlFileVersion.isPending()) {
				return;
			}

			String checksum = _syncHelper.getChecksum(modifiedTime, typePK);

			if (calculateChecksum &&
				((checksum != null) || dlFileEntry.isInTrash())) {

				calculateChecksum = false;
			}

			syncDLObject = _syncHelper.toSyncDLObject(
				dlFileEntry, event, calculateChecksum);

			syncDLObject.setModifiedTime(modifiedTime);

			if (checksum != null) {
				syncDLObject.setChecksum(checksum);
			}

			if (event.equals(SyncDLObjectConstants.EVENT_TRASH)) {
				setUser(syncDLObject);
			}

			syncDLObject.setLanTokenKey(
				_syncHelper.getLanTokenKey(modifiedTime, typePK, false));
		}
		else {
			DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(typePK);

			if ((dlFolder == null) ||
				!_syncHelper.isSupportedFolder(dlFolder)) {

				return;
			}

			syncDLObject = _syncHelper.toSyncDLObject(dlFolder, event);

			syncDLObject.setModifiedTime(modifiedTime);
		}

		_syncHelper.addSyncDLObject(syncDLObject, _syncDLObjectLocalService);
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLSyncEventLocalService(
		DLSyncEventLocalService dlSyncEventLocalService) {

		_dlSyncEventLocalService = dlSyncEventLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSyncDLObjectLocalService(
		SyncDLObjectLocalService syncDLObjectLocalService) {

		_syncDLObjectLocalService = syncDLObjectLocalService;
	}

	protected void setUser(SyncDLObject syncDLObject) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			User user = permissionChecker.getUser();

			syncDLObject.setUserId(user.getUserId());
			syncDLObject.setUserName(user.getFullName());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLSyncEventMessageListener.class);

	private DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFolderLocalService _dlFolderLocalService;
	private DLSyncEventLocalService _dlSyncEventLocalService;
	private SyncDLObjectLocalService _syncDLObjectLocalService;

	@Reference
	private SyncHelper _syncHelper;

}