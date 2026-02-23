/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.search.spi.model.index.contributor;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommercePaymentEntry> {

	public CommercePaymentEntryModelIndexerWriterContributor(
		CommercePaymentEntryLocalService commercePaymentEntryLocalService) {

		_commercePaymentEntryLocalService = commercePaymentEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commercePaymentEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommercePaymentEntry commercePaymentEntry) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommercePaymentEntryLocalService
		_commercePaymentEntryLocalService;

}