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

package com.liferay.knowledge.base.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(
	category = "collaboration",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration",
	localization = "content/Language",
	name = "knowledge.base.group.service.configuration.name"
)
public interface KBGroupServiceConfiguration {

	@Meta.AD(deflt = "true", required = false)
	public boolean articleIncrementPriorityEnabled();

	@Meta.AD(deflt = ".markdown|.md", required = false)
	public String[] markdownImporterArticleExtensions();

	@Meta.AD(deflt = "intro.markdown", required = false)
	public String markdownImporterArticleIntro();

	@Meta.AD(deflt = ".bmp|.gif|.jpeg|.jpg|.png", required = false)
	public String[] markdownImporterImageFileExtensions();

	@Meta.AD(deflt = "/images", required = false)
	public String markdownImporterImageFolder();

	@Meta.AD(deflt = "false", required = false)
	public boolean sourceURLEnabled();

	@Meta.AD(deflt = "edit-on-github", required = false)
	public String sourceURLEditMessageKey();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		required = false
	)
	public String emailFromName();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		required = false
	)
	public String emailFromAddress();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailKBArticleAddedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_article_added_subject.tmpl}",
		required = false
	)
	public String emailKBArticleAddedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_article_added_body.tmpl}",
		required = false
	)
	public String emailKBArticleAddedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailKBArticleUpdatedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_article_updated_subject.tmpl}",
		required = false
	)
	public String emailKBArticleUpdatedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_article_updated_body.tmpl}",
		required = false
	)
	public String emailKBArticleUpdatedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailKBArticleSuggestionInProgressEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_in_progress_subject.tmpl}",
		required = false
	)
	public String emailKBArticleSuggestionInProgressSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_in_progress_body.tmpl}",
		required = false
	)
	public String emailKBArticleSuggestionInProgressBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailKBArticleSuggestionReceivedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_received_subject.tmpl}",
		required = false
	)
	public String emailKBArticleSuggestionReceivedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_received_body.tmpl}",
		required = false
	)
	public String emailKBArticleSuggestionReceivedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailKBArticleSuggestionResolvedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_resolved_subject.tmpl}",
		required = false
	)
	public String emailKBArticleSuggestionResolvedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_resolved_body.tmpl}",
		required = false
	)
	public String emailKBArticleSuggestionResolvedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableRSS();

	@Meta.AD(deflt = "20", required = false)
	public int rssDelta();

	@Meta.AD(deflt = "full-content", required = false)
	public String rssDisplayStyle();

	@Meta.AD(deflt = "atom10", required = false)
	public String rssFormat();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/rss.feed.type.default}",
		required = false
	)
	public String rssFeedType();

}