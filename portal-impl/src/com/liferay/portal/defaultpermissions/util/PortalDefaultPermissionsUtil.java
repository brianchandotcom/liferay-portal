/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.util;

import com.liferay.portal.defaultpermissions.kernel.configuration.manager.PortalDefaultPermissionsConfigurationManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;

import java.util.Map;

/**
 * @author Stefano Motta
 */
public class PortalDefaultPermissionsUtil {

	public static void setModelDefaultPermissions(
			AuditedModel auditedModel, long companyId, long groupId,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, String[]> defaultPermissions =
			PortalDefaultPermissionsConfigurationManagerUtil.
				getDefaultPermissions(
					companyId, groupId, auditedModel.getModelClassName());

		if ((defaultPermissions == null) || defaultPermissions.isEmpty()) {
			return;
		}

		ModelPermissions modelPermissions =
			serviceContext.getModelPermissions();

		if (modelPermissions == null) {
			modelPermissions = ModelPermissionsFactory.create(
				auditedModel.getModelClassName());
		}

		for (Map.Entry<String, String[]> entry :
				defaultPermissions.entrySet()) {

			modelPermissions.addRolePermissions(
				entry.getKey(), entry.getValue());
		}

		serviceContext.setModelPermissions(modelPermissions);

		ResourceLocalServiceUtil.updateModelResources(
			auditedModel, serviceContext);
	}

}