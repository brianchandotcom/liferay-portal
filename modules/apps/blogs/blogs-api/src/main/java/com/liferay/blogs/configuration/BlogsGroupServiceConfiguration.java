/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sergio González
 */
@ExtendedObjectClassDefinition(
	category = "blogs", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.blogs.configuration.BlogsGroupServiceConfiguration",
	localization = "content/Language",
	name = "blogs-group-service-configuration-name"
)
public interface BlogsGroupServiceConfiguration {

	@Meta.AD(deflt = "true", name = "enable-rss", required = false)
	public boolean enableRss();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		name = "rss-delta", required = false
	)
	public String rssDelta();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/rss.feed.display.style.default}",
		name = "rss-display-style", required = false
	)
	public String rssDisplayStyle();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/rss.feed.type.default}",
		name = "rss-feed-type", required = false
	)
	public String rssFeedType();

	@Meta.AD(
		deflt = "false", name = "send-notifications-to-blogs-entry-creator",
		required = false
	)
	public default boolean sendNotificationsToBlogsEntryCreator() {
		return false;
	}

	@Meta.AD(deflt = "300", name = "small-image-width", required = false)
	public int smallImageWidth();

	@Meta.AD(
		deflt = "false", name = "subscribe-blogs-entry-creator-to-comments",
		required = false
	)
	public default boolean subscribeBlogsEntryCreatorToComments() {
		return false;
	}

}