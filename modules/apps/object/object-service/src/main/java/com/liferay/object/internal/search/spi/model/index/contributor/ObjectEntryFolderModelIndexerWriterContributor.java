/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Mikel Lorza
 */
public class ObjectEntryFolderModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectEntryFolder> {

	public ObjectEntryFolderModelIndexerWriterContributor(
		ObjectEntryFolderLocalService objectEntryFolderLocalService) {

		_objectEntryFolderLocalService = objectEntryFolderLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectEntryFolderLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}