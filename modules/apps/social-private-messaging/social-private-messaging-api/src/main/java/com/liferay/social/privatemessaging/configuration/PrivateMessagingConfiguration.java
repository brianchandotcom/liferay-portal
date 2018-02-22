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

package com.liferay.social.privatemessaging.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Peter Fellwock
 */
@ExtendedObjectClassDefinition(
	category = "messaging", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.social.privatemessaging.configuration.PrivateMessagingConfiguration",
	localization = "content/Language",
	name = "privatemessaging-service-configuration-name"
)
public interface PrivateMessagingConfiguration {

	@Meta.AD(
		deflt = "com/liferay/social/privatemessaging/dependencies/notification_message_body.tmpl",
		name = "email-body", required = false
	)
	public String emailBody();

	@Meta.AD(
		deflt = "20", name = "autocomplete-recipient-max", required = false
	)
	public int autocompleteRecipientMax();

	@Meta.AD(
		deflt = "", name = "autocomplete-recipient-site-excludes",
		required = false
	)
	public String[] autocompleteRecipientSiteExcludes();

	@Meta.AD(
		deflt = "com/liferay/social/privatemessaging/dependencies/notification_message_subject.tmpl",
		name = "email-subject", required = false
	)
	public String emailSubject();

	@Meta.AD(
		deflt = "all", name = "autocomplete-recipient-type", required = false
	)
	public String autocompleteRecipientType();

}