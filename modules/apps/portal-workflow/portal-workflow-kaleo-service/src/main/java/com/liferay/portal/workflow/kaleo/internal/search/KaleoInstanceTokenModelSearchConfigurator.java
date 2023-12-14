/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor.KaleoInstanceTokenModelIndexerWriterContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(service = ModelSearchConfigurator.class)
public class KaleoInstanceTokenModelSearchConfigurator
	implements ModelSearchConfigurator<KaleoInstanceToken> {

	@Override
	public String getClassName() {
		return KaleoInstanceToken.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID, KaleoInstanceTokenField.KALEO_INSTANCE_ID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {
			KaleoInstanceTokenField.ASSET_DESCRIPTION,
			KaleoInstanceTokenField.ASSET_TITLE
		};
	}

	@Override
	public ModelIndexerWriterContributor<KaleoInstanceToken>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new KaleoInstanceTokenModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_kaleoInstanceLocalService, _kaleoInstanceTokenLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Reference
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	private ModelIndexerWriterContributor<KaleoInstanceToken>
		_modelIndexWriterContributor;

}