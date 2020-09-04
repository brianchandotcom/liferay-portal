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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.ddm.DDMHelper;
import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPInstanceDisplayContext extends BaseCPDefinitionsDisplayContext {

	public CPInstanceDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommercePriceFormatter commercePriceFormatter,
		CommerceProductPriceCalculation commerceProductPriceCalculation,
		CPDefinitionOptionRelService cpDefinitionOptionRelService,
		CPInstanceHelper cpInstanceHelper,
		CPMeasurementUnitLocalService cpMeasurementUnitLocalService,
		DDMHelper ddmHelper) {

		super(actionHelper, httpServletRequest);

		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commercePriceFormatter = commercePriceFormatter;
		_commerceProductPriceCalculation = commerceProductPriceCalculation;
		_cpDefinitionOptionRelService = cpDefinitionOptionRelService;
		_cpInstanceHelper = cpInstanceHelper;
		_cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
		_ddmHelper = ddmHelper;
	}

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpInstanceJsonParse(long cpInstanceId)
		throws PortalException {

		if (cpInstanceId <= 0) {
			return Collections.emptyMap();
		}

		return _cpInstanceHelper.getCPInstanceCPDefinitionOptionRelsMap(
			cpInstanceId);
	}

	public String getCommerceCurrencyCode() throws PortalException {
		CommerceCurrency commerceCurrency = getCommerceCurrency();

		if (commerceCurrency != null) {
			return commerceCurrency.getCode();
		}

		return StringPool.BLANK;
	}

	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels()
		throws PortalException {

		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return Collections.emptyList();
		}

		return _cpDefinitionOptionRelService.getCPDefinitionOptionRels(
			cpDefinition.getCPDefinitionId(), true);
	}

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws PortalException {

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelListMap = cpInstanceJsonParse(
				getCPInstanceId());

		if (cpDefinitionOptionRelListMap.isEmpty() ||
			!cpDefinitionOptionRelListMap.containsKey(cpDefinitionOptionRel)) {

			return Collections.emptyList();
		}

		return cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance != null) {
			return _cpInstance;
		}

		_cpInstance = actionHelper.getCPInstance(
			cpRequestHelper.getRenderRequest());

		return _cpInstance;
	}

	public long getCPInstanceId() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		if (cpInstance == null) {
			return 0;
		}

		return cpInstance.getCPInstanceId();
	}

	public String getCPMeasurementUnitName(int type) {
		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitLocalService.fetchPrimaryCPMeasurementUnit(
				cpRequestHelper.getCompanyId(), type);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit.getName(cpRequestHelper.getLocale());
		}

		return StringPool.BLANK;
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(_getEditCPInstancePortletURL());
				dropdownItem.setLabel(
					LanguageUtil.get(cpRequestHelper.getRequest(), "add-sku"));
				dropdownItem.setTarget("sidePanel");
			}
		).build();

		CPDefinition cpDefinition = getCPDefinition();

		if ((cpDefinition != null) && !cpDefinition.isIgnoreSKUCombinations()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(_getAddMultipleCPInstancePortletURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							cpRequestHelper.getRequest(),
							"generate-all-sku-combinations"));
				});
		}

		return creationMenu;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		if (getCPDefinitionId() > 0) {
			portletURL.setParameter(
				"mvcRenderCommandName", "editProductDefinition");
		}
		else {
			portletURL.setParameter("mvcRenderCommandName", "viewInstances");
			portletURL.setParameter(
				"catalogNavigationItem", "view-all-instances");
		}

		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		return portletURL;
	}

	public BigDecimal getPrice() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		if (cpInstance == null) {
			return BigDecimal.ZERO;
		}

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.getBasePrice(
				cpInstance.getCPInstanceId(), getCommerceCurrency());

		return round(commerceMoney.getPrice());
	}

	public BigDecimal getPromoPrice() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		if (cpInstance == null) {
			return BigDecimal.ZERO;
		}

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.getBasePromoPrice(
				cpInstance.getCPInstanceId(), getCommerceCurrency());

		return round(commerceMoney.getPrice());
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS;
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(), CPInstance.class.getName(),
			getCPInstanceId(), null);
	}

	public String renderOptions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		CPDefinition cpDefinition = getCPDefinition();

		return _ddmHelper.renderCPInstanceOptions(
			getCPDefinitionId(), null, cpDefinition.isIgnoreSKUCombinations(),
			renderRequest, renderResponse,
			_cpInstanceHelper.getCPDefinitionOptionRelsMap(
				getCPDefinitionId(), true, false));
	}

	public BigDecimal round(BigDecimal value) throws PortalException {
		CommerceCurrency commerceCurrency = getCommerceCurrency();

		if (commerceCurrency == null) {
			return value;
		}

		return commerceCurrency.round(value);
	}

	protected CommerceCurrency getCommerceCurrency() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();

		CommerceCatalog commerceCatalog = cpDefinition.getCommerceCatalog();

		return _commerceCurrencyLocalService.getCommerceCurrency(
			commerceCatalog.getCompanyId(),
			commerceCatalog.getCommerceCurrencyCode());
	}

	private String _getAddMultipleCPInstancePortletURL() throws Exception {
		LiferayPortletResponse liferayPortletResponse =
			cpRequestHelper.getLiferayPortletResponse();

		ActionURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editProductInstance");
		portletURL.setParameter(Constants.CMD, Constants.ADD_MULTIPLE);
		portletURL.setParameter("redirect", cpRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));

		return portletURL.toString();
	}

	private String _getEditCPInstancePortletURL() throws Exception {
		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CPDefinition.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter("mvcRenderCommandName", "editProductInstance");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CommerceProductPriceCalculation
		_commerceProductPriceCalculation;
	private final CPDefinitionOptionRelService _cpDefinitionOptionRelService;
	private CPInstance _cpInstance;
	private final CPInstanceHelper _cpInstanceHelper;
	private final CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;
	private final DDMHelper _ddmHelper;

}