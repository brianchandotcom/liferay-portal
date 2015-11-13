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

package com.liferay.journal.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Jürgen Kappler
 */
@ConfigurationAdmin(category = "web-experience-management")
@Meta.OCD(id = "com.liferay.journal.web.configuration.JournalWebConfiguration")
public interface JournalWebConfiguration {

	@Meta.AD(deflt = "15", required = false)
	public int checkInterval();

	@Meta.AD(deflt = "descriptive", required = false)
	public String defaultDisplayView();

	@Meta.AD(deflt = "icon|descriptive|list", required = false)
	public String[] displayViews();

	@Meta.AD(deflt = "true", required = false)
	public boolean journalArticleForceAutogenerateId();

	@Meta.AD(
		deflt = "content|small-image|categorization|schedule|display-page|related-assets|permissions|custom-fields",
		required = false
	)
	public String[] journalArticleFormAdd();

	@Meta.AD(
		deflt = "content|small-image|categorization|display-page|related-assets|custom-fields",
		required = false
	)
	public String[] journalArticleFormDefaultValues();

	@Meta.AD(
		deflt = "content|small-image|categorization|schedule|display-page|related-assets|custom-fields",
		required = false
	)
	public String[] journalArticleFormUpdate();

	@Meta.AD(deflt = "true", required = false)
	public boolean journalArticlesSearchWithIndex();

	@Meta.AD(deflt = "true", required = false)
	public boolean journalFeedForceAutogenerateId();

	@Meta.AD(deflt = "true", required = false)
	public boolean publishToLiveByDefault();

	@Meta.AD(deflt = "true", required = false)
	public boolean publishVersionHistoryByDefault();

}