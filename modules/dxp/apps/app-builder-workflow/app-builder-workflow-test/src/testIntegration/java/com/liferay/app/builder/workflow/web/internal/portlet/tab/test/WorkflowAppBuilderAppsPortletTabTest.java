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

package com.liferay.app.builder.workflow.web.internal.portlet.tab.test;

import com.liferay.app.builder.portlet.tab.AppBuilderAppsPortletTab;
import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.app.builder.workflow.service.AppBuilderWorkflowTaskLinkLocalService;
import com.liferay.app.builder.workflow.web.internal.portlet.test.BaseAppBuilderPortletTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowAppBuilderAppsPortletTabTest
	extends BaseAppBuilderPortletTestCase {

	@Test
	public void testDeleteApp() throws Exception {
		App app = addApp();

		_appBuilderAppsPortletTab.deleteApp(
			app.getId(), TestPropsValues.getUser());

		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks =
			_appBuilderWorkflowTaskLinkLocalService.
				getAppBuilderWorkflowTaskLinks(app.getId());

		Assert.assertEquals(
			appBuilderWorkflowTaskLinks.toString(), 0,
			appBuilderWorkflowTaskLinks.size());
	}

	@Test
	public void testGetEditEntryPoint() {
		String editEntryPoint = _appBuilderAppsPortletTab.getEditEntryPoint();

		Assert.assertNotNull(editEntryPoint);
		Assert.assertTrue(
			editEntryPoint,
			editEntryPoint.startsWith("app-builder-workflow-web"));
		Assert.assertTrue(
			editEntryPoint,
			editEntryPoint.endsWith("js/pages/apps/edit/EditApp.es"));
	}

	@Test
	public void testGetListEntryPoint() {
		String listEntryPoint = _appBuilderAppsPortletTab.getListEntryPoint();

		Assert.assertNotNull(listEntryPoint);
		Assert.assertTrue(
			listEntryPoint,
			listEntryPoint.startsWith("app-builder-workflow-web"));
		Assert.assertTrue(
			listEntryPoint,
			listEntryPoint.endsWith("/js/pages/apps/ListApps.es"));
	}

	@Inject(filter = "app.builder.apps.tabs.name=workflow")
	private AppBuilderAppsPortletTab _appBuilderAppsPortletTab;

	@Inject
	private AppBuilderWorkflowTaskLinkLocalService
		_appBuilderWorkflowTaskLinkLocalService;

}