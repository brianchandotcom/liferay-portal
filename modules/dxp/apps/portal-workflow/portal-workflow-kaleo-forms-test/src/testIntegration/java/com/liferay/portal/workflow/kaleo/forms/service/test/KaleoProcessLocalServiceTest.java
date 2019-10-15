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

package com.liferay.portal.workflow.kaleo.forms.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.forms.test.util.KaleoProcessTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
@Sync
public class KaleoProcessLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddKaleoProcess() throws Exception {
		int initialCount =
			KaleoProcessLocalServiceUtil.getKaleoProcessesCount();

		KaleoProcessTestUtil.addKaleoProcess(KaleoProcess.class.getName());

		int actualCount = KaleoProcessLocalServiceUtil.getKaleoProcessesCount();

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testUpdateKaleoProcess() throws Exception {
		int initialCount =
			KaleoProcessLocalServiceUtil.getKaleoProcessesCount();

		KaleoProcess kaleoProcess = KaleoProcessTestUtil.addKaleoProcess(
			KaleoProcess.class.getName());

		KaleoProcessTestUtil.updateKaleoProcess(
			kaleoProcess, KaleoProcess.class.getName());

		int actualCount = KaleoProcessLocalServiceUtil.getKaleoProcessesCount();

		Assert.assertEquals(initialCount + 1, actualCount);
	}

}