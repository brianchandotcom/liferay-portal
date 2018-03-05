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

package com.liferay.blogs.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sergio González
 */
@ExtendedObjectClassDefinition(
	category = "blogs",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.blogs.web.internal.configuration.BlogsPortletInstanceConfiguration",
	localization = "content/Language",
	name = "blogs-portlet-instance-configuration-name"
)
public interface BlogsPortletInstanceConfiguration {

	@Meta.AD(deflt = "abstract", name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	@Meta.AD(deflt = "true", name = "enable-comment-ratings", required = false)
	public boolean enableCommentRatings();

	@Meta.AD(deflt = "true", name = "enable-comments", required = false)
	public boolean enableComments();

	@Meta.AD(deflt = "true", name = "enable-flags", required = false)
	public boolean enableFlags();

	@Meta.AD(deflt = "true", name = "enable-ratings", required = false)
	public boolean enableRatings();

	@Meta.AD(deflt = "false", name = "enable-reading-time", required = false)
	public boolean enableReadingTime();

	@Meta.AD(deflt = "true", name = "enable-related-assets", required = false)
	public boolean enableRelatedAssets();

	@Meta.AD(deflt = "false", name = "enable-view-count", required = false)
	public boolean enableViewCount();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		name = "page-delta", required = false
	)
	public String pageDelta();

	@Meta.AD(
		deflt = "inline", name = "social-bookmarks-display-style",
		required = false
	)
	public String socialBookmarksDisplayStyle();

	@Meta.AD(
		deflt = StringPool.STAR, name = "social-bookmarks-types",
		required = false
	)
	public String socialBookmarksTypes();

}