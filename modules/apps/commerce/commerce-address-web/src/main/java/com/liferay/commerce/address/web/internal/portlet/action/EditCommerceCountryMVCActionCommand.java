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

package com.liferay.commerce.address.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchCountryException;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.portal.kernel.exception.CountryA2Exception;
import com.liferay.portal.kernel.exception.CountryA3Exception;
import com.liferay.portal.kernel.exception.CountryNameException;
import com.liferay.portal.kernel.exception.DuplicateCountryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_COUNTRY,
		"mvc.command.name=/commerce_country/edit_commerce_country"
	},
	service = MVCActionCommand.class
)
public class EditCommerceCountryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Country country = _updateCountry(actionRequest);

				String redirect = _getSaveAndContinueRedirect(
					actionRequest, country);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCountries(actionRequest);
			}
			else if (cmd.equals("setActive")) {
				setActive(actionRequest);
			}
			else if (cmd.equals("updateChannels")) {
				Callable<Object> commerceCountryChannelsCallable =
					new CommerceCountryChannelsCallable(actionRequest);

				TransactionInvokerUtil.invoke(
					_transactionConfig, commerceCountryChannelsCallable);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof NoSuchCountryException ||
				throwable instanceof PrincipalException) {

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (throwable instanceof CountryA2Exception ||
					 throwable instanceof CountryA3Exception ||
					 throwable instanceof CountryNameException ||
					 throwable instanceof DuplicateCountryException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/commerce_country/edit_commerce_country");
			}
			else {
				_log.error(throwable, throwable);

				throw new Exception(throwable);
			}
		}
	}

	protected void setActive(ActionRequest actionRequest)
		throws PortalException {

		long countryId = ParamUtil.getLong(actionRequest, "countryId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		_countryService.updateActive(countryId, active);
	}

	private void _deleteCountries(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCountryIds = null;

		long countryId = ParamUtil.getLong(actionRequest, "countryId");

		if (countryId > 0) {
			deleteCountryIds = new long[] {countryId};
		}
		else {
			deleteCountryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCountryIds"), 0L);
		}

		for (long deleteCountryId : deleteCountryIds) {
			_countryService.deleteCountry(deleteCountryId);
		}
	}

	private String _getSaveAndContinueRedirect(
			ActionRequest actionRequest, Country country)
		throws Exception {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommercePortletKeys.COMMERCE_COUNTRY,
			PortletRequest.RENDER_PHASE);

		if (country != null) {
			portletURL.setParameter(
				"mvcRenderCommandName",
				"/commerce_country/edit_commerce_country");
			portletURL.setParameter(
				"countryId", String.valueOf(country.getCountryId()));

			String backURL = ParamUtil.getString(actionRequest, "backURL");

			portletURL.setParameter("backURL", backURL);
		}

		return portletURL.toString();
	}

	private void _updateChannels(ActionRequest actionRequest)
		throws PortalException {

		long countryId = ParamUtil.getLong(actionRequest, "countryId");

		long[] commerceChannelIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "commerceChannelIds"), 0L);

		boolean channelFilterEnabled = ParamUtil.getBoolean(
			actionRequest, "channelFilterEnabled");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceChannelRel.class.getName(), actionRequest);

		_commerceChannelRelService.deleteCommerceChannelRels(
			Country.class.getName(), countryId);

		for (long commerceChannelId : commerceChannelIds) {
			if (commerceChannelId == 0) {
				continue;
			}

			_commerceChannelRelService.addCommerceChannelRel(
				Country.class.getName(), countryId, commerceChannelId,
				serviceContext);
		}

		_countryService.updateGroupFilterEnabled(
			countryId, channelFilterEnabled);
	}

	private Country _updateCountry(ActionRequest actionRequest)
		throws Exception {

		long countryId = ParamUtil.getLong(actionRequest, "countryId");

		String a2 = ParamUtil.getString(actionRequest, "a2");
		String a3 = ParamUtil.getString(actionRequest, "a3");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		boolean billingAllowed = ParamUtil.getBoolean(
			actionRequest, "billingAllowed");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String number = ParamUtil.getString(actionRequest, "number");
		double position = ParamUtil.getDouble(actionRequest, "position");
		boolean shippingAllowed = ParamUtil.getBoolean(
			actionRequest, "shippingAllowed");
		boolean subjectToVAT = ParamUtil.getBoolean(
			actionRequest, "subjectToVAT");

		Country country = null;

		if (countryId <= 0) {
			country = _countryService.addCountry(
				a2, a3, active, billingAllowed, null,
				nameMap.get(LocaleUtil.getDefault()), number, position,
				shippingAllowed, subjectToVAT, false,
				ServiceContextFactory.getInstance(
					Country.class.getName(), actionRequest));
		}
		else {
			country = _countryService.updateCountry(
				countryId, a2, a3, active, billingAllowed, null,
				nameMap.get(LocaleUtil.getDefault()), number, position,
				shippingAllowed, subjectToVAT);
		}

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			_countryLocalService.updateCountryLocalization(
				country, LanguageUtil.getLanguageId(entry.getKey()),
				entry.getValue());
		}

		return country;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceCountryMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private CountryService _countryService;

	@Reference
	private Portal _portal;

	private class CommerceCountryChannelsCallable implements Callable<Object> {

		@Override
		public Object call() throws Exception {
			_updateChannels(_actionRequest);

			return null;
		}

		private CommerceCountryChannelsCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}