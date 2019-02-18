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

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.document.library.dto.v1_0.AdaptedImages;
import com.liferay.headless.document.library.dto.v1_0.Categories;
import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.internal.dto.v1_0.AdaptedImagesImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.CategoriesImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.DocumentImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

		return _toDocument(
			fileEntry, fileEntry.getFileVersion(),
			_userService.getUserById(fileEntry.getUserId()));
	}

	private AdaptedImages[] _getAdaptiveMedias(FileEntry fileEntry)
		throws Exception {

		if (!_amImageMimeTypeProvider.isMimeTypeSupported(
				fileEntry.getMimeType())) {

			return new AdaptedImages[0];
		}

		Stream<AdaptiveMedia<AMImageProcessor>> stream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).withConfigurationStatus(
					AMImageQueryBuilder.ConfigurationStatus.ANY
				).done());

		return stream.map(
			this::_toAdaptedImages
		).toArray(
			AdaptedImages[]::new
		);
	}

	private String[] _getAssetTagNames(FileEntry fileEntry) {
		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		return ListUtil.toArray(assetTags, AssetTag.NAME_ACCESSOR);
	}

	private Categories[] _getCategories(FileEntry fileEntry) {
		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		Stream<AssetCategory> stream = assetCategories.stream();

		return stream.map(
			assetCategory -> new CategoriesImpl() {
				{
					setCategoryId(assetCategory.getCategoryId());
					setCategoryName(assetCategory.getName());
				}
			}
		).toArray(
			Categories[]::new
		);
	}

	private <T, S> T _getValue(
		AdaptiveMedia<S> adaptiveMedia, AMAttribute<S, T> amAttribute) {

		Optional<T> optional = adaptiveMedia.getValueOptional(amAttribute);

		return optional.orElse(null);
	}

	private AdaptedImages _toAdaptedImages(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia) {

		return new AdaptedImagesImpl() {
			{
				setContentUrl(String.valueOf(adaptiveMedia.getURI()));
				setHeight(
					_getValue(
						adaptiveMedia,
						AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
				setResolutionName(
					_getValue(
						adaptiveMedia,
						AMAttribute.getConfigurationUuidAMAttribute()));
				setSizeInBytes(
					_getValue(
						adaptiveMedia,
						AMAttribute.getContentLengthAMAttribute()));
				setWidth(
					_getValue(
						adaptiveMedia,
						AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
			}
		};
	}

	private Document _toDocument(
			FileEntry fileEntry, FileVersion fileVersion, User user)
		throws Exception {

		return new DocumentImpl() {
			{
				setAdaptedImages(_getAdaptiveMedias(fileEntry));
				setCategories(_getCategories(fileEntry));
				setContentUrl(
					_dlURLHelper.getPreviewURL(
						fileEntry, fileVersion, null, ""));
				setCreator(CreatorUtil.toCreator(user));
				setDateCreated(fileEntry.getCreateDate());
				setDateModified(fileEntry.getModifiedDate());
				setDescription(fileEntry.getDescription());
				setEncodingFormat(fileEntry.getMimeType());
				setFileExtension(fileEntry.getExtension());
				setFolderId(fileEntry.getFolderId());
				setId(fileEntry.getFileEntryId());
				setKeywords(_getAssetTagNames(fileEntry));
				setSizeInBytes(fileEntry.getSize());
				setTitle(fileEntry.getTitle());
			}
		};
	}

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private UserService _userService;

}