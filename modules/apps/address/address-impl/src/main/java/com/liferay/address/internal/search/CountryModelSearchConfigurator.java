/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.search;

import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Balazs Breier
 */
@Component(service = ModelSearchConfigurator.class)
public class CountryModelSearchConfigurator
	implements ModelSearchConfigurator<Country> {

	@Override
	public String getClassName() {
		return Country.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.CREATE_DATE, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.MODIFIED_DATE, Field.NAME, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<Country>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new ModelIndexerWriterContributor<>(
			_countryLocalService::getIndexableActionableDynamicQuery);
	}

	@Reference
	private CountryLocalService _countryLocalService;

	private ModelIndexerWriterContributor<Country> _modelIndexWriterContributor;

}