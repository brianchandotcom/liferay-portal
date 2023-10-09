/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.search;

import com.liferay.bookmarks.model.BookmarksFolder;
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
public class BookmarksFolderModelSearchConfigurator
	implements ModelSearchConfigurator<BookmarksFolder> {

	@Override
	public String getClassName() {
		return BookmarksFolder.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.TITLE, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<BookmarksFolder>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.bookmarks.model.BookmarksFolder)"
	)
	private ModelIndexerWriterContributor<BookmarksFolder>
		_modelIndexWriterContributor;

	@Reference(
		target = "(indexer.class.name=com.liferay.bookmarks.model.BookmarksFolder)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

}