/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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