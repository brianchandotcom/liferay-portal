/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.metatype.annotations;

import aQute.bnd.annotation.xml.XMLAttribute;

import com.liferay.petra.string.StringPool;

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

	public String featureFlagKey() default StringPool.BLANK;

	public String[] nameArguments() default {};

	public boolean requiredInput() default false;

	public String visibilityControllerKey() default "";

}