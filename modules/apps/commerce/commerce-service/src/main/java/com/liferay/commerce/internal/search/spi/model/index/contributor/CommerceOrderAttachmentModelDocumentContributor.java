/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderAttachment;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceOrderAttachment",
	service = ModelDocumentContributor.class
)
public class CommerceOrderAttachmentModelDocumentContributor
	implements ModelDocumentContributor<CommerceOrderAttachment> {

	@Override
	public void contribute(
		Document document, CommerceOrderAttachment commerceOrderAttachment) {

		document.addNumberSortable(
			Field.ENTRY_CLASS_PK,
			commerceOrderAttachment.getCommerceOrderAttachmentId());
		document.addNumber(
			"commerceOrderId", commerceOrderAttachment.getCommerceOrderId());
		document.addKeyword(
			"externalReferenceCode",
			commerceOrderAttachment.getExternalReferenceCode());
		document.addNumber(
			"fileEntryId", commerceOrderAttachment.getFileEntryId());
		document.addNumberSortable(
			"priority", commerceOrderAttachment.getPriority());
		document.addKeyword(
			"restricted",
			String.valueOf(commerceOrderAttachment.isRestricted()));
		document.addText("title", commerceOrderAttachment.getTitle());
		document.addKeyword(
			"type", StringUtil.toLowerCase(commerceOrderAttachment.getType()));

		try {
			CommerceOrder commerceOrder =
				_commerceOrderLocalService.fetchCommerceOrder(
					commerceOrderAttachment.getCommerceOrderId());

			if (commerceOrder == null) {
				return;
			}

			AccountEntry accountEntry = commerceOrder.getAccountEntry();

			if (accountEntry == null) {
				return;
			}

			document.addNumber(
				"accountEntryGroupId", accountEntry.getAccountEntryGroupId());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to index account group for commerce order ",
						"attachment ",
						commerceOrderAttachment.getCommerceOrderAttachmentId()),
					portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderAttachmentModelDocumentContributor.class);

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}