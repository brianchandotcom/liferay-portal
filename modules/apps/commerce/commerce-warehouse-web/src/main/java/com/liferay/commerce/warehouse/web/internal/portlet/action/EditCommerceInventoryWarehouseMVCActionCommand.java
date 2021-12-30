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

import com.liferay.commerce.exception.CommerceGeocoderException;
import com.liferay.commerce.exception.NoSuchWarehouseException;
import com.liferay.commerce.inventory.exception.CommerceInventoryWarehouseActiveException;
import com.liferay.commerce.inventory.exception.CommerceInventoryWarehouseNameException;
import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.model.CommerceGeocoder;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_INVENTORY_WAREHOUSE,
		"mvc.command.name=/commerce_inventory_warehouse/edit_commerce_inventory_warehouse"
	},
	service = MVCActionCommand.class
)
public class EditCommerceInventoryWarehouseMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				_deleteCommerceInventoryWarehouses(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				Callable<Object> commerceInventoryWarehouseCallable =
					new CommerceInventoryWarehouseCallable(actionRequest);

				TransactionInvokerUtil.invoke(
					_transactionConfig, commerceInventoryWarehouseCallable);
			}
			else if (cmd.equals("geolocate")) {
				_geolocateCommerceInventoryWarehouse(actionRequest);
			}
			else if (cmd.equals("setActive")) {
				_setActive(actionRequest);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof CommerceGeocoderException) {
				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(
					actionRequest, throwable.getClass(),
					throwable.getMessage());
			}
			else if (throwable instanceof NoSuchWarehouseException ||
					 throwable instanceof PrincipalException) {

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (throwable instanceof
						CommerceInventoryWarehouseActiveException ||
					 throwable instanceof
						 CommerceInventoryWarehouseNameException ||
					 throwable instanceof MVCCException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/commerce_inventory_warehouse" +
						"/edit_commerce_inventory_warehouse");
			}
		}
	}

	private void _deleteCommerceInventoryWarehouses(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceInventoryWarehouseIds;

		long commerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseId");

		if (commerceInventoryWarehouseId > 0) {
			deleteCommerceInventoryWarehouseIds = new long[] {
				commerceInventoryWarehouseId
			};
		}
		else {
			deleteCommerceInventoryWarehouseIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceInventoryWarehouseIds"),
				0L);
		}

		for (long deleteCommerceInventoryWarehouseId :
				deleteCommerceInventoryWarehouseIds) {

			_commerceInventoryWarehouseService.deleteCommerceInventoryWarehouse(
				deleteCommerceInventoryWarehouseId);
		}
	}

	private void _geolocateCommerceInventoryWarehouse(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseId");

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				commerceInventoryWarehouseId);

		Country country = _getCountry(
			_portal.getCompanyId(actionRequest),
			commerceInventoryWarehouse.getCountryTwoLettersISOCode());

		Region region = _getRegion(
			country.getCountryId(),
			commerceInventoryWarehouse.getCommerceRegionCode());

		double[] coordinates = _commerceGeocoder.getCoordinates(
			commerceInventoryWarehouse.getStreet1(),
			commerceInventoryWarehouse.getCity(),
			commerceInventoryWarehouse.getZip(), region, country);

		_commerceInventoryWarehouseService.geolocateCommerceInventoryWarehouse(
			commerceInventoryWarehouseId, coordinates[0], coordinates[1]);
	}

	private Country _getCountry(long companyId, String countryCode)
		throws PortalException {

		return _countryLocalService.getCountryByA2(companyId, countryCode);
	}

	private Region _getRegion(long countryId, String regionCode)
		throws PortalException {

		return _regionLocalService.getRegion(countryId, regionCode);
	}

	private void _setActive(ActionRequest actionRequest) throws Exception {
		long commerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		_commerceInventoryWarehouseService.setActive(
			commerceInventoryWarehouseId, active);
	}

	private void _updateChannels(ActionRequest actionRequest) throws Exception {
		long commerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseId");

		if (commerceInventoryWarehouseId == 0) {
			commerceInventoryWarehouseId = GetterUtil.getLong(
				actionRequest.getAttribute("commerceInventoryWarehouseId"));
		}

		long[] commerceChannelIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "commerceChannelIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceInventoryWarehouse.class.getName(), actionRequest);

		_commerceChannelRelService.deleteCommerceChannelRels(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouseId);

		for (long commerceChannelId : commerceChannelIds) {
			if (commerceChannelId != 0) {
				_commerceChannelRelService.addCommerceChannelRel(
					CommerceInventoryWarehouse.class.getName(),
					commerceInventoryWarehouseId, commerceChannelId,
					serviceContext);
			}
		}
	}

	private CommerceInventoryWarehouse _updateCommerceInventoryWarehouse(
			ActionRequest actionRequest)
		throws Exception {

		long commerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		String commerceRegionCode = ParamUtil.getString(
			actionRequest, "commerceRegionCode");
		String commerceCountryCode = ParamUtil.getString(
			actionRequest, "countryTwoLettersISOCode");
		double latitude = ParamUtil.getDouble(actionRequest, "latitude");
		double longitude = ParamUtil.getDouble(actionRequest, "longitude");
		long mvccVersion = ParamUtil.getLong(actionRequest, "mvccVersion");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceInventoryWarehouse.class.getName(), actionRequest);

		CommerceInventoryWarehouse commerceInventoryWarehouse = null;

		if (commerceInventoryWarehouseId <= 0) {
			commerceInventoryWarehouse =
				_commerceInventoryWarehouseService.
					addCommerceInventoryWarehouse(
						null, name, description, active, street1, street2,
						street3, city, zip, commerceRegionCode,
						commerceCountryCode, latitude, longitude,
						serviceContext);

			actionRequest.setAttribute(
				"commerceInventoryWarehouseId",
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId());
		}
		else {
			commerceInventoryWarehouse =
				_commerceInventoryWarehouseService.
					updateCommerceInventoryWarehouse(
						commerceInventoryWarehouseId, name, description, active,
						street1, street2, street3, city, zip,
						commerceRegionCode, commerceCountryCode, latitude,
						longitude, mvccVersion, serviceContext);
		}

		return commerceInventoryWarehouse;
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceGeocoder _commerceGeocoder;

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RegionLocalService _regionLocalService;

	private class CommerceInventoryWarehouseCallable
		implements Callable<Object> {

		@Override
		public Object call() throws Exception {
			_updateCommerceInventoryWarehouse(_actionRequest);
			_updateChannels(_actionRequest);

			return null;
		}

		private CommerceInventoryWarehouseCallable(
			ActionRequest actionRequest) {

			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}