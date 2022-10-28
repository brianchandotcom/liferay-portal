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

package com.liferay.account.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Pei-Jung Lan
 */
@ExtendedObjectClassDefinition(
	category = "accounts", scope = ExtendedObjectClassDefinition.Scope.COMPANY,
	strictScope = true
)
@Meta.OCD(
	id = "com.liferay.account.configuration.AccountEntryEmailConfiguration",
	localization = "content/Language",
	name = "account-entry-email-configuration-name"
)
public interface AccountEntryEmailConfiguration {

	@Meta.AD(
		deflt = "${resource:com/liferay/account/dependencies/account_entry_invite_user_subject.tmpl}",
		description = "invitation-email-subject-description",
		name = "invitation-email-subject", required = false
	)
	public LocalizedValuesMap invitationEmailSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/account/dependencies/account_entry_invite_user_body.tmpl}",
		description = "invitation-email-body-description",
		name = "invitation-email-body", required = false
	)
	public LocalizedValuesMap invitationEmailBody();

	@Meta.AD(
		deflt = "48",
		description = "invitation-token-expiration-time-description",
		name = "invitation-token-expiration-time", required = false
	)
	public int invitationTokenExpirationTime();

}