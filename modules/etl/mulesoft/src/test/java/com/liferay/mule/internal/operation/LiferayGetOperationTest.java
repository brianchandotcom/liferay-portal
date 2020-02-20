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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matija Petanjek
 */
public class LiferayGetOperationTest extends BaseLiferayOperationTestCase {

	@Test
	public void testGetOperation() throws Exception {
		String payload = getPayload("test-get-products-flow");

		Matcher matcher = productPagePattern.matcher(payload);

		Assert.assertTrue(matcher.matches());
	}

	@Override
	protected String getConfigFile() {
		return "test-get-operation.xml";
	}

	private static final Pattern productPagePattern = Pattern.compile(
		"[\\s\\S]+\"items\"[\\s\\S]+\"active\"[\\s\\S]+\"catalogId\"[\\s\\S]+" +
			"\"name\"[\\s\\S]+\"productType\"[\\s\\S]+\"lastPage\"[\\s\\S]+" +
				"\"page\"[\\s\\S]+\"pageSize\"[\\s\\S]+\"totalCount\"" +
					"[\\s\\S]+");

}