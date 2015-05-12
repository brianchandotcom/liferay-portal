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

package com.liferay.rss.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Juergen Kappler
 */
@Meta.OCD(
	id = "com.liferay.rss.web.configuration.RSSPortletInstanceConfiguration"
)
public interface RSSPortletInstanceConfiguration {

	/**
	 * Set a DDM template ID that starts with the prefix "ddmTemplate_" (i.e.
	 * ddmTemplate_rss-navigation-ftl) to use as the display
	 * style.
	 */
	@Meta.AD(
		deflt = "", required = false
	)
	public String displayStyle();

	@Meta.AD(
		deflt = "0", required = false
	)
	public long displayStyleGroupId();

	@Meta.AD(
		deflt = "4", required = false
	)
	public int entriesPerFeed();

	@Meta.AD(
		deflt = "8", required = false
	)
	public int expandedEntriesPerFeed();

	@Meta.AD(
		deflt = "right", required = false
	)
	public String feedImageAlignment();

	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean showFeedDescription();

	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean showFeedImage();

	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean showFeedItemAuthor();

	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean showFeedPublishedDate();

	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean showFeedTitle();

	@Meta.AD(
		deflt = "", required = false
	)
	public String[] titles();

	@Meta.AD(
		deflt = "http://www.liferay.com/community/blogs/-/blogs_stream/community/rss|http://partners.userland.com/nytRss/technology.xml",
		required = false
	)
	public String[] urls();

}