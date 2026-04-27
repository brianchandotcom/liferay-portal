/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search;

import com.liferay.commerce.internal.search.spi.model.result.contributor.CommerceOrderAttachmentModelSummaryContributor;
import com.liferay.commerce.model.CommerceOrderAttachment;
import com.liferay.commerce.service.CommerceOrderAttachmentLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = ModelSearchConfigurator.class)
public class CommerceOrderAttachmentModelSearchConfigurator
	implements ModelSearchConfigurator<CommerceOrderAttachment> {

	@Override
	public String getClassName() {
		return CommerceOrderAttachment.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CommerceOrderAttachment>
		getModelIndexerWriterContributor() {

		return _modelIndexerWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexerWriterContributor = new ModelIndexerWriterContributor<>(
			IndexerWriterMode.UPDATE,
			_commerceOrderAttachmentLocalService::
				getIndexableActionableDynamicQuery);
		_modelSummaryContributor =
			new CommerceOrderAttachmentModelSummaryContributor();
	}

	@Reference
	private CommerceOrderAttachmentLocalService
		_commerceOrderAttachmentLocalService;

	private ModelIndexerWriterContributor<CommerceOrderAttachment>
		_modelIndexerWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

}