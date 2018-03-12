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
	category = "knowledge-base",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration",
	localization = "content/Language",
	name = "knowledge-base-group-service-configuration-name"
)
public interface KBGroupServiceConfiguration {

	@Meta.AD(deflt = "alloyeditor", name = "get-editor-name", required = false)
	public String getEditorName();

	@Meta.AD(
		deflt = "true", name = "article-increment-priority-enabled",
		required = false
	)
	public boolean articleIncrementPriorityEnabled();

	@Meta.AD(
		deflt = ".markdown|.md", name = "markdown-importer-article-extensions",
		required = false
	)
	public String[] markdownImporterArticleExtensions();

	@Meta.AD(
		deflt = "intro.markdown", name = "markdown-importer-article-intro",
		required = false
	)
	public String markdownImporterArticleIntro();

	@Meta.AD(
		deflt = ".bmp|.gif|.jpeg|.jpg|.png",
		name = "markdown-importer-image-file-extensions", required = false
	)
	public String[] markdownImporterImageFileExtensions();

	@Meta.AD(
		deflt = "/images", name = "markdown-importer-image-folder",
		required = false
	)
	public String markdownImporterImageFolder();

	@Meta.AD(deflt = "false", name = "source-url-enabled", required = false)
	public boolean sourceURLEnabled();

	@Meta.AD(
		deflt = "edit-on-github", name = "source-url-edit-message-key",
		required = false
	)
	public String sourceURLEditMessageKey();

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
		deflt = "true", name = "email-kb-article-added-enabled",
		required = false
	)
	public boolean emailKBArticleAddedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_article_added_subject.tmpl}",
		name = "email-kb-article-added-subject", required = false
	)
	public String emailKBArticleAddedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_article_added_body.tmpl}",
		name = "email-kb-article-added-body", required = false
	)
	public String emailKBArticleAddedBody();

	@Meta.AD(
		deflt = "true", name = "email-kb-article-updated-enabled",
		required = false
	)
	public boolean emailKBArticleUpdatedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_article_updated_subject.tmpl}",
		name = "email-kb-article-updated-subject", required = false
	)
	public String emailKBArticleUpdatedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_article_updated_body.tmpl}",
		name = "email-kb-article-updated-body", required = false
	)
	public String emailKBArticleUpdatedBody();

	@Meta.AD(
		deflt = "true",
		name = "email-kb-article-suggestion-in-progress-enabled",
		required = false
	)
	public boolean emailKBArticleSuggestionInProgressEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_in_progress_subject.tmpl}",
		name = "email-kb-article-suggestion-in-progress-subject",
		required = false
	)
	public String emailKBArticleSuggestionInProgressSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_in_progress_body.tmpl}",
		name = "email-kb-article-suggestion-in-progress-body", required = false
	)
	public String emailKBArticleSuggestionInProgressBody();

	@Meta.AD(
		deflt = "true", name = "email-kb-article-suggestion-received-enabled",
		required = false
	)
	public boolean emailKBArticleSuggestionReceivedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_received_subject.tmpl}",
		name = "email-kb-article-suggestion-received-subject", required = false
	)
	public String emailKBArticleSuggestionReceivedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_received_body.tmpl}",
		name = "email-kb-article-suggestion-received-body", required = false
	)
	public String emailKBArticleSuggestionReceivedBody();

	@Meta.AD(
		deflt = "true", name = "email-kb-article-suggestion-resolved-enabled",
		required = false
	)
	public boolean emailKBArticleSuggestionResolvedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_resolved_subject.tmpl}",
		name = "email-kb-article-suggestion-resolved-subject", required = false
	)
	public String emailKBArticleSuggestionResolvedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/knowledge/base/dependencies/email_kb_suggestion_resolved_body.tmpl}",
		name = "email-kb-article-suggestion-resolved-body", required = false
	)
	public String emailKBArticleSuggestionResolvedBody();

	@Meta.AD(deflt = "true", name = "enable-rss", required = false)
	public boolean enableRSS();

	@Meta.AD(deflt = "20", name = "rss-delta", required = false)
	public int rssDelta();

	@Meta.AD(
		deflt = "full-content", name = "rss-display-style", required = false
	)
	public String rssDisplayStyle();

	@Meta.AD(deflt = "atom10", name = "rss-format", required = false)
	public String rssFormat();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/rss.feed.type.default}",
		name = "rss-feed-type", required = false
	)
	public String rssFeedType();

}