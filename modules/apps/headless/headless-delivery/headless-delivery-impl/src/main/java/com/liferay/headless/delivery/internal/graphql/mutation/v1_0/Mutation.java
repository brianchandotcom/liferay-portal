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

package com.liferay.headless.delivery.internal.graphql.mutation.v1_0;

import com.liferay.headless.delivery.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.Folder;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardAttachment;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardSection;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingResource;
import com.liferay.headless.delivery.resource.v1_0.CommentResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.resource.v1_0.FolderResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardAttachmentResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardMessageResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardSectionResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardThreadResource;
import com.liferay.headless.delivery.resource.v1_0.RatingResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentResource;
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

	public static void setDocumentResourceComponentServiceObjects(
		ComponentServiceObjects<DocumentResource>
			documentResourceComponentServiceObjects) {

		_documentResourceComponentServiceObjects =
			documentResourceComponentServiceObjects;
	}

	public static void setFolderResourceComponentServiceObjects(
		ComponentServiceObjects<FolderResource>
			folderResourceComponentServiceObjects) {

		_folderResourceComponentServiceObjects =
			folderResourceComponentServiceObjects;
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

	public static void setStructuredContentResourceComponentServiceObjects(
		ComponentServiceObjects<StructuredContentResource>
			structuredContentResourceComponentServiceObjects) {

		_structuredContentResourceComponentServiceObjects =
			structuredContentResourceComponentServiceObjects;
	}

	public static void
		setStructuredContentFolderResourceComponentServiceObjects(
			ComponentServiceObjects<StructuredContentFolderResource>
				structuredContentFolderResourceComponentServiceObjects) {

		_structuredContentFolderResourceComponentServiceObjects =
			structuredContentFolderResourceComponentServiceObjects;
	}

	@GraphQLInvokeDetached
	public void deleteBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId)
		throws Exception {

		_applyVoidComponentServiceObjects(
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
	public void deleteBlogPostingImage(
			@GraphQLName("blog-posting-image-id") Long blogPostingImageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.deleteBlogPostingImage(
					blogPostingImageId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName("postContentSpaceBlogPostingImageContentSpaceIdMultipartBody")
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
	public void deleteComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
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

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postDocumentComment(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postDocumentComment(
				documentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postStructuredContentComment(
			@GraphQLName("structured-content-id") Long structuredContentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postStructuredContentComment(
				structuredContentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName("postContentSpaceDocumentContentSpaceIdMultipartBody")
	public Document postContentSpaceDocument(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postContentSpaceDocument(
				contentSpaceId, multipartBody));
	}

	@GraphQLInvokeDetached
	public void deleteDocument(@GraphQLName("document-id") Long documentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.deleteDocument(documentId));
	}

	@GraphQLInvokeDetached
	@GraphQLName("patchDocumentDocumentIdMultipartBody")
	public Document patchDocument(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.patchDocument(
				documentId, multipartBody));
	}

	@GraphQLInvokeDetached
	@GraphQLName("putDocumentDocumentIdMultipartBody")
	public Document putDocument(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.putDocument(
				documentId, multipartBody));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName("postFolderDocumentFolderIdMultipartBody")
	public Document postFolderDocument(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postFolderDocument(
				folderId, multipartBody));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Folder postContentSpaceFolder(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.postContentSpaceFolder(
				contentSpaceId, folder));
	}

	@GraphQLInvokeDetached
	public void deleteFolder(@GraphQLName("folder-id") Long folderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.deleteFolder(folderId));
	}

	@GraphQLInvokeDetached
	public Folder patchFolder(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.patchFolder(folderId, folder));
	}

	@GraphQLInvokeDetached
	public Folder putFolder(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.putFolder(folderId, folder));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Folder postFolderFolder(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.postFolderFolder(
				folderId, folder));
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
	public void deleteKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId)
		throws Exception {

		_applyVoidComponentServiceObjects(
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
	@GraphQLName(
		"postKnowledgeBaseArticleKnowledgeBaseAttachmentKnowledgeBaseArticleIdMultipartBody"
	)
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
	public void deleteKnowledgeBaseAttachment(
			@GraphQLName("knowledge-base-attachment-id") Long
				knowledgeBaseAttachmentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
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
	public void deleteKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
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

	@GraphQLInvokeDetached
	public void deleteMessageBoardAttachment(
			@GraphQLName("message-board-attachment-id") Long
				messageBoardAttachmentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.deleteMessageBoardAttachment(
					messageBoardAttachmentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName(
		"postMessageBoardMessageMessageBoardAttachmentMessageBoardMessageIdMultipartBody"
	)
	public MessageBoardAttachment postMessageBoardMessageMessageBoardAttachment(
			@GraphQLName("message-board-message-id") Long messageBoardMessageId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.
					postMessageBoardMessageMessageBoardAttachment(
						messageBoardMessageId, multipartBody));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName(
		"postMessageBoardThreadMessageBoardAttachmentMessageBoardThreadIdMultipartBody"
	)
	public MessageBoardAttachment postMessageBoardThreadMessageBoardAttachment(
			@GraphQLName("message-board-thread-id") Long messageBoardThreadId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.
					postMessageBoardThreadMessageBoardAttachment(
						messageBoardThreadId, multipartBody));
	}

	@GraphQLInvokeDetached
	public void deleteMessageBoardMessage(
			@GraphQLName("message-board-message-id") Long messageBoardMessageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.deleteMessageBoardMessage(
					messageBoardMessageId));
	}

	@GraphQLInvokeDetached
	public MessageBoardMessage patchMessageBoardMessage(
			@GraphQLName("message-board-message-id") Long messageBoardMessageId,
			@GraphQLName("MessageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.patchMessageBoardMessage(
					messageBoardMessageId, messageBoardMessage));
	}

	@GraphQLInvokeDetached
	public MessageBoardMessage putMessageBoardMessage(
			@GraphQLName("message-board-message-id") Long messageBoardMessageId,
			@GraphQLName("MessageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.putMessageBoardMessage(
					messageBoardMessageId, messageBoardMessage));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardMessage postMessageBoardMessageMessageBoardMessage(
			@GraphQLName("message-board-message-id") Long messageBoardMessageId,
			@GraphQLName("MessageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.
					postMessageBoardMessageMessageBoardMessage(
						messageBoardMessageId, messageBoardMessage));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardMessage postMessageBoardThreadMessageBoardMessage(
			@GraphQLName("message-board-thread-id") Long messageBoardThreadId,
			@GraphQLName("MessageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.
					postMessageBoardThreadMessageBoardMessage(
						messageBoardThreadId, messageBoardMessage));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardSection postContentSpaceMessageBoardSection(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("MessageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.postContentSpaceMessageBoardSection(
					contentSpaceId, messageBoardSection));
	}

	@GraphQLInvokeDetached
	public void deleteMessageBoardSection(
			@GraphQLName("message-board-section-id") Long messageBoardSectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.deleteMessageBoardSection(
					messageBoardSectionId));
	}

	@GraphQLInvokeDetached
	public MessageBoardSection patchMessageBoardSection(
			@GraphQLName("message-board-section-id") Long messageBoardSectionId,
			@GraphQLName("MessageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.patchMessageBoardSection(
					messageBoardSectionId, messageBoardSection));
	}

	@GraphQLInvokeDetached
	public MessageBoardSection putMessageBoardSection(
			@GraphQLName("message-board-section-id") Long messageBoardSectionId,
			@GraphQLName("MessageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.putMessageBoardSection(
					messageBoardSectionId, messageBoardSection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardSection postMessageBoardSectionMessageBoardSection(
			@GraphQLName("message-board-section-id") Long messageBoardSectionId,
			@GraphQLName("MessageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.
					postMessageBoardSectionMessageBoardSection(
						messageBoardSectionId, messageBoardSection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardThread postContentSpaceMessageBoardThread(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("MessageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.postContentSpaceMessageBoardThread(
					contentSpaceId, messageBoardThread));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardThread postMessageBoardSectionMessageBoardThread(
			@GraphQLName("message-board-section-id") Long messageBoardSectionId,
			@GraphQLName("MessageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.
					postMessageBoardSectionMessageBoardThread(
						messageBoardSectionId, messageBoardThread));
	}

	@GraphQLInvokeDetached
	public void deleteMessageBoardThread(
			@GraphQLName("message-board-thread-id") Long messageBoardThreadId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.deleteMessageBoardThread(
					messageBoardThreadId));
	}

	@GraphQLInvokeDetached
	public MessageBoardThread patchMessageBoardThread(
			@GraphQLName("message-board-thread-id") Long messageBoardThreadId,
			@GraphQLName("MessageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.patchMessageBoardThread(
					messageBoardThreadId, messageBoardThread));
	}

	@GraphQLInvokeDetached
	public MessageBoardThread putMessageBoardThread(
			@GraphQLName("message-board-thread-id") Long messageBoardThreadId,
			@GraphQLName("MessageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.putMessageBoardThread(
					messageBoardThreadId, messageBoardThread));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postBlogPostingRating(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("Rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.postBlogPostingRating(
				blogPostingId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postDocumentRating(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("Rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.postDocumentRating(
				documentId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postKnowledgeBaseArticleRating(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			@GraphQLName("Rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.postKnowledgeBaseArticleRating(
				knowledgeBaseArticleId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postMessageBoardMessageRating(
			@GraphQLName("message-board-message-id") Long messageBoardMessageId,
			@GraphQLName("Rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.postMessageBoardMessageRating(
				messageBoardMessageId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postMessageBoardThreadRating(
			@GraphQLName("message-board-thread-id") Long messageBoardThreadId,
			@GraphQLName("Rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.postMessageBoardThreadRating(
				messageBoardThreadId, rating));
	}

	@GraphQLInvokeDetached
	public void deleteRating(@GraphQLName("rating-id") Long ratingId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.deleteRating(ratingId));
	}

	@GraphQLInvokeDetached
	public Rating putRating(
			@GraphQLName("rating-id") Long ratingId,
			@GraphQLName("Rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.putRating(ratingId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postStructuredContentRating(
			@GraphQLName("structured-content-id") Long structuredContentId,
			@GraphQLName("Rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.postStructuredContentRating(
				structuredContentId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent postContentSpaceStructuredContent(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("StructuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.postContentSpaceStructuredContent(
					contentSpaceId, structuredContent));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent postStructuredContentFolderStructuredContent(
			@GraphQLName("structured-content-folder-id") Long
				structuredContentFolderId,
			@GraphQLName("StructuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.
					postStructuredContentFolderStructuredContent(
						structuredContentFolderId, structuredContent));
	}

	@GraphQLInvokeDetached
	public void deleteStructuredContent(
			@GraphQLName("structured-content-id") Long structuredContentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.deleteStructuredContent(
					structuredContentId));
	}

	@GraphQLInvokeDetached
	public StructuredContent patchStructuredContent(
			@GraphQLName("structured-content-id") Long structuredContentId,
			@GraphQLName("StructuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.patchStructuredContent(
					structuredContentId, structuredContent));
	}

	@GraphQLInvokeDetached
	public StructuredContent putStructuredContent(
			@GraphQLName("structured-content-id") Long structuredContentId,
			@GraphQLName("StructuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.putStructuredContent(
					structuredContentId, structuredContent));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContentFolder postContentSpaceStructuredContentFolder(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("StructuredContentFolder") StructuredContentFolder
				structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.
					postContentSpaceStructuredContentFolder(
						contentSpaceId, structuredContentFolder));
	}

	@GraphQLInvokeDetached
	public void deleteStructuredContentFolder(
			@GraphQLName("structured-content-folder-id") Long
				structuredContentFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.deleteStructuredContentFolder(
					structuredContentFolderId));
	}

	@GraphQLInvokeDetached
	public StructuredContentFolder putStructuredContentFolder(
			@GraphQLName("structured-content-folder-id") Long
				structuredContentFolderId,
			@GraphQLName("StructuredContentFolder") StructuredContentFolder
				structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.putStructuredContentFolder(
					structuredContentFolderId, structuredContentFolder));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContentFolder
			postStructuredContentFolderStructuredContentFolder(
				@GraphQLName("structured-content-folder-id") Long
					structuredContentFolderId,
				@GraphQLName("StructuredContentFolder") StructuredContentFolder
					structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.
					postStructuredContentFolderStructuredContentFolder(
						structuredContentFolderId, structuredContentFolder));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
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

	private void _populateResourceContext(DocumentResource documentResource)
		throws Exception {

		documentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(FolderResource folderResource)
		throws Exception {

		folderResource.setContextCompany(
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

	private void _populateResourceContext(
			StructuredContentResource structuredContentResource)
		throws Exception {

		structuredContentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			StructuredContentFolderResource structuredContentFolderResource)
		throws Exception {

		structuredContentFolderResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<BlogPostingResource>
		_blogPostingResourceComponentServiceObjects;
	private static ComponentServiceObjects<BlogPostingImageResource>
		_blogPostingImageResourceComponentServiceObjects;
	private static ComponentServiceObjects<CommentResource>
		_commentResourceComponentServiceObjects;
	private static ComponentServiceObjects<DocumentResource>
		_documentResourceComponentServiceObjects;
	private static ComponentServiceObjects<FolderResource>
		_folderResourceComponentServiceObjects;
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
	private static ComponentServiceObjects<StructuredContentResource>
		_structuredContentResourceComponentServiceObjects;
	private static ComponentServiceObjects<StructuredContentFolderResource>
		_structuredContentFolderResourceComponentServiceObjects;

}