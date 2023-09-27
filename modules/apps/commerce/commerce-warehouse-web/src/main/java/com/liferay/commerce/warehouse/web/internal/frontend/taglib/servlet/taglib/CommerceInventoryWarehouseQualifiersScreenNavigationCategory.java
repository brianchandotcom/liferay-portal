/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.warehouse.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.country.CommerceCountryManager;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseRelService;
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
	property = "screen.navigation.category.order:Integer=20",
	service = ScreenNavigationCategory.class
)
public class CommerceInventoryWarehouseQualifiersScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceInventoryWarehouseScreenNavigationConstants.
			CATEGORY_KEY_QUALIFIERS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return language.get(resourceBundle, "eligibility");
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
	protected CommerceInventoryWarehouseRelService
		commerceInventoryWarehouseRelService;

	@Reference
	protected CountryService countryService;

	@Reference
	protected Language language;

	@Reference
	protected RegionService regionService;

}