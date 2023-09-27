/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.search.DDMStructureIndexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.search.indexer.IndexerWriter;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lucas Marques de Paula
 */
@Component(
	property = "ddm.structure.indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntryMetadata",
	service = DDMStructureIndexer.class
)
public class DLFileEntryMetadataDDMStructureIndexer
	implements DDMStructureIndexer {

	@Override
	public void reindexDDMStructures(List<Long> ddmStructureIds)
		throws SearchException {

		if (_indexStatusManager.isIndexReadOnly()) {
			return;
		}

		List<DLFileEntry> dlFileEntries =
			dlFileEntryLocalService.getDDMStructureFileEntries(
				ArrayUtil.toLongArray(ddmStructureIds));

		indexerWriter.reindex(dlFileEntries);
	}

	@Reference
	protected DLFileEntryLocalService dlFileEntryLocalService;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference(
		target = "(indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry)"
	)
	protected IndexerWriter<DLFileEntry> indexerWriter;

	@Reference
	private IndexStatusManager _indexStatusManager;

}