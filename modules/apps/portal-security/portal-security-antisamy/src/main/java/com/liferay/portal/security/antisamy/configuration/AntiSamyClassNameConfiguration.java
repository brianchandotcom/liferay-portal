/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.antisamy.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marta Medio
 */
@ExtendedObjectClassDefinition(
	category = "security-tools", factoryInstanceLabelAttribute = "className",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.security.antisamy.configuration.AntiSamyClassNameConfiguration",
	localization = "content/Language",
	name = "anti-samy-class-name-configuration-name"
)
public interface AntiSamyClassNameConfiguration {

	@Meta.AD(deflt = "", name = "class-name")
	public String className();

	@Meta.AD(
		deflt = "/META-INF/resources/sanitizer-configuration.xml",
		name = "configuration-file-url"
	)
	public String configurationFileURL();

}