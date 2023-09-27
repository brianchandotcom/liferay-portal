/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.search.spi.model.index.contributor;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

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
			document.addNumber(
				"classNameId", commercePaymentEntry.getClassNameId());
			document.addNumber("classPK", commercePaymentEntry.getClassPK());
			document.addKeyword(
				"currencyCode", commercePaymentEntry.getCurrencyCode());
			document.addKeyword(
				"paymentIntegrationKey",
				commercePaymentEntry.getPaymentIntegrationKey());
			document.addKeyword(
				"transactionCode", commercePaymentEntry.getTransactionCode());
			document.addKeyword(
				"paymentStatus", commercePaymentEntry.getPaymentStatus());
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

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentEntryModelDocumentContributor.class);

}