/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask.constants;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Luis Ortiz
 */
public class BackgroundTaskConstantsTest {

	@Test
	public void testGetStatusCssClass() {
		Assert.assertEquals(
			"text-warning",
			BackgroundTaskConstants.getStatusCssClass(
				BackgroundTaskConstants.STATUS_COMPLETED_WITH_ERRORS));
		Assert.assertEquals(
			"text-warning",
			BackgroundTaskConstants.getStatusCssClass(
				BackgroundTaskConstants.STATUS_COMPLETED_WITH_WARNINGS));
		Assert.assertEquals(
			"text-danger",
			BackgroundTaskConstants.getStatusCssClass(
				BackgroundTaskConstants.STATUS_FAILED));
		Assert.assertEquals(
			"text-success",
			BackgroundTaskConstants.getStatusCssClass(
				BackgroundTaskConstants.STATUS_SUCCESSFUL));
	}

	@Test
	public void testGetStatusLabel() {
		Assert.assertEquals(
			"completed-with-errors",
			BackgroundTaskConstants.getStatusLabel(
				BackgroundTaskConstants.STATUS_COMPLETED_WITH_ERRORS));
		Assert.assertEquals(
			"completed-with-warnings",
			BackgroundTaskConstants.getStatusLabel(
				BackgroundTaskConstants.STATUS_COMPLETED_WITH_WARNINGS));
		Assert.assertEquals(
			"failed",
			BackgroundTaskConstants.getStatusLabel(
				BackgroundTaskConstants.STATUS_FAILED));
		Assert.assertEquals(
			"successful",
			BackgroundTaskConstants.getStatusLabel(
				BackgroundTaskConstants.STATUS_SUCCESSFUL));
	}

}