/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor.KaleoLogModelIndexerWriterContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = ModelSearchConfigurator.class)
public class KaleoLogModelSearchConfigurator
	implements ModelSearchConfigurator<KaleoLog> {

	@Override
	public String getClassName() {
		return KaleoLog.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID, "kaleoLogId"
		};
	}

	@Override
	public ModelIndexerWriterContributor<KaleoLog>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new KaleoLogModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_kaleoLogLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

	private ModelIndexerWriterContributor<KaleoLog>
		_modelIndexWriterContributor;

}