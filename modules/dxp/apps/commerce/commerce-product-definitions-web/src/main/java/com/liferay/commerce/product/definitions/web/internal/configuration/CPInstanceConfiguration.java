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
	name = "commerce.instance.configuration.name"
)
public interface CPInstanceConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "changeable.default.language.description",
		name = "changeable.default.language", required = false
	)
	public boolean changeableDefaultLanguage();

	@Meta.AD(deflt = "15", required = false)
	public int checkInterval();

	@Meta.AD(deflt = "descriptive", required = false)
	public String defaultDisplayView();

	@Meta.AD(deflt = "icon|descriptive|list", required = false)
	public String[] displayViews();

	@Meta.AD(deflt = "true", required = false)
	public boolean definitionForceAutogenerateId();

	@Meta.AD(deflt = "true", required = false)
	public boolean definitionsSearchWithIndex();

	@Meta.AD(deflt = "true", required = false)
	public boolean publishToLiveByDefault();

	@Meta.AD(deflt = "true", required = false)
	public boolean publishVersionHistoryByDefault();

}