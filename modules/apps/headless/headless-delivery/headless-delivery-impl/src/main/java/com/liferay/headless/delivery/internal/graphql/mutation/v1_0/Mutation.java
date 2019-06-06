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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.function.Function;

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

	@GraphQLInvokeDetached
	public void deleteBlogPosting(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource -> blogPostingResource.deleteBlogPosting(
				blogPostingId));
	}

	@GraphQLInvokeDetached
	public BlogPosting patchBlogPosting(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("blogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource -> blogPostingResource.patchBlogPosting(
				blogPostingId, blogPosting));
	}

	@GraphQLInvokeDetached
	public BlogPosting putBlogPosting(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("blogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource -> blogPostingResource.putBlogPosting(
				blogPostingId, blogPosting));
	}

	@GraphQLInvokeDetached
	public void deleteBlogPostingMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource ->
				blogPostingResource.deleteBlogPostingMyRating(blogPostingId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postBlogPostingMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource -> blogPostingResource.postBlogPostingMyRating(
				blogPostingId, rating));
	}

	@GraphQLInvokeDetached
	public Rating putBlogPostingMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource -> blogPostingResource.putBlogPostingMyRating(
				blogPostingId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPosting postSiteBlogPosting(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("blogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			blogPostingResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingResource),
			blogPostingResource -> blogPostingResource.postSiteBlogPosting(
				siteId, blogPosting));
	}

	@GraphQLInvokeDetached
	public void deleteBlogPostingImage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingImageId") Long blogPostingImageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			blogPostingImageResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingImageResource),
			blogPostingImageResource ->
				blogPostingImageResource.deleteBlogPostingImage(
					blogPostingImageId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName("postSiteBlogPostingImageSiteIdMultipartBody")
	public BlogPostingImage postSiteBlogPostingImage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			blogPostingImageResource -> _populateResourceContext(
				dataFetchingEnvironment, blogPostingImageResource),
			blogPostingImageResource ->
				blogPostingImageResource.postSiteBlogPostingImage(
					siteId, multipartBody));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postBlogPostingComment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> commentResource.postBlogPostingComment(
				blogPostingId, comment));
	}

	@GraphQLInvokeDetached
	public void deleteComment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("commentId") Long commentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> commentResource.deleteComment(commentId));
	}

	@GraphQLInvokeDetached
	public Comment putComment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("commentId") Long commentId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> commentResource.putComment(commentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postCommentComment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentCommentId") Long parentCommentId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> commentResource.postCommentComment(
				parentCommentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postDocumentComment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> commentResource.postDocumentComment(
				documentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postStructuredContentComment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			commentResource -> _populateResourceContext(
				dataFetchingEnvironment, commentResource),
			commentResource -> commentResource.postStructuredContentComment(
				structuredContentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName("postDocumentFolderDocumentDocumentFolderIdMultipartBody")
	public Document postDocumentFolderDocument(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.postDocumentFolderDocument(
				documentFolderId, multipartBody));
	}

	@GraphQLInvokeDetached
	public void deleteDocument(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.deleteDocument(documentId));
	}

	@GraphQLInvokeDetached
	@GraphQLName("patchDocumentDocumentIdMultipartBody")
	public Document patchDocument(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.patchDocument(
				documentId, multipartBody));
	}

	@GraphQLInvokeDetached
	@GraphQLName("putDocumentDocumentIdMultipartBody")
	public Document putDocument(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.putDocument(
				documentId, multipartBody));
	}

	@GraphQLInvokeDetached
	public void deleteDocumentMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.deleteDocumentMyRating(
				documentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postDocumentMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.postDocumentMyRating(
				documentId, rating));
	}

	@GraphQLInvokeDetached
	public Rating putDocumentMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.putDocumentMyRating(
				documentId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName("postSiteDocumentSiteIdMultipartBody")
	public Document postSiteDocument(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			documentResource -> _populateResourceContext(
				dataFetchingEnvironment, documentResource),
			documentResource -> documentResource.postSiteDocument(
				siteId, multipartBody));
	}

	@GraphQLInvokeDetached
	public void deleteDocumentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentFolderId") Long documentFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			documentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, documentFolderResource),
			documentFolderResource ->
				documentFolderResource.deleteDocumentFolder(documentFolderId));
	}

	@GraphQLInvokeDetached
	public DocumentFolder patchDocumentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			documentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, documentFolderResource),
			documentFolderResource ->
				documentFolderResource.patchDocumentFolder(
					documentFolderId, documentFolder));
	}

	@GraphQLInvokeDetached
	public DocumentFolder putDocumentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			documentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, documentFolderResource),
			documentFolderResource -> documentFolderResource.putDocumentFolder(
				documentFolderId, documentFolder));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DocumentFolder postDocumentFolderDocumentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentDocumentFolderId") Long parentDocumentFolderId,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			documentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, documentFolderResource),
			documentFolderResource ->
				documentFolderResource.postDocumentFolderDocumentFolder(
					parentDocumentFolderId, documentFolder));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DocumentFolder postSiteDocumentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			documentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, documentFolderResource),
			documentFolderResource ->
				documentFolderResource.postSiteDocumentFolder(
					siteId, documentFolder));
	}

	@GraphQLInvokeDetached
	public void deleteKnowledgeBaseArticle(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
					knowledgeBaseArticleId));
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.patchKnowledgeBaseArticle(
					knowledgeBaseArticleId, knowledgeBaseArticle));
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.putKnowledgeBaseArticle(
					knowledgeBaseArticleId, knowledgeBaseArticle));
	}

	@GraphQLInvokeDetached
	public void deleteKnowledgeBaseArticleMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.deleteKnowledgeBaseArticleMyRating(
					knowledgeBaseArticleId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postKnowledgeBaseArticleMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.postKnowledgeBaseArticleMyRating(
					knowledgeBaseArticleId, rating));
	}

	@GraphQLInvokeDetached
	public Rating putKnowledgeBaseArticleMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.putKnowledgeBaseArticleMyRating(
					knowledgeBaseArticleId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentKnowledgeBaseArticleId") Long
				parentKnowledgeBaseArticleId,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					postKnowledgeBaseArticleKnowledgeBaseArticle(
						parentKnowledgeBaseArticleId, knowledgeBaseArticle));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					postKnowledgeBaseFolderKnowledgeBaseArticle(
						knowledgeBaseFolderId, knowledgeBaseArticle));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle postSiteKnowledgeBaseArticle(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			knowledgeBaseArticleResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseArticleResource),
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
					siteId, knowledgeBaseArticle));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName(
		"postKnowledgeBaseArticleKnowledgeBaseAttachmentKnowledgeBaseArticleIdMultipartBody"
	)
	public KnowledgeBaseAttachment
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("knowledgeBaseArticleId") Long
					knowledgeBaseArticleId,
				@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			knowledgeBaseAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseAttachmentResource),
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.
					postKnowledgeBaseArticleKnowledgeBaseAttachment(
						knowledgeBaseArticleId, multipartBody));
	}

	@GraphQLInvokeDetached
	public void deleteKnowledgeBaseAttachment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseAttachmentId") Long
				knowledgeBaseAttachmentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			knowledgeBaseAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseAttachmentResource),
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.deleteKnowledgeBaseAttachment(
					knowledgeBaseAttachmentId));
	}

	@GraphQLInvokeDetached
	public void deleteKnowledgeBaseFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			knowledgeBaseFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseFolderResource),
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.deleteKnowledgeBaseFolder(
					knowledgeBaseFolderId));
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			@GraphQLName("knowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			knowledgeBaseFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseFolderResource),
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.patchKnowledgeBaseFolder(
					knowledgeBaseFolderId, knowledgeBaseFolder));
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			@GraphQLName("knowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			knowledgeBaseFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseFolderResource),
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.putKnowledgeBaseFolder(
					knowledgeBaseFolderId, knowledgeBaseFolder));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentKnowledgeBaseFolderId") Long
				parentKnowledgeBaseFolderId,
			@GraphQLName("knowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			knowledgeBaseFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseFolderResource),
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.
					postKnowledgeBaseFolderKnowledgeBaseFolder(
						parentKnowledgeBaseFolderId, knowledgeBaseFolder));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("knowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			knowledgeBaseFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, knowledgeBaseFolderResource),
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
					siteId, knowledgeBaseFolder));
	}

	@GraphQLInvokeDetached
	public void deleteMessageBoardAttachment(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardAttachmentId") Long
				messageBoardAttachmentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			messageBoardAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardAttachmentResource),
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
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			messageBoardAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardAttachmentResource),
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
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			messageBoardAttachmentResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardAttachmentResource),
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.
					postMessageBoardThreadMessageBoardAttachment(
						messageBoardThreadId, multipartBody));
	}

	@GraphQLInvokeDetached
	public void deleteMessageBoardMessage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.deleteMessageBoardMessage(
					messageBoardMessageId));
	}

	@GraphQLInvokeDetached
	public MessageBoardMessage patchMessageBoardMessage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("messageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.patchMessageBoardMessage(
					messageBoardMessageId, messageBoardMessage));
	}

	@GraphQLInvokeDetached
	public MessageBoardMessage putMessageBoardMessage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("messageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.putMessageBoardMessage(
					messageBoardMessageId, messageBoardMessage));
	}

	@GraphQLInvokeDetached
	public void deleteMessageBoardMessageMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.deleteMessageBoardMessageMyRating(
					messageBoardMessageId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postMessageBoardMessageMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.postMessageBoardMessageMyRating(
					messageBoardMessageId, rating));
	}

	@GraphQLInvokeDetached
	public Rating putMessageBoardMessageMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.putMessageBoardMessageMyRating(
					messageBoardMessageId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardMessage postMessageBoardMessageMessageBoardMessage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentMessageBoardMessageId") Long
				parentMessageBoardMessageId,
			@GraphQLName("messageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.
					postMessageBoardMessageMessageBoardMessage(
						parentMessageBoardMessageId, messageBoardMessage));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardMessage postMessageBoardThreadMessageBoardMessage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("messageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			messageBoardMessageResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardMessageResource),
			messageBoardMessageResource ->
				messageBoardMessageResource.
					postMessageBoardThreadMessageBoardMessage(
						messageBoardThreadId, messageBoardMessage));
	}

	@GraphQLInvokeDetached
	public void deleteMessageBoardSection(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			messageBoardSectionResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardSectionResource),
			messageBoardSectionResource ->
				messageBoardSectionResource.deleteMessageBoardSection(
					messageBoardSectionId));
	}

	@GraphQLInvokeDetached
	public MessageBoardSection patchMessageBoardSection(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId,
			@GraphQLName("messageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			messageBoardSectionResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardSectionResource),
			messageBoardSectionResource ->
				messageBoardSectionResource.patchMessageBoardSection(
					messageBoardSectionId, messageBoardSection));
	}

	@GraphQLInvokeDetached
	public MessageBoardSection putMessageBoardSection(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId,
			@GraphQLName("messageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			messageBoardSectionResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardSectionResource),
			messageBoardSectionResource ->
				messageBoardSectionResource.putMessageBoardSection(
					messageBoardSectionId, messageBoardSection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardSection postMessageBoardSectionMessageBoardSection(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentMessageBoardSectionId") Long
				parentMessageBoardSectionId,
			@GraphQLName("messageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			messageBoardSectionResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardSectionResource),
			messageBoardSectionResource ->
				messageBoardSectionResource.
					postMessageBoardSectionMessageBoardSection(
						parentMessageBoardSectionId, messageBoardSection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardSection postSiteMessageBoardSection(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("messageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			messageBoardSectionResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardSectionResource),
			messageBoardSectionResource ->
				messageBoardSectionResource.postSiteMessageBoardSection(
					siteId, messageBoardSection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardThread postMessageBoardSectionMessageBoardThread(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId,
			@GraphQLName("messageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.
					postMessageBoardSectionMessageBoardThread(
						messageBoardSectionId, messageBoardThread));
	}

	@GraphQLInvokeDetached
	public void deleteMessageBoardThread(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.deleteMessageBoardThread(
					messageBoardThreadId));
	}

	@GraphQLInvokeDetached
	public MessageBoardThread patchMessageBoardThread(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("messageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.patchMessageBoardThread(
					messageBoardThreadId, messageBoardThread));
	}

	@GraphQLInvokeDetached
	public MessageBoardThread putMessageBoardThread(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("messageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.putMessageBoardThread(
					messageBoardThreadId, messageBoardThread));
	}

	@GraphQLInvokeDetached
	public void deleteMessageBoardThreadMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.deleteMessageBoardThreadMyRating(
					messageBoardThreadId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postMessageBoardThreadMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.postMessageBoardThreadMyRating(
					messageBoardThreadId, rating));
	}

	@GraphQLInvokeDetached
	public Rating putMessageBoardThreadMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.putMessageBoardThreadMyRating(
					messageBoardThreadId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageBoardThread postSiteMessageBoardThread(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("messageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			messageBoardThreadResource -> _populateResourceContext(
				dataFetchingEnvironment, messageBoardThreadResource),
			messageBoardThreadResource ->
				messageBoardThreadResource.postSiteMessageBoardThread(
					siteId, messageBoardThread));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent postSiteStructuredContent(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("structuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.postSiteStructuredContent(
					siteId, structuredContent));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent postStructuredContentFolderStructuredContent(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId,
			@GraphQLName("structuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.
					postStructuredContentFolderStructuredContent(
						structuredContentFolderId, structuredContent));
	}

	@GraphQLInvokeDetached
	public void deleteStructuredContent(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.deleteStructuredContent(
					structuredContentId));
	}

	@GraphQLInvokeDetached
	public StructuredContent patchStructuredContent(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("structuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.patchStructuredContent(
					structuredContentId, structuredContent));
	}

	@GraphQLInvokeDetached
	public StructuredContent putStructuredContent(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("structuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.putStructuredContent(
					structuredContentId, structuredContent));
	}

	@GraphQLInvokeDetached
	public void deleteStructuredContentMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.deleteStructuredContentMyRating(
					structuredContentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postStructuredContentMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.postStructuredContentMyRating(
					structuredContentId, rating));
	}

	@GraphQLInvokeDetached
	public Rating putStructuredContentMyRating(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			structuredContentResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentResource),
			structuredContentResource ->
				structuredContentResource.putStructuredContentMyRating(
					structuredContentId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContentFolder postSiteStructuredContentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("structuredContentFolder") StructuredContentFolder
				structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			structuredContentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentFolderResource),
			structuredContentFolderResource ->
				structuredContentFolderResource.postSiteStructuredContentFolder(
					siteId, structuredContentFolder));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContentFolder
			postStructuredContentFolderStructuredContentFolder(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("parentStructuredContentFolderId") Long
					parentStructuredContentFolderId,
				@GraphQLName("structuredContentFolder") StructuredContentFolder
					structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			structuredContentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentFolderResource),
			structuredContentFolderResource ->
				structuredContentFolderResource.
					postStructuredContentFolderStructuredContentFolder(
						parentStructuredContentFolderId,
						structuredContentFolder));
	}

	@GraphQLInvokeDetached
	public void deleteStructuredContentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			structuredContentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentFolderResource),
			structuredContentFolderResource ->
				structuredContentFolderResource.deleteStructuredContentFolder(
					structuredContentFolderId));
	}

	@GraphQLInvokeDetached
	public StructuredContentFolder patchStructuredContentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId,
			@GraphQLName("structuredContentFolder") StructuredContentFolder
				structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			structuredContentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentFolderResource),
			structuredContentFolderResource ->
				structuredContentFolderResource.patchStructuredContentFolder(
					structuredContentFolderId, structuredContentFolder));
	}

	@GraphQLInvokeDetached
	public StructuredContentFolder putStructuredContentFolder(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId,
			@GraphQLName("structuredContentFolder") StructuredContentFolder
				structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			structuredContentFolderResource -> _populateResourceContext(
				dataFetchingEnvironment, structuredContentFolderResource),
			structuredContentFolderResource ->
				structuredContentFolderResource.putStructuredContentFolder(
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