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

import com.liferay.commerce.admin.web.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.CommerceShippingMethodNameException;
import com.liferay.commerce.exception.NoSuchShippingMethodException;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceShippingMethod"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingMethodMVCActionCommand
	extends BaseMVCActionCommand {

	protected CommerceShippingMethod createCommerceShippingMethod(
			ActionRequest actionRequest)
		throws PortalException {

		return createCommerceShippingMethod(actionRequest, false);
	}

	protected CommerceShippingMethod createCommerceShippingMethod(
			ActionRequest actionRequest, boolean active)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale siteDefaultLocale = themeDisplay.getSiteDefaultLocale();

		String engineKey = ParamUtil.getString(actionRequest, "engineKey");

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				engineKey);

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		nameMap.put(
			siteDefaultLocale,
			commerceShippingEngine.getName(siteDefaultLocale));
		descriptionMap.put(
			siteDefaultLocale,
			commerceShippingEngine.getDescription(siteDefaultLocale));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShippingMethod.class.getName(), actionRequest);

		return _commerceShippingMethodService.addCommerceShippingMethod(
			nameMap, descriptionMap, null, engineKey, 0, active,
			serviceContext);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.EDIT)) {
				editCommerceShippingMethod(actionRequest, actionResponse);

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceShippingMethod(actionRequest);
			}
			else if (cmd.equals("setActive")) {
				setActive(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchShippingMethodException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceShippingMethodNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceShippingMethod");

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void editCommerceShippingMethod(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String redirect = null;

		long commerceShippingMethodId = ParamUtil.getLong(
			actionRequest, "commerceShippingMethodId");

		if (commerceShippingMethodId > 0) {
			redirect = getEditCommerceShippingMethodURL(
				actionRequest, commerceShippingMethodId);
		}
		else {
			CommerceShippingMethod commerceShippingMethod =
				createCommerceShippingMethod(actionRequest);

			redirect = getEditCommerceShippingMethodURL(
				actionRequest,
				commerceShippingMethod.getCommerceShippingMethodId());
		}

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected String getEditCommerceShippingMethodURL(
		ActionRequest actionRequest, long commerceShippingMethodId) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommerceAdminPortletKeys.COMMERCE_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceShippingMethod");
		portletURL.setParameter(
			"commerceShippingMethodId",
			String.valueOf(commerceShippingMethodId));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String engineKey = ParamUtil.getString(actionRequest, "engineKey");

		if (Validator.isNotNull(engineKey)) {
			portletURL.setParameter("engineKey", engineKey);
		}

		return portletURL.toString();
	}

	protected void setActive(ActionRequest actionRequest) throws Exception {
		long commerceShippingMethodId = ParamUtil.getLong(
			actionRequest, "commerceShippingMethodId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		if (commerceShippingMethodId > 0) {
			_commerceShippingMethodService.setActive(
				commerceShippingMethodId, active);
		}
		else {
			createCommerceShippingMethod(actionRequest, active);
		}
	}

	protected CommerceShippingMethod updateCommerceShippingMethod(
			ActionRequest actionRequest)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		long commerceShippingMethodId = ParamUtil.getLong(
			actionRequest, "commerceShippingMethodId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		File imageFile = uploadPortletRequest.getFile("imageFile");
		String engineKey = ParamUtil.getString(actionRequest, "engineKey");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShippingMethod.class.getName(), actionRequest);

		CommerceShippingMethod commerceShippingMethod = null;

		if (commerceShippingMethodId <= 0) {
			commerceShippingMethod =
				_commerceShippingMethodService.addCommerceShippingMethod(
					nameMap, descriptionMap, imageFile, engineKey, priority,
					active, serviceContext);
		}
		else {
			commerceShippingMethod =
				_commerceShippingMethodService.updateCommerceShippingMethod(
					commerceShippingMethodId, nameMap, descriptionMap,
					imageFile, priority, active);
		}

		return commerceShippingMethod;
	}

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private Portal _portal;

}