/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(category = "pages")
@Meta.OCD(
	description = "redirect-configuration-description",
	id = "com.liferay.redirect.internal.configuration.RedirectConfiguration",
	localization = "content/Language", name = "redirect-configuration-name"
)
public interface RedirectConfiguration {

	@Meta.AD(
		deflt = "false", description = "redirect-not-found-entry-enabled-help",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "30", description = "redirect-not-found-entry-max-age-help",
		name = "redirect-not-found-entry-max-age", required = false
	)
	public int redirectNotFoundEntryMaxAge();

	@Meta.AD(
		deflt = "1000",
		description = "maximum-number-of-redirect-not-found-entries-help",
		name = "maximum-number-of-redirect-not-found-entries", required = false
	)
	public int maximumNumberOfRedirectNotFoundEntries();

	@Meta.AD(
		deflt = "24",
		description = "check-redirect-not-found-entries-interval-help",
		name = "check-redirect-not-found-entries-interval", required = false
	)
	public int checkRedirectNotFoundEntriesInterval();

}