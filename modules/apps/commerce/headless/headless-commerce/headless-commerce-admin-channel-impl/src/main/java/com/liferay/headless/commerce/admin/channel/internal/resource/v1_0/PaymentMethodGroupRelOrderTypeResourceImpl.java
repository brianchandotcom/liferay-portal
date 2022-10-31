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

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.payment.exception.NoSuchPaymentMethodGroupRelException;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierService;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.PaymentMethodGroupRelOrderType;
import com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.PaymentMethodGroupRelOrderTypeDTOConverter;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelOrderTypeResource;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/payment-method-group-rel-order-type.properties",
	scope = ServiceScope.PROTOTYPE,
	service = PaymentMethodGroupRelOrderTypeResource.class
)
public class PaymentMethodGroupRelOrderTypeResourceImpl
	extends BasePaymentMethodGroupRelOrderTypeResourceImpl {

	@Override
	public void deletePaymentMethodGroupRelOrderType(Long id) throws Exception {
		_commercePaymentMethodGroupRelQualifierService.
			deleteCommercePaymentMethodGroupRelQualifier(id);
	}

	@Override
	public Page<PaymentMethodGroupRelOrderType>
			getPaymentMethodGroupRelIdPaymentMethodGroupRelOrderTypesPage(
				Long id, String search, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelService.
				getCommercePaymentMethodGroupRel(id);

		if (commercePaymentMethodGroupRel == null) {
			throw new NoSuchPaymentMethodGroupRelException(
				"Unable to find payment method group rel with ID " + id);
		}

		return Page.of(
			transform(
				_commercePaymentMethodGroupRelQualifierService.
					getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition()),
				corEntryRel -> _toPaymentMethodGroupRelOrderType(corEntryRel)),
			pagination,
			_commercePaymentMethodGroupRelQualifierService.
				getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
					id, search));
	}

	@Override
	public PaymentMethodGroupRelOrderType
			postPaymentMethodGroupRelIdPaymentMethodGroupRelOrderType(
				Long id,
				PaymentMethodGroupRelOrderType paymentMethodGroupRelOrderType)
		throws Exception {

		CommerceOrderType commerceOrderType = _getCommerceOrderType(
			paymentMethodGroupRelOrderType);

		return _toPaymentMethodGroupRelOrderType(
			_commercePaymentMethodGroupRelQualifierService.
				addCommercePaymentMethodGroupRelQualifier(
					CommerceOrderType.class.getName(),
					commerceOrderType.getCommerceOrderTypeId(), id));
	}

	private Map<String, Map<String, String>> _getActions(
			CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				commercePaymentMethodGroupRelQualifier.
					getCommercePaymentMethodGroupRelQualifierId(),
				"deletePaymentMethodGroupRelOrderType",
				_commercePaymentMethodGroupRelQualifierModelResourcePermission)
		).build();
	}

	private CommerceOrderType _getCommerceOrderType(
			PaymentMethodGroupRelOrderType paymentMethodGroupRelOrderType)
		throws Exception {

		CommerceOrderType commerceOrderType = null;

		if (paymentMethodGroupRelOrderType.getOrderTypeId() > 0) {
			commerceOrderType = _commerceOrderTypeService.getCommerceOrderType(
				paymentMethodGroupRelOrderType.getOrderTypeId());
		}
		else {
			commerceOrderType =
				_commerceOrderTypeService.fetchByExternalReferenceCode(
					paymentMethodGroupRelOrderType.
						getOrderTypeExternalReferenceCode(),
					contextCompany.getCompanyId());
		}

		return commerceOrderType;
	}

	private PaymentMethodGroupRelOrderType _toPaymentMethodGroupRelOrderType(
			CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier)
		throws Exception {

		return _paymentMethodGroupRelOrderTypeDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commercePaymentMethodGroupRelQualifier),
				_dtoConverterRegistry,
				commercePaymentMethodGroupRelQualifier.
					getCommercePaymentMethodGroupRelQualifierId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier)"
	)
	private ModelResourcePermission<CommercePaymentMethodGroupRelQualifier>
		_commercePaymentMethodGroupRelQualifierModelResourcePermission;

	@Reference
	private CommercePaymentMethodGroupRelQualifierService
		_commercePaymentMethodGroupRelQualifierService;

	@Reference
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private PaymentMethodGroupRelOrderTypeDTOConverter
		_paymentMethodGroupRelOrderTypeDTOConverter;

}