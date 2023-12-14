/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor.KaleoInstanceModelIndexerWriterContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = ModelSearchConfigurator.class)
public class KaleoInstanceModelSearchConfigurator
	implements ModelSearchConfigurator<KaleoInstance> {

	@Override
	public String getClassName() {
		return KaleoInstance.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID, "kaleoInstanceId"
		};
	}

	@Override
	public ModelIndexerWriterContributor<KaleoInstance>
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
			new KaleoInstanceModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_kaleoInstanceLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	private ModelIndexerWriterContributor<KaleoInstance>
		_modelIndexWriterContributor;

}