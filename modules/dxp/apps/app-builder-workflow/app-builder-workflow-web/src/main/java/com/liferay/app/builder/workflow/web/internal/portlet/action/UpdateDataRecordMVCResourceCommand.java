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

package com.liferay.app.builder.workflow.web.internal.portlet.action;

import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordResource;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;

import java.io.Serializable;

import java.util.Map;
import java.util.Optional;

import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"app.builder.app.scope=workflow",
		"mvc.command.name=/app_builder/update_data_record"
	},
	service = MVCResourceCommand.class
)
public class UpdateDataRecordMVCResourceCommand
	extends BaseAppBuilderAppMVCResourceCommand<DataRecord> {

	@Override
	protected Optional<DataRecord> doTransactionalCommand(
			ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DataRecordResource dataRecordResource = DataRecordResource.builder(
		).user(
			themeDisplay.getUser()
		).build();

		DataRecord dataRecord = dataRecordResource.putDataRecord(
			ParamUtil.getLong(resourceRequest, "dataRecordId"),
			DataRecord.toDTO(
				ParamUtil.getString(resourceRequest, "dataRecord")));

		if (Validator.isNotNull(
				ParamUtil.getString(resourceRequest, "transitionName")) &&
			Validator.isNotNull(
				ParamUtil.getLong(resourceRequest, "workflowInstanceId"))) {

			WorkflowInstance workflowInstance =
				_workflowInstanceManager.getWorkflowInstance(
					themeDisplay.getCompanyId(),
					ParamUtil.getLong(resourceRequest, "workflowInstanceId"));

			Map<String, Serializable> workflowContext =
				workflowInstance.getWorkflowContext();

			workflowContext.put(
				WorkflowConstants.CONTEXT_USER_ID,
				String.valueOf(themeDisplay.getUserId()));

			_workflowInstanceManager.signalWorkflowInstance(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				ParamUtil.getLong(resourceRequest, "workflowInstanceId"),
				ParamUtil.getString(resourceRequest, "transitionName"),
				workflowContext);
		}

		return Optional.of(dataRecord);
	}

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}