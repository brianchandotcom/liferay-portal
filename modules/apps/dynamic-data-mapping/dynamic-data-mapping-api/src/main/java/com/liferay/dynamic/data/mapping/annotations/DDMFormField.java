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

	public String label() default StringPool.BLANK;

	public String name() default StringPool.BLANK;

	public String[] optionLabels() default {};

	public String[] optionValues() default {};

	public String predefinedValue() default StringPool.BLANK;

	public String[] properties() default {};

	public boolean required() default false;

	public String tip() default StringPool.BLANK;

	public String type() default StringPool.BLANK;

	public String validationErrorMessage() default StringPool.BLANK;

	public String validationExpression() default StringPool.BLANK;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMFormRule}
	 */
	@Deprecated
	public String visibilityExpression() default StringPool.BLANK;

}