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

package com.liferay.talend.properties.parameters;

import com.liferay.talend.common.oas.OASParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class RequestParameterPropertiesTest {

	@Test
	public void testAddProperties() {
		RequestParameterProperties requestParameterProperties =
			new RequestParameterProperties("request");

		requestParameterProperties.setupProperties();

		requestParameterProperties.addParameters(_testOASParameters);

		Assert.assertFalse(
			"Request parameter properties is not empty",
			requestParameterProperties.isEmpty());

		List<RequestParameter> requestParameters =
			requestParameterProperties.getRequestParameters();

		Assert.assertEquals(
			"Request parameter properties size", _testOASParameters.size(),
			requestParameters.size());

		for (RequestParameter requestParameter : requestParameters) {
			_assertOASParameterExists(requestParameter);
		}

		requestParameterProperties.addParameters(_ignoredOASParameters);

		requestParameters = requestParameterProperties.getRequestParameters();

		Assert.assertEquals(
			"Request parameter properties size", _testOASParameters.size(),
			requestParameters.size());
	}

	@Test
	public void testConstructor() {
		RequestParameterProperties requestParameterProperties =
			new RequestParameterProperties("request");

		requestParameterProperties.setupProperties();

		Assert.assertTrue(
			"Request parameter properties is empty",
			requestParameterProperties.isEmpty());

		Assert.assertEquals(
			"Request parameter properties name", "request",
			requestParameterProperties.getName());
	}

	@Test
	public void testRemoveProperties() {
		RequestParameterProperties requestParameterProperties =
			new RequestParameterProperties("request");

		requestParameterProperties.setupProperties();

		requestParameterProperties.addParameters(_testOASParameters);

		Assert.assertFalse(
			"Request parameter properties is not empty",
			requestParameterProperties.isEmpty());

		requestParameterProperties.removeAll();

		Assert.assertTrue(
			"Request parameter properties is empty after removeAll",
			requestParameterProperties.isEmpty());

		requestParameterProperties.addParameters(_testOASParameters);

		List<RequestParameter> requestParameters =
			requestParameterProperties.getRequestParameters();

		Assert.assertEquals(
			"Request parameter properties size after new addition",
			_testOASParameters.size(), requestParameters.size());
	}

	private void _assertOASParameterExists(RequestParameter requestParameter) {
		for (OASParameter oasParameter : _testOASParameters) {
			String oasParameterName = oasParameter.getName();

			if (oasParameter.isRequired()) {
				oasParameterName = oasParameterName + "*";
			}

			if (Objects.equals(requestParameter.getName(), oasParameterName)) {
				Assert.assertEquals(
					oasParameter.isLocationPath(),
					requestParameter.isPathLocation());

				return;
			}
		}

		throw new RuntimeException(
			"Request parameter not found in OAS parameters: " +
				requestParameter.getName());
	}

	private static final List<OASParameter> _ignoredOASParameters =
		new ArrayList<OASParameter>() {
			{
				add(new OASParameter("page", "query"));
				add(new OASParameter("pageSize", "query"));
			}
		};

	private static final List<OASParameter> _testOASParameters =
		new ArrayList<OASParameter>() {
			{
				add(new OASParameter("optionalQuery", "query"));
				add(new OASParameter("requiredPath", "path"));

				OASParameter oasParameter = new OASParameter(
					"requiredQuery", "query");

				oasParameter.setRequired(true);

				add(oasParameter);
			}
		};

}