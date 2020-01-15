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

package com.liferay.layout.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Rubén Pulido
 */
@ExtendedObjectClassDefinition(category = "pages")
@Meta.OCD(
	id = "com.liferay.layout.internal.configuration.LayoutConverterConfiguration",
	localization = "content/Language",
	name = "layout-converter-configuration-name"
)
public interface LayoutConverterConfiguration {

	@Meta.AD(
		deflt = "1_column,2_columns_i,2_columns_ii,2_columns_iii,3_columns,1_2_columns_i,1_2_columns_ii,1_2_1_columns_i,1_2_1_columns_ii,1_3_1_columns,1_3_2_columns,2_1_2_columns,2_2_columns,3_2_3_columns",
		description = "layout-converter-configuration-verified-layout-template-ids-description",
		name = "verified-layout-template-ids", required = false
	)
	public String[] verifiedLayoutTemplateIds();

}