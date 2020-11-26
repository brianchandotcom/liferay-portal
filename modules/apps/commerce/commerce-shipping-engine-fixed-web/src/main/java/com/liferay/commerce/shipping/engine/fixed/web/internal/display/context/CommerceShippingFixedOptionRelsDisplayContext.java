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

package com.liferay.commerce.shipping.engine.fixed.web.internal.display.context;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.constants.CommerceShippingEngineFixedWebKeys;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.shipping.engine.fixed.web.internal.servlet.taglib.ui.CommerceShippingMethodFixedOptionSettingsScreenNavigationEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionRelsDisplayContext
	extends BaseCommerceShippingFixedOptionDisplayContext {

	public CommerceShippingFixedOptionRelsDisplayContext(
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceCountryService commerceCountryService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceRegionService commerceRegionService,
		CommerceShippingMethodService commerceShippingMethodService,
		CommerceShippingFixedOptionService commerceShippingFixedOptionService,
		CommerceInventoryWarehouseService commerceInventoryWarehouseService,
		CommerceShippingFixedOptionRelService
			commerceShippingFixedOptionRelService,
		CPMeasurementUnitLocalService cpMeasurementUnitLocalService,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		super(
			commerceChannelLocalService, commerceCurrencyLocalService,
			commerceShippingMethodService, renderRequest, renderResponse);

		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
		_commerceShippingFixedOptionService =
			commerceShippingFixedOptionService;
		_commerceInventoryWarehouseService = commerceInventoryWarehouseService;
		_commerceShippingFixedOptionRelService =
			commerceShippingFixedOptionRelService;
		_cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
		_portal = portal;
	}

	public String getAddShippingFixedOptionURL() throws Exception {
		PortletURL editCommerceChannelPortletURL =
			_portal.getControlPanelPortletURL(
				renderRequest, CommercePortletKeys.COMMERCE_SHIPPING_METHODS,
				PortletRequest.RENDER_PHASE);

		editCommerceChannelPortletURL.setParameter(
			"mvcRenderCommandName", "editCommerceShippingFixedOptionRel");
		editCommerceChannelPortletURL.setParameter(
			"commerceShippingMethodId",
			String.valueOf(getCommerceShippingMethodId()));

		editCommerceChannelPortletURL.setWindowState(LiferayWindowState.POP_UP);

		return editCommerceChannelPortletURL.toString();
	}

	public List<CommerceCountry> getCommerceCountries() {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _commerceCountryService.getCommerceCountries(
			themeDisplay.getCompanyId(), true);
	}

	public long getCommerceCountryId() throws PortalException {
		long commerceCountryId = 0;

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			getCommerceShippingFixedOptionRel();

		if (commerceShippingFixedOptionRel != null) {
			commerceCountryId =
				commerceShippingFixedOptionRel.getCommerceCountryId();
		}

		return commerceCountryId;
	}

	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses()
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			getCommerceShippingMethod();

		return _commerceInventoryWarehouseService.
			getCommerceInventoryWarehouses(
				commerceShippingMethod.getCompanyId(),
				commerceShippingMethod.getGroupId(), true);
	}

	public long getCommerceRegionId() throws PortalException {
		long commerceRegionId = 0;

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			getCommerceShippingFixedOptionRel();

		if (commerceShippingFixedOptionRel != null) {
			commerceRegionId =
				commerceShippingFixedOptionRel.getCommerceRegionId();
		}

		return commerceRegionId;
	}

	public List<CommerceRegion> getCommerceRegions() throws PortalException {
		return _commerceRegionService.getCommerceRegions(
			getCommerceCountryId(), true);
	}

	public CommerceShippingFixedOptionRel getCommerceShippingFixedOptionRel()
		throws PortalException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			(CommerceShippingFixedOptionRel)renderRequest.getAttribute(
				CommerceShippingEngineFixedWebKeys.
					COMMERCE_SHIPPING_FIXED_OPTION_REL);

		if (commerceShippingFixedOptionRel != null) {
			return commerceShippingFixedOptionRel;
		}

		long commerceShippingFixedOptionRelId = ParamUtil.getLong(
			renderRequest, "commerceShippingFixedOptionRelId");

		if (commerceShippingFixedOptionRelId > 0) {
			commerceShippingFixedOptionRel =
				_commerceShippingFixedOptionRelService.
					fetchCommerceShippingFixedOptionRel(
						commerceShippingFixedOptionRelId);
		}

		if (commerceShippingFixedOptionRel != null) {
			renderRequest.setAttribute(
				CommerceShippingEngineFixedWebKeys.
					COMMERCE_SHIPPING_FIXED_OPTION_REL,
				commerceShippingFixedOptionRel);
		}

		return commerceShippingFixedOptionRel;
	}

	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions()
		throws PortalException {

		return _commerceShippingFixedOptionService.
			getCommerceShippingFixedOptions(
				getCommerceShippingMethodId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
	}

	public String getCPMeasurementUnitName(int type) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitLocalService.fetchPrimaryCPMeasurementUnit(
				themeDisplay.getCompanyId(), type);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit.getName(themeDisplay.getLanguageId());
		}

		return StringPool.BLANK;
	}

	public CreationMenu getCreationMenu() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(getAddShippingFixedOptionURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						themeDisplay.getRequest(),
						"add-shipping-option-setting"));
				dropdownItem.setTarget("sidePanel");
			}
		).build();
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CommerceShippingMethodFixedOptionSettingsScreenNavigationEntry.
			CATEGORY_KEY;
	}

	public boolean isVisible() throws PortalException {
		List<CommerceShippingFixedOption> commerceShippingFixedOptions =
			getCommerceShippingFixedOptions();

		if (commerceShippingFixedOptions.isEmpty()) {
			return false;
		}

		return true;
	}

	private final CommerceCountryService _commerceCountryService;
	private final CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;
	private final CommerceRegionService _commerceRegionService;
	private final CommerceShippingFixedOptionRelService
		_commerceShippingFixedOptionRelService;
	private final CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;
	private final CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;
	private final Portal _portal;

}