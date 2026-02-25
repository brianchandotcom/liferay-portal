/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelSearchConfigurator.class)
public class CommerceShipmentModelSearchConfigurator
	implements ModelSearchConfigurator<CommerceShipment> {

	@Override
	public String getClassName() {
		return CommerceShipment.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CommerceShipment>
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
			IndexerWriterMode.UPDATE,
			_commerceShipmentLocalService::getIndexableActionableDynamicQuery);
	}

	@Reference
	private CommerceShipmentLocalService _commerceShipmentLocalService;

	private ModelIndexerWriterContributor<CommerceShipment>
		_modelIndexWriterContributor;

}