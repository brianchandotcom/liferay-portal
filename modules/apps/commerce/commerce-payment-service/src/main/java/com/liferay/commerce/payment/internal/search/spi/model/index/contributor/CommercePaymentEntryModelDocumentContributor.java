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