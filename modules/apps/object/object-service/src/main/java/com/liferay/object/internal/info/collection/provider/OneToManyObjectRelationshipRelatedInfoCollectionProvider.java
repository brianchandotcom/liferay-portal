/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.info.collection.provider;

import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;

/**
 * @author Jürgen Kappler
 */
public class OneToManyObjectRelationshipRelatedInfoCollectionProvider
	extends BaseObjectRelationshipRelatedInfoCollectionProvider {

	public OneToManyObjectRelationshipRelatedInfoCollectionProvider(
		Language language, ObjectDefinition objectDefinition1,
		ObjectDefinition objectDefinition2,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectRelationship objectRelationship) {

		super(
			language, objectDefinition1, objectDefinition2,
			objectEntryLocalService, objectRelationship);
	}

	@Override
	protected InfoPage<ObjectEntry> getCollectionInfoPage(
			ObjectEntry objectEntry, Pagination pagination)
		throws PortalException {

		return InfoPage.of(
			objectEntryLocalService.getOneToManyObjectEntries(
				objectEntry.getGroupId(),
				objectRelationship.getObjectRelationshipId(),
				objectEntry.getObjectEntryId(), true, null,
				pagination.getStart(), pagination.getEnd()),
			pagination,
			objectEntryLocalService.getOneToManyObjectEntriesCount(
				objectEntry.getGroupId(),
				objectRelationship.getObjectRelationshipId(),
				objectEntry.getObjectEntryId(), true, null));
	}

}