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

package com.liferay.product.menu.my.space.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"panel.category.key=" + PanelCategoryKeys.MY_SPACE},
	service = PanelCategory.class
)
public class MyAccountPanelCategory extends BasePanelCategory {

	@Override
	public String getIconCssClass() {
		return "icon-user";
	}

	@Override
	public String getKey() {
		return PanelCategoryKeys.MY_SPACE_PRODUCTIVITY_CENTER;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "my-account");
	}

}