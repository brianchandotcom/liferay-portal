/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

/**
 * @author Mikel Lorza
 */
public class SharingEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<SharingEntry> {

	public SharingEntryModelIndexerWriterContributor(
		SharingEntryLocalService sharingEntryLocalService) {

		_sharingEntryLocalService = sharingEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _sharingEntryLocalService.getIndexableActionableDynamicQuery();
	}

	private final SharingEntryLocalService _sharingEntryLocalService;

}