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

package com.liferay.portal.search.web.internal.search.bar.portlet.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Olivia Yu
 */
@ExtendedObjectClassDefinition(
	category = "search",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.portal.search.web.internal.search.bar.portlet.configuration.SearchBarPortletInstanceConfiguration",
	localization = "content/Language",
	name = "search-bar-portlet-instance-configuration-name"
)
public interface SearchBarPortletInstanceConfiguration {

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(
		deflt = "true", description = "enable-suggestions-help",
		name = "enable-suggestions", required = false
	)
	public boolean enableSuggestions();

	@Meta.AD(
		deflt = "{\"contributorName\":\"basic\"\\,\"displayGroupName\":\"suggestions\"\\,\"size\":5}",
		description = "suggestions-contributor-configuration-help",
		name = "suggestions-contributor-configuration", required = false
	)
	public String[] suggestionsContributorConfigurations();

	@Meta.AD(
		deflt = "2", name = "character-threshold-for-displaying-suggestions",
		required = false
	)
	public int suggestionsDisplayThreshold();

}