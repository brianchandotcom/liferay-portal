/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search;

import com.liferay.change.tracking.internal.search.spi.model.index.contributor.CTRemoteModelIndexerWriterContributor;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.service.CTRemoteLocalService;
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
public class CTRemoteModelSearchConfigurator
	implements ModelSearchConfigurator<CTRemote> {

	@Override
	public String getClassName() {
		return CTRemote.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<CTRemote>
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
			new CTRemoteModelIndexerWriterContributor(
				_ctRemoteLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private CTRemoteLocalService _ctRemoteLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CTRemote>
		_modelIndexWriterContributor;

}