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

package com.liferay.object.internal.uad.anonymizer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.internal.uad.constants.ObjectUADConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;
import com.liferay.user.associated.data.util.UADDynamicQueryUtil;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryUADAnonymizer
	extends DynamicQueryUADAnonymizer<ObjectEntry> {

	public ObjectEntryUADAnonymizer(
		AssetEntryLocalService assetEntryLocalService,
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
	}

	@Override
	public void autoAnonymize(
		ObjectEntry objectEntry, long userId, User anonymousUser) {

		if (objectEntry.getUserId() == userId) {
			objectEntry.setUserId(anonymousUser.getUserId());
			objectEntry.setUserName(anonymousUser.getFullName());
		}

		if (objectEntry.getStatusByUserId() == userId) {
			objectEntry.setStatusByUserId(anonymousUser.getUserId());
			objectEntry.setStatusByUserName(anonymousUser.getFullName());
		}

		_objectEntryLocalService.updateObjectEntry(objectEntry);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			_objectDefinition.getClassName(), objectEntry.getObjectEntryId());

		if ((assetEntry != null) && (assetEntry.getUserId() == userId)) {
			assetEntry.setUserId(anonymousUser.getUserId());
			assetEntry.setUserName(anonymousUser.getFullName());

			_assetEntryLocalService.updateAssetEntry(assetEntry);
		}
	}

	@Override
	public void delete(ObjectEntry objectEntry) throws PortalException {
		try {
			ObjectEntryThreadLocal.setDisassociateRelatedModels(true);

			_objectEntryLocalService.deleteObjectEntry(objectEntry);
		}
		finally {
			ObjectEntryThreadLocal.setDisassociateRelatedModels(false);
		}
	}

	@Override
	public Class<ObjectEntry> getTypeClass() {
		return ObjectEntry.class;
	}

	@Override
	public String getTypeKey() {
		return _objectDefinition.getClassName();
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			_objectEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"objectDefinitionId",
					_objectDefinition.getObjectDefinitionId())));

		return actionableDynamicQuery;
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return ObjectUADConstants.USER_ID_FIELD_NAMES_OBJECT_ENTRY;
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery(long userId) {
		ActionableDynamicQuery actionableDynamicQuery =
			doGetActionableDynamicQuery();

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				UADDynamicQueryUtil.addDynamicQueryCriteria(
					dynamicQuery, doGetUserIdFieldNames(), userId);
			});

		return actionableDynamicQuery;
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;

}