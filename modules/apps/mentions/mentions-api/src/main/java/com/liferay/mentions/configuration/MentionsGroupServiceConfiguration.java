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

import com.liferay.configuration.admin.ConfigurationAdmin;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Sergio González
 */
@ConfigurationAdmin(category = "collaboration")
@Meta.OCD(
	id = "com.liferay.mentions.configuration.MentionsGroupServiceConfiguration"
)
public interface MentionsGroupServiceConfiguration {

	@Meta.AD(
		deflt = "${resource:com/liferay/mentions/configuration/dependencies/asset_entry_mention_email_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap assetEntryMentionEmailBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/mentions/configuration/dependencies/asset_entry_mention_email_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap assetEntryMentionEmailSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/mentions/configuration/dependencies/comment_mention_email_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap commentMentionEmailBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/mentions/configuration/dependencies/comment_mention_email_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap commentMentionEmailSubject();

}