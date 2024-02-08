/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.search;

import com.liferay.dynamic.data.lists.internal.search.spi.model.index.contributor.DDLRecordSetModelIndexerWriterContributor;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
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
 * @author Marcela Cunha
 */
@Component(service = ModelSearchConfigurator.class)
public class DDLRecordSetModelSearchConfigurator
	implements ModelSearchConfigurator<DDLRecordSet> {

	@Override
	public String getClassName() {
		return DDLRecordSet.class.getName();
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
		return new String[] {Field.DESCRIPTION, Field.NAME};
	}

	@Override
	public ModelIndexerWriterContributor<DDLRecordSet>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new DDLRecordSetModelIndexerWriterContributor(
				new DDLRecordBatchReindexer(
					_indexerDocumentBuilder, _indexerWriter),
				_ddlRecordSetLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference(
		target = "(indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecord)"
	)
	private IndexerDocumentBuilder _indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecord)"
	)
	private IndexerWriter<DDLRecord> _indexerWriter;

	private ModelIndexerWriterContributor<DDLRecordSet>
		_modelIndexWriterContributor;

}