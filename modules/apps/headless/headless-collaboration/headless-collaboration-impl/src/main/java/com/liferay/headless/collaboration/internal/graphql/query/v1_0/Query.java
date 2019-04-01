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

package com.liferay.headless.collaboration.internal.graphql.query.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.headless.collaboration.dto.v1_0.Comment;
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.collaboration.dto.v1_0.MessageBoardAttachment;
import com.liferay.headless.collaboration.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.collaboration.dto.v1_0.MessageBoardSection;
import com.liferay.headless.collaboration.dto.v1_0.MessageBoardThread;
import com.liferay.headless.collaboration.dto.v1_0.Rating;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.headless.collaboration.resource.v1_0.CommentResource;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.headless.collaboration.resource.v1_0.MessageBoardAttachmentResource;
import com.liferay.headless.collaboration.resource.v1_0.MessageBoardMessageResource;
import com.liferay.headless.collaboration.resource.v1_0.MessageBoardSectionResource;
import com.liferay.headless.collaboration.resource.v1_0.MessageBoardThreadResource;
import com.liferay.headless.collaboration.resource.v1_0.RatingResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setBlogPostingResourceComponentServiceObjects(
		ComponentServiceObjects<BlogPostingResource>
			blogPostingResourceComponentServiceObjects) {

		_blogPostingResourceComponentServiceObjects =
			blogPostingResourceComponentServiceObjects;
	}

	public static void setBlogPostingImageResourceComponentServiceObjects(
		ComponentServiceObjects<BlogPostingImageResource>
			blogPostingImageResourceComponentServiceObjects) {

		_blogPostingImageResourceComponentServiceObjects =
			blogPostingImageResourceComponentServiceObjects;
	}

	public static void setCommentResourceComponentServiceObjects(
		ComponentServiceObjects<CommentResource>
			commentResourceComponentServiceObjects) {

		_commentResourceComponentServiceObjects =
			commentResourceComponentServiceObjects;
	}

	public static void setKnowledgeBaseArticleResourceComponentServiceObjects(
		ComponentServiceObjects<KnowledgeBaseArticleResource>
			knowledgeBaseArticleResourceComponentServiceObjects) {

		_knowledgeBaseArticleResourceComponentServiceObjects =
			knowledgeBaseArticleResourceComponentServiceObjects;
	}

	public static void
		setKnowledgeBaseAttachmentResourceComponentServiceObjects(
			ComponentServiceObjects<KnowledgeBaseAttachmentResource>
				knowledgeBaseAttachmentResourceComponentServiceObjects) {

		_knowledgeBaseAttachmentResourceComponentServiceObjects =
			knowledgeBaseAttachmentResourceComponentServiceObjects;
	}

	public static void setKnowledgeBaseFolderResourceComponentServiceObjects(
		ComponentServiceObjects<KnowledgeBaseFolderResource>
			knowledgeBaseFolderResourceComponentServiceObjects) {

		_knowledgeBaseFolderResourceComponentServiceObjects =
			knowledgeBaseFolderResourceComponentServiceObjects;
	}

	public static void setMessageBoardAttachmentResourceComponentServiceObjects(
		ComponentServiceObjects<MessageBoardAttachmentResource>
			messageBoardAttachmentResourceComponentServiceObjects) {

		_messageBoardAttachmentResourceComponentServiceObjects =
			messageBoardAttachmentResourceComponentServiceObjects;
	}

	public static void setMessageBoardMessageResourceComponentServiceObjects(
		ComponentServiceObjects<MessageBoardMessageResource>
			messageBoardMessageResourceComponentServiceObjects) {

		_messageBoardMessageResourceComponentServiceObjects =
			messageBoardMessageResourceComponentServiceObjects;
	}

	public static void setMessageBoardSectionResourceComponentServiceObjects(
		ComponentServiceObjects<MessageBoardSectionResource>
			messageBoardSectionResourceComponentServiceObjects) {

		_messageBoardSectionResourceComponentServiceObjects =
			messageBoardSectionResourceComponentServiceObjects;
	}

	public static void setMessageBoardThreadResourceComponentServiceObjects(
		ComponentServiceObjects<MessageBoardThreadResource>
			messageBoardThreadResourceComponentServiceObjects) {

		_messageBoardThreadResourceComponentServiceObjects =
			messageBoardThreadResourceComponentServiceObjects;
	}

	public static void setRatingResourceComponentServiceObjects(
		ComponentServiceObjects<RatingResource>
			ratingResourceComponentServiceObjects) {

		_ratingResourceComponentServiceObjects =
			ratingResourceComponentServiceObjects;
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPosting getBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.getBlogPosting(
				blogPostingId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPosting> getContentSpaceBlogPostingsPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> {
				Page paginationPage =
					blogPostingResource.getContentSpaceBlogPostingsPage(
						contentSpaceId, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImage getBlogPostingImage(
			@GraphQLName("blog-posting-image-id") Long blogPostingImageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.getBlogPostingImage(
					blogPostingImageId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPostingImage> getContentSpaceBlogPostingImagesPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource -> {
				Page paginationPage =
					blogPostingImageResource.
						getContentSpaceBlogPostingImagesPage(
							contentSpaceId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getBlogPostingCommentsPage(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> {
				Page paginationPage =
					commentResource.getBlogPostingCommentsPage(
						blogPostingId, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment getComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.getComment(commentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getCommentCommentsPage(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> {
				Page paginationPage = commentResource.getCommentCommentsPage(
					commentId, search, filter, Pagination.of(pageSize, page),
					sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getContentSpaceKnowledgeBaseArticlesPage(
				@GraphQLName("content-space-id") Long contentSpaceId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getContentSpaceKnowledgeBaseArticlesPage(
							contentSpaceId, flatten, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.getKnowledgeBaseArticle(
					knowledgeBaseArticleId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				@GraphQLName("knowledge-base-article-id") Long
					knowledgeBaseArticleId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
							knowledgeBaseArticleId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				@GraphQLName("knowledge-base-folder-id") Long
					knowledgeBaseFolderId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
							knowledgeBaseFolderId, flatten, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseAttachment>
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
				@GraphQLName("knowledge-base-article-id") Long
					knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseAttachmentResource -> {
				Page paginationPage =
					knowledgeBaseAttachmentResource.
						getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
							knowledgeBaseArticleId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseAttachment getKnowledgeBaseAttachment(
			@GraphQLName("knowledge-base-attachment-id") Long
				knowledgeBaseAttachmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.getKnowledgeBaseAttachment(
					knowledgeBaseAttachmentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseFolder>
			getContentSpaceKnowledgeBaseFoldersPage(
				@GraphQLName("content-space-id") Long contentSpaceId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource -> {
				Page paginationPage =
					knowledgeBaseFolderResource.
						getContentSpaceKnowledgeBaseFoldersPage(
							contentSpaceId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.getKnowledgeBaseFolder(
					knowledgeBaseFolderId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				@GraphQLName("knowledge-base-folder-id") Long
					knowledgeBaseFolderId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource -> {
				Page paginationPage =
					knowledgeBaseFolderResource.
						getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
							knowledgeBaseFolderId,
							Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardAttachment getMessageBoardAttachment(
			@GraphQLName("message-board-attachment-id") Long
				messageBoardAttachmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.getMessageBoardAttachment(
					messageBoardAttachmentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardAttachment>
			getMessageBoardMessageMessageBoardAttachmentsPage(
				@GraphQLName("message-board-message-id") Long
					messageBoardMessageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource -> {
				Page paginationPage =
					messageBoardAttachmentResource.
						getMessageBoardMessageMessageBoardAttachmentsPage(
							messageBoardMessageId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardAttachment>
			getMessageBoardThreadMessageBoardAttachmentsPage(
				@GraphQLName("message-board-thread-id") Long
					messageBoardThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource -> {
				Page paginationPage =
					messageBoardAttachmentResource.
						getMessageBoardThreadMessageBoardAttachmentsPage(
							messageBoardThreadId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardMessage getMessageBoardMessage(
			@GraphQLName("message-board-message-id") Long messageBoardMessageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.getMessageBoardMessage(
					messageBoardMessageId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardMessage>
			getMessageBoardMessageMessageBoardMessagesPage(
				@GraphQLName("message-board-message-id") Long
					messageBoardMessageId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource -> {
				Page paginationPage =
					messageBoardMessageResource.
						getMessageBoardMessageMessageBoardMessagesPage(
							messageBoardMessageId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardMessage>
			getMessageBoardThreadMessageBoardMessagesPage(
				@GraphQLName("message-board-thread-id") Long
					messageBoardThreadId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource -> {
				Page paginationPage =
					messageBoardMessageResource.
						getMessageBoardThreadMessageBoardMessagesPage(
							messageBoardThreadId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardSection>
			getContentSpaceMessageBoardSectionsPage(
				@GraphQLName("content-space-id") Long contentSpaceId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource -> {
				Page paginationPage =
					messageBoardSectionResource.
						getContentSpaceMessageBoardSectionsPage(
							contentSpaceId, flatten, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardSection getMessageBoardSection(
			@GraphQLName("message-board-section-id") Long messageBoardSectionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.getMessageBoardSection(
					messageBoardSectionId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardSection>
			getMessageBoardSectionMessageBoardSectionsPage(
				@GraphQLName("message-board-section-id") Long
					messageBoardSectionId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource -> {
				Page paginationPage =
					messageBoardSectionResource.
						getMessageBoardSectionMessageBoardSectionsPage(
							messageBoardSectionId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardThread>
			getContentSpaceMessageBoardThreadsPage(
				@GraphQLName("content-space-id") Long contentSpaceId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource -> {
				Page paginationPage =
					messageBoardThreadResource.
						getContentSpaceMessageBoardThreadsPage(
							contentSpaceId, flatten, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardThread>
			getMessageBoardSectionMessageBoardThreadsPage(
				@GraphQLName("message-board-section-id") Long
					messageBoardSectionId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource -> {
				Page paginationPage =
					messageBoardThreadResource.
						getMessageBoardSectionMessageBoardThreadsPage(
							messageBoardSectionId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardThread getMessageBoardThread(
			@GraphQLName("message-board-thread-id") Long messageBoardThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.getMessageBoardThread(
					messageBoardThreadId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Rating> getBlogPostingRatingsPage(
			@GraphQLName("blog-posting-id") Long blogPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> {
				Page paginationPage = ratingResource.getBlogPostingRatingsPage(
					blogPostingId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Rating> getKnowledgeBaseArticleRatingsPage(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> {
				Page paginationPage =
					ratingResource.getKnowledgeBaseArticleRatingsPage(
						knowledgeBaseArticleId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Rating> getMessageBoardMessageRatingsPage(
			@GraphQLName("message-board-message-id") Long messageBoardMessageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> {
				Page paginationPage =
					ratingResource.getMessageBoardMessageRatingsPage(
						messageBoardMessageId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Rating> getMessageBoardThreadRatingsPage(
			@GraphQLName("message-board-thread-id") Long messageBoardThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> {
				Page paginationPage =
					ratingResource.getMessageBoardThreadRatingsPage(
						messageBoardThreadId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating getRating(@GraphQLName("rating-id") Long ratingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.getRating(ratingId));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			BlogPostingResource blogPostingResource)
		throws Exception {

		blogPostingResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			BlogPostingImageResource blogPostingImageResource)
		throws Exception {

		blogPostingImageResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(CommentResource commentResource)
		throws Exception {

		commentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			KnowledgeBaseArticleResource knowledgeBaseArticleResource)
		throws Exception {

		knowledgeBaseArticleResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			KnowledgeBaseAttachmentResource knowledgeBaseAttachmentResource)
		throws Exception {

		knowledgeBaseAttachmentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			KnowledgeBaseFolderResource knowledgeBaseFolderResource)
		throws Exception {

		knowledgeBaseFolderResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			MessageBoardAttachmentResource messageBoardAttachmentResource)
		throws Exception {

		messageBoardAttachmentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			MessageBoardMessageResource messageBoardMessageResource)
		throws Exception {

		messageBoardMessageResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			MessageBoardSectionResource messageBoardSectionResource)
		throws Exception {

		messageBoardSectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			MessageBoardThreadResource messageBoardThreadResource)
		throws Exception {

		messageBoardThreadResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(RatingResource ratingResource)
		throws Exception {

		ratingResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<BlogPostingResource>
		_blogPostingResourceComponentServiceObjects;
	private static ComponentServiceObjects<BlogPostingImageResource>
		_blogPostingImageResourceComponentServiceObjects;
	private static ComponentServiceObjects<CommentResource>
		_commentResourceComponentServiceObjects;
	private static ComponentServiceObjects<KnowledgeBaseArticleResource>
		_knowledgeBaseArticleResourceComponentServiceObjects;
	private static ComponentServiceObjects<KnowledgeBaseAttachmentResource>
		_knowledgeBaseAttachmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<KnowledgeBaseFolderResource>
		_knowledgeBaseFolderResourceComponentServiceObjects;
	private static ComponentServiceObjects<MessageBoardAttachmentResource>
		_messageBoardAttachmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<MessageBoardMessageResource>
		_messageBoardMessageResourceComponentServiceObjects;
	private static ComponentServiceObjects<MessageBoardSectionResource>
		_messageBoardSectionResourceComponentServiceObjects;
	private static ComponentServiceObjects<MessageBoardThreadResource>
		_messageBoardThreadResourceComponentServiceObjects;
	private static ComponentServiceObjects<RatingResource>
		_ratingResourceComponentServiceObjects;

}