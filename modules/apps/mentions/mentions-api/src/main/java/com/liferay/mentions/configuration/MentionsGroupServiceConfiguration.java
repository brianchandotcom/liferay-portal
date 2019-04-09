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

package com.liferay.mentions.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Sergio González
 */
@ExtendedObjectClassDefinition(category = "community-tools")
@Meta.OCD(
	id = "com.liferay.mentions.configuration.MentionsGroupServiceConfiguration",
	localization = "content/Language",
	name = "mentions-group-service-configuration-name"
)
public interface MentionsGroupServiceConfiguration {

	@Meta.AD(
		deflt = "${resource:com/liferay/mentions/configuration/dependencies/asset_entry_mention_email_body.tmpl}",
		name = "asset-entry-mention-email-body", required = false
	)
	public LocalizedValuesMap assetEntryMentionEmailBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/mentions/configuration/dependencies/asset_entry_mention_email_subject.tmpl}",
		name = "asset-entry-mention-email-subject", required = false
	)
	public LocalizedValuesMap assetEntryMentionEmailSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/mentions/configuration/dependencies/comment_mention_email_body.tmpl}",
		name = "comment-mention-email-body", required = false
	)
	public LocalizedValuesMap commentMentionEmailBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/mentions/configuration/dependencies/comment_mention_email_subject.tmpl}",
		name = "comment-mention-email-subject", required = false
	)
	public LocalizedValuesMap commentMentionEmailSubject();

}