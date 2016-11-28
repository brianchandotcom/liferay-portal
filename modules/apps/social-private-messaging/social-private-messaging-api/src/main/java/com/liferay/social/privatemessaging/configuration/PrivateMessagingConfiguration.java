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
	category = "other", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.social.privatemessaging.configuration.PrivateMessagingConfiguration",
	localization = "content/Language",
	name = "privatemessaging.service.configuration.name"
)
public interface PrivateMessagingConfiguration {

	@Meta.AD(
		deflt = "com/liferay/social/privatemessaging/dependencies/notification_message_body.tmpl",
		required = false
	)
	public String emailBody();

	@Meta.AD(deflt = "20", required = false)
	public int autocompleteRecipientMax();

	@Meta.AD(deflt = "", required = false)
	public String[] autocompleteRecipientSiteExcludes();

	@Meta.AD(
		deflt = "com/liferay/social/privatemessaging/dependencies/notification_message_subject.tmpl",
		required = false
	)
	public String emailSubject();

	@Meta.AD(deflt = "all", required = false)
	public String autocompleteRecipientType();

}