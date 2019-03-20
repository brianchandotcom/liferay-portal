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
import com.liferay.headless.collaboration.dto.v1_0.DiscussionAttachment;
import com.liferay.headless.collaboration.dto.v1_0.DiscussionForumPosting;
import com.liferay.headless.collaboration.dto.v1_0.DiscussionSection;
import com.liferay.headless.collaboration.dto.v1_0.DiscussionThread;
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.headless.collaboration.resource.v1_0.CommentResource;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionAttachmentResource;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionForumPostingResource;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionSectionResource;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionThreadResource;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseFolderResource;
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

	public static void setDiscussionAttachmentResourceComponentServiceObjects(
		ComponentServiceObjects<DiscussionAttachmentResource>
			discussionAttachmentResourceComponentServiceObjects) {

		_discussionAttachmentResourceComponentServiceObjects =
			discussionAttachmentResourceComponentServiceObjects;
	}

	public static void setDiscussionForumPostingResourceComponentServiceObjects(
		ComponentServiceObjects<DiscussionForumPostingResource>
			discussionForumPostingResourceComponentServiceObjects) {

		_discussionForumPostingResourceComponentServiceObjects =
			discussionForumPostingResourceComponentServiceObjects;
	}

	public static void setDiscussionSectionResourceComponentServiceObjects(
		ComponentServiceObjects<DiscussionSectionResource>
			discussionSectionResourceComponentServiceObjects) {

		_discussionSectionResourceComponentServiceObjects =
			discussionSectionResourceComponentServiceObjects;
	}

	public static void setDiscussionThreadResourceComponentServiceObjects(
		ComponentServiceObjects<DiscussionThreadResource>
			discussionThreadResourceComponentServiceObjects) {

		_discussionThreadResourceComponentServiceObjects =
			discussionThreadResourceComponentServiceObjects;
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
						contentSpaceId, filter, Pagination.of(pageSize, page),
						sorts);

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
							contentSpaceId, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getBlogPostingCommentsPage(
			@GraphQLName("blog-posting-id") Long blogPostingId,
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
						blogPostingId, filter, Pagination.of(pageSize, page),
						sorts);

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
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> {
				Page paginationPage = commentResource.getCommentCommentsPage(
					commentId, filter, Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionAttachment getDiscussionAttachment(
			@GraphQLName("discussion-attachment-id") Long
				discussionAttachmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionAttachmentResource ->
				discussionAttachmentResource.getDiscussionAttachment(
					discussionAttachmentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DiscussionAttachment>
			getDiscussionForumPostingDiscussionAttachmentsPage(
				@GraphQLName("discussion-forum-posting-id") Long
					discussionForumPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionAttachmentResource -> {
				Page paginationPage =
					discussionAttachmentResource.
						getDiscussionForumPostingDiscussionAttachmentsPage(
							discussionForumPostingId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DiscussionAttachment>
			getDiscussionThreadDiscussionAttachmentsPage(
				@GraphQLName("discussion-thread-id") Long discussionThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionAttachmentResource -> {
				Page paginationPage =
					discussionAttachmentResource.
						getDiscussionThreadDiscussionAttachmentsPage(
							discussionThreadId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionForumPosting getDiscussionForumPosting(
			@GraphQLName("discussion-forum-posting-id") Long
				discussionForumPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionForumPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionForumPostingResource ->
				discussionForumPostingResource.getDiscussionForumPosting(
					discussionForumPostingId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DiscussionForumPosting>
			getDiscussionForumPostingDiscussionForumPostingsPage(
				@GraphQLName("discussion-forum-posting-id") Long
					discussionForumPostingId,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionForumPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionForumPostingResource -> {
				Page paginationPage =
					discussionForumPostingResource.
						getDiscussionForumPostingDiscussionForumPostingsPage(
							discussionForumPostingId, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DiscussionForumPosting>
			getDiscussionThreadDiscussionForumPostingsPage(
				@GraphQLName("discussion-thread-id") Long discussionThreadId,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionForumPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionForumPostingResource -> {
				Page paginationPage =
					discussionForumPostingResource.
						getDiscussionThreadDiscussionForumPostingsPage(
							discussionThreadId, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DiscussionSection> getContentSpaceDiscussionSectionsPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionSectionResource -> {
				Page paginationPage =
					discussionSectionResource.
						getContentSpaceDiscussionSectionsPage(
							contentSpaceId, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionSection getDiscussionSection(
			@GraphQLName("discussion-section-id") Long discussionSectionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionSectionResource ->
				discussionSectionResource.getDiscussionSection(
					discussionSectionId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DiscussionSection>
			getDiscussionSectionDiscussionSectionsPage(
				@GraphQLName("discussion-section-id") Long discussionSectionId,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionSectionResource -> {
				Page paginationPage =
					discussionSectionResource.
						getDiscussionSectionDiscussionSectionsPage(
							discussionSectionId, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DiscussionThread> getContentSpaceDiscussionThreadsPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionThreadResource -> {
				Page paginationPage =
					discussionThreadResource.
						getContentSpaceDiscussionThreadsPage(
							contentSpaceId, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DiscussionThread>
			getDiscussionSectionDiscussionThreadsPage(
				@GraphQLName("discussion-section-id") Long discussionSectionId,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionThreadResource -> {
				Page paginationPage =
					discussionThreadResource.
						getDiscussionSectionDiscussionThreadsPage(
							discussionSectionId, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionThread getDiscussionThread(
			@GraphQLName("discussion-thread-id") Long discussionThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionThreadResource ->
				discussionThreadResource.getDiscussionThread(
					discussionThreadId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getContentSpaceKnowledgeBaseArticlesPage(
				@GraphQLName("content-space-id") Long contentSpaceId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getContentSpaceKnowledgeBaseArticlesPage(
							contentSpaceId, Pagination.of(pageSize, page));

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
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
							knowledgeBaseArticleId,
							Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				@GraphQLName("knowledge-base-folder-id") Long
					knowledgeBaseFolderId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
							knowledgeBaseFolderId,
							Pagination.of(pageSize, page));

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
			DiscussionAttachmentResource discussionAttachmentResource)
		throws Exception {

		discussionAttachmentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DiscussionForumPostingResource discussionForumPostingResource)
		throws Exception {

		discussionForumPostingResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DiscussionSectionResource discussionSectionResource)
		throws Exception {

		discussionSectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DiscussionThreadResource discussionThreadResource)
		throws Exception {

		discussionThreadResource.setContextCompany(
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

	private static ComponentServiceObjects<BlogPostingResource>
		_blogPostingResourceComponentServiceObjects;
	private static ComponentServiceObjects<BlogPostingImageResource>
		_blogPostingImageResourceComponentServiceObjects;
	private static ComponentServiceObjects<CommentResource>
		_commentResourceComponentServiceObjects;
	private static ComponentServiceObjects<DiscussionAttachmentResource>
		_discussionAttachmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<DiscussionForumPostingResource>
		_discussionForumPostingResourceComponentServiceObjects;
	private static ComponentServiceObjects<DiscussionSectionResource>
		_discussionSectionResourceComponentServiceObjects;
	private static ComponentServiceObjects<DiscussionThreadResource>
		_discussionThreadResourceComponentServiceObjects;
	private static ComponentServiceObjects<KnowledgeBaseArticleResource>
		_knowledgeBaseArticleResourceComponentServiceObjects;
	private static ComponentServiceObjects<KnowledgeBaseAttachmentResource>
		_knowledgeBaseAttachmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<KnowledgeBaseFolderResource>
		_knowledgeBaseFolderResourceComponentServiceObjects;

}