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

package com.liferay.portlet;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jorge Ferrer
 */
public class CompanyPortletSettingsTest extends BasePortletSettingsTestCase {

	@Before
	public void setUp() throws Exception {
		portletSettings = new CompanyPortletSettings(getSettings());
	}

	@Test
	public void testGetDefaultFromProperties() {
		setPortalProperties(TEST_PROPERTY_NAME, TEST_PROPERTY_VALUE);

		String value = portletSettings.getValue(TEST_PROPERTY_NAME, null);

		Assert.assertEquals(TEST_PROPERTY_VALUE, value);
	}

	@Test
	public void testSetValueWithProperty() {
		setPortalProperties(TEST_PROPERTY_NAME, TEST_PROPERTY_VALUE);

		portletSettings.setValue(TEST_PROPERTY_NAME, "newValue");

		String value = portletSettings.getValue(TEST_PROPERTY_NAME, null);

		Assert.assertEquals("newValue", value);
	}

	protected void setPortalProperties(String name, String value) {
		Properties properties = new Properties();

		properties.setProperty(name, value);

		CompanyPortletSettings companyPortletSettings =
			(CompanyPortletSettings)portletSettings;

		companyPortletSettings.setPortalProperties(properties);
	}

}