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
import com.liferay.headless.delivery.dto.v1_0.WikiNode;
import com.liferay.headless.delivery.dto.v1_0.WikiPage;
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
import com.liferay.headless.delivery.resource.v1_0.WikiNodeResource;
import com.liferay.headless.delivery.resource.v1_0.WikiPageResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	public static void setWikiNodeResourceComponentServiceObjects(
		ComponentServiceObjects<WikiNodeResource>
			wikiNodeResourceComponentServiceObjects) {

		_wikiNodeResourceComponentServiceObjects =
			wikiNodeResourceComponentServiceObjects;
	}

	public static void setWikiPageResourceComponentServiceObjects(
		ComponentServiceObjects<WikiPageResource>
			wikiPageResourceComponentServiceObjects) {

		_wikiPageResourceComponentServiceObjects =
			wikiPageResourceComponentServiceObjects;
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
	public BlogPostingPage getSiteBlogPostingsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> new BlogPostingPage(
				blogPostingResource.getSiteBlogPostingsPage(
					siteId, search,
					_filterBiFunction.apply(blogPostingResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(blogPostingResource, sortsString))));
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
	public BlogPostingImagePage getSiteBlogPostingImagesPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource -> new BlogPostingImagePage(
				blogPostingImageResource.getSiteBlogPostingImagesPage(
					siteId, search,
					_filterBiFunction.apply(
						blogPostingImageResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						blogPostingImageResource, sortsString))));
	}

	@GraphQLField
	public CommentPage getBlogPostingCommentsPage(
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> new CommentPage(
				commentResource.getBlogPostingCommentsPage(
					blogPostingId, search,
					_filterBiFunction.apply(commentResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(commentResource, sortsString))));
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
	public CommentPage getCommentCommentsPage(
			@GraphQLName("parentCommentId") Long parentCommentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> new CommentPage(
				commentResource.getCommentCommentsPage(
					parentCommentId, search,
					_filterBiFunction.apply(commentResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(commentResource, sortsString))));
	}

	@GraphQLField
	public CommentPage getDocumentCommentsPage(
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> new CommentPage(
				commentResource.getDocumentCommentsPage(
					documentId, search,
					_filterBiFunction.apply(commentResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(commentResource, sortsString))));
	}

	@GraphQLField
	public CommentPage getStructuredContentCommentsPage(
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> new CommentPage(
				commentResource.getStructuredContentCommentsPage(
					structuredContentId, search,
					_filterBiFunction.apply(commentResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(commentResource, sortsString))));
	}

	@GraphQLField
	public ContentSetElementPage getContentSetContentSetElementsPage(
			@GraphQLName("contentSetId") Long contentSetId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentSetElementResource -> new ContentSetElementPage(
				contentSetElementResource.getContentSetContentSetElementsPage(
					contentSetId, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ContentSetElementPage getSiteContentSetByKeyContentSetElementsPage(
			@GraphQLName("siteId") Long siteId, @GraphQLName("key") String key,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentSetElementResource -> new ContentSetElementPage(
				contentSetElementResource.
					getSiteContentSetByKeyContentSetElementsPage(
						siteId, key, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ContentSetElementPage getSiteContentSetByUuidContentSetElementsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("uuid") String uuid,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentSetElementResource -> new ContentSetElementPage(
				contentSetElementResource.
					getSiteContentSetByUuidContentSetElementsPage(
						siteId, uuid, Pagination.of(page, pageSize))));
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
	public ContentStructurePage getSiteContentStructuresPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentStructureResource -> new ContentStructurePage(
				contentStructureResource.getSiteContentStructuresPage(
					siteId, search,
					_filterBiFunction.apply(
						contentStructureResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						contentStructureResource, sortsString))));
	}

	@GraphQLField
	public DocumentPage getDocumentFolderDocumentsPage(
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> new DocumentPage(
				documentResource.getDocumentFolderDocumentsPage(
					documentFolderId, search,
					_filterBiFunction.apply(documentResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(documentResource, sortsString))));
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
	public DocumentPage getSiteDocumentsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> new DocumentPage(
				documentResource.getSiteDocumentsPage(
					siteId, flatten, search,
					_filterBiFunction.apply(documentResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(documentResource, sortsString))));
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
	public DocumentFolderPage getDocumentFolderDocumentFoldersPage(
			@GraphQLName("parentDocumentFolderId") Long parentDocumentFolderId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource -> new DocumentFolderPage(
				documentFolderResource.getDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, search,
					_filterBiFunction.apply(
						documentFolderResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						documentFolderResource, sortsString))));
	}

	@GraphQLField
	public DocumentFolderPage getSiteDocumentFoldersPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource -> new DocumentFolderPage(
				documentFolderResource.getSiteDocumentFoldersPage(
					siteId, flatten, search,
					_filterBiFunction.apply(
						documentFolderResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						documentFolderResource, sortsString))));
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
	public KnowledgeBaseArticlePage
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				@GraphQLName("parentKnowledgeBaseArticleId") Long
					parentKnowledgeBaseArticleId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> new KnowledgeBaseArticlePage(
				knowledgeBaseArticleResource.
					getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
						parentKnowledgeBaseArticleId, search,
						_filterBiFunction.apply(
							knowledgeBaseArticleResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							knowledgeBaseArticleResource, sortsString))));
	}

	@GraphQLField
	public KnowledgeBaseArticlePage
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				@GraphQLName("knowledgeBaseFolderId") Long
					knowledgeBaseFolderId,
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> new KnowledgeBaseArticlePage(
				knowledgeBaseArticleResource.
					getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
						knowledgeBaseFolderId, flatten, search,
						_filterBiFunction.apply(
							knowledgeBaseArticleResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							knowledgeBaseArticleResource, sortsString))));
	}

	@GraphQLField
	public KnowledgeBaseArticlePage getSiteKnowledgeBaseArticlesPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> new KnowledgeBaseArticlePage(
				knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
					siteId, flatten, search,
					_filterBiFunction.apply(
						knowledgeBaseArticleResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						knowledgeBaseArticleResource, sortsString))));
	}

	@GraphQLField
	public KnowledgeBaseAttachmentPage
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
				@GraphQLName("knowledgeBaseArticleId") Long
					knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseAttachmentResource -> new KnowledgeBaseAttachmentPage(
				knowledgeBaseAttachmentResource.
					getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
						knowledgeBaseArticleId)));
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
	public KnowledgeBaseFolderPage
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				@GraphQLName("parentKnowledgeBaseFolderId") Long
					parentKnowledgeBaseFolderId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource -> new KnowledgeBaseFolderPage(
				knowledgeBaseFolderResource.
					getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
						parentKnowledgeBaseFolderId,
						Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public KnowledgeBaseFolderPage getSiteKnowledgeBaseFoldersPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource -> new KnowledgeBaseFolderPage(
				knowledgeBaseFolderResource.getSiteKnowledgeBaseFoldersPage(
					siteId, Pagination.of(page, pageSize))));
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
	public MessageBoardAttachmentPage
			getMessageBoardMessageMessageBoardAttachmentsPage(
				@GraphQLName("messageBoardMessageId") Long
					messageBoardMessageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource -> new MessageBoardAttachmentPage(
				messageBoardAttachmentResource.
					getMessageBoardMessageMessageBoardAttachmentsPage(
						messageBoardMessageId)));
	}

	@GraphQLField
	public MessageBoardAttachmentPage
			getMessageBoardThreadMessageBoardAttachmentsPage(
				@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource -> new MessageBoardAttachmentPage(
				messageBoardAttachmentResource.
					getMessageBoardThreadMessageBoardAttachmentsPage(
						messageBoardThreadId)));
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
	public MessageBoardMessagePage
			getMessageBoardMessageMessageBoardMessagesPage(
				@GraphQLName("parentMessageBoardMessageId") Long
					parentMessageBoardMessageId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource -> new MessageBoardMessagePage(
				messageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, search,
						_filterBiFunction.apply(
							messageBoardMessageResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							messageBoardMessageResource, sortsString))));
	}

	@GraphQLField
	public MessageBoardMessagePage
			getMessageBoardThreadMessageBoardMessagesPage(
				@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource -> new MessageBoardMessagePage(
				messageBoardMessageResource.
					getMessageBoardThreadMessageBoardMessagesPage(
						messageBoardThreadId, search,
						_filterBiFunction.apply(
							messageBoardMessageResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							messageBoardMessageResource, sortsString))));
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
	public MessageBoardSectionPage
			getMessageBoardSectionMessageBoardSectionsPage(
				@GraphQLName("parentMessageBoardSectionId") Long
					parentMessageBoardSectionId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource -> new MessageBoardSectionPage(
				messageBoardSectionResource.
					getMessageBoardSectionMessageBoardSectionsPage(
						parentMessageBoardSectionId, search,
						_filterBiFunction.apply(
							messageBoardSectionResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							messageBoardSectionResource, sortsString))));
	}

	@GraphQLField
	public MessageBoardSectionPage getSiteMessageBoardSectionsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource -> new MessageBoardSectionPage(
				messageBoardSectionResource.getSiteMessageBoardSectionsPage(
					siteId, flatten, search,
					_filterBiFunction.apply(
						messageBoardSectionResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						messageBoardSectionResource, sortsString))));
	}

	@GraphQLField
	public MessageBoardThreadPage getMessageBoardSectionMessageBoardThreadsPage(
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource -> new MessageBoardThreadPage(
				messageBoardThreadResource.
					getMessageBoardSectionMessageBoardThreadsPage(
						messageBoardSectionId, search,
						_filterBiFunction.apply(
							messageBoardThreadResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							messageBoardThreadResource, sortsString))));
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
	public MessageBoardThreadPage getSiteMessageBoardThreadsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource -> new MessageBoardThreadPage(
				messageBoardThreadResource.getSiteMessageBoardThreadsPage(
					siteId, flatten, search,
					_filterBiFunction.apply(
						messageBoardThreadResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						messageBoardThreadResource, sortsString))));
	}

	@GraphQLField
	public StructuredContentPage getContentStructureStructuredContentsPage(
			@GraphQLName("contentStructureId") Long contentStructureId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource -> new StructuredContentPage(
				structuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, search,
						_filterBiFunction.apply(
							structuredContentResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							structuredContentResource, sortsString))));
	}

	@GraphQLField
	public StructuredContentPage getSiteStructuredContentsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource -> new StructuredContentPage(
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, flatten, search,
					_filterBiFunction.apply(
						structuredContentResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						structuredContentResource, sortsString))));
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
	public StructuredContentPage
			getStructuredContentFolderStructuredContentsPage(
				@GraphQLName("structuredContentFolderId") Long
					structuredContentFolderId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource -> new StructuredContentPage(
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, search,
						_filterBiFunction.apply(
							structuredContentResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							structuredContentResource, sortsString))));
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
	public StructuredContentFolderPage getSiteStructuredContentFoldersPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource -> new StructuredContentFolderPage(
				structuredContentFolderResource.
					getSiteStructuredContentFoldersPage(
						siteId, flatten, search,
						_filterBiFunction.apply(
							structuredContentFolderResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							structuredContentFolderResource, sortsString))));
	}

	@GraphQLField
	public StructuredContentFolderPage
			getStructuredContentFolderStructuredContentFoldersPage(
				@GraphQLName("parentStructuredContentFolderId") Long
					parentStructuredContentFolderId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource -> new StructuredContentFolderPage(
				structuredContentFolderResource.
					getStructuredContentFolderStructuredContentFoldersPage(
						parentStructuredContentFolderId, search,
						_filterBiFunction.apply(
							structuredContentFolderResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							structuredContentFolderResource, sortsString))));
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

	@GraphQLField
	public WikiNodePage getSiteWikiNodesPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> new WikiNodePage(
				wikiNodeResource.getSiteWikiNodesPage(
					siteId, search,
					_filterBiFunction.apply(wikiNodeResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(wikiNodeResource, sortsString))));
	}

	@GraphQLField
	public WikiNode getWikiNode(@GraphQLName("wikiNodeId") Long wikiNodeId)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.getWikiNode(wikiNodeId));
	}

	@GraphQLField
	public WikiPagePage getWikiNodeWikiPagesPage(
			@GraphQLName("wikiNodeId") Long wikiNodeId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> new WikiPagePage(
				wikiPageResource.getWikiNodeWikiPagesPage(
					wikiNodeId, search,
					_filterBiFunction.apply(wikiPageResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(wikiPageResource, sortsString))));
	}

	@GraphQLField
	public WikiPage getWikiPage(@GraphQLName("wikiPageId") Long wikiPageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.getWikiPage(wikiPageId));
	}

	@GraphQLTypeExtension(KnowledgeBaseAttachment.class)
	public class GetKnowledgeBaseArticleKnowledgeBaseArticlesPageTypeExtension {

		private KnowledgeBaseAttachment _knowledgeBaseAttachment;

		public GetKnowledgeBaseArticleKnowledgeBaseArticlesPageTypeExtension(
			KnowledgeBaseAttachment knowledgeBaseAttachment) {

			_knowledgeBaseAttachment = knowledgeBaseAttachment;
		}

		@GraphQLField
		public KnowledgeBaseArticlePage
				getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					@GraphQLName("search") String search,
					@GraphQLName("filter") String filterString,
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page,
					@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_knowledgeBaseArticleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				knowledgeBaseArticleResource -> new KnowledgeBaseArticlePage(
					knowledgeBaseArticleResource.
						getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
							_knowledgeBaseAttachment.getId(), search,
							_filterBiFunction.apply(
								knowledgeBaseArticleResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								knowledgeBaseArticleResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(Document.class)
	public class GetDocumentFolderTypeExtension {

		private Document _document;

		public GetDocumentFolderTypeExtension(Document document) {
			_document = document;
		}

		@GraphQLField
		public DocumentFolder getDocumentFolder() throws Exception {
			return _applyComponentServiceObjects(
				_documentFolderResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				documentFolderResource ->
					documentFolderResource.getDocumentFolder(
						_document.getId()));
		}

	}

	@GraphQLTypeExtension(MessageBoardThread.class)
	public class GetMessageBoardThreadMessageBoardAttachmentsPageTypeExtension {

		private MessageBoardThread _messageBoardThread;

		public GetMessageBoardThreadMessageBoardAttachmentsPageTypeExtension(
			MessageBoardThread messageBoardThread) {

			_messageBoardThread = messageBoardThread;
		}

		@GraphQLField
		public MessageBoardAttachmentPage
				getMessageBoardThreadMessageBoardAttachmentsPage()
			throws Exception {

			return _applyComponentServiceObjects(
				_messageBoardAttachmentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				messageBoardAttachmentResource ->
					new MessageBoardAttachmentPage(
						messageBoardAttachmentResource.
							getMessageBoardThreadMessageBoardAttachmentsPage(
								_messageBoardThread.getId())));
		}

	}

	@GraphQLTypeExtension(StructuredContent.class)
	public class GetStructuredContentRenderedContentTemplateTypeExtension {

		private StructuredContent _structuredContent;

		public GetStructuredContentRenderedContentTemplateTypeExtension(
			StructuredContent structuredContent) {

			_structuredContent = structuredContent;
		}

		@GraphQLField
		public String getStructuredContentRenderedContentTemplate(
				@GraphQLName("templateId") Long templateId)
			throws Exception {

			return _applyComponentServiceObjects(
				_structuredContentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				structuredContentResource ->
					structuredContentResource.
						getStructuredContentRenderedContentTemplate(
							_structuredContent.getId(), templateId));
		}

	}

	@GraphQLTypeExtension(KnowledgeBaseFolder.class)
	public class GetKnowledgeBaseFolderKnowledgeBaseFoldersPageTypeExtension {

		private KnowledgeBaseFolder _knowledgeBaseFolder;

		public GetKnowledgeBaseFolderKnowledgeBaseFoldersPageTypeExtension(
			KnowledgeBaseFolder knowledgeBaseFolder) {

			_knowledgeBaseFolder = knowledgeBaseFolder;
		}

		@GraphQLField
		public KnowledgeBaseFolderPage
				getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_knowledgeBaseFolderResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				knowledgeBaseFolderResource -> new KnowledgeBaseFolderPage(
					knowledgeBaseFolderResource.
						getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
							_knowledgeBaseFolder.getId(),
							Pagination.of(page, pageSize))));
		}

	}

	@GraphQLTypeExtension(Document.class)
	public class GetDocumentMyRatingTypeExtension {

		private Document _document;

		public GetDocumentMyRatingTypeExtension(Document document) {
			_document = document;
		}

		@GraphQLField
		public Rating getDocumentMyRating() throws Exception {
			return _applyComponentServiceObjects(
				_documentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				documentResource -> documentResource.getDocumentMyRating(
					_document.getId()));
		}

	}

	@GraphQLTypeExtension(ContentStructure.class)
	public class GetContentStructureStructuredContentsPageTypeExtension {

		private ContentStructure _contentStructure;

		public GetContentStructureStructuredContentsPageTypeExtension(
			ContentStructure contentStructure) {

			_contentStructure = contentStructure;
		}

		@GraphQLField
		public StructuredContentPage getContentStructureStructuredContentsPage(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_structuredContentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				structuredContentResource -> new StructuredContentPage(
					structuredContentResource.
						getContentStructureStructuredContentsPage(
							_contentStructure.getId(), search,
							_filterBiFunction.apply(
								structuredContentResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								structuredContentResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(MessageBoardMessage.class)
	public class
		GetMessageBoardMessageMessageBoardAttachmentsPageTypeExtension {

		private MessageBoardMessage _messageBoardMessage;

		public GetMessageBoardMessageMessageBoardAttachmentsPageTypeExtension(
			MessageBoardMessage messageBoardMessage) {

			_messageBoardMessage = messageBoardMessage;
		}

		@GraphQLField
		public MessageBoardAttachmentPage
				getMessageBoardMessageMessageBoardAttachmentsPage()
			throws Exception {

			return _applyComponentServiceObjects(
				_messageBoardAttachmentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				messageBoardAttachmentResource ->
					new MessageBoardAttachmentPage(
						messageBoardAttachmentResource.
							getMessageBoardMessageMessageBoardAttachmentsPage(
								_messageBoardMessage.getId())));
		}

	}

	@GraphQLTypeExtension(BlogPosting.class)
	public class GetBlogPostingCommentsPageTypeExtension {

		private BlogPosting _blogPosting;

		public GetBlogPostingCommentsPageTypeExtension(
			BlogPosting blogPosting) {

			_blogPosting = blogPosting;
		}

		@GraphQLField
		public CommentPage getBlogPostingCommentsPage(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_commentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				commentResource -> new CommentPage(
					commentResource.getBlogPostingCommentsPage(
						_blogPosting.getId(), search,
						_filterBiFunction.apply(commentResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(commentResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(DocumentFolder.class)
	public class GetDocumentFolderDocumentsPageTypeExtension {

		private DocumentFolder _documentFolder;

		public GetDocumentFolderDocumentsPageTypeExtension(
			DocumentFolder documentFolder) {

			_documentFolder = documentFolder;
		}

		@GraphQLField
		public DocumentPage getDocumentFolderDocumentsPage(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_documentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				documentResource -> new DocumentPage(
					documentResource.getDocumentFolderDocumentsPage(
						_documentFolder.getId(), search,
						_filterBiFunction.apply(documentResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							documentResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(StructuredContent.class)
	public class GetStructuredContentCommentsPageTypeExtension {

		private StructuredContent _structuredContent;

		public GetStructuredContentCommentsPageTypeExtension(
			StructuredContent structuredContent) {

			_structuredContent = structuredContent;
		}

		@GraphQLField
		public CommentPage getStructuredContentCommentsPage(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_commentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				commentResource -> new CommentPage(
					commentResource.getStructuredContentCommentsPage(
						_structuredContent.getId(), search,
						_filterBiFunction.apply(commentResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(commentResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(WikiNode.class)
	public class GetWikiNodeWikiPagesPageTypeExtension {

		private WikiNode _wikiNode;

		public GetWikiNodeWikiPagesPageTypeExtension(WikiNode wikiNode) {
			_wikiNode = wikiNode;
		}

		@GraphQLField
		public WikiPagePage getWikiNodeWikiPagesPage(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_wikiPageResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				wikiPageResource -> new WikiPagePage(
					wikiPageResource.getWikiNodeWikiPagesPage(
						_wikiNode.getId(), search,
						_filterBiFunction.apply(wikiPageResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							wikiPageResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(KnowledgeBaseArticle.class)
	public class GetKnowledgeBaseArticleMyRatingTypeExtension {

		private KnowledgeBaseArticle _knowledgeBaseArticle;

		public GetKnowledgeBaseArticleMyRatingTypeExtension(
			KnowledgeBaseArticle knowledgeBaseArticle) {

			_knowledgeBaseArticle = knowledgeBaseArticle;
		}

		@GraphQLField
		public Rating getKnowledgeBaseArticleMyRating() throws Exception {
			return _applyComponentServiceObjects(
				_knowledgeBaseArticleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				knowledgeBaseArticleResource ->
					knowledgeBaseArticleResource.
						getKnowledgeBaseArticleMyRating(
							_knowledgeBaseArticle.getId()));
		}

	}

	@GraphQLTypeExtension(MessageBoardMessage.class)
	public class GetMessageBoardThreadTypeExtension {

		private MessageBoardMessage _messageBoardMessage;

		public GetMessageBoardThreadTypeExtension(
			MessageBoardMessage messageBoardMessage) {

			_messageBoardMessage = messageBoardMessage;
		}

		@GraphQLField
		public MessageBoardThread getMessageBoardThread() throws Exception {
			return _applyComponentServiceObjects(
				_messageBoardThreadResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				messageBoardThreadResource ->
					messageBoardThreadResource.getMessageBoardThread(
						_messageBoardMessage.getMessageBoardThreadId()));
		}

	}

	@GraphQLTypeExtension(DocumentFolder.class)
	public class GetDocumentFolderDocumentFoldersPageTypeExtension {

		private DocumentFolder _documentFolder;

		public GetDocumentFolderDocumentFoldersPageTypeExtension(
			DocumentFolder documentFolder) {

			_documentFolder = documentFolder;
		}

		@GraphQLField
		public DocumentFolderPage getDocumentFolderDocumentFoldersPage(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_documentFolderResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				documentFolderResource -> new DocumentFolderPage(
					documentFolderResource.getDocumentFolderDocumentFoldersPage(
						_documentFolder.getId(), search,
						_filterBiFunction.apply(
							documentFolderResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							documentFolderResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(KnowledgeBaseFolder.class)
	public class GetKnowledgeBaseFolderKnowledgeBaseArticlesPageTypeExtension {

		private KnowledgeBaseFolder _knowledgeBaseFolder;

		public GetKnowledgeBaseFolderKnowledgeBaseArticlesPageTypeExtension(
			KnowledgeBaseFolder knowledgeBaseFolder) {

			_knowledgeBaseFolder = knowledgeBaseFolder;
		}

		@GraphQLField
		public KnowledgeBaseArticlePage
				getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					@GraphQLName("flatten") Boolean flatten,
					@GraphQLName("search") String search,
					@GraphQLName("filter") String filterString,
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page,
					@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_knowledgeBaseArticleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				knowledgeBaseArticleResource -> new KnowledgeBaseArticlePage(
					knowledgeBaseArticleResource.
						getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
							_knowledgeBaseFolder.getId(), flatten, search,
							_filterBiFunction.apply(
								knowledgeBaseArticleResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								knowledgeBaseArticleResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(StructuredContent.class)
	public class GetStructuredContentMyRatingTypeExtension {

		private StructuredContent _structuredContent;

		public GetStructuredContentMyRatingTypeExtension(
			StructuredContent structuredContent) {

			_structuredContent = structuredContent;
		}

		@GraphQLField
		public Rating getStructuredContentMyRating() throws Exception {
			return _applyComponentServiceObjects(
				_structuredContentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				structuredContentResource ->
					structuredContentResource.getStructuredContentMyRating(
						_structuredContent.getId()));
		}

	}

	@GraphQLTypeExtension(BlogPosting.class)
	public class GetBlogPostingMyRatingTypeExtension {

		private BlogPosting _blogPosting;

		public GetBlogPostingMyRatingTypeExtension(BlogPosting blogPosting) {
			_blogPosting = blogPosting;
		}

		@GraphQLField
		public Rating getBlogPostingMyRating() throws Exception {
			return _applyComponentServiceObjects(
				_blogPostingResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				blogPostingResource ->
					blogPostingResource.getBlogPostingMyRating(
						_blogPosting.getId()));
		}

	}

	@GraphQLTypeExtension(Document.class)
	public class GetDocumentCommentsPageTypeExtension {

		private Document _document;

		public GetDocumentCommentsPageTypeExtension(Document document) {
			_document = document;
		}

		@GraphQLField
		public CommentPage getDocumentCommentsPage(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_commentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				commentResource -> new CommentPage(
					commentResource.getDocumentCommentsPage(
						_document.getId(), search,
						_filterBiFunction.apply(commentResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(commentResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(KnowledgeBaseArticle.class)
	public class
		GetKnowledgeBaseArticleKnowledgeBaseAttachmentsPageTypeExtension {

		private KnowledgeBaseArticle _knowledgeBaseArticle;

		public GetKnowledgeBaseArticleKnowledgeBaseAttachmentsPageTypeExtension(
			KnowledgeBaseArticle knowledgeBaseArticle) {

			_knowledgeBaseArticle = knowledgeBaseArticle;
		}

		@GraphQLField
		public KnowledgeBaseAttachmentPage
				getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage()
			throws Exception {

			return _applyComponentServiceObjects(
				_knowledgeBaseAttachmentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				knowledgeBaseAttachmentResource ->
					new KnowledgeBaseAttachmentPage(
						knowledgeBaseAttachmentResource.
							getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
								_knowledgeBaseArticle.getId())));
		}

	}

	@GraphQLTypeExtension(StructuredContentFolder.class)
	public class GetStructuredContentFolderStructuredContentsPageTypeExtension {

		private StructuredContentFolder _structuredContentFolder;

		public GetStructuredContentFolderStructuredContentsPageTypeExtension(
			StructuredContentFolder structuredContentFolder) {

			_structuredContentFolder = structuredContentFolder;
		}

		@GraphQLField
		public StructuredContentPage
				getStructuredContentFolderStructuredContentsPage(
					@GraphQLName("search") String search,
					@GraphQLName("filter") String filterString,
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page,
					@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_structuredContentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				structuredContentResource -> new StructuredContentPage(
					structuredContentResource.
						getStructuredContentFolderStructuredContentsPage(
							_structuredContentFolder.getId(), search,
							_filterBiFunction.apply(
								structuredContentResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								structuredContentResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(StructuredContentFolder.class)
	public class
		GetStructuredContentFolderStructuredContentFoldersPageTypeExtension {

		private StructuredContentFolder _structuredContentFolder;

		public GetStructuredContentFolderStructuredContentFoldersPageTypeExtension(
			StructuredContentFolder structuredContentFolder) {

			_structuredContentFolder = structuredContentFolder;
		}

		@GraphQLField
		public StructuredContentFolderPage
				getStructuredContentFolderStructuredContentFoldersPage(
					@GraphQLName("search") String search,
					@GraphQLName("filter") String filterString,
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page,
					@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_structuredContentFolderResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				structuredContentFolderResource ->
					new StructuredContentFolderPage(
						structuredContentFolderResource.
							getStructuredContentFolderStructuredContentFoldersPage(
								_structuredContentFolder.getId(), search,
								_filterBiFunction.apply(
									structuredContentFolderResource,
									filterString),
								Pagination.of(page, pageSize),
								_sortsBiFunction.apply(
									structuredContentFolderResource,
									sortsString))));
		}

	}

	@GraphQLTypeExtension(MessageBoardMessage.class)
	public class GetMessageBoardMessageMyRatingTypeExtension {

		private MessageBoardMessage _messageBoardMessage;

		public GetMessageBoardMessageMyRatingTypeExtension(
			MessageBoardMessage messageBoardMessage) {

			_messageBoardMessage = messageBoardMessage;
		}

		@GraphQLField
		public Rating getMessageBoardMessageMyRating() throws Exception {
			return _applyComponentServiceObjects(
				_messageBoardMessageResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				messageBoardMessageResource ->
					messageBoardMessageResource.getMessageBoardMessageMyRating(
						_messageBoardMessage.getId()));
		}

	}

	@GraphQLTypeExtension(MessageBoardMessage.class)
	public class GetMessageBoardMessageMessageBoardMessagesPageTypeExtension {

		private MessageBoardMessage _messageBoardMessage;

		public GetMessageBoardMessageMessageBoardMessagesPageTypeExtension(
			MessageBoardMessage messageBoardMessage) {

			_messageBoardMessage = messageBoardMessage;
		}

		@GraphQLField
		public MessageBoardMessagePage
				getMessageBoardMessageMessageBoardMessagesPage(
					@GraphQLName("search") String search,
					@GraphQLName("filter") String filterString,
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page,
					@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_messageBoardMessageResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				messageBoardMessageResource -> new MessageBoardMessagePage(
					messageBoardMessageResource.
						getMessageBoardMessageMessageBoardMessagesPage(
							_messageBoardMessage.getId(), search,
							_filterBiFunction.apply(
								messageBoardMessageResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								messageBoardMessageResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(Comment.class)
	public class GetCommentCommentsPageTypeExtension {

		private Comment _comment;

		public GetCommentCommentsPageTypeExtension(Comment comment) {
			_comment = comment;
		}

		@GraphQLField
		public CommentPage getCommentCommentsPage(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_commentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				commentResource -> new CommentPage(
					commentResource.getCommentCommentsPage(
						_comment.getId(), search,
						_filterBiFunction.apply(commentResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(commentResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(MessageBoardSection.class)
	public class GetMessageBoardSectionMessageBoardSectionsPageTypeExtension {

		private MessageBoardSection _messageBoardSection;

		public GetMessageBoardSectionMessageBoardSectionsPageTypeExtension(
			MessageBoardSection messageBoardSection) {

			_messageBoardSection = messageBoardSection;
		}

		@GraphQLField
		public MessageBoardSectionPage
				getMessageBoardSectionMessageBoardSectionsPage(
					@GraphQLName("search") String search,
					@GraphQLName("filter") String filterString,
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page,
					@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_messageBoardSectionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				messageBoardSectionResource -> new MessageBoardSectionPage(
					messageBoardSectionResource.
						getMessageBoardSectionMessageBoardSectionsPage(
							_messageBoardSection.getId(), search,
							_filterBiFunction.apply(
								messageBoardSectionResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								messageBoardSectionResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(StructuredContent.class)
	public class GetContentStructureTypeExtension {

		private StructuredContent _structuredContent;

		public GetContentStructureTypeExtension(
			StructuredContent structuredContent) {

			_structuredContent = structuredContent;
		}

		@GraphQLField
		public ContentStructure getContentStructure() throws Exception {
			return _applyComponentServiceObjects(
				_contentStructureResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				contentStructureResource ->
					contentStructureResource.getContentStructure(
						_structuredContent.getContentStructureId()));
		}

	}

	@GraphQLTypeExtension(MessageBoardSection.class)
	public class GetMessageBoardSectionMessageBoardThreadsPageTypeExtension {

		private MessageBoardSection _messageBoardSection;

		public GetMessageBoardSectionMessageBoardThreadsPageTypeExtension(
			MessageBoardSection messageBoardSection) {

			_messageBoardSection = messageBoardSection;
		}

		@GraphQLField
		public MessageBoardThreadPage
				getMessageBoardSectionMessageBoardThreadsPage(
					@GraphQLName("search") String search,
					@GraphQLName("filter") String filterString,
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page,
					@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_messageBoardThreadResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				messageBoardThreadResource -> new MessageBoardThreadPage(
					messageBoardThreadResource.
						getMessageBoardSectionMessageBoardThreadsPage(
							_messageBoardSection.getId(), search,
							_filterBiFunction.apply(
								messageBoardThreadResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								messageBoardThreadResource, sortsString))));
		}

	}

	@GraphQLTypeExtension(MessageBoardThread.class)
	public class GetMessageBoardThreadMyRatingTypeExtension {

		private MessageBoardThread _messageBoardThread;

		public GetMessageBoardThreadMyRatingTypeExtension(
			MessageBoardThread messageBoardThread) {

			_messageBoardThread = messageBoardThread;
		}

		@GraphQLField
		public Rating getMessageBoardThreadMyRating() throws Exception {
			return _applyComponentServiceObjects(
				_messageBoardThreadResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				messageBoardThreadResource ->
					messageBoardThreadResource.getMessageBoardThreadMyRating(
						_messageBoardThread.getId()));
		}

	}

	@GraphQLTypeExtension(MessageBoardThread.class)
	public class GetMessageBoardThreadMessageBoardMessagesPageTypeExtension {

		private MessageBoardThread _messageBoardThread;

		public GetMessageBoardThreadMessageBoardMessagesPageTypeExtension(
			MessageBoardThread messageBoardThread) {

			_messageBoardThread = messageBoardThread;
		}

		@GraphQLField
		public MessageBoardMessagePage
				getMessageBoardThreadMessageBoardMessagesPage(
					@GraphQLName("search") String search,
					@GraphQLName("filter") String filterString,
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page,
					@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_messageBoardMessageResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				messageBoardMessageResource -> new MessageBoardMessagePage(
					messageBoardMessageResource.
						getMessageBoardThreadMessageBoardMessagesPage(
							_messageBoardThread.getId(), search,
							_filterBiFunction.apply(
								messageBoardMessageResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								messageBoardMessageResource, sortsString))));
		}

	}

	@GraphQLName("BlogPostingPage")
	public class BlogPostingPage {

		public BlogPostingPage(Page blogPostingPage) {
			items = blogPostingPage.getItems();
			page = blogPostingPage.getPage();
			pageSize = blogPostingPage.getPageSize();
			totalCount = blogPostingPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<BlogPosting> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("BlogPostingImagePage")
	public class BlogPostingImagePage {

		public BlogPostingImagePage(Page blogPostingImagePage) {
			items = blogPostingImagePage.getItems();
			page = blogPostingImagePage.getPage();
			pageSize = blogPostingImagePage.getPageSize();
			totalCount = blogPostingImagePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<BlogPostingImage> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("CommentPage")
	public class CommentPage {

		public CommentPage(Page commentPage) {
			items = commentPage.getItems();
			page = commentPage.getPage();
			pageSize = commentPage.getPageSize();
			totalCount = commentPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Comment> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ContentSetElementPage")
	public class ContentSetElementPage {

		public ContentSetElementPage(Page contentSetElementPage) {
			items = contentSetElementPage.getItems();
			page = contentSetElementPage.getPage();
			pageSize = contentSetElementPage.getPageSize();
			totalCount = contentSetElementPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<ContentSetElement> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ContentStructurePage")
	public class ContentStructurePage {

		public ContentStructurePage(Page contentStructurePage) {
			items = contentStructurePage.getItems();
			page = contentStructurePage.getPage();
			pageSize = contentStructurePage.getPageSize();
			totalCount = contentStructurePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<ContentStructure> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("DocumentPage")
	public class DocumentPage {

		public DocumentPage(Page documentPage) {
			items = documentPage.getItems();
			page = documentPage.getPage();
			pageSize = documentPage.getPageSize();
			totalCount = documentPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Document> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("DocumentFolderPage")
	public class DocumentFolderPage {

		public DocumentFolderPage(Page documentFolderPage) {
			items = documentFolderPage.getItems();
			page = documentFolderPage.getPage();
			pageSize = documentFolderPage.getPageSize();
			totalCount = documentFolderPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<DocumentFolder> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("KnowledgeBaseArticlePage")
	public class KnowledgeBaseArticlePage {

		public KnowledgeBaseArticlePage(Page knowledgeBaseArticlePage) {
			items = knowledgeBaseArticlePage.getItems();
			page = knowledgeBaseArticlePage.getPage();
			pageSize = knowledgeBaseArticlePage.getPageSize();
			totalCount = knowledgeBaseArticlePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<KnowledgeBaseArticle> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("KnowledgeBaseAttachmentPage")
	public class KnowledgeBaseAttachmentPage {

		public KnowledgeBaseAttachmentPage(Page knowledgeBaseAttachmentPage) {
			items = knowledgeBaseAttachmentPage.getItems();
			page = knowledgeBaseAttachmentPage.getPage();
			pageSize = knowledgeBaseAttachmentPage.getPageSize();
			totalCount = knowledgeBaseAttachmentPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<KnowledgeBaseAttachment> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("KnowledgeBaseFolderPage")
	public class KnowledgeBaseFolderPage {

		public KnowledgeBaseFolderPage(Page knowledgeBaseFolderPage) {
			items = knowledgeBaseFolderPage.getItems();
			page = knowledgeBaseFolderPage.getPage();
			pageSize = knowledgeBaseFolderPage.getPageSize();
			totalCount = knowledgeBaseFolderPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<KnowledgeBaseFolder> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("MessageBoardAttachmentPage")
	public class MessageBoardAttachmentPage {

		public MessageBoardAttachmentPage(Page messageBoardAttachmentPage) {
			items = messageBoardAttachmentPage.getItems();
			page = messageBoardAttachmentPage.getPage();
			pageSize = messageBoardAttachmentPage.getPageSize();
			totalCount = messageBoardAttachmentPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<MessageBoardAttachment> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("MessageBoardMessagePage")
	public class MessageBoardMessagePage {

		public MessageBoardMessagePage(Page messageBoardMessagePage) {
			items = messageBoardMessagePage.getItems();
			page = messageBoardMessagePage.getPage();
			pageSize = messageBoardMessagePage.getPageSize();
			totalCount = messageBoardMessagePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<MessageBoardMessage> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("MessageBoardSectionPage")
	public class MessageBoardSectionPage {

		public MessageBoardSectionPage(Page messageBoardSectionPage) {
			items = messageBoardSectionPage.getItems();
			page = messageBoardSectionPage.getPage();
			pageSize = messageBoardSectionPage.getPageSize();
			totalCount = messageBoardSectionPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<MessageBoardSection> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("MessageBoardThreadPage")
	public class MessageBoardThreadPage {

		public MessageBoardThreadPage(Page messageBoardThreadPage) {
			items = messageBoardThreadPage.getItems();
			page = messageBoardThreadPage.getPage();
			pageSize = messageBoardThreadPage.getPageSize();
			totalCount = messageBoardThreadPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<MessageBoardThread> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("StructuredContentPage")
	public class StructuredContentPage {

		public StructuredContentPage(Page structuredContentPage) {
			items = structuredContentPage.getItems();
			page = structuredContentPage.getPage();
			pageSize = structuredContentPage.getPageSize();
			totalCount = structuredContentPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<StructuredContent> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("StructuredContentFolderPage")
	public class StructuredContentFolderPage {

		public StructuredContentFolderPage(Page structuredContentFolderPage) {
			items = structuredContentFolderPage.getItems();
			page = structuredContentFolderPage.getPage();
			pageSize = structuredContentFolderPage.getPageSize();
			totalCount = structuredContentFolderPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<StructuredContentFolder> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WikiNodePage")
	public class WikiNodePage {

		public WikiNodePage(Page wikiNodePage) {
			items = wikiNodePage.getItems();
			page = wikiNodePage.getPage();
			pageSize = wikiNodePage.getPageSize();
			totalCount = wikiNodePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<WikiNode> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WikiPagePage")
	public class WikiPagePage {

		public WikiPagePage(Page wikiPagePage) {
			items = wikiPagePage.getItems();
			page = wikiPagePage.getPage();
			pageSize = wikiPagePage.getPageSize();
			totalCount = wikiPagePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<WikiPage> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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
		blogPostingResource.setContextHttpServletRequest(_httpServletRequest);
		blogPostingResource.setContextHttpServletResponse(_httpServletResponse);
		blogPostingResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			BlogPostingImageResource blogPostingImageResource)
		throws Exception {

		blogPostingImageResource.setContextAcceptLanguage(_acceptLanguage);
		blogPostingImageResource.setContextCompany(_company);
		blogPostingImageResource.setContextHttpServletRequest(
			_httpServletRequest);
		blogPostingImageResource.setContextHttpServletResponse(
			_httpServletResponse);
		blogPostingImageResource.setContextUser(_user);
	}

	private void _populateResourceContext(CommentResource commentResource)
		throws Exception {

		commentResource.setContextAcceptLanguage(_acceptLanguage);
		commentResource.setContextCompany(_company);
		commentResource.setContextHttpServletRequest(_httpServletRequest);
		commentResource.setContextHttpServletResponse(_httpServletResponse);
		commentResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			ContentSetElementResource contentSetElementResource)
		throws Exception {

		contentSetElementResource.setContextAcceptLanguage(_acceptLanguage);
		contentSetElementResource.setContextCompany(_company);
		contentSetElementResource.setContextHttpServletRequest(
			_httpServletRequest);
		contentSetElementResource.setContextHttpServletResponse(
			_httpServletResponse);
		contentSetElementResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			ContentStructureResource contentStructureResource)
		throws Exception {

		contentStructureResource.setContextAcceptLanguage(_acceptLanguage);
		contentStructureResource.setContextCompany(_company);
		contentStructureResource.setContextHttpServletRequest(
			_httpServletRequest);
		contentStructureResource.setContextHttpServletResponse(
			_httpServletResponse);
		contentStructureResource.setContextUser(_user);
	}

	private void _populateResourceContext(DocumentResource documentResource)
		throws Exception {

		documentResource.setContextAcceptLanguage(_acceptLanguage);
		documentResource.setContextCompany(_company);
		documentResource.setContextHttpServletRequest(_httpServletRequest);
		documentResource.setContextHttpServletResponse(_httpServletResponse);
		documentResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			DocumentFolderResource documentFolderResource)
		throws Exception {

		documentFolderResource.setContextAcceptLanguage(_acceptLanguage);
		documentFolderResource.setContextCompany(_company);
		documentFolderResource.setContextHttpServletRequest(
			_httpServletRequest);
		documentFolderResource.setContextHttpServletResponse(
			_httpServletResponse);
		documentFolderResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			KnowledgeBaseArticleResource knowledgeBaseArticleResource)
		throws Exception {

		knowledgeBaseArticleResource.setContextAcceptLanguage(_acceptLanguage);
		knowledgeBaseArticleResource.setContextCompany(_company);
		knowledgeBaseArticleResource.setContextHttpServletRequest(
			_httpServletRequest);
		knowledgeBaseArticleResource.setContextHttpServletResponse(
			_httpServletResponse);
		knowledgeBaseArticleResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			KnowledgeBaseAttachmentResource knowledgeBaseAttachmentResource)
		throws Exception {

		knowledgeBaseAttachmentResource.setContextAcceptLanguage(
			_acceptLanguage);
		knowledgeBaseAttachmentResource.setContextCompany(_company);
		knowledgeBaseAttachmentResource.setContextHttpServletRequest(
			_httpServletRequest);
		knowledgeBaseAttachmentResource.setContextHttpServletResponse(
			_httpServletResponse);
		knowledgeBaseAttachmentResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			KnowledgeBaseFolderResource knowledgeBaseFolderResource)
		throws Exception {

		knowledgeBaseFolderResource.setContextAcceptLanguage(_acceptLanguage);
		knowledgeBaseFolderResource.setContextCompany(_company);
		knowledgeBaseFolderResource.setContextHttpServletRequest(
			_httpServletRequest);
		knowledgeBaseFolderResource.setContextHttpServletResponse(
			_httpServletResponse);
		knowledgeBaseFolderResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			MessageBoardAttachmentResource messageBoardAttachmentResource)
		throws Exception {

		messageBoardAttachmentResource.setContextAcceptLanguage(
			_acceptLanguage);
		messageBoardAttachmentResource.setContextCompany(_company);
		messageBoardAttachmentResource.setContextHttpServletRequest(
			_httpServletRequest);
		messageBoardAttachmentResource.setContextHttpServletResponse(
			_httpServletResponse);
		messageBoardAttachmentResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			MessageBoardMessageResource messageBoardMessageResource)
		throws Exception {

		messageBoardMessageResource.setContextAcceptLanguage(_acceptLanguage);
		messageBoardMessageResource.setContextCompany(_company);
		messageBoardMessageResource.setContextHttpServletRequest(
			_httpServletRequest);
		messageBoardMessageResource.setContextHttpServletResponse(
			_httpServletResponse);
		messageBoardMessageResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			MessageBoardSectionResource messageBoardSectionResource)
		throws Exception {

		messageBoardSectionResource.setContextAcceptLanguage(_acceptLanguage);
		messageBoardSectionResource.setContextCompany(_company);
		messageBoardSectionResource.setContextHttpServletRequest(
			_httpServletRequest);
		messageBoardSectionResource.setContextHttpServletResponse(
			_httpServletResponse);
		messageBoardSectionResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			MessageBoardThreadResource messageBoardThreadResource)
		throws Exception {

		messageBoardThreadResource.setContextAcceptLanguage(_acceptLanguage);
		messageBoardThreadResource.setContextCompany(_company);
		messageBoardThreadResource.setContextHttpServletRequest(
			_httpServletRequest);
		messageBoardThreadResource.setContextHttpServletResponse(
			_httpServletResponse);
		messageBoardThreadResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			StructuredContentResource structuredContentResource)
		throws Exception {

		structuredContentResource.setContextAcceptLanguage(_acceptLanguage);
		structuredContentResource.setContextCompany(_company);
		structuredContentResource.setContextHttpServletRequest(
			_httpServletRequest);
		structuredContentResource.setContextHttpServletResponse(
			_httpServletResponse);
		structuredContentResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			StructuredContentFolderResource structuredContentFolderResource)
		throws Exception {

		structuredContentFolderResource.setContextAcceptLanguage(
			_acceptLanguage);
		structuredContentFolderResource.setContextCompany(_company);
		structuredContentFolderResource.setContextHttpServletRequest(
			_httpServletRequest);
		structuredContentFolderResource.setContextHttpServletResponse(
			_httpServletResponse);
		structuredContentFolderResource.setContextUser(_user);
	}

	private void _populateResourceContext(WikiNodeResource wikiNodeResource)
		throws Exception {

		wikiNodeResource.setContextAcceptLanguage(_acceptLanguage);
		wikiNodeResource.setContextCompany(_company);
		wikiNodeResource.setContextHttpServletRequest(_httpServletRequest);
		wikiNodeResource.setContextHttpServletResponse(_httpServletResponse);
		wikiNodeResource.setContextUser(_user);
	}

	private void _populateResourceContext(WikiPageResource wikiPageResource)
		throws Exception {

		wikiPageResource.setContextAcceptLanguage(_acceptLanguage);
		wikiPageResource.setContextCompany(_company);
		wikiPageResource.setContextHttpServletRequest(_httpServletRequest);
		wikiPageResource.setContextHttpServletResponse(_httpServletResponse);
		wikiPageResource.setContextUser(_user);
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
	private static ComponentServiceObjects<WikiNodeResource>
		_wikiNodeResourceComponentServiceObjects;
	private static ComponentServiceObjects<WikiPageResource>
		_wikiPageResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private User _user;

}