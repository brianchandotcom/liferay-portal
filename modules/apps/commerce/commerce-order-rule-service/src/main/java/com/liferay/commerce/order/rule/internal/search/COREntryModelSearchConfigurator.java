/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.internal.search;

import com.liferay.commerce.order.rule.internal.search.spi.model.index.contributor.COREntryModelIndexerWriterContributor;
import com.liferay.commerce.order.rule.internal.search.spi.model.result.contributor.COREntryModelSummaryContributor;
import com.liferay.commerce.order.rule.internal.search.spi.model.result.contributor.COREntryModelVisibilityContributor;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
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
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelSearchConfigurator.class)
public class COREntryModelSearchConfigurator
	implements ModelSearchConfigurator<COREntry> {

	@Override
	public String getClassName() {
		return COREntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<COREntry>
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
			new COREntryModelIndexerWriterContributor(
				_corEntryLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
		_modelVisibilityContributor = new COREntryModelVisibilityContributor(
			_corEntryLocalService);
	}

	@Reference
	private COREntryLocalService _corEntryLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<COREntry>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new COREntryModelSummaryContributor();
	private ModelVisibilityContributor _modelVisibilityContributor;

}