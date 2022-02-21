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

package com.liferay.commerce.shipping.engine.fixed.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_SHIPPING_METHODS,
		"mvc.command.name=/commerce_shipping_methods/edit_commerce_shipping_fixed_option"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingFixedOptionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CommerceShippingFixedOption commerceShippingFixedOption =
					_updateCommerceShippingFixedOption(actionRequest);

				String redirect = _getSaveAndContinueRedirect(
					actionRequest, commerceShippingFixedOption);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCommerceShippingFixedOptions(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchShippingFixedOptionException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	private void _deleteCommerceShippingFixedOptions(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceShippingFixedOptionIds = null;

		long commerceShippingFixedOptionId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionId");

		if (commerceShippingFixedOptionId > 0) {
			deleteCommerceShippingFixedOptionIds = new long[] {
				commerceShippingFixedOptionId
			};
		}
		else {
			deleteCommerceShippingFixedOptionIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceShippingFixedOptionIds"),
				0L);
		}

		for (long deleteCommerceShippingFixedOptionId :
				deleteCommerceShippingFixedOptionIds) {

			_commerceShippingFixedOptionService.
				deleteCommerceShippingFixedOption(
					deleteCommerceShippingFixedOptionId);
		}
	}

	private String _getSaveAndContinueRedirect(
			ActionRequest actionRequest,
			CommerceShippingFixedOption commerceShippingFixedOption)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, portletConfig.getPortletName(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_shipping_methods/edit_commerce_shipping_fixed_option");
		portletURL.setParameter(
			"commerceShippingFixedOptionId",
			String.valueOf(
				commerceShippingFixedOption.
					getCommerceShippingFixedOptionId()));

		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	private CommerceShippingFixedOption _updateCommerceShippingFixedOption(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceShippingFixedOptionId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		BigDecimal amount = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "amount", BigDecimal.ZERO);
		double priority = ParamUtil.getDouble(actionRequest, "priority");

		CommerceShippingFixedOption commerceShippingFixedOption = null;

		if (commerceShippingFixedOptionId > 0) {
			commerceShippingFixedOption =
				_commerceShippingFixedOptionService.
					updateCommerceShippingFixedOption(
						commerceShippingFixedOptionId, amount, descriptionMap,
						nameMap, priority);
		}
		else {
			long commerceShippingMethodId = ParamUtil.getLong(
				actionRequest, "commerceShippingMethodId");

			CommerceShippingMethod commerceShippingMethod =
				_commerceShippingMethodService.getCommerceShippingMethod(
					commerceShippingMethodId);

			commerceShippingFixedOption =
				_commerceShippingFixedOptionService.
					addCommerceShippingFixedOption(
						commerceShippingMethod.getGroupId(),
						commerceShippingMethod.getCommerceShippingMethodId(),
						amount, descriptionMap, nameMap, priority);
		}

		return commerceShippingFixedOption;
	}

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private Portal _portal;

}