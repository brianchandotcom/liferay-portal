/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "rss",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.rss.web.internal.configuration.RSSPortletInstanceConfiguration",
	localization = "content/Language",
	name = "rss-portlet-instance-configuration-name"
)
public interface RSSPortletInstanceConfiguration {

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	/**
	 * Set a DDM template ID that starts with the prefix "ddmTemplate_" (i.e.
	 * ddmTemplate_rss-navigation-ftl) to use as the display style.
	 */
	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "true", name = "show-feed-title", required = false)
	public boolean showFeedTitle();

	@Meta.AD(
		deflt = "true", name = "show-feed-published-date", required = false
	)
	public boolean showFeedPublishedDate();

	@Meta.AD(deflt = "true", name = "show-feed-description", required = false)
	public boolean showFeedDescription();

	@Meta.AD(deflt = "true", name = "show-feed-image", required = false)
	public boolean showFeedImage();

	@Meta.AD(deflt = "true", name = "show-feed-item-author", required = false)
	public boolean showFeedItemAuthor();

	@Meta.AD(deflt = "4", name = "entries-per-feed", required = false)
	public int entriesPerFeed();

	@Meta.AD(deflt = "8", name = "expanded-entries-per-feed", required = false)
	public int expandedEntriesPerFeed();

	@Meta.AD(deflt = "right", name = "feed-image-alignment", required = false)
	public String feedImageAlignment();

	@Meta.AD(name = "titles", required = false)
	public String[] titles();

	@Meta.AD(name = "urls", required = false)
	public String[] urls();

}