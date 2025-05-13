/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemPermissionProvider.class)
public class ObjectEntryFolderInfoItemPermissionProvider
	implements InfoItemPermissionProvider<ObjectEntryFolder> {

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return _hasPermission(
			permissionChecker, classPKInfoItemIdentifier.getClassPK(),
			actionId);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			ObjectEntryFolder objectEntryFolder, String actionId)
		throws InfoItemPermissionException {

		return _hasPermission(
			permissionChecker, objectEntryFolder.getObjectEntryFolderId(),
			actionId);
	}

	private boolean _hasPermission(
			PermissionChecker permissionChecker, long objectEntryFolderId,
			String actionId)
		throws InfoItemPermissionException {

		try {
			return _objectEntryFolderModelResourcePermission.contains(
				permissionChecker, objectEntryFolderId, actionId);
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(
				objectEntryFolderId, portalException);
		}
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectEntryFolder)"
	)
	private ModelResourcePermission<ObjectEntryFolder>
		_objectEntryFolderModelResourcePermission;

}