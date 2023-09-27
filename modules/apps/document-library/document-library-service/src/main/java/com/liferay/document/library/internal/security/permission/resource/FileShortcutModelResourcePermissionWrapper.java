/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.security.permission.resource;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileShortcut",
	service = ModelResourcePermission.class
)
public class FileShortcutModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<FileShortcut> {

	@Override
	protected ModelResourcePermission<FileShortcut>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			FileShortcut.class, FileShortcut::getFileShortcutId,
			_dlAppLocalService::getFileShortcut, _portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				(permissionChecker, name, fileShortcut, actionId) ->
					fileShortcut.containsPermission(
						permissionChecker, actionId)));
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference(target = "(resource.name=" + DLConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}