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

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jorge Ferrer
 */
public abstract class BasePortletSettingsTestCase {

	@Test
	public void testGetInexistentValue() {
		String value = portletSettings.getValue("inexistentName", null);

		Assert.assertNull(value);
	}

	@Test
	public void testGetValidValue() {
		String value = portletSettings.getValue(TEST_SETTINGS_NAME, null);

		Assert.assertEquals(TEST_SETTINGS_VALUE, value);
	}

	@Test
	public void testSetExistentValue() {
		portletSettings.setValue(TEST_SETTINGS_NAME, "newValue");

		String value = portletSettings.getValue(TEST_SETTINGS_NAME, null);

		Assert.assertEquals("newValue", value);
	}

	@Test
	public void testSetInexistentValue() {
		portletSettings.setValue("inexistentName", "newValue");

		String value = portletSettings.getValue("inexistentName", null);

		Assert.assertEquals("newValue", value);
	}

	protected PortletPreferences getSettings() throws ReadOnlyException {
		PortletPreferences settings = new PortletPreferencesImpl();

		settings.setValue(TEST_SETTINGS_NAME, TEST_SETTINGS_VALUE);

		return settings;
	}

	protected static final String TEST_PROPERTY_NAME = "testPropertyName";

	protected static final String TEST_PROPERTY_VALUE = "testPropertyValue";

	protected static final String TEST_SETTINGS_NAME = "testSettingsName";

	protected static final String TEST_SETTINGS_VALUE = "testSettingsValue";

	protected PortletSettings portletSettings = null;

}