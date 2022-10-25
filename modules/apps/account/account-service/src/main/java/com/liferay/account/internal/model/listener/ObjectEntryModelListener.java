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

package com.liferay.account.internal.model.listener;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.entry.permission.util.ObjectEntryPermissionUtil;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = ModelListener.class)
public class ObjectEntryModelListener extends BaseModelListener<ObjectEntry> {

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			ObjectEntryPermissionUtil.checkAccountEntryPermission(
				_accountEntryLocalService, ObjectActionKeys.ADD_OBJECT_ENTRY,
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId()),
				objectEntry, _objectFieldLocalService, objectEntry.getUserId());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			ObjectEntryPermissionUtil.checkAccountEntryPermission(
				_accountEntryLocalService, ActionKeys.UPDATE,
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId()),
				objectEntry, _objectFieldLocalService, objectEntry.getUserId());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}