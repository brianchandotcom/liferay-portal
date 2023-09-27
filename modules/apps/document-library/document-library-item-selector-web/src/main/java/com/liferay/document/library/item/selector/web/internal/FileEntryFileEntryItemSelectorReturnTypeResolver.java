/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.item.selector.web.internal;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.video.renderer.DLVideoRenderer;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PropsValues;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class FileEntryFileEntryItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<FileEntryItemSelectorReturnType, FileEntry> {

	@Override
	public Class<FileEntryItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return FileEntryItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		String previewURL = null;

		if (fileEntry.getGroupId() == fileEntry.getRepositoryId()) {
			previewURL = _dlURLHelper.getImagePreviewURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, false, false);
		}
		else {
			previewURL = _portletFileRepository.getPortletFileEntryURL(
				themeDisplay, fileEntry, "&imagePreview=1", false);
		}

		return JSONUtil.put(
			"classNameId", _portal.getClassNameId(FileEntry.class)
		).put(
			"extension", fileEntry.getExtension()
		).put(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId())
		).put(
			"groupId", String.valueOf(fileEntry.getGroupId())
		).put(
			"html",
			() -> {
				if (ArrayUtil.contains(
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES,
						fileEntry.getMimeType()) ||
					Objects.equals(
						ContentTypes.
							APPLICATION_VND_LIFERAY_VIDEO_EXTERNAL_SHORTCUT_HTML,
						fileEntry.getMimeType())) {

					return _dlVideoRenderer.renderHTML(
						fileEntry.getFileVersion(), themeDisplay.getRequest());
				}

				return null;
			}
		).put(
			"size", fileEntry.getSize()
		).put(
			"title", fileEntry.getTitle()
		).put(
			"type", "document"
		).put(
			"url", previewURL
		).put(
			"uuid", fileEntry.getUuid()
		).toString();
	}

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private DLVideoRenderer _dlVideoRenderer;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

}