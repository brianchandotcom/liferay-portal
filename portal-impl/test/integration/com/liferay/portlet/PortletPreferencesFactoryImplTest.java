/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PortletPreferencesFactoryImplTest {

	@Test
	public void testBlankPreference() throws Exception {
		String expectedXML =
			"<portlet-preferences><preference><name>name</name><value>" +
				"</value></preference></portlet-preferences>";

		PortletPreferencesImpl portletPreferencesImpl =
			new PortletPreferencesImpl();

		portletPreferencesImpl.setValue("name", "");

		String actualXML = PortletPreferencesFactoryUtil.toXML(
			portletPreferencesImpl);

		Assert.assertEquals(expectedXML, actualXML);

		portletPreferencesImpl = deserialize(expectedXML);

		Map<String, Preference> preferencesMap =
			portletPreferencesImpl.getPreferences();

		Assert.assertEquals(1, preferencesMap.size());

		Preference preference = preferencesMap.values().iterator().next();

		Assert.assertEquals("name", preference.getName());

		String[] values = preference.getValues();

		Assert.assertEquals(1, values.length);
		Assert.assertEquals("", values[0]);
	}

	@Test
	public void testComplexPortletPreferences() throws Exception {
		PortletPreferencesImpl portletPreferencesImpl =
			new PortletPreferencesImpl();

		// Blank

		portletPreferencesImpl.setValue("", "");

		// Empty

		portletPreferencesImpl.setValues("name1", new String[0]);

		// Multiple

		portletPreferencesImpl.setValues("name2", new String[] {"", "value1"});

		// Read only

		Preference preference = new Preference(
			"name3", new String[] {"value2", "value3"}, true);

		Map<String, Preference> preferencesMap =
			portletPreferencesImpl.getPreferences();

		preferencesMap.put("name3", preference);

		String actualXML = PortletPreferencesFactoryUtil.toXML(
			portletPreferencesImpl);

		portletPreferencesImpl = deserialize(actualXML);

		preferencesMap = portletPreferencesImpl.getPreferences();

		Assert.assertEquals(4, preferencesMap.size());

		// Blank

		preference = preferencesMap.get("");

		Assert.assertNotNull(preference);
		Assert.assertEquals("", preference.getName());

		String[] values = preference.getValues();

		Assert.assertEquals(1, values.length);
		Assert.assertEquals("", values[0]);
		Assert.assertFalse(preference.isReadOnly());

		// Empty

		preference = preferencesMap.get("name1");

		Assert.assertNotNull(preference);
		Assert.assertEquals("name1", preference.getName());
		values = preference.getValues();
		Assert.assertEquals(0, values.length);
		Assert.assertFalse(preference.isReadOnly());

		// Multiple

		preference = preferencesMap.get("name2");

		Assert.assertNotNull(preference);
		Assert.assertEquals("name2", preference.getName());

		values = preference.getValues();

		Assert.assertEquals(2, values.length);
		Assert.assertEquals("", values[0]);
		Assert.assertEquals("value1", values[1]);
		Assert.assertFalse(preference.isReadOnly());

		// Read only

		preference = preferencesMap.get("name3");

		Assert.assertNotNull(preference);
		Assert.assertEquals("name3", preference.getName());

		values = preference.getValues();

		Assert.assertEquals(2, values.length);
		Assert.assertEquals("value2", values[0]);
		Assert.assertEquals("value3", values[1]);
		Assert.assertTrue(preference.isReadOnly());

	}

	@Test
	public void testEmptyPortletPreferences() throws SystemException {
		String expectedXML = "<portlet-preferences></portlet-preferences>";

		PortletPreferencesImpl portletPreferencesImpl =
			new PortletPreferencesImpl();

		String actualXML = PortletPreferencesFactoryUtil.toXML(
			portletPreferencesImpl);

		Assert.assertEquals(expectedXML, actualXML);

		portletPreferencesImpl =
			(PortletPreferencesImpl)
				PortletPreferencesFactoryUtil.fromDefaultXML(expectedXML);

		Map<String, Preference> preferencesMap =
			portletPreferencesImpl.getPreferences();

		Assert.assertEquals(0, preferencesMap.size());
	}

	@Test
	public void testEmptyPreference() throws Exception {
		String expectedXML =
			"<portlet-preferences><preference><name>name</name></preference>" +
				"</portlet-preferences>";

		PortletPreferencesImpl portletPreferencesImpl =
			new PortletPreferencesImpl();

		portletPreferencesImpl.setValues("name", new String[0]);

		String actualXML = PortletPreferencesFactoryUtil.toXML(
			portletPreferencesImpl);

		Assert.assertEquals(expectedXML, actualXML);

		portletPreferencesImpl = deserialize(expectedXML);

		Map<String, Preference> preferencesMap =
			portletPreferencesImpl.getPreferences();

		Assert.assertEquals(1, preferencesMap.size());

		Preference preference = preferencesMap.values().iterator().next();

		Assert.assertEquals("name", preference.getName());
		Assert.assertEquals(0, preference.getValues().length);
		Assert.assertFalse(preference.isReadOnly());
	}

	@Test
	public void testMultiplePreferences() throws Exception {
		String expectedXML =
			"<portlet-preferences><preference><name>name</name><value>value1" +
				"</value><value>value2</value></preference>" +
					"</portlet-preferences>";

		PortletPreferencesImpl portletPreferencesImpl =
			new PortletPreferencesImpl();

		String[] values = {"value1", "value2"};

		portletPreferencesImpl.setValues("name", values);

		String actualXML = PortletPreferencesFactoryUtil.toXML(
			portletPreferencesImpl);

		Assert.assertEquals(expectedXML, actualXML);

		portletPreferencesImpl = deserialize(expectedXML);

		Map<String, Preference> preferencesMap =
			portletPreferencesImpl.getPreferences();

		Assert.assertEquals(1, preferencesMap.size());

		Preference preference = preferencesMap.values().iterator().next();

		Assert.assertEquals("name", preference.getName());

		values = preference.getValues();

		Assert.assertEquals(2, values.length);
		Assert.assertEquals("value1", values[0]);
		Assert.assertEquals("value2", values[1]);
	}

	@Test
	public void testSinglePreference() throws Exception {
		String expectedXML =
			"<portlet-preferences><preference><name>name</name><value>value" +
				"</value></preference></portlet-preferences>";

		PortletPreferencesImpl portletPreferencesImpl =
			new PortletPreferencesImpl();

		portletPreferencesImpl.setValue("name", "value");

		String actualXML = PortletPreferencesFactoryUtil.toXML(
			portletPreferencesImpl);

		Assert.assertEquals(expectedXML, actualXML);

		portletPreferencesImpl = deserialize(expectedXML);

		Map<String, Preference> preferencesMap =
			portletPreferencesImpl.getPreferences();

		Assert.assertEquals(1, preferencesMap.size());

		Preference preference = preferencesMap.values().iterator().next();

		Assert.assertEquals("name", preference.getName());

		String[] values = preference.getValues();

		Assert.assertEquals(1, values.length);
		Assert.assertEquals("value", values[0]);
	}

	protected PortletPreferencesImpl deserialize(String xml) throws Exception {
		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)
				PortletPreferencesFactoryUtil.fromDefaultXML(xml);

		return portletPreferencesImpl;
	}

}