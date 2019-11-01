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

package com.liferay.configuration.admin.web.internal.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"configuration.pid=com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerGroupConfiguration",
		"configuration.pid=com.liferay.blogs.configuration.BlogsGroupServiceConfiguration",
		"configuration.pid=com.liferay.bookmarks.configuration.BookmarksGroupServiceConfiguration",
		"configuration.pid=com.liferay.comment.configuration.CommentGroupServiceConfiguration",
		"configuration.pid=com.liferay.dynamic.data.mapping.configuration.DDMGroupServiceConfiguration",
		"configuration.pid=com.liferay.dynamic.data.mapping.data.provider.configuration.DDMDataProviderConfiguration",
		"configuration.pid=com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration",
		"configuration.pid=com.liferay.journal.configuration.JournalGroupServiceConfiguration",
		"configuration.pid=com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration",
		"configuration.pid=com.liferay.sharing.internal.configuration.SharingGroupConfiguration",
		"configuration.pid=com.liferay.social.activity.configuration.SocialActivityGroupServiceConfiguration",
		"configuration.pid=com.liferay.wiki.configuration.WikiGroupServiceConfiguration"
	},
	service = ConfigurationVisibilityController.class
)
public class SiteDefaultConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		if (scope.equals(scope.GROUP.getValue())) {
			return false;
		}

		return true;
	}

}