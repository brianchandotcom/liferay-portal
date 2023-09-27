/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.site.setting.internal.helper.v1_0;

import com.liferay.commerce.exception.NoSuchWarehouseException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.Warehouse;
import com.liferay.headless.commerce.admin.site.setting.internal.mapper.v1_0.DTOMapper;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
@Component(service = WarehouseHelper.class)
public class WarehouseHelper {

	public Warehouse addOrUpdateWarehouse(
			Long groupId, Warehouse warehouse, User user)
		throws PortalException {

		try {
			CommerceInventoryWarehouse commerceInventoryWarehouse =
				updateWarehouse(warehouse.getId(), warehouse, user);

			return _dtoMapper.modelToDTO(commerceInventoryWarehouse);
		}
		catch (NoSuchWarehouseException noSuchWarehouseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find availabilityEstimate with ID: " +
						warehouse.getId(),
					noSuchWarehouseException);
			}
		}

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.addCommerceInventoryWarehouse(
				null, LanguageUtils.getLocalizedMap(warehouse.getName()),
				LanguageUtils.getLocalizedMap(warehouse.getDescription()),
				GetterUtil.get(warehouse.getActive(), false),
				warehouse.getStreet1(), warehouse.getStreet2(),
				warehouse.getStreet3(), warehouse.getCity(), warehouse.getZip(),
				"", "", GetterUtil.get(warehouse.getLatitude(), 0D),
				GetterUtil.get(warehouse.getLongitude(), 0D),
				_serviceContextHelper.getServiceContext(
					groupId, new long[0], user, true));

		return _dtoMapper.modelToDTO(commerceInventoryWarehouse);
	}

	public void deleteWarehouse(Long id) throws PortalException {
		_commerceInventoryWarehouseService.deleteCommerceInventoryWarehouse(id);
	}

	public Warehouse getWarehouse(Long id) throws PortalException {
		return _dtoMapper.modelToDTO(
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				id));
	}

	public Page<Warehouse> getWarehouses(
			Long groupId, Boolean active, Pagination pagination)
		throws PortalException {

		return null;
		/*
				List<CommerceInventoryWarehouse> commerceInventoryWarehouses;
				int count;

				if (active == null) {
					commerceInventoryWarehouses =
						_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
							groupId, pagination.getStartPosition(),
							pagination.getEndPosition(), null);

					count = _commerceInventoryWarehouseService.getCommerceInventoryWarehousesCount(
						groupId);
				}
				else {
					commerceInventoryWarehouses =
						_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
							groupId, active, pagination.getStartPosition(),
							pagination.getEndPosition(), null);

					count = _commerceInventoryWarehouseService.getCommerceInventoryWarehousesCount(
						groupId, active);
				}

				List<Warehouse> availabilityEstimates = new ArrayList<>();

				for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryWarehouses) {
					availabilityEstimates.add(_dtoMapper.modelToDTO(commerceInventoryWarehouse));
				}

				return Page.of(availabilityEstimates, pagination, count);
		*/

	}

	public CommerceInventoryWarehouse updateWarehouse(
			Long id, Warehouse warehouse, User user)
		throws PortalException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				id);

		return _commerceInventoryWarehouseService.
			updateCommerceInventoryWarehouse(
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				LanguageUtils.getLocalizedMap(warehouse.getName()),
				LanguageUtils.getLocalizedMap(warehouse.getDescription()),
				GetterUtil.get(
					warehouse.getActive(),
					commerceInventoryWarehouse.isActive()),
				GetterUtil.get(
					warehouse.getStreet1(),
					commerceInventoryWarehouse.getStreet1()),
				GetterUtil.get(
					warehouse.getStreet2(),
					commerceInventoryWarehouse.getStreet2()),
				GetterUtil.get(
					warehouse.getStreet3(),
					commerceInventoryWarehouse.getStreet3()),
				GetterUtil.get(
					warehouse.getCity(), commerceInventoryWarehouse.getCity()),
				GetterUtil.get(
					warehouse.getZip(), commerceInventoryWarehouse.getZip()),
				null, null,
				GetterUtil.get(
					warehouse.getLatitude(),
					commerceInventoryWarehouse.getLatitude()),
				GetterUtil.get(
					warehouse.getLongitude(),
					commerceInventoryWarehouse.getLongitude()),
				GetterUtil.get(
					warehouse.getMvccVersion(),
					commerceInventoryWarehouse.getMvccVersion()),
				_serviceContextHelper.getServiceContext(
					0, new long[0], user, true));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WarehouseHelper.class);

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Reference
	private DTOMapper _dtoMapper;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}