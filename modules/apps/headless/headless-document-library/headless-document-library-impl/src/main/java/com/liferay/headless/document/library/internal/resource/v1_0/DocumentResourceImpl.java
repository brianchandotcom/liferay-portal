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
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.document.library.dto.v1_0.AdaptedImages;
import com.liferay.headless.document.library.dto.v1_0.Categories;
import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.document.library.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.document.library.internal.odata.entity.v1_0.DocumentEntityModel;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

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
	scope = ServiceScope.PROTOTYPE,
	service = {DocumentResource.class, EntityModelResource.class}
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
		return _entityModel;
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

	@Override
	public Document postContentSpaceDocument(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		return _addDocument(contentSpaceId, 0L, contentSpaceId, multipartBody);
	}

	@Override
	public Document postFolderDocument(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

		Folder folder = _dlAppService.getFolder(folderId);

		return _addDocument(
			folder.getRepositoryId(), folderId, folder.getGroupId(),
			multipartBody);
	}

	@Override
	public Document putDocument(Long documentId, MultipartBody multipartBody)
		throws Exception {

		Document document = multipartBody.getValueAsInstance(
			"Document", Document.class);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		String binaryFileName = binaryFile.getFileName();

		String title = Optional.ofNullable(
			document.getTitle()
		).orElse(
			binaryFileName
		);

		FileEntry oldFileEntry = _dlAppService.getFileEntry(documentId);

		FileEntry fileEntry = _dlAppService.updateFileEntry(
			documentId, binaryFileName, binaryFile.getContentType(), title,
			document.getDescription(), null, DLVersionNumberIncrease.AUTOMATIC,
			binaryFile.getInputStream(), binaryFile.getSize(),
			_getServiceContext(
				oldFileEntry.getGroupId(), document.getCategoryIds(),
				document.getKeywords()));

		return _toDocument(
			fileEntry, fileEntry.getFileVersion(),
			_userService.getUserById(fileEntry.getUserId()));
	}

	private Document _addDocument(
			Long repositoryId, long folderId, Long groupId,
			MultipartBody multipartBody)
		throws Exception {

		Document document = multipartBody.getValueAsInstance(
			"Document", Document.class);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		String binaryFileName = binaryFile.getFileName();

		String title = Optional.ofNullable(
			document.getTitle()
		).orElse(
			binaryFileName
		);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			repositoryId, folderId, binaryFileName, binaryFile.getContentType(),
			title, document.getDescription(), null, binaryFile.getInputStream(),
			binaryFile.getSize(),
			_getServiceContext(
				groupId, document.getCategoryIds(), document.getKeywords()));

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

	private Categories[] _getCategories(FileEntry fileEntry) {
		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		Stream<AssetCategory> stream = assetCategories.stream();

		return stream.map(
			assetCategory -> new Categories() {
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
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
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

	private ServiceContext _getServiceContext(
		long groupId, Long[] categoryIds, String[] keywords) {

		return new ServiceContext() {
			{
				setAddGroupPermissions(true);
				setAddGuestPermissions(true);

				if (ArrayUtil.isNotEmpty(categoryIds)) {
					setAssetCategoryIds(ArrayUtil.toArray(categoryIds));
				}

				if (ArrayUtil.isNotEmpty(keywords)) {
					setAssetTagNames(keywords);
				}

				setScopeGroupId(groupId);
			}
		};
	}

	private <T, S> T _getValue(
		AdaptiveMedia<S> adaptiveMedia, AMAttribute<S, T> amAttribute) {

		Optional<T> optional = adaptiveMedia.getValueOptional(amAttribute);

		return optional.orElse(null);
	}

	private AdaptedImages _toAdaptedImages(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia) {

		return new AdaptedImages() {
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

		return new Document() {
			{
				adaptedImages = _getAdaptiveMedias(fileEntry);
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						FileEntry.class.getName(), fileEntry.getFileEntryId()));
				categories = _getCategories(fileEntry);
				categoryIds = ArrayUtil.toArray(
					_assetCategoryLocalService.getCategoryIds(
						FileEntry.class.getName(), fileEntry.getFileEntryId()));
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
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()),
					AssetTag.NAME_ACCESSOR);
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	private static final EntityModel _entityModel = new DocumentEntityModel();

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
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserLocalService _userService;

}