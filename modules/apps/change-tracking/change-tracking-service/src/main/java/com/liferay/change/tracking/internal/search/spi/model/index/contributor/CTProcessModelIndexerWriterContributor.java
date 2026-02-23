/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Pei-Jung Lan
 */
public class CTProcessModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CTProcess> {

	public CTProcessModelIndexerWriterContributor(
		CTProcessLocalService ctProcessLocalService) {

		_ctProcessLocalService = ctProcessLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_ctProcessLocalService.getIndexableActionableDynamicQuery();

		if (!CTCollectionThreadLocal.isProductionMode()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Restricting indexable results of ",
						CTProcess.class.getName(), " because this can only be ",
						"performed in production mode"));
			}

			indexableActionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> dynamicQuery.add(
					RestrictionsFactoryUtil.eq("ctCollectionId", -1L)));
		}

		return indexableActionableDynamicQuery;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTProcessModelIndexerWriterContributor.class);

	private final CTProcessLocalService _ctProcessLocalService;

}