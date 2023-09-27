/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Lino Alves
 */
@ExtendedObjectClassDefinition(
	category = "dynamic-data-mapping",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.dynamic.data.mapping.configuration.DDMGroupServiceConfiguration",
	localization = "content/Language", name = "ddm-service-configuration-name"
)
public interface DDMGroupServiceConfiguration {

	@Meta.AD(
		deflt = ".gif|.jpeg|.jpg|.png",
		description = "small-image-extensions-description",
		name = "small-image-extensions", required = false
	)
	public String[] smallImageExtensions();

	@Meta.AD(
		deflt = "51200", description = "small-image-max-size-description",
		name = "small-image-max-size", required = false
	)
	public int smallImageMaxSize();

}