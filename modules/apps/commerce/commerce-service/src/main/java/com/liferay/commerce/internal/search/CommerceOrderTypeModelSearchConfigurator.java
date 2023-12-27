/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search;

import com.liferay.commerce.internal.search.spi.model.index.contributor.CommerceOrderTypeModelIndexerWriterContributor;
import com.liferay.commerce.internal.search.spi.model.result.contributor.CommerceOrderTypeModelSummaryContributor;
import com.liferay.commerce.internal.search.spi.model.result.contributor.CommerceOrderTypeModelVisibilityContributor;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
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
public class CommerceOrderTypeModelSearchConfigurator
	implements ModelSearchConfigurator<CommerceOrderType> {

	@Override
	public String getClassName() {
		return CommerceOrderType.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<CommerceOrderType>
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
			new CommerceOrderTypeModelIndexerWriterContributor(
				_commerceOrderTypeLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
		_modelSummaryContributor =
			new CommerceOrderTypeModelSummaryContributor();
		_modelVisibilityContributor =
			new CommerceOrderTypeModelVisibilityContributor(
				_commerceOrderTypeLocalService);
	}

	@Reference
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CommerceOrderType>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;
	private ModelVisibilityContributor _modelVisibilityContributor;

}