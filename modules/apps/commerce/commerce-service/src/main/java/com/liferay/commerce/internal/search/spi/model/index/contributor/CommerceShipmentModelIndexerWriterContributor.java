/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.index.contributor;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommerceShipment> {

	public CommerceShipmentModelIndexerWriterContributor(
		CommerceShipmentLocalService commerceShipmentLocalService) {

		_commerceShipmentLocalService = commerceShipmentLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceShipmentLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommerceShipment commerceShipment) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommerceShipmentLocalService _commerceShipmentLocalService;

}