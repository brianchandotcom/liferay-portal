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

package com.liferay.layout.page.template.internal.exportimport.content.processor;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "content.processor.type=LayoutPageTemplateStructureRelReferences",
	service = ExportImportContentProcessor.class
)
public class DataValuesMappingExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String data, boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		if (!JSONUtil.isValid(data)) {
			return data;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(data);

		_replaceExportContentReferences(
			jsonObject, portletDataContext, stagedModel);

		return jsonObject.toString();
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String data)
		throws Exception {

		if (!JSONUtil.isValid(data)) {
			return data;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(data);

		_replaceImportContentReferences(jsonObject, portletDataContext);

		return jsonObject.toString();
	}

	@Override
	public void validateContentReferences(long groupId, String data) {
	}

	private void _replaceCollectionExportContentReferences(
		JSONObject itemJSONObject, PortletDataContext portletDataContext,
		StagedModel stagedModel) {

		if (!itemJSONObject.has("config")) {
			return;
		}

		JSONObject configJSONObject = itemJSONObject.getJSONObject("config");

		if (!configJSONObject.has("collection")) {
			return;
		}

		JSONObject collectionJSONObject = configJSONObject.getJSONObject(
			"collection");

		String type = collectionJSONObject.getString("type");

		if (!Objects.equals(
				type, InfoListItemSelectorReturnType.class.getName())) {

			return;
		}

		long classPK = collectionJSONObject.getLong("classPK");

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(classPK);

		if (assetListEntry != null) {
			try {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, assetListEntry, stagedModel,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
			}
			catch (PortletDataException portletDataException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portletDataException);
				}
			}
		}
	}

	private void _replaceCollectionImportContentReferences(
		JSONObject itemJSONObject, PortletDataContext portletDataContext) {

		if (!itemJSONObject.has("config")) {
			return;
		}

		JSONObject configJSONObject = itemJSONObject.getJSONObject("config");

		if (!configJSONObject.has("collection")) {
			return;
		}

		JSONObject collectionJSONObject = configJSONObject.getJSONObject(
			"collection");

		String type = collectionJSONObject.getString("type");

		if (!Objects.equals(
				type, InfoListItemSelectorReturnType.class.getName())) {

			return;
		}

		long classPK = collectionJSONObject.getLong("classPK");

		Map<Long, Long> assetListEntryNewPrimaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetListEntry.class.getName());

		long newClassPK = MapUtil.getLong(
			assetListEntryNewPrimaryKeys, classPK, classPK);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(newClassPK);

		if (assetListEntry != null) {
			collectionJSONObject.put(
				"classNameId",
				_portal.getClassNameId(assetListEntry.getAssetEntryType())
			).put(
				"classPK", String.valueOf(newClassPK)
			).put(
				"itemSubtype", assetListEntry.getAssetEntrySubtype()
			).put(
				"itemType", assetListEntry.getAssetEntryType()
			).put(
				"title", assetListEntry.getTitle()
			);
		}
	}

	private void _replaceExportContentReferences(
		JSONObject jsonObject, PortletDataContext portletDataContext,
		StagedModel stagedModel) {

		if (!jsonObject.has("items")) {
			return;
		}

		JSONObject itemsJSONObject = jsonObject.getJSONObject("items");

		if (itemsJSONObject == null) {
			return;
		}

		for (String key : itemsJSONObject.keySet()) {
			JSONObject itemJSONObject = itemsJSONObject.getJSONObject(key);

			if (Objects.equals(
					itemJSONObject.get("type"),
					LayoutDataItemTypeConstants.TYPE_COLLECTION)) {

				_replaceCollectionExportContentReferences(
					itemJSONObject, portletDataContext, stagedModel);
			}
		}
	}

	private void _replaceImportContentReferences(
		JSONObject jsonObject, PortletDataContext portletDataContext) {

		if (!jsonObject.has("items")) {
			return;
		}

		JSONObject itemsJSONObject = jsonObject.getJSONObject("items");

		if (itemsJSONObject == null) {
			return;
		}

		for (String key : itemsJSONObject.keySet()) {
			JSONObject itemJSONObject = itemsJSONObject.getJSONObject(key);

			if (Objects.equals(
					itemJSONObject.get("type"),
					LayoutDataItemTypeConstants.TYPE_COLLECTION)) {

				_replaceCollectionImportContentReferences(
					itemJSONObject, portletDataContext);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataValuesMappingExportImportContentProcessor.class);

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}