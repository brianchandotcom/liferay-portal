/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelSearchConfigurator.class)
public class CommerceOrderItemModelSearchConfigurator
	implements ModelSearchConfigurator<CommerceOrderItem> {

	@Override
	public String getClassName() {
		return CommerceOrderItem.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CommerceOrderItem>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return null;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new ModelIndexerWriterContributor<>(
			IndexerWriterMode.UPDATE,
			_commerceOrderItemLocalService::getIndexableActionableDynamicQuery);
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	private ModelIndexerWriterContributor<CommerceOrderItem>
		_modelIndexWriterContributor;

}