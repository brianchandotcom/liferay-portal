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

package com.liferay.media.object.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.media.object.apio.architect.model.MediaObject;
import com.liferay.media.object.apio.internal.architect.form.MediaObjectCreatorForm;
import com.liferay.media.object.apio.internal.helper.MediaObjectHelper;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ListUtil;

import io.vavr.control.Try;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/MediaObject">MediaObject</a> resources through a web
 * API. The resources are mapped from the internal model {@code FileEntry}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true, service = NestedCollectionResource.class)
public class MediaObjectNestedCollectionResource
	implements NestedCollectionResource
		<FileEntry, Long, MediaObjectIdentifier, Long, RootFolderIdentifier> {

	@Override
	public NestedCollectionRoutes<FileEntry, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<FileEntry, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addFileEntry,
			_hasPermission.forAddingIn(RootFolderIdentifier.class),
			MediaObjectCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "document";
	}

	@Override
	public ItemRoutes<FileEntry, Long> itemRoutes(
		ItemRoutes.Builder<FileEntry, Long> builder) {

		return builder.addGetter(
			_dlAppService::getFileEntry
		).addRemover(
			idempotent(_dlAppService::deleteFileEntry),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<FileEntry> representor(
		Representor.Builder<FileEntry, Long> builder) {

		return builder.types(
			"Document"
		).identifier(
			FileEntry::getFileEntryId
		).addBidirectionalModel(
			"folder", "documents", FolderIdentifier.class,
			FileEntry::getFolderId
		).addRelativeURL(
			"contentUrl", this::_getFileEntryPreviewURL
		).addDate(
			"dateCreated", FileEntry::getCreateDate
		).addDate(
			"dateModified", FileEntry::getModifiedDate
		).addLinkedModel(
			"creator", PersonIdentifier.class, FileEntry::getUserId
		).addNestedList(
			"adaptedMedia", this::_getAdaptiveMedias,
			this::_getAdaptiveMediaNestedRepresentor
		).addNumber(
			"sizeInBytes", FileEntry::getSize
		).addRelatedCollection(
			"category", CategoryIdentifier.class
		).addString(
			"description", FileEntry::getDescription
		).addString(
			"encodingFormat", FileEntry::getMimeType
		).addString(
			"fileExtension", FileEntry::getExtension
		).addString(
			"title", FileEntry::getTitle
		).addStringList(
			"keywords", this::_getMediaObjectAssetTags
		).build();
	}

	private FileEntry _addFileEntry(long groupId, MediaObject mediaObject)
		throws PortalException {

		return _mediaObjectHelper.addFileEntry(groupId, 0L, mediaObject);
	}

	private NestedRepresentor<AdaptiveMedia<AMImageProcessor>>
		_getAdaptiveMediaNestedRepresentor(
			NestedRepresentor.Builder<AdaptiveMedia<AMImageProcessor>>
				builder) {

		return builder.types(
			"ImageObject", "MediaObject"
		).addNumber(
			"height",
			adaptiveMedia -> _getAdaptiveMediaValue(
				adaptiveMedia, AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT)
		).addNumber(
			"sizeInBytes",
			adaptiveMedia -> _getAdaptiveMediaValue(
				adaptiveMedia, AMAttribute.getContentLengthAMAttribute())
		).addNumber(
			"width",
			adaptiveMedia -> _getAdaptiveMediaValue(
				adaptiveMedia, AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH)
		).addRelativeURL(
			"contentUrl",
			adaptiveMedia -> String.valueOf(adaptiveMedia.getURI())
		).addString(
			"resolutionName",
			adaptiveMedia -> _getAdaptiveMediaValue(
				adaptiveMedia, AMAttribute.getConfigurationUuidAMAttribute())
		).build();
	}

	private List<AdaptiveMedia<AMImageProcessor>> _getAdaptiveMedias(
		FileEntry fileEntry) {

		return Try.of(
			fileEntry::getMimeType
		).filter(
			_amImageMimeTypeProvider::isMimeTypeSupported
		).mapTry(
			mimeType -> _amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).withConfigurationStatus(
					AMImageQueryBuilder.ConfigurationStatus.ANY
				).done())
		).getOrElse(
			Stream::empty
		).collect(
			Collectors.toList()
		);
	}

	private <V> V _getAdaptiveMediaValue(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia,
		AMAttribute<AMImageProcessor, V> amAttribute) {

		Optional<V> valueOptional = adaptiveMedia.getValueOptional(amAttribute);

		return valueOptional.orElse(null);
	}

	private String _getFileEntryPreviewURL(FileEntry fileEntry) {
		try {
			FileVersion fileVersion = fileEntry.getFileVersion();

			return DLUtil.getPreviewURL(
				fileEntry, fileVersion, null, "", false, false);
		}
		catch (PortalException pe) {
			if (_log.isInfoEnabled()) {
				_log.info(pe, pe);
			}

			return null;
		}
	}

	private List<String> _getMediaObjectAssetTags(FileEntry fileEntry) {
		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		return ListUtil.toList(assetTags, AssetTag::getName);
	}

	private PageItems<FileEntry> _getPageItems(
			Pagination pagination, long groupId)
		throws PortalException {

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			groupId, 0, pagination.getStartPosition(),
			pagination.getEndPosition(), null);
		int count = _dlAppService.getFileEntriesCount(groupId, 0);

		return new PageItems<>(fileEntries, count);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MediaObjectNestedCollectionResource.class);

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private MediaObjectHelper _mediaObjectHelper;

}