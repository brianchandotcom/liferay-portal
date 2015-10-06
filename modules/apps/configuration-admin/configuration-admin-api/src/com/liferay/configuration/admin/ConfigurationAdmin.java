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

package com.liferay.configuration.admin;

import aQute.bnd.annotation.xml.XMLAttribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Iván Zaera
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@XMLAttribute(
	embedIn = ConfigurationAdmin.EMBED_IN, namespace = ConfigurationAdmin.NS,
	prefix = ConfigurationAdmin.PREFIX
)
public @interface ConfigurationAdmin {

	public static final String EMBED_IN = "*";

	public static final String NS =
		"http://www.liferay.com/xsd/meta-type-hints_7_0_0";

	public static final String PREFIX = "mh";

	public String category() default "";

}