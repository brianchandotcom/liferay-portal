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

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true, property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class FileEntryURLItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<URLItemSelectorReturnType, FileEntry> {

	@Override
	public Class<URLItemSelectorReturnType> getItemSelectorReturnTypeClass() {
		return URLItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		if (fileEntry.getGroupId() == fileEntry.getRepositoryId()) {
			return DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, false, false);
		}

		return PortletFileRepositoryUtil.getPortletFileEntryURL(
			themeDisplay, fileEntry, StringPool.BLANK, false);
	}

}