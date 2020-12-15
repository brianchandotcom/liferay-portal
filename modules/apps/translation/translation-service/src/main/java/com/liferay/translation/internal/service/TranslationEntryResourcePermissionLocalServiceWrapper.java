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

package com.liferay.translation.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.translation.constants.TranslationActionKeys;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class TranslationEntryResourcePermissionLocalServiceWrapper
	extends ResourcePermissionLocalServiceWrapper {

	public TranslationEntryResourcePermissionLocalServiceWrapper() {
		super(null);
	}

	public TranslationEntryResourcePermissionLocalServiceWrapper(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		super(resourcePermissionLocalService);
	}

	@Override
	public boolean hasResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException {

		if (!StringUtil.startsWith(name, TranslationEntry.class.getName())) {
			return super.hasResourcePermission(
				companyId, name, scope, primKey, roleId, actionId);
		}

		if (scope != ResourceConstants.SCOPE_INDIVIDUAL) {
			return false;
		}

		TranslationEntry translationEntry =
			_translationEntryLocalService.fetchTranslationEntry(
				GetterUtil.getLong(primKey));

		if (translationEntry == null) {
			return super.hasResourcePermission(
				companyId, name, scope, primKey, roleId, actionId);
		}

		if (_resourcePermissionLocalService.hasResourcePermission(
				companyId, translationEntry.getClassName(), scope,
				String.valueOf(translationEntry.getClassPK()), roleId,
				ActionKeys.UPDATE)) {

			return true;
		}

		String languageResourceName =
			TranslationConstants.RESOURCE_NAME + "." +
				translationEntry.getLanguageId();

		return _resourcePermissionLocalService.hasResourcePermission(
			companyId, languageResourceName, scope, languageResourceName,
			roleId, TranslationActionKeys.TRANSLATE);
	}

	@Override
	public boolean hasResourcePermission(
			long companyId, String name, int scope, String primKey,
			long[] roleIds, String actionId)
		throws PortalException {

		if (!StringUtil.startsWith(name, TranslationEntry.class.getName())) {
			return super.hasResourcePermission(
				companyId, name, scope, primKey, roleIds, actionId);
		}

		if (scope != ResourceConstants.SCOPE_INDIVIDUAL) {
			return false;
		}

		TranslationEntry translationEntry =
			_translationEntryLocalService.fetchTranslationEntry(
				GetterUtil.getLong(primKey));

		if (translationEntry == null) {
			return super.hasResourcePermission(
				companyId, name, scope, primKey, roleIds, actionId);
		}

		if (_resourcePermissionLocalService.hasResourcePermission(
				companyId, translationEntry.getClassName(), scope,
				String.valueOf(translationEntry.getClassPK()), roleIds,
				ActionKeys.UPDATE)) {

			return true;
		}

		String languageResourceName =
			TranslationConstants.RESOURCE_NAME + "." +
				translationEntry.getLanguageId();

		return _resourcePermissionLocalService.hasResourcePermission(
			companyId, languageResourceName, scope, languageResourceName,
			roleIds, TranslationActionKeys.TRANSLATE);
	}

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private TranslationEntryLocalService _translationEntryLocalService;

}