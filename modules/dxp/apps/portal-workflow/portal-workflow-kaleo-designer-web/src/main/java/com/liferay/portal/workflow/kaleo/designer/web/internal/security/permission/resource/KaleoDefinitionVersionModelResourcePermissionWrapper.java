/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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