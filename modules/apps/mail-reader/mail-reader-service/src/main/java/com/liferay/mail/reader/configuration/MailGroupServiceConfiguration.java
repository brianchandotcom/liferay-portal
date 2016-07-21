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
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.mail.reader.configuration.MailConfiguration",
	localization = "content/Language", name = "mail.service.configuration.name"
)
public interface MailGroupServiceConfiguration {

	@Meta.AD(
		deflt = "{\"accounts\": [{\"titleLanguageKey\": \"gmail-account\",\"descriptionLanguageKey\": \"please-enable-imap-in-you-gmail-settings-for-mail-to-work\",\"address\": \"@gmail.com\",\"protocol\": \"imap\",\"hideSettings\": true,\"incomingHostName\": \"imap.gmail.com\",\"incomingPort\": \"993\",\"incomingSecure\": true,\"outgoingHostName\": \"smtp.gmail.com\",\"outgoingPort\": \"465\",\"outgoingSecure\": \"true\",\"folderPrefix\": \"\",\"useLocalPartAsLogin\": true},{\"titleLanguageKey\": \"custom-mail-account\",\"descriptionLanguageKey\": \"\",\"address\": \"\",\"protocol\": \"imap\",\"hideSettings\": false,\"incomingHostName\": \"\",\"incomingPort\": \"110\",\"incomingSecure\": false,\"outgoingHostName\": \"\",\"outgoingPort\": \"25\",\"outgoingSecure\": \"false\",\"folderPrefix\": \"\",\"useLocalPartAsLogin\": false}]}",
		required = false
	)
	public String defaultAccounts();

	@Meta.AD(deflt = "109|110|143|220|993|995|1110|2221", required = false)
	public String[] incomingPorts();

	@Meta.AD(deflt = "false", required = false)
	public boolean javamailDebug();

	@Meta.AD(deflt = "1000", required = false)
	public int messagesSyncCount();

	@Meta.AD(
		deflt = "25|26|79|110|143|465|587|2500|2525|3535", required = false
	)
	public String[] outgoingPorts();

}