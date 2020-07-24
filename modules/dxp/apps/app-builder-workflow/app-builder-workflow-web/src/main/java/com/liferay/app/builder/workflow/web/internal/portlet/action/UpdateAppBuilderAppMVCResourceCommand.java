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
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;
import java.util.Optional;

import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = {
		"javax.portlet.name=" + AppBuilderPortletKeys.APPS,
		"mvc.command.name=/app_builder/update_workflow_app"
	},
	service = MVCResourceCommand.class
)
public class UpdateAppBuilderAppMVCResourceCommand
	extends BaseAppBuilderAppMVCResourceCommand<App> {

	@Override
	protected Optional<App> doTransactionalCommand(
			ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AppResource appResource = AppResource.builder(
		).user(
			themeDisplay.getUser()
		).build();

		App app = appResource.getApp(
			ParamUtil.getLong(resourceRequest, "appBuilderAppId"));

		String appVersion = app.getVersion();

		app = appResource.putApp(
			ParamUtil.getLong(resourceRequest, "appBuilderAppId"),
			App.toDTO(ParamUtil.getString(resourceRequest, "app")));

		AppWorkflowResource appWorkflowResource = AppWorkflowResource.builder(
		).user(
			themeDisplay.getUser()
		).build();

		AppWorkflow appWorkflow = AppWorkflow.toDTO(
			ParamUtil.getString(resourceRequest, "appWorkflow"));

		if (_isModifiedAppWorkflow(
				appWorkflowResource.getAppWorkflow(
					ParamUtil.getLong(resourceRequest, "appBuilderAppId")),
				appWorkflow)) {

			if (Objects.equals(app.getVersion(), appVersion)) {
				AppBuilderAppVersion appBuilderAppVersion =
					_appBuilderAppVersionLocalService.addAppBuilderAppVersion(
						app.getSiteId(), themeDisplay.getCompanyId(),
						themeDisplay.getUserId(), app.getId(),
						app.getDataRecordCollectionId(),
						app.getDataDefinitionId(), app.getDataLayoutId());

				app.setVersion(appBuilderAppVersion.getVersion());
			}

			appWorkflowResource.putAppWorkflow(app.getId(), appWorkflow);
		}

		return Optional.of(app);
	}

	private boolean _isModifiedAppWorkflow(
		AppWorkflow appWorkflow, AppWorkflow newAppWorkflow) {

		if (!Objects.deepEquals(
				appWorkflow.getAppWorkflowStates(),
				newAppWorkflow.getAppWorkflowStates())) {

			return true;
		}

		if (!Objects.deepEquals(
				appWorkflow.getAppWorkflowTasks(),
				newAppWorkflow.getAppWorkflowTasks())) {

			return true;
		}

		return false;
	}

	@Reference
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

}