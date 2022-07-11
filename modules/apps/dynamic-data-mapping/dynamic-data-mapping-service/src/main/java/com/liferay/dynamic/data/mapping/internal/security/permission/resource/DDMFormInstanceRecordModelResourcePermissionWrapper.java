/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.internal.security.permission.resource;

import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
	service = ModelResourcePermission.class
)
public class DDMFormInstanceRecordModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<DDMFormInstanceRecord> {

	@Override
	protected ModelResourcePermission<DDMFormInstanceRecord>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			DDMFormInstanceRecord.class,
			DDMFormInstanceRecord::getFormInstanceRecordId,
			_ddmFormInstanceRecordLocalService::getDDMFormInstanceRecord,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
				consumer.accept(
					new DDMFormInstanceRecordAutosavedModelResourcePermissionLogic());
				consumer.accept(
					new DDMFormInstanceRecordInheritanceModelResourcePermissionLogic());
			});
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstance)"
	)
	private ModelResourcePermission<DDMFormInstance>
		_ddmFormInstanceModelResourcePermission;

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Reference(target = "(resource.name=" + DDMConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	private class DDMFormInstanceRecordAutosavedModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<DDMFormInstanceRecord> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				DDMFormInstanceRecord formInstanceRecord, String actionId)
			throws PortalException {

			if (!actionId.equals(ActionKeys.UPDATE)) {
				return null;
			}

			if ((formInstanceRecord.getStatus() ==
					WorkflowConstants.STATUS_DRAFT) &&
				(formInstanceRecord.getUserId() ==
					permissionChecker.getUserId())) {

				return true;
			}

			return null;
		}

	}

	private class DDMFormInstanceRecordInheritanceModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<DDMFormInstanceRecord> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				DDMFormInstanceRecord formInstanceRecord, String actionId)
			throws PortalException {

			return _ddmFormInstanceModelResourcePermission.contains(
				permissionChecker, formInstanceRecord.getFormInstance(),
				actionId);
		}

	}

}