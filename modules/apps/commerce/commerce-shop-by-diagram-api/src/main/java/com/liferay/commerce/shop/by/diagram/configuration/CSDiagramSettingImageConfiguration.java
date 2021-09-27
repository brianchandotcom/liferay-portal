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

package com.liferay.commerce.shop.by.diagram.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "catalog", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.shop.by.diagram.configuration.CSDiagramSettingImageConfiguration",
	localization = "content/Language",
	name = "commerce-shop-by-diagram-setting-image-configuration-name"
)
public interface CSDiagramSettingImageConfiguration {

	@Meta.AD(
		deflt = "#Livello_Testi > text,[id*=MTEXT] > text",
		name = "image-css-selectors", required = false
	)
	public String[] imageCSSSelectors();

	@Meta.AD(
		deflt = ".gif,.jpeg,.jpg,.png,.svg", name = "image-extensions",
		required = false
	)
	public String[] imageExtensions();

	@Meta.AD(deflt = "5242880", name = "image-max-size", required = false)
	public long imageMaxSize();

	@Meta.AD(deflt = "1", name = "radius")
	public double radius();

}