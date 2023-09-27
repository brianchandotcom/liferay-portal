/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.item.selector.web.internal;

import com.liferay.document.library.video.renderer.DLVideoRenderer;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.VideoEmbeddableHTMLItemSelectorReturnType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class FileEntryVideoEmbeddableHTMLItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<VideoEmbeddableHTMLItemSelectorReturnType, FileEntry> {

	@Override
	public Class<VideoEmbeddableHTMLItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return VideoEmbeddableHTMLItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		return JSONUtil.put(
			"html",
			_dlVideoRenderer.renderHTML(
				fileEntry.getFileVersion(), themeDisplay.getRequest())
		).put(
			"title", fileEntry.getTitle()
		).toString();
	}

	@Reference
	private DLVideoRenderer _dlVideoRenderer;

}