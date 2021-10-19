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

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class FolderItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<FolderItemSelectorReturnType, Folder> {

	@Override
	public Class<FolderItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return FolderItemSelectorReturnType.class;
	}

	@Override
	public Class<Folder> getModelClass() {
		return Folder.class;
	}

	@Override
	public String getValue(Folder folder, ThemeDisplay themeDisplay)
		throws Exception {

		return JSONUtil.put(
			"folderId", String.valueOf(folder.getFolderId())
		).put(
			"groupId", String.valueOf(folder.getGroupId())
		).put(
			"name", folder.getName()
		).put(
			"repositoryId", String.valueOf(folder.getRepositoryId())
		).toString();
	}

}