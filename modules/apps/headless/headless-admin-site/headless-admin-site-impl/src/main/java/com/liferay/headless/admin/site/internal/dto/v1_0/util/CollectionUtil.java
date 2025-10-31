/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.ClassNameReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionReference;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class CollectionUtil {

	public static CollectionReference getCollectionReference(
		long companyId, JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		String type = jsonObject.getString("type");

		if (Validator.isNull(type)) {
			return null;
		}

		if (Objects.equals(
				type, InfoListItemSelectorReturnType.class.getName())) {

			return _toCollectionItemExternalReference(
				AssetListEntryLocalServiceUtil.fetchAssetListEntry(
					jsonObject.getLong("classPK")),
				companyId, jsonObject, scopeGroupId);
		}

		String key = jsonObject.getString("key", null);

		if (Validator.isNull(key)) {
			return null;
		}

		ClassNameReference classNameReference = new ClassNameReference();

		classNameReference.setClassName(() -> key);

		return classNameReference;
	}

	private static Scope _getItemScope(
			Long companyId, String itemExternalReferenceCode, long scopeGroupId)
		throws PortalException {

		if (Validator.isNull(itemExternalReferenceCode) ||
			(companyId == null)) {

			return null;
		}

		Group group = GroupLocalServiceUtil.getGroupByExternalReferenceCode(
			itemExternalReferenceCode, companyId);

		if ((group == null) || (group.getGroupId() == scopeGroupId)) {
			return null;
		}

		return new Scope() {
			{
				setExternalReferenceCode(group::getExternalReferenceCode);
				setType(
					() -> {
						if (group.getType() == GroupConstants.TYPE_DEPOT) {
							return Type.ASSET_LIBRARY;
						}

						return Type.SITE;
					});
			}
		};
	}

	private static CollectionItemExternalReference
		_toCollectionItemExternalReference(
			AssetListEntry assetListEntry, long companyId,
			JSONObject jsonObject, long scopeGroupId) {

		CollectionItemExternalReference collectionItemExternalReference =
			new CollectionItemExternalReference();

		if (assetListEntry != null) {
			collectionItemExternalReference.setExternalReferenceCode(
				assetListEntry::getExternalReferenceCode);
			collectionItemExternalReference.setScope(
				() -> ItemScopeUtil.getItemScope(
					assetListEntry.getGroupId(), scopeGroupId));

			return collectionItemExternalReference;
		}

		String externalReferenceCode = jsonObject.getString(
			"externalReferenceCode");

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		collectionItemExternalReference.setExternalReferenceCode(
			() -> externalReferenceCode);
		collectionItemExternalReference.setScope(
			() -> _getItemScope(
				companyId, jsonObject.getString("scopeExternalReferenceCode"),
				scopeGroupId));

		return collectionItemExternalReference;
	}

}