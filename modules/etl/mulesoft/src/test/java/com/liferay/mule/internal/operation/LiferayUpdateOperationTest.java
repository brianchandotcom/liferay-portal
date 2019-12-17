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

package com.liferay.mule.internal.operation;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matija Petanjek
 */
public class LiferayUpdateOperationTest extends BaseLiferayOperationTestCase {

	@Test
	public void testUpdateOperation() throws Exception {
		String payload = getPayload("test-update-product-flow");

		Assert.assertTrue(
			payload.contains("\"en_US\" : \"Updated Test Product\""));
	}

	@Override
	protected String getConfigFile() {
		return "test-update-operation.xml";
	}

}