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

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTab;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTabContext;
import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.workflow.web.internal.portlet.test.BaseAppBuilderPortletTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.portal.kernel.test.rule.DataGuard;
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
public class WorkflowAppBuilderAppPortletTabTest
	extends BaseAppBuilderPortletTestCase {

	@Test
	public void testGetAppBuilderAppPortletTabContext() throws Exception {
		App app = addApp();

		AppBuilderApp appBuilderApp =
			appBuilderAppLocalService.getAppBuilderApp(app.getId());

		AppBuilderAppPortletTabContext appBuilderAppPortletTabContext =
			_appBuilderAppPortletTab.getAppBuilderAppPortletTabContext(
				appBuilderApp, 0);

		_assertAppBuilderAppPortletTabContext(
			appBuilderAppPortletTabContext, app.getDataLayoutId(), false);

		DataRecord dataRecord = addDataRecord(app);

		appBuilderAppPortletTabContext =
			_appBuilderAppPortletTab.getAppBuilderAppPortletTabContext(
				appBuilderApp, dataRecord.getId());

		_assertAppBuilderAppPortletTabContext(
			appBuilderAppPortletTabContext, app.getDataLayoutId(), false);

		updateDataRecord(app, dataRecord.getId());

		appBuilderAppPortletTabContext =
			_appBuilderAppPortletTab.getAppBuilderAppPortletTabContext(
				appBuilderApp, dataRecord.getId());

		_assertAppBuilderAppPortletTabContext(
			appBuilderAppPortletTabContext, app.getDataLayoutId(), true);
	}

	@Test
	public void testGetEditEntryPoint() throws Exception {
		String editEntryPoint = _appBuilderAppPortletTab.getEditEntryPoint();

		Assert.assertNotNull(editEntryPoint);
		Assert.assertTrue(
			editEntryPoint,
			editEntryPoint.startsWith("app-builder-workflow-web"));
		Assert.assertTrue(
			editEntryPoint,
			editEntryPoint.endsWith("/js/pages/entry/EditEntry.es"));
	}

	@Test
	public void testGetListEntryPoint() throws Exception {
		String listEntryPoint = _appBuilderAppPortletTab.getListEntryPoint();

		Assert.assertNotNull(listEntryPoint);
		Assert.assertTrue(
			listEntryPoint,
			listEntryPoint.startsWith("app-builder-workflow-web"));
		Assert.assertTrue(
			listEntryPoint,
			listEntryPoint.endsWith("/js/pages/entry/ListEntries.es"));
	}

	@Test
	public void testGetViewEntryPoint() throws Exception {
		String viewEntryPoint = _appBuilderAppPortletTab.getViewEntryPoint();

		Assert.assertNotNull(viewEntryPoint);
		Assert.assertTrue(
			viewEntryPoint,
			viewEntryPoint.startsWith("app-builder-workflow-web"));
		Assert.assertTrue(
			viewEntryPoint,
			viewEntryPoint.endsWith("/js/pages/entry/ViewEntry.es"));
	}

	private void _assertAppBuilderAppPortletTabContext(
		AppBuilderAppPortletTabContext appBuilderAppPortletTabContext,
		Long dataLayoutId, boolean readOnly) {

		List<Long> dataLayoutIds =
			appBuilderAppPortletTabContext.getDataLayoutIds();

		Assert.assertEquals(dataLayoutIds.toString(), 1, dataLayoutIds.size());
		Assert.assertEquals(dataLayoutId, dataLayoutIds.get(0));
		Assert.assertEquals(
			readOnly,
			appBuilderAppPortletTabContext.isReadOnly(dataLayoutIds.get(0)));
	}

	@Inject(filter = "app.builder.app.tab.name=workflow")
	private AppBuilderAppPortletTab _appBuilderAppPortletTab;

}