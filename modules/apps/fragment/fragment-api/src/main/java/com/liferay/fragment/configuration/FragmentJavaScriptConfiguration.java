/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Pavel Savinov
 */
@ExtendedObjectClassDefinition(
	category = "page-fragments",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.fragment.configuration.FragmentJavaScriptConfiguration",
	localization = "content/Language", name = "javascript-configuration-name"
)
@ProviderType
public interface FragmentJavaScriptConfiguration {

	@Meta.AD(
		deflt = "true", description = "javascript-module-enabled-description",
		name = "javascript-module-enabled", required = false
	)
	public boolean javaScriptModuleEnabled();

}