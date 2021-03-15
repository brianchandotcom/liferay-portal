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

package com.liferay.talend.properties.resource;

import com.liferay.talend.BasePropertiesTestCase;

import org.junit.Assert;
import org.junit.Test;

import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Igor Beslic
 */
public class ResourcePropertiesTest extends BasePropertiesTestCase {

	@Test
	public void testValidateOpenAPIModule() throws Exception {
		ComponentProperties componentProperties =
			getDefaultInitializedComponentPropertiesInstance(
				LiferayResourceProperties.class);

		LiferayResourceProperties liferayResourceProperties =
			(LiferayResourceProperties)componentProperties;

		StringProperty endpointStringProperty =
			liferayResourceProperties.endpoint;

		endpointStringProperty.setValue("/test/by-externalReferenceCode");

		_assertEndpointUrlEquals(
			"/o/headless-liferay/v1.0/test/by-externalReferenceCode",
			"/headless-liferay/v1.0", liferayResourceProperties);

		_assertEndpointUrlEquals(
			"/o/headless-liferay/v1.0/test/by-externalReferenceCode",
			"/headless-liferay/v1.0/", liferayResourceProperties);

		_assertEndpointUrlEquals(
			"/o/headless-liferay/v1.0/test/by-externalReferenceCode",
			"headless-liferay/v1.0", liferayResourceProperties);

		_assertEndpointUrlEquals(
			"/o/headless-liferay/v1.0/test/by-externalReferenceCode",
			"headless-liferay/v1.0/", liferayResourceProperties);
	}

	private void _assertEndpointUrlEquals(
		String expected, String openAPIModule,
		LiferayResourceProperties liferayResourceProperties) {

		StringProperty openAPIModuleStringProperty =
			liferayResourceProperties.openAPIModule;

		openAPIModuleStringProperty.setValue(openAPIModule);

		liferayResourceProperties.validateOpenAPIModule();

		Assert.assertEquals(
			expected, liferayResourceProperties.getEndpointUrl());
	}

}