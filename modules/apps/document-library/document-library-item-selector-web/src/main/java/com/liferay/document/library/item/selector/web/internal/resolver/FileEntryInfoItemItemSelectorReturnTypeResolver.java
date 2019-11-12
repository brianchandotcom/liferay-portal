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

package com.liferay.document.library.item.selector.web.internal.resolver;

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

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
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject fileEntryJSONObject = JSONUtil.put(
			"className", FileEntry.class.getName()
		).put(
			"classNameId", _portal.getClassNameId(FileEntry.class.getName())
		).put(
			"classPK", fileEntry.getFileEntryId()
		).put(
			"title", fileEntry.getTitle()
		);

		return fileEntryJSONObject.toString();
	}

	@Reference
	private Portal _portal;

}