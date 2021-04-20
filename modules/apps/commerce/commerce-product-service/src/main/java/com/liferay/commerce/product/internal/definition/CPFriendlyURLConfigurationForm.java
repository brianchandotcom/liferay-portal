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

package com.liferay.commerce.product.internal.definition;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;

/**
 * @author Pei-Jung Lan
 */
@DDMForm
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SINGLE_PAGE_MODE,
	value = {
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"assetCategoryURLSeparator",
								"productURLSeparator"
							}
						)
					}
				)
			}
		)
	}
)
public interface CPFriendlyURLConfigurationForm {

	@DDMFormField(
		label = "%asset-category-url-separator",
		tip = "%asset-category-url-separator-help",
		validationExpression = "(assetCategoryURLSeparator != '-') && (assetCategoryURLSeparator != '~') && (assetCategoryURLSeparator != 'b') && (assetCategoryURLSeparator != 'd') && (assetCategoryURLSeparator != 'w')"
	)
	public String assetCategoryURLSeparator();

	@DDMFormField(
		label = "%product-url-separator", tip = "%product-url-separator-help",
		validationExpression = "(productURLSeparator != '-') && (productURLSeparator != '~') && (productURLSeparator != 'b') && (productURLSeparator != 'd') && (productURLSeparator != 'w')"
	)
	public String productURLSeparator();

}