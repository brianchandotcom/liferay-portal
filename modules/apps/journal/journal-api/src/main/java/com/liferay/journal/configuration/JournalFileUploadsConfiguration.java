/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(category = "web-content")
@Meta.OCD(
	id = "com.liferay.journal.configuration.JournalFileUploadsConfiguration",
	localization = "content/Language",
	name = "journal-file-uploads-configuration-name"
)
public interface JournalFileUploadsConfiguration {

	@Meta.AD(
		deflt = ".gif,.jpeg,.jpg,.png", name = "allowed-file-extensions",
		required = false
	)
	public String[] imageExtensions();

	@Meta.AD(
		deflt = "51200", name = "maximum-file-size-of-small-image",
		required = false
	)
	public long smallImageMaxSize();

}