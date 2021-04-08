/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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