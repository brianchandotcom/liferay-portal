/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

/**
 * @author Inácio Nery
 */
public class KaleoInstanceModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<KaleoInstance> {

	public KaleoInstanceModelIndexerWriterContributor(
		KaleoInstanceLocalService kaleoInstanceLocalService) {

		_kaleoInstanceLocalService = kaleoInstanceLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoInstanceLocalService.getIndexableActionableDynamicQuery();
	}

	private final KaleoInstanceLocalService _kaleoInstanceLocalService;

}