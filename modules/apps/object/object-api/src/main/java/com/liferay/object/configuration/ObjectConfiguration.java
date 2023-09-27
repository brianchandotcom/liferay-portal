/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Rodrigo Paulino
 */
@ExtendedObjectClassDefinition(
	category = "object", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.object.configuration.ObjectConfiguration",
	localization = "content/Language", name = "object-configuration-name"
)
public interface ObjectConfiguration {

	@Meta.AD(
		deflt = "100",
		description = "maximum-number-of-guest-user-object-entries-per-object-definition-help",
		name = "maximum-number-of-guest-user-object-entries-per-object-definition",
		required = false
	)
	public int maximumNumberOfGuestUserObjectEntriesPerObjectDefinition();

	@Meta.AD(
		deflt = "25", description = "maximum-file-size-for-guest-users-help",
		name = "maximum-file-size-for-guest-users", required = false
	)
	public int maximumFileSizeForGuestUsers();

}