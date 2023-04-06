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

import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.payment.model.CommercePaymentEntryAudit",
	service = ModelDocumentContributor.class
)
public class CommercePaymentEntryAuditModelDocumentContributor
	implements ModelDocumentContributor<CommercePaymentEntryAudit> {

	@Override
	public void contribute(
		Document document,
		CommercePaymentEntryAudit commercePaymentEntryAudit) {

		try {
			document.addNumber(
				"commercePaymentEntryId",
				commercePaymentEntryAudit.getCommercePaymentEntryId());
			document.addKeyword(
				"logType", commercePaymentEntryAudit.getLogType());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				long commercePaymentEntryAuditId =
					commercePaymentEntryAudit.getCommercePaymentEntryAuditId();

				_log.warn(
					"Unable to index commerce payment entry audit " +
						commercePaymentEntryAuditId,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentEntryAuditModelDocumentContributor.class);

}