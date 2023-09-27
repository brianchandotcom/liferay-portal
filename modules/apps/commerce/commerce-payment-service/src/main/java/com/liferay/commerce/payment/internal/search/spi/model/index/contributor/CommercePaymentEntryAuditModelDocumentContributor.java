/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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