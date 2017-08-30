/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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