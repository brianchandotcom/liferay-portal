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
import com.liferay.commerce.currency.exception.CommerceCurrencyNameException;
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommerceCurrencyPortletKeys.COMMERCE_CURRENCY,
		"mvc.command.name=/commerce_currency/edit_exchange_rate"
	},
	service = MVCActionCommand.class
)
public class EditExchangeRateMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			if (cmd.equals(Constants.UPDATE)) {
				_updateExchangeRateConfiguration(actionRequest, serviceContext);

				updateExchangeRates(serviceContext);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCurrencyException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof CommerceCurrencyCodeException ||
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

	protected void updateExchangeRates(ServiceContext serviceContext)
		throws Exception {

		CommerceCurrencyConfiguration commerceCurrencyConfiguration =
			_configurationProvider.getConfiguration(
				CommerceCurrencyConfiguration.class,
				new CompanyServiceSettingsLocator(
					serviceContext.getCompanyId(),
					CommerceCurrencyExchangeRateConstants.SERVICE_NAME));

		List<CommerceCurrency> commerceCurrencies =
			_commerceCurrencyService.getCommerceCurrencies(
				serviceContext.getCompanyId(), true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (CommerceCurrency commerceCurrency : commerceCurrencies) {
			_commerceCurrencyService.updateExchangeRate(
				commerceCurrency.getCommerceCurrencyId(),
				commerceCurrencyConfiguration.defaultExchangeRateProviderKey());
		}
	}

	private void _updateExchangeRateConfiguration(
			ActionRequest actionRequest, ServiceContext serviceContext)
		throws Exception {

		Map<String, String> parameterMap = PropertiesParamUtil.getProperties(
			actionRequest, "exchangeRateConfiguration--");

		Settings settings = _settingsFactory.getSettings(
			new CompanyServiceSettingsLocator(
				serviceContext.getCompanyId(),
				CommerceCurrencyExchangeRateConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private SettingsFactory _settingsFactory;

}