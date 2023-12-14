/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.internal.search;

import com.liferay.data.engine.internal.search.spi.model.index.contributor.DEDataListViewModelIndexerWriterContributor;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(service = ModelSearchConfigurator.class)
public class DEDataListViewModelSearchConfigurator
	implements ModelSearchConfigurator<DEDataListView> {

	@Override
	public String getClassName() {
		return DEDataListView.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.NAME};
	}

	@Override
	public ModelIndexerWriterContributor<DEDataListView>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new DEDataListViewModelIndexerWriterContributor(
				_deDataListViewLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<DEDataListView>
		_modelIndexWriterContributor;

}