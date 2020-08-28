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

package com.liferay.app.builder.workflow.web.internal.portlet.action.test;

import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.web.internal.portlet.test.BaseAppBuilderPortletTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AddAppBuilderAppMVCResourceCommandTest
	extends BaseAppBuilderPortletTestCase {

	@Test
	public void testAddAppWorkflow() throws Exception {
		App app = addApp();

		Assert.assertNotNull(app);
		Assert.assertEquals("1.0", app.getVersion());

		AppWorkflow appWorkflow = getAppWorkflow(app);

		Assert.assertNotNull(appWorkflow);
		Assert.assertEquals("1.0", appWorkflow.getAppVersion());
	}

	@Test
	public void testAddAppWorkflowWithDuplicateTasks() throws Exception {
		AppWorkflow appWorkflow = createAppWorkflow();

		appWorkflow.setAppWorkflowTasks(
			ArrayUtil.append(
				appWorkflow.getAppWorkflowTasks(),
				appWorkflow.getAppWorkflowTasks()));

		Assert.assertNull(addApp(appWorkflow));
	}

}