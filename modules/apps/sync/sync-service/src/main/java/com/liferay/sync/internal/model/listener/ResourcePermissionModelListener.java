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

package com.liferay.sync.internal.model.listener;

import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.sync.model.SyncDLObject;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = ModelListener.class)
public class ResourcePermissionModelListener
	extends SyncBaseModelListener<ResourcePermission> {

	@Override
	public void onBeforeCreate(ResourcePermission resourcePermission)
		throws ModelListenerException {

		SyncDLObject syncDLObject = getSyncDLObject(resourcePermission);

		if (syncDLObject == null) {
			return;
		}

		if (resourcePermission.hasActionId(ActionKeys.VIEW)) {
			updateSyncDLObject(syncDLObject);
		}
	}

	@Override
	public void onBeforeRemove(ResourcePermission resourcePermission)
		throws ModelListenerException {

		SyncDLObject syncDLObject = getSyncDLObject(resourcePermission);

		if (syncDLObject == null) {
			return;
		}

		if (resourcePermission.hasActionId(ActionKeys.VIEW)) {
			Date date = new Date();

			syncDLObject.setModifiedTime(date.getTime());
			syncDLObject.setLastPermissionChangeDate(date);

			syncDLObjectLocalService.updateSyncDLObject(syncDLObject);
		}
	}

	@Override
	public void onBeforeUpdate(ResourcePermission resourcePermission)
		throws ModelListenerException {

		SyncDLObject syncDLObject = getSyncDLObject(resourcePermission);

		if (syncDLObject == null) {
			return;
		}

		ResourcePermission originalResourcePermission =
			resourcePermissionLocalService.fetchResourcePermission(
				resourcePermission.getResourcePermissionId());

		if (originalResourcePermission.hasActionId(ActionKeys.VIEW) &&
			!resourcePermission.hasActionId(ActionKeys.VIEW)) {

			Date date = new Date();

			syncDLObject.setModifiedTime(date.getTime());
			syncDLObject.setLastPermissionChangeDate(date);

			syncDLObjectLocalService.updateSyncDLObject(syncDLObject);
		}
		else if (!originalResourcePermission.hasActionId(ActionKeys.VIEW) &&
				 resourcePermission.hasActionId(ActionKeys.VIEW)) {

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					NoticeableExecutorService noticeableExecutorService =
						portalExecutorManager.getPortalExecutor(
							ResourcePermissionModelListener.class.getName());

					noticeableExecutorService.submit(
						() -> {
							try {
								updateSyncDLObject(syncDLObject);
							}
							catch (Exception exception) {
								throw new ModelListenerException(exception);
							}

							return null;
						});

					return null;
				});
		}
	}

}