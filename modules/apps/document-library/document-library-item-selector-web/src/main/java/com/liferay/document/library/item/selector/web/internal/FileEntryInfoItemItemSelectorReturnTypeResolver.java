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

package com.liferay.document.library.item.selector.web.internal;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class FileEntryInfoItemItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<InfoItemItemSelectorReturnType, FileEntry> {

	@Override
	public Class<InfoItemItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return InfoItemItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay) {
		ClassType classType = _getClassType(
			fileEntry, themeDisplay.getLocale());

		JSONObject fileEntryJSONObject = JSONUtil.put(
			"className", FileEntry.class.getName()
		).put(
			"classNameId",
			String.valueOf(_portal.getClassNameId(FileEntry.class.getName()))
		).put(
			"classPK", String.valueOf(fileEntry.getFileEntryId())
		).put(
			"classTypeId", (classType != null) ? classType.getClassTypeId() : 0
		).put(
			"subtype",
			(classType != null) ? classType.getName() : StringPool.BLANK
		).put(
			"title", fileEntry.getTitle()
		).put(
			"type",
			ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(), DLFileEntry.class.getName())
		);

		return fileEntryJSONObject.toString();
	}

	private ClassType _getClassType(FileEntry fileEntry, Locale locale) {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					_portal.getClassNameId(DLFileEntry.class));

		if (assetRendererFactory == null) {
			return null;
		}

		try {
			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					fileEntry.getFileEntryId());

			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				DLFileEntryConstants.getClassName(),
				assetRenderer.getClassPK());

			ClassTypeReader classTypeReader =
				assetRendererFactory.getClassTypeReader();

			return classTypeReader.getClassType(
				assetEntry.getClassTypeId(), locale);
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Reference
	private Portal _portal;

}