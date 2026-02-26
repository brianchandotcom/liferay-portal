/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;

/**
 * @author Feliphe Marinho
 * @author Gabriel Albuquerque
 */
public class ObjectEntryBatchReindexer {

	public ObjectEntryBatchReindexer(
		ObjectEntryLocalService objectEntryLocalService,
		ObjectDefinition objectDefinition) {

		_objectEntryLocalService = objectEntryLocalService;
		_objectDefinition = objectDefinition;
	}

	public String getClassName() {
		return _objectDefinition.getClassName();
	}

	public void reindex(
		IndexerDocumentBuilder indexerDocumentBuilder, long accountEntryId,
		long companyId) {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_objectEntryLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName(
					"objectDefinitionId");

				dynamicQuery.add(
					property.eq(_objectDefinition.getObjectDefinitionId()));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(ObjectEntry objectEntry) ->
				indexableActionableDynamicQuery.addDocument(
					indexerDocumentBuilder.getDocument(objectEntry)));

		indexableActionableDynamicQuery.performActions();
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;

}