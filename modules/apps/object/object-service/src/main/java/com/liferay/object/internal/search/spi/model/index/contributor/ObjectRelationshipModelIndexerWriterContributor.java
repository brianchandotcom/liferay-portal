/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Gleice Lisbino
 */
public class ObjectRelationshipModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectRelationship> {

	public ObjectRelationshipModelIndexerWriterContributor(
		ObjectRelationshipLocalService
			objectRelationshipLocalServiceLocalService) {

		_objectRelationshipLocalServiceLocalService =
			objectRelationshipLocalServiceLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectRelationshipLocalServiceLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final ObjectRelationshipLocalService
		_objectRelationshipLocalServiceLocalService;

}