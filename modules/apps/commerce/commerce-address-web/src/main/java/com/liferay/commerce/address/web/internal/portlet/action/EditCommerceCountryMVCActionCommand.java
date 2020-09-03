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
import com.liferay.commerce.exception.CommerceCountryAlreadyExistsException;
import com.liferay.commerce.exception.CommerceCountryNameException;
import com.liferay.commerce.exception.CommerceCountryThreeLettersISOCodeException;
import com.liferay.commerce.exception.CommerceCountryTwoLettersISOCodeException;
import com.liferay.commerce.exception.NoSuchCountryException;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
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
		"mvc.command.name=editCommerceCountry"
	},
	service = MVCActionCommand.class
)
public class EditCommerceCountryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCommerceCountries(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceCountryIds = null;

		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");

		if (commerceCountryId > 0) {
			deleteCommerceCountryIds = new long[] {commerceCountryId};
		}
		else {
			deleteCommerceCountryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceCountryIds"),
				0L);
		}

		for (long deleteCommerceCountryId : deleteCommerceCountryIds) {
			_commerceCountryService.deleteCommerceCountry(
				deleteCommerceCountryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CommerceCountry commerceCountry = updateCommerceCountry(
					actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, commerceCountry);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceCountries(actionRequest);
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
			else if (throwable instanceof
						CommerceCountryAlreadyExistsException ||
					 throwable instanceof CommerceCountryNameException ||
					 throwable instanceof
						 CommerceCountryThreeLettersISOCodeException ||
					 throwable instanceof
						 CommerceCountryTwoLettersISOCodeException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceCountry");
			}
			else {
				_log.error(throwable, throwable);

				throw new Exception(throwable);
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CommerceCountry commerceCountry)
		throws Exception {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommercePortletKeys.COMMERCE_COUNTRY,
			PortletRequest.RENDER_PHASE);

		if (commerceCountry != null) {
			portletURL.setParameter(
				"mvcRenderCommandName", "editCommerceCountry");
			portletURL.setParameter(
				"commerceCountryId",
				String.valueOf(commerceCountry.getCommerceCountryId()));

			String backURL = ParamUtil.getString(actionRequest, "backURL");

			portletURL.setParameter("backURL", backURL);
		}

		return portletURL.toString();
	}

	protected void setActive(ActionRequest actionRequest)
		throws PortalException {

		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		_commerceCountryService.setActive(commerceCountryId, active);
	}

	protected void updateChannels(ActionRequest actionRequest)
		throws PortalException {

		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");

		long[] commerceChannelIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "commerceChannelIds"), 0L);

		boolean channelFilterEnabled = ParamUtil.getBoolean(
			actionRequest, "channelFilterEnabled");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceChannelRel.class.getName(), actionRequest);

		_commerceChannelRelService.deleteCommerceChannelRels(
			CommerceCountry.class.getName(), commerceCountryId);

		for (long commerceChannelId : commerceChannelIds) {
			if (commerceChannelId == 0) {
				continue;
			}

			_commerceChannelRelService.addCommerceChannelRel(
				CommerceCountry.class.getName(), commerceCountryId,
				commerceChannelId, serviceContext);
		}

		_commerceCountryService.updateCommerceCountryChannelFilter(
			commerceCountryId, channelFilterEnabled);
	}

	protected CommerceCountry updateCommerceCountry(ActionRequest actionRequest)
		throws Exception {

		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		boolean billingAllowed = ParamUtil.getBoolean(
			actionRequest, "billingAllowed");
		boolean shippingAllowed = ParamUtil.getBoolean(
			actionRequest, "shippingAllowed");
		String twoLettersISOCode = ParamUtil.getString(
			actionRequest, "twoLettersISOCode");
		String threeLettersISOCode = ParamUtil.getString(
			actionRequest, "threeLettersISOCode");
		int numericISOCode = ParamUtil.getInteger(
			actionRequest, "numericISOCode");
		boolean subjectToVAT = ParamUtil.getBoolean(
			actionRequest, "subjectToVAT");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceCountry.class.getName(), actionRequest);

		CommerceCountry commerceCountry = null;

		if (commerceCountryId <= 0) {
			commerceCountry = _commerceCountryService.addCommerceCountry(
				nameMap, billingAllowed, shippingAllowed, twoLettersISOCode,
				threeLettersISOCode, numericISOCode, subjectToVAT, priority,
				active, serviceContext);
		}
		else {
			commerceCountry = _commerceCountryService.updateCommerceCountry(
				commerceCountryId, nameMap, billingAllowed, shippingAllowed,
				twoLettersISOCode, threeLettersISOCode, numericISOCode,
				subjectToVAT, priority, active, serviceContext);
		}

		return commerceCountry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceCountryMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private Portal _portal;

	private class CommerceCountryChannelsCallable implements Callable<Object> {

		@Override
		public Object call() throws Exception {
			updateChannels(_actionRequest);

			return null;
		}

		private CommerceCountryChannelsCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}