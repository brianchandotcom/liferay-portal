/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.security.permission.resource;

import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
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
			_infoItemServiceRegistry.getFirstInfoItemService(
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
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private TranslationEntryLocalService _translationEntryLocalService;

}