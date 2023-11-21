/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.security.permission.resource;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeModel;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType",
	service = ModelResourcePermission.class
)
public class DLFileEntryTypeModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<DLFileEntryType> {

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			DLFileEntryType dlFileEntryType, String actionId)
		throws PortalException {

		String structureModelResourceName =
			_ddmPermissionSupport.getStructureModelResourceName(
				DLFileEntryMetadata.class.getName());

		if (permissionChecker.hasOwnerPermission(
				dlFileEntryType.getCompanyId(), structureModelResourceName,
				dlFileEntryType.getDataDefinitionId(),
				dlFileEntryType.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				dlFileEntryType.getGroupId(), structureModelResourceName,
				dlFileEntryType.getDataDefinitionId(), actionId)) {

			return true;
		}

		return super.contains(permissionChecker, dlFileEntryType, actionId);
	}

	@Override
	protected ModelResourcePermission<DLFileEntryType>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			DLFileEntryType.class, DLFileEntryTypeModel::getFileEntryTypeId,
			_dlFileEntryTypeLocalService::getDLFileEntryType,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				new StagedModelPermissionLogic<>(
					_stagingPermission, DLPortletKeys.DOCUMENT_LIBRARY,
					DLFileEntryTypeModel::getFileEntryTypeId)));
	}

	@Reference
	private DDMPermissionSupport _ddmPermissionSupport;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference(target = "(resource.name=" + DLConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private StagingPermission _stagingPermission;

}