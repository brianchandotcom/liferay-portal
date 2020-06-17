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

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordResource;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Rafael Praxedes
 */
public class AddDataRecordMVCResourceCommand extends BaseMVCResourceCommand {

	public AddDataRecordMVCResourceCommand(
		AppBuilderAppDataRecordLinkLocalService
			appBuilderAppDataRecordLinkLocalService,
		AppBuilderAppLocalService appBuilderAppLocalService,
		DDLRecordLocalService ddlRecordLocalService) {

		_appBuilderAppDataRecordLinkLocalService =
			appBuilderAppDataRecordLinkLocalService;
		_appBuilderAppLocalService = appBuilderAppLocalService;
		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_addDataRecord(resourceRequest);

					return null;
				});
		}
		catch (Throwable throwable) {
			if (throwable instanceof PortletException) {
				throw (PortletException)throwable;
			}

			throw new PortletException(throwable);
		}
	}

	private void _addDataRecord(ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(
				ParamUtil.getLong(resourceRequest, "appId"));

		DataRecordResource dataRecordResource = DataRecordResource.builder(
		).user(
			themeDisplay.getUser()
		).build();

		DataRecord dataRecord = dataRecordResource.postDataDefinitionDataRecord(
			appBuilderApp.getDdmStructureId(),
			DataRecord.toDTO(
				ParamUtil.getString(resourceRequest, "dataRecord")));

		_appBuilderAppDataRecordLinkLocalService.addAppBuilderAppDataRecordLink(
			appBuilderApp.getCompanyId(), appBuilderApp.getAppBuilderAppId(),
			dataRecord.getId());

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
			themeDisplay.getUserId(), AppBuilderApp.class.getName(),
			dataRecord.getId(),
			_ddlRecordLocalService.getDDLRecord(dataRecord.getId()),
			new ServiceContext());
	}

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

	private final AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;
	private final AppBuilderAppLocalService _appBuilderAppLocalService;
	private final DDLRecordLocalService _ddlRecordLocalService;

}