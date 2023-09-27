/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.opensaml.saml.saml2.core.Attribute;

/**
 * @author Mika Koivisto
 */
public class SamlUtilTest extends BaseSamlTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testGetAttributesMap() {
		List<Attribute> attributes = new ArrayList<>();

		attributes.add(
			OpenSamlUtil.buildAttribute("emailAddress", "test@liferay.com"));
		attributes.add(
			OpenSamlUtil.buildAttribute("firstName", "TestFirstName"));
		attributes.add(OpenSamlUtil.buildAttribute("lastName", "TestLastName"));
		attributes.add(OpenSamlUtil.buildAttribute("screenName", "test"));

		Map<String, List<Serializable>> attributesMap =
			SamlUtil.getAttributesMap(attributes, new Properties());

		Assert.assertEquals(
			"test@liferay.com",
			SamlUtil.getValueAsString("emailAddress", attributesMap));
		Assert.assertEquals(
			"TestFirstName",
			SamlUtil.getValueAsString("firstName", attributesMap));
		Assert.assertEquals(
			"TestLastName",
			SamlUtil.getValueAsString("lastName", attributesMap));
		Assert.assertEquals(
			"test", SamlUtil.getValueAsString("screenName", attributesMap));
	}

	@Test
	public void testGetAttributesMapEachAttributeMappedExactlyOnce() {
		List<Attribute> attributes = new ArrayList<>();

		attributes.add(
			OpenSamlUtil.buildAttribute("attribute1", "attribute1Value"));
		attributes.add(
			OpenSamlUtil.buildAttribute("attribute2", "attribute2Value"));

		Properties attributeMappingsProperties = new Properties();

		attributeMappingsProperties.put("attribute1", "attribute3");
		attributeMappingsProperties.put("attribute2", "attribute1");

		Map<String, List<Serializable>> attributesMap =
			SamlUtil.getAttributesMap(attributes, attributeMappingsProperties);

		List<Serializable> serializables = attributesMap.get("attribute1");

		Assert.assertArrayEquals(
			"Attribute which is explicitly mapped should not be implicitly " +
				"mapped",
			new String[] {"attribute2Value"}, serializables.toArray());

		Assert.assertEquals(
			"attribute1Value",
			SamlUtil.getValueAsString("attribute3", attributesMap));
	}

	@Test
	public void testGetAttributesMapExplicitMappingHasHigherPriority() {
		List<Attribute> attributes = new ArrayList<>();

		attributes.add(
			OpenSamlUtil.buildAttribute("uuid", "testImplicitMapped"));
		attributes.add(
			OpenSamlUtil.buildAttribute("UUID", "testExplicitMapped"));

		Properties attributeMappingsProperties = new Properties();

		attributeMappingsProperties.put("UUID", "uuid");

		Map<String, List<Serializable>> attributesMap =
			SamlUtil.getAttributesMap(attributes, attributeMappingsProperties);

		List<Serializable> serializables = attributesMap.get("uuid");

		Assert.assertArrayEquals(
			"Explicitly mapped attribute should take priority over " +
				"implicitly mapped ones",
			new String[] {"testExplicitMapped", "testImplicitMapped"},
			serializables.toArray());

		Assert.assertEquals(
			"testExplicitMapped",
			SamlUtil.getValueAsString("uuid", attributesMap));
	}

	@Test
	public void testGetAttributesMapFallbackToImplicitMapping() {
		List<Attribute> attributes = new ArrayList<>();

		attributes.add(
			OpenSamlUtil.buildAttribute("uuid", "testImplicitMapped"));

		Properties attributeMappingsProperties = new Properties();

		attributeMappingsProperties.put("UUID", "uuid");

		Assert.assertEquals(
			"testImplicitMapped",
			SamlUtil.getValueAsString(
				"uuid",
				SamlUtil.getAttributesMap(
					attributes, attributeMappingsProperties)));
	}

	@Test
	public void testGetAttributesMapWithMapping() {
		List<Attribute> attributes = new ArrayList<>();

		attributes.add(
			OpenSamlUtil.buildAttribute("givenName", "TestFirstName"));
		attributes.add(OpenSamlUtil.buildAttribute("mail", "test@liferay.com"));
		attributes.add(OpenSamlUtil.buildAttribute("sn", "TestLastName"));
		attributes.add(OpenSamlUtil.buildAttribute("title", "TestJobTitle"));
		attributes.add(
			OpenSamlUtil.buildAttribute("userPrincipalName", "test"));

		Properties attributeMappingsProperties = new Properties();

		attributeMappingsProperties.put("givenName", "firstName");
		attributeMappingsProperties.put("mail", "emailAddress");
		attributeMappingsProperties.put("sn", "lastName");
		attributeMappingsProperties.put("userPrincipalName", "screenName");

		Map<String, List<Serializable>> attributesMap =
			SamlUtil.getAttributesMap(attributes, attributeMappingsProperties);

		Assert.assertEquals(
			"test@liferay.com",
			SamlUtil.getValueAsString("emailAddress", attributesMap));
		Assert.assertEquals(
			"TestFirstName",
			SamlUtil.getValueAsString("firstName", attributesMap));
		Assert.assertEquals(
			"TestLastName",
			SamlUtil.getValueAsString("lastName", attributesMap));
		Assert.assertEquals(
			"test", SamlUtil.getValueAsString("screenName", attributesMap));
		Assert.assertEquals(
			"TestJobTitle", SamlUtil.getValueAsString("title", attributesMap));
	}

	@Test
	public void testGetAttributesMapWithMappingAndFriendlyName() {
		List<Attribute> attributes = new ArrayList<>();

		attributes.add(
			OpenSamlUtil.buildAttribute(
				"givenName", "firstName", Attribute.UNSPECIFIED,
				"TestFirstName"));
		attributes.add(
			OpenSamlUtil.buildAttribute(
				"mail", "emailAddress", Attribute.UNSPECIFIED,
				"test@liferay.com"));
		attributes.add(
			OpenSamlUtil.buildAttribute(
				"sn", "lastName", Attribute.UNSPECIFIED, "TestLastName"));
		attributes.add(
			OpenSamlUtil.buildAttribute(
				"title", "jobTitle", Attribute.UNSPECIFIED, "TestJobTitle"));
		attributes.add(
			OpenSamlUtil.buildAttribute(
				"userPrincipalName", "screenName", Attribute.UNSPECIFIED,
				"test"));

		DateTime modifiedDate = new DateTime(DateTimeZone.UTC);

		attributes.add(
			OpenSamlUtil.buildAttribute(
				"whenChanged", "modifiedDate", Attribute.UNSPECIFIED,
				modifiedDate));

		Properties attributeMappingsProperties = new Properties();

		attributeMappingsProperties.put("emailAddress", "emailAddress");
		attributeMappingsProperties.put("firstName", "firstName");
		attributeMappingsProperties.put("lastName", "lastName");
		attributeMappingsProperties.put("screenName", "screenName");
		attributeMappingsProperties.put("whenChanged", "modifiedDate");

		Map<String, List<Serializable>> attributesMap =
			SamlUtil.getAttributesMap(attributes, attributeMappingsProperties);

		Assert.assertEquals(
			"test@liferay.com",
			SamlUtil.getValueAsString("emailAddress", attributesMap));
		Assert.assertEquals(
			"TestFirstName",
			SamlUtil.getValueAsString("firstName", attributesMap));
		Assert.assertEquals(
			"TestLastName",
			SamlUtil.getValueAsString("lastName", attributesMap));
		Assert.assertNotNull(
			SamlUtil.getValueAsDate("modifiedDate", attributesMap));
		Assert.assertTrue(
			modifiedDate.isEqual(
				SamlUtil.getValueAsDateTime("modifiedDate", attributesMap)));
		Assert.assertEquals(
			"test", SamlUtil.getValueAsString("screenName", attributesMap));
		Assert.assertEquals(
			"TestJobTitle", SamlUtil.getValueAsString("title", attributesMap));
	}

}