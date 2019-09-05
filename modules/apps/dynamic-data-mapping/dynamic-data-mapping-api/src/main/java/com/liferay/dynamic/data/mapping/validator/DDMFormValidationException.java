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

package com.liferay.dynamic.data.mapping.validator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMFormValidationException extends PortalException {

	public DDMFormValidationException() {
	}

	public DDMFormValidationException(String msg) {
		super(msg);
	}

	public DDMFormValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DDMFormValidationException(Throwable cause) {
		super(cause);
	}

	public static class MustNotDuplicateFieldName
		extends DDMFormValidationException {

		public MustNotDuplicateFieldName(String fieldName) {
			super(
				String.format(
					"The field name %s cannot be defined more than once",
					fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	public static class MustSetAvailableLocales
		extends DDMFormValidationException {

		public MustSetAvailableLocales() {
			super(
				"The available locales property was not set for the DDM form");
		}

	}

	public static class MustSetDefaultLocale
		extends DDMFormValidationException {

		public MustSetDefaultLocale() {
			super("The default locale property was not set for the DDM form");
		}

	}

	public static class MustSetDefaultLocaleAsAvailableLocale
		extends DDMFormValidationException {

		public MustSetDefaultLocaleAsAvailableLocale(Locale defaultLocale) {
			super(
				String.format(
					"The default locale %s must be set to a valid available " +
						"locale",
					defaultLocale));

			_defaultLocale = defaultLocale;
		}

		public Locale getDefaultLocale() {
			return _defaultLocale;
		}

		private final Locale _defaultLocale;

	}

	public static class MustSetFieldsForForm
		extends DDMFormValidationException {

		public MustSetFieldsForForm() {
			super("At least one field must be set");
		}

	}

	public static class MustSetFieldType extends DDMFormValidationException {

		public MustSetFieldType(String fieldName) {
			super(
				String.format(
					"The field type was never set for the DDM form field " +
						"with the field name %s",
					fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	public static class MustSetOptionsForField
		extends DDMFormValidationException {

		public MustSetOptionsForField(String fieldName) {
			super(
				String.format(
					"At least one option must be set for field %s", fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	public static class MustSetValidAvailableLocalesForProperty
		extends DDMFormValidationException {

		public MustSetValidAvailableLocalesForProperty(
			String fieldName, String property) {

			super(
				String.format(
					"Invalid available locales set for the property '%s' of " +
						"field name %s",
					property, fieldName));

			_fieldName = fieldName;
			_property = property;
		}

		public String getFieldName() {
			return _fieldName;
		}

		public String getProperty() {
			return _property;
		}

		private String _fieldName;
		private String _property;

	}

	public static class MustSetValidCharactersForFieldName
		extends DDMFormValidationException {

		public MustSetValidCharactersForFieldName(String fieldName) {
			super(
				String.format(
					"Invalid characters entered for field name %s", fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	public static class MustSetValidCharactersForFieldType
		extends DDMFormValidationException {

		public MustSetValidCharactersForFieldType(String fieldType) {
			super(
				String.format(
					"Invalid characters entered for field type %s", fieldType));

			_fieldType = fieldType;
		}

		public String getFieldType() {
			return _fieldType;
		}

		private final String _fieldType;

	}

	public static class MustSetValidDefaultLocaleForProperty
		extends DDMFormValidationException {

		public MustSetValidDefaultLocaleForProperty(
			String fieldName, String property) {

			super(
				String.format(
					"Invalid default locale set for the property '%s' of " +
						"field name %s",
					property, fieldName));

			_fieldName = fieldName;
			_property = property;
		}

		public String getFieldName() {
			return _fieldName;
		}

		public String getProperty() {
			return _property;
		}

		private String _fieldName;
		private String _property;

	}

	public static class MustSetValidFormRuleExpression
		extends DDMFormValidationException {

		public MustSetValidFormRuleExpression(
			String expressionType, String expression, Throwable cause) {

			super(
				String.format(
					"Invalid form rule %s expression set: \"%s\"",
					expressionType, expression),
				cause);

			_expression = expression;
		}

		public String getExpression() {
			return _expression;
		}

		private final String _expression;

	}

	public static class MustSetValidIndexType
		extends DDMFormValidationException {

		public MustSetValidIndexType(String fieldName) {
			super(
				String.format(
					"Invalid index type set for field %s", fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	public static class MustSetValidValidationExpression
		extends MustSetValidDDMFormFieldExpression {

		public MustSetValidValidationExpression(
			String fieldName, String expression) {

			super(fieldName, "validation", expression);
		}

		public String getValidationExpression() {
			return expression;
		}

		public String getValidationExpressionArgument() {
			Matcher matcher = _validationExpressionPattern.matcher(expression);

			if (matcher.find()) {
				return matcher.group(3);
			}

			return StringPool.BLANK;
		}

		private static final Pattern _validationExpressionPattern =
			Pattern.compile("(contains|match)\\((.+), \"(.+)\"\\)");

	}

	public static class MustSetValidVisibilityExpression
		extends MustSetValidDDMFormFieldExpression {

		public MustSetValidVisibilityExpression(
			String fieldName, String expression) {

			super(fieldName, "visibility", expression);
		}

		public String getVisibilityExpression() {
			return expression;
		}

	}

	private static class MustSetValidDDMFormFieldExpression
		extends DDMFormValidationException {

		public MustSetValidDDMFormFieldExpression(
			String fieldName, String expressionType, String expression) {

			super(
				String.format(
					"Invalid %s expression set for field %s: %s",
					expressionType, fieldName, expression));

			this.fieldName = fieldName;
			this.expression = expression;
		}

		public String getExpression() {
			return expression;
		}

		public String getFieldName() {
			return fieldName;
		}

		protected String expression;
		protected String fieldName;

	}

}