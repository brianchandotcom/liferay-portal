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

package com.liferay.blogs.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Sergio González
 */
@ConfigurationAdmin(category = "collaboration")
@Meta.OCD(
	id = "com.liferay.blogs.web.configuration.BlogsPortletInstanceConfiguration"
)
public interface BlogsPortletInstanceConfiguration {

	@Meta.AD(deflt = "abstract", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "0", required = false)
	public long displayStyleGroupId();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableCommentRatings();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableComments();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableFlags();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableRatings();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableRelatedAssets();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableSocialBookmarks();

	@Meta.AD(deflt = "false", required = false)
	public boolean enableViewCount();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		required = false
	)
	public String pageDelta();

	@Meta.AD(deflt = "bottom", required = false)
	public String socialBookmarksDisplayPosition();

	@Meta.AD(deflt = "menu", required = false)
	public String socialBookmarksDisplayStyle();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/social.bookmark.types}",
		required = false
	)
	public String socialBookmarksTypes();

}