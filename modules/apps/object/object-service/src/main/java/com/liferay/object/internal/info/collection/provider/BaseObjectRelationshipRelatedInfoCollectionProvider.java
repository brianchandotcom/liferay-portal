/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.info.collection.provider;

import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
		Language language, ObjectDefinition objectDefinition1,
		ObjectDefinition objectDefinition2,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectRelationship objectRelationship) {

		_language = language;
		this.objectEntryLocalService = objectEntryLocalService;
		this.objectRelationship = objectRelationship;
		_objectDefinition1 = objectDefinition1;
		_objectDefinition2 = objectDefinition2;
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
		return _objectDefinition2.getClassName();
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			RelatedInfoItemCollectionProvider.super.getKey(), "_",
			_objectDefinition1.getCompanyId(), "_",
			_objectDefinition1.getName(), "_", objectRelationship.getName());
	}

	@Override
	public String getLabel(Locale locale) {
		if (!objectRelationship.isSelf()) {
			return objectRelationship.getLabel(locale);
		}

		return StringBundler.concat(
			objectRelationship.getLabel(locale), StringPool.SPACE,
			StringPool.OPEN_PARENTHESIS,
			_language.get(
				locale, objectRelationship.isReverse() ? "child" : "parent"),
			StringPool.CLOSE_PARENTHESIS);
	}

	@Override
	public String getSourceItemClassName() {
		return _objectDefinition1.getClassName();
	}

	@Override
	public boolean isAvailable() {
		if (_objectDefinition1.getCompanyId() !=
				CompanyThreadLocal.getCompanyId()) {

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
	private final ObjectDefinition _objectDefinition1;
	private final ObjectDefinition _objectDefinition2;

}