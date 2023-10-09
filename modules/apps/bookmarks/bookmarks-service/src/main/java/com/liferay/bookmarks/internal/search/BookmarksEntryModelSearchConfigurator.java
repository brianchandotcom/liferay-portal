/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.search;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(service = ModelSearchConfigurator.class)
public class BookmarksEntryModelSearchConfigurator
	implements ModelSearchConfigurator<BookmarksEntry> {

	@Override
	public String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID, Field.URL
		};
	}

	@Override
	public ModelIndexerWriterContributor<BookmarksEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.bookmarks.model.BookmarksEntry)"
	)
	private ModelIndexerWriterContributor<BookmarksEntry>
		_modelIndexWriterContributor;

	@Reference(
		target = "(indexer.class.name=com.liferay.bookmarks.model.BookmarksEntry)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

}