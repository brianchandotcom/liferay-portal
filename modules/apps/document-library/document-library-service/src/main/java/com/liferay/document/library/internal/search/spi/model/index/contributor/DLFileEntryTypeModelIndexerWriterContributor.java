/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Alejandro Tardín
 */
public class DLFileEntryTypeModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DLFileEntryType> {

	public DLFileEntryTypeModelIndexerWriterContributor(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dlFileEntryTypeLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		DLFileEntryType dlFileEntryType) {

		return IndexerWriterMode.UPDATE;
	}

	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}