/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tika.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jorge Díaz
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.tika.internal.configuration.TikaConfiguration",
	localization = "content/Language", name = "tika-configuration-name"
)
public interface TikaConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "text-extraction-fork-process-enabled-description",
		name = "text-extraction-fork-process-enabled", required = false
	)
	public boolean textExtractionForkProcessEnabled();

	@Meta.AD(
		deflt = "application/x-tika-ooxml",
		description = "text-extraction-fork-process-mime-types-description",
		name = "text-extraction-fork-process-mime-types", required = false
	)
	public String[] textExtractionForkProcessMimeTypes();

	@Meta.AD(
		deflt = "dependencies/tika.xml",
		description = "tika-config-xml-description", name = "tika-config-xml",
		required = false
	)
	public String tikaConfigXml();

}