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

import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;
import com.liferay.portal.util.MailServiceTestUtil;

/**
 * @author Manuel de la Peña
 * @author Roberto Díaz
 */
public class MailExecutionTestListener extends AbstractExecutionTestListener {

	@Override
	public void runAfterTest(TestContext testContext) {
		MailServiceTestUtil.stop();
	}

	@Override
	public void runBeforeTest(TestContext testContext) {
		MailServiceTestUtil.start();
	}

}