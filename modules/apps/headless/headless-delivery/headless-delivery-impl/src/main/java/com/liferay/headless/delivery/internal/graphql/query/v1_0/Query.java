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

package com.liferay.headless.delivery.internal.graphql.query.v1_0;

import com.liferay.headless.delivery.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.headless.delivery.dto.v1_0.ContentSetElement;
import com.liferay.headless.delivery.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
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
import com.liferay.headless.delivery.resource.v1_0.ContentSetElementResource;
import com.liferay.headless.delivery.resource.v1_0.ContentStructureResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardAttachmentResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardMessageResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardSectionResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardThreadResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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

	public static void setContentSetElementResourceComponentServiceObjects(
		ComponentServiceObjects<ContentSetElementResource>
			contentSetElementResourceComponentServiceObjects) {

		_contentSetElementResourceComponentServiceObjects =
			contentSetElementResourceComponentServiceObjects;
	}

	public static void setContentStructureResourceComponentServiceObjects(
		ComponentServiceObjects<ContentStructureResource>
			contentStructureResourceComponentServiceObjects) {

		_contentStructureResourceComponentServiceObjects =
			contentStructureResourceComponentServiceObjects;
	}

	public static void setDocumentResourceComponentServiceObjects(
		ComponentServiceObjects<DocumentResource>
			documentResourceComponentServiceObjects) {

		_documentResourceComponentServiceObjects =
			documentResourceComponentServiceObjects;
	}

	public static void setDocumentFolderResourceComponentServiceObjects(
		ComponentServiceObjects<DocumentFolderResource>
			documentFolderResourceComponentServiceObjects) {

		_documentFolderResourceComponentServiceObjects =
			documentFolderResourceComponentServiceObjects;
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

	@GraphQLField
	public BlogPosting getBlogPosting(
			@GraphQLName("blogPostingId") Long blogPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.getBlogPosting(
				blogPostingId));
	}

	@GraphQLField
	public Rating getBlogPostingMyRating(
			@GraphQLName("blogPostingId") Long blogPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.getBlogPostingMyRating(
				blogPostingId));
	}

	@GraphQLField
	public java.util.Collection<BlogPosting> getSiteBlogPostingsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> {
				Page paginationPage =
					blogPostingResource.getSiteBlogPostingsPage(
						siteId, search, filter, Pagination.of(page, pageSize),
						sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public BlogPostingImage getBlogPostingImage(
			@GraphQLName("blogPostingImageId") Long blogPostingImageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.getBlogPostingImage(
					blogPostingImageId));
	}

	@GraphQLField
	public java.util.Collection<BlogPostingImage> getSiteBlogPostingImagesPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource -> {
				Page paginationPage =
					blogPostingImageResource.getSiteBlogPostingImagesPage(
						siteId, search, filter, Pagination.of(page, pageSize),
						sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Comment> getBlogPostingCommentsPage(
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> {
				Page paginationPage =
					commentResource.getBlogPostingCommentsPage(
						blogPostingId, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Comment getComment(@GraphQLName("commentId") Long commentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.getComment(commentId));
	}

	@GraphQLField
	public java.util.Collection<Comment> getCommentCommentsPage(
			@GraphQLName("parentCommentId") Long parentCommentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> {
				Page paginationPage = commentResource.getCommentCommentsPage(
					parentCommentId, search, filter,
					Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Comment> getDocumentCommentsPage(
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> {
				Page paginationPage = commentResource.getDocumentCommentsPage(
					documentId, search, filter, Pagination.of(page, pageSize),
					sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Comment> getStructuredContentCommentsPage(
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> {
				Page paginationPage =
					commentResource.getStructuredContentCommentsPage(
						structuredContentId, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<ContentSetElement>
			getContentSetContentSetElementsPage(
				@GraphQLName("contentSetId") Long contentSetId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentSetElementResource -> {
				Page paginationPage =
					contentSetElementResource.
						getContentSetContentSetElementsPage(
							contentSetId, Pagination.of(page, pageSize));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<ContentSetElement>
			getSiteContentSetByKeyContentSetElementsPage(
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("key") String key,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentSetElementResource -> {
				Page paginationPage =
					contentSetElementResource.
						getSiteContentSetByKeyContentSetElementsPage(
							siteId, key, Pagination.of(page, pageSize));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<ContentSetElement>
			getSiteContentSetByUuidContentSetElementsPage(
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("uuid") String uuid,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentSetElementResource -> {
				Page paginationPage =
					contentSetElementResource.
						getSiteContentSetByUuidContentSetElementsPage(
							siteId, uuid, Pagination.of(page, pageSize));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public ContentStructure getContentStructure(
			@GraphQLName("contentStructureId") Long contentStructureId)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentStructureResource ->
				contentStructureResource.getContentStructure(
					contentStructureId));
	}

	@GraphQLField
	public java.util.Collection<ContentStructure> getSiteContentStructuresPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentStructureResource -> {
				Page paginationPage =
					contentStructureResource.getSiteContentStructuresPage(
						siteId, search, filter, Pagination.of(page, pageSize),
						sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Document> getDocumentFolderDocumentsPage(
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> {
				Page paginationPage =
					documentResource.getDocumentFolderDocumentsPage(
						documentFolderId, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Document getDocument(@GraphQLName("documentId") Long documentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.getDocument(documentId));
	}

	@GraphQLField
	public Rating getDocumentMyRating(
			@GraphQLName("documentId") Long documentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.getDocumentMyRating(
				documentId));
	}

	@GraphQLField
	public java.util.Collection<Document> getSiteDocumentsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> {
				Page paginationPage = documentResource.getSiteDocumentsPage(
					siteId, flatten, search, filter,
					Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public DocumentFolder getDocumentFolder(
			@GraphQLName("documentFolderId") Long documentFolderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource -> documentFolderResource.getDocumentFolder(
				documentFolderId));
	}

	@GraphQLField
	public java.util.Collection<DocumentFolder>
			getDocumentFolderDocumentFoldersPage(
				@GraphQLName("parentDocumentFolderId") Long
					parentDocumentFolderId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource -> {
				Page paginationPage =
					documentFolderResource.getDocumentFolderDocumentFoldersPage(
						parentDocumentFolderId, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<DocumentFolder> getSiteDocumentFoldersPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource -> {
				Page paginationPage =
					documentFolderResource.getSiteDocumentFoldersPage(
						siteId, flatten, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.getKnowledgeBaseArticle(
					knowledgeBaseArticleId));
	}

	@GraphQLField
	public Rating getKnowledgeBaseArticleMyRating(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.getKnowledgeBaseArticleMyRating(
					knowledgeBaseArticleId));
	}

	@GraphQLField
	public java.util.Collection<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				@GraphQLName("parentKnowledgeBaseArticleId") Long
					parentKnowledgeBaseArticleId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
							parentKnowledgeBaseArticleId, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				@GraphQLName("knowledgeBaseFolderId") Long
					knowledgeBaseFolderId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
							knowledgeBaseFolderId, flatten, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<KnowledgeBaseArticle>
			getSiteKnowledgeBaseArticlesPage(
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getSiteKnowledgeBaseArticlesPage(
							siteId, flatten, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<KnowledgeBaseAttachment>
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
				@GraphQLName("knowledgeBaseArticleId") Long
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
	public KnowledgeBaseAttachment getKnowledgeBaseAttachment(
			@GraphQLName("knowledgeBaseAttachmentId") Long
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
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.getKnowledgeBaseFolder(
					knowledgeBaseFolderId));
	}

	@GraphQLField
	public java.util.Collection<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				@GraphQLName("parentKnowledgeBaseFolderId") Long
					parentKnowledgeBaseFolderId,
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
							parentKnowledgeBaseFolderId,
							Pagination.of(page, pageSize));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<KnowledgeBaseFolder>
			getSiteKnowledgeBaseFoldersPage(
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource -> {
				Page paginationPage =
					knowledgeBaseFolderResource.getSiteKnowledgeBaseFoldersPage(
						siteId, Pagination.of(page, pageSize));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public MessageBoardAttachment getMessageBoardAttachment(
			@GraphQLName("messageBoardAttachmentId") Long
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
	public java.util.Collection<MessageBoardAttachment>
			getMessageBoardMessageMessageBoardAttachmentsPage(
				@GraphQLName("messageBoardMessageId") Long
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
	public java.util.Collection<MessageBoardAttachment>
			getMessageBoardThreadMessageBoardAttachmentsPage(
				@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
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
	public MessageBoardMessage getMessageBoardMessage(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.getMessageBoardMessage(
					messageBoardMessageId));
	}

	@GraphQLField
	public Rating getMessageBoardMessageMyRating(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.getMessageBoardMessageMyRating(
					messageBoardMessageId));
	}

	@GraphQLField
	public java.util.Collection<MessageBoardMessage>
			getMessageBoardMessageMessageBoardMessagesPage(
				@GraphQLName("parentMessageBoardMessageId") Long
					parentMessageBoardMessageId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource -> {
				Page paginationPage =
					messageBoardMessageResource.
						getMessageBoardMessageMessageBoardMessagesPage(
							parentMessageBoardMessageId, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<MessageBoardMessage>
			getMessageBoardThreadMessageBoardMessagesPage(
				@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource -> {
				Page paginationPage =
					messageBoardMessageResource.
						getMessageBoardThreadMessageBoardMessagesPage(
							messageBoardThreadId, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public MessageBoardSection getMessageBoardSection(
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.getMessageBoardSection(
					messageBoardSectionId));
	}

	@GraphQLField
	public java.util.Collection<MessageBoardSection>
			getMessageBoardSectionMessageBoardSectionsPage(
				@GraphQLName("parentMessageBoardSectionId") Long
					parentMessageBoardSectionId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource -> {
				Page paginationPage =
					messageBoardSectionResource.
						getMessageBoardSectionMessageBoardSectionsPage(
							parentMessageBoardSectionId, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<MessageBoardSection>
			getSiteMessageBoardSectionsPage(
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource -> {
				Page paginationPage =
					messageBoardSectionResource.getSiteMessageBoardSectionsPage(
						siteId, flatten, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<MessageBoardThread>
			getMessageBoardSectionMessageBoardThreadsPage(
				@GraphQLName("messageBoardSectionId") Long
					messageBoardSectionId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource -> {
				Page paginationPage =
					messageBoardThreadResource.
						getMessageBoardSectionMessageBoardThreadsPage(
							messageBoardSectionId, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public MessageBoardThread getMessageBoardThread(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.getMessageBoardThread(
					messageBoardThreadId));
	}

	@GraphQLField
	public Rating getMessageBoardThreadMyRating(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.getMessageBoardThreadMyRating(
					messageBoardThreadId));
	}

	@GraphQLField
	public java.util.Collection<MessageBoardThread>
			getSiteMessageBoardThreadsPage(
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource -> {
				Page paginationPage =
					messageBoardThreadResource.getSiteMessageBoardThreadsPage(
						siteId, flatten, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<StructuredContent>
			getContentStructureStructuredContentsPage(
				@GraphQLName("contentStructureId") Long contentStructureId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource -> {
				Page paginationPage =
					structuredContentResource.
						getContentStructureStructuredContentsPage(
							contentStructureId, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<StructuredContent>
			getSiteStructuredContentsPage(
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource -> {
				Page paginationPage =
					structuredContentResource.getSiteStructuredContentsPage(
						siteId, flatten, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public StructuredContent getSiteStructuredContentByKey(
			@GraphQLName("siteId") Long siteId, @GraphQLName("key") String key)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.getSiteStructuredContentByKey(
					siteId, key));
	}

	@GraphQLField
	public StructuredContent getSiteStructuredContentByUuid(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("uuid") String uuid)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.getSiteStructuredContentByUuid(
					siteId, uuid));
	}

	@GraphQLField
	public java.util.Collection<StructuredContent>
			getStructuredContentFolderStructuredContentsPage(
				@GraphQLName("structuredContentFolderId") Long
					structuredContentFolderId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource -> {
				Page paginationPage =
					structuredContentResource.
						getStructuredContentFolderStructuredContentsPage(
							structuredContentFolderId, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public StructuredContent getStructuredContent(
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.getStructuredContent(
					structuredContentId));
	}

	@GraphQLField
	public Rating getStructuredContentMyRating(
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.getStructuredContentMyRating(
					structuredContentId));
	}

	@GraphQLField
	public String getStructuredContentRenderedContentTemplate(
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("templateId") Long templateId)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.
					getStructuredContentRenderedContentTemplate(
						structuredContentId, templateId));
	}

	@GraphQLField
	public java.util.Collection<StructuredContentFolder>
			getSiteStructuredContentFoldersPage(
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource -> {
				Page paginationPage =
					structuredContentFolderResource.
						getSiteStructuredContentFoldersPage(
							siteId, flatten, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<StructuredContentFolder>
			getStructuredContentFolderStructuredContentFoldersPage(
				@GraphQLName("parentStructuredContentFolderId") Long
					parentStructuredContentFolderId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource -> {
				Page paginationPage =
					structuredContentFolderResource.
						getStructuredContentFolderStructuredContentFoldersPage(
							parentStructuredContentFolderId, search, filter,
							Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public StructuredContentFolder getStructuredContentFolder(
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.getStructuredContentFolder(
					structuredContentFolderId));
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

		blogPostingResource.setContextAcceptLanguage(_acceptLanguage);
		blogPostingResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			BlogPostingImageResource blogPostingImageResource)
		throws Exception {

		blogPostingImageResource.setContextAcceptLanguage(_acceptLanguage);
		blogPostingImageResource.setContextCompany(_company);
	}

	private void _populateResourceContext(CommentResource commentResource)
		throws Exception {

		commentResource.setContextAcceptLanguage(_acceptLanguage);
		commentResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			ContentSetElementResource contentSetElementResource)
		throws Exception {

		contentSetElementResource.setContextAcceptLanguage(_acceptLanguage);
		contentSetElementResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			ContentStructureResource contentStructureResource)
		throws Exception {

		contentStructureResource.setContextAcceptLanguage(_acceptLanguage);
		contentStructureResource.setContextCompany(_company);
	}

	private void _populateResourceContext(DocumentResource documentResource)
		throws Exception {

		documentResource.setContextAcceptLanguage(_acceptLanguage);
		documentResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			DocumentFolderResource documentFolderResource)
		throws Exception {

		documentFolderResource.setContextAcceptLanguage(_acceptLanguage);
		documentFolderResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			KnowledgeBaseArticleResource knowledgeBaseArticleResource)
		throws Exception {

		knowledgeBaseArticleResource.setContextAcceptLanguage(_acceptLanguage);
		knowledgeBaseArticleResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			KnowledgeBaseAttachmentResource knowledgeBaseAttachmentResource)
		throws Exception {

		knowledgeBaseAttachmentResource.setContextAcceptLanguage(
			_acceptLanguage);
		knowledgeBaseAttachmentResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			KnowledgeBaseFolderResource knowledgeBaseFolderResource)
		throws Exception {

		knowledgeBaseFolderResource.setContextAcceptLanguage(_acceptLanguage);
		knowledgeBaseFolderResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			MessageBoardAttachmentResource messageBoardAttachmentResource)
		throws Exception {

		messageBoardAttachmentResource.setContextAcceptLanguage(
			_acceptLanguage);
		messageBoardAttachmentResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			MessageBoardMessageResource messageBoardMessageResource)
		throws Exception {

		messageBoardMessageResource.setContextAcceptLanguage(_acceptLanguage);
		messageBoardMessageResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			MessageBoardSectionResource messageBoardSectionResource)
		throws Exception {

		messageBoardSectionResource.setContextAcceptLanguage(_acceptLanguage);
		messageBoardSectionResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			MessageBoardThreadResource messageBoardThreadResource)
		throws Exception {

		messageBoardThreadResource.setContextAcceptLanguage(_acceptLanguage);
		messageBoardThreadResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			StructuredContentResource structuredContentResource)
		throws Exception {

		structuredContentResource.setContextAcceptLanguage(_acceptLanguage);
		structuredContentResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			StructuredContentFolderResource structuredContentFolderResource)
		throws Exception {

		structuredContentFolderResource.setContextAcceptLanguage(
			_acceptLanguage);
		structuredContentFolderResource.setContextCompany(_company);
	}

	private static ComponentServiceObjects<BlogPostingResource>
		_blogPostingResourceComponentServiceObjects;
	private static ComponentServiceObjects<BlogPostingImageResource>
		_blogPostingImageResourceComponentServiceObjects;
	private static ComponentServiceObjects<CommentResource>
		_commentResourceComponentServiceObjects;
	private static ComponentServiceObjects<ContentSetElementResource>
		_contentSetElementResourceComponentServiceObjects;
	private static ComponentServiceObjects<ContentStructureResource>
		_contentStructureResourceComponentServiceObjects;
	private static ComponentServiceObjects<DocumentResource>
		_documentResourceComponentServiceObjects;
	private static ComponentServiceObjects<DocumentFolderResource>
		_documentFolderResourceComponentServiceObjects;
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
	private static ComponentServiceObjects<StructuredContentResource>
		_structuredContentResourceComponentServiceObjects;
	private static ComponentServiceObjects<StructuredContentFolderResource>
		_structuredContentFolderResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private Company _company;

}