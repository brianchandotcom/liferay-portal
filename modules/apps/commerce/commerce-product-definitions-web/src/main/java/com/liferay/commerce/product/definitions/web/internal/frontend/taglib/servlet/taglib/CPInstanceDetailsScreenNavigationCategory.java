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

package com.liferay.commerce.product.definitions.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.product.servlet.taglib.ui.constants.CPInstanceScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "screen.navigation.category.order:Integer=10",
	service = ScreenNavigationCategory.class
)
public class CPInstanceDetailsScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CPInstanceScreenNavigationConstants.CATEGORY_KEY_DETAILS;
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(
			locale, CPInstanceScreenNavigationConstants.CATEGORY_KEY_DETAILS);
	}

	@Override
	public String getScreenNavigationKey() {
		return CPInstanceScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_CP_INSTANCE_GENERAL;
	}

	@Reference
	protected Language language;

}