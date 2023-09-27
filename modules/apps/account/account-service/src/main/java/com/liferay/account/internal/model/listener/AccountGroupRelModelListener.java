/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.model.listener;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = ModelListener.class)
public class AccountGroupRelModelListener
	extends BaseModelListener<AccountGroupRel> {

	@Override
	public void onAfterCreate(AccountGroupRel accountGroupRel)
		throws ModelListenerException {

		if (accountGroupRel.getClassNameId() ==
				_classNameLocalService.getClassNameId(
					AccountEntry.class.getName())) {

			_reindexAccountEntry(accountGroupRel.getClassPK());
			_reindexAccountGroup(accountGroupRel.getAccountGroupId());
		}
	}

	@Override
	public void onAfterRemove(AccountGroupRel accountGroupRel)
		throws ModelListenerException {

		if (accountGroupRel.getClassNameId() ==
				_classNameLocalService.getClassNameId(
					AccountEntry.class.getName())) {

			_reindexAccountEntry(accountGroupRel.getClassPK());
			_reindexAccountGroup(accountGroupRel.getAccountGroupId());
		}
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

	private void _reindexAccountGroup(long accountGroupId) {
		try {
			Indexer<AccountGroup> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(AccountGroup.class);

			indexer.reindex(AccountGroup.class.getName(), accountGroupId);
		}
		catch (SearchException searchException) {
			throw new ModelListenerException(searchException);
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

}