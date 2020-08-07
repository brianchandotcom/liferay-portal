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

package com.liferay.app.builder.workflow.web.internal.portlet.tab;

import com.liferay.app.builder.portlet.tab.AppBuilderAppsPortletTab;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, property = "app.builder.apps.tabs.name=workflow",
	service = AppBuilderAppsPortletTab.class
)
public class WorkflowAppBuilderAppsPortletTab
	implements AppBuilderAppsPortletTab {

	@Override
	public void deleteApp(long appBuilderAppId, User user) throws Exception {
		AppWorkflowResource appWorkflowResource = AppWorkflowResource.builder(
		).user(
			user
		).build();

		appWorkflowResource.deleteAppWorkflow(appBuilderAppId);
	}

	@Override
	public String getEditEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-workflow-web/js/pages/apps/edit/EditApp.es");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"workflow-powered");
	}

	@Override
	public String getListEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-workflow-web/js/pages/apps/ListApps.es");
	}

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

}