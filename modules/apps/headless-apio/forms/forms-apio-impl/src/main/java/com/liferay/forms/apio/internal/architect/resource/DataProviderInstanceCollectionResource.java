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

package com.liferay.forms.apio.internal.architect.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.forms.apio.architect.identifier.DataProviderInstanceIdentifier;
import com.liferay.portal.kernel.model.Company;
import com.liferay.site.apio.identifier.WebSiteIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose FormInstance resources through
 * a web API. The resources are mapped from the internal model {@code
 * DDMDataProviderInstance}.
 * @author Victor Oliveira
 */
@Component(immediate = true)
public class DataProviderInstanceCollectionResource
	implements NestedCollectionResource<DDMDataProviderInstance, Long,
		DataProviderInstanceIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<DDMDataProviderInstance, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<DDMDataProviderInstance, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "data-provider-instance";
	}

	@Override
	public ItemRoutes<DDMDataProviderInstance, Long> itemRoutes(
		ItemRoutes.Builder<DDMDataProviderInstance, Long> builder) {

		return builder.addGetter(
			_ddmDataProviderInstanceService::getDataProviderInstance
		).build();
	}

	@Override
	public Representor<DDMDataProviderInstance, Long> representor(
		Representor.Builder<DDMDataProviderInstance, Long> builder) {

		return builder.types(
			"DataProviderInstance"
		).identifier(
			DDMDataProviderInstance::getDataProviderInstanceId
		).addBidirectionalModel(
			"webSite", "data-provider-instance", WebSiteIdentifier.class,
			DDMDataProviderInstance::getGroupId
		).addDate(
			"createDate", DDMDataProviderInstance::getCreateDate
		).addDate(
			"modifiedDate", DDMDataProviderInstance::getModifiedDate
		).addLocalizedStringByLocale(
			"description", DDMDataProviderInstance::getDescription
		).addLocalizedStringByLocale(
			"name", DDMDataProviderInstance::getName
		).addNumber(
			"companyId", DDMDataProviderInstance::getCompanyId
		).addNumber(
			"groupId", DDMDataProviderInstance::getGroupId
		).addNumber(
			"userId", DDMDataProviderInstance::getUserId
		).addString(
			"userName", DDMDataProviderInstance::getUserName
		).addString(
			"definition", DDMDataProviderInstance::getDefinition
		).addString(
			"type", DDMDataProviderInstance::getType
		).build();
	}

	private PageItems<DDMDataProviderInstance> _getPageItems(
		Pagination pagination, Long groupId, Company company) {

		long[] groupIds = {groupId};
		long companyId = company.getCompanyId();

		List<DDMDataProviderInstance> ddmDataProviderInstance =
			_ddmDataProviderInstanceService.getDataProviderInstances(
				companyId, groupIds, pagination.getStartPosition(),
				pagination.getEndPosition());

		int count =
			_ddmDataProviderInstanceService.getDataProviderInstancesCount(
				companyId, groupIds);

		return new PageItems<>(ddmDataProviderInstance, count);
	}

	@Reference
	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;

}