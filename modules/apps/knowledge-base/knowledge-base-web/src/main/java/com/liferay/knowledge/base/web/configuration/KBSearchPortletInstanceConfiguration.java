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

package com.liferay.knowledge.base.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(
	category = "collaboration",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.knowledge.base.web.configuration.KBSearchPortletInstanceConfiguration",
	localization = "content/Language",
	name = "knowledge-base-search-portlet-instance-configuration-name"
)
public interface KBSearchPortletInstanceConfiguration {

	@Meta.AD(
		deflt = "true", name = "show-kbarticle-author-column", required = false
	)
	public boolean showKBArticleAuthorColumn();

	@Meta.AD(
		deflt = "true", name = "show-kbarticle-create-date-column",
		required = false
	)
	public boolean showKBArticleCreateDateColumn();

	@Meta.AD(
		deflt = "true", name = "show-kbarticle-modified-date-column",
		required = false
	)
	public boolean showKBArticleModifiedDateColumn();

	@Meta.AD(
		deflt = "true", name = "show-kbarticle-views-column", required = false
	)
	public boolean showKBArticleViewsColumn();

	@Meta.AD(
		deflt = "false", name = "enable-kbarticle-description", required = false
	)
	public boolean enableKBArticleDescription();

	@Meta.AD(
		deflt = "true", name = "enable-kbarticle-ratings", required = false
	)
	public boolean enableKBArticleRatings();

	@Meta.AD(
		deflt = "true", name = "show-kbarticle-asset-entries", required = false
	)
	public boolean showKBArticleAssetEntries();

	@Meta.AD(
		deflt = "true", name = "show-kbarticle-attachments", required = false
	)
	public boolean showKBArticleAttachments();

	@Meta.AD(
		deflt = "true", name = "enable-kbarticle-asset-links", required = false
	)
	public boolean enableKBArticleAssetLinks();

	@Meta.AD(
		deflt = "true", name = "enable-kbarticle-view-count-increment",
		required = false
	)
	public boolean enableKBArticleViewCountIncrement();

	@Meta.AD(
		deflt = "true", name = "enable-kbarticle-subscriptions",
		required = false
	)
	public boolean enableKBArticleSubscriptions();

	@Meta.AD(
		deflt = "true", name = "enable-kbarticle-history", required = false
	)
	public boolean enableKBArticleHistory();

	@Meta.AD(deflt = "true", name = "enable-kbarticle-print", required = false)
	public boolean enableKBArticlePrint();

	@Meta.AD(
		deflt = "false", name = "enable-social-bookmarks", required = false
	)
	public boolean enableSocialBookmarks();

	@Meta.AD(
		deflt = "menu", name = "social-bookmarks-display-style",
		required = false
	)
	public String socialBookmarksDisplayStyle();

	@Meta.AD(
		deflt = "bottom", name = "social-bookmarks-display-position",
		required = false
	)
	public String socialBookmarksDisplayPosition();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/social.bookmark.types}",
		name = "social-bookmarks-types", required = false
	)
	public String socialBookmarksTypes();

}