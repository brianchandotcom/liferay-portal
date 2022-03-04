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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.common.spi.service.context.ServiceContextRequestUtil;
import com.liferay.headless.delivery.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.dto.v1_0.util.ContentValueUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.BlogPostingImageEntityModel;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingImageResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;

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
	extends BaseBlogPostingImageResourceImpl implements EntityModelResource {

	@Override
	public void deleteBlogPostingImage(Long blogPostingImageId)
		throws Exception {

		FileEntry fileEntry = _getFileEntry(blogPostingImageId);

		_dlAppService.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public BlogPostingImage getBlogPostingImage(Long blogPostingImageId)
		throws Exception {

		return _toBlogPostingImage(_getFileEntry(blogPostingImageId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<BlogPostingImage> getSiteBlogPostingImagesPage(
			Long siteId, String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Folder folder = _blogsEntryService.addAttachmentsFolder(siteId);

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			filter, DLFileEntry.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setFolderIds(new long[] {folder.getFolderId()});
			},
			sorts,
			document -> _toBlogPostingImage(
				_dlAppService.getFileEntry(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public BlogPostingImage postSiteBlogPostingImage(
			Long siteId, MultipartBody multipartBody)
		throws Exception {

		Folder folder = _blogsEntryService.addAttachmentsFolder(siteId);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		if (binaryFile == null) {
			throw new BadRequestException("No file found in body");
		}

		Optional<BlogPostingImage> blogPostingImageOptional =
			multipartBody.getValueAsInstanceOptional(
				"blogPostingImage", BlogPostingImage.class);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, siteId, folder.getFolderId(), binaryFile.getFileName(),
			binaryFile.getContentType(),
			blogPostingImageOptional.map(
				BlogPostingImage::getTitle
			).orElse(
				binaryFile.getFileName()
			),
			null, null, null, binaryFile.getInputStream(), binaryFile.getSize(),
			null, null,
			ServiceContextRequestUtil.createServiceContext(
				siteId, contextHttpServletRequest,
				blogPostingImageOptional.map(
					BlogPostingImage::getViewableByAsString
				).orElse(
					BlogPostingImage.ViewableBy.ANYONE.getValue()
				)));

		return _toBlogPostingImage(fileEntry);
	}

	private FileEntry _getFileEntry(Long fileEntryId) throws Exception {
		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		Folder folder = _blogsEntryService.addAttachmentsFolder(
			fileEntry.getGroupId());

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
				contentValue = ContentValueUtil.toContentValue(
					"contentValue", fileEntry::getContentStream,
					Optional.of(contextUriInfo));
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	private static final EntityModel _entityModel =
		new BlogPostingImageEntityModel();

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

}