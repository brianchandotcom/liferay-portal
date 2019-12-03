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
import com.liferay.headless.delivery.dto.v1_0.WikiNode;
import com.liferay.headless.delivery.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.dto.v1_0.WikiPageAttachment;
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
import com.liferay.headless.delivery.resource.v1_0.WikiNodeResource;
import com.liferay.headless.delivery.resource.v1_0.WikiPageAttachmentResource;
import com.liferay.headless.delivery.resource.v1_0.WikiPageResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

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

	public static void setWikiPageAttachmentResourceComponentServiceObjects(
		ComponentServiceObjects<WikiPageAttachmentResource>
			wikiPageAttachmentResourceComponentServiceObjects) {

		_wikiPageAttachmentResourceComponentServiceObjects =
			wikiPageAttachmentResourceComponentServiceObjects;
	}

	@GraphQLField(
		description = "Deletes the blog post and returns a 204 if the operation succeeds."
	)
	public boolean deleteBlogPosting(
			@GraphQLName("blogPostingId") Long blogPostingId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.deleteBlogPosting(
				blogPostingId));

		return true;
	}

	@GraphQLField(
		description = "Updates the blog post using only the fields received in the request body. Any other fields are left untouched. Returns the updated blog post."
	)
	public BlogPosting patchBlogPosting(
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("blogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.patchBlogPosting(
				blogPostingId, blogPosting));
	}

	@GraphQLField(
		description = "Replaces the blog post with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public BlogPosting updateBlogPosting(
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("blogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.putBlogPosting(
				blogPostingId, blogPosting));
	}

	@GraphQLField(
		description = "Deletes the blog post rating of the user who authenticated the request."
	)
	public boolean deleteBlogPostingMyRating(
			@GraphQLName("blogPostingId") Long blogPostingId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource ->
				blogPostingResource.deleteBlogPostingMyRating(blogPostingId));

		return true;
	}

	@GraphQLField(
		description = "Creates a new blog post rating by the user who authenticated the request."
	)
	public Rating createBlogPostingMyRating(
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.postBlogPostingMyRating(
				blogPostingId, rating));
	}

	@GraphQLField(
		description = "Replaces an existing blog post rating by the user who authenticated the request."
	)
	public Rating updateBlogPostingMyRating(
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.putBlogPostingMyRating(
				blogPostingId, rating));
	}

	@GraphQLField(description = "Creates a new blog post.")
	public BlogPosting createSiteBlogPosting(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("blogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.postSiteBlogPosting(
				siteId, blogPosting));
	}

	@GraphQLField
	public boolean updateSiteBlogPostingSubscribe(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource ->
				blogPostingResource.putSiteBlogPostingSubscribe(siteId));

		return true;
	}

	@GraphQLField
	public boolean updateSiteBlogPostingUnsubscribe(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource ->
				blogPostingResource.putSiteBlogPostingUnsubscribe(siteId));

		return true;
	}

	@GraphQLField(description = "Deletes the blog post's image.")
	public boolean deleteBlogPostingImage(
			@GraphQLName("blogPostingImageId") Long blogPostingImageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.deleteBlogPostingImage(
					blogPostingImageId));

		return true;
	}

	@GraphQLField(
		description = "Creates a blog post image. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`blogPostingImage`) with the metadata."
	)
	@GraphQLName(
		value = "postSiteBlogPostingImageSiteIdMultipartBody",
		description = "Creates a blog post image. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`blogPostingImage`) with the metadata."
	)
	public BlogPostingImage createSiteBlogPostingImage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.postSiteBlogPostingImage(
					siteId, multipartBody));
	}

	@GraphQLField(description = "Creates a new comment on the blog post.")
	public Comment createBlogPostingComment(
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postBlogPostingComment(
				blogPostingId, comment));
	}

	@GraphQLField(
		description = "Deletes the comment and returns a 204 if the operation succeeded."
	)
	public boolean deleteComment(@GraphQLName("commentId") Long commentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.deleteComment(commentId));

		return true;
	}

	@GraphQLField(
		description = "Replaces the comment with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public Comment updateComment(
			@GraphQLName("commentId") Long commentId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.putComment(commentId, comment));
	}

	@GraphQLField(
		description = "Creates a new child comment of the existing comment."
	)
	public Comment createCommentComment(
			@GraphQLName("parentCommentId") Long parentCommentId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postCommentComment(
				parentCommentId, comment));
	}

	@GraphQLField(description = "Creates a new comment on the document.")
	public Comment createDocumentComment(
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postDocumentComment(
				documentId, comment));
	}

	@GraphQLField(
		description = "Creates a new comment on the structured content."
	)
	public Comment createStructuredContentComment(
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postStructuredContentComment(
				structuredContentId, comment));
	}

	@GraphQLField(
		description = "Creates a new document inside the folder identified by `documentFolderId`. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@GraphQLName(
		value = "postDocumentFolderDocumentDocumentFolderIdMultipartBody",
		description = "Creates a new document inside the folder identified by `documentFolderId`. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	public Document createDocumentFolderDocument(
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postDocumentFolderDocument(
				documentFolderId, multipartBody));
	}

	@GraphQLField(
		description = "Deletes the document and returns a 204 if the operation succeeds."
	)
	public boolean deleteDocument(@GraphQLName("documentId") Long documentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.deleteDocument(documentId));

		return true;
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body, leaving any other fields untouched. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@GraphQLName(
		value = "patchDocumentDocumentIdMultipartBody",
		description = "Updates only the fields received in the request body, leaving any other fields untouched. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	public Document patchDocument(
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.patchDocument(
				documentId, multipartBody));
	}

	@GraphQLField(
		description = "Replaces the document with the information sent in the request body. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@GraphQLName(
		value = "putDocumentDocumentIdMultipartBody",
		description = "Replaces the document with the information sent in the request body. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	public Document updateDocument(
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.putDocument(
				documentId, multipartBody));
	}

	@GraphQLField(
		description = "Deletes the document's rating and returns a 204 if the operation succeeded."
	)
	public boolean deleteDocumentMyRating(
			@GraphQLName("documentId") Long documentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.deleteDocumentMyRating(
				documentId));

		return true;
	}

	@GraphQLField(
		description = "Creates a new rating for the document, by the user who authenticated the request."
	)
	public Rating createDocumentMyRating(
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postDocumentMyRating(
				documentId, rating));
	}

	@GraphQLField(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public Rating updateDocumentMyRating(
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.putDocumentMyRating(
				documentId, rating));
	}

	@GraphQLField(
		description = "Creates a new document. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@GraphQLName(
		value = "postSiteDocumentSiteIdMultipartBody",
		description = "Creates a new document. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	public Document createSiteDocument(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postSiteDocument(
				siteId, multipartBody));
	}

	@GraphQLField(
		description = "Deletes the document folder and returns a 204 if the operation succeeds."
	)
	public boolean deleteDocumentFolder(
			@GraphQLName("documentFolderId") Long documentFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.deleteDocumentFolder(documentFolderId));

		return true;
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body. Any other fields are left untouched."
	)
	public DocumentFolder patchDocumentFolder(
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.patchDocumentFolder(
					documentFolderId, documentFolder));
	}

	@GraphQLField(
		description = "Replaces the document folder with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public DocumentFolder updateDocumentFolder(
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource -> documentFolderResource.putDocumentFolder(
				documentFolderId, documentFolder));
	}

	@GraphQLField
	public boolean updateDocumentFolderSubscribe(
			@GraphQLName("documentFolderId") Long documentFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.putDocumentFolderSubscribe(
					documentFolderId));

		return true;
	}

	@GraphQLField
	public boolean updateDocumentFolderUnsubscribe(
			@GraphQLName("documentFolderId") Long documentFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.putDocumentFolderUnsubscribe(
					documentFolderId));

		return true;
	}

	@GraphQLField(
		description = "Creates a new folder in a folder identified by `parentDocumentFolderId`."
	)
	public DocumentFolder createDocumentFolderDocumentFolder(
			@GraphQLName("parentDocumentFolderId") Long parentDocumentFolderId,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.postDocumentFolderDocumentFolder(
					parentDocumentFolderId, documentFolder));
	}

	@GraphQLField(description = "Creates a new document folder.")
	public DocumentFolder createSiteDocumentFolder(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.postSiteDocumentFolder(
					siteId, documentFolder));
	}

	@GraphQLField(
		description = "Deletes the knowledge base article and returns a 204 if the operation succeeds."
	)
	public boolean deleteKnowledgeBaseArticle(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
					knowledgeBaseArticleId));

		return true;
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.patchKnowledgeBaseArticle(
					knowledgeBaseArticleId, knowledgeBaseArticle));
	}

	@GraphQLField(
		description = "Replaces the knowledge base article with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public KnowledgeBaseArticle updateKnowledgeBaseArticle(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.putKnowledgeBaseArticle(
					knowledgeBaseArticleId, knowledgeBaseArticle));
	}

	@GraphQLField(
		description = "Deletes the knowledge base article's rating and returns a 204 if the operation succeeds."
	)
	public boolean deleteKnowledgeBaseArticleMyRating(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.deleteKnowledgeBaseArticleMyRating(
					knowledgeBaseArticleId));

		return true;
	}

	@GraphQLField(
		description = "Creates a rating for the knowledge base article."
	)
	public Rating createKnowledgeBaseArticleMyRating(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.postKnowledgeBaseArticleMyRating(
					knowledgeBaseArticleId, rating));
	}

	@GraphQLField(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public Rating updateKnowledgeBaseArticleMyRating(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.putKnowledgeBaseArticleMyRating(
					knowledgeBaseArticleId, rating));
	}

	@GraphQLField
	public boolean updateKnowledgeBaseArticleSubscribe(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.putKnowledgeBaseArticleSubscribe(
					knowledgeBaseArticleId));

		return true;
	}

	@GraphQLField
	public boolean updateKnowledgeBaseArticleUnsubscribe(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.putKnowledgeBaseArticleUnsubscribe(
					knowledgeBaseArticleId));

		return true;
	}

	@GraphQLField(
		description = "Creates a child knowledge base article of the knowledge base article identified by `parentKnowledgeBaseArticleId`."
	)
	public KnowledgeBaseArticle createKnowledgeBaseArticleKnowledgeBaseArticle(
			@GraphQLName("parentKnowledgeBaseArticleId") Long
				parentKnowledgeBaseArticleId,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					postKnowledgeBaseArticleKnowledgeBaseArticle(
						parentKnowledgeBaseArticleId, knowledgeBaseArticle));
	}

	@GraphQLField(
		description = "Creates a new knowledge base article in the folder."
	)
	public KnowledgeBaseArticle createKnowledgeBaseFolderKnowledgeBaseArticle(
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
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

	@GraphQLField(description = "Creates a new knowledge base article.")
	public KnowledgeBaseArticle createSiteKnowledgeBaseArticle(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
					siteId, knowledgeBaseArticle));
	}

	@GraphQLField
	public boolean updateSiteKnowledgeBaseArticleSubscribe(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					putSiteKnowledgeBaseArticleSubscribe(siteId));

		return true;
	}

	@GraphQLField
	public boolean updateSiteKnowledgeBaseArticleUnsubscribe(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					putSiteKnowledgeBaseArticleUnsubscribe(siteId));

		return true;
	}

	@GraphQLField(
		description = "Creates a new attachment for an existing knowledge base article. The request body must be `multipart/form-data` with two parts, a `file` part with the file's bytes, and an optional JSON string (`knowledgeBaseAttachment`) with the metadata."
	)
	@GraphQLName(
		value = "postKnowledgeBaseArticleKnowledgeBaseAttachmentKnowledgeBaseArticleIdMultipartBody",
		description = "Creates a new attachment for an existing knowledge base article. The request body must be `multipart/form-data` with two parts, a `file` part with the file's bytes, and an optional JSON string (`knowledgeBaseAttachment`) with the metadata."
	)
	public KnowledgeBaseAttachment
			createKnowledgeBaseArticleKnowledgeBaseAttachment(
				@GraphQLName("knowledgeBaseArticleId") Long
					knowledgeBaseArticleId,
				@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.
					postKnowledgeBaseArticleKnowledgeBaseAttachment(
						knowledgeBaseArticleId, multipartBody));
	}

	@GraphQLField(
		description = "Deletes the knowledge base file attachment and returns a 204 if the operation succeeds."
	)
	public boolean deleteKnowledgeBaseAttachment(
			@GraphQLName("knowledgeBaseAttachmentId") Long
				knowledgeBaseAttachmentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.deleteKnowledgeBaseAttachment(
					knowledgeBaseAttachmentId));

		return true;
	}

	@GraphQLField(
		description = "Deletes the knowledge base folder and returns a 204 if the operation succeeds."
	)
	public boolean deleteKnowledgeBaseFolder(
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.deleteKnowledgeBaseFolder(
					knowledgeBaseFolderId));

		return true;
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			@GraphQLName("knowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.patchKnowledgeBaseFolder(
					knowledgeBaseFolderId, knowledgeBaseFolder));
	}

	@GraphQLField(
		description = "Replaces the knowledge base folder with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public KnowledgeBaseFolder updateKnowledgeBaseFolder(
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			@GraphQLName("knowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.putKnowledgeBaseFolder(
					knowledgeBaseFolderId, knowledgeBaseFolder));
	}

	@GraphQLField(
		description = "Creates a knowledge base folder inside the parent folder."
	)
	public KnowledgeBaseFolder createKnowledgeBaseFolderKnowledgeBaseFolder(
			@GraphQLName("parentKnowledgeBaseFolderId") Long
				parentKnowledgeBaseFolderId,
			@GraphQLName("knowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.
					postKnowledgeBaseFolderKnowledgeBaseFolder(
						parentKnowledgeBaseFolderId, knowledgeBaseFolder));
	}

	@GraphQLField(description = "Creates a new knowledge base folder.")
	public KnowledgeBaseFolder createSiteKnowledgeBaseFolder(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("knowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
					siteId, knowledgeBaseFolder));
	}

	@GraphQLField(
		description = "Deletes the message board attachment and returns a 204 if the operation succeeds."
	)
	public boolean deleteMessageBoardAttachment(
			@GraphQLName("messageBoardAttachmentId") Long
				messageBoardAttachmentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.deleteMessageBoardAttachment(
					messageBoardAttachmentId));

		return true;
	}

	@GraphQLField(
		description = "Creates an attachment for the message board message. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`MessageBoardAttachment`) with the metadata."
	)
	@GraphQLName(
		value = "postMessageBoardMessageMessageBoardAttachmentMessageBoardMessageIdMultipartBody",
		description = "Creates an attachment for the message board message. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`MessageBoardAttachment`) with the metadata."
	)
	public MessageBoardAttachment
			createMessageBoardMessageMessageBoardAttachment(
				@GraphQLName("messageBoardMessageId") Long
					messageBoardMessageId,
				@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.
					postMessageBoardMessageMessageBoardAttachment(
						messageBoardMessageId, multipartBody));
	}

	@GraphQLField(
		description = "Creates a new attachment for the message board thread. The request body should be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`knowledgeBaseAttachment`) with the metadata."
	)
	@GraphQLName(
		value = "postMessageBoardThreadMessageBoardAttachmentMessageBoardThreadIdMultipartBody",
		description = "Creates a new attachment for the message board thread. The request body should be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`knowledgeBaseAttachment`) with the metadata."
	)
	public MessageBoardAttachment
			createMessageBoardThreadMessageBoardAttachment(
				@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
				@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.
					postMessageBoardThreadMessageBoardAttachment(
						messageBoardThreadId, multipartBody));
	}

	@GraphQLField(
		description = "Deletes the message board message and returns a 204 if the operation succeeds."
	)
	public boolean deleteMessageBoardMessage(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.deleteMessageBoardMessage(
					messageBoardMessageId));

		return true;
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	public MessageBoardMessage patchMessageBoardMessage(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("messageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.patchMessageBoardMessage(
					messageBoardMessageId, messageBoardMessage));
	}

	@GraphQLField(
		description = "Replaces the message board message with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public MessageBoardMessage updateMessageBoardMessage(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("messageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.putMessageBoardMessage(
					messageBoardMessageId, messageBoardMessage));
	}

	@GraphQLField(
		description = "Deletes the message board message's rating and returns a 204 if the operation succeeds."
	)
	public boolean deleteMessageBoardMessageMyRating(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.deleteMessageBoardMessageMyRating(
					messageBoardMessageId));

		return true;
	}

	@GraphQLField(
		description = "Creates a rating for the message board message."
	)
	public Rating createMessageBoardMessageMyRating(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.postMessageBoardMessageMyRating(
					messageBoardMessageId, rating));
	}

	@GraphQLField(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public Rating updateMessageBoardMessageMyRating(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.putMessageBoardMessageMyRating(
					messageBoardMessageId, rating));
	}

	@GraphQLField
	public boolean updateMessageBoardMessageSubscribe(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.putMessageBoardMessageSubscribe(
					messageBoardMessageId));

		return true;
	}

	@GraphQLField
	public boolean updateMessageBoardMessageUnsubscribe(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.putMessageBoardMessageUnsubscribe(
					messageBoardMessageId));

		return true;
	}

	@GraphQLField(
		description = "Creates a child message board message of the parent message."
	)
	public MessageBoardMessage createMessageBoardMessageMessageBoardMessage(
			@GraphQLName("parentMessageBoardMessageId") Long
				parentMessageBoardMessageId,
			@GraphQLName("messageBoardMessage") MessageBoardMessage
				messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.
					postMessageBoardMessageMessageBoardMessage(
						parentMessageBoardMessageId, messageBoardMessage));
	}

	@GraphQLField(
		description = "Creates a new message in the message board thread."
	)
	public MessageBoardMessage createMessageBoardThreadMessageBoardMessage(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("messageBoardMessage") MessageBoardMessage
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

	@GraphQLField(
		description = "Deletes the message board section and returns a 204 if the operation succeeds."
	)
	public boolean deleteMessageBoardSection(
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.deleteMessageBoardSection(
					messageBoardSectionId));

		return true;
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	public MessageBoardSection patchMessageBoardSection(
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId,
			@GraphQLName("messageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.patchMessageBoardSection(
					messageBoardSectionId, messageBoardSection));
	}

	@GraphQLField(
		description = "Replaces the message board section with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public MessageBoardSection updateMessageBoardSection(
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId,
			@GraphQLName("messageBoardSection") MessageBoardSection
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
	public boolean updateMessageBoardSectionSubscribe(
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.putMessageBoardSectionSubscribe(
					messageBoardSectionId));

		return true;
	}

	@GraphQLField
	public boolean updateMessageBoardSectionUnsubscribe(
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.putMessageBoardSectionUnsubscribe(
					messageBoardSectionId));

		return true;
	}

	@GraphQLField(
		description = "Creates a new message board section in the parent section."
	)
	public MessageBoardSection createMessageBoardSectionMessageBoardSection(
			@GraphQLName("parentMessageBoardSectionId") Long
				parentMessageBoardSectionId,
			@GraphQLName("messageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.
					postMessageBoardSectionMessageBoardSection(
						parentMessageBoardSectionId, messageBoardSection));
	}

	@GraphQLField(description = "Creates a new message board section.")
	public MessageBoardSection createSiteMessageBoardSection(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("messageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.postSiteMessageBoardSection(
					siteId, messageBoardSection));
	}

	@GraphQLField(
		description = "Creates a new message board thread inside a section."
	)
	public MessageBoardThread createMessageBoardSectionMessageBoardThread(
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId,
			@GraphQLName("messageBoardThread") MessageBoardThread
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

	@GraphQLField(
		description = "Deletes the message board thread and returns a 204 if the operation succeeds."
	)
	public boolean deleteMessageBoardThread(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.deleteMessageBoardThread(
					messageBoardThreadId));

		return true;
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	public MessageBoardThread patchMessageBoardThread(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("messageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.patchMessageBoardThread(
					messageBoardThreadId, messageBoardThread));
	}

	@GraphQLField(
		description = "Replaces the message board thread with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public MessageBoardThread updateMessageBoardThread(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("messageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.putMessageBoardThread(
					messageBoardThreadId, messageBoardThread));
	}

	@GraphQLField(
		description = "Deletes the message board thread's rating and returns a 204 if the operation succeeds."
	)
	public boolean deleteMessageBoardThreadMyRating(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.deleteMessageBoardThreadMyRating(
					messageBoardThreadId));

		return true;
	}

	@GraphQLField(description = "Creates the message board thread's rating.")
	public Rating createMessageBoardThreadMyRating(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.postMessageBoardThreadMyRating(
					messageBoardThreadId, rating));
	}

	@GraphQLField(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public Rating updateMessageBoardThreadMyRating(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.putMessageBoardThreadMyRating(
					messageBoardThreadId, rating));
	}

	@GraphQLField
	public boolean updateMessageBoardThreadSubscribe(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.putMessageBoardThreadSubscribe(
					messageBoardThreadId));

		return true;
	}

	@GraphQLField
	public boolean updateMessageBoardThreadUnsubscribe(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.putMessageBoardThreadUnsubscribe(
					messageBoardThreadId));

		return true;
	}

	@GraphQLField(description = "Creates a new message board thread.")
	public MessageBoardThread createSiteMessageBoardThread(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("messageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.postSiteMessageBoardThread(
					siteId, messageBoardThread));
	}

	@GraphQLField(description = "Creates a new structured content.")
	public StructuredContent createSiteStructuredContent(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("structuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.postSiteStructuredContent(
					siteId, structuredContent));
	}

	@GraphQLField(
		description = "Creates a new structured content in the folder."
	)
	public StructuredContent createStructuredContentFolderStructuredContent(
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId,
			@GraphQLName("structuredContent") StructuredContent
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

	@GraphQLField
	public boolean updateStructuredContentSubscribe(
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.putStructuredContentSubscribe(
					structuredContentId));

		return true;
	}

	@GraphQLField
	public boolean updateStructuredContentUnsubscribe(
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.putStructuredContentUnsubscribe(
					structuredContentId));

		return true;
	}

	@GraphQLField(
		description = "Deletes the structured content and returns a 204 if the operation succeeds."
	)
	public boolean deleteStructuredContent(
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.deleteStructuredContent(
					structuredContentId));

		return true;
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	public StructuredContent patchStructuredContent(
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("structuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.patchStructuredContent(
					structuredContentId, structuredContent));
	}

	@GraphQLField(
		description = "Replaces the structured content with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public StructuredContent updateStructuredContent(
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("structuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.putStructuredContent(
					structuredContentId, structuredContent));
	}

	@GraphQLField(
		description = "Deletes the structured content's rating and returns a 204 if the operation succeeds."
	)
	public boolean deleteStructuredContentMyRating(
			@GraphQLName("structuredContentId") Long structuredContentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.deleteStructuredContentMyRating(
					structuredContentId));

		return true;
	}

	@GraphQLField(description = "Create a rating for the structured content.")
	public Rating createStructuredContentMyRating(
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.postStructuredContentMyRating(
					structuredContentId, rating));
	}

	@GraphQLField(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public Rating updateStructuredContentMyRating(
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.putStructuredContentMyRating(
					structuredContentId, rating));
	}

	@GraphQLField(description = "Creates a new structured content folder.")
	public StructuredContentFolder createSiteStructuredContentFolder(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("structuredContentFolder") StructuredContentFolder
				structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.postSiteStructuredContentFolder(
					siteId, structuredContentFolder));
	}

	@GraphQLField(
		description = "Creates a new structured content folder in an existing folder."
	)
	public StructuredContentFolder
			createStructuredContentFolderStructuredContentFolder(
				@GraphQLName("parentStructuredContentFolderId") Long
					parentStructuredContentFolderId,
				@GraphQLName("structuredContentFolder") StructuredContentFolder
					structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.
					postStructuredContentFolderStructuredContentFolder(
						parentStructuredContentFolderId,
						structuredContentFolder));
	}

	@GraphQLField(
		description = "Deletes the structured content folder and returns a 204 if the operation succeeds."
	)
	public boolean deleteStructuredContentFolder(
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.deleteStructuredContentFolder(
					structuredContentFolderId));

		return true;
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	public StructuredContentFolder patchStructuredContentFolder(
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId,
			@GraphQLName("structuredContentFolder") StructuredContentFolder
				structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.patchStructuredContentFolder(
					structuredContentFolderId, structuredContentFolder));
	}

	@GraphQLField(
		description = "Replaces the structured content folder with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public StructuredContentFolder updateStructuredContentFolder(
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId,
			@GraphQLName("structuredContentFolder") StructuredContentFolder
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
	public boolean updateStructuredContentFolderSubscribe(
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.
					putStructuredContentFolderSubscribe(
						structuredContentFolderId));

		return true;
	}

	@GraphQLField
	public boolean updateStructuredContentFolderUnsubscribe(
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.
					putStructuredContentFolderUnsubscribe(
						structuredContentFolderId));

		return true;
	}

	@GraphQLField(description = "Creates a new wiki node")
	public WikiNode createSiteWikiNode(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("wikiNode") WikiNode wikiNode)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.postSiteWikiNode(
				siteId, wikiNode));
	}

	@GraphQLField
	public boolean updateWikiNodeSubscribe(
			@GraphQLName("wikiNodeId") Long wikiNodeId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.putWikiNodeSubscribe(
				wikiNodeId));

		return true;
	}

	@GraphQLField
	public boolean updateWikiNodeUnsubscribe(
			@GraphQLName("wikiNodeId") Long wikiNodeId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.putWikiNodeUnsubscribe(
				wikiNodeId));

		return true;
	}

	@GraphQLField(
		description = "Deletes the wiki node and returns a 204 if the operation succeeds."
	)
	public boolean deleteWikiNode(@GraphQLName("wikiNodeId") Long wikiNodeId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.deleteWikiNode(wikiNodeId));

		return true;
	}

	@GraphQLField(
		description = "Replaces the wiki node with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public WikiNode updateWikiNode(
			@GraphQLName("wikiNodeId") Long wikiNodeId,
			@GraphQLName("wikiNode") WikiNode wikiNode)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.putWikiNode(
				wikiNodeId, wikiNode));
	}

	@GraphQLField(description = "Creates a new wiki page")
	public WikiPage createWikiNodeWikiPage(
			@GraphQLName("wikiNodeId") Long wikiNodeId,
			@GraphQLName("wikiPage") WikiPage wikiPage)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.postWikiNodeWikiPage(
				wikiNodeId, wikiPage));
	}

	@GraphQLField
	public boolean updateWikiPageSubscribe(
			@GraphQLName("wikiPageId") Long wikiPageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.putWikiPageSubscribe(
				wikiPageId));

		return true;
	}

	@GraphQLField
	public boolean updateWikiPageUnsubscribe(
			@GraphQLName("wikiPageId") Long wikiPageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.putWikiPageUnsubscribe(
				wikiPageId));

		return true;
	}

	@GraphQLField(
		description = "Creates a child wiki page of the parent wiki page."
	)
	public WikiPage createWikiPageWikiPage(
			@GraphQLName("parentWikiPageId") Long parentWikiPageId,
			@GraphQLName("wikiPage") WikiPage wikiPage)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.postWikiPageWikiPage(
				parentWikiPageId, wikiPage));
	}

	@GraphQLField(
		description = "Deletes the wiki page and returns a 204 if the operation succeeds."
	)
	public boolean deleteWikiPage(@GraphQLName("wikiPageId") Long wikiPageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.deleteWikiPage(wikiPageId));

		return true;
	}

	@GraphQLField(
		description = "Replaces the wiki page with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public WikiPage updateWikiPage(
			@GraphQLName("wikiPageId") Long wikiPageId,
			@GraphQLName("wikiPage") WikiPage wikiPage)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.putWikiPage(
				wikiPageId, wikiPage));
	}

	@GraphQLField(
		description = "Deletes the wiki page attachment and returns a 204 if the operation succeeds."
	)
	public boolean deleteWikiPageAttachment(
			@GraphQLName("wikiPageAttachmentId") Long wikiPageAttachmentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wikiPageAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageAttachmentResource ->
				wikiPageAttachmentResource.deleteWikiPageAttachment(
					wikiPageAttachmentId));

		return true;
	}

	@GraphQLField(
		description = "Creates an attachment for the wiki page. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`WikiPageAttachment`) with the metadata."
	)
	@GraphQLName(
		value = "postWikiPageWikiPageAttachmentWikiPageIdMultipartBody",
		description = "Creates an attachment for the wiki page. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`WikiPageAttachment`) with the metadata."
	)
	public WikiPageAttachment createWikiPageWikiPageAttachment(
			@GraphQLName("wikiPageId") Long wikiPageId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageAttachmentResource ->
				wikiPageAttachmentResource.postWikiPageWikiPageAttachment(
					wikiPageId, multipartBody));
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

		blogPostingResource.setContextAcceptLanguage(_acceptLanguage);
		blogPostingResource.setContextCompany(_company);
		blogPostingResource.setContextHttpServletRequest(_httpServletRequest);
		blogPostingResource.setContextHttpServletResponse(_httpServletResponse);
		blogPostingResource.setContextUriInfo(_uriInfo);
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
		blogPostingImageResource.setContextUriInfo(_uriInfo);
		blogPostingImageResource.setContextUser(_user);
	}

	private void _populateResourceContext(CommentResource commentResource)
		throws Exception {

		commentResource.setContextAcceptLanguage(_acceptLanguage);
		commentResource.setContextCompany(_company);
		commentResource.setContextHttpServletRequest(_httpServletRequest);
		commentResource.setContextHttpServletResponse(_httpServletResponse);
		commentResource.setContextUriInfo(_uriInfo);
		commentResource.setContextUser(_user);
	}

	private void _populateResourceContext(DocumentResource documentResource)
		throws Exception {

		documentResource.setContextAcceptLanguage(_acceptLanguage);
		documentResource.setContextCompany(_company);
		documentResource.setContextHttpServletRequest(_httpServletRequest);
		documentResource.setContextHttpServletResponse(_httpServletResponse);
		documentResource.setContextUriInfo(_uriInfo);
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
		documentFolderResource.setContextUriInfo(_uriInfo);
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
		knowledgeBaseArticleResource.setContextUriInfo(_uriInfo);
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
		knowledgeBaseAttachmentResource.setContextUriInfo(_uriInfo);
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
		knowledgeBaseFolderResource.setContextUriInfo(_uriInfo);
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
		messageBoardAttachmentResource.setContextUriInfo(_uriInfo);
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
		messageBoardMessageResource.setContextUriInfo(_uriInfo);
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
		messageBoardSectionResource.setContextUriInfo(_uriInfo);
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
		messageBoardThreadResource.setContextUriInfo(_uriInfo);
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
		structuredContentResource.setContextUriInfo(_uriInfo);
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
		structuredContentFolderResource.setContextUriInfo(_uriInfo);
		structuredContentFolderResource.setContextUser(_user);
	}

	private void _populateResourceContext(WikiNodeResource wikiNodeResource)
		throws Exception {

		wikiNodeResource.setContextAcceptLanguage(_acceptLanguage);
		wikiNodeResource.setContextCompany(_company);
		wikiNodeResource.setContextHttpServletRequest(_httpServletRequest);
		wikiNodeResource.setContextHttpServletResponse(_httpServletResponse);
		wikiNodeResource.setContextUriInfo(_uriInfo);
		wikiNodeResource.setContextUser(_user);
	}

	private void _populateResourceContext(WikiPageResource wikiPageResource)
		throws Exception {

		wikiPageResource.setContextAcceptLanguage(_acceptLanguage);
		wikiPageResource.setContextCompany(_company);
		wikiPageResource.setContextHttpServletRequest(_httpServletRequest);
		wikiPageResource.setContextHttpServletResponse(_httpServletResponse);
		wikiPageResource.setContextUriInfo(_uriInfo);
		wikiPageResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			WikiPageAttachmentResource wikiPageAttachmentResource)
		throws Exception {

		wikiPageAttachmentResource.setContextAcceptLanguage(_acceptLanguage);
		wikiPageAttachmentResource.setContextCompany(_company);
		wikiPageAttachmentResource.setContextHttpServletRequest(
			_httpServletRequest);
		wikiPageAttachmentResource.setContextHttpServletResponse(
			_httpServletResponse);
		wikiPageAttachmentResource.setContextUriInfo(_uriInfo);
		wikiPageAttachmentResource.setContextUser(_user);
	}

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
	private static ComponentServiceObjects<WikiNodeResource>
		_wikiNodeResourceComponentServiceObjects;
	private static ComponentServiceObjects<WikiPageResource>
		_wikiPageResourceComponentServiceObjects;
	private static ComponentServiceObjects<WikiPageAttachmentResource>
		_wikiPageAttachmentResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;
	private User _user;

}