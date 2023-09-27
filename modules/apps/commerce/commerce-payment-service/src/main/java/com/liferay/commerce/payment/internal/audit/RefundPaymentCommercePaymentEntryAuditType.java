/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.audit;

import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.payment.audit.CommercePaymentEntryAuditType;
import com.liferay.commerce.payment.constants.CommercePaymentEntryAuditConstants;
import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "commerce.payment.entry.audit.type.key=" + CommercePaymentEntryAuditConstants.TYPE_REFUND_PAYMENT,
	service = CommercePaymentEntryAuditType.class
)
public class RefundPaymentCommercePaymentEntryAuditType
	implements CommercePaymentEntryAuditType {

	@Override
	public String formatAmount(
			CommercePaymentEntryAudit commercePaymentEntryAudit, Locale locale)
		throws PortalException {

		return _commercePriceFormatter.format(
			_commerceCurrencyLocalService.getCommerceCurrency(
				commercePaymentEntryAudit.getCompanyId(),
				commercePaymentEntryAudit.getCurrencyCode()),
			commercePaymentEntryAudit.getAmount(), locale);
	}

	@Override
	public String formatLog(
			CommercePaymentEntryAudit commercePaymentEntryAudit, Locale locale)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			commercePaymentEntryAudit.getLogTypeSettings());

		long classNameId = (long)jsonObject.get(
			CommercePaymentEntryAuditConstants.FIELD_CLASS_NAME_ID);

		return StringBundler.concat(
			_language.format(
				locale, "payment-refund-for-x",
				_classNameLocalService.getClassName(classNameId)),
			", ",
			_language.format(
				locale, "id-x",
				jsonObject.get(
					CommercePaymentEntryAuditConstants.FIELD_CLASS_PK)));
	}

	@Override
	public String getLog(Map<String, Object> context) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (Map.Entry<String, Object> entry : context.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject.toString();
	}

	@Override
	public String getType() {
		return CommercePaymentEntryAuditConstants.TYPE_REFUND_PAYMENT;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}