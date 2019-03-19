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

package com.liferay.headless.collaboration.internal.graphql.mutation.v1_0;

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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

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

	@GraphQLInvokeDetached
	public boolean deleteBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.deleteBlogPosting(
				blogPostingId));
	}

	@GraphQLInvokeDetached
	public BlogPosting patchBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("BlogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.patchBlogPosting(
				blogPostingId, blogPosting));
	}

	@GraphQLInvokeDetached
	public BlogPosting putBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("BlogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.putBlogPosting(
				blogPostingId, blogPosting));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPosting postContentSpaceBlogPosting(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("BlogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource ->
				blogPostingResource.postContentSpaceBlogPosting(
					contentSpaceId, blogPosting));
	}

	@GraphQLInvokeDetached
	public boolean deleteBlogPostingImage(
			@GraphQLName("blog-posting-image-id") Long blogPostingImageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.deleteBlogPostingImage(
					blogPostingImageId));
	}

	@GraphQLInvokeDetached
	public BlogPostingImage putBlogPostingImage(
			@GraphQLName("blog-posting-image-id") Long blogPostingImageId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.putBlogPostingImage(
					blogPostingImageId, multipartBody));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImage postContentSpaceBlogPostingImage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.postContentSpaceBlogPostingImage(
					contentSpaceId, multipartBody));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postBlogPostingComment(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postBlogPostingComment(
				blogPostingId, comment));
	}

	@GraphQLInvokeDetached
	public boolean deleteComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.deleteComment(commentId));
	}

	@GraphQLInvokeDetached
	public Comment putComment(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.putComment(commentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postCommentComment(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postCommentComment(
				commentId, comment));
	}

	@GraphQLInvokeDetached
	public boolean deleteDiscussionAttachment(
			@GraphQLName("discussion-attachment-id") Long
				discussionAttachmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionAttachmentResource ->
				discussionAttachmentResource.deleteDiscussionAttachment(
					discussionAttachmentId));
	}

	@GraphQLInvokeDetached
	public boolean deleteDiscussionForumPostingsAttachment(
			@GraphQLName("discussion-forum-postings-attachment-id") Long
				discussionForumPostingsAttachmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionAttachmentResource ->
				discussionAttachmentResource.
					deleteDiscussionForumPostingsAttachment(
						discussionForumPostingsAttachmentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionAttachment
			postDiscussionForumPostingDiscussionForumPostingsAttachment(
				@GraphQLName("discussion-forum-posting-id") Long
					discussionForumPostingId,
				@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionAttachmentResource ->
				discussionAttachmentResource.
					postDiscussionForumPostingDiscussionForumPostingsAttachment(
						discussionForumPostingId, multipartBody));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionAttachment postDiscussionThreadDiscussionAttachment(
			@GraphQLName("discussion-thread-id") Long discussionThreadId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionAttachmentResource ->
				discussionAttachmentResource.
					postDiscussionThreadDiscussionAttachment(
						discussionThreadId, multipartBody));
	}

	@GraphQLInvokeDetached
	public boolean deleteDiscussionForumPosting(
			@GraphQLName("discussion-forum-posting-id") Long
				discussionForumPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionForumPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionForumPostingResource ->
				discussionForumPostingResource.deleteDiscussionForumPosting(
					discussionForumPostingId));
	}

	@GraphQLInvokeDetached
	public DiscussionForumPosting patchDiscussionForumPosting(
			@GraphQLName("discussion-forum-posting-id") Long
				discussionForumPostingId,
			@GraphQLName("DiscussionForumPosting") DiscussionForumPosting
				discussionForumPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionForumPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionForumPostingResource ->
				discussionForumPostingResource.patchDiscussionForumPosting(
					discussionForumPostingId, discussionForumPosting));
	}

	@GraphQLInvokeDetached
	public DiscussionForumPosting putDiscussionForumPosting(
			@GraphQLName("discussion-forum-posting-id") Long
				discussionForumPostingId,
			@GraphQLName("DiscussionForumPosting") DiscussionForumPosting
				discussionForumPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionForumPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionForumPostingResource ->
				discussionForumPostingResource.putDiscussionForumPosting(
					discussionForumPostingId, discussionForumPosting));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionForumPosting
			postDiscussionForumPostingDiscussionForumPosting(
				@GraphQLName("discussion-forum-posting-id") Long
					discussionForumPostingId,
				@GraphQLName("DiscussionForumPosting") DiscussionForumPosting
					discussionForumPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionForumPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionForumPostingResource ->
				discussionForumPostingResource.
					postDiscussionForumPostingDiscussionForumPosting(
						discussionForumPostingId, discussionForumPosting));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionForumPosting postDiscussionThreadDiscussionForumPosting(
			@GraphQLName("discussion-thread-id") Long discussionThreadId,
			@GraphQLName("DiscussionForumPosting") DiscussionForumPosting
				discussionForumPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionForumPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionForumPostingResource ->
				discussionForumPostingResource.
					postDiscussionThreadDiscussionForumPosting(
						discussionThreadId, discussionForumPosting));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionSection postContentSpaceDiscussionSection(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("DiscussionSection") DiscussionSection
				discussionSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionSectionResource ->
				discussionSectionResource.postContentSpaceDiscussionSection(
					contentSpaceId, discussionSection));
	}

	@GraphQLInvokeDetached
	public boolean deleteDiscussionSection(
			@GraphQLName("discussion-section-id") Long discussionSectionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionSectionResource ->
				discussionSectionResource.deleteDiscussionSection(
					discussionSectionId));
	}

	@GraphQLInvokeDetached
	public DiscussionSection patchDiscussionSection(
			@GraphQLName("discussion-section-id") Long discussionSectionId,
			@GraphQLName("DiscussionSection") DiscussionSection
				discussionSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionSectionResource ->
				discussionSectionResource.patchDiscussionSection(
					discussionSectionId, discussionSection));
	}

	@GraphQLInvokeDetached
	public DiscussionSection putDiscussionSection(
			@GraphQLName("discussion-section-id") Long discussionSectionId,
			@GraphQLName("DiscussionSection") DiscussionSection
				discussionSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionSectionResource ->
				discussionSectionResource.putDiscussionSection(
					discussionSectionId, discussionSection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionSection postDiscussionSectionDiscussionSection(
			@GraphQLName("discussion-section-id") Long discussionSectionId,
			@GraphQLName("DiscussionSection") DiscussionSection
				discussionSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionSectionResource ->
				discussionSectionResource.
					postDiscussionSectionDiscussionSection(
						discussionSectionId, discussionSection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionThread postContentSpaceDiscussionThread(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("DiscussionThread") DiscussionThread discussionThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionThreadResource ->
				discussionThreadResource.postContentSpaceDiscussionThread(
					contentSpaceId, discussionThread));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DiscussionThread postDiscussionSectionDiscussionThread(
			@GraphQLName("discussion-section-id") Long discussionSectionId,
			@GraphQLName("DiscussionThread") DiscussionThread discussionThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionThreadResource ->
				discussionThreadResource.postDiscussionSectionDiscussionThread(
					discussionSectionId, discussionThread));
	}

	@GraphQLInvokeDetached
	public boolean deleteDiscussionThread(
			@GraphQLName("discussion-thread-id") Long discussionThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionThreadResource ->
				discussionThreadResource.deleteDiscussionThread(
					discussionThreadId));
	}

	@GraphQLInvokeDetached
	public DiscussionThread patchDiscussionThread(
			@GraphQLName("discussion-thread-id") Long discussionThreadId,
			@GraphQLName("DiscussionThread") DiscussionThread discussionThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionThreadResource ->
				discussionThreadResource.patchDiscussionThread(
					discussionThreadId, discussionThread));
	}

	@GraphQLInvokeDetached
	public DiscussionThread putDiscussionThread(
			@GraphQLName("discussion-thread-id") Long discussionThreadId,
			@GraphQLName("DiscussionThread") DiscussionThread discussionThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_discussionThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			discussionThreadResource ->
				discussionThreadResource.putDiscussionThread(
					discussionThreadId, discussionThread));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle postContentSpaceKnowledgeBaseArticle(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					postContentSpaceKnowledgeBaseArticle(
						contentSpaceId, knowledgeBaseArticle));
	}

	@GraphQLInvokeDetached
	public boolean deleteKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
					knowledgeBaseArticleId));
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.patchKnowledgeBaseArticle(
					knowledgeBaseArticleId, knowledgeBaseArticle));
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.putKnowledgeBaseArticle(
					knowledgeBaseArticleId, knowledgeBaseArticle));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					postKnowledgeBaseArticleKnowledgeBaseArticle(
						knowledgeBaseArticleId, knowledgeBaseArticle));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					postKnowledgeBaseFolderKnowledgeBaseArticle(
						knowledgeBaseFolderId, knowledgeBaseArticle));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseAttachment
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				@GraphQLName("knowledge-base-article-id") Long
					knowledgeBaseArticleId,
				@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.
					postKnowledgeBaseArticleKnowledgeBaseAttachment(
						knowledgeBaseArticleId, multipartBody));
	}

	@GraphQLInvokeDetached
	public boolean deleteKnowledgeBaseAttachment(
			@GraphQLName("knowledge-base-attachment-id") Long
				knowledgeBaseAttachmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.deleteKnowledgeBaseAttachment(
					knowledgeBaseAttachmentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseFolder postContentSpaceKnowledgeBaseFolder(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("KnowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.postContentSpaceKnowledgeBaseFolder(
					contentSpaceId, knowledgeBaseFolder));
	}

	@GraphQLInvokeDetached
	public boolean deleteKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.deleteKnowledgeBaseFolder(
					knowledgeBaseFolderId));
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId,
			@GraphQLName("KnowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.patchKnowledgeBaseFolder(
					knowledgeBaseFolderId, knowledgeBaseFolder));
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId,
			@GraphQLName("KnowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.putKnowledgeBaseFolder(
					knowledgeBaseFolderId, knowledgeBaseFolder));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId,
			@GraphQLName("KnowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.
					postKnowledgeBaseFolderKnowledgeBaseFolder(
						knowledgeBaseFolderId, knowledgeBaseFolder));
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