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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.Collections;
import java.util.Locale;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseObjectRelationshipRelatedInfoCollectionProvider
	implements RelatedInfoItemCollectionProvider {

	public BaseObjectRelationshipRelatedInfoCollectionProvider(
			Language language, ObjectDefinition objectDefinition,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectEntryLocalService objectEntryLocalService,
			ObjectRelationship objectRelationship)
		throws PortalException {

		_language = language;

		this.objectEntryLocalService = objectEntryLocalService;
		this.objectRelationship = objectRelationship;

		_objectDefinition = objectDefinition;

		_relatedObjectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				this.objectRelationship.getObjectDefinitionId2());
	}

	@Override
	public InfoPage<ObjectEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

		if (!(relatedItem instanceof ObjectEntry)) {
			return InfoPage.of(
				Collections.emptyList(), collectionQuery.getPagination(), 0);
		}

		try {
			return getCollectionInfoPage(
				(ObjectEntry)relatedItem, collectionQuery.getPagination());
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
			"_", objectRelationship.getName());
	}

	@Override
	public String getLabel(Locale locale) {
		if (!objectRelationship.isSelf()) {
			return _relatedObjectDefinition.getLabel(locale);
		}

		return StringBundler.concat(
			_relatedObjectDefinition.getLabel(locale), StringPool.SPACE,
			StringPool.OPEN_PARENTHESIS,
			_language.get(
				locale, objectRelationship.isReverse() ? "child" : "parent"),
			StringPool.CLOSE_PARENTHESIS);
	}

	@Override
	public String getSourceItemClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public boolean isAvailable() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-176083") ||
			(_objectDefinition.getCompanyId() !=
				CompanyThreadLocal.getCompanyId())) {

			return false;
		}

		return true;
	}

	protected InfoPage<ObjectEntry> getCollectionInfoPage(
			ObjectEntry objectEntry, Pagination pagination)
		throws PortalException {

		return null;
	}

	protected final ObjectEntryLocalService objectEntryLocalService;
	protected final ObjectRelationship objectRelationship;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseObjectRelationshipRelatedInfoCollectionProvider.class);

	private final Language _language;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinition _relatedObjectDefinition;

}