/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.settings.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jiefeng Wu
 */
@ExtendedObjectClassDefinition(
	category = "email", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.mail.settings.configuration.MailSettingSystemConfiguration",
	localization = "content/Language",
	name = "mail-settings-system-configuration-name"
)
public interface MailSettingSystemConfiguration {

	@Meta.AD(
		deflt = "", description = "mail-audit-trail-description",
		name = "mail-audit-trail", required = false
	)
	public String mailAuditTrail();

	@Meta.AD(
		deflt = "0", description = "mail-batch-size-description",
		name = "mail-batch-size", required = false
	)
	public String mailBatchSize();

	@Meta.AD(
		deflt = "noreply@liferay.com|test@liferay.com|noreply@domain.invalid|test@domain.invalid",
		description = "mail-send-blacklist-description",
		name = "mail-send-blacklist", required = false
	)
	public String[] mailSendBlacklist();

	@Meta.AD(
		deflt = "", description = "mail-session-jndi-name-description",
		name = "mail-session-jndi-name", required = false
	)
	public String mailSessionJndiName();

	@Meta.AD(
		deflt = "false",
		description = "mail-throws-exception-on-failure-description",
		name = "mail-throws-exception-on-failure", required = false
	)
	public boolean mailThrowsExceptionOnFailure();

	@Meta.AD(
		deflt = "events", description = "pop-server-subdomain-description",
		name = "pop-server-subdomain", required = false
	)
	public String popServerSubdomain();

}