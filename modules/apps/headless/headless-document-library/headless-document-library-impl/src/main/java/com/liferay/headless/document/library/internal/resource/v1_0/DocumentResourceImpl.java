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
import com.liferay.headless.document.library.internal.odata.entity.v1_0.DocumentEntityModel;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.ws.rs.core.MultivaluedMap;

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
public class DocumentResourceImpl
	extends BaseDocumentResourceImpl implements EntityModelResource {

	@Override
	public boolean deleteDocument(Long documentId) throws Exception {
		_dlAppService.deleteFileEntry(documentId);

		return true;
	}

	@Override
	public Page<Document> getContentSpaceDocumentsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getDocumentsPage(
			booleanQuery -> {
				if (contentSpaceId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.GROUP_ID, String.valueOf(contentSpaceId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, pagination, sorts);
	}

	@Override
	public Document getDocument(Long documentId) throws Exception {
		FileEntry fileEntry = _dlAppService.getFileEntry(documentId);

		return _toDocument(fileEntry);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _vocabularyEntityModel;
	}

	@Override
	public Page<Document> getFolderDocumentsPage(
			Long folderId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getDocumentsPage(
			booleanQuery -> {
				if (folderId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.FOLDER_ID, String.valueOf(folderId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, pagination, sorts);
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

	private Page<Document> _getDocumentsPage(
			Consumer<BooleanQuery> booleanQueryConsumer, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		List<FileEntry> fileEntries = new ArrayList<>();

		Hits hits = SearchUtil.getHits(
			filter, _indexerRegistry.nullSafeGetIndexer(DLFileEntry.class),
			pagination, booleanQueryConsumer,
			queryConfig -> {
				queryConfig.setSelectedFieldNames(Field.ENTRY_CLASS_PK);
			},
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			_searchResultPermissionFilterFactory, sorts);

		for (com.liferay.portal.kernel.search.Document document :
				hits.getDocs()) {

			long fileEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			fileEntries.add(fileEntry);
		}

		return Page.of(
			transform(fileEntries, this::_toDocument), pagination,
			fileEntries.size());
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
				contentUrl = String.valueOf(adaptiveMedia.getURI());
				height = _getValue(
					adaptiveMedia, AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);
				resolutionName = _getValue(
					adaptiveMedia,
					AMAttribute.getConfigurationUuidAMAttribute());
				sizeInBytes = _getValue(
					adaptiveMedia, AMAttribute.getContentLengthAMAttribute());
				width = _getValue(
					adaptiveMedia, AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);
			}
		};
	}

	private Document _toDocument(FileEntry fileEntry) throws Exception {
		return _toDocument(
			fileEntry, fileEntry.getFileVersion(),
			_userLocalService.getUserById(fileEntry.getUserId()));
	}

	private Document _toDocument(
			FileEntry fileEntry, FileVersion fileVersion, User user)
		throws Exception {

		return new DocumentImpl() {
			{
				adaptedImages = _getAdaptiveMedias(fileEntry);
				categories = _getCategories(fileEntry);
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileVersion, null, "");
				creator = CreatorUtil.toCreator(_portal, user);
				dateCreated = fileEntry.getCreateDate();
				dateModified = fileEntry.getModifiedDate();
				description = fileEntry.getDescription();
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				folderId = fileEntry.getFolderId();
				id = fileEntry.getFileEntryId();
				keywords = _getAssetTagNames(fileEntry);
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	private static final DocumentEntityModel _vocabularyEntityModel =
		new DocumentEntityModel();

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ClassNameService _classNameService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserLocalService _userLocalService;

}