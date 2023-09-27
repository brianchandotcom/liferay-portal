/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.metatype.definitions.test;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeService;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.FrameworkUtil;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class MetatypeAttributeDefinitionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDefaultValue() {
		ExtendedMetaTypeInformation extendedMetaTypeInformation =
			_extendedMetaTypeService.getMetaTypeInformation(
				FrameworkUtil.getBundle(MetatypeAttributeDefinitionTest.class));

		ExtendedObjectClassDefinition extendedObjectClassDefinition =
			extendedMetaTypeInformation.getObjectClassDefinition(
				"com.liferay.portal.configuration.metatype.definitions.test." +
					"TestConfiguration",
				null);

		Assert.assertArrayEquals(
			"Default value of single valued string attribute containing " +
				"non-escaped delimiter should be considered valid.",
			new String[] {"a=b,c=d\\,e=f"},
			_getDefaultValue(
				extendedObjectClassDefinition,
				"testStringEscapeSingleValuedAttribute"));

		Assert.assertArrayEquals(
			new String[] {"a=b", "c=d,e=f"},
			_getDefaultValue(
				extendedObjectClassDefinition,
				"testStringEscapeMultiValuedAttribute"));
	}

	private String[] _getDefaultValue(
		ExtendedObjectClassDefinition extendedObjectClassDefinition,
		String id) {

		AttributeDefinition[] attributeDefinitions =
			extendedObjectClassDefinition.getAttributeDefinitions(
				ObjectClassDefinition.ALL);

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			if (id.equals(attributeDefinition.getID())) {
				return attributeDefinition.getDefaultValue();
			}
		}

		return null;
	}

	@Inject
	private ExtendedMetaTypeService _extendedMetaTypeService;

	@Meta.OCD(
		id = "com.liferay.portal.configuration.metatype.definitions.test.TestConfiguration"
	)
	private interface TestConfiguration {

		@Meta.AD(deflt = "a=b,c=d\\,e=f", required = false)
		public String[] testStringEscapeMultiValuedAttribute();

		@Meta.AD(deflt = "a=b,c=d\\,e=f", required = false)
		public String testStringEscapeSingleValuedAttribute();

	}

}