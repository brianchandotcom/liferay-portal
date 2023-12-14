/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search;

import com.liferay.change.tracking.internal.search.spi.model.index.contributor.CTCollectionModelIndexerWriterContributor;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ModelSearchConfigurator.class)
public class CTCollectionModelSearchConfigurator
	implements ModelSearchConfigurator<CTCollection> {

	@Override
	public String getClassName() {
		return CTCollection.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<CTCollection>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new CTCollectionModelIndexerWriterContributor(
				_ctCollectionLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CTCollection>
		_modelIndexWriterContributor;

}