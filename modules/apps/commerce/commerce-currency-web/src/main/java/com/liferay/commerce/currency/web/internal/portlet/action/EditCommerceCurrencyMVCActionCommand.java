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

package com.liferay.commerce.currency.web.internal.portlet.action;

import com.liferay.commerce.currency.configuration.CommerceCurrencyConfiguration;
import com.liferay.commerce.currency.constants.CommerceCurrencyExchangeRateConstants;
import com.liferay.commerce.currency.constants.CommerceCurrencyPortletKeys;
import com.liferay.commerce.currency.exception.CommerceCurrencyCodeException;
import com.liferay.commerce.currency.exception.CommerceCurrencyFractionDigitsException;
import com.liferay.commerce.currency.exception.CommerceCurrencyNameException;
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommerceCurrencyPortletKeys.COMMERCE_CURRENCY,
		"mvc.command.name=/commerce_currency/edit_commerce_currency"
	},
	service = MVCActionCommand.class
)
public class EditCommerceCurrencyMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCommerceCurrencies(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceCurrencyIds = null;

		long commerceCurrencyId = ParamUtil.getLong(
			actionRequest, "commerceCurrencyId");

		if (commerceCurrencyId > 0) {
			deleteCommerceCurrencyIds = new long[] {commerceCurrencyId};
		}
		else {
			deleteCommerceCurrencyIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceCurrencyIds"),
				0L);
		}

		for (long deleteCommerceCurrencyId : deleteCommerceCurrencyIds) {
			_commerceCurrencyService.deleteCommerceCurrency(
				deleteCommerceCurrencyId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceCurrencies(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceCurrency(actionRequest);
			}
			else if (cmd.equals("setActive")) {
				setActive(actionRequest);
			}
			else if (cmd.equals("setPrimary")) {
				setPrimary(actionRequest);
			}
			else if (cmd.equals("updateExchangeRates")) {
				updateExchangeRates(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCurrencyException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof CommerceCurrencyCodeException ||
					 exception instanceof
						 CommerceCurrencyFractionDigitsException ||
					 exception instanceof CommerceCurrencyNameException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/commerce_currency/edit_commerce_currency");
			}
			else {
				throw exception;
			}
		}
	}

	protected void setActive(ActionRequest actionRequest)
		throws PortalException {

		long commerceCurrencyId = ParamUtil.getLong(
			actionRequest, "commerceCurrencyId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		_commerceCurrencyService.setActive(commerceCurrencyId, active);
	}

	protected void setPrimary(ActionRequest actionRequest)
		throws PortalException {

		long commerceCurrencyId = ParamUtil.getLong(
			actionRequest, "commerceCurrencyId");

		boolean primary = ParamUtil.getBoolean(actionRequest, "primary");

		_commerceCurrencyService.setPrimary(commerceCurrencyId, primary);
	}

	protected CommerceCurrency updateCommerceCurrency(
			ActionRequest actionRequest)
		throws PortalException {

		int maxFractionDigits = ParamUtil.getInteger(
			actionRequest, "maxFractionDigits");
		int minFractionDigits = ParamUtil.getInteger(
			actionRequest, "minFractionDigits");

		if ((maxFractionDigits < 0) || (minFractionDigits < 0) ||
			(maxFractionDigits < minFractionDigits)) {

			throw new CommerceCurrencyFractionDigitsException();
		}

		long commerceCurrencyId = ParamUtil.getLong(
			actionRequest, "commerceCurrencyId");

		String code = ParamUtil.getString(actionRequest, "code");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String rate = ParamUtil.getString(actionRequest, "rate");
		Map<Locale, String> formatPatternMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "formatPattern");

		String roundingMode = ParamUtil.getString(
			actionRequest, "roundingMode");
		boolean primary = ParamUtil.getBoolean(actionRequest, "primary");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		String symbol = ParamUtil.getString(actionRequest, "symbol");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		CommerceCurrency commerceCurrency = null;

		if (commerceCurrencyId <= 0) {
			commerceCurrency = _commerceCurrencyService.addCommerceCurrency(
				code, nameMap, symbol, new BigDecimal(rate), formatPatternMap,
				maxFractionDigits, minFractionDigits, roundingMode, primary,
				priority, active);
		}
		else {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceCurrency.class.getName(), actionRequest);

			commerceCurrency = _commerceCurrencyService.updateCommerceCurrency(
				commerceCurrencyId, code, nameMap, symbol, new BigDecimal(rate),
				formatPatternMap, maxFractionDigits, minFractionDigits,
				roundingMode, primary, priority, active, serviceContext);
		}

		return commerceCurrency;
	}

	protected void updateExchangeRates(ActionRequest actionRequest)
		throws Exception {

		long[] updateCommerceCurrencyExchangeRateIds = null;

		long commerceCurrencyId = ParamUtil.getLong(
			actionRequest, "commerceCurrencyId");

		if (commerceCurrencyId > 0) {
			updateCommerceCurrencyExchangeRateIds = new long[] {
				commerceCurrencyId
			};
		}
		else {
			updateCommerceCurrencyExchangeRateIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "updateCommerceCurrencyExchangeRateIds"),
				0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceCurrency.class.getName(), actionRequest);

		CommerceCurrencyConfiguration commerceCurrencyConfiguration =
			_configurationProvider.getConfiguration(
				CommerceCurrencyConfiguration.class,
				new CompanyServiceSettingsLocator(
					serviceContext.getCompanyId(),
					CommerceCurrencyExchangeRateConstants.SERVICE_NAME));

		String exchangeRateProviderKey =
			commerceCurrencyConfiguration.defaultExchangeRateProviderKey();

		for (long updateCommerceCurrencyExchangeRateId :
				updateCommerceCurrencyExchangeRateIds) {

			_commerceCurrencyService.updateExchangeRate(
				updateCommerceCurrencyExchangeRateId, exchangeRateProviderKey);
		}
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private ConfigurationProvider _configurationProvider;

}