/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.item.selector.web.internal.layout.list.retriever;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.list.retriever.ClassedModelListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ScopeUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ListObjectReferenceFactory.class)
public class AssetEntryListListObjectReferenceFactory
	implements ListObjectReferenceFactory<InfoListItemSelectorReturnType> {

	@Override
	public ListObjectReference getListObjectReference(
		long companyId, long groupId, JSONObject jsonObject) {

		String classPK = jsonObject.getString("classPK");
		String itemType = jsonObject.getString("itemType");

		if (Validator.isNotNull(classPK)) {
			jsonObject.put(
				"itemType",
				() -> {
					AssetListEntry assetListEntry =
						_assetListEntryLocalService.fetchAssetListEntry(
							GetterUtil.getLong(classPK));

					if (assetListEntry == null) {
						return itemType;
					}

					return assetListEntry.getAssetEntryType();
				});
		}
		else {
			Long itemGroupId = ScopeUtil.getItemGroupId(
				companyId, jsonObject.getString("scopeExternalReferenceCode"),
				groupId);

			if (itemGroupId == null) {
				return new ClassedModelListObjectReference(jsonObject);
			}

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.
					fetchAssetListEntryByExternalReferenceCode(
						jsonObject.getString("externalReferenceCode"),
						itemGroupId);

			if (assetListEntry != null) {
				jsonObject.put(
					"className", AssetListEntry.class
				).put(
					"classPK", assetListEntry.getAssetListEntryId()
				).put(
					"itemType", assetListEntry.getAssetEntryType()
				).put(
					"title", assetListEntry.getTitle()
				);
			}
		}

		return new ClassedModelListObjectReference(jsonObject);
	}

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

}