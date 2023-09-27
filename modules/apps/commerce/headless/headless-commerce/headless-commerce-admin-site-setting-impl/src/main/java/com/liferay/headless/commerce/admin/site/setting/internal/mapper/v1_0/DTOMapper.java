/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.site.setting.internal.mapper.v1_0;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.AvailabilityEstimate;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.TaxCategory;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.Warehouse;
import com.liferay.headless.commerce.core.util.LanguageUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
@Component(service = DTOMapper.class)
public class DTOMapper {

	public AvailabilityEstimate modelToDTO(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {

		AvailabilityEstimate availabilityEstimate = new AvailabilityEstimate();

		if (commerceAvailabilityEstimate == null) {
			return availabilityEstimate;
		}

		availabilityEstimate.setId(
			commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId());
		availabilityEstimate.setPriority(
			commerceAvailabilityEstimate.getPriority());
		availabilityEstimate.setTitle(
			LanguageUtils.getLanguageIdMap(
				commerceAvailabilityEstimate.getTitleMap()));

		return availabilityEstimate;
	}

	public Warehouse modelToDTO(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		Warehouse warehouse = new Warehouse();

		if (commerceInventoryWarehouse == null) {
			return warehouse;
		}

		warehouse.setActive(commerceInventoryWarehouse.isActive());
		warehouse.setCity(commerceInventoryWarehouse.getCity());
		warehouse.setDescription(
			LanguageUtils.getLanguageIdMap(
				commerceInventoryWarehouse.getDescriptionMap()));
		warehouse.setId(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());
		warehouse.setLatitude(commerceInventoryWarehouse.getLatitude());
		warehouse.setLongitude(commerceInventoryWarehouse.getLongitude());
		warehouse.setName(
			LanguageUtils.getLanguageIdMap(
				commerceInventoryWarehouse.getNameMap()));
		warehouse.setStreet1(commerceInventoryWarehouse.getStreet1());
		warehouse.setStreet2(commerceInventoryWarehouse.getStreet2());
		warehouse.setStreet3(commerceInventoryWarehouse.getStreet3());
		warehouse.setZip(commerceInventoryWarehouse.getZip());

		return warehouse;
	}

	public TaxCategory modelToDTO(CPTaxCategory cpTaxCategory) {
		TaxCategory taxCategory = new TaxCategory();

		if (cpTaxCategory == null) {
			return taxCategory;
		}

		taxCategory.setDescription(
			LanguageUtils.getLanguageIdMap(cpTaxCategory.getDescriptionMap()));
		taxCategory.setId(cpTaxCategory.getCPTaxCategoryId());
		taxCategory.setName(
			LanguageUtils.getLanguageIdMap(cpTaxCategory.getNameMap()));

		return taxCategory;
	}

}