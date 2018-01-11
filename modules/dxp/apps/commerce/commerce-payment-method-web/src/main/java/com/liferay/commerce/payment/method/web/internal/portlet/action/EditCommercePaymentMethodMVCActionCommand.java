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

package com.liferay.commerce.payment.method.web.internal.portlet.action;

import com.liferay.commerce.admin.web.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.CommercePaymentMethodNameException;
import com.liferay.commerce.exception.NoSuchPaymentMethodException;
import com.liferay.commerce.model.CommercePaymentEngine;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.service.CommercePaymentMethodService;
import com.liferay.commerce.util.CommercePaymentEngineRegistry;
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
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
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
		"mvc.command.name=editCommercePaymentMethod"
	},
	service = MVCActionCommand.class
)
public class EditCommercePaymentMethodMVCActionCommand
	extends BaseMVCActionCommand {

	protected CommercePaymentMethod createCommercePaymentMethod(
			ActionRequest actionRequest)
		throws PortalException {

		return createCommercePaymentMethod(actionRequest, false);
	}

	protected CommercePaymentMethod createCommercePaymentMethod(
			ActionRequest actionRequest, boolean active)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale siteDefaultLocale = themeDisplay.getSiteDefaultLocale();

		String engineKey = ParamUtil.getString(actionRequest, "engineKey");

		CommercePaymentEngine commercePaymentEngine =
			_commercePaymentEngineRegistry.getCommercePaymentEngine(engineKey);

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		nameMap.put(
			siteDefaultLocale,
			commercePaymentEngine.getName(siteDefaultLocale));
		descriptionMap.put(
			siteDefaultLocale,
			commercePaymentEngine.getDescription(siteDefaultLocale));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePaymentMethod.class.getName(), actionRequest);

		return _commercePaymentMethodService.addCommercePaymentMethod(
			nameMap, descriptionMap, null, engineKey,
			new HashMap<String, String>(), 0, active, serviceContext);
	}

	protected void deleteCommercePaymentMethod(ActionRequest actionRequest)
		throws PortalException {

		long commercePaymentMethodId = ParamUtil.getLong(
			actionRequest, "commercePaymentMethodId");

		_commercePaymentMethodService.deleteCommercePaymentMethod(
			commercePaymentMethodId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommercePaymentMethod(actionRequest);
			}
			else if (cmd.equals(Constants.EDIT)) {
				editCommercePaymentMethod(actionRequest, actionResponse);

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommercePaymentMethod(actionRequest);
			}
			else if (cmd.equals("setActive")) {
				setActive(actionRequest);
			}
			else if (cmd.equals("viewRestrictions")) {
				viewRestrictions(actionRequest, actionResponse);

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchPaymentMethodException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommercePaymentMethodNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommercePaymentMethod");
			}
			else {
				throw e;
			}
		}
	}

	protected void editCommercePaymentMethod(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String redirect = null;

		long commercePaymentMethodId = ParamUtil.getLong(
			actionRequest, "commercePaymentMethodId");

		if (commercePaymentMethodId > 0) {
			redirect = getRedirectURL(
				actionRequest, commercePaymentMethodId,
				"editCommercePaymentMethod");
		}
		else {
			CommercePaymentMethod commercePaymentMethod =
				createCommercePaymentMethod(actionRequest);

			redirect = getRedirectURL(
				actionRequest,
				commercePaymentMethod.getCommercePaymentMethodId(),
				"editCommercePaymentMethod");
		}

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected String getRedirectURL(
		ActionRequest actionRequest, long commercePaymentMethodId,
		String mvcRenderCommandName) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommerceAdminPortletKeys.COMMERCE_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
		portletURL.setParameter(
			"commercePaymentMethodId", String.valueOf(commercePaymentMethodId));

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
		long commercePaymentMethodId = ParamUtil.getLong(
			actionRequest, "commercePaymentMethodId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		if (commercePaymentMethodId > 0) {
			_commercePaymentMethodService.setActive(
				commercePaymentMethodId, active);
		}
		else {
			createCommercePaymentMethod(actionRequest, active);
		}
	}

	protected CommercePaymentMethod updateCommercePaymentMethod(
			ActionRequest actionRequest)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		long commercePaymentMethodId = ParamUtil.getLong(
			actionRequest, "commercePaymentMethodId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		File imageFile = uploadPortletRequest.getFile("imageFile");
		String engineKey = ParamUtil.getString(actionRequest, "engineKey");
		UnicodeProperties engineParameterMap =
			PropertiesParamUtil.getProperties(actionRequest, "settings--");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePaymentMethod.class.getName(), actionRequest);

		CommercePaymentMethod commercePaymentMethod = null;

		if (commercePaymentMethodId <= 0) {
			commercePaymentMethod =
				_commercePaymentMethodService.addCommercePaymentMethod(
					nameMap, descriptionMap, imageFile, engineKey,
					engineParameterMap, priority, active, serviceContext);
		}
		else {
			commercePaymentMethod =
				_commercePaymentMethodService.updateCommercePaymentMethod(
					commercePaymentMethodId, nameMap, descriptionMap, imageFile,
					engineParameterMap, priority, active, serviceContext);
		}

		return commercePaymentMethod;
	}

	protected void viewRestrictions(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String redirect = null;

		long commercePaymentMethodId = ParamUtil.getLong(
			actionRequest, "commercePaymentMethodId");

		if (commercePaymentMethodId > 0) {
			redirect = getRedirectURL(
				actionRequest, commercePaymentMethodId,
				"viewCommercePaymentMethodAddressRestriction");
		}
		else {
			CommercePaymentMethod commercePaymentMethod =
				createCommercePaymentMethod(actionRequest);

			redirect = getRedirectURL(
				actionRequest,
				commercePaymentMethod.getCommercePaymentMethodId(),
				"viewCommercePaymentMethodAddressRestriction");
		}

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	@Reference
	private CommercePaymentEngineRegistry _commercePaymentEngineRegistry;

	@Reference
	private CommercePaymentMethodService _commercePaymentMethodService;

	@Reference
	private Portal _portal;

}