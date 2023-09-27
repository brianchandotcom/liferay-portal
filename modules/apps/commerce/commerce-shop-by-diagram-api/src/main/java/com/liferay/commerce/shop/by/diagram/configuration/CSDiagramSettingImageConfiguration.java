/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Meta.AD(deflt = "1", name = "radius", required = false)
	public double radius();

}