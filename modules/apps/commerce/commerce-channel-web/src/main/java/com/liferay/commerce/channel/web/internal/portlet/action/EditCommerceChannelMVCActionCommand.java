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

package com.liferay.commerce.channel.web.internal.portlet.action;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceChannelPermission;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_CHANNELS,
		"mvc.command.name=/commerce_channels/edit_commerce_channel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceChannelMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCommerceChannel(ActionRequest actionRequest)
		throws Exception {

		long[] commerceChannelIds = null;

		long commerceChannelId = ParamUtil.getLong(
			actionRequest, "commerceChannelId");

		if (commerceChannelId > 0) {
			commerceChannelIds = new long[] {commerceChannelId};
		}
		else {
			commerceChannelIds = ParamUtil.getLongValues(
				actionRequest, "commerceChannelIds");
		}

		for (long deleteCommerceChannelId : commerceChannelIds) {
			_commerceChannelService.deleteCommerceChannel(
				deleteCommerceChannelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceChannel(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceChannel(actionRequest);
			}
			else if (cmd.equals("selectSite")) {
				selectSite(actionRequest);
			}
		}
		catch (PrincipalException principalException) {
			SessionErrors.add(actionRequest, principalException.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	protected ObjectValuePair<Long, String> getWorkflowDefinitionOVP(
		ActionRequest actionRequest, long typePK, String typePrefix) {

		String workflowDefinition = ParamUtil.getString(
			actionRequest, typePrefix + "WorkflowDefinition");

		return new ObjectValuePair<>(typePK, workflowDefinition);
	}

	protected CommerceChannel selectSite(ActionRequest actionRequest)
		throws Exception {

		long commerceChannelId = ParamUtil.getLong(
			actionRequest, "commerceChannelId");

		long siteGroupId = ParamUtil.getLong(actionRequest, "siteGroupId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		return _commerceChannelService.updateCommerceChannel(
			commerceChannel.getCommerceChannelId(), siteGroupId,
			commerceChannel.getName(), commerceChannel.getType(),
			commerceChannel.getTypeSettingsProperties(),
			commerceChannel.getCommerceCurrencyCode());
	}

	protected CommerceChannel updateCommerceChannel(ActionRequest actionRequest)
		throws Exception {

		long commerceChannelId = ParamUtil.getLong(
			actionRequest, "commerceChannelId");

		String name = ParamUtil.getString(actionRequest, "name");

		String commerceCurrencyCode = ParamUtil.getString(
			actionRequest, "commerceCurrencyCode");

		String priceDisplayType = ParamUtil.getString(
			actionRequest, "priceDisplayType");

		boolean discountsTargetNetPrice = ParamUtil.getBoolean(
			actionRequest, "discountsTargetNetPrice");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		_updateAccountCartMaxAllowed(commerceChannel, actionRequest);
		_updatePurchaseOrderNumber(commerceChannel, actionRequest);
		_updateShippingTaxCategory(commerceChannel, actionRequest);
		_updateSiteType(commerceChannel, actionRequest);
		updateWorkflowDefinitionLinks(commerceChannel, actionRequest);

		return _commerceChannelService.updateCommerceChannel(
			commerceChannelId, commerceChannel.getSiteGroupId(), name,
			commerceChannel.getType(),
			commerceChannel.getTypeSettingsProperties(), commerceCurrencyCode,
			priceDisplayType, discountsTargetNetPrice);
	}

	protected void updateWorkflowDefinitionLinks(
			CommerceChannel commerceChannel, ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_commerceChannelPermission.check(
			themeDisplay.getPermissionChecker(), commerceChannel,
			ActionKeys.UPDATE);

		List<ObjectValuePair<Long, String>> workflowDefinitionOVPs =
			new ArrayList<>(2);

		workflowDefinitionOVPs.add(
			getWorkflowDefinitionOVP(
				actionRequest, CommerceOrderConstants.TYPE_PK_APPROVAL,
				"buyer-order-approval"));
		workflowDefinitionOVPs.add(
			getWorkflowDefinitionOVP(
				actionRequest, CommerceOrderConstants.TYPE_PK_FULFILLMENT,
				"seller-order-acceptance"));

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLinks(
			_portal.getUserId(actionRequest), commerceChannel.getCompanyId(),
			commerceChannel.getGroupId(), CommerceOrder.class.getName(), 0,
			workflowDefinitionOVPs);
	}

	private void _updateAccountCartMaxAllowed(
			CommerceChannel commerceChannel, ActionRequest actionRequest)
		throws Exception {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				commerceChannel.getGroupId(),
				CommerceConstants.SERVICE_NAME_ORDER_FIELDS));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		Map<String, String> parameterMap = PropertiesParamUtil.getProperties(
			actionRequest, "orderSettings--");

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	private void _updatePurchaseOrderNumber(
			CommerceChannel commerceChannel, ActionRequest actionRequest)
		throws Exception {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				commerceChannel.getGroupId(),
				CommerceConstants.SERVICE_NAME_ORDER));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		Map<String, String> parameterMap = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	private void _updateShippingTaxCategory(
			CommerceChannel commerceChannel, ActionRequest actionRequest)
		throws Exception {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				commerceChannel.getGroupId(),
				CommerceConstants.SERVICE_NAME_TAX));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		Map<String, String> parameterMap = PropertiesParamUtil.getProperties(
			actionRequest, "shippingTaxSettings--");

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	private void _updateSiteType(
			CommerceChannel commerceChannel, ActionRequest actionRequest)
		throws Exception {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				commerceChannel.getGroupId(),
				CommerceAccountConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		Map<String, String> parameterMap = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	@Reference
	private CommerceChannelPermission _commerceChannelPermission;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private Portal _portal;

	@Reference
	private SettingsFactory _settingsFactory;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}