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

package com.liferay.commerce.shipping.web.internal.portlet.action;

import com.liferay.commerce.exception.NoSuchAddressRestrictionException;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_CHANNELS,
		"mvc.command.name=editCommerceShippingMethodAddressRestriction"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingMethodAddressRestrictionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			Callable<Object> commerceAddressRestrictionsCallable =
				new CommerceAddressRestrictionsCallable(actionRequest);

			TransactionInvokerUtil.invoke(
				_transactionConfig, commerceAddressRestrictionsCallable);
		}
		catch (Throwable t) {
			if (t instanceof NoSuchAddressRestrictionException ||
				t instanceof PrincipalException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, t.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				_log.error(t, t);
			}
		}
	}

	protected void updateCommerceAddressRestrictions(
			ActionRequest actionRequest)
		throws Exception {

		long commerceChannelId = ParamUtil.getLong(
			actionRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		List<CommerceShippingMethod> commerceShippingMethods =
			_commerceShippingMethodService.getCommerceShippingMethods(
				commerceChannel.getGroupId(), true);

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			_commerceShippingMethodService.deleteCommerceAddressRestrictions(
				commerceShippingMethod.getCommerceShippingMethodId());
		}

		List<CommerceCountry> commerceCountries =
			_commerceCountryService.getCommerceCountries(
				_portal.getCompanyId(actionRequest), true);

		for (CommerceCountry commerceCountry : commerceCountries) {
			long[] commercePaymentMethodGroupRelIds = ParamUtil.getLongValues(
				actionRequest,
				String.valueOf(commerceCountry.getCommerceCountryId()));

			if (ArrayUtil.isEmpty(commercePaymentMethodGroupRelIds)) {
				continue;
			}

			for (long commercePaymentMethodGroupRelId :
					commercePaymentMethodGroupRelIds) {

				if (commercePaymentMethodGroupRelId <= 0) {
					continue;
				}

				_commerceShippingMethodService.addCommerceAddressRestriction(
					_portal.getUserId(actionRequest),
					commerceChannel.getGroupId(),
					commercePaymentMethodGroupRelId,
					commerceCountry.getCommerceCountryId());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceShippingMethodAddressRestrictionMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private Portal _portal;

	private class CommerceAddressRestrictionsCallable
		implements Callable<Object> {

		@Override
		public Object call() throws Exception {
			updateCommerceAddressRestrictions(_actionRequest);

			return null;
		}

		private CommerceAddressRestrictionsCallable(
			ActionRequest actionRequest) {

			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}