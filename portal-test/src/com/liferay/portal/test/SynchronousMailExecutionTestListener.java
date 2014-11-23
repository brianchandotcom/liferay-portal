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

package com.liferay.portal.test;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.test.TestContext;
import com.liferay.portal.util.test.MailServiceTestUtil;

/**
 * @author Manuel de la Peña
 * @author Roberto Díaz
 */
public class SynchronousMailExecutionTestListener
	extends SynchronousDestinationExecutionTestListener {

	@Override
	public void runAfterClass(TestContext testContext) {
		super.runAfterClass(testContext);

		classSyncHandler.restorePreviousSync();

		MailServiceTestUtil.stop();
	}

	@Override
	public void runAfterTest(TestContext testContext) {
		super.runAfterTest(testContext);

		MailServiceTestUtil.clearMessages();
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		super.runBeforeClass(testContext);

		MailServiceTestUtil.start();

		classSyncHandler.replaceDestination(DestinationNames.MAIL);
	}

}