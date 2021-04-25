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
import com.liferay.headless.delivery.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.dto.v1_0.WikiNode;
import com.liferay.headless.delivery.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.dto.v1_0.WikiPageAttachment;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingResource;
import com.liferay.headless.delivery.resource.v1_0.CommentResource;
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
import com.liferay.headless.delivery.resource.v1_0.NavigationMenuResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentResource;
import com.liferay.headless.delivery.resource.v1_0.WikiNodeResource;
import com.liferay.headless.delivery.resource.v1_0.WikiPageAttachmentResource;
import com.liferay.headless.delivery.resource.v1_0.WikiPageResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.Response;
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

	public static void setNavigationMenuResourceComponentServiceObjects(
		ComponentServiceObjects<NavigationMenuResource>
			navigationMenuResourceComponentServiceObjects) {

		_navigationMenuResourceComponentServiceObjects =
			navigationMenuResourceComponentServiceObjects;
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

	@GraphQLField
	public Response deleteBlogPostingBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.deleteBlogPostingBatch(
				callbackURL, object));
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

	@GraphQLField
	public Response updateBlogPostingBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.putBlogPostingBatch(
				callbackURL, object));
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

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateBlogPostingPermission(
				@GraphQLName("blogPostingId") Long blogPostingId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> {
				Page paginationPage =
					blogPostingResource.putBlogPostingPermission(
						blogPostingId, permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField(description = "Creates a new blog post.")
	public BlogPosting createSiteBlogPosting(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("blogPosting") BlogPosting blogPosting)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.postSiteBlogPosting(
				Long.valueOf(siteKey), blogPosting));
	}

	@GraphQLField
	public Response createSiteBlogPostingBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> blogPostingResource.postSiteBlogPostingBatch(
				Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteBlogPostingPermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource -> {
				Page paginationPage =
					blogPostingResource.putSiteBlogPostingPermission(
						Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public boolean updateSiteBlogPostingSubscribe(
			@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource ->
				blogPostingResource.putSiteBlogPostingSubscribe(
					Long.valueOf(siteKey)));

		return true;
	}

	@GraphQLField
	public boolean updateSiteBlogPostingUnsubscribe(
			@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingResource ->
				blogPostingResource.putSiteBlogPostingUnsubscribe(
					Long.valueOf(siteKey)));

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

	@GraphQLField
	public Response deleteBlogPostingImageBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.deleteBlogPostingImageBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Creates a blog post image. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`blogPostingImage`) with the metadata."
	)
	@GraphQLName(
		description = "Creates a blog post image. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`blogPostingImage`) with the metadata.",
		value = "postSiteBlogPostingImageSiteIdMultipartBody"
	)
	public BlogPostingImage createSiteBlogPostingImage(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.postSiteBlogPostingImage(
					Long.valueOf(siteKey), multipartBody));
	}

	@GraphQLField
	public Response createSiteBlogPostingImageBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects,
			this::_populateResourceContext,
			blogPostingImageResource ->
				blogPostingImageResource.postSiteBlogPostingImageBatch(
					Long.valueOf(siteKey), multipartBody, callbackURL, object));
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

	@GraphQLField
	public Response createBlogPostingCommentBatch(
			@GraphQLName("blogPostingId") Long blogPostingId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postBlogPostingCommentBatch(
				blogPostingId, callbackURL, object));
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

	@GraphQLField
	public Response deleteCommentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.deleteCommentBatch(
				callbackURL, object));
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

	@GraphQLField
	public Response updateCommentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.putCommentBatch(
				callbackURL, object));
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

	@GraphQLField
	public Response createDocumentCommentBatch(
			@GraphQLName("documentId") Long documentId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postDocumentCommentBatch(
				documentId, callbackURL, object));
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

	@GraphQLField
	public Response createStructuredContentCommentBatch(
			@GraphQLName("structuredContentId") Long structuredContentId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource ->
				commentResource.postStructuredContentCommentBatch(
					structuredContentId, callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateAssetLibraryContentStructurePermission(
				@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentStructureResource -> {
				Page paginationPage =
					contentStructureResource.
						putAssetLibraryContentStructurePermission(
							Long.valueOf(assetLibraryId), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateContentStructurePermission(
				@GraphQLName("contentStructureId") Long contentStructureId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentStructureResource -> {
				Page paginationPage =
					contentStructureResource.putContentStructurePermission(
						contentStructureId, permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteContentStructurePermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects,
			this::_populateResourceContext,
			contentStructureResource -> {
				Page paginationPage =
					contentStructureResource.putSiteContentStructurePermission(
						Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLName(
		description = "null",
		value = "postAssetLibraryDocumentAssetLibraryIdMultipartBody"
	)
	public Document createAssetLibraryDocument(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postAssetLibraryDocument(
				Long.valueOf(assetLibraryId), multipartBody));
	}

	@GraphQLField
	public Response createAssetLibraryDocumentBatch(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postAssetLibraryDocumentBatch(
				Long.valueOf(assetLibraryId), multipartBody, callbackURL,
				object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateAssetLibraryDocumentPermission(
				@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> {
				Page paginationPage =
					documentResource.putAssetLibraryDocumentPermission(
						Long.valueOf(assetLibraryId), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField(
		description = "Creates a new document inside the folder identified by `documentFolderId`. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@GraphQLName(
		description = "Creates a new document inside the folder identified by `documentFolderId`. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.",
		value = "postDocumentFolderDocumentDocumentFolderIdMultipartBody"
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

	@GraphQLField
	public Response createDocumentFolderDocumentBatch(
			@GraphQLName("documentFolderId") Long documentFolderId,
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource ->
				documentResource.postDocumentFolderDocumentBatch(
					documentFolderId, multipartBody, callbackURL, object));
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

	@GraphQLField
	public Response deleteDocumentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.deleteDocumentBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Updates only the fields received in the request body, leaving any other fields untouched. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@GraphQLName(
		description = "Updates only the fields received in the request body, leaving any other fields untouched. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.",
		value = "patchDocumentDocumentIdMultipartBody"
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
		description = "Replaces the document with the information sent in the request body. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.",
		value = "putDocumentDocumentIdMultipartBody"
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

	@GraphQLField
	public Response updateDocumentBatch(
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.putDocumentBatch(
				multipartBody, callbackURL, object));
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

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateDocumentPermission(
				@GraphQLName("documentId") Long documentId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> {
				Page paginationPage = documentResource.putDocumentPermission(
					documentId, permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField(
		description = "Creates a new document. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@GraphQLName(
		description = "Creates a new document. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.",
		value = "postSiteDocumentSiteIdMultipartBody"
	)
	public Document createSiteDocument(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postSiteDocument(
				Long.valueOf(siteKey), multipartBody));
	}

	@GraphQLField
	public Response createSiteDocumentBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postSiteDocumentBatch(
				Long.valueOf(siteKey), multipartBody, callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteDocumentPermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> {
				Page paginationPage =
					documentResource.putSiteDocumentPermission(
						Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public DocumentFolder createAssetLibraryDocumentFolder(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.postAssetLibraryDocumentFolder(
					Long.valueOf(assetLibraryId), documentFolder));
	}

	@GraphQLField
	public Response createAssetLibraryDocumentFolderBatch(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.postAssetLibraryDocumentFolderBatch(
					Long.valueOf(assetLibraryId), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateAssetLibraryDocumentFolderPermission(
				@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource -> {
				Page paginationPage =
					documentFolderResource.
						putAssetLibraryDocumentFolderPermission(
							Long.valueOf(assetLibraryId), permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response deleteDocumentFolderBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.deleteDocumentFolderBatch(
					callbackURL, object));
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
	public Response updateDocumentFolderBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.putDocumentFolderBatch(
					callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateDocumentFolderPermission(
				@GraphQLName("documentFolderId") Long documentFolderId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource -> {
				Page paginationPage =
					documentFolderResource.putDocumentFolderPermission(
						documentFolderId, permissions);

				return paginationPage.getItems();
			});
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
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("documentFolder") DocumentFolder documentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.postSiteDocumentFolder(
					Long.valueOf(siteKey), documentFolder));
	}

	@GraphQLField
	public Response createSiteDocumentFolderBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource ->
				documentFolderResource.postSiteDocumentFolderBatch(
					Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteDocumentFolderPermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentFolderResource -> {
				Page paginationPage =
					documentFolderResource.putSiteDocumentFolderPermission(
						Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response deleteKnowledgeBaseArticleBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.deleteKnowledgeBaseArticleBatch(
					callbackURL, object));
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

	@GraphQLField
	public Response updateKnowledgeBaseArticleBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.putKnowledgeBaseArticleBatch(
					callbackURL, object));
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
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateKnowledgeBaseArticlePermission(
				@GraphQLName("knowledgeBaseArticleId") Long
					knowledgeBaseArticleId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						putKnowledgeBaseArticlePermission(
							knowledgeBaseArticleId, permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response createKnowledgeBaseFolderKnowledgeBaseArticleBatch(
			@GraphQLName("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					postKnowledgeBaseFolderKnowledgeBaseArticleBatch(
						knowledgeBaseFolderId, callbackURL, object));
	}

	@GraphQLField(description = "Creates a new knowledge base article.")
	public KnowledgeBaseArticle createSiteKnowledgeBaseArticle(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("knowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
					Long.valueOf(siteKey), knowledgeBaseArticle));
	}

	@GraphQLField
	public Response createSiteKnowledgeBaseArticleBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.postSiteKnowledgeBaseArticleBatch(
					Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteKnowledgeBaseArticlePermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource -> {
				Page paginationPage =
					knowledgeBaseArticleResource.
						putSiteKnowledgeBaseArticlePermission(
							Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public boolean updateSiteKnowledgeBaseArticleSubscribe(
			@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					putSiteKnowledgeBaseArticleSubscribe(
						Long.valueOf(siteKey)));

		return true;
	}

	@GraphQLField
	public boolean updateSiteKnowledgeBaseArticleUnsubscribe(
			@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseArticleResource ->
				knowledgeBaseArticleResource.
					putSiteKnowledgeBaseArticleUnsubscribe(
						Long.valueOf(siteKey)));

		return true;
	}

	@GraphQLField(
		description = "Creates a new attachment for an existing knowledge base article. The request body must be `multipart/form-data` with two parts, a `file` part with the file's bytes, and an optional JSON string (`knowledgeBaseAttachment`) with the metadata."
	)
	@GraphQLName(
		description = "Creates a new attachment for an existing knowledge base article. The request body must be `multipart/form-data` with two parts, a `file` part with the file's bytes, and an optional JSON string (`knowledgeBaseAttachment`) with the metadata.",
		value = "postKnowledgeBaseArticleKnowledgeBaseAttachmentKnowledgeBaseArticleIdMultipartBody"
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

	@GraphQLField
	public Response createKnowledgeBaseArticleKnowledgeBaseAttachmentBatch(
			@GraphQLName("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.
					postKnowledgeBaseArticleKnowledgeBaseAttachmentBatch(
						knowledgeBaseArticleId, multipartBody, callbackURL,
						object));
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

	@GraphQLField
	public Response deleteKnowledgeBaseAttachmentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseAttachmentResource ->
				knowledgeBaseAttachmentResource.
					deleteKnowledgeBaseAttachmentBatch(callbackURL, object));
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

	@GraphQLField
	public Response deleteKnowledgeBaseFolderBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.deleteKnowledgeBaseFolderBatch(
					callbackURL, object));
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

	@GraphQLField
	public Response updateKnowledgeBaseFolderBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.putKnowledgeBaseFolderBatch(
					callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateKnowledgeBaseFolderPermission(
				@GraphQLName("knowledgeBaseFolderId") Long
					knowledgeBaseFolderId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource -> {
				Page paginationPage =
					knowledgeBaseFolderResource.
						putKnowledgeBaseFolderPermission(
							knowledgeBaseFolderId, permissions);

				return paginationPage.getItems();
			});
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
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("knowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
					Long.valueOf(siteKey), knowledgeBaseFolder));
	}

	@GraphQLField
	public Response createSiteKnowledgeBaseFolderBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource ->
				knowledgeBaseFolderResource.postSiteKnowledgeBaseFolderBatch(
					Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteKnowledgeBaseFolderPermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			knowledgeBaseFolderResource -> {
				Page paginationPage =
					knowledgeBaseFolderResource.
						putSiteKnowledgeBaseFolderPermission(
							Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response deleteMessageBoardAttachmentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.
					deleteMessageBoardAttachmentBatch(callbackURL, object));
	}

	@GraphQLField(
		description = "Creates an attachment for the message board message. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`MessageBoardAttachment`) with the metadata."
	)
	@GraphQLName(
		description = "Creates an attachment for the message board message. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`MessageBoardAttachment`) with the metadata.",
		value = "postMessageBoardMessageMessageBoardAttachmentMessageBoardMessageIdMultipartBody"
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

	@GraphQLField
	public Response createMessageBoardMessageMessageBoardAttachmentBatch(
			@GraphQLName("messageBoardMessageId") Long messageBoardMessageId,
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.
					postMessageBoardMessageMessageBoardAttachmentBatch(
						messageBoardMessageId, multipartBody, callbackURL,
						object));
	}

	@GraphQLField(
		description = "Creates a new attachment for the message board thread. The request body should be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`knowledgeBaseAttachment`) with the metadata."
	)
	@GraphQLName(
		description = "Creates a new attachment for the message board thread. The request body should be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`knowledgeBaseAttachment`) with the metadata.",
		value = "postMessageBoardThreadMessageBoardAttachmentMessageBoardThreadIdMultipartBody"
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

	@GraphQLField
	public Response createMessageBoardThreadMessageBoardAttachmentBatch(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardAttachmentResource ->
				messageBoardAttachmentResource.
					postMessageBoardThreadMessageBoardAttachmentBatch(
						messageBoardThreadId, multipartBody, callbackURL,
						object));
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

	@GraphQLField
	public Response deleteMessageBoardMessageBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.deleteMessageBoardMessageBatch(
					callbackURL, object));
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

	@GraphQLField
	public Response updateMessageBoardMessageBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.putMessageBoardMessageBatch(
					callbackURL, object));
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
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateMessageBoardMessagePermission(
				@GraphQLName("messageBoardMessageId") Long
					messageBoardMessageId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource -> {
				Page paginationPage =
					messageBoardMessageResource.
						putMessageBoardMessagePermission(
							messageBoardMessageId, permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response createMessageBoardThreadMessageBoardMessageBatch(
			@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.
					postMessageBoardThreadMessageBoardMessageBatch(
						messageBoardThreadId, callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes the site's message board message by external reference code."
	)
	public boolean deleteSiteMessageBoardMessageByExternalReferenceCode(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.
					deleteSiteMessageBoardMessageByExternalReferenceCode(
						Long.valueOf(siteKey), externalReferenceCode));

		return true;
	}

	@GraphQLField(
		description = "Updates the site's message board message with the given external reference code, or creates it if it not exists."
	)
	public MessageBoardMessage
			updateSiteMessageBoardMessageByExternalReferenceCode(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("messageBoardMessage") MessageBoardMessage
					messageBoardMessage)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource ->
				messageBoardMessageResource.
					putSiteMessageBoardMessageByExternalReferenceCode(
						Long.valueOf(siteKey), externalReferenceCode,
						messageBoardMessage));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteMessageBoardMessagePermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardMessageResource -> {
				Page paginationPage =
					messageBoardMessageResource.
						putSiteMessageBoardMessagePermission(
							Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response deleteMessageBoardSectionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.deleteMessageBoardSectionBatch(
					callbackURL, object));
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
	public Response updateMessageBoardSectionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.putMessageBoardSectionBatch(
					callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateMessageBoardSectionPermission(
				@GraphQLName("messageBoardSectionId") Long
					messageBoardSectionId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource -> {
				Page paginationPage =
					messageBoardSectionResource.
						putMessageBoardSectionPermission(
							messageBoardSectionId, permissions);

				return paginationPage.getItems();
			});
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
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("messageBoardSection") MessageBoardSection
				messageBoardSection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.postSiteMessageBoardSection(
					Long.valueOf(siteKey), messageBoardSection));
	}

	@GraphQLField
	public Response createSiteMessageBoardSectionBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource ->
				messageBoardSectionResource.postSiteMessageBoardSectionBatch(
					Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteMessageBoardSectionPermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardSectionResource -> {
				Page paginationPage =
					messageBoardSectionResource.
						putSiteMessageBoardSectionPermission(
							Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response createMessageBoardSectionMessageBoardThreadBatch(
			@GraphQLName("messageBoardSectionId") Long messageBoardSectionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.
					postMessageBoardSectionMessageBoardThreadBatch(
						messageBoardSectionId, callbackURL, object));
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

	@GraphQLField
	public Response deleteMessageBoardThreadBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.deleteMessageBoardThreadBatch(
					callbackURL, object));
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

	@GraphQLField
	public Response updateMessageBoardThreadBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.putMessageBoardThreadBatch(
					callbackURL, object));
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
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateMessageBoardThreadPermission(
				@GraphQLName("messageBoardThreadId") Long messageBoardThreadId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource -> {
				Page paginationPage =
					messageBoardThreadResource.putMessageBoardThreadPermission(
						messageBoardThreadId, permissions);

				return paginationPage.getItems();
			});
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
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("messageBoardThread") MessageBoardThread
				messageBoardThread)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.postSiteMessageBoardThread(
					Long.valueOf(siteKey), messageBoardThread));
	}

	@GraphQLField
	public Response createSiteMessageBoardThreadBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource ->
				messageBoardThreadResource.postSiteMessageBoardThreadBatch(
					Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteMessageBoardThreadPermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageBoardThreadResource -> {
				Page paginationPage =
					messageBoardThreadResource.
						putSiteMessageBoardThreadPermission(
							Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField(
		description = "Deletes the navigation menu and returns a 204 if the operation succeeds"
	)
	public boolean deleteNavigationMenu(
			@GraphQLName("navigationMenuId") Long navigationMenuId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects,
			this::_populateResourceContext,
			navigationMenuResource ->
				navigationMenuResource.deleteNavigationMenu(navigationMenuId));

		return true;
	}

	@GraphQLField
	public Response deleteNavigationMenuBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects,
			this::_populateResourceContext,
			navigationMenuResource ->
				navigationMenuResource.deleteNavigationMenuBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Replaces the navigation menu with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	public NavigationMenu updateNavigationMenu(
			@GraphQLName("navigationMenuId") Long navigationMenuId,
			@GraphQLName("navigationMenu") NavigationMenu navigationMenu)
		throws Exception {

		return _applyComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects,
			this::_populateResourceContext,
			navigationMenuResource -> navigationMenuResource.putNavigationMenu(
				navigationMenuId, navigationMenu));
	}

	@GraphQLField
	public Response updateNavigationMenuBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects,
			this::_populateResourceContext,
			navigationMenuResource ->
				navigationMenuResource.putNavigationMenuBatch(
					callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateNavigationMenuPermission(
				@GraphQLName("navigationMenuId") Long navigationMenuId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects,
			this::_populateResourceContext,
			navigationMenuResource -> {
				Page paginationPage =
					navigationMenuResource.putNavigationMenuPermission(
						navigationMenuId, permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField(description = "Creates a new navigation menu.")
	public NavigationMenu createSiteNavigationMenu(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("navigationMenu") NavigationMenu navigationMenu)
		throws Exception {

		return _applyComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects,
			this::_populateResourceContext,
			navigationMenuResource ->
				navigationMenuResource.postSiteNavigationMenu(
					Long.valueOf(siteKey), navigationMenu));
	}

	@GraphQLField
	public Response createSiteNavigationMenuBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects,
			this::_populateResourceContext,
			navigationMenuResource ->
				navigationMenuResource.postSiteNavigationMenuBatch(
					Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteNavigationMenuPermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects,
			this::_populateResourceContext,
			navigationMenuResource -> {
				Page paginationPage =
					navigationMenuResource.putSiteNavigationMenuPermission(
						Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public StructuredContent createAssetLibraryStructuredContent(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("structuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.postAssetLibraryStructuredContent(
					Long.valueOf(assetLibraryId), structuredContent));
	}

	@GraphQLField
	public Response createAssetLibraryStructuredContentBatch(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.
					postAssetLibraryStructuredContentBatch(
						Long.valueOf(assetLibraryId), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateAssetLibraryStructuredContentPermission(
				@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource -> {
				Page paginationPage =
					structuredContentResource.
						putAssetLibraryStructuredContentPermission(
							Long.valueOf(assetLibraryId), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField(description = "Creates a new structured content.")
	public StructuredContent createSiteStructuredContent(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("structuredContent") StructuredContent
				structuredContent)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.postSiteStructuredContent(
					Long.valueOf(siteKey), structuredContent));
	}

	@GraphQLField
	public Response createSiteStructuredContentBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.postSiteStructuredContentBatch(
					Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteStructuredContentPermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource -> {
				Page paginationPage =
					structuredContentResource.
						putSiteStructuredContentPermission(
							Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
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
	public Response createStructuredContentFolderStructuredContentBatch(
			@GraphQLName("structuredContentFolderId") Long
				structuredContentFolderId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.
					postStructuredContentFolderStructuredContentBatch(
						structuredContentFolderId, callbackURL, object));
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

	@GraphQLField
	public Response deleteStructuredContentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.deleteStructuredContentBatch(
					callbackURL, object));
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

	@GraphQLField
	public Response updateStructuredContentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource ->
				structuredContentResource.putStructuredContentBatch(
					callbackURL, object));
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

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateStructuredContentPermission(
				@GraphQLName("structuredContentId") Long structuredContentId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentResource -> {
				Page paginationPage =
					structuredContentResource.putStructuredContentPermission(
						structuredContentId, permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public StructuredContentFolder createAssetLibraryStructuredContentFolder(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("structuredContentFolder") StructuredContentFolder
				structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.
					postAssetLibraryStructuredContentFolder(
						Long.valueOf(assetLibraryId), structuredContentFolder));
	}

	@GraphQLField
	public Response createAssetLibraryStructuredContentFolderBatch(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.
					postAssetLibraryStructuredContentFolderBatch(
						Long.valueOf(assetLibraryId), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateAssetLibraryStructuredContentFolderPermission(
				@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource -> {
				Page paginationPage =
					structuredContentFolderResource.
						putAssetLibraryStructuredContentFolderPermission(
							Long.valueOf(assetLibraryId), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField(description = "Creates a new structured content folder.")
	public StructuredContentFolder createSiteStructuredContentFolder(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("structuredContentFolder") StructuredContentFolder
				structuredContentFolder)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.postSiteStructuredContentFolder(
					Long.valueOf(siteKey), structuredContentFolder));
	}

	@GraphQLField
	public Response createSiteStructuredContentFolderBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.
					postSiteStructuredContentFolderBatch(
						Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteStructuredContentFolderPermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource -> {
				Page paginationPage =
					structuredContentFolderResource.
						putSiteStructuredContentFolderPermission(
							Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateStructuredContentFolderPermission(
				@GraphQLName("structuredContentFolderId") Long
					structuredContentFolderId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource -> {
				Page paginationPage =
					structuredContentFolderResource.
						putStructuredContentFolderPermission(
							structuredContentFolderId, permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response deleteStructuredContentFolderBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.
					deleteStructuredContentFolderBatch(callbackURL, object));
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
	public Response updateStructuredContentFolderBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			structuredContentFolderResource ->
				structuredContentFolderResource.putStructuredContentFolderBatch(
					callbackURL, object));
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
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("wikiNode") WikiNode wikiNode)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.postSiteWikiNode(
				Long.valueOf(siteKey), wikiNode));
	}

	@GraphQLField
	public Response createSiteWikiNodeBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.postSiteWikiNodeBatch(
				Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes the site's wiki node by external reference code."
	)
	public boolean deleteSiteWikiNodeByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource ->
				wikiNodeResource.deleteSiteWikiNodeByExternalReferenceCode(
					externalReferenceCode, Long.valueOf(siteKey)));

		return true;
	}

	@GraphQLField(
		description = "Updates the site's wiki node with the given external reference code, or creates it if it not exists."
	)
	public WikiNode updateSiteWikiNodeByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("wikiNode") WikiNode wikiNode)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource ->
				wikiNodeResource.putSiteWikiNodeByExternalReferenceCode(
					externalReferenceCode, Long.valueOf(siteKey), wikiNode));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateSiteWikiNodePermission(
				@GraphQLName("siteKey") @NotEmpty String siteKey,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> {
				Page paginationPage =
					wikiNodeResource.putSiteWikiNodePermission(
						Long.valueOf(siteKey), permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response deleteWikiNodeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.deleteWikiNodeBatch(
				callbackURL, object));
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

	@GraphQLField
	public Response updateWikiNodeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> wikiNodeResource.putWikiNodeBatch(
				callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateWikiNodePermission(
				@GraphQLName("wikiNodeId") Long wikiNodeId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiNodeResource -> {
				Page paginationPage = wikiNodeResource.putWikiNodePermission(
					wikiNodeId, permissions);

				return paginationPage.getItems();
			});
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
		description = "Deletes the wiki page by external reference code."
	)
	public boolean deleteSiteWikiPageByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource ->
				wikiPageResource.deleteSiteWikiPageByExternalReferenceCode(
					externalReferenceCode, Long.valueOf(siteKey)));

		return true;
	}

	@GraphQLField(
		description = "Updates the wiki page with the given external reference code, or creates it if it not exists."
	)
	public WikiPage updateSiteWikiPageByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("wikiPage") WikiPage wikiPage)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource ->
				wikiPageResource.putSiteWikiPageByExternalReferenceCode(
					externalReferenceCode, Long.valueOf(siteKey), wikiPage));
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
	public Response createWikiNodeWikiPageBatch(
			@GraphQLName("wikiNodeId") Long wikiNodeId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.postWikiNodeWikiPageBatch(
				wikiNodeId, callbackURL, object));
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

	@GraphQLField
	public Response deleteWikiPageBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.deleteWikiPageBatch(
				callbackURL, object));
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

	@GraphQLField
	public Response updateWikiPageBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> wikiPageResource.putWikiPageBatch(
				callbackURL, object));
	}

	@GraphQLField
	public java.util.Collection<com.liferay.portal.vulcan.permission.Permission>
			updateWikiPagePermission(
				@GraphQLName("wikiPageId") Long wikiPageId,
				@GraphQLName("permissions")
					com.liferay.portal.vulcan.permission.Permission[]
						permissions)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageResource -> {
				Page paginationPage = wikiPageResource.putWikiPagePermission(
					wikiPageId, permissions);

				return paginationPage.getItems();
			});
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

	@GraphQLField
	public Response deleteWikiPageAttachmentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageAttachmentResource ->
				wikiPageAttachmentResource.deleteWikiPageAttachmentBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Creates an attachment for the wiki page. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`WikiPageAttachment`) with the metadata."
	)
	@GraphQLName(
		description = "Creates an attachment for the wiki page. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`WikiPageAttachment`) with the metadata.",
		value = "postWikiPageWikiPageAttachmentWikiPageIdMultipartBody"
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

	@GraphQLField
	public Response createWikiPageWikiPageAttachmentBatch(
			@GraphQLName("wikiPageId") Long wikiPageId,
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wikiPageAttachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			wikiPageAttachmentResource ->
				wikiPageAttachmentResource.postWikiPageWikiPageAttachmentBatch(
					wikiPageId, multipartBody, callbackURL, object));
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
		blogPostingResource.setGroupLocalService(_groupLocalService);
		blogPostingResource.setRoleLocalService(_roleLocalService);
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
		blogPostingImageResource.setGroupLocalService(_groupLocalService);
		blogPostingImageResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(CommentResource commentResource)
		throws Exception {

		commentResource.setContextAcceptLanguage(_acceptLanguage);
		commentResource.setContextCompany(_company);
		commentResource.setContextHttpServletRequest(_httpServletRequest);
		commentResource.setContextHttpServletResponse(_httpServletResponse);
		commentResource.setContextUriInfo(_uriInfo);
		commentResource.setContextUser(_user);
		commentResource.setGroupLocalService(_groupLocalService);
		commentResource.setRoleLocalService(_roleLocalService);
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
		contentStructureResource.setContextUriInfo(_uriInfo);
		contentStructureResource.setContextUser(_user);
		contentStructureResource.setGroupLocalService(_groupLocalService);
		contentStructureResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(DocumentResource documentResource)
		throws Exception {

		documentResource.setContextAcceptLanguage(_acceptLanguage);
		documentResource.setContextCompany(_company);
		documentResource.setContextHttpServletRequest(_httpServletRequest);
		documentResource.setContextHttpServletResponse(_httpServletResponse);
		documentResource.setContextUriInfo(_uriInfo);
		documentResource.setContextUser(_user);
		documentResource.setGroupLocalService(_groupLocalService);
		documentResource.setRoleLocalService(_roleLocalService);
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
		documentFolderResource.setGroupLocalService(_groupLocalService);
		documentFolderResource.setRoleLocalService(_roleLocalService);
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
		knowledgeBaseArticleResource.setGroupLocalService(_groupLocalService);
		knowledgeBaseArticleResource.setRoleLocalService(_roleLocalService);
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
		knowledgeBaseAttachmentResource.setGroupLocalService(
			_groupLocalService);
		knowledgeBaseAttachmentResource.setRoleLocalService(_roleLocalService);
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
		knowledgeBaseFolderResource.setGroupLocalService(_groupLocalService);
		knowledgeBaseFolderResource.setRoleLocalService(_roleLocalService);
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
		messageBoardAttachmentResource.setGroupLocalService(_groupLocalService);
		messageBoardAttachmentResource.setRoleLocalService(_roleLocalService);
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
		messageBoardMessageResource.setGroupLocalService(_groupLocalService);
		messageBoardMessageResource.setRoleLocalService(_roleLocalService);
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
		messageBoardSectionResource.setGroupLocalService(_groupLocalService);
		messageBoardSectionResource.setRoleLocalService(_roleLocalService);
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
		messageBoardThreadResource.setGroupLocalService(_groupLocalService);
		messageBoardThreadResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			NavigationMenuResource navigationMenuResource)
		throws Exception {

		navigationMenuResource.setContextAcceptLanguage(_acceptLanguage);
		navigationMenuResource.setContextCompany(_company);
		navigationMenuResource.setContextHttpServletRequest(
			_httpServletRequest);
		navigationMenuResource.setContextHttpServletResponse(
			_httpServletResponse);
		navigationMenuResource.setContextUriInfo(_uriInfo);
		navigationMenuResource.setContextUser(_user);
		navigationMenuResource.setGroupLocalService(_groupLocalService);
		navigationMenuResource.setRoleLocalService(_roleLocalService);
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
		structuredContentResource.setGroupLocalService(_groupLocalService);
		structuredContentResource.setRoleLocalService(_roleLocalService);
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
		structuredContentFolderResource.setGroupLocalService(
			_groupLocalService);
		structuredContentFolderResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(WikiNodeResource wikiNodeResource)
		throws Exception {

		wikiNodeResource.setContextAcceptLanguage(_acceptLanguage);
		wikiNodeResource.setContextCompany(_company);
		wikiNodeResource.setContextHttpServletRequest(_httpServletRequest);
		wikiNodeResource.setContextHttpServletResponse(_httpServletResponse);
		wikiNodeResource.setContextUriInfo(_uriInfo);
		wikiNodeResource.setContextUser(_user);
		wikiNodeResource.setGroupLocalService(_groupLocalService);
		wikiNodeResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(WikiPageResource wikiPageResource)
		throws Exception {

		wikiPageResource.setContextAcceptLanguage(_acceptLanguage);
		wikiPageResource.setContextCompany(_company);
		wikiPageResource.setContextHttpServletRequest(_httpServletRequest);
		wikiPageResource.setContextHttpServletResponse(_httpServletResponse);
		wikiPageResource.setContextUriInfo(_uriInfo);
		wikiPageResource.setContextUser(_user);
		wikiPageResource.setGroupLocalService(_groupLocalService);
		wikiPageResource.setRoleLocalService(_roleLocalService);
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
		wikiPageAttachmentResource.setGroupLocalService(_groupLocalService);
		wikiPageAttachmentResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<BlogPostingResource>
		_blogPostingResourceComponentServiceObjects;
	private static ComponentServiceObjects<BlogPostingImageResource>
		_blogPostingImageResourceComponentServiceObjects;
	private static ComponentServiceObjects<CommentResource>
		_commentResourceComponentServiceObjects;
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
	private static ComponentServiceObjects<NavigationMenuResource>
		_navigationMenuResourceComponentServiceObjects;
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
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}