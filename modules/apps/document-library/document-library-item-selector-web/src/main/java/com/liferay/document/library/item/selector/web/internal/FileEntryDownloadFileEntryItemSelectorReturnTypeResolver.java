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

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class FileEntryDownloadFileEntryItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<DownloadFileEntryItemSelectorReturnType, FileEntry> {

	@Override
	public Class<DownloadFileEntryItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return DownloadFileEntryItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return JSONUtil.put(
			"classNameId", _portal.getClassNameId(FileEntry.class)
		).put(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId())
		).put(
			"groupId", String.valueOf(fileEntry.getGroupId())
		).put(
			"title", fileEntry.getTitle()
		).put(
			"type", "document"
		).put(
			"url",
			_dlURLHelper.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, false, false)
		).put(
			"uuid", fileEntry.getUuid()
		).toString();
	}

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private Portal _portal;

}