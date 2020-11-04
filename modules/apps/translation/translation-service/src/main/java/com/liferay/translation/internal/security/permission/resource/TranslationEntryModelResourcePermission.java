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

package com.liferay.translation.internal.security.permission.resource;

import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.translation.constants.TranslationActionKeys;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.translation.model.TranslationEntry",
	service = ModelResourcePermission.class
)
public class TranslationEntryModelResourcePermission
	implements ModelResourcePermission<TranslationEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, primaryKey, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, TranslationEntry.class.getName(), primaryKey,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			TranslationEntry translationEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, translationEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, TranslationEntry.class.getName(),
				translationEntry.getTranslationEntryId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_translationEntryLocalService.getTranslationEntry(primaryKey),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			TranslationEntry translationEntry, String actionId)
		throws PortalException {

		InfoItemPermissionProvider<Object> infoItemPermissionProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemPermissionProvider.class,
				translationEntry.getClassName());

		if (infoItemPermissionProvider.hasPermission(
				permissionChecker,
				new InfoItemReference(
					translationEntry.getClassName(),
					translationEntry.getClassPK()),
				actionId)) {

			return true;
		}

		String name =
			TranslationConstants.RESOURCE_NAME + "." +
				translationEntry.getLanguageId();

		if (permissionChecker.hasPermission(
				translationEntry.getGroupId(), name, name,
				TranslationActionKeys.TRANSLATE)) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return TranslationEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private TranslationEntryLocalService _translationEntryLocalService;

}