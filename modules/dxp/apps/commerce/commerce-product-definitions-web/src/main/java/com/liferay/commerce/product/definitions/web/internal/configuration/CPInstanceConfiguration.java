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

package com.liferay.commerce.product.definitions.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "commerce", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.product.definitions.web.internal.configuration.CPInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-instances-configuration-name"
)
public interface CPInstanceConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "changeable-default-language-description",
		name = "changeable-default-language", required = false
	)
	public boolean changeableDefaultLanguage();

	@Meta.AD(deflt = "15", name = "check-internal", required = false)
	public int checkInterval();

	@Meta.AD(
		deflt = "descriptive", name = "default-display-view", required = false
	)
	public String defaultDisplayView();

	@Meta.AD(
		deflt = "icon|descriptive|list", name = "display-views",
		required = false
	)
	public String[] displayViews();

	@Meta.AD(
		deflt = "true", name = "definition-force-autogenerate-id",
		required = false
	)
	public boolean definitionForceAutogenerateId();

	@Meta.AD(
		deflt = "true", name = "definitions-search-with-index", required = false
	)
	public boolean definitionsSearchWithIndex();

	@Meta.AD(
		deflt = "true", name = "publish-to-live-by-default", required = false
	)
	public boolean publishToLiveByDefault();

	@Meta.AD(
		deflt = "true", name = "publish-version-history-by-default",
		required = false
	)
	public boolean publishVersionHistoryByDefault();

}