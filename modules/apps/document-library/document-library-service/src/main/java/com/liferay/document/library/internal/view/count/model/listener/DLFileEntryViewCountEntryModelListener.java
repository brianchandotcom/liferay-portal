/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.view.count.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.view.count.model.ViewCountEntry;
import com.liferay.view.count.model.listener.ViewCountEntryModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ViewCountEntryModelListener.class)
public class DLFileEntryViewCountEntryModelListener
	implements ViewCountEntryModelListener {

	@Override
	public String getModelClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public void onAfterIncrement(ViewCountEntry viewCountEntry) {
		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
			viewCountEntry.getClassPK());

		if (dlFileEntry == null) {
			return;
		}

		try {
			Indexer<DLFileEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(DLFileEntry.class);

			indexer.reindex(dlFileEntry);
		}
		catch (SearchException searchException) {
			ReflectionUtil.throwException(searchException);
		}
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}