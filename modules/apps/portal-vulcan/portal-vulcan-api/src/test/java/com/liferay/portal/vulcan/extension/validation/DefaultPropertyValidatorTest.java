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

package com.liferay.portal.vulcan.extension.validation;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carlos Correa
 */
public class DefaultPropertyValidatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidateBigDecimal() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			"field", PropertyDefinition.PropertyType.BIG_DECIMAL,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(
			propertyDefinition, new BigDecimal(Long.MAX_VALUE));
	}

	@Test
	public void testValidateBoolean() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			"field", PropertyDefinition.PropertyType.BOOLEAN,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(propertyDefinition, true);
	}

	@Test
	public void testValidateDateTime() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			String.class, "field", PropertyDefinition.PropertyType.DATE_TIME,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(
			propertyDefinition, "2021-07-04T12:12:02Z");
	}

	@Test
	public void testValidateDecimal() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			"field", PropertyDefinition.PropertyType.DECIMAL,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(propertyDefinition, Float.MAX_VALUE);
	}

	@Test
	public void testValidateDouble() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			"field", PropertyDefinition.PropertyType.DOUBLE,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(propertyDefinition, Double.MAX_VALUE);
	}

	@Test
	public void testValidateInteger() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			"field", PropertyDefinition.PropertyType.INTEGER,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(
			propertyDefinition, Integer.MAX_VALUE);
	}

	@Test(expected = ValidationException.class)
	public void testValidateInvalidBigDecimal() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			"field", PropertyDefinition.PropertyType.BIG_DECIMAL,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(
			propertyDefinition, "This is a invalid value");
	}

	@Test(expected = ValidationException.class)
	public void testValidateInvalidDateTime() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			String.class, "field", PropertyDefinition.PropertyType.DATE_TIME,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(
			propertyDefinition, "2021-07-04T12:12:02");
	}

	@Test(expected = ValidationException.class)
	public void testValidateInvalidMultipleElements() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			TestInvalidClass.class, "field",
			PropertyDefinition.PropertyType.MULTIPLE_ELEMENT,
			defaultPropertyValidator, false);

		List<Map<String, Object>> multipleElements = Arrays.asList(
			new HashMapBuilder<>().<String, Object>put(
				"field1", "field1Text1"
			).<String, Object>put(
				"field2", "field1Text2"
			).<String, Object>put(
				"field3", 1L
			).build(),
			new HashMapBuilder<>().<String, Object>put(
				"field1", "field2Text1"
			).<String, Object>put(
				"field2", "field2Text2"
			).<String, Object>put(
				"field3", 2L
			).build());

		defaultPropertyValidator.validate(propertyDefinition, multipleElements);
	}

	@Test(expected = ValidationException.class)
	public void testValidateInvalidSingleElement() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			TestInvalidClass.class, "field",
			PropertyDefinition.PropertyType.SINGLE_ELEMENT,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(
			propertyDefinition,
			new HashMapBuilder<>().<String, Object>put(
				"field1", "field1Text1"
			).<String, Object>put(
				"field2", "field1Text2"
			).<String, Object>put(
				"field3", 1L
			).build());
	}

	@Test(expected = ValidationException.class)
	public void testValidateInvalidValue() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			"field", PropertyDefinition.PropertyType.BOOLEAN,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(
			propertyDefinition, "This is a invalid value");
	}

	@Test
	public void testValidateLong() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			"field", PropertyDefinition.PropertyType.LONG,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(propertyDefinition, Long.MAX_VALUE);
	}

	@Test
	public void testValidateMultipleElements() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			TestClass.class, "field",
			PropertyDefinition.PropertyType.MULTIPLE_ELEMENT,
			defaultPropertyValidator, false);

		Map<String, Object>[] multipleElements = new Map[] {
			new HashMapBuilder<>().<String, Object>put(
				"field1", "field1Text1"
			).<String, Object>put(
				"field2", "field1Text2"
			).<String, Object>put(
				"field3", 1L
			).build(),
			new HashMapBuilder<>().<String, Object>put(
				"field1", "field2Text1"
			).<String, Object>put(
				"field2", "field2Text2"
			).<String, Object>put(
				"field3", 2L
			).build()
		};

		defaultPropertyValidator.validate(propertyDefinition, multipleElements);
	}

	@Test(expected = ValidationException.class)
	public void testValidateNullClass() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			null, "field", PropertyDefinition.PropertyType.BOOLEAN,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(propertyDefinition, true);
	}

	@Test
	public void testValidateSingleElement() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			TestClass.class, "field",
			PropertyDefinition.PropertyType.SINGLE_ELEMENT,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(
			propertyDefinition,
			new HashMapBuilder<>().<String, Object>put(
				"field1", "field1Text1"
			).<String, Object>put(
				"field2", "field1Text2"
			).<String, Object>put(
				"field3", 1L
			).build());
	}

	@Test
	public void testValidateText() {
		DefaultPropertyValidator defaultPropertyValidator =
			new DefaultPropertyValidator();

		PropertyDefinition propertyDefinition = new PropertyDefinition(
			"field", PropertyDefinition.PropertyType.TEXT,
			defaultPropertyValidator, false);

		defaultPropertyValidator.validate(
			propertyDefinition, "This is a valid text");
	}

	public static class TestClass implements Serializable {

		public String getField1() {
			return _field1;
		}

		public String getField2() {
			return _field2;
		}

		public Long getField3() {
			return _field3;
		}

		public void setField1(String field1) {
			_field1 = field1;
		}

		public void setField2(String field2) {
			_field2 = field2;
		}

		public void setField3(Long field3) {
			_field3 = field3;
		}

		private String _field1;
		private String _field2;
		private Long _field3;

	}

	public static class TestInvalidClass implements Serializable {
	}

}