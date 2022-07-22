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
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.CountryA2Exception;
import com.liferay.portal.kernel.exception.CountryA3Exception;
import com.liferay.portal.kernel.exception.CountryNameException;
import com.liferay.portal.kernel.exception.CountryNumberException;
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
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

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
	service = AopService.class
)
public class EditCountryMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

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
					 throwable instanceof CountryNumberException ||
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
		String name = ParamUtil.getString(actionRequest, "name");
		String number = ParamUtil.getString(actionRequest, "number");
		double position = ParamUtil.getDouble(actionRequest, "position");
		boolean shippingAllowed = ParamUtil.getBoolean(
			actionRequest, "shippingAllowed");
		boolean subjectToVAT = ParamUtil.getBoolean(
			actionRequest, "subjectToVAT");

		Country country = _countryService.addCountry(
			a2, a3, active, billingAllowed, null, name, number, position,
			shippingAllowed, subjectToVAT, false,
			ServiceContextFactory.getInstance(
				Country.class.getName(), actionRequest));

		_updateCountryLocalizations(
			country,
			LocalizationUtil.getLocalizationMap(actionRequest, "title"));

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
		String name = ParamUtil.getString(actionRequest, "name");
		String number = ParamUtil.getString(actionRequest, "number");
		double position = ParamUtil.getDouble(actionRequest, "position");
		boolean shippingAllowed = ParamUtil.getBoolean(
			actionRequest, "shippingAllowed");
		boolean subjectToVAT = ParamUtil.getBoolean(
			actionRequest, "subjectToVAT");

		Country country = _countryService.getCountry(countryId);

		country = _countryService.updateCountry(
			countryId, a2, a3, active, billingAllowed, country.getIdd(), name,
			number, position, shippingAllowed, subjectToVAT);

		_updateCountryLocalizations(
			country,
			LocalizationUtil.getLocalizationMap(actionRequest, "title"));

		return country;
	}

	private void _updateCountryLocalizations(
			Country country, Map<Locale, String> localizationMap)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		for (Map.Entry<Locale, String> entry : localizationMap.entrySet()) {
			map.put(_language.getLanguageId(entry.getKey()), entry.getValue());
		}

		_countryLocalService.updateCountryLocalizations(country, map);
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