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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.function.Function;

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
	@GraphQLInvokeDetached
	public BlogPosting getBlogPosting(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource -> blogPostingResource.getBlogPosting(
				blogPostingId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating getBlogPostingMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource -> blogPostingResource.getBlogPostingMyRating(
				blogPostingId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPosting> getSiteBlogPostingsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource -> {
				Page paginationPage =
					blogPostingResource.getSiteBlogPostingsPage(
						siteId, search, filter, Pagination.of(pageSize, page),
						sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImage getBlogPostingImage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingImageId") Long blogPostingImageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			blogPostingImageResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingImageResource),
			blogPostingImageResource ->
				blogPostingImageResource.getBlogPostingImage(
					blogPostingImageId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPostingImage> getSiteBlogPostingImagesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			blogPostingImageResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingImageResource),
			blogPostingImageResource -> {
				Page paginationPage =
					blogPostingImageResource.getSiteBlogPostingImagesPage(
						siteId, search, filter, Pagination.of(pageSize, page),
						sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getBlogPostingCommentsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
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
	public Comment getComment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("commentId") Long commentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> commentResource.getComment(commentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getCommentCommentsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentCommentId") Long parentCommentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> {
				Page paginationPage = commentResource.getCommentCommentsPage(
					parentCommentId, search, filter,
					Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getDocumentCommentsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> {
				Page paginationPage = commentResource.getDocumentCommentsPage(
					documentId, search, filter, Pagination.of(pageSize, page),
					sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getStructuredContentCommentsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> {
				Page paginationPage =
					commentResource.getStructuredContentCommentsPage(
						structuredContentId, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<ContentSetElement> getContentSetContentSetElementsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("contentSetId") Long contentSetId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects,
			contentSetElementResource -> _populateResourceContext(
				dataFetchingEnvironment, contentSetElementResource),
			contentSetElementResource -> {
				Page paginationPage =
					contentSetElementResource.
						getContentSetContentSetElementsPage(
							contentSetId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<ContentSetElement>
			getSiteContentSetByKeyContentSetElementsPage(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("key") String key,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects,
			contentSetElementResource -> _populateResourceContext(
				dataFetchingEnvironment, contentSetElementResource),
			contentSetElementResource -> {
				Page paginationPage =
					contentSetElementResource.
						getSiteContentSetByKeyContentSetElementsPage(
							siteId, key, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<ContentSetElement>
			getSiteContentSetByUuidContentSetElementsPage(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("uuid") String uuid,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects,
			contentSetElementResource -> _populateResourceContext(
				dataFetchingEnvironment, contentSetElementResource),
			contentSetElementResource -> {
				Page paginationPage =
					contentSetElementResource.
						getSiteContentSetByUuidContentSetElementsPage(
							siteId, uuid, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public ContentStructure getContentStructure(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("contentStructureId") Long contentStructureId)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects,
			contentStructureResource -> _populateResourceContext(
				dataFetchingEnvironment, contentStructureResource),
			contentStructureResource ->
				contentStructureResource.getContentStructure(
					contentStructureId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<ContentStructure> getSiteContentStructuresPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects,
			contentStructureResource -> _populateResourceContext(
				dataFetchingEnvironment, contentStructureResource),
			contentStructureResource -> {
				Page paginationPage =
					contentStructureResource.getSiteContentStructuresPage(
						siteId, search, filter, Pagination.of(pageSize, page),
						sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Document> getDocumentFolderDocumentsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> {
				Page paginationPage =
					documentResource.getDocumentFolderDocumentsPage(
						documentFolderId, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Document getDocument(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.getDocument(documentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating getDocumentMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.getDocumentMyRating(
				documentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Document> getSiteDocumentsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> {
				Page paginationPage = documentResource.getSiteDocumentsPage(
					siteId, flatten, search, filter,
					Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DocumentFolder getDocumentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentFolderId") Long documentFolderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			documentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, documentFolderResource),
			documentFolderResource -> documentFolderResource.getDocumentFolder(
				documentFolderId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DocumentFolder> getDocumentFolderDocumentFoldersPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentDocumentFolderId") Long parentDocumentFolderId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			documentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, documentFolderResource),
			documentFolderResource -> {
				Page paginationPage =
					documentFolderResource.getDocumentFolderDocumentFoldersPage(
						parentDocumentFolderId, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DocumentFolder> getSiteDocumentFoldersPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			documentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, documentFolderResource),
			documentFolderResource -> {
				Page paginationPage =
					documentFolderResource.getSiteDocumentFoldersPage(
						siteId, flatten, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.getKnowledgeBaseArticle(
					knowledgeBaseArticleId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating getKnowledgeBaseArticleMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.getKnowledgeBaseArticleMyRating(
					knowledgeBaseArticleId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				DataFetchingEnvironment dataFetchingEnvironment,
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
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
							parentKnowledgeBaseArticleId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				DataFetchingEnvironment dataFetchingEnvironment,
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
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
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
	public Collection<KnowledgeBaseArticle> getSiteKnowledgeBaseArticlesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						getSiteKnowledgeBaseArticlesPage(
							siteId, flatten, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseAttachment>
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("knowledgeBaseArticleId") Long
					knowledgeBaseArticleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			knowledgeBaseAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseAttachmentResource),
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
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseAttachmentId") Long
				knowledgeBaseAttachmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			knowledgeBaseAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseAttachmentResource),
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.getKnowledgeBaseAttachment(
					knowledgeBaseAttachmentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			knowledgeBaseFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseFolderResource),
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.getKnowledgeBaseFolder(
					knowledgeBaseFolderId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("parentKnowledgeBaseFolderId") Long
					parentKnowledgeBaseFolderId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			knowledgeBaseFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseFolderResource),
			knowledgeBaseFolderResource -> {
				Page paginationPage =
					knowledgeBaseFolderResource.
						getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
							parentKnowledgeBaseFolderId,
							Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseFolder> getSiteKnowledgeBaseFoldersPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			knowledgeBaseFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseFolderResource),
			knowledgeBaseFolderResource -> {
				Page paginationPage =
					knowledgeBaseFolderResource.getSiteKnowledgeBaseFoldersPage(
						siteId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardAttachment getMessageBoardAttachment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardAttachmentId") Long
				messageBoardAttachmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			messageBoardAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardAttachmentResource),
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.getMessageBoardAttachment(
					messageBoardAttachmentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardAttachment>
			getMessageBoardMessageMessageBoardAttachmentsPage(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("messageBoardMessageId") Long
					messageBoardMessageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			messageBoardAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardAttachmentResource),
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
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			messageBoardAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardAttachmentResource),
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
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.getMessageBoardMessage(
					messageBoardMessageId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating getMessageBoardMessageMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.getMessageBoardMessageMyRating(
					messageBoardMessageId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardMessage>
			getMessageBoardMessageMessageBoardMessagesPage(
				DataFetchingEnvironment dataFetchingEnvironment,
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
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource -> {
				Page paginationPage =
					messageBoardMessageResource.
						getMessageBoardMessageMessageBoardMessagesPage(
							parentMessageBoardMessageId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardMessage>
			getMessageBoardThreadMessageBoardMessagesPage(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
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
	public MessageBoardSection getMessageBoardSection(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			messageBoardSectionResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardSectionResource),
			messageBoardSectionResource ->
				messageBoardSectionResource.getMessageBoardSection(
					messageBoardSectionId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardSection>
			getMessageBoardSectionMessageBoardSectionsPage(
				DataFetchingEnvironment dataFetchingEnvironment,
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
			messageBoardSectionResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardSectionResource),
			messageBoardSectionResource -> {
				Page paginationPage =
					messageBoardSectionResource.
						getMessageBoardSectionMessageBoardSectionsPage(
							parentMessageBoardSectionId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardSection> getSiteMessageBoardSectionsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			messageBoardSectionResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardSectionResource),
			messageBoardSectionResource -> {
				Page paginationPage =
					messageBoardSectionResource.getSiteMessageBoardSectionsPage(
						siteId, flatten, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardThread>
			getMessageBoardSectionMessageBoardThreadsPage(
				DataFetchingEnvironment dataFetchingEnvironment,
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
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
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
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.getMessageBoardThread(
					messageBoardThreadId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating getMessageBoardThreadMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.getMessageBoardThreadMyRating(
					messageBoardThreadId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<MessageBoardThread> getSiteMessageBoardThreadsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource -> {
				Page paginationPage =
					messageBoardThreadResource.getSiteMessageBoardThreadsPage(
						siteId, flatten, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContent>
			getContentStructureStructuredContentsPage(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("contentStructureId") Long contentStructureId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource -> {
				Page paginationPage =
					structuredContentResource.
						getContentStructureStructuredContentsPage(
							contentStructureId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContent> getSiteStructuredContentsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource -> {
				Page paginationPage =
					structuredContentResource.getSiteStructuredContentsPage(
						siteId, flatten, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent getSiteStructuredContentByKey(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId, @GraphQLName("key") String key)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.getSiteStructuredContentByKey(
					siteId, key));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent getSiteStructuredContentByUuid(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("uuid") String uuid)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.getSiteStructuredContentByUuid(
					siteId, uuid));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContent>
			getStructuredContentFolderStructuredContentsPage(
				DataFetchingEnvironment dataFetchingEnvironment,
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
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource -> {
				Page paginationPage =
					structuredContentResource.
						getStructuredContentFolderStructuredContentsPage(
							structuredContentFolderId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent getStructuredContent(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.getStructuredContent(
					structuredContentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating getStructuredContentMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.getStructuredContentMyRating(
					structuredContentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public String getStructuredContentRenderedContentTemplate(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("templateId") Long templateId)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.
					getStructuredContentRenderedContentTemplate(
						structuredContentId, templateId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContentFolder>
			getSiteStructuredContentFoldersPage(
				DataFetchingEnvironment dataFetchingEnvironment,
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
			structuredContentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentFolderResource),
			structuredContentFolderResource -> {
				Page paginationPage =
					structuredContentFolderResource.
						getSiteStructuredContentFoldersPage(
							siteId, flatten, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContentFolder>
			getStructuredContentFolderStructuredContentFoldersPage(
				DataFetchingEnvironment dataFetchingEnvironment,
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
			structuredContentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentFolderResource),
			structuredContentFolderResource -> {
				Page paginationPage =
					structuredContentFolderResource.
						getStructuredContentFolderStructuredContentFoldersPage(
							parentStructuredContentFolderId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContentFolder getStructuredContentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			structuredContentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentFolderResource),
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
			DataFetchingEnvironment dataFetchingEnvironment,
			BlogPostingResource blogPostingResource)
		throws Exception {

		blogPostingResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		blogPostingResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			BlogPostingImageResource blogPostingImageResource)
		throws Exception {

		blogPostingImageResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		blogPostingImageResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			CommentResource commentResource)
		throws Exception {

		commentResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		commentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			ContentSetElementResource contentSetElementResource)
		throws Exception {

		contentSetElementResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		contentSetElementResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			ContentStructureResource contentStructureResource)
		throws Exception {

		contentStructureResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		contentStructureResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			DocumentResource documentResource)
		throws Exception {

		documentResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		documentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			DocumentFolderResource documentFolderResource)
		throws Exception {

		documentFolderResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		documentFolderResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			KnowledgeBaseArticleResource knowledgeBaseArticleResource)
		throws Exception {

		knowledgeBaseArticleResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		knowledgeBaseArticleResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			KnowledgeBaseAttachmentResource knowledgeBaseAttachmentResource)
		throws Exception {

		knowledgeBaseAttachmentResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		knowledgeBaseAttachmentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			KnowledgeBaseFolderResource knowledgeBaseFolderResource)
		throws Exception {

		knowledgeBaseFolderResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		knowledgeBaseFolderResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			MessageBoardAttachmentResource messageBoardAttachmentResource)
		throws Exception {

		messageBoardAttachmentResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		messageBoardAttachmentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			MessageBoardMessageResource messageBoardMessageResource)
		throws Exception {

		messageBoardMessageResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		messageBoardMessageResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			MessageBoardSectionResource messageBoardSectionResource)
		throws Exception {

		messageBoardSectionResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		messageBoardSectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			MessageBoardThreadResource messageBoardThreadResource)
		throws Exception {

		messageBoardThreadResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		messageBoardThreadResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			StructuredContentResource structuredContentResource)
		throws Exception {

		structuredContentResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		structuredContentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			StructuredContentFolderResource structuredContentFolderResource)
		throws Exception {

		structuredContentFolderResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		structuredContentFolderResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	public static void setAcceptLanguageFunction(
		Function<Object, AcceptLanguage> acceptLanguageFunction) {

		_acceptLanguageFunction = acceptLanguageFunction;
	}

	private static Function<Object, AcceptLanguage> _acceptLanguageFunction;
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

}