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

package com.liferay.headless.document.library.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/document.properties",
	scope = ServiceScope.PROTOTYPE, service = DocumentResource.class
)
public class DocumentResourceImpl extends BaseDocumentResourceImpl {

	@Override
	public Document getDocument(Long documentId) throws Exception {
		FileEntry fileEntry = _dlAppService.getFileEntry(documentId);

		return _toDocument(fileEntry, fileEntry.getFileVersion());
	}

	private String[] _getAssetTagNames(FileEntry fileEntry) {
		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		return ListUtil.toArray(assetTags, AssetTag.NAME_ACCESSOR);
	}

	private Long[] _getCategoryIds(FileEntry fileEntry) {
		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		return ListUtil.toArray(
			assetCategories, AssetCategory.CATEGORY_ID_ACCESSOR);
	}

	private Document _toDocument(FileEntry fileEntry, FileVersion fileVersion) {
		String previewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileVersion, null, "");

		Long[] categoryIds = _getCategoryIds(fileEntry);

		String[] keywords = _getAssetTagNames(fileEntry);

		return new Document() {
			{
				setCategory(categoryIds);
				setContentUrl(previewURL);
				setDateCreated(fileEntry.getCreateDate());
				setDateModified(fileEntry.getModifiedDate());
				setDescription(fileEntry.getDescription());
				setEncodingFormat(fileEntry.getMimeType());
				setFileExtension(fileEntry.getExtension());
				setFolderId(fileEntry.getFolderId());
				setId(fileEntry.getFileEntryId());
				setKeywords(keywords);
				setSizeInBytes(fileEntry.getSize());
				setTitle(fileEntry.getTitle());
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

}