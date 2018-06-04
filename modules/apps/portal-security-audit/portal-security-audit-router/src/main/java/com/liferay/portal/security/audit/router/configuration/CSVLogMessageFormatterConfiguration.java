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

package com.liferay.portal.security.audit.router.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@ExtendedObjectClassDefinition(category = "audit")
@Meta.OCD(
	id = "com.liferay.portal.security.audit.router.configuration.CSVLogMessageFormatterConfiguration",
	localization = "content/Language",
	name = "csv-log-message-formatter-configuration-name"
)
public interface CSVLogMessageFormatterConfiguration {

	@Meta.AD(
		deflt = "additionalInfo|className|classPK|clientHost|clientIP|companyId|eventType|message|serverName|serverPort|sessionID|timestamp|userId|userName",
		name = "columns", required = false
	)
	public String[] columns();

}