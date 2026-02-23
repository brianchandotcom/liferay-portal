/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

/**
 * @author Petteri Karttunen
 */
public class SXPBlueprintModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<SXPBlueprint> {

	public SXPBlueprintModelIndexerWriterContributor(
		SXPBlueprintLocalService sxpBlueprintLocalService) {

		_sxpBlueprintLocalService = sxpBlueprintLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _sxpBlueprintLocalService.getIndexableActionableDynamicQuery();
	}

	private final SXPBlueprintLocalService _sxpBlueprintLocalService;

}