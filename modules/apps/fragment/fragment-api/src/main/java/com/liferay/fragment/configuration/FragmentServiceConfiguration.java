/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
	category = "page-fragments", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.fragment.configuration.FragmentServiceConfiguration",
	localization = "content/Language", name = "fragment-configuration-name"
)
@ProviderType
public interface FragmentServiceConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "propagate-fragment-changes-automatically-description",
		name = "propagate-fragment-changes-automatically", required = false
	)
	public boolean propagateChanges();

	@Meta.AD(
		deflt = "true",
		description = "propagate-contributed-fragment-changes-automatically-description",
		name = "propagate-contributed-fragment-changes-automatically",
		required = false
	)
	public boolean propagateContributedFragmentChanges();

}