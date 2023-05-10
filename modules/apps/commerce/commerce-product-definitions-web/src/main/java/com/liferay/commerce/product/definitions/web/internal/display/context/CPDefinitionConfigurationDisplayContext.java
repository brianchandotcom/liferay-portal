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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.servlet.taglib.ui.constants.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.service.CPDAvailabilityEstimateService;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.commerce.service.CommerceAvailabilityEstimateService;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.commerce.util.comparator.CommerceAvailabilityEstimatePriorityComparator;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionConfigurationDisplayContext
	extends CPDefinitionsDisplayContext {

	public CPDefinitionConfigurationDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		AccountGroupRelLocalService accountGroupRelLocalService,
		CommerceAvailabilityEstimateService commerceAvailabilityEstimateService,
		CommerceCatalogService commerceCatalogService,
		CommerceChannelRelService commerceChannelRelService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceLowStockActivityRegistry commerceLowStockActivityRegistry,
		ConfigurationProvider configurationProvider,
		CPDAvailabilityEstimateService cpdAvailabilityEstimateService,
		CPDefinitionInventoryEngineRegistry cpDefinitionInventoryEngineRegistry,
		CPDefinitionInventoryService cpDefinitionInventoryService,
		CPDefinitionService cpDefinitionService,
		CPMeasurementUnitLocalService cpMeasurementUnitLocalService,
		CPTaxCategoryService cpTaxCategoryService, CPFriendlyURL cpFriendlyURL,
		ItemSelector itemSelector) {

		super(
			actionHelper, httpServletRequest, accountGroupRelLocalService,
			commerceCatalogService, commerceChannelRelService,
			configurationProvider, cpDefinitionService, cpFriendlyURL,
			itemSelector);

		_commerceAvailabilityEstimateService =
			commerceAvailabilityEstimateService;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceLowStockActivityRegistry = commerceLowStockActivityRegistry;
		_cpdAvailabilityEstimateService = cpdAvailabilityEstimateService;
		_cpDefinitionInventoryEngineRegistry =
			cpDefinitionInventoryEngineRegistry;
		_cpDefinitionInventoryService = cpDefinitionInventoryService;
		_cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
		_cpTaxCategoryService = cpTaxCategoryService;
	}

	public List<CommerceAvailabilityEstimate> getCommerceAvailabilityEstimates()
		throws PortalException {

		return _commerceAvailabilityEstimateService.
			getCommerceAvailabilityEstimates(
				cpRequestHelper.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new CommerceAvailabilityEstimatePriorityComparator(true));
	}

	public String getCommerceCurrencyCode() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				themeDisplay.getCompanyId());

		if (commerceCurrency == null) {
			return StringPool.BLANK;
		}

		return commerceCurrency.getCode();
	}

	public List<CommerceLowStockActivity> getCommerceLowStockActivities() {
		return _commerceLowStockActivityRegistry.
			getCommerceLowStockActivities();
	}

	public CPDAvailabilityEstimate getCPDAvailabilityEstimate()
		throws PortalException {

		return _cpdAvailabilityEstimateService.
			fetchCPDAvailabilityEstimateByCPDefinitionId(getCPDefinitionId());
	}

	public CPDefinitionInventory getCPDefinitionInventory()
		throws PortalException {

		if (_cpDefinitionInventory != null) {
			return _cpDefinitionInventory;
		}

		_cpDefinitionInventory = _getCPDefinitionInventory();

		return _cpDefinitionInventory;
	}

	public List<CPDefinitionInventoryEngine> getCPDefinitionInventoryEngines() {
		return _cpDefinitionInventoryEngineRegistry.
			getCPDefinitionInventoryEngines();
	}

	public String getCPMeasurementUnitName(int type) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitLocalService.fetchPrimaryCPMeasurementUnit(
				themeDisplay.getCompanyId(), type);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit.getName(themeDisplay.getLanguageId());
		}

		return StringPool.BLANK;
	}

	public List<CPTaxCategory> getCPTaxCategories() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _cpTaxCategoryService.getCPTaxCategories(
			themeDisplay.getCompanyId());
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_CONFIGURATION;
	}

	private CPDefinitionInventory _getCPDefinitionInventory()
		throws PortalException {

		RenderRequest renderRequest = cpRequestHelper.getRenderRequest();

		CPDefinitionInventory cpDefinitionInventory =
			(CPDefinitionInventory)renderRequest.getAttribute(
				CPWebKeys.CP_DEFINITION_INVENTORY);

		if (cpDefinitionInventory != null) {
			return cpDefinitionInventory;
		}

		long cpDefinitionId = ParamUtil.getLong(
			renderRequest, "cpDefinitionId");

		if (cpDefinitionId > 0) {
			cpDefinitionInventory =
				_cpDefinitionInventoryService.
					fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
		}

		if (cpDefinitionInventory != null) {
			renderRequest.setAttribute(
				CPWebKeys.CP_DEFINITION_INVENTORY, cpDefinitionInventory);
		}

		return cpDefinitionInventory;
	}

	private final CommerceAvailabilityEstimateService
		_commerceAvailabilityEstimateService;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final CommerceLowStockActivityRegistry
		_commerceLowStockActivityRegistry;
	private final CPDAvailabilityEstimateService
		_cpdAvailabilityEstimateService;
	private CPDefinitionInventory _cpDefinitionInventory;
	private final CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;
	private final CPDefinitionInventoryService _cpDefinitionInventoryService;
	private final CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;
	private final CPTaxCategoryService _cpTaxCategoryService;

}