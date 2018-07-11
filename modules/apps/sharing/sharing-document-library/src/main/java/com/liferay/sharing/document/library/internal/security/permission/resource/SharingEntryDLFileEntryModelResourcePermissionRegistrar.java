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

package com.liferay.sharing.document.library.internal.security.permission.resource;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.lang.reflect.Field;

import java.util.Iterator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class SharingEntryDLFileEntryModelResourcePermissionRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		_modelResourcePermissionLogic =
			(permissionChecker, name, dlFileEntry, actionId) -> {
				if (!SharingEntryActionKey.isSupportedActionId(actionId)) {
					return null;
				}

				SharingEntryActionKey sharingEntryActionKey =
					SharingEntryActionKey.parseFromActionId(actionId);

				long classNameId = _classNameLocalService.getClassNameId(name);

				if (_sharingEntryLocalService.hasSharingPermission(
						permissionChecker.getUserId(), classNameId,
						dlFileEntry.getFileEntryId(), sharingEntryActionKey)) {

					return true;
				}

				return null;
			};

		Class<? extends ModelResourcePermission> clazz =
			_modelResourcePermission.getClass();

		try {
			Field field = clazz.getDeclaredField(
				"_modelResourcePermissionLogics");

			field.setAccessible(true);

			List<ModelResourcePermissionLogic> modelResourcePermissionLogics =
				(List<ModelResourcePermissionLogic>)field.get(
					_modelResourcePermission);

			modelResourcePermissionLogics.add(0, _modelResourcePermissionLogic);
		}
		catch (IllegalAccessException | NoSuchFieldException e) {
			_log.error(
				"Unable to include sharing permission check for DLFileEntry");
		}
	}

	@Deactivate
	protected void deactivate() {
		Class<? extends ModelResourcePermission> clazz =
			_modelResourcePermission.getClass();

		try {
			Field field = clazz.getDeclaredField(
				"_modelResourcePermissionLogics");

			field.setAccessible(true);

			List<ModelResourcePermissionLogic> modelResourcePermissionLogics =
				(List<ModelResourcePermissionLogic>)field.get(
					_modelResourcePermission);

			Iterator<ModelResourcePermissionLogic> it =
				modelResourcePermissionLogics.iterator();

			while (it.hasNext()) {
				if (it.next() == _modelResourcePermissionLogic) {
					it.remove();
				}
			}
		}
		catch (IllegalAccessException | NoSuchFieldException e) {
			_log.error(
				"Unable to remove sharing permission check for DLFileEntry");
		}

		_modelResourcePermissionLogic = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingEntryDLFileEntryModelResourcePermissionRegistrar.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileEntry)",
		unbind = "-"
	)
	private ModelResourcePermission<DLFileEntry> _modelResourcePermission;

	private ModelResourcePermissionLogic<DLFileEntry>
		_modelResourcePermissionLogic;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}