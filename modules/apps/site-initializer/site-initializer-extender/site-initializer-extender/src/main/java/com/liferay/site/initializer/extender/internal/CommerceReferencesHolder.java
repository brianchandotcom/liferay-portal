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
import com.liferay.commerce.initializer.util.CPOptionsImporter;
import com.liferay.commerce.initializer.util.CPSpecificationOptionsImporter;
import com.liferay.commerce.initializer.util.CommerceInventoryWarehousesImporter;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductOptionResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductSpecificationResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CommerceReferencesHolder.class)
public class CommerceReferencesHolder {

	@Reference
	public CatalogResource.Factory catalogResourceFactory;

	@Reference
	public ChannelResource.Factory channelResourceFactory;

	@Reference
	public CommerceAccountRoleHelper commerceAccountRoleHelper;

	@Reference
	public CommerceChannelLocalService commerceChannelLocalService;

	@Reference
	public CommerceCurrencyLocalService commerceCurrencyLocalService;

	@Reference
	public CommerceInventoryWarehousesImporter
		commerceInventoryWarehousesImporter;

	@Reference
	public CommerceNotificationTemplateLocalService
		commerceNotificationTemplateLocalService;

	@Reference
	public CPDefinitionLocalService cpDefinitionLocalService;

	@Reference
	public CPDefinitionsImporter cpDefinitionsImporter;

	@Reference
	public CPInstanceLocalService cpInstanceLocalService;

	@Reference
	public CPMeasurementUnitLocalService cpMeasurementUnitLocalService;

	@Reference
	public CPOptionsImporter cpOptionsImporter;

	@Reference
	public CPSpecificationOptionsImporter cpSpecificationOptionsImporter;

	@Reference
	public OptionResource.Factory optionResourceFactory;

	@Reference
	public ProductOptionResource.Factory productOptionResourceFactory;

	@Reference
	public ProductSpecificationResource.Factory
		productSpecificationResourceFactory;

}