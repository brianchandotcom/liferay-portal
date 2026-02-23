/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

/**
 * @author Alejandro Tardín
 */
public class RedirectNotFoundEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<RedirectNotFoundEntry> {

	public RedirectNotFoundEntryModelIndexerWriterContributor(
		RedirectNotFoundEntryLocalService redirectNotFoundEntryLocalService) {

		_redirectNotFoundEntryLocalService = redirectNotFoundEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _redirectNotFoundEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		return IndexerWriterMode.UPDATE;
	}

	private final RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}