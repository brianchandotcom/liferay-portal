/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.PaymentMethodGroupRelTerm;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingFixedOptionTerm;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.Term;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TermResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/term.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = TermResource.class
)
public class TermResourceImpl extends BaseTermResourceImpl {

	@NestedField(parentClass = PaymentMethodGroupRelTerm.class, value = "term")
	@Override
	public Term getPaymentMethodGroupRelTermTerm(Long id) throws Exception {
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier =
				_commercePaymentMethodGroupRelQualifierService.
					getCommercePaymentMethodGroupRelQualifier(id);

		return _termDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePaymentMethodGroupRelQualifier.getClassPK(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = ShippingFixedOptionTerm.class, value = "term")
	@Override
	public Term getShippingFixedOptionTermTerm(Long id) throws Exception {
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				_commerceShippingFixedOptionQualifierService.
					getCommerceShippingFixedOptionQualifier(id);

		return _termDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceShippingFixedOptionQualifier.getClassPK(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CommercePaymentMethodGroupRelQualifierService
		_commercePaymentMethodGroupRelQualifierService;

	@Reference
	private CommerceShippingFixedOptionQualifierService
		_commerceShippingFixedOptionQualifierService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.TermDTOConverter)"
	)
	private DTOConverter<CommerceTermEntry, Term> _termDTOConverter;

}