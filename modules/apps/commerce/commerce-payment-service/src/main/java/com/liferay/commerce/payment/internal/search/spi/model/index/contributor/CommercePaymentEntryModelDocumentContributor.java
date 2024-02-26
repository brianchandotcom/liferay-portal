/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.search.spi.model.index.contributor;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.payment.model.CommercePaymentEntry",
	service = ModelDocumentContributor.class
)
public class CommercePaymentEntryModelDocumentContributor
	implements ModelDocumentContributor<CommercePaymentEntry> {

	@Override
	public void contribute(
		Document document, CommercePaymentEntry commercePaymentEntry) {

		try {
			document.addNumberSortable(
				Field.ENTRY_CLASS_PK,
				commercePaymentEntry.getCommercePaymentEntryId());

			document.addNumber(
				"classNameId", commercePaymentEntry.getClassNameId());
			document.addNumber("classPK", commercePaymentEntry.getClassPK());
			document.addKeyword(
				"currencyCode", commercePaymentEntry.getCurrencyCode());
			document.addKeyword(
				"externalReferenceCode",
				commercePaymentEntry.getExternalReferenceCode());
			document.addText("note", commercePaymentEntry.getNote());
			document.addKeyword(
				"paymentIntegrationKey",
				commercePaymentEntry.getPaymentIntegrationKey());
			document.addKeyword(
				"paymentStatus", commercePaymentEntry.getPaymentStatus());
			document.addKeyword(
				"reasonKey", commercePaymentEntry.getReasonKey());

			String[] languageIds = _getLanguageIds(
				commercePaymentEntry.getDefaultLanguageId(),
				commercePaymentEntry.getReasonName());

			for (String languageId : languageIds) {
				document.addText(
					_localization.getLocalizedName("reasonName", languageId),
					commercePaymentEntry.getReasonName(languageId));
			}

			document.addKeyword(
				"transactionCode", commercePaymentEntry.getTransactionCode());
			document.addNumber("type", commercePaymentEntry.getType());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to commerce index payment entry " +
						commercePaymentEntry.getCommercePaymentEntryId(),
					exception);
			}
		}
	}

	private String[] _getLanguageIds(String defaultLanguageId, String value) {
		String[] languageIds = _localization.getAvailableLanguageIds(value);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentEntryModelDocumentContributor.class);

	@Reference
	private Localization _localization;

}