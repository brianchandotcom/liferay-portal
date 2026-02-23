/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

/**
 * @author Petteri Karttunen
 */
public class SXPElementModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<SXPElement> {

	public SXPElementModelIndexerWriterContributor(
		SXPElementLocalService sxpElementLocalService) {

		_sxpElementLocalService = sxpElementLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _sxpElementLocalService.getIndexableActionableDynamicQuery();
	}

	private final SXPElementLocalService _sxpElementLocalService;

}