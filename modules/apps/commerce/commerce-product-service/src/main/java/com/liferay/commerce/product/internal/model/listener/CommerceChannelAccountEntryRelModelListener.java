/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Danny Situ
 */
@Component(service = ModelListener.class)
public class CommerceChannelAccountEntryRelModelListener
	extends BaseModelListener<CommerceChannelAccountEntryRel> {

	@Override
	public void onAfterCreate(
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel) {

		_reindexAccountEntry(
			commerceChannelAccountEntryRel.getAccountEntryId());
	}

	@Override
	public void onAfterRemove(
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel) {

		_reindexAccountEntry(
			commerceChannelAccountEntryRel.getAccountEntryId());
	}

	@Override
	public void onAfterUpdate(
			CommerceChannelAccountEntryRel
				originalCommerceChannelAccountEntryRel,
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel)
		throws ModelListenerException {

		if (originalCommerceChannelAccountEntryRel.getAccountEntryId() ==
				commerceChannelAccountEntryRel.getAccountEntryId()) {

			return;
		}

		_reindexAccountEntry(
			originalCommerceChannelAccountEntryRel.getAccountEntryId());
		_reindexAccountEntry(
			commerceChannelAccountEntryRel.getAccountEntryId());
	}

	private void _reindexAccountEntry(long accountEntryId) {
		try {
			Indexer<AccountEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(AccountEntry.class);

			indexer.reindex(AccountEntry.class.getName(), accountEntryId);
		}
		catch (SearchException searchException) {
			throw new ModelListenerException(searchException);
		}
	}

}