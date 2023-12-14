/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor.KaleoTaskInstanceTokenModelIndexerWriterContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = ModelSearchConfigurator.class)
public class KaleoTaskInstanceTokenModelSearchConfigurator
	implements ModelSearchConfigurator<KaleoTaskInstanceToken> {

	@Override
	public String getClassName() {
		return KaleoTaskInstanceToken.class.getName();
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
		return new String[] {
			KaleoTaskInstanceTokenField.ASSET_DESCRIPTION,
			KaleoTaskInstanceTokenField.ASSET_TITLE
		};
	}

	@Override
	public ModelIndexerWriterContributor<KaleoTaskInstanceToken>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public boolean isPermissionAware() {
		return false;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new KaleoTaskInstanceTokenModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_kaleoTaskInstanceTokenLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	private ModelIndexerWriterContributor<KaleoTaskInstanceToken>
		_modelIndexWriterContributor;

}