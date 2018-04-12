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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPDefinitionIgnoreSKUCombinationsException;
import com.liferay.commerce.product.exception.CPInstanceDDMContentException;
import com.liferay.commerce.product.exception.NoSuchSkuContributorCPDefinitionOptionRelException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editProductInstance"
	},
	service = MVCActionCommand.class
)
public class EditCPInstanceMVCActionCommand extends BaseMVCActionCommand {

	protected void buildCPInstances(ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPInstance.class.getName(), actionRequest);

		_cpInstanceService.buildCPInstances(cpDefinitionId, serviceContext);
	}

	protected void deleteCPInstances(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPInstanceIds = null;

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		if (cpInstanceId > 0) {
			deleteCPInstanceIds = new long[] {cpInstanceId};
		}
		else {
			deleteCPInstanceIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPInstanceIds"), 0L);
		}

		for (long deleteCPInstanceId : deleteCPInstanceIds) {
			_cpInstanceService.deleteCPInstance(deleteCPInstanceId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CPInstance cpInstance = updateCPInstance(actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, cpInstance);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				buildCPInstances(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPInstances(actionRequest);
			}
			else if (cmd.equals("updatePricingInfo")) {
				updatePricingInfo(actionRequest);
			}
			else if (cmd.equals("updateShippingInfo")) {
				updateShippingInfo(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof CPDefinitionIgnoreSKUCombinationsException ||
				e instanceof CPInstanceDDMContentException ||
				e instanceof
					NoSuchSkuContributorCPDefinitionOptionRelException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw new PortletException(e);
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CPInstance cpInstance)
		throws Exception {

		if (cpInstance == null) {
			return ParamUtil.getString(actionRequest, "redirect");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CPDefinition.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter("mvcRenderCommandName", "editProductInstance");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));

		return portletURL.toString();
	}

	protected CPInstance updateCPInstance(ActionRequest actionRequest)
		throws Exception {

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		String sku = ParamUtil.getString(actionRequest, "sku");
		String gtin = ParamUtil.getString(actionRequest, "gtin");
		String manufacturerPartNumber = ParamUtil.getString(
			actionRequest, "manufacturerPartNumber");
		boolean purchasable = ParamUtil.getBoolean(
			actionRequest, "purchasable");
		boolean published = ParamUtil.getBoolean(actionRequest, "published");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPInstance.class.getName(), actionRequest);

		CPInstance cpInstance = null;

		if (cpInstanceId > 0) {
			cpInstance = _cpInstanceService.updateCPInstance(
				cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
				published, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);
		}
		else {
			String ddmFormValues = ParamUtil.getString(
				actionRequest, "ddmFormValues");

			cpInstance = _cpInstanceService.addCPInstance(
				cpDefinitionId, sku, gtin, manufacturerPartNumber, purchasable,
				ddmFormValues, published, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);
		}

		return cpInstance;
	}

	protected void updatePricingInfo(ActionRequest actionRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPInstance.class.getName(), actionRequest);

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		double cost = ParamUtil.getDouble(actionRequest, "cost");
		double price = ParamUtil.getDouble(actionRequest, "price");

		_cpInstanceService.updatePricingInfo(
			cpInstanceId, cost, price, serviceContext);
	}

	protected void updateShippingInfo(ActionRequest actionRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPInstance.class.getName(), actionRequest);

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		double width = ParamUtil.getDouble(actionRequest, "width");
		double height = ParamUtil.getDouble(actionRequest, "height");
		double depth = ParamUtil.getDouble(actionRequest, "depth");
		double weight = ParamUtil.getDouble(actionRequest, "weight");

		_cpInstanceService.updateShippingInfo(
			cpInstanceId, width, height, depth, weight, serviceContext);
	}

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceService _cpInstanceService;

}