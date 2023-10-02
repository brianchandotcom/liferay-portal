/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Mikel Lorza
 */
@ExtendedObjectClassDefinition(
	category = "pages", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.layout.configuration.LockedLayoutsGroupConfiguration",
	localization = "content/Language",
	name = "locked-layouts-group-configuration-name"
)
public interface LockedLayoutsGroupConfiguration {

	@Meta.AD(
		deflt = "true", name = "allow-automatic-unlocking-process",
		required = false
	)
	public boolean allowAutomaticUnlockingProcess();

	@Meta.AD(
		deflt = "5", max = "99999", min = "1", name = "autosave-minutes",
		required = false
	)
	public int autosaveMinutes();

}