/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Iván Zaera
 */
@ExtendedObjectClassDefinition(
	category = "wiki",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.wiki.web.internal.configuration.WikiPortletInstanceConfiguration",
	localization = "content/Language",
	name = "wiki-portlet-instance-configuration-name"
)
public interface WikiPortletInstanceConfiguration {

	/**
	 * Set a DDM template ID that starts with the prefix "ddmTemplate_" (i.e.
	 * ddmTemplate_06cd7b42-e8a4-4b5e-8d5a-b4f4dbba5618) to use as the display
	 * style.
	 */
	@Meta.AD(deflt = "", name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	/**
	 * Set this to <code>true</code> to enable ratings in Wiki comments.
	 */
	@Meta.AD(deflt = "true", name = "enable-comment-ratings", required = false)
	public boolean enableCommentRatings();

	/**
	 * Set this to <code>true</code> to enable comments for Wiki pages.
	 */
	@Meta.AD(deflt = "true", name = "enable-comments", required = false)
	public boolean enableComments();

	/**
	 * Set this to <code>true</code> to enable highlighting of search results in
	 * the Wiki portlet.
	 */
	@Meta.AD(deflt = "true", name = "enable-highlighting", required = false)
	public boolean enableHighlighting();

	/**
	 * Set this to <code>true</code> to enable ratings for Wiki pages.
	 */
	@Meta.AD(deflt = "true", name = "enable-page-ratings", required = false)
	public boolean enablePageRatings();

	@Meta.AD(deflt = "true", name = "enable-related-assets", required = false)
	public boolean enableRelatedAssets();

	@Meta.AD(deflt = "", name = "hidden-nodes", required = false)
	public String[] hiddenNodes();

	@Meta.AD(deflt = "", name = "visible-nodes", required = false)
	public String[] visibleNodes();

}