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
	id = "com.liferay.sharing.configuration.SharingEntryCollaborationEmailConfiguration",
	localization = "content/Language",
	name = "sharing-entry-invitation-to-collaborate-email-configuration-name"
)
public interface SharingEntryCollaborationEmailConfiguration {

	@Meta.AD(
		deflt = "${resource:com/liferay/sharing/configuration/dependencies/sharing_entry_collaboration_email_body.tmpl}",
		description = "invitation-to-collaborate-email-body-description",
		name = "invitation-to-collaborate-email-body", required = false
	)
	public LocalizedValuesMap invitationToCollaborateEmailBody();

	@Meta.AD(
		description = "invitation-to-collaborate-email-sender-email-address-description",
		name = "invitation-to-collaborate-email-sender-email-address",
		required = false
	)
	public String invitationToCollaborateEmailSenderEmailAddress();

	@Meta.AD(
		description = "invitation-to-collaborate-email-sender-name-description",
		name = "invitation-to-collaborate-email-sender-name", required = false
	)
	public String invitationToCollaborateEmailSenderName();

	@Meta.AD(
		deflt = "${resource:com/liferay/sharing/configuration/dependencies/sharing_entry_collaboration_email_subject.tmpl}",
		description = "invitation-to-collaborate-email-subject-description",
		name = "invitation-to-collaborate-email-subject", required = false
	)
	public LocalizedValuesMap invitationToCollaborateEmailSubject();

	@Meta.AD(
		deflt = "48",
		description = "invitation-to-collaborate-token-expiration-time-description",
		name = "invitation-to-collaborate-token-expiration-time",
		required = false
	)
	public int invitationToCollaborateTokenExpirationTime();

}