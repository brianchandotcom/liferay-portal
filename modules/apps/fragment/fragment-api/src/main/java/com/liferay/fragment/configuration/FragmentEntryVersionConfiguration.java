/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Georgel Pop
 */
@ExtendedObjectClassDefinition(
	category = "page-fragments",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.fragment.configuration.FragmentEntryVersionConfiguration",
	localization = "content/Language",
	name = "fragment-entry-version-configuration-name"
)
public interface FragmentEntryVersionConfiguration {

	@Meta.AD(
		deflt = "", description = "clean-up-cron-expression-description",
		name = "clean-up-cron-expression", required = false
	)
	public String cleanUpCronExpression();

	@Meta.AD(
		deflt = "10",
		description = "maximum-versions-per-fragment-entry-description",
		name = "maximum-versions-per-fragment-entry", required = false
	)
	public int maximumVersionsPerFragmentEntry();

}