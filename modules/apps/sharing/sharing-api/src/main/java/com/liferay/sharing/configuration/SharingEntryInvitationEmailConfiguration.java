/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Alicia García
 */
@ExtendedObjectClassDefinition(
	category = "sharing", scope = ExtendedObjectClassDefinition.Scope.COMPANY,
	strictScope = true
)
@Meta.OCD(
	id = "com.liferay.sharing.configuration.SharingEntryInvitationEmailConfiguration",
	localization = "content/Language",
	name = "sharing-entry-invitation-email-configuration-name"
)
public interface SharingEntryInvitationEmailConfiguration {

	@Meta.AD(
		deflt = "${resource:com/liferay/sharing/configuration/dependencies/sharing_entry_invitation_email_body.tmpl}",
		description = "invitation-email-body-description",
		name = "invitation-email-body", required = false
	)
	public LocalizedValuesMap invitationEmailBody();

	@Meta.AD(
		description = "invitation-email-sender-email-address-description",
		name = "invitation-email-sender-email-address", required = false
	)
	public String invitationEmailSenderEmailAddress();

	@Meta.AD(
		description = "invitation-email-sender-name-description",
		name = "invitation-email-sender-name", required = false
	)
	public String invitationEmailSenderName();

	@Meta.AD(
		deflt = "${resource:com/liferay/sharing/configuration/dependencies/sharing_entry_invitation_email_subject.tmpl}",
		description = "invitation-email-subject-description",
		name = "invitation-email-subject", required = false
	)
	public LocalizedValuesMap invitationEmailSubject();

}