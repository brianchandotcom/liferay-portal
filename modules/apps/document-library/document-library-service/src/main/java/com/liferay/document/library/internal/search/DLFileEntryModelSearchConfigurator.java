/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search;

import com.liferay.document.library.internal.search.spi.model.index.contributor.DLFileEntryModelIndexerWriterContributor;
import com.liferay.document.library.internal.search.spi.model.result.contributor.DLFileEntryModelSummaryContributor;
import com.liferay.document.library.internal.search.spi.model.result.contributor.DLFileEntryModelVisibilityContributor;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = ModelSearchConfigurator.class)
public class DLFileEntryModelSearchConfigurator
	implements ModelSearchConfigurator<DLFileEntry> {

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.CONTENT,
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
			Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<DLFileEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public ModelVisibilityContributor getModelVisibilityContributor() {
		return _modelVisibilityContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new DLFileEntryModelIndexerWriterContributor(
				_dlFileEntryLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
		_modelVisibilityContributor = new DLFileEntryModelVisibilityContributor(
			_dlAppLocalService);
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<DLFileEntry>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new DLFileEntryModelSummaryContributor();
	private ModelVisibilityContributor _modelVisibilityContributor;

}