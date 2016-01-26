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

package com.liferay.journal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "web-experience-management",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.journal.configuration.JournalGroupServiceConfiguration",
	localization = "content/Language",
	name = "%journal.group.service.configuration.name"
)
public interface JournalGroupServiceConfiguration {

	@Meta.AD(deflt = "false", required = false)
	public boolean changeableDefaultLanguage();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_added_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleAddedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailArticleAddedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_added_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleAddedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_approval_denied_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleApprovalDeniedBody();

	@Meta.AD(deflt = "false", required = false)
	public boolean emailArticleApprovalDeniedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_approval_denied_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleApprovalDeniedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_approval_granted_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleApprovalGrantedBody();

	@Meta.AD(deflt = "false", required = false)
	public boolean emailArticleApprovalGrantedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_approval_granted_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleApprovalGrantedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_approval_requested_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleApprovalRequestedBody();

	@Meta.AD(deflt = "false", required = false)
	public boolean emailArticleApprovalRequestedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_approval_requested_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleApprovalRequestedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_moved_from_folder_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleMovedFromFolderBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailArticleMovedFromFolderEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_moved_from_folder_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleMovedFromFolderSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_moved_to_folder_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleMovedToFolderBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailArticleMovedToFolderEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_moved_to_folder_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleMovedToFolderSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_review_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleReviewBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailArticleReviewEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_review_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleReviewSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_updated_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleUpdatedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailArticleUpdatedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/journal/dependencies/email_article_updated_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailArticleUpdatedSubject();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		required = false
	)
	public String emailFromName();

}