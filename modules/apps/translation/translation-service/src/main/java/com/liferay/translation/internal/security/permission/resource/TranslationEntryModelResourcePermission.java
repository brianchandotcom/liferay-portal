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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(
	immediate = true,
	property = "model.class.name=" + TranslationConstants.MODEL_CLASS_NAME,
	service = ModelResourcePermission.class
)
public class TranslationEntryModelResourcePermission
	implements ModelResourcePermission<TranslationEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long translationEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, translationEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, TranslationEntry.class.getName(),
				translationEntryId, actionId);
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
			PermissionChecker permissionChecker, long translationEntryId,
			String actionId)
		throws PortalException {

		return _contains(
			permissionChecker,
			_translationEntryLocalService.getTranslationEntry(
				translationEntryId),
			actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, TranslationEntry translationEntry,
		String actionId) {

		return _contains(permissionChecker, translationEntry, actionId);
	}

	@Override
	public String getModelName() {
		return TranslationEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	private boolean _contains(
		PermissionChecker permissionChecker, TranslationEntry translationEntry,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				translationEntry.getCompanyId(),
				TranslationEntry.class.getName(),
				translationEntry.getTranslationEntryId(),
				translationEntry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			translationEntry.getGroupId(), TranslationEntry.class.getName(),
			translationEntry.getTranslationEntryId(), actionId);
	}

	@Reference(
		target = "(resource.name=" + TranslationConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private TranslationEntryLocalService _translationEntryLocalService;

}