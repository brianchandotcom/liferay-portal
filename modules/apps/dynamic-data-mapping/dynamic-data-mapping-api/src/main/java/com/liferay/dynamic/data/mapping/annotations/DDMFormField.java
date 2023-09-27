/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.annotations;

import com.liferay.petra.string.StringPool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Marcellus Tavares
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DDMFormField {

	public String dataType() default StringPool.BLANK;

	public DDMFormFieldProperty[] ddmFormFieldProperties() default {};

	public String label() default StringPool.BLANK;

	public String name() default StringPool.BLANK;

	public String[] optionLabels() default {};

	public String[] optionValues() default {};

	public String predefinedValue() default StringPool.BLANK;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #ddmFormFieldProperties()}
	 */
	@Deprecated
	public String[] properties() default {};

	public boolean required() default false;

	public String tip() default StringPool.BLANK;

	public String type() default StringPool.BLANK;

	public String validationErrorMessage() default StringPool.BLANK;

	public String validationExpression() default StringPool.BLANK;

	public String validationExpressionName() default StringPool.BLANK;

	public String validationParameter() default StringPool.BLANK;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMFormRule}
	 */
	@Deprecated
	public String visibilityExpression() default StringPool.BLANK;

}