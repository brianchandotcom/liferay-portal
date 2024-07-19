/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;
import com.liferay.portal.tools.service.builder.test.service.base.IndexEntryLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class IndexEntryLocalServiceImpl extends IndexEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public IndexEntry addIndexEntry(long companyId, String name) {
		long indexEntryId = counterLocalService.increment();

		IndexEntry indexEntry = indexEntryPersistence.create(indexEntryId);

		indexEntry.setCompanyId(companyId);
		indexEntry.setName(name);

		return indexEntryPersistence.updateImpl(indexEntry);
	}

	public boolean addKeywordsEntry(
		long keywordsEntryId, IndexEntry indexEntry) {

		if (!super.addKeywordsEntryIndexEntry(keywordsEntryId, indexEntry)) {
			return false;
		}

		try {
			Indexer<IndexEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(IndexEntry.class);

			indexer.reindex(indexEntry);
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}

		return true;
	}

}