/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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