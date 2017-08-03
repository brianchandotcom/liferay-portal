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

package com.liferay.portal.workflow.kaleo.designer.internal.activator;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.designer.service.KaleoDraftDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.designer.util.KaleoDesignerUtil;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = KaleoDesignerServiceActivator.class)
public class KaleoDesignerServiceActivator {

	@Activate
	protected void activate() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			ServiceContext serviceContext = _createServiceContext(company);

			_addMissingWorkflowDraftDefinitions(company, serviceContext);
		}
	}

	private void _addMissingWorkflowDraftDefinitions(
			Company company, ServiceContext serviceContext)
		throws PortalException, WorkflowException {

		List<WorkflowDefinition> workflowDefinitions =
			_workflowDefinitionManager.getWorkflowDefinitions(
				company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			KaleoDesignerUtil.addMissingKaleoDraftDefinition(
				workflowDefinition.getName(), workflowDefinition.getVersion(),
				workflowDefinition.getTitle(), workflowDefinition.getContent(),
				serviceContext);
		}
	}

	private ServiceContext _createServiceContext(Company company)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(company.getCompanyId());
		serviceContext.setScopeGroupId(0);

		User user = company.getDefaultUser();

		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private KaleoDraftDefinitionLocalService _kaleoDraftDefinitionLocalService;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowDefinitionManager _workflowDefinitionManager;

}