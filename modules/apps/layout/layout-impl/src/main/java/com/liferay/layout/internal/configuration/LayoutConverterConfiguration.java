/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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