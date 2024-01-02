/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = "indexer.class.name=com.liferay.account.model.AccountEntry",
	service = ModelDocumentContributor.class
)
public class AccountEntryModelDocumentContributor
	implements ModelDocumentContributor<AccountEntry> {

	@Override
	public void contribute(Document document, AccountEntry accountEntry) {
		try {
			document.addKeyword(
				"commerceChannelIds", getCommerceChannelIds(accountEntry));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index account entry " +
						accountEntry.getAccountEntryId(),
					exception);
			}
		}
	}

	protected long[] getCommerceChannelIds(AccountEntry accountEntry) {
		long[] commerceChannelIds = ListUtil.toLongArray(
			_commerceChannelAccountEntryRelLocalService.
				getCommerceChannelAccountEntryRels(
					accountEntry.getAccountEntryId(),
					CommerceChannelAccountEntryRelConstants.TYPE_ELIGIBILITY,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
			CommerceChannelAccountEntryRel::getCommerceChannelId);

		if (commerceChannelIds.length == 0) {
			commerceChannelIds = new long[] {0};
		}

		return commerceChannelIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryModelDocumentContributor.class);

	@Reference
	private CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

}