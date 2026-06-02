/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.search;

import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Balazs Breier
 */
@Component(service = ModelSearchConfigurator.class)
public class RegionModelSearchConfigurator
	implements ModelSearchConfigurator<Region> {

	@Override
	public String getClassName() {
		return Region.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.CREATE_DATE, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.MODIFIED_DATE, Field.NAME, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<Region>
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
			_regionLocalService::getIndexableActionableDynamicQuery);
	}

	private ModelIndexerWriterContributor<Region> _modelIndexWriterContributor;

	@Reference
	private RegionLocalService _regionLocalService;

}