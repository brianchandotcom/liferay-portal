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

package com.liferay.portal.workflow.kaleo.designer.web.internal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
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
		_verifyKaleoDefinitionVersions();
	}

	private ServiceContext _getServiceContext() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
		}

		return serviceContext;
	}

	private void _verifyKaleoDefinitionVersions() throws PortalException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				_kaleoDefinitionVersionLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				(Object object) -> {
					KaleoDefinitionVersion kaleoDefinitionVersion =
						(KaleoDefinitionVersion)object;

					_verifyKaleoDefinitionVersions(kaleoDefinitionVersion);
				});

			actionableDynamicQuery.performActions();
		}
	}

	private void _verifyKaleoDefinitionVersions(
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
			_resourceLocalService.addModelResources(
				kaleoDefinitionVersion, _getServiceContext());
		}
	}

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.portal.workflow.kaleo.designer.web)(release.schema.version=1.0.2))"
	)
	private Release _release;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}