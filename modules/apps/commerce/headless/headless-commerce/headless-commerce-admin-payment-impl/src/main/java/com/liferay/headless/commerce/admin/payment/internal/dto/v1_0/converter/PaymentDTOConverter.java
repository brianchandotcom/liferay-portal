/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.payment.internal.dto.v1_0.converter;

import com.liferay.commerce.constants.CommercePaymentEntryConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryService;
import com.liferay.headless.commerce.admin.payment.dto.v1_0.Payment;
import com.liferay.headless.commerce.admin.payment.dto.v1_0.Status;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Commerce.Admin.Payment",
		"dto.class.name=com.liferay.commerce.model.CommercePayment",
		"version=v1.0"
	},
	service = DTOConverter.class
)
public class PaymentDTOConverter
	implements DTOConverter<CommercePaymentEntry, Payment> {

	@Override
	public String getContentType() {
		return Payment.class.getSimpleName();
	}

	@Override
	public Payment toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePaymentEntry commercePaymentEntry =
			_commercePaymentService.getCommercePaymentEntry(
				GetterUtil.getLong(dtoConverterContext.getId()));

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				commercePaymentEntry.getCompanyId(),
				commercePaymentEntry.getCurrencyCode());

		Locale locale = dtoConverterContext.getLocale();

		ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
			locale);

		return new Payment() {
			{
				actions = dtoConverterContext.getActions();
				amount = commercePaymentEntry.getAmount();
				amountFormatted = _formatAmount(
					commercePaymentEntry.getAmount(), commerceCurrency, locale);
				callbackURL = commercePaymentEntry.getCallbackURL();
				cancelURL = commercePaymentEntry.getCancelURL();
				channelId = commercePaymentEntry.getCommerceChannelId();
				comment = commercePaymentEntry.getNote();
				createDate = commercePaymentEntry.getCreateDate();
				currencyCode = commercePaymentEntry.getCurrencyCode();
				errorMessages = commercePaymentEntry.getErrorMessages();
				externalReferenceCode =
					commercePaymentEntry.getExternalReferenceCode();
				id = commercePaymentEntry.getCommercePaymentEntryId();
				languageId = commercePaymentEntry.getLanguageId();
				paymentIntegrationKey =
					commercePaymentEntry.getPaymentIntegrationKey();
				paymentIntegrationType =
					commercePaymentEntry.getPaymentIntegrationType();
				paymentStatus = commercePaymentEntry.getPaymentStatus();
				paymentStatusInfo = _toPaymentStatusInfo(
					commercePaymentEntry.getPaymentStatus(),
					CommercePaymentEntryConstants.getPaymentStatusLabel(
						commercePaymentEntry.getPaymentStatus()),
					_language.get(
						resourceBundle,
						CommercePaymentEntryConstants.getPaymentStatusLabel(
							commercePaymentEntry.getPaymentStatus())));
				reasonKey = commercePaymentEntry.getReasonKey();
				reasonName = LanguageUtils.getLanguageIdMap(
					commercePaymentEntry.getReasonNameMap());
				redirectURL = commercePaymentEntry.getRedirectURL();
				relatedItemId = commercePaymentEntry.getClassPK();
				relatedItemName = commercePaymentEntry.getClassName();
				relatedItemNameLabel = _language.get(
					resourceBundle,
					"model.resource." + commercePaymentEntry.getClassName());
				transactionCode = commercePaymentEntry.getTransactionCode();
				type = commercePaymentEntry.getType();
				typeLabel = _language.get(
					resourceBundle,
					CommercePaymentEntryConstants.getTypeLabel(
						commercePaymentEntry.getType()));
			}
		};
	}

	private String _formatAmount(
			BigDecimal amount, CommerceCurrency commerceCurrency, Locale locale)
		throws Exception {

		if (amount == null) {
			amount = BigDecimal.ZERO;
		}

		return _commercePriceFormatter.format(commerceCurrency, amount, locale);
	}

	private Status _toPaymentStatusInfo(
		int paymentStatus, String commercePaymentEntryWorkflowStatusLabel,
		String commercePaymentEntryWorkflowStatusLabelI18n) {

		return new Status() {
			{
				code = paymentStatus;
				label = commercePaymentEntryWorkflowStatusLabel;
				label_i18n = commercePaymentEntryWorkflowStatusLabelI18n;
			}
		};
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommercePaymentEntryService _commercePaymentService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private Language _language;

}