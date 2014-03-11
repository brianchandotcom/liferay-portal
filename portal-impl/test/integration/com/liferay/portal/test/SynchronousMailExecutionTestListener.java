/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.util.MailServiceTestUtil;

/**
 * @author Manuel de la Peña
 * @author Roberto Díaz
 */
public class SynchronousMailExecutionTestListener
	extends SynchronousDestinationExecutionTestListener {

	@Override
	public void runAfterTest(TestContext testContext) {
		MailServiceTestUtil.stop();

		super.runAfterTest(testContext);
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		super.runBeforeClass(testContext);

		classSyncHandler.replaceDestination(DestinationNames.MAIL);
	}

	@Override
	public void runBeforeTest(TestContext testContext) {
		super.runBeforeTest(testContext);

		MailServiceTestUtil.start();

		methodSyncHandler.replaceDestination(DestinationNames.MAIL);
	}

}