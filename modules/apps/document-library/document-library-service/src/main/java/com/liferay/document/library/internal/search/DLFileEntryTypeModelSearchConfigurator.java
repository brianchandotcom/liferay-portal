/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search;

import com.liferay.document.library.internal.search.spi.model.index.contributor.DLFileEntryTypeModelIndexerWriterContributor;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ModelSearchConfigurator.class)
public class DLFileEntryTypeModelSearchConfigurator
	implements ModelSearchConfigurator<DLFileEntryType> {

	@Override
	public String getClassName() {
		return DLFileEntryType.class.getName();
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
	public ModelIndexerWriterContributor<DLFileEntryType>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new DLFileEntryTypeModelIndexerWriterContributor(
				_dlFileEntryTypeLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<DLFileEntryType>
		_modelIndexWriterContributor;

}