/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.security.permission.resource;

import com.liferay.dynamic.data.lists.constants.DDLConstants;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet",
	service = ModelResourcePermission.class
)
public class DDLRecordSetModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<DDLRecordSet> {

	@Override
	protected ModelResourcePermission<DDLRecordSet>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			DDLRecordSet.class, DDLRecordSet::getRecordSetId,
			_ddlRecordSetLocalService::getDDLRecordSet,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				(permissionChecker, name, recordSet, actionId) -> {
					if (recordSet.getScope() !=
							DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS) {

						return null;
					}

					return _stagingPermission.hasPermission(
						permissionChecker, recordSet.getGroupId(), name,
						recordSet.getRecordSetId(),
						DDLPortletKeys.DYNAMIC_DATA_LISTS, actionId);
				}));
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference(target = "(resource.name=" + DDLConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private StagingPermission _stagingPermission;

}