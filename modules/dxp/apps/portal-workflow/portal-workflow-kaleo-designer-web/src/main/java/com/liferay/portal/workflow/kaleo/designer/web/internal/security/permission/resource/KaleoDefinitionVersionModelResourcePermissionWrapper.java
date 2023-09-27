/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.security.permission.resource;

import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDesignerPermission;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion",
	service = ModelResourcePermission.class
)
public class KaleoDefinitionVersionModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<KaleoDefinitionVersion> {

	@Override
	protected ModelResourcePermission<KaleoDefinitionVersion>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			KaleoDefinitionVersion.class,
			KaleoDefinitionVersion::getKaleoDefinitionVersionId,
			_kaleoDefinitionVersionLocalService::getKaleoDefinitionVersion,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
			});
	}

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference(
		target = "(resource.name=" + KaleoDesignerPermission.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}