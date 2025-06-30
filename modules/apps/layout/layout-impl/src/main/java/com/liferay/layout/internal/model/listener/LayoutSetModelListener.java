/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.model.listener;

import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class LayoutSetModelListener extends BaseModelListener<LayoutSet> {

	@Override
	public void onAfterRemove(LayoutSet layoutSet) {
	}

	@Override
	public void onAfterUpdate(
		LayoutSet originalLayoutSet, LayoutSet layoutSet) {

		if ((originalLayoutSet != null) &&
			(layoutSet.getFaviconFileEntryId() !=
				originalLayoutSet.getFaviconFileEntryId()) &&
			(layoutSet.getFaviconFileEntryId() > 0)) {

			_grantGuestDownloadPermission(
				layoutSet.getCompanyId(), layoutSet.getFaviconFileEntryId());
		}
	}

	@Override
	public void onBeforeRemove(LayoutSet layoutSet)
		throws ModelListenerException {

		if (layoutSet == null) {
			return;
		}

		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRels(
			_portal.getClassNameId(LayoutSet.class),
			layoutSet.getLayoutSetId());
	}

	private void _grantGuestDownloadPermission(
		long companyId, long fileEntryId) {

		try {
			Role guestRole = _roleLocalService.getRole(
				companyId, RoleConstants.GUEST);

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, DLFileEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(fileEntryId),
				guestRole.getRoleId(), new String[] {ActionKeys.DOWNLOAD});

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Granted guest view permission to favicon file entry: " +
						fileEntryId);
			}
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to grant guest view permission to favicon file " +
					"entry: " + fileEntryId,
				portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetModelListener.class);

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}