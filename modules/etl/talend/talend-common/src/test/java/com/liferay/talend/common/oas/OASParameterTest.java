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

package com.liferay.talend.common.oas;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class OASParameterTest {

	@Test
	public void testConstructor() {
		OASParameter oasParameter = new OASParameter("testParameter", "query");

		Assert.assertEquals(
			"OpenAPI Specification parameter name value", "testParameter",
			oasParameter.getName());

		Assert.assertFalse(
			"OpenAPI Specification parameter is required",
			oasParameter.isRequired());

		Assert.assertFalse(
			"OpenAPI Specification parameter is path",
			oasParameter.isLocationPath());

		oasParameter.setRequired(true);

		Assert.assertTrue(
			"OpenAPI Specification parameter is required",
			oasParameter.isRequired());

		oasParameter.setLocation(OASParameter.Location.PATH);

		Assert.assertTrue(
			"OpenAPI Specification parameter is path",
			oasParameter.isLocationPath());
	}

	@Test
	public void testLocation() {
		OASParameter oasParameter = new OASParameter("testParameter", "path");

		Assert.assertEquals(
			"OpenAPI parameter has location in path",
			OASParameter.Location.PATH, oasParameter.getLocation());

		Assert.assertTrue(oasParameter.isLocationPath());

		oasParameter = new OASParameter("testParameter", "query");

		Assert.assertEquals(
			"OpenAPI parameter has location in query",
			OASParameter.Location.QUERY, oasParameter.getLocation());

		Assert.assertFalse(oasParameter.isLocationPath());
	}

}