/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectView;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectViewModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectView> {

	public ObjectViewModelIndexerWriterContributor(
		ObjectViewLocalService objectViewLocalService) {

		_objectViewLocalService = objectViewLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectViewLocalService.getIndexableActionableDynamicQuery();
	}

	private final ObjectViewLocalService _objectViewLocalService;

}