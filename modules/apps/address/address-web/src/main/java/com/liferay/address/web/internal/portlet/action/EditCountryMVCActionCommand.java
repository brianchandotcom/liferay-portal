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

package com.liferay.address.web.internal.portlet.action;

import com.liferay.address.web.internal.constants.AddressPortletKeys;
import com.liferay.portal.kernel.exception.CountryA2Exception;
import com.liferay.portal.kernel.exception.CountryA3Exception;
import com.liferay.portal.kernel.exception.CountryNameException;
import com.liferay.portal.kernel.exception.DuplicateCountryException;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AddressPortletKeys.COUNTRIES_MANAGEMENT_ADMIN,
		"mvc.command.name=/address/edit_country"
	},
	service = MVCActionCommand.class
)
public class EditCountryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (cmd.equals(Constants.ADD)) {
				Country country = _addCountry(actionRequest);

				redirect = HttpComponentsUtil.setParameter(
					redirect, actionResponse.getNamespace() + "countryId",
					country.getCountryId());
			}
			else if (cmd.equals(Constants.UPDATE)) {
				_updateCountry(actionRequest);
			}

			if (Validator.isNotNull(redirect)) {
				sendRedirect(actionRequest, actionResponse, redirect);
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
					"mvcRenderCommandName", "/address/edit_country");
			}
			else {
				throw new Exception(throwable);
			}
		}
	}

	private Country _addCountry(ActionRequest actionRequest) throws Exception {
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

		Country country = _countryService.addCountry(
			a2, a3, active, billingAllowed, null,
			nameMap.get(LocaleUtil.getDefault()), number, position,
			shippingAllowed, subjectToVAT, false,
			ServiceContextFactory.getInstance(
				Country.class.getName(), actionRequest));

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			_countryLocalService.updateCountryLocalization(
				country, _language.getLanguageId(entry.getKey()),
				entry.getValue());
		}

		return country;
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

		Country country = _countryService.getCountry(countryId);

		country = _countryService.updateCountry(
			countryId, a2, a3, active, billingAllowed, country.getIdd(),
			country.getName(), number, position, shippingAllowed, subjectToVAT);

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			_countryLocalService.updateCountryLocalization(
				country, _language.getLanguageId(entry.getKey()),
				entry.getValue());
		}

		return country;
	}

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private CountryService _countryService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}