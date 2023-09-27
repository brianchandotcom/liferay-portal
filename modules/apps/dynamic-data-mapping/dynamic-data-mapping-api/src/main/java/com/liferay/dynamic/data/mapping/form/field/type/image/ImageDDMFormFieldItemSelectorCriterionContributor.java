/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.image;

import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.item.selector.ItemSelectorCriterion;

/**
 * @author Eudaldo Alonso
 */
public interface ImageDDMFormFieldItemSelectorCriterionContributor {

	public ItemSelectorCriterion getItemSelectorCriterion(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext);

	public boolean isVisible(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext);

}