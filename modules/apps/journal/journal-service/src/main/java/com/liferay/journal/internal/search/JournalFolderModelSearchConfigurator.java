/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.search;

import com.liferay.journal.internal.search.spi.model.result.contributor.JournalFolderModelSummaryContributor;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = ModelSearchConfigurator.class)
public class JournalFolderModelSearchConfigurator
	implements ModelSearchConfigurator<JournalFolder> {

	@Override
	public String getClassName() {
		return JournalFolder.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.DESCRIPTION, Field.TITLE};
	}

	@Override
	public ModelIndexerWriterContributor<JournalFolder>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public boolean isSelectAllLocales() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new ModelIndexerWriterContributor<>(
			_journalFolderLocalService::getIndexableActionableDynamicQuery);
	}

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

	private ModelIndexerWriterContributor<JournalFolder>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new JournalFolderModelSummaryContributor();

}