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

package com.liferay.commerce.tax.engine.fixed.web.internal.display.context;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.tax.engine.fixed.configuration.CommerceTaxByAddressTypeConfiguration;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelService;
import com.liferay.commerce.tax.engine.fixed.web.internal.frontend.CommerceTaxRateSettingDataSetConstants;
import com.liferay.commerce.tax.engine.fixed.web.internal.servlet.taglib.ui.CommerceTaxMethodAddressRateRelsScreenNavigationEntry;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateAddressRelsDisplayContext
	extends BaseCommerceTaxFixedRateDisplayContext {

	public CommerceTaxFixedRateAddressRelsDisplayContext(
		CommerceChannelLocalService commerceChannelLocalService,
		ModelResourcePermission<CommerceChannel>
			commerceChannelModelResourcePermission,
		CommerceCountryService commerceCountryService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceRegionService commerceRegionService,
		CommerceTaxMethodService commerceTaxMethodService,
		CommerceTaxFixedRateAddressRelService
			commerceTaxFixedRateAddressRelService,
		CPTaxCategoryService cpTaxCategoryService,
		PercentageFormatter percentageFormatter, RenderRequest renderRequest) {

		super(
			commerceChannelLocalService, commerceChannelModelResourcePermission,
			commerceCurrencyLocalService, commerceTaxMethodService,
			cpTaxCategoryService, percentageFormatter, renderRequest);

		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
		_commerceTaxFixedRateAddressRelService =
			commerceTaxFixedRateAddressRelService;
	}

	public String getAddTaxRateSettingURL() throws Exception {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			commerceTaxFixedRateRequestHelper.getRequest(),
			CommercePortletKeys.COMMERCE_TAX_METHODS,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceTaxFixedRateAddressRel");
		portletURL.setParameter(
			"commerceTaxMethodId", String.valueOf(getCommerceTaxMethodId()));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public List<CommerceCountry> getCommerceCountries() {
		return _commerceCountryService.getCommerceCountries(
			commerceTaxFixedRateRequestHelper.getCompanyId(), true);
	}

	public long getCommerceCountryId() throws PortalException {
		long commerceCountryId = 0;

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			getCommerceTaxFixedRateAddressRel();

		if (commerceTaxFixedRateAddressRel != null) {
			commerceCountryId =
				commerceTaxFixedRateAddressRel.getCommerceCountryId();
		}

		return commerceCountryId;
	}

	public long getCommerceRegionId() throws PortalException {
		long commerceRegionId = 0;

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			getCommerceTaxFixedRateAddressRel();

		if (commerceTaxFixedRateAddressRel != null) {
			commerceRegionId =
				commerceTaxFixedRateAddressRel.getCommerceRegionId();
		}

		return commerceRegionId;
	}

	public List<CommerceRegion> getCommerceRegions() throws PortalException {
		return _commerceRegionService.getCommerceRegions(
			getCommerceCountryId(), true);
	}

	public CommerceTaxFixedRateAddressRel getCommerceTaxFixedRateAddressRel()
		throws PortalException {

		long commerceTaxFixedRateAddressRelId = ParamUtil.getLong(
			commerceTaxFixedRateRequestHelper.getRequest(),
			"commerceTaxFixedRateAddressRelId");

		return _commerceTaxFixedRateAddressRelService.
			fetchCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRelId);
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (hasUpdateCommerceChannelPermission()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(getAddTaxRateSettingURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							commerceTaxFixedRateRequestHelper.getRequest(),
							"add-tax-rate-setting"));
					dropdownItem.setTarget("modal-lg");
				});
		}

		return creationMenu;
	}

	public String getDatasetView() throws PortalException {
		CommerceTaxMethod commerceTaxMethod = getCommerceTaxMethod();

		if (commerceTaxMethod.isPercentage()) {
			return CommerceTaxRateSettingDataSetConstants.
				COMMERCE_DATA_SET_KEY_PERCENTAGE_TAX_RATE_SETTING;
		}

		return CommerceTaxRateSettingDataSetConstants.
			COMMERCE_DATA_SET_KEY_TAX_RATE_SETTING;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CommerceTaxMethodAddressRateRelsScreenNavigationEntry.
			CATEGORY_KEY;
	}

	public boolean isTaxAppliedToShippingAddress() throws PortalException {
		CommerceTaxMethod commerceTaxMethod = getCommerceTaxMethod();

		CommerceTaxByAddressTypeConfiguration
			commerceTaxByAddressTypeConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CommerceTaxByAddressTypeConfiguration.class,
					new GroupServiceSettingsLocator(
						commerceTaxMethod.getGroupId(),
						CommerceTaxByAddressTypeConfiguration.class.getName()));

		return commerceTaxByAddressTypeConfiguration.
			taxAppliedToShippingAddress();
	}

	private final CommerceCountryService _commerceCountryService;
	private final CommerceRegionService _commerceRegionService;
	private final CommerceTaxFixedRateAddressRelService
		_commerceTaxFixedRateAddressRelService;

}