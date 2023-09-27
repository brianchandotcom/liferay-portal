/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ServiceWrapper.class)
public class TranslationEntryResourcePermissionLocalServiceWrapper
	extends ResourcePermissionLocalServiceWrapper {

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