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

package com.liferay.portal.workflow.web.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.adapter.StagedGroupedWorkflowDefinitionLink;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.xml.Element;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedGroupedWorkflowDefinitionLinkStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedGroupedWorkflowDefinitionLink> {

	public static final String[] CLASS_NAMES = {
		StagedGroupedWorkflowDefinitionLink.class.getName()
	};

	@Override
	public void deleteStagedModel(
			StagedGroupedWorkflowDefinitionLink
				stagedGroupedWorkflowDefinitionLink)
		throws PortalException {

		_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
			stagedGroupedWorkflowDefinitionLink);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {
	}

	@Override
	public List<StagedGroupedWorkflowDefinitionLink>
		fetchStagedModelsByUuidAndCompanyId(String uuid, long companyId) {

		return Collections.emptyList();
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedGroupedWorkflowDefinitionLink
				stagedGroupedWorkflowDefinitionLink)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			stagedGroupedWorkflowDefinitionLink);

		element.addAttribute(
			"display-name",
			stagedGroupedWorkflowDefinitionLink.getWorkflowDefinitionName());
		element.addAttribute(
			"referrer-class-pk",
			String.valueOf(stagedGroupedWorkflowDefinitionLink.getClassPK()));
		element.addAttribute(
			"referrer-class-name",
			String.valueOf(stagedGroupedWorkflowDefinitionLink.getClassName()));
		element.addAttribute(
			"type-pk",
			String.valueOf(stagedGroupedWorkflowDefinitionLink.getTypePK()));
		element.addAttribute(
			"version",
			String.valueOf(
				stagedGroupedWorkflowDefinitionLink.
					getWorkflowDefinitionVersion()));
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedGroupedWorkflowDefinitionLink
				stagedGroupedWorkflowDefinitionLink)
		throws Exception {
	}

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}