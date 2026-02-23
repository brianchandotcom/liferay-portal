/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.internal.search.spi.model.index.contributor;

import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataListViewModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DEDataListView> {

	public DEDataListViewModelIndexerWriterContributor(
		DEDataListViewLocalService deDataListViewLocalService) {

		_deDataListViewLocalService = deDataListViewLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _deDataListViewLocalService.getIndexableActionableDynamicQuery();
	}

	private final DEDataListViewLocalService _deDataListViewLocalService;

}