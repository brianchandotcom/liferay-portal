/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener;

import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.sharing.service.SharingEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ModelListener.class)
public class ObjectEntryFolderModelListener
	extends BaseModelListener<ObjectEntryFolder> {

	@Override
	public void onAfterCreate(ObjectEntryFolder objectEntryFolder)
		throws ModelListenerException {

		if (!FeatureFlagManagerUtil.isEnabled(
				objectEntryFolder.getCompanyId(), "LPD-17564")) {

			return;
		}

		try {
			Role role = _getOrAddCMSAdministratorRoleAndPermissions(
				objectEntryFolder.getCompanyId(),
				objectEntryFolder.getUserId());

			_resourcePermissionLocalService.setResourcePermissions(
				objectEntryFolder.getCompanyId(),
				ObjectEntryFolder.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntryFolder.getObjectEntryFolderId()),
				role.getRoleId(),
				TransformUtil.transformToArray(
					_resourceActionLocalService.getResourceActions(
						ObjectEntryFolder.class.getName()),
					ResourceAction::getActionId, String.class));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterRemove(ObjectEntryFolder objectEntryFolder)
		throws ModelListenerException {

		if (!FeatureFlagManagerUtil.isEnabled(
				objectEntryFolder.getCompanyId(), "LPD-17564")) {

			return;
		}

		_sharingEntryLocalService.deleteSharingEntries(
			_portal.getClassNameId(ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId());
	}

	private Role _getOrAddCMSAdministratorRoleAndPermissions(
			long companyId, long userId)
		throws Exception {

		String name = RoleConstants.CMS_ADMINISTRATOR;

		Role role = _roleLocalService.fetchRole(companyId, name);

		if (role != null) {
			return role;
		}

		return _roleLocalService.addRole(
			null, userId, null, 0, name, null, null, RoleConstants.TYPE_REGULAR,
			null, null);
	}

	@Reference
	private Portal _portal;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}