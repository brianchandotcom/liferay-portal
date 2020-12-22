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

package com.liferay.app.builder.workflow.web.internal.servlet.taglib;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.constants.AppBuilderWebKeys;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;

import java.io.IOException;

import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = DynamicInclude.class)
public class AppBuilderEditEntryPostJSPDynamicInclude
	extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		long dataRecordId = ParamUtil.getLong(
			httpServletRequest, "dataRecordId");

		if (dataRecordId == 0) {
			return;
		}

		AppBuilderApp appBuilderApp =
			(AppBuilderApp)httpServletRequest.getAttribute(
				AppBuilderWebKeys.APP);

		if ((appBuilderApp == null) ||
			!Objects.equals(
				appBuilderApp.getScope(),
				AppBuilderAppConstants.SCOPE_WORKFLOW)) {

			return;
		}

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			_appBuilderAppDataRecordLinkLocalService.
				fetchDDLRecordAppBuilderAppDataRecordLink(dataRecordId);

		if (appBuilderAppDataRecordLink == null) {
			return;
		}

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				appBuilderAppDataRecordLink.getCompanyId(),
				appBuilderAppDataRecordLink.getGroupId(),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				dataRecordId);

		httpServletRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_INSTANCE_ID,
			workflowInstanceLink.getWorkflowInstanceId());

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.app.builder.web#/edit_app_entry.jsp#post");
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/edit_app_entry.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.app.builder.workflow.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppBuilderEditEntryPostJSPDynamicInclude.class);

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}