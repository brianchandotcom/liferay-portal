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

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = {
		"javax.portlet.name=" + AppBuilderPortletKeys.APPS,
		"mvc.command.name=/app_builder/delete_workflow_app"
	},
	service = MVCResourceCommand.class
)
public class DeleteAppBuilderAppMVCResourceCommand
	extends BaseAppBuilderAppMVCResourceCommand<Void> {

	@Override
	protected Optional<Void> doTransactionalCommand(
			ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AppResource appResource = AppResource.builder(
		).user(
			themeDisplay.getUser()
		).build();

		appResource.deleteApp(
			ParamUtil.getLong(resourceRequest, "appBuilderAppId"));

		AppWorkflowResource appWorkflowResource = AppWorkflowResource.builder(
		).user(
			themeDisplay.getUser()
		).build();

		appWorkflowResource.deleteAppWorkflow(
			ParamUtil.getLong(resourceRequest, "appBuilderAppId"));

		return Optional.empty();
	}

}