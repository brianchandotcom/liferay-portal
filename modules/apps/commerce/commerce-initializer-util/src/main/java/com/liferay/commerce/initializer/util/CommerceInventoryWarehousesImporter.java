/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.initializer.util;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceInventoryWarehousesImporter.class)
public class CommerceInventoryWarehousesImporter {

	public List<CommerceInventoryWarehouse> importCommerceInventoryWarehouses(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws Exception {

		if ((jsonArray == null) || (jsonArray.length() <= 0)) {
			return Collections.emptyList();
		}

		ServiceContext serviceContext = getServiceContext(scopeGroupId, userId);

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			new ArrayList<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			CommerceInventoryWarehouse commerceInventoryWarehouse =
				_importCommerceInventoryWarehouse(
					jsonArray.getJSONObject(i), serviceContext);

			commerceInventoryWarehouses.add(commerceInventoryWarehouse);
		}

		return commerceInventoryWarehouses;
	}

	protected ServiceContext getServiceContext(long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		return serviceContext;
	}

	private CommerceInventoryWarehouse _importCommerceInventoryWarehouse(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		// Commerce inventory warehouse

		String externalReferenceCode = jsonObject.getString(
			"externalReferenceCode");

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseLocalService.
				fetchCommerceInventoryWarehouseByReferenceCode(
					externalReferenceCode, serviceContext.getCompanyId());

		if (Validator.isNotNull(externalReferenceCode) &&
			(commerceInventoryWarehouse == null)) {

			String countryNumericISOCode = jsonObject.getString("country");

			Country country = _countryLocalService.fetchCountryByNumber(
				serviceContext.getCompanyId(), countryNumericISOCode);

			String regionCode = jsonObject.getString("region");

			Region region = _regionLocalService.getRegion(
				country.getCountryId(), regionCode);

			Map<Locale, String> nameMap = Collections.singletonMap(
				LocaleUtil.getSiteDefault(), jsonObject.getString("name"));
			Map<Locale, String> descriptionMap = Collections.singletonMap(
				LocaleUtil.getSiteDefault(),
				jsonObject.getString("description"));
			boolean active = jsonObject.getBoolean("active", true);
			String street1 = jsonObject.getString("street1");
			String street2 = jsonObject.getString("street2");
			String street3 = jsonObject.getString("street3");
			String city = jsonObject.getString("city");
			String zip = jsonObject.getString("zip");
			double latitude = jsonObject.getDouble("latitude");
			double longitude = jsonObject.getDouble("longitude");

			commerceInventoryWarehouse =
				_commerceInventoryWarehouseLocalService.
					addCommerceInventoryWarehouse(
						externalReferenceCode, nameMap, descriptionMap, active,
						street1, street2, street3, city, zip,
						region.getRegionCode(), country.getA2(), latitude,
						longitude, serviceContext);
		}

		// Commerce channel rel

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				serviceContext.getScopeGroupId());

		if (commerceChannel != null) {
			CommerceChannelRel commerceChannelRel =
				_commerceChannelRelLocalService.fetchCommerceChannelRel(
					CommerceInventoryWarehouse.class.getName(),
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					commerceChannel.getCommerceChannelId());

			if (commerceChannelRel != null) {
				return commerceInventoryWarehouse;
			}

			_commerceChannelRelLocalService.addCommerceChannelRel(
				CommerceInventoryWarehouse.class.getName(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				commerceChannel.getCommerceChannelId(), serviceContext);
		}

		return commerceInventoryWarehouse;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Reference
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}