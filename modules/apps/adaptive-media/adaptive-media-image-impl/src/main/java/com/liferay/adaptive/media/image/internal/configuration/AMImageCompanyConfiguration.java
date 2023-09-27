/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "adaptive-media", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.adaptive.media.image.internal.configuration.AMImageCompanyConfiguration",
	localization = "content/Language",
	name = "adaptive-media-image-company-configuration-name"
)
public interface AMImageCompanyConfiguration {

	@Meta.AD(name = "image-variants", required = false)
	public String[] imageVariants();

}