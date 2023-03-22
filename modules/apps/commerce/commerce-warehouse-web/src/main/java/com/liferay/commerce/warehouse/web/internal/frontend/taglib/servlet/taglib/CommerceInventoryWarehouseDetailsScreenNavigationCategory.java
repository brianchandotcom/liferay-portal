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

package com.liferay.commerce.warehouse.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.country.CommerceCountryManager;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.warehouse.web.internal.constants.CommerceInventoryWarehouseScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "screen.navigation.category.order:Integer=10",
	service = ScreenNavigationCategory.class
)
public class CommerceInventoryWarehouseDetailsScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceInventoryWarehouseScreenNavigationConstants.
			CATEGORY_KEY_DETAILS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return language.get(
			resourceBundle,
			CommerceInventoryWarehouseScreenNavigationConstants.
				CATEGORY_KEY_DETAILS);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceInventoryWarehouseScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_INVENTORY_WAREHOUSE_GENERAL;
	}

	@Reference
	protected CommerceChannelService commerceChannelService;

	@Reference
	protected CommerceCountryManager commerceCountryManager;

	@Reference
	protected CountryService countryService;

	@Reference
	protected Language language;

	@Reference
	protected RegionService regionService;

}