/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.style.book.internal.search.spi.model.result.contributor.StyleBookEntryModelSummaryContributor;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(service = ModelSearchConfigurator.class)
public class StyleBookEntryModelSearchConfigurator
	implements ModelSearchConfigurator<StyleBookEntry> {

	@Override
	public String getClassName() {
		return StyleBookEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.NAME, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<StyleBookEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new ModelIndexerWriterContributor<>(
			_styleBookEntryLocalService::getIndexableActionableDynamicQuery);
	}

	private ModelIndexerWriterContributor<StyleBookEntry>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new StyleBookEntryModelSummaryContributor();

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}