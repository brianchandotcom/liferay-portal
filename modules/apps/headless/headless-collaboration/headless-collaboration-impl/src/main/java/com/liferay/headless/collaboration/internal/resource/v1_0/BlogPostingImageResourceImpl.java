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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public boolean deleteImageObject(Long imageObjectId) throws Exception {
		_dlAppService.deleteFileEntry(imageObjectId);

		return true;
	}

	@Override
	public Page<BlogPostingImage> getContentSpaceBlogPostingImagesPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Folder folder = _blogsEntryService.addAttachmentsFolder(contentSpaceId);

		Hits hits = SearchUtil.getHits(
			filter, _indexerRegistry.nullSafeGetIndexer(DLFileEntry.class),
			pagination,
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						Field.FOLDER_ID, String.valueOf(folder.getFolderId())),
					BooleanClauseOccur.MUST);
			},
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			_searchResultPermissionFilterFactory, sorts);

		List<FileEntry> fileEntries = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			long fileEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			fileEntries.add(fileEntry);
		}

		return Page.of(
			transform(fileEntries, this::_toBlogPostingImage), pagination,
			fileEntries.size());
	}

	@Override
	public BlogPostingImage getImageObject(Long imageObjectId)
		throws Exception {

		FileEntry fileEntry = _dlAppService.getFileEntry(imageObjectId);

		return _toBlogPostingImage(fileEntry);
	}

	@Override
	public BlogPostingImage postContentSpaceBlogPostingImage(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Folder folder = _blogsEntryService.addAttachmentsFolder(contentSpaceId);

		BlogPostingImage blogPostingImage = multipartBody.getValueAsInstance(
			"blogPostingImage", BlogPostingImage.class);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		String binaryFileName = binaryFile.getFileName();

		String title = Optional.ofNullable(
			blogPostingImage.getTitle()
		).orElse(
			binaryFileName
		);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(contentSpaceId);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			contentSpaceId, folder.getFolderId(), binaryFileName,
			binaryFile.getContentType(), title, null, null,
			binaryFile.getInputStream(), binaryFile.getSize(), serviceContext);

		return _toBlogPostingImage(fileEntry);
	}

	@Override
	public BlogPostingImage putImageObject(
			Long imageObjectId, MultipartBody multipartBody)
		throws Exception {

		BlogPostingImage blogPostingImage = multipartBody.getValueAsInstance(
			"blogPostingImage", BlogPostingImage.class);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		String binaryFileName = binaryFile.getFileName();

		String title = Optional.ofNullable(
			blogPostingImage.getTitle()
		).orElse(
			binaryFileName
		);

		FileEntry fileEntry = _dlAppService.updateFileEntry(
			imageObjectId, binaryFileName, binaryFile.getContentType(), title,
			null, null, DLVersionNumberIncrease.AUTOMATIC,
			binaryFile.getInputStream(), binaryFile.getSize(),
			new ServiceContext());

		return _toBlogPostingImage(fileEntry);
	}

	private BlogPostingImage _toBlogPostingImage(FileEntry fileEntry)
		throws PortalException {

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

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

}