/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectLayout;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Carolina Barbosa
 */
public class ObjectLayoutModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectLayout> {

	public ObjectLayoutModelIndexerWriterContributor(
		ObjectLayoutLocalService objectLayoutLocalService) {

		_objectLayoutLocalService = objectLayoutLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectLayoutLocalService.getIndexableActionableDynamicQuery();
	}

	private final ObjectLayoutLocalService _objectLayoutLocalService;

}