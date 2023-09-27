/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		validationExpression = "isValidURLSeparator(assetCategoryURLSeparator)"
	)
	public String assetCategoryURLSeparator();

	@DDMFormField(
		label = "%product-url-separator", tip = "%product-url-separator-help",
		validationExpression = "isValidURLSeparator(productURLSeparator)"
	)
	public String productURLSeparator();

}