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

package com.liferay.commerce.tax.engine.fixed.web.internal.frontend;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProvider;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableSchema;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilder;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilderFactory;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaField;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateService;
import com.liferay.commerce.tax.engine.fixed.web.internal.model.TaxRate;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.data.provider.key=" + CommerceTaxRateClayTable.NAME,
		"commerce.data.set.display.name=" + CommerceTaxRateClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceTaxRateClayTable
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider, CommerceDataSetDataProvider<TaxRate> {

	public static final String NAME = "tax-rates";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayTableActions = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			TaxRate taxRate = (TaxRate)model;

			long commerceChannelId = ParamUtil.getLong(
				httpServletRequest, "commerceChannelId");

			CommerceChannel commerceChannel =
				_commerceChannelService.getCommerceChannel(commerceChannelId);

			if (!_commerceChannelModelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), commerceChannel,
					ActionKeys.UPDATE)) {

				return clayTableActions;
			}

			ClayDataSetAction editClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK,
				_getTaxRateEditURL(httpServletRequest, taxRate.getTaxRateId()),
				StringPool.BLANK, LanguageUtil.get(httpServletRequest, "edit"),
				null, false, false);

			editClayDataSetAction.setTarget("sidePanel");

			clayTableActions.add(editClayDataSetAction);

			ClayDataSetAction deleteClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK,
				_getTaxRateDeleteURL(
					httpServletRequest, taxRate.getTaxRateId()),
				StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "delete"), null, false,
				false);

			clayTableActions.add(deleteClayDataSetAction);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return clayTableActions;
	}

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");
		long commerceTaxMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceTaxMethodId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		return _commerceTaxFixedRateService.getCommerceTaxFixedRatesCount(
			commerceChannel.getGroupId(), commerceTaxMethodId);
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		ClayTableSchemaField nameField = clayTableSchemaBuilder.addField(
			"name", "name");

		nameField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addField("rate", "rate");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<TaxRate> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");
		long commerceTaxMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceTaxMethodId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				commerceChannel.getCompanyId(),
				commerceChannel.getCommerceCurrencyCode());

		CommerceTaxMethod commerceTaxMethod =
			_commerceTaxMethodLocalService.getCommerceTaxMethod(
				commerceTaxMethodId);

		List<CommerceTaxFixedRate> commerceTaxFixedRates =
			_commerceTaxFixedRateService.getCommerceTaxFixedRates(
				commerceChannel.getGroupId(), commerceTaxMethodId,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		List<TaxRate> taxRates = new ArrayList<>();

		for (CommerceTaxFixedRate commerceTaxFixedRate :
				commerceTaxFixedRates) {

			CPTaxCategory cpTaxCategory =
				commerceTaxFixedRate.getCPTaxCategory();

			taxRates.add(
				new TaxRate(
					cpTaxCategory.getName(themeDisplay.getLanguageId()),
					_getLocalizedRate(
						commerceCurrency, commerceTaxMethod.isPercentage(),
						commerceTaxFixedRate.getRate(),
						_portal.getLocale(httpServletRequest)),
					commerceTaxFixedRate.getCommerceTaxFixedRateId()));
		}

		return taxRates;
	}

	private String _getLocalizedRate(
			CommerceCurrency commerceCurrency, boolean isPercentage,
			double rate, Locale locale)
		throws PortalException {

		BigDecimal bigDecimalPercentage = new BigDecimal(rate);

		if (isPercentage) {
			return _percentageFormatter.getLocalizedPercentage(
				locale, commerceCurrency.getMaxFractionDigits(),
				commerceCurrency.getMinFractionDigits(), bigDecimalPercentage);
		}

		CommerceMoney commerceMoney = _commerceMoneyFactory.create(
			commerceCurrency, bigDecimalPercentage);

		return commerceMoney.format(locale);
	}

	private String _getTaxRateDeleteURL(
		HttpServletRequest httpServletRequest, long taxRateId) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_TAX_METHODS,
			PortletRequest.ACTION_PHASE);

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editCommerceTaxFixedRate");
		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter(
			"commerceTaxFixedRateId", String.valueOf(taxRateId));

		return portletURL.toString();
	}

	private String _getTaxRateEditURL(
			HttpServletRequest httpServletRequest, long taxRateId)
		throws Exception {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceTaxMethod.class.getName(),
			PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceTaxFixedRate");

		long commerceTaxMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceTaxMethodId");

		portletURL.setParameter(
			"commerceTaxMethodId", String.valueOf(commerceTaxMethodId));

		portletURL.setParameter(
			"commerceTaxFixedRateId", String.valueOf(taxRateId));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannel)"
	)
	private ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommerceTaxFixedRateService _commerceTaxFixedRateService;

	@Reference
	private CommerceTaxMethodLocalService _commerceTaxMethodLocalService;

	@Reference
	private PercentageFormatter _percentageFormatter;

	@Reference
	private Portal _portal;

}