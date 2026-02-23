/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Murilo Stodolni
 */
public class ObjectFolderModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectFolder> {

	public ObjectFolderModelIndexerWriterContributor(
		ObjectFolderLocalService objectFolderLocalService) {

		_objectFolderLocalService = objectFolderLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectFolderLocalService.getIndexableActionableDynamicQuery();
	}

	private final ObjectFolderLocalService _objectFolderLocalService;

}