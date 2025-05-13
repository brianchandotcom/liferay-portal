/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action;

import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Murilo Stodolni
 */
@Component(
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/get_object_definition_info"
	},
	service = MVCResourceCommand.class
)
public class GetObjectDefinitionInfoMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				ParamUtil.getLong(resourceRequest, "objectDefinitionId"));

		if (objectDefinition == null) {
			return;
		}

		String tableName = objectDefinition.getDBTableName();

		if (objectDefinition.isRootDescendantNode()) {
			objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectDefinition.getRootObjectDefinitionId());
		}

		boolean workflowSupported = _isWorkflowSupported(objectDefinition);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"isWorkflowSupported", workflowSupported
			).put(
				"tableName", tableName
			).put(
				"workflowDefinitionTitle",
				_getWorkflowDefinitionTitle(
					objectDefinition, resourceRequest, workflowSupported)
			));
	}

	private String _getWorkflowDefinitionTitle(
			ObjectDefinition objectDefinition, ResourceRequest resourceRequest,
			boolean workflowSupported)
		throws Exception {

		if (!workflowSupported) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId(),
				objectDefinition.getClassName(), 0, 0);

		if (workflowDefinitionLink == null) {
			return _language.get(themeDisplay.getLocale(), "no-workflow");
		}

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.fetchKaleoDefinition(
				workflowDefinitionLink.getWorkflowDefinitionName(),
				ServiceContextFactory.getInstance(resourceRequest));

		return kaleoDefinition.getTitle(themeDisplay.getLocale());
	}

	private boolean _isWorkflowSupported(ObjectDefinition objectDefinition) {
		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				objectDefinition.getClassName());

		if ((workflowHandler == null) || !workflowHandler.isVisible()) {
			return false;
		}

		return true;
	}

	@Reference
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}