/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(category = "documents-and-media")
@Meta.OCD(
	id = "com.liferay.document.library.configuration.DLFileEntryConfiguration",
	localization = "content/Language", name = "dl-file-entry-configuration-name"
)
@ProviderType
public interface DLFileEntryConfiguration {

	@Meta.AD(
		deflt = "104857600",
		description = "previewable-processor-max-size-help",
		name = "previewable-processor-max-size", required = false
	)
	public long previewableProcessorMaxSize();

}