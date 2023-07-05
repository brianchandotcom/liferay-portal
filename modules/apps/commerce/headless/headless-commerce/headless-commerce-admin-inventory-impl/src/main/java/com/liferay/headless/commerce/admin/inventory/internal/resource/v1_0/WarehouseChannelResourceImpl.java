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

package com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchWarehouseException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.Warehouse;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseChannel;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.WarehouseChannelResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/warehouse-channel.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, WarehouseChannelResource.class}
)
public class WarehouseChannelResourceImpl
	extends BaseWarehouseChannelResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteWarehouseChannel(Long id) throws Exception {
		_commerceChannelRelService.deleteCommerceChannelRel(id);
	}

	@Override
	public Page<WarehouseChannel>
			getWarehouseByExternalReferenceCodeWarehouseChannelsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryWarehouse == null) {
			throw new NoSuchWarehouseException(
				"Unable to find warehouse with external reference code " +
					externalReferenceCode);
		}

		List<CommerceChannelRel> commerceChannelRels =
			_commerceChannelRelService.getCommerceChannelRels(
				CommerceInventoryWarehouse.class.getName(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				null, pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems = _commerceChannelRelService.getCommerceChannelRelsCount(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

		return Page.of(
			_toWarehouseChannels(commerceChannelRels), pagination, totalItems);
	}

	@NestedField(parentClass = Warehouse.class, value = "warehouseChannels")
	public Page<WarehouseChannel> getWarehouseIdWarehouseChannelsPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.
				fetchByCommerceInventoryWarehouse(id);

		if (commerceInventoryWarehouse == null) {
			return Page.of(Collections.emptyList());
		}

		List<CommerceChannelRel> commerceChannelRel =
			_commerceChannelRelService.getCommerceChannelRels(
				CommerceInventoryWarehouse.class.getName(), id, search,
				pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems = _commerceChannelRelService.getCommerceChannelRelsCount(
			CommerceInventoryWarehouse.class.getName(), id, search);

		return Page.of(
			_toWarehouseChannels(commerceChannelRel), pagination, totalItems);
	}

	@Override
	public WarehouseChannel
			postWarehouseByExternalReferenceCodeWarehouseChannel(
				String externalReferenceCode, WarehouseChannel warehouseChannel)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryWarehouse == null) {
			throw new NoSuchWarehouseException(
				"Unable to find warehouse with external reference code " +
					externalReferenceCode);
		}

		CommerceChannelRel commerceChannelRel =
			_addCommerceInventoryWarehouseChannelRel(
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				warehouseChannel);

		return _toWarehouseChannel(
			commerceChannelRel.getCommerceChannelRelId());
	}

	@Override
	public WarehouseChannel postWarehouseIdWarehouseChannel(
			Long id, WarehouseChannel warehouseChannel)
		throws Exception {

		CommerceChannelRel commerceChannelRel =
			_addCommerceInventoryWarehouseChannelRel(id, warehouseChannel);

		return _toWarehouseChannel(
			commerceChannelRel.getCommerceChannelRelId());
	}

	private CommerceChannelRel _addCommerceInventoryWarehouseChannelRel(
			long commerceInventoryWarehouseId,
			WarehouseChannel warehouseChannel)
		throws Exception {

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		CommerceChannel commerceChannel;

		if (Validator.isNull(
				warehouseChannel.getChannelExternalReferenceCode())) {

			commerceChannel = _commerceChannelService.fetchCommerceChannel(
				warehouseChannel.getChannelId());

			if (commerceChannel == null) {
				throw new NoSuchChannelException(
					"Unable to find channel with commerce channel id " +
						warehouseChannel.getChannelId());
			}
		}
		else {
			commerceChannel =
				_commerceChannelService.fetchByExternalReferenceCode(
					warehouseChannel.getChannelExternalReferenceCode(),
					serviceContext.getCompanyId());

			if (commerceChannel == null) {
				throw new NoSuchChannelException(
					"Unable to find channel with external reference code " +
						warehouseChannel.getChannelExternalReferenceCode());
			}
		}

		return _commerceChannelRelService.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouseId,
			commerceChannel.getCommerceChannelId(), serviceContext);
	}

	private Map<String, Map<String, String>> _getActions(
		CommerceChannelRel commerceChannelRel) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceChannelRel.getCommerceChannelRelId(),
				"deleteWarehouseChannel",
				_commerceChannelRelModelResourcePermission)
		).build();
	}

	private WarehouseChannel _toWarehouseChannel(Long commerceChannelRelId)
		throws Exception {

		CommerceChannelRel commerceChannelRel =
			_commerceChannelRelService.getCommerceChannelRel(
				commerceChannelRelId);

		return _warehouseChannelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceChannelRel), _dtoConverterRegistry,
				commerceChannelRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<WarehouseChannel> _toWarehouseChannels(
			List<CommerceChannelRel> commerceChannelRels)
		throws Exception {

		List<WarehouseChannel> warehouseChannels = new ArrayList<>();

		for (CommerceChannelRel commerceChannelRel : commerceChannelRels) {
			warehouseChannels.add(
				_toWarehouseChannel(
					commerceChannelRel.getCommerceChannelRelId()));
		}

		return warehouseChannels;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannelRel)"
	)
	private ModelResourcePermission<CommerceChannelRel>
		_commerceChannelRelModelResourcePermission;

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.inventory.model.CommerceInventoryWarehouse)"
	)
	private ModelResourcePermission<CommerceInventoryWarehouse>
		_commerceInventoryWarehouseModelResourcePermission;

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter.WarehouseChannelDTOConverter)"
	)
	private DTOConverter<CommerceChannelRel, WarehouseChannel>
		_warehouseChannelDTOConverter;

}