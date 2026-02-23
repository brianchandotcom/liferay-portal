/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSpecificationOptionModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPSpecificationOption> {

	public CPSpecificationOptionModelIndexerWriterContributor(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService) {

		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpSpecificationOptionLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CPSpecificationOption cpSpecificationOption) {

		return IndexerWriterMode.UPDATE;
	}

	private final CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

}