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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class VerifyUUIDTest extends BaseVerifyTestCase {

	@Test
	public void testVerifyModel() {
		testVerifyModel("Layout", "plid");
	}

	@Test
	public void testVerifyModelWithUnknownPKColumnName() {
		testVerifyModel("Layout", _UNKNOWN);
	}

	@Test
	public void testVerifyUnknownModelWithUnknownPKColumnName() {
		testVerifyModel(_UNKNOWN, _UNKNOWN);
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyUUID();
	}

	protected void testVerifyModel(String model, String pkColumnName) {
		try {
			VerifyUUID.verifyModel(model, pkColumnName);
		}
		catch (Exception e) {
			boolean exceptionTypeCorrect = false;

			if ((e instanceof SQLException) || (e instanceof VerifyException)) {
				exceptionTypeCorrect = true;
			}

			Assert.assertTrue(exceptionTypeCorrect);
		}
	}

	private static final String _UNKNOWN = "Unknown";

}