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
import com.liferay.app.builder.workflow.web.internal.portlet.test.BaseAppBuilderPortletTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;

import org.apache.log4j.Level;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class DeleteAppBuilderAppMVCResourceCommandTest
	extends BaseAppBuilderPortletTestCase {

	@Test
	public void testDeleteAppWorkflow() throws Exception {
		App app = addApp();

		Assert.assertNotNull(app);
		Assert.assertTrue(deleteApp(app));
	}

	@Test
	public void testDeleteAppWorkflowWithIncompleteInstance() throws Exception {
		App app = addApp();

		addDataRecord(app);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					ProxyMessageListener.class.getName(), Level.OFF)) {

			Assert.assertFalse(deleteApp(app));
		}
	}

}