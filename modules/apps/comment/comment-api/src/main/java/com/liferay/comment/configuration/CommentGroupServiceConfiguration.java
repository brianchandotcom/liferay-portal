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

package com.liferay.comment.configuration;

import aQute.bnd.annotation.ProviderType;
import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Roberto Díaz
 * @author István András Dézsi
 */
@ExtendedObjectClassDefinition(
	category = "comments", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.comment.configuration.CommentGroupServiceConfiguration",
	localization = "content/Language", name = "discussion-configuration-name"
)
@ProviderType
public interface CommentGroupServiceConfiguration {

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		name = "email-from-name", required = false
	)
	public String emailFromName();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		name = "email-from-address", required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "true", name = "email-discussion-comment-added-enabled",
		required = false
	)
	public boolean discussionEmailCommentsAddedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/comment/configuration/dependencies/discussion_email_added_body.tmpl}",
		name = "email-discussion-comment-added-body", required = false
	)
	public LocalizedValuesMap discussionEmailBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/comment/configuration/dependencies/discussion_email_added_subject.tmpl}",
		name = "email-discussion-comment-added-subject", required = false
	)
	public LocalizedValuesMap discussionEmailSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/comment/configuration/dependencies/discussion_email_updated_body.tmpl}",
		name = "email-discussion-comment-updated-body", required = false
	)
	public LocalizedValuesMap discussionEmailUpdatedBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/comment/configuration/dependencies/discussion_email_updated_subject.tmpl}",
		name = "email-discussion-comment-updated-subject", required = false
	)
	public LocalizedValuesMap discussionEmailUpdatedSubject();

}