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

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchOrderTypeException;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceOrderTypeRelService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderType;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderTypeChannel;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.OrderTypeChannelDTOConverter;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderTypeChannelResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
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
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/order-type-channel.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, OrderTypeChannelResource.class}
)
public class OrderTypeChannelResourceImpl
	extends BaseOrderTypeChannelResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteOrderTypeChannel(Long id) throws Exception {
		_commerceOrderTypeRelService.deleteCommerceOrderTypeRel(id);
	}

	@Override
	public Page<OrderTypeChannel>
			getOrderTypeByExternalReferenceCodeOrderTypeChannelsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrderType == null) {
			throw new NoSuchOrderTypeException(
				"Unable to find Order Type with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CommerceOrderTypeRel> commerceOrderTypeRels =
			_commerceOrderTypeRelService.
				getCommerceOrderTypeCommerceChannelRels(
					commerceOrderType.getCommerceOrderTypeId(), null,
					pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_commerceOrderTypeRelService.
				getCommerceOrderTypeCommerceChannelRelsCount(
					commerceOrderType.getCommerceOrderTypeId(), null);

		return Page.of(
			_toOrderTypeChannels(commerceOrderTypeRels), pagination,
			totalItems);
	}

	@NestedField(parentClass = OrderType.class, value = "orderTypeChannels")
	@Override
	public Page<OrderTypeChannel> getOrderTypeIdOrderTypeChannelsPage(
			Long id, String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.fetchCommerceOrderType(id);

		if (commerceOrderType == null) {
			return Page.of(Collections.emptyList());
		}

		List<CommerceOrderTypeRel> commerceOrderTypeRels =
			_commerceOrderTypeRelService.
				getCommerceOrderTypeCommerceChannelRels(
					id, search, pagination.getStartPosition(),
					pagination.getEndPosition());

		int totalItems =
			_commerceOrderTypeRelService.
				getCommerceOrderTypeCommerceChannelRelsCount(id, search);

		return Page.of(
			_toOrderTypeChannels(commerceOrderTypeRels), pagination,
			totalItems);
	}

	@Override
	public OrderTypeChannel
			postOrderTypeByExternalReferenceCodeOrderTypeChannel(
				String externalReferenceCode, OrderTypeChannel orderTypeChannel)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrderType == null) {
			throw new NoSuchOrderTypeException(
				"Unable to find Order Type with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _addCommerceOrderTypeChannelRel(
			commerceOrderType.getCommerceOrderTypeId(), orderTypeChannel);
	}

	@Override
	public OrderTypeChannel postOrderTypeIdOrderTypeChannel(
			Long id, OrderTypeChannel orderTypeChannel)
		throws Exception {

		return _addCommerceOrderTypeChannelRel(id, orderTypeChannel);
	}

	private OrderTypeChannel _addCommerceOrderTypeChannelRel(
			long commerceOrderTypeId, OrderTypeChannel orderTypeChannel)
		throws Exception {

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		CommerceChannel commerceChannel;

		if (Validator.isNull(
				orderTypeChannel.getChannelExternalReferenceCode())) {

			commerceChannel = _commerceChannelService.getCommerceChannel(
				orderTypeChannel.getChannelId());
		}
		else {
			commerceChannel =
				_commerceChannelService.fetchByExternalReferenceCode(
					orderTypeChannel.getChannelExternalReferenceCode(),
					serviceContext.getCompanyId());

			if (commerceChannel == null) {
				throw new NoSuchChannelException(
					"Unable to find Channel with externalReferenceCode: " +
						orderTypeChannel.getChannelExternalReferenceCode());
			}
		}

		CommerceOrderTypeRel commerceOrderTypeRel =
			_commerceOrderTypeRelService.addCommerceOrderTypeRel(
				CommerceChannel.class.getName(),
				commerceChannel.getCommerceChannelId(), commerceOrderTypeId,
				serviceContext);

		return _toOrderTypeChannel(
			commerceOrderTypeRel.getCommerceOrderTypeRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceOrderTypeRel commerceOrderTypeRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceOrderTypeRel.getCommerceOrderTypeRelId(),
				"deleteOrderTypeChannel",
				_commerceOrderTypeRelModelResourcePermission)
		).build();
	}

	private OrderTypeChannel _toOrderTypeChannel(Long commerceOrderTypeRelId)
		throws Exception {

		CommerceOrderTypeRel commerceOrderTypeRel =
			_commerceOrderTypeRelService.getCommerceOrderTypeRel(
				commerceOrderTypeRelId);

		return _orderTypeChannelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceOrderTypeRel), _dtoConverterRegistry,
				commerceOrderTypeRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<OrderTypeChannel> _toOrderTypeChannels(
			List<CommerceOrderTypeRel> commerceOrderTypeRels)
		throws Exception {

		List<OrderTypeChannel> orderTypeChannels = new ArrayList<>();

		for (CommerceOrderTypeRel commerceOrderTypeRel :
				commerceOrderTypeRels) {

			orderTypeChannels.add(
				_toOrderTypeChannel(
					commerceOrderTypeRel.getCommerceOrderTypeRelId()));
		}

		return orderTypeChannels;
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrderTypeRel)"
	)
	private ModelResourcePermission<CommerceOrderTypeRel>
		_commerceOrderTypeRelModelResourcePermission;

	@Reference
	private CommerceOrderTypeRelService _commerceOrderTypeRelService;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private OrderTypeChannelDTOConverter _orderTypeChannelDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}