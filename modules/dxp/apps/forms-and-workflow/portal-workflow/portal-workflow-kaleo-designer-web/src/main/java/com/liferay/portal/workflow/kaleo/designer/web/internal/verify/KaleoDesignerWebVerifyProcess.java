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

package com.liferay.portal.workflow.kaleo.designer.web.internal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.portal.workflow.kaleo.designer.web",
	service = VerifyProcess.class
)
public class KaleoDesignerWebVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyKaleoDefinitionVersions();
	}

	protected ServiceContext getServiceContext() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();

			serviceContext.setAddGuestPermissions(true);
			serviceContext.setAddGroupPermissions(true);
		}

		return serviceContext;
	}

	protected void verifyKaleoDefinitionVersions() throws PortalException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				_kaleoDefinitionVersionLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				new ActionableDynamicQuery.PerformActionMethod() {

					@Override
					public void performAction(Object object)
						throws PortalException {

						KaleoDefinitionVersion kaleoDefinitionVersion =
							(KaleoDefinitionVersion)object;

						verifyKaleoDefinitionVersions(kaleoDefinitionVersion);
					}

				});

			actionableDynamicQuery.performActions();
		}
	}

	protected void verifyKaleoDefinitionVersions(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		Role role = _roleLocalService.getRole(
			kaleoDefinitionVersion.getCompanyId(), RoleConstants.OWNER);

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				kaleoDefinitionVersion.getCompanyId(),
				KaleoDefinitionVersion.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(
					kaleoDefinitionVersion.getKaleoDefinitionVersionId()),
				role.getRoleId());

		if (resourcePermission == null) {
			ServiceContext serviceContext = getServiceContext();

			_resourceLocalService.addModelResources(
				kaleoDefinitionVersion, serviceContext);
		}
	}

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}