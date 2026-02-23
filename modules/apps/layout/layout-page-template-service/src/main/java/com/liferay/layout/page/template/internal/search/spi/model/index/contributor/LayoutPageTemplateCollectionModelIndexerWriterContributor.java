/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.search.spi.model.index.contributor;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Roselaine Marques
 */
public class LayoutPageTemplateCollectionModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<LayoutPageTemplateCollection> {

	public LayoutPageTemplateCollectionModelIndexerWriterContributor(
		LayoutPageTemplateCollectionLocalService
			layoutPageTemplateCollectionLocalService) {

		_layoutPageTemplateCollectionLocalService =
			layoutPageTemplateCollectionLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _layoutPageTemplateCollectionLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

}