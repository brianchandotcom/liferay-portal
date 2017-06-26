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

package com.liferay.flags.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Peter Fellwock
 */
@ExtendedObjectClassDefinition(
	category = "collaboration",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.flags.configuration.FlagsConfiguration",
	localization = "content/Language", name = "flags-service-configuration-name"
)
public interface FlagsGroupServiceConfiguration {

	@Meta.AD(
		deflt = "com/liferay/flags/dependencies/email_flag_body.tmpl",
		required = false
	)
	public String emailBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailEntryAddedEnabled();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		required = false
	)
	public String emailFromName();

	@Meta.AD(
		deflt = "com/liferay/flags/dependencies/email_flag_subject.tmpl",
		required = false
	)
	public String emailSubject();

	@Meta.AD(deflt = "flase", required = false)
	public boolean guestUsersEnabled();

	@Meta.AD(
		deflt = "sexual-content|violent-or-repulsive-content|hateful-or-abusive-content|harmful-dangerous-acts|spam|infringes-my-rights",
		required = false
	)
	public String[] reasons();

}