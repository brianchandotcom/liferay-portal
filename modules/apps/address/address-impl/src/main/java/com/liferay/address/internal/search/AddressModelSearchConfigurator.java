/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.search;

import com.liferay.address.internal.search.spi.model.index.contributor.AddressModelIndexerWriterContributor;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = ModelSearchConfigurator.class)
public class AddressModelSearchConfigurator
	implements ModelSearchConfigurator<Address> {

	@Override
	public String getClassName() {
		return Address.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.MODIFIED_DATE, Field.NAME, Field.TYPE, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<Address>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new AddressModelIndexerWriterContributor(
			_addressLocalService, _dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<Address> _modelIndexWriterContributor;

}