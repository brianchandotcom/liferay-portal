/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.graphql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Javier Gamarra
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
	{
		ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD,
		ElementType.TYPE, ElementType.TYPE_USE
	}
)
public @interface GraphQLField {

	public String description() default "";

	public boolean value() default true;

}