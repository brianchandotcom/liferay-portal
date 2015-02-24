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

package com.liferay.wiki.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Iván Zaera
 */
@Meta.OCD(
	id = "com.liferay.wiki.configuration.WikiPortletInstanceConfiguration"
)
public interface WikiPortletInstanceConfiguration {

	/**
	# Set a DDM template ID that starts with the prefix "ddmTemplate_" (i.e.
	# ddmTemplate_06cd7b42-e8a4-4b5e-8d5a-b4f4dbba5618)to use as the display
	# style.
	 */
	@Meta.AD(
		deflt = "", required = false
	)
	public String displayStyle();

	/**
	 * Whether or not to enable ratings in Wiki comments.
	 */
	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean enableCommentRatings();

	/**
	 * Set this to true to enable comments for wiki pages.
	 */
	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean enableComments();

	/**
	 * Set this to true to enable ratings for wiki pages.
	 */
	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean enablePageRatings();

	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean enableRelatedAssets();

	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean enableRss();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		required = false
	)
	public String rssDelta();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/rss.feed.display.style.default}",
		required = false
	)
	public String rssDisplayStyle();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/rss.feed.type.default}",
		required = false
	)
	public String rssFeedType();

}