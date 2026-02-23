/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;

/**
 * @author Adolfo Pérez
 */
public class TranslationEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<TranslationEntry> {

	public TranslationEntryModelIndexerWriterContributor(
		TranslationEntryLocalService translationEntryLocalService) {

		_translationEntryLocalService = translationEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _translationEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final TranslationEntryLocalService _translationEntryLocalService;

}