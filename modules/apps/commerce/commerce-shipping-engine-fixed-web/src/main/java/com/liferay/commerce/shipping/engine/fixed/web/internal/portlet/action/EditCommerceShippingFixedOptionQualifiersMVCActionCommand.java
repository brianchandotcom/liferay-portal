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
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.shipping.engine.fixed.exception.DuplicateCommerceShippingFixedOptionQualifierException;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

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
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_SHIPPING_METHODS,
		"mvc.command.name=/commerce_shipping_methods/edit_commerce_shipping_fixed_option_qualifiers"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingFixedOptionQualifiersMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCommerceShippingFixedOptionQualifiers(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof
					DuplicateCommerceShippingFixedOptionQualifierException) {

				SessionErrors.add(actionRequest, exception.getClass());

				SessionMessages.add(
					actionRequest,
					_portal.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
				SessionMessages.add(
					actionRequest,
					_portal.getPortletId(actionRequest) +
						SessionMessages.
							KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
			}
			else {
				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
		}
	}

	private void _updateCommerceShippingFixedOptionQualifiers(
			ActionRequest actionRequest)
		throws Exception {

		long commerceShippingFixedOptionId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionId");

		String orderTypeQualifiers = ParamUtil.getString(
			actionRequest, "orderTypeQualifiers");

		if (Objects.equals(orderTypeQualifiers, "all")) {
			_commerceShippingFixedOptionQualifierService.
				deleteCommerceShippingFixedOptionQualifiers(
					CommerceOrderType.class.getName(),
					commerceShippingFixedOptionId);
		}

		String termEntryQualifiers = ParamUtil.getString(
			actionRequest, "termEntryQualifiers");

		if (Objects.equals(termEntryQualifiers, "none")) {
			_commerceShippingFixedOptionQualifierService.
				deleteCommerceShippingFixedOptionQualifiers(
					CommerceTermEntry.class.getName(),
					commerceShippingFixedOptionId);
		}
	}

	@Reference
	private CommerceShippingFixedOptionQualifierService
		_commerceShippingFixedOptionQualifierService;

	@Reference
	private Portal _portal;

}