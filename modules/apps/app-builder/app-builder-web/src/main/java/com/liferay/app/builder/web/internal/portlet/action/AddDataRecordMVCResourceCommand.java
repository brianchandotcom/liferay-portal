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

package com.liferay.app.builder.web.internal.portlet.action;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordResource;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.Serializable;

import java.util.Optional;

import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"app.builder.app.scope=" + AppBuilderAppConstants.SCOPE_STANDARD,
		"mvc.command.name=/app_builder/add_data_record"
	},
	service = MVCResourceCommand.class
)
public class AddDataRecordMVCResourceCommand
	extends BaseAppBuilderMVCResourceCommand<DataRecord> {

	@Override
	protected Optional<DataRecord> doTransactionalCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AppBuilderAppVersion appBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				ParamUtil.getLong(resourceRequest, "appBuilderAppId"));

		DataRecordResource dataRecordResource = DataRecordResource.builder(
		).user(
			themeDisplay.getUser()
		).build();

		DataRecord dataRecord = dataRecordResource.postDataDefinitionDataRecord(
			appBuilderAppVersion.getDdmStructureId(),
			DataRecord.toDTO(
				ParamUtil.getString(resourceRequest, "dataRecord")));

		_appBuilderAppDataRecordLinkLocalService.addAppBuilderAppDataRecordLink(
			themeDisplay.getScopeGroupId(), appBuilderAppVersion.getCompanyId(),
			appBuilderAppVersion.getAppBuilderAppId(),
			appBuilderAppVersion.getAppBuilderAppVersionId(),
			dataRecord.getId());

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(resourceResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL(
			_portal.getPortletId(resourceRequest));

		portletURL.setParameter("mvcPath", "/edit_entry.jsp");
		portletURL.setParameter(
			"dataRecordId", String.valueOf(dataRecord.getId()));

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
			themeDisplay.getUserId(),
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()),
			dataRecord.getId(),
			_ddlRecordLocalService.getDDLRecord(dataRecord.getId()),
			new ServiceContext(),
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_URL, portletURL.toString()
			).build());

		return Optional.of(dataRecord);
	}

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Reference
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private Portal _portal;

}