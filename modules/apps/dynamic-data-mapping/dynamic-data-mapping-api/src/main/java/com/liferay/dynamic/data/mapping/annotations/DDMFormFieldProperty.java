/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.annotations;

import com.liferay.petra.string.StringPool;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rodrigo Paulino
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DDMFormFieldProperty {

	public String name() default StringPool.BLANK;

	public String[] value() default {};

}