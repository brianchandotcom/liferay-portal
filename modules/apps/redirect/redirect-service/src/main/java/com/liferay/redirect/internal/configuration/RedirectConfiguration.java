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

package com.liferay.redirect.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(
	category = "pages", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "redirect-configuration-description",
	id = "com.liferay.redirect.internal.configuration.RedirectConfiguration",
	localization = "content/Language", name = "redirect-configuration-name"
)
public interface RedirectConfiguration {

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