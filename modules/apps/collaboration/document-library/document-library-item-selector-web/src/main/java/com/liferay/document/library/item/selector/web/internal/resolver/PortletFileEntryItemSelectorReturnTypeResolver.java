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
import com.liferay.item.selector.criteria.PortletFileEntryItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true, property = {"service.ranking:Integer=100"},
	service = ItemSelectorReturnTypeResolver.class
)
public class PortletFileEntryItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<PortletFileEntryItemSelectorReturnType, FileEntry> {

	@Override
	public Class<PortletFileEntryItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return PortletFileEntryItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject fileEntryJSONObject = JSONFactoryUtil.createJSONObject();

		fileEntryJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
		fileEntryJSONObject.put("groupId", fileEntry.getGroupId());
		fileEntryJSONObject.put("title", fileEntry.getTitle());
		fileEntryJSONObject.put("type", "document");

		String previewURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			themeDisplay, fileEntry, StringPool.BLANK, false);

		fileEntryJSONObject.put("url", previewURL);

		fileEntryJSONObject.put("uuid", fileEntry.getUuid());

		return fileEntryJSONObject.toString();
	}

}