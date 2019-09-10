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

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = "service.ranking:Integer=100",
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

		JSONObject fileEntryJSONObject = JSONUtil.put(
			"fileEntryId", fileEntry.getFileEntryId()
		).put(
			"groupId", fileEntry.getGroupId()
		).put(
			"title", fileEntry.getTitle()
		).put(
			"type", "document"
		);

		fileEntryJSONObject.put(
			"url",
			_dlURLHelper.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, true, false)
		).put(
			"uuid", fileEntry.getUuid()
		);

		return fileEntryJSONObject.toString();
	}

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private PortletFileRepository _portletFileRepository;

}