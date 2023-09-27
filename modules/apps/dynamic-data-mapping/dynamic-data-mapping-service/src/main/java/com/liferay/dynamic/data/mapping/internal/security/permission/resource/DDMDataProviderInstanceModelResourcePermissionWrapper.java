/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.security.permission.resource;

import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance",
	service = ModelResourcePermission.class
)
public class DDMDataProviderInstanceModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<DDMDataProviderInstance> {

	@Override
	protected ModelResourcePermission<DDMDataProviderInstance>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			DDMDataProviderInstance.class,
			DDMDataProviderInstance::getDataProviderInstanceId,
			_ddmDataProviderInstanceLocalService::getDDMDataProviderInstance,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
			});
	}

	@Reference
	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;

	@Reference(target = "(resource.name=" + DDMConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}