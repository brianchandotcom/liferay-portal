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

import com.liferay.commerce.payment.exception.NoSuchPaymentMethodGroupRelException;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierService;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.PaymentMethodGroupRelTerm;
import com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.PaymentMethodGroupRelTermDTOConverter;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelTermResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/payment-method-group-rel-term.properties",
	scope = ServiceScope.PROTOTYPE,
	service = PaymentMethodGroupRelTermResource.class
)
public class PaymentMethodGroupRelTermResourceImpl
	extends BasePaymentMethodGroupRelTermResourceImpl {

	@Override
	public void deletePaymentMethodGroupRelTerm(Long id) throws Exception {
		_commercePaymentMethodGroupRelQualifierService.
			deleteCommercePaymentMethodGroupRelQualifier(id);
	}

	@Override
	public Page<PaymentMethodGroupRelTerm>
			getPaymentMethodGroupRelIdPaymentMethodGroupRelTermsPage(
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
					getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition()),
				corEntryRel -> _toPaymentMethodGroupRelTerm(corEntryRel)),
			pagination,
			_commercePaymentMethodGroupRelQualifierService.
				getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
					id, search));
	}

	@Override
	public PaymentMethodGroupRelTerm
			postPaymentMethodGroupRelIdPaymentMethodGroupRelTerm(
				Long id, PaymentMethodGroupRelTerm paymentMethodGroupRelTerm)
		throws Exception {

		CommerceTermEntry commerceTermEntry = _getCommerceTermEntry(
			paymentMethodGroupRelTerm);

		return _toPaymentMethodGroupRelTerm(
			_commercePaymentMethodGroupRelQualifierService.
				addCommercePaymentMethodGroupRelQualifier(
					CommerceTermEntry.class.getName(),
					commerceTermEntry.getCommerceTermEntryId(), id));
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
				"deletePaymentMethodGroupRelTerm",
				_commercePaymentMethodGroupRelQualifierModelResourcePermission)
		).build();
	}

	private CommerceTermEntry _getCommerceTermEntry(
			PaymentMethodGroupRelTerm paymentMethodGroupRelTerm)
		throws Exception {

		CommerceTermEntry commerceTerm = null;

		if (paymentMethodGroupRelTerm.getTermId() > 0) {
			commerceTerm = _commerceTermEntryService.getCommerceTermEntry(
				paymentMethodGroupRelTerm.getTermId());
		}
		else {
			commerceTerm =
				_commerceTermEntryService.fetchByExternalReferenceCode(
					contextCompany.getCompanyId(),
					paymentMethodGroupRelTerm.getTermExternalReferenceCode());
		}

		return commerceTerm;
	}

	private PaymentMethodGroupRelTerm _toPaymentMethodGroupRelTerm(
			CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier)
		throws Exception {

		return _paymentMethodGroupRelTermDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commercePaymentMethodGroupRelQualifier),
				_dtoConverterRegistry,
				commercePaymentMethodGroupRelQualifier.
					getCommercePaymentMethodGroupRelQualifierId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

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
	private CommerceTermEntryService _commerceTermEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private PaymentMethodGroupRelTermDTOConverter
		_paymentMethodGroupRelTermDTOConverter;

}