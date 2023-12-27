/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.internal.search;

import com.liferay.commerce.term.internal.search.spi.model.index.contributor.CommerceTermEntryModelIndexerWriterContributor;
import com.liferay.commerce.term.internal.search.spi.model.result.contributor.CommerceTermEntryModelSummaryContributor;
import com.liferay.commerce.term.internal.search.spi.model.result.contributor.CommerceTermEntryModelVisibilityContributor;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
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
public class CommerceTermEntryModelSearchConfigurator
	implements ModelSearchConfigurator<CommerceTermEntry> {

	@Override
	public String getClassName() {
		return CommerceTermEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<CommerceTermEntry>
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
			new CommerceTermEntryModelIndexerWriterContributor(
				_commerceTermEntryLocalService,
				_dynamicQueryBatchIndexingActionableFactory);

		_modelSummaryContributor =
			new CommerceTermEntryModelSummaryContributor();
		_modelVisibilityContributor =
			new CommerceTermEntryModelVisibilityContributor(
				_commerceTermEntryLocalService);
	}

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CommerceTermEntry>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;
	private ModelVisibilityContributor _modelVisibilityContributor;

}