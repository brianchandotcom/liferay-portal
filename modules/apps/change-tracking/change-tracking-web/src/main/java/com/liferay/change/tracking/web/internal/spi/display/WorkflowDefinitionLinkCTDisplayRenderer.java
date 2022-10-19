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

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class WorkflowDefinitionLinkCTDisplayRenderer
	extends BaseCTDisplayRenderer<WorkflowDefinitionLink> {

	@Override
	public Class<WorkflowDefinitionLink> getModelClass() {
		return WorkflowDefinitionLink.class;
	}

	@Override
	public String getTitle(
			Locale locale, WorkflowDefinitionLink workflowDefinitionLink)
		throws PortalException {

		String scope = null;

		if (workflowDefinitionLink.getGroupId() == 0) {
			Company company = _companyLocalService.getCompany(
				workflowDefinitionLink.getCompanyId());

			scope = company.getName();
		}
		else {
			Group group = _groupLocalService.getGroup(
				workflowDefinitionLink.getGroupId());

			scope = group.getDescriptiveName(locale);
		}

		return _language.format(
			locale, "x-for-x-for-x",
			new String[] {
				workflowDefinitionLink.getWorkflowDefinitionName(),
				"model.resource." + workflowDefinitionLink.getClassName(), scope
			});
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

}