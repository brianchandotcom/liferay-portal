/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author David Truong
 */
public class CTCollectionModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CTCollection> {

	public CTCollectionModelIndexerWriterContributor(
		CTCollectionLocalService ctCollectionLocalService) {

		_ctCollectionLocalService = ctCollectionLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_ctCollectionLocalService.getIndexableActionableDynamicQuery();

		if (!CTCollectionThreadLocal.isProductionMode()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Restricting indexable results of ",
						CTCollection.class.getName(), " because this can only ",
						"be performed in production mode"));
			}

			indexableActionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> dynamicQuery.add(
					RestrictionsFactoryUtil.eq("ctCollectionId", -1L)));
		}

		return indexableActionableDynamicQuery;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTCollectionModelIndexerWriterContributor.class);

	private final CTCollectionLocalService _ctCollectionLocalService;

}