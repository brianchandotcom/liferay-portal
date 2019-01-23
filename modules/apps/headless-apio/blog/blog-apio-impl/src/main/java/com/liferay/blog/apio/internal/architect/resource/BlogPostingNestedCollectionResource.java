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

package com.liferay.blog.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.aggregate.rating.apio.architect.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.blog.apio.architect.model.BlogPosting;
import com.liferay.blog.apio.internal.architect.form.BlogPostingForm;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.comment.apio.architect.identifier.CommentIdentifier;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.exception.ValidationException;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import io.vavr.control.Try;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/BlogPosting">BlogPosting </a> resources through a web
 * API. The resources are mapped from the internal model {@code BlogsEntry}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = NestedCollectionResource.class)
public class BlogPostingNestedCollectionResource
	implements NestedCollectionResource
		<BlogsEntry, Long, BlogPostingIdentifier, Long, ContentSpace> {

	@Override
	public NestedCollectionRoutes<BlogsEntry, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<BlogsEntry, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addBlogsEntry,
			_hasPermission.forAddingIn(ContentSpace.class),
			BlogPostingForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "blog-posting";
	}

	@Override
	public ItemRoutes<BlogsEntry, Long> itemRoutes(
		ItemRoutes.Builder<BlogsEntry, Long> builder) {

		return builder.addGetter(
			_blogsEntryService::getEntry
		).addRemover(
			idempotent(_blogsEntryService::deleteEntry),
			_hasPermission::forDeleting
		).addUpdater(
			this::_updateBlogsEntry, _hasPermission::forUpdating,
			BlogPostingForm::buildForm
		).build();
	}

	@Override
	public Representor<BlogsEntry> representor(
		Representor.Builder<BlogsEntry, Long> builder) {

		return builder.types(
			"BlogPosting"
		).identifier(
			BlogsEntry::getEntryId
		).addBidirectionalModel(
			"contentSpace", "blogPosts", ContentSpace.class,
			BlogsEntry::getGroupId
		).addDate(
			"dateCreated", BlogsEntry::getCreateDate
		).addDate(
			"dateModified", BlogsEntry::getModifiedDate
		).addDate(
			"datePublished", BlogsEntry::getDisplayDate
		).addLinkedModel(
			"aggregateRating", AggregateRatingIdentifier.class,
			ClassNameClassPK::create
		).addLinkedModel(
			"creator", PersonIdentifier.class, BlogsEntry::getUserId
		).addLinkedModel(
			"image", MediaObjectIdentifier.class,
			this::_getCoverImageFileEntryId
		).addRelatedCollection(
			"category", CategoryIdentifier.class
		).addRelatedCollection(
			"comment", CommentIdentifier.class
		).addString(
			"alternativeHeadline", BlogsEntry::getSubtitle
		).addString(
			"articleBody", BlogsEntry::getContent
		).addString(
			"caption", BlogsEntry::getCoverImageCaption
		).addString(
			"description", BlogsEntry::getDescription
		).addString(
			"encodingFormat", blogsEntry -> "text/html"
		).addString(
			"friendlyUrlPath", BlogsEntry::getUrlTitle
		).addString(
			"headline", BlogsEntry::getTitle
		).addStringList(
			"keywords", this::_getBlogsEntryAssetTags
		).build();
	}

	private BlogsEntry _addBlogsEntry(
			long groupId, BlogPosting blogPosting)
		throws PortalException {

		Optional<LocalDateTime> publishedDateOptional =
			_getLocalDateTimeOptional(blogPosting.getPublishedDate());

		LocalDateTime localDateTime =
			publishedDateOptional.orElse(LocalDateTime.now());

		try {
			return _blogsEntryService.addEntry(blogPosting.getHeadline(),
				blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyURLPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(),
				true, true, new String[0], blogPosting.getCaption(),
				_getImageSelector(blogPosting), null,
				_getServiceContext(groupId, blogPosting));
		}
		catch (DuplicateFriendlyURLEntryException dfurlee) {
			throw new ValidationException("Duplicate friendly URL", dfurlee);
		}
	}

	private List<String> _getBlogsEntryAssetTags(BlogsEntry blogsEntry) {
		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		return ListUtil.toList(assetTags, AssetTag::getName);
	}

	private Long _getCoverImageFileEntryId(BlogsEntry blogsEntry) {
		long coverImageFileEntryId = blogsEntry.getCoverImageFileEntryId();

		if (coverImageFileEntryId == 0L) {
			return null;
		}

		return coverImageFileEntryId;
	}

	private ImageSelector _getImageSelector(BlogPosting blogPosting) {
		Long imageId = blogPosting.getImageId();

		if ((imageId == null) || imageId.equals(0L)) {
			return null;
		}

		return Try.of(
			() -> _dlAppLocalService.getFileEntry(imageId)
		).mapTry(
			fileEntry -> new ImageSelector(
				FileUtil.getBytes(fileEntry.getContentStream()),
				fileEntry.getFileName(), fileEntry.getMimeType(),
				"{\"height\": 0,\"width\": 0,\"x\": 0,\"y\": 0}")
		).getOrElseThrow(
			t -> new BadRequestException(
				"Unable to find file entry with id " + imageId, t)
		);
	}

	private PageItems<BlogsEntry> _getPageItems(
		Pagination pagination, long contentSpaceId) {

		List<BlogsEntry> blogsEntries = _blogsEntryService.getGroupEntries(
			contentSpaceId, WorkflowConstants.STATUS_APPROVED,
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = _blogsEntryService.getGroupEntriesCount(
			contentSpaceId, WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(blogsEntries, count);
	}

	private ServiceContext _getServiceContext(
		long groupId, BlogPosting blogPosting) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		List<Long> categories = blogPosting.getCategories();

		if (ListUtil.isNotEmpty(categories)) {
			serviceContext.setAssetCategoryIds(
				ArrayUtil.toLongArray(categories));
		}

		List<String> keywords = blogPosting.getKeywords();

		if (ListUtil.isNotEmpty(keywords)) {
			serviceContext.setAssetTagNames(ArrayUtil.toStringArray(keywords));
		}

		Date createdDate = blogPosting.getCreatedDate();

		if (createdDate != null) {
			serviceContext.setCreateDate(createdDate);
		}

		Date modifiedDate = blogPosting.getModifiedDate();

		if (modifiedDate != null) {
			serviceContext.setModifiedDate(modifiedDate);
		}

		serviceContext.setScopeGroupId(groupId);

		return serviceContext;
	}

	private BlogsEntry _updateBlogsEntry(
			long blogsEntryId, BlogPosting blogPosting)
		throws PortalException {

		Optional<LocalDateTime> publishedDateOptional =
			_getLocalDateTimeOptional(blogPosting.getPublishedDate());

		LocalDateTime localDateTime =
			publishedDateOptional.orElse(LocalDateTime.now());

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogsEntryId);
		ImageSelector imageSelector = _getImageSelector(blogPosting);

		ServiceContext serviceContext = _getServiceContext(
			blogsEntry.getGroupId(), blogPosting);

		return _blogsEntryService.updateEntry(
			blogsEntryId, blogPosting.getHeadline(),
			blogPosting.getAlternativeHeadline(),
			blogPosting.getFriendlyURLPath(), blogPosting.getDescription(),
			blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
			localDateTime.getDayOfMonth(), localDateTime.getYear(),
			localDateTime.getHour(), localDateTime.getMinute(), true, true,
			new String[0], blogPosting.getCaption(), imageSelector, null,
			serviceContext);
	}

	private Optional<LocalDateTime> _getLocalDateTimeOptional(
		Date dateToConvert) {

		return Optional.ofNullable(
			dateToConvert
		).map(
			date -> LocalDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault())
		);
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference(target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)")
	private HasPermission<Long> _hasPermission;

}