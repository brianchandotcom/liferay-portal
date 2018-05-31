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

package com.liferay.mail.reader.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Peter Fellwock
 */
@ExtendedObjectClassDefinition(
	category = "community-tools",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.mail.reader.configuration.MailGroupServiceConfiguration",
	localization = "content/Language", name = "mail-service-configuration-name"
)
public interface MailGroupServiceConfiguration {

	@Meta.AD(
		deflt = "109|110|143|220|993|995|1110|2221", name = "incoming-ports",
		required = false
	)
	public String[] incomingPorts();

	@Meta.AD(deflt = "false", name = "javamail-debug", required = false)
	public boolean javamailDebug();

	@Meta.AD(deflt = "1000", name = "message-sync-count", required = false)
	public int messagesSyncCount();

	@Meta.AD(
		deflt = "25|26|79|110|143|465|587|2500|2525|3535",
		name = "outgoing-ports", required = false
	)
	public String[] outgoingPorts();

}