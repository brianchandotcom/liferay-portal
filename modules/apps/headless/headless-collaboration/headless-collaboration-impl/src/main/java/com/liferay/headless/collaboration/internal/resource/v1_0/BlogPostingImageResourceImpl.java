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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/blog-posting-image.properties",
	scope = ServiceScope.PROTOTYPE, service = BlogPostingImageResource.class
)
public class BlogPostingImageResourceImpl
	extends BaseBlogPostingImageResourceImpl {

	@Override
	public boolean deleteBlogPostingImage(Long blogPostingImageId)
		throws Exception {

		FileEntry fileEntry = _getFileEntry(blogPostingImageId);

		_dlAppService.deleteFileEntry(fileEntry.getFileEntryId());

		return true;
	}

	@Override
	public BlogPostingImage getBlogPostingImage(Long blogPostingImageId)
		throws Exception {

		FileEntry fileEntry = _getFileEntry(blogPostingImageId);

		return _toBlogPostingImage(fileEntry);
	}

	@Override
	public Page<BlogPostingImage> getContentSpaceBlogPostingImagesPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<FileEntry> fileEntries = new ArrayList<>();

		Folder folder = _blogsEntryService.addAttachmentsFolder(contentSpaceId);

		Indexer<Folder> indexer = _indexerRegistry.getIndexer(Folder.class);

		SearchContext searchContext = SearchUtil.createSearchContext(
			filter, pagination,
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						Field.FOLDER_ID, String.valueOf(folder.getFolderId())),
					BooleanClauseOccur.MUST);
			},
			queryConfig -> {
				queryConfig.setSelectedFieldNames(Field.ENTRY_CLASS_PK);
			},
			sorts);

		searchContext.setCompanyId(contextCompany.getCompanyId());

		Hits hits = indexer.search(searchContext);

		for (Document document : hits.getDocs()) {
			long fileEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			fileEntries.add(fileEntry);
		}

		return Page.of(
			transform(fileEntries, this::_toBlogPostingImage), pagination,
			indexer.searchCount(searchContext));
	}

	@Override
	public BlogPostingImage patchBlogPostingImage(
			Long blogPostingImageId, MultipartBody multipartBody)
		throws Exception {

		FileEntry existingFileEntry = _getFileEntry(blogPostingImageId);

		BinaryFile binaryFile = Optional.ofNullable(
			multipartBody.getBinaryFile("file")
		).orElse(
			new BinaryFile(
				existingFileEntry.getMimeType(),
				existingFileEntry.getFileName(),
				existingFileEntry.getContentStream(),
				existingFileEntry.getSize())
		);

		Optional<BlogPostingImage> optional = Optional.empty();

		if (Validator.isNotNull(
				multipartBody.getValueAsString("blogPostingImage"))) {

			optional = Optional.of(
				multipartBody.getValueAsInstance(
					"blogPostingImage", BlogPostingImage.class));
		}

		FileEntry fileEntry = _dlAppService.updateFileEntry(
			blogPostingImageId, binaryFile.getFileName(),
			binaryFile.getContentType(),
			optional.map(
				BlogPostingImage::getTitle
			).orElseGet(
				existingFileEntry::getTitle
			),
			null, null, DLVersionNumberIncrease.AUTOMATIC,
			binaryFile.getInputStream(), binaryFile.getSize(),
			new ServiceContext());

		return _toBlogPostingImage(fileEntry);
	}

	@Override
	public BlogPostingImage postContentSpaceBlogPostingImage(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Folder folder = _blogsEntryService.addAttachmentsFolder(contentSpaceId);
		BinaryFile binaryFile = multipartBody.getBinaryFile("file");
		BlogPostingImage blogPostingImage = multipartBody.getValueAsInstance(
			"blogPostingImage", BlogPostingImage.class);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			contentSpaceId, folder.getFolderId(), binaryFile.getFileName(),
			binaryFile.getContentType(),
			Optional.ofNullable(
				blogPostingImage.getTitle()
			).orElse(
				binaryFile.getFileName()
			),
			null, null, binaryFile.getInputStream(), binaryFile.getSize(),
			new ServiceContext() {
				{
					setAddGroupPermissions(true);
					setAddGuestPermissions(true);
					setScopeGroupId(contentSpaceId);
				}
			});

		return _toBlogPostingImage(fileEntry);
	}

	@Override
	public BlogPostingImage putBlogPostingImage(
			Long blogPostingImageId, MultipartBody multipartBody)
		throws Exception {

		FileEntry existingFileEntry = _getFileEntry(blogPostingImageId);
		BinaryFile binaryFile = multipartBody.getBinaryFile("file");
		BlogPostingImage blogPostingImage = multipartBody.getValueAsInstance(
			"blogPostingImage", BlogPostingImage.class);

		FileEntry fileEntry = _dlAppService.updateFileEntry(
			existingFileEntry.getFileEntryId(), binaryFile.getFileName(),
			binaryFile.getContentType(),
			Optional.ofNullable(
				blogPostingImage.getTitle()
			).orElse(
				binaryFile.getFileName()
			),
			null, null, DLVersionNumberIncrease.AUTOMATIC,
			binaryFile.getInputStream(), binaryFile.getSize(),
			new ServiceContext());

		return _toBlogPostingImage(fileEntry);
	}

	private FileEntry _getFileEntry(Long fileEntryId) throws Exception {
		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		Folder folder = _blogsEntryService.addAttachmentsFolder(
			fileEntry.getFolderId());

		if (fileEntry.getFolderId() != folder.getFolderId()) {
			throw new BadRequestException(
				fileEntryId +
					" does not correspond to a valid BlogPostingImage");
		}

		return fileEntry;
	}

	private BlogPostingImage _toBlogPostingImage(FileEntry fileEntry)
		throws Exception {

		return new BlogPostingImage() {
			{
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "");
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private IndexerRegistry _indexerRegistry;

}