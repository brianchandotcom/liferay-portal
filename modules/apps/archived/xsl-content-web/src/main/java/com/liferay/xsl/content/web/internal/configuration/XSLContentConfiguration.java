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

package com.liferay.xsl.content.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(category = "xsl-content")
@Meta.OCD(
	id = "com.liferay.xsl.content.web.internal.configuration.XSLContentConfiguration",
	localization = "content/Language", name = "xsl-content-configuration-name"
)
public interface XSLContentConfiguration {

	@Meta.AD(
		deflt = "@portlet_context_url@", id = "valid.url.prefixes",
		name = "valid-url-prefixes", required = false
	)
	public String validUrlPrefixes();

	@Meta.AD(
		deflt = "false", id = "xml.doctype.declaration.allowed",
		name = "xml-doctype-declaration-allowed", required = false
	)
	public boolean xmlDoctypeDeclarationAllowed();

	@Meta.AD(
		deflt = "false", id = "xml.external.general.entities.allowed",
		name = "xml-external-general-entities-allowed", required = false
	)
	public boolean xmlExternalGeneralEntitiesAllowed();

	@Meta.AD(
		deflt = "false", id = "xml.external.parameter.entities.allowed",
		name = "xml-external-parameter-entities-allowed", required = false
	)
	public boolean xmlExternalParameterEntitiesAllowed();

	@Meta.AD(
		deflt = "true", id = "xsl.secure.processing.enabled",
		name = "xsl-secure-processing-enabled", required = false
	)
	public boolean xslSecureProcessingEnabled();

}