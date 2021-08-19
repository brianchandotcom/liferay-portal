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

package com.liferay.commerce.payment.web.internal.portlet.action;

import com.liferay.commerce.exception.NoSuchPaymentMethodException;
import com.liferay.commerce.payment.exception.CommercePaymentMethodGroupRelNameException;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PAYMENT_METHODS,
		"mvc.command.name=/commerce_payment_methods/edit_commerce_payment_method_group_rel"
	},
	service = MVCActionCommand.class
)
public class EditCommercePaymentMethodGroupRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommercePaymentMethodGroupRel(
			ActionRequest actionRequest)
		throws PortalException {

		long commercePaymentMethodGroupRelId = ParamUtil.getLong(
			actionRequest, "commercePaymentMethodGroupRelId");

		_commercePaymentMethodGroupRelService.
			deleteCommercePaymentMethodGroupRel(
				commercePaymentMethodGroupRelId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommercePaymentMethodGroupRel(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommercePaymentMethodGroupRel(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchPaymentMethodException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof
						CommercePaymentMethodGroupRelNameException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/commerce_payment_methods" +
						"/edit_commerce_payment_method_group_rel");
			}
			else {
				throw exception;
			}
		}
	}

	protected String getRedirectURL(
		ActionRequest actionRequest, long commercePaymentMethodGroupRelId,
		String mvcRenderCommandName) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				actionRequest, CPPortletKeys.COMMERCE_CHANNELS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			mvcRenderCommandName
		).setRedirect(
			() -> {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				if (Validator.isNotNull(redirect)) {
					return redirect;
				}

				return null;
			}
		).setParameter(
			"commercePaymentMethodGroupRelId", commercePaymentMethodGroupRelId
		).setParameter(
			"engineKey",
			() -> {
				String engineKey = ParamUtil.getString(
					actionRequest, "engineKey");

				if (Validator.isNotNull(engineKey)) {
					return engineKey;
				}

				return null;
			}
		).buildString();
	}

	protected CommercePaymentMethodGroupRel updateCommercePaymentMethodGroupRel(
			ActionRequest actionRequest)
		throws PortalException {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel = null;

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "nameMapAsXML");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "descriptionMapAsXML");
		File imageFile = uploadPortletRequest.getFile("imageFile");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		long commercePaymentMethodGroupRelId = ParamUtil.getLong(
			actionRequest, "commercePaymentMethodGroupRelId");

		if (commercePaymentMethodGroupRelId <= 0) {
			long commerceChannelId = ParamUtil.getLong(
				actionRequest, "commerceChannelId");

			CommerceChannel commerceChannel =
				_commerceChannelService.getCommerceChannel(commerceChannelId);

			String commercePaymentMethodEngineKey = ParamUtil.getString(
				actionRequest, "commercePaymentMethodEngineKey");

			commercePaymentMethodGroupRel =
				_commercePaymentMethodGroupRelService.
					addCommercePaymentMethodGroupRel(
						commerceChannel.getGroupId(), nameMap, descriptionMap,
						imageFile, commercePaymentMethodEngineKey, priority,
						active);
		}
		else {
			commercePaymentMethodGroupRel =
				_commercePaymentMethodGroupRelService.
					updateCommercePaymentMethodGroupRel(
						commercePaymentMethodGroupRelId, nameMap,
						descriptionMap, imageFile, priority, active);
		}

		return commercePaymentMethodGroupRel;
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

	@Reference
	private Portal _portal;

}