/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Meta.AD(deflt = "", name = "destination", required = false)
	public String destination();

}