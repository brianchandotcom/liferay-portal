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

package com.liferay.commerce.warehouse.web.internal.portlet.action;

import com.liferay.commerce.admin.web.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.CommerceGeocoderException;
import com.liferay.commerce.exception.CommerceWarehouseCommerceRegionIdException;
import com.liferay.commerce.exception.CommerceWarehouseNameException;
import com.liferay.commerce.exception.NoSuchWarehouseException;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.CommerceWarehouseService;
import com.liferay.commerce.warehouse.web.internal.util.WarehousesCommerceAdminModule;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
		"mvc.command.name=editCommerceWarehouse"
	},
	service = MVCActionCommand.class
)
public class EditCommerceWarehouseMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceWarehouses(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceWarehouseIds = null;

		long commerceWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseId");

		if (commerceWarehouseId > 0) {
			deleteCommerceWarehouseIds = new long[] {commerceWarehouseId};
		}
		else {
			deleteCommerceWarehouseIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceWarehouseIds"),
				0L);
		}

		for (long deleteCommerceWarehouseId : deleteCommerceWarehouseIds) {
			_commerceWarehouseService.deleteCommerceWarehouse(
				deleteCommerceWarehouseId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("geolocate")) {
				geolocateCommerceWarehouse(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceWarehouses(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceWarehouse(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof CommerceGeocoderException) {
				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass(), e.getMessage());

				actionResponse.setRenderParameter(
					"commerceAdminModuleKey",
					WarehousesCommerceAdminModule.KEY);
			}
			else if (e instanceof NoSuchWarehouseException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceWarehouseCommerceRegionIdException ||
					 e instanceof CommerceWarehouseNameException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceWarehouse");
			}
			else {
				throw e;
			}
		}
	}

	protected void geolocateCommerceWarehouse(ActionRequest actionRequest)
		throws PortalException {

		long commerceWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseId");

		_commerceWarehouseService.geolocateCommerceWarehouse(
			commerceWarehouseId);
	}

	protected CommerceWarehouse updateCommerceWarehouse(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		double latitude = ParamUtil.getDouble(actionRequest, "latitude");
		double longitude = ParamUtil.getDouble(actionRequest, "longitude");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceWarehouse.class.getName(), actionRequest);

		CommerceWarehouse commerceWarehouse = null;

		if (commerceWarehouseId <= 0) {
			commerceWarehouse = _commerceWarehouseService.addCommerceWarehouse(
				name, description, street1, street2, street3, city, zip,
				commerceRegionId, commerceCountryId, latitude, longitude,
				serviceContext);
		}
		else {
			commerceWarehouse =
				_commerceWarehouseService.updateCommerceWarehouse(
					commerceWarehouseId, name, description, street1, street2,
					street3, city, zip, commerceRegionId, commerceCountryId,
					latitude, longitude, serviceContext);
		}

		return commerceWarehouse;
	}

	@Reference
	private CommerceWarehouseService _commerceWarehouseService;

}