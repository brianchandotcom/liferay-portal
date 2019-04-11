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

package com.liferay.portal.configuration.metatype.annotations;

import aQute.bnd.annotation.xml.XMLAttribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alejandro Tardín
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@XMLAttribute(
	embedIn = "*", namespace = ExtendedAttributeDefinition.XML_NAMESPACE,
	prefix = ExtendedAttributeDefinition.XML_ATTRIBUTE_PREFIX
)
public @interface ExtendedAttributeDefinition {

	public static final String XML_ATTRIBUTE_PREFIX = "cf";

	public static final String XML_NAMESPACE =
		"http://www.liferay.com/xsd/liferay-configuration-admin_1_0_0.xsd";

	public String[] descriptionArguments() default {};

	public String[] nameArguments() default {};

	public boolean requiredInput() default false;

}