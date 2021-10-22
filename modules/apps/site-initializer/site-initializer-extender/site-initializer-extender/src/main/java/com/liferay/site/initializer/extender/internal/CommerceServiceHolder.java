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

package com.liferay.site.initializer.extender.internal;

import com.liferay.commerce.account.util.CommerceAccountRoleHelper;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.initializer.util.CPDefinitionsImporter;
import com.liferay.commerce.initializer.util.CommerceInventoryWarehousesImporter;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CommerceServiceHolder.class)
public class CommerceServiceHolder {

	public CatalogResource.Factory getCatalogResourceFactory() {
		return _catalogResourceFactory;
	}

	public ChannelResource.Factory getChannelResourceFactory() {
		return _channelResourceFactory;
	}

	public CommerceAccountRoleHelper getCommerceAccountRoleHelper() {
		return _commerceAccountRoleHelper;
	}

	public CommerceCurrencyLocalService getCommerceCurrencyLocalService() {
		return _commerceCurrencyLocalService;
	}

	public CommerceInventoryWarehousesImporter
		getCommerceInventoryWarehousesImporter() {

		return _commerceInventoryWarehousesImporter;
	}

	public CPDefinitionsImporter getCpDefinitionsImporter() {
		return _cpDefinitionsImporter;
	}

	public CPFileImporter getCpFileImporter() {
		return _cpFileImporter;
	}

	public CPMeasurementUnitLocalService getCpMeasurementUnitLocalService() {
		return _cpMeasurementUnitLocalService;
	}

	@Reference
	private CatalogResource.Factory _catalogResourceFactory;

	@Reference
	private ChannelResource.Factory _channelResourceFactory;

	@Reference
	private CommerceAccountRoleHelper _commerceAccountRoleHelper;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceInventoryWarehousesImporter
		_commerceInventoryWarehousesImporter;

	@Reference
	private CPDefinitionsImporter _cpDefinitionsImporter;

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

}