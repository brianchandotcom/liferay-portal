/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.internal.info.collection.provider;

import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.Collections;
import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class ManyToManyObjectRelationshipRelatedInfoCollectionProvider
	implements RelatedInfoItemCollectionProvider {

	public ManyToManyObjectRelationshipRelatedInfoCollectionProvider(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectEntryLocalService objectEntryLocalService)
		throws PortalException {

		_objectDefinition = objectDefinition;
		_objectRelationship = objectRelationship;
		_objectEntryLocalService = objectEntryLocalService;

		_relatedObjectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				_objectRelationship.getObjectDefinitionId2());
	}

	@Override
	public InfoPage<ObjectEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

		if (!(relatedItem instanceof ObjectEntry)) {
			return InfoPage.of(
				Collections.emptyList(), collectionQuery.getPagination(), 0);
		}

		ObjectEntry objectEntry = (ObjectEntry)relatedItem;

		Pagination pagination = collectionQuery.getPagination();

		try {
			return InfoPage.of(
				_objectEntryLocalService.getManyToManyObjectEntries(
					objectEntry.getGroupId(),
					_objectRelationship.getObjectRelationshipId(),
					objectEntry.getObjectEntryId(), true,
					_objectRelationship.isReverse(), pagination.getStart(),
					pagination.getEnd()),
				collectionQuery.getPagination(),
				_objectEntryLocalService.getManyToManyObjectEntriesCount(
					objectEntry.getGroupId(),
					_objectRelationship.getObjectRelationshipId(),
					objectEntry.getObjectEntryId(), true,
					_objectRelationship.isReverse()));
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public String getCollectionItemClassName() {
		return _relatedObjectDefinition.getClassName();
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			RelatedInfoItemCollectionProvider.super.getKey(), "_",
			_objectDefinition.getCompanyId(), "_", _objectDefinition.getName(),
			"_", _relatedObjectDefinition.getName(), "_",
			_objectRelationship.getType());
	}

	@Override
	public String getLabel(Locale locale) {
		return _relatedObjectDefinition.getPluralLabel(locale);
	}

	@Override
	public String getSourceItemClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public boolean isAvailable() {
		if (_objectDefinition.getCompanyId() !=
				CompanyThreadLocal.getCompanyId()) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ManyToManyObjectRelationshipRelatedInfoCollectionProvider.class);

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectRelationship _objectRelationship;
	private final ObjectDefinition _relatedObjectDefinition;

}