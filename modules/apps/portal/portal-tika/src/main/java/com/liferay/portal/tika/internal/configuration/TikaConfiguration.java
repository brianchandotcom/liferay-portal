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