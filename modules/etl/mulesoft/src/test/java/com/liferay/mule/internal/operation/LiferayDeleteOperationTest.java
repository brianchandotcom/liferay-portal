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
public class LiferayDeleteOperationTest extends BaseLiferayOperationTestCase {

	@Test
	public void testDeleteOperation() throws Exception {
		String payload = getPayload("test-delete-product-flow");

		Assert.assertTrue(payload.isEmpty());
	}

	@Override
	protected String getConfigFile() {
		return "test-delete-operation.xml";
	}

}