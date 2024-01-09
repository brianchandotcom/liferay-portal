/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search;

import com.liferay.dynamic.data.mapping.internal.search.spi.model.index.contributor.DDMFormInstanceModelIndexerWriterContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = ModelSearchConfigurator.class)
public class DDMFormInstanceModelSearchConfigurator
	implements ModelSearchConfigurator<DDMFormInstance> {

	@Override
	public String getClassName() {
		return DDMFormInstance.class.getName();
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
	public ModelIndexerWriterContributor<DDMFormInstance>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new DDMFormInstanceModelIndexerWriterContributor(
				_ddmFormInstanceLocalService,
				new DDMFormInstanceRecordBatchReindexer(
					_formInstanceRecordLocalService, _indexerDocumentBuilder,
					_indexerWriter),
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private DDMFormInstanceRecordLocalService _formInstanceRecordLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord)"
	)
	private IndexerDocumentBuilder _indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord)"
	)
	private IndexerWriter<DDMFormInstanceRecord> _indexerWriter;

	private ModelIndexerWriterContributor<DDMFormInstance>
		_modelIndexWriterContributor;

}