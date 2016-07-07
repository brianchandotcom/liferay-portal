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

package com.liferay.sync.model.listener;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.sync.constants.SyncDeviceConstants;
import com.liferay.sync.model.SyncDevice;
import com.liferay.sync.service.SyncDeviceLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan McCann
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterRemove(User user) throws ModelListenerException {
		try {
			List<SyncDevice> syncDevices =
				_syncDeviceLocalService.getSyncDevices(
					user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

			for (SyncDevice syncDevice : syncDevices) {
				_syncDeviceLocalService.deleteSyncDevice(syncDevice);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onBeforeUpdate(User user) throws ModelListenerException {
		try {
			User originalUser = _userLocalService.getUser(user.getUserId());

			if (originalUser.isActive() && !user.isActive()) {
				List<SyncDevice> syncDevices =
					_syncDeviceLocalService.getSyncDevices(
						user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null);

				for (SyncDevice syncDevice : syncDevices) {
					_syncDeviceLocalService.updateStatus(
						syncDevice.getSyncDeviceId(),
						SyncDeviceConstants.STATUS_INACTIVE);
				}
			}
			else if (!originalUser.isActive() && user.isActive()) {
				List<SyncDevice> syncDevices =
					_syncDeviceLocalService.getSyncDevices(
						user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null);

				for (SyncDevice syncDevice : syncDevices) {
					_syncDeviceLocalService.updateStatus(
						syncDevice.getSyncDeviceId(),
						SyncDeviceConstants.STATUS_ACTIVE);
				}
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference(unbind = "-")
	protected void setSyncDeviceLocalService(
		SyncDeviceLocalService syncDeviceLocalService) {

		_syncDeviceLocalService = syncDeviceLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static SyncDeviceLocalService _syncDeviceLocalService;
	private static UserLocalService _userLocalService;

}