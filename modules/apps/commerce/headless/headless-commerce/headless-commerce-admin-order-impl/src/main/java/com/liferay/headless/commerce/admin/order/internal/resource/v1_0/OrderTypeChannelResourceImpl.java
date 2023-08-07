/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderTypeChannelResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/order-type-channel.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = OrderTypeChannelResource.class
)
public class OrderTypeChannelResourceImpl
	extends BaseOrderTypeChannelResourceImpl {

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
				"Unable to find order type with external reference code " +
					externalReferenceCode);
		}

		return Page.of(
			transform(
				_commerceOrderTypeRelService.
					getCommerceOrderTypeCommerceChannelRels(
						commerceOrderType.getCommerceOrderTypeId(), null,
						pagination.getStartPosition(),
						pagination.getEndPosition()),
				commerceOrderTypeRel -> _toOrderTypeChannel(
					commerceOrderTypeRel.getCommerceOrderTypeRelId())),
			pagination,
			_commerceOrderTypeRelService.
				getCommerceOrderTypeCommerceChannelRelsCount(
					commerceOrderType.getCommerceOrderTypeId(), null));
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

		return Page.of(
			transform(
				_commerceOrderTypeRelService.
					getCommerceOrderTypeCommerceChannelRels(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition()),
				commerceOrderTypeRel -> _toOrderTypeChannel(
					commerceOrderTypeRel.getCommerceOrderTypeRelId())),
			pagination,
			_commerceOrderTypeRelService.
				getCommerceOrderTypeCommerceChannelRelsCount(id, search));
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
				"Unable to find order type with external reference code " +
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

		CommerceChannel commerceChannel = null;

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

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
					"Unable to find channel with external reference code " +
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

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.OrderTypeChannelDTOConverter)"
	)
	private DTOConverter<CommerceOrderTypeRel, OrderTypeChannel>
		_orderTypeChannelDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}