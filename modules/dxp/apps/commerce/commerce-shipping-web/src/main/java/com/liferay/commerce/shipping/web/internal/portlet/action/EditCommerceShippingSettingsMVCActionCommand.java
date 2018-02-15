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

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.exception.CommerceWarehouseActiveException;
import com.liferay.commerce.exception.CommerceWarehouseNameException;
import com.liferay.commerce.model.CommerceShippingOriginLocator;
import com.liferay.commerce.model.CommerceWarehouseConstants;
import com.liferay.commerce.service.CommerceWarehouseService;
import com.liferay.commerce.util.CommerceShippingOriginLocatorRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceShippingSettings"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingSettingsMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			updateDefaultCommerceWarehouse(actionRequest, serviceContext);

			Settings settings = _settingsFactory.getSettings(
				new GroupServiceSettingsLocator(
					serviceContext.getScopeGroupId(),
					CommerceConstants.SHIPPING_SERVICE_NAME));

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			updateOrigin(actionRequest, modifiableSettings, serviceContext);

			modifiableSettings.store();
		}
		catch (Exception e) {
			if (e instanceof CommerceWarehouseActiveException ||
				e instanceof CommerceWarehouseNameException ||
				e instanceof PrincipalException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw e;
			}
		}
	}

	protected void updateDefaultCommerceWarehouse(
			ActionRequest actionRequest, ServiceContext serviceContext)
		throws PortalException {

		String commerceShippingOriginLocatorKey = ParamUtil.getString(
			actionRequest, "commerceShippingOriginLocatorKey");

		if (!commerceShippingOriginLocatorKey.equals("address")) {
			return;
		}

		String name = ParamUtil.getString(
			actionRequest, commerceShippingOriginLocatorKey + "Origin--name--");
		String street1 = ParamUtil.getString(
			actionRequest,
			commerceShippingOriginLocatorKey + "Origin--street1--");
		String street2 = ParamUtil.getString(
			actionRequest,
			commerceShippingOriginLocatorKey + "Origin--street2--");
		String street3 = ParamUtil.getString(
			actionRequest,
			commerceShippingOriginLocatorKey + "Origin--street3--");
		String city = ParamUtil.getString(
			actionRequest, commerceShippingOriginLocatorKey + "Origin--city--");
		String zip = ParamUtil.getString(
			actionRequest, commerceShippingOriginLocatorKey + "Origin--zip--");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest,
			commerceShippingOriginLocatorKey + "Origin--commerceRegionId--");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest,
			commerceShippingOriginLocatorKey + "Origin--commerceCountryId--");
		double latitude = ParamUtil.getDouble(
			actionRequest,
			commerceShippingOriginLocatorKey + "Origin--latitude--");
		double longitude = ParamUtil.getDouble(
			actionRequest,
			commerceShippingOriginLocatorKey + "Origin--longitude--");

		_commerceWarehouseService.updateDefaultCommerceWarehouse(
			CommerceWarehouseConstants.DEFAULT_ID, name, street1, street2,
			street3, city, zip, commerceRegionId, commerceCountryId, latitude,
			longitude, serviceContext);
	}

	protected void updateOrigin(
			ActionRequest actionRequest, ModifiableSettings modifiableSettings,
			ServiceContext serviceContext)
		throws Exception {

		String commerceShippingOriginLocatorKey = ParamUtil.getString(
			actionRequest, "commerceShippingOriginLocatorKey");

		Map<String, String> parameterMap = PropertiesParamUtil.getProperties(
			actionRequest, commerceShippingOriginLocatorKey + "Origin--");

		CommerceShippingOriginLocator commerceShippingOriginLocator =
			_commerceShippingOriginLocatorRegistry.
				getCommerceShippingOriginLocator(
					commerceShippingOriginLocatorKey);

		commerceShippingOriginLocator.updateConfiguration(
			parameterMap, serviceContext);

		modifiableSettings.setValue(
			"commerceShippingOriginLocatorKey",
			commerceShippingOriginLocatorKey);
	}

	@Reference
	private CommerceShippingOriginLocatorRegistry
		_commerceShippingOriginLocatorRegistry;

	@Reference
	private CommerceWarehouseService _commerceWarehouseService;

	@Reference
	private SettingsFactory _settingsFactory;

}