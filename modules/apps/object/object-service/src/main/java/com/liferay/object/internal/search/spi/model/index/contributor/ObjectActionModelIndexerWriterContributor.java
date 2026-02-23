/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectAction;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Marco Leo
 */
public class ObjectActionModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectAction> {

	public ObjectActionModelIndexerWriterContributor(
		ObjectActionLocalService objectActionLocalService) {

		_objectActionLocalService = objectActionLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectActionLocalService.getIndexableActionableDynamicQuery();
	}

	private final ObjectActionLocalService _objectActionLocalService;

}