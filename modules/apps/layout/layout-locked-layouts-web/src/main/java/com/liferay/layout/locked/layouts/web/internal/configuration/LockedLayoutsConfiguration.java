/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Lourdes Fernández Besada
 */
@ExtendedObjectClassDefinition(
	category = "pages", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.layout.locked.layouts.web.internal.configuration.LockedLayoutsConfiguration",
	localization = "content/Language", name = "locked-pages-configuration-name"
)
public interface LockedLayoutsConfiguration {

	@Meta.AD(
		deflt = "true", name = "allow-automatic-unlocking-process",
		required = false
	)
	public boolean allowAutomaticUnlockingProcess();

	@Meta.AD(
		deflt = "5", max = "99999", min = "1", name = "time-without-autosave",
		required = false
	)
	public int timeWithoutAutosave();

}