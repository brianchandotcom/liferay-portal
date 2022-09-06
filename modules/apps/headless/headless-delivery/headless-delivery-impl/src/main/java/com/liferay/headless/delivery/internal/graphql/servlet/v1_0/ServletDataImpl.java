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

package com.liferay.headless.delivery.internal.graphql.servlet.v1_0;

import com.liferay.headless.delivery.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.delivery.internal.graphql.query.v1_0.Query;
import com.liferay.headless.delivery.internal.resource.v1_0.BlogPostingImageResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.BlogPostingResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.CommentResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.ContentElementResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.ContentSetElementResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.ContentStructureResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.ContentTemplateResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.DocumentFolderResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.DocumentResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.KnowledgeBaseArticleResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.KnowledgeBaseAttachmentResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.KnowledgeBaseFolderResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.LanguageResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.MessageBoardAttachmentResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.MessageBoardMessageResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.MessageBoardSectionResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.MessageBoardThreadResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.NavigationMenuResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.SitePageResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.StructuredContentFolderResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.StructuredContentResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.WikiNodeResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.WikiPageAttachmentResourceImpl;
import com.liferay.headless.delivery.internal.resource.v1_0.WikiPageResourceImpl;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingResource;
import com.liferay.headless.delivery.resource.v1_0.CommentResource;
import com.liferay.headless.delivery.resource.v1_0.ContentElementResource;
import com.liferay.headless.delivery.resource.v1_0.ContentSetElementResource;
import com.liferay.headless.delivery.resource.v1_0.ContentStructureResource;
import com.liferay.headless.delivery.resource.v1_0.ContentTemplateResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.headless.delivery.resource.v1_0.LanguageResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardAttachmentResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardMessageResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardSectionResource;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardThreadResource;
import com.liferay.headless.delivery.resource.v1_0.NavigationMenuResource;
import com.liferay.headless.delivery.resource.v1_0.SitePageResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentResource;
import com.liferay.headless.delivery.resource.v1_0.WikiNodeResource;
import com.liferay.headless.delivery.resource.v1_0.WikiPageAttachmentResource;
import com.liferay.headless.delivery.resource.v1_0.WikiPageResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setBlogPostingResourceComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects);
		Mutation.setBlogPostingImageResourceComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects);
		Mutation.setCommentResourceComponentServiceObjects(
			_commentResourceComponentServiceObjects);
		Mutation.setContentStructureResourceComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects);
		Mutation.setDocumentResourceComponentServiceObjects(
			_documentResourceComponentServiceObjects);
		Mutation.setDocumentFolderResourceComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects);
		Mutation.setKnowledgeBaseArticleResourceComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects);
		Mutation.setKnowledgeBaseAttachmentResourceComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects);
		Mutation.setKnowledgeBaseFolderResourceComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects);
		Mutation.setMessageBoardAttachmentResourceComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects);
		Mutation.setMessageBoardMessageResourceComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects);
		Mutation.setMessageBoardSectionResourceComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects);
		Mutation.setMessageBoardThreadResourceComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects);
		Mutation.setNavigationMenuResourceComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects);
		Mutation.setStructuredContentResourceComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects);
		Mutation.setStructuredContentFolderResourceComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects);
		Mutation.setWikiNodeResourceComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects);
		Mutation.setWikiPageResourceComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects);
		Mutation.setWikiPageAttachmentResourceComponentServiceObjects(
			_wikiPageAttachmentResourceComponentServiceObjects);

		Query.setBlogPostingResourceComponentServiceObjects(
			_blogPostingResourceComponentServiceObjects);
		Query.setBlogPostingImageResourceComponentServiceObjects(
			_blogPostingImageResourceComponentServiceObjects);
		Query.setCommentResourceComponentServiceObjects(
			_commentResourceComponentServiceObjects);
		Query.setContentElementResourceComponentServiceObjects(
			_contentElementResourceComponentServiceObjects);
		Query.setContentSetElementResourceComponentServiceObjects(
			_contentSetElementResourceComponentServiceObjects);
		Query.setContentStructureResourceComponentServiceObjects(
			_contentStructureResourceComponentServiceObjects);
		Query.setContentTemplateResourceComponentServiceObjects(
			_contentTemplateResourceComponentServiceObjects);
		Query.setDocumentResourceComponentServiceObjects(
			_documentResourceComponentServiceObjects);
		Query.setDocumentFolderResourceComponentServiceObjects(
			_documentFolderResourceComponentServiceObjects);
		Query.setKnowledgeBaseArticleResourceComponentServiceObjects(
			_knowledgeBaseArticleResourceComponentServiceObjects);
		Query.setKnowledgeBaseAttachmentResourceComponentServiceObjects(
			_knowledgeBaseAttachmentResourceComponentServiceObjects);
		Query.setKnowledgeBaseFolderResourceComponentServiceObjects(
			_knowledgeBaseFolderResourceComponentServiceObjects);
		Query.setLanguageResourceComponentServiceObjects(
			_languageResourceComponentServiceObjects);
		Query.setMessageBoardAttachmentResourceComponentServiceObjects(
			_messageBoardAttachmentResourceComponentServiceObjects);
		Query.setMessageBoardMessageResourceComponentServiceObjects(
			_messageBoardMessageResourceComponentServiceObjects);
		Query.setMessageBoardSectionResourceComponentServiceObjects(
			_messageBoardSectionResourceComponentServiceObjects);
		Query.setMessageBoardThreadResourceComponentServiceObjects(
			_messageBoardThreadResourceComponentServiceObjects);
		Query.setNavigationMenuResourceComponentServiceObjects(
			_navigationMenuResourceComponentServiceObjects);
		Query.setSitePageResourceComponentServiceObjects(
			_sitePageResourceComponentServiceObjects);
		Query.setStructuredContentResourceComponentServiceObjects(
			_structuredContentResourceComponentServiceObjects);
		Query.setStructuredContentFolderResourceComponentServiceObjects(
			_structuredContentFolderResourceComponentServiceObjects);
		Query.setWikiNodeResourceComponentServiceObjects(
			_wikiNodeResourceComponentServiceObjects);
		Query.setWikiPageResourceComponentServiceObjects(
			_wikiPageResourceComponentServiceObjects);
		Query.setWikiPageAttachmentResourceComponentServiceObjects(
			_wikiPageAttachmentResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Delivery";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-delivery-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#deleteBlogPosting",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "deleteBlogPosting"));
		_resourceMethodPairs.put(
			"mutation#deleteBlogPostingBatch",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "deleteBlogPostingBatch"));
		_resourceMethodPairs.put(
			"mutation#patchBlogPosting",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "patchBlogPosting"));
		_resourceMethodPairs.put(
			"mutation#updateBlogPosting",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "putBlogPosting"));
		_resourceMethodPairs.put(
			"mutation#updateBlogPostingBatch",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "putBlogPostingBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteBlogPostingMyRating",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "deleteBlogPostingMyRating"));
		_resourceMethodPairs.put(
			"mutation#createBlogPostingMyRating",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "postBlogPostingMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateBlogPostingMyRating",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "putBlogPostingMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateBlogPostingPermissionsPage",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class,
				"putBlogPostingPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createSiteBlogPosting",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "postSiteBlogPosting"));
		_resourceMethodPairs.put(
			"mutation#createSiteBlogPostingBatch",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "postSiteBlogPostingBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteBlogPostingByExternalReferenceCode",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class,
				"deleteSiteBlogPostingByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteBlogPostingByExternalReferenceCode",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class,
				"putSiteBlogPostingByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteBlogPostingPermissionsPage",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class,
				"putSiteBlogPostingPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateSiteBlogPostingSubscribe",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "putSiteBlogPostingSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateSiteBlogPostingUnsubscribe",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class,
				"putSiteBlogPostingUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#deleteBlogPostingImage",
			new ObjectValuePair<>(
				BlogPostingImageResourceImpl.class, "deleteBlogPostingImage"));
		_resourceMethodPairs.put(
			"mutation#deleteBlogPostingImageBatch",
			new ObjectValuePair<>(
				BlogPostingImageResourceImpl.class,
				"deleteBlogPostingImageBatch"));
		_resourceMethodPairs.put(
			"mutation#createSiteBlogPostingImage",
			new ObjectValuePair<>(
				BlogPostingImageResourceImpl.class,
				"postSiteBlogPostingImage"));
		_resourceMethodPairs.put(
			"mutation#createSiteBlogPostingImageBatch",
			new ObjectValuePair<>(
				BlogPostingImageResourceImpl.class,
				"postSiteBlogPostingImageBatch"));
		_resourceMethodPairs.put(
			"mutation#createBlogPostingComment",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "postBlogPostingComment"));
		_resourceMethodPairs.put(
			"mutation#createBlogPostingCommentBatch",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "postBlogPostingCommentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteComment",
			new ObjectValuePair<>(CommentResourceImpl.class, "deleteComment"));
		_resourceMethodPairs.put(
			"mutation#deleteCommentBatch",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "deleteCommentBatch"));
		_resourceMethodPairs.put(
			"mutation#updateComment",
			new ObjectValuePair<>(CommentResourceImpl.class, "putComment"));
		_resourceMethodPairs.put(
			"mutation#updateCommentBatch",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "putCommentBatch"));
		_resourceMethodPairs.put(
			"mutation#createCommentComment",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "postCommentComment"));
		_resourceMethodPairs.put(
			"mutation#createDocumentComment",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "postDocumentComment"));
		_resourceMethodPairs.put(
			"mutation#createDocumentCommentBatch",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "postDocumentCommentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"deleteSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"putSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"deleteSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"putSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"putSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"deleteSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"putSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createStructuredContentComment",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "postStructuredContentComment"));
		_resourceMethodPairs.put(
			"mutation#createStructuredContentCommentBatch",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"postStructuredContentCommentBatch"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryContentStructurePermissionsPage",
			new ObjectValuePair<>(
				ContentStructureResourceImpl.class,
				"putAssetLibraryContentStructurePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateContentStructurePermissionsPage",
			new ObjectValuePair<>(
				ContentStructureResourceImpl.class,
				"putContentStructurePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateSiteContentStructurePermissionsPage",
			new ObjectValuePair<>(
				ContentStructureResourceImpl.class,
				"putSiteContentStructurePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryDocument",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "postAssetLibraryDocument"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryDocumentBatch",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "postAssetLibraryDocumentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAssetLibraryDocumentByExternalReferenceCode",
			new ObjectValuePair<>(
				DocumentResourceImpl.class,
				"deleteAssetLibraryDocumentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryDocumentByExternalReferenceCode",
			new ObjectValuePair<>(
				DocumentResourceImpl.class,
				"putAssetLibraryDocumentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryDocumentPermissionsPage",
			new ObjectValuePair<>(
				DocumentResourceImpl.class,
				"putAssetLibraryDocumentPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createDocumentFolderDocument",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "postDocumentFolderDocument"));
		_resourceMethodPairs.put(
			"mutation#createDocumentFolderDocumentBatch",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "postDocumentFolderDocumentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDocument",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "deleteDocument"));
		_resourceMethodPairs.put(
			"mutation#deleteDocumentBatch",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "deleteDocumentBatch"));
		_resourceMethodPairs.put(
			"mutation#patchDocument",
			new ObjectValuePair<>(DocumentResourceImpl.class, "patchDocument"));
		_resourceMethodPairs.put(
			"mutation#updateDocument",
			new ObjectValuePair<>(DocumentResourceImpl.class, "putDocument"));
		_resourceMethodPairs.put(
			"mutation#updateDocumentBatch",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "putDocumentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDocumentMyRating",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "deleteDocumentMyRating"));
		_resourceMethodPairs.put(
			"mutation#createDocumentMyRating",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "postDocumentMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateDocumentMyRating",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "putDocumentMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateDocumentPermissionsPage",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "putDocumentPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createSiteDocument",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "postSiteDocument"));
		_resourceMethodPairs.put(
			"mutation#createSiteDocumentBatch",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "postSiteDocumentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteDocumentByExternalReferenceCode",
			new ObjectValuePair<>(
				DocumentResourceImpl.class,
				"deleteSiteDocumentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteDocumentByExternalReferenceCode",
			new ObjectValuePair<>(
				DocumentResourceImpl.class,
				"putSiteDocumentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteDocumentPermissionsPage",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "putSiteDocumentPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryDocumentFolder",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"postAssetLibraryDocumentFolder"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryDocumentFolderBatch",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"postAssetLibraryDocumentFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryDocumentFolderPermissionsPage",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"putAssetLibraryDocumentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#deleteDocumentFolder",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class, "deleteDocumentFolder"));
		_resourceMethodPairs.put(
			"mutation#deleteDocumentFolderBatch",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class, "deleteDocumentFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#patchDocumentFolder",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class, "patchDocumentFolder"));
		_resourceMethodPairs.put(
			"mutation#updateDocumentFolder",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class, "putDocumentFolder"));
		_resourceMethodPairs.put(
			"mutation#updateDocumentFolderBatch",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class, "putDocumentFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#updateDocumentFolderPermissionsPage",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"putDocumentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateDocumentFolderSubscribe",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"putDocumentFolderSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateDocumentFolderUnsubscribe",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"putDocumentFolderUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#createDocumentFolderDocumentFolder",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"postDocumentFolderDocumentFolder"));
		_resourceMethodPairs.put(
			"mutation#createSiteDocumentFolder",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class, "postSiteDocumentFolder"));
		_resourceMethodPairs.put(
			"mutation#createSiteDocumentFolderBatch",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"postSiteDocumentFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#updateSiteDocumentFolderPermissionsPage",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"putSiteDocumentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#deleteKnowledgeBaseArticle",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"deleteKnowledgeBaseArticle"));
		_resourceMethodPairs.put(
			"mutation#deleteKnowledgeBaseArticleBatch",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"deleteKnowledgeBaseArticleBatch"));
		_resourceMethodPairs.put(
			"mutation#patchKnowledgeBaseArticle",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"patchKnowledgeBaseArticle"));
		_resourceMethodPairs.put(
			"mutation#updateKnowledgeBaseArticle",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putKnowledgeBaseArticle"));
		_resourceMethodPairs.put(
			"mutation#updateKnowledgeBaseArticleBatch",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putKnowledgeBaseArticleBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteKnowledgeBaseArticleMyRating",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"deleteKnowledgeBaseArticleMyRating"));
		_resourceMethodPairs.put(
			"mutation#createKnowledgeBaseArticleMyRating",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"postKnowledgeBaseArticleMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateKnowledgeBaseArticleMyRating",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putKnowledgeBaseArticleMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateKnowledgeBaseArticlePermissionsPage",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putKnowledgeBaseArticlePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateKnowledgeBaseArticleSubscribe",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putKnowledgeBaseArticleSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateKnowledgeBaseArticleUnsubscribe",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putKnowledgeBaseArticleUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#createKnowledgeBaseArticleKnowledgeBaseArticle",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"postKnowledgeBaseArticleKnowledgeBaseArticle"));
		_resourceMethodPairs.put(
			"mutation#createKnowledgeBaseFolderKnowledgeBaseArticle",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"postKnowledgeBaseFolderKnowledgeBaseArticle"));
		_resourceMethodPairs.put(
			"mutation#createKnowledgeBaseFolderKnowledgeBaseArticleBatch",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"postKnowledgeBaseFolderKnowledgeBaseArticleBatch"));
		_resourceMethodPairs.put(
			"mutation#createSiteKnowledgeBaseArticle",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"postSiteKnowledgeBaseArticle"));
		_resourceMethodPairs.put(
			"mutation#createSiteKnowledgeBaseArticleBatch",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"postSiteKnowledgeBaseArticleBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteKnowledgeBaseArticleByExternalReferenceCode",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"deleteSiteKnowledgeBaseArticleByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteKnowledgeBaseArticleByExternalReferenceCode",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putSiteKnowledgeBaseArticleByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteKnowledgeBaseArticlePermissionsPage",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putSiteKnowledgeBaseArticlePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateSiteKnowledgeBaseArticleSubscribe",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putSiteKnowledgeBaseArticleSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateSiteKnowledgeBaseArticleUnsubscribe",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"putSiteKnowledgeBaseArticleUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#createKnowledgeBaseArticleKnowledgeBaseAttachment",
			new ObjectValuePair<>(
				KnowledgeBaseAttachmentResourceImpl.class,
				"postKnowledgeBaseArticleKnowledgeBaseAttachment"));
		_resourceMethodPairs.put(
			"mutation#createKnowledgeBaseArticleKnowledgeBaseAttachmentBatch",
			new ObjectValuePair<>(
				KnowledgeBaseAttachmentResourceImpl.class,
				"postKnowledgeBaseArticleKnowledgeBaseAttachmentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteKnowledgeBaseAttachment",
			new ObjectValuePair<>(
				KnowledgeBaseAttachmentResourceImpl.class,
				"deleteKnowledgeBaseAttachment"));
		_resourceMethodPairs.put(
			"mutation#deleteKnowledgeBaseAttachmentBatch",
			new ObjectValuePair<>(
				KnowledgeBaseAttachmentResourceImpl.class,
				"deleteKnowledgeBaseAttachmentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteKnowledgeBaseFolder",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"deleteKnowledgeBaseFolder"));
		_resourceMethodPairs.put(
			"mutation#deleteKnowledgeBaseFolderBatch",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"deleteKnowledgeBaseFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#patchKnowledgeBaseFolder",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"patchKnowledgeBaseFolder"));
		_resourceMethodPairs.put(
			"mutation#updateKnowledgeBaseFolder",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"putKnowledgeBaseFolder"));
		_resourceMethodPairs.put(
			"mutation#updateKnowledgeBaseFolderBatch",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"putKnowledgeBaseFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#updateKnowledgeBaseFolderPermissionsPage",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"putKnowledgeBaseFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createKnowledgeBaseFolderKnowledgeBaseFolder",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"postKnowledgeBaseFolderKnowledgeBaseFolder"));
		_resourceMethodPairs.put(
			"mutation#createSiteKnowledgeBaseFolder",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"postSiteKnowledgeBaseFolder"));
		_resourceMethodPairs.put(
			"mutation#createSiteKnowledgeBaseFolderBatch",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"postSiteKnowledgeBaseFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteKnowledgeBaseFolderByExternalReferenceCode",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"deleteSiteKnowledgeBaseFolderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteKnowledgeBaseFolderByExternalReferenceCode",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"putSiteKnowledgeBaseFolderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteKnowledgeBaseFolderPermissionsPage",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"putSiteKnowledgeBaseFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardAttachment",
			new ObjectValuePair<>(
				MessageBoardAttachmentResourceImpl.class,
				"deleteMessageBoardAttachment"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardAttachmentBatch",
			new ObjectValuePair<>(
				MessageBoardAttachmentResourceImpl.class,
				"deleteMessageBoardAttachmentBatch"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardMessageMessageBoardAttachment",
			new ObjectValuePair<>(
				MessageBoardAttachmentResourceImpl.class,
				"postMessageBoardMessageMessageBoardAttachment"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardMessageMessageBoardAttachmentBatch",
			new ObjectValuePair<>(
				MessageBoardAttachmentResourceImpl.class,
				"postMessageBoardMessageMessageBoardAttachmentBatch"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardThreadMessageBoardAttachment",
			new ObjectValuePair<>(
				MessageBoardAttachmentResourceImpl.class,
				"postMessageBoardThreadMessageBoardAttachment"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardThreadMessageBoardAttachmentBatch",
			new ObjectValuePair<>(
				MessageBoardAttachmentResourceImpl.class,
				"postMessageBoardThreadMessageBoardAttachmentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardMessage",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"deleteMessageBoardMessage"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardMessageBatch",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"deleteMessageBoardMessageBatch"));
		_resourceMethodPairs.put(
			"mutation#patchMessageBoardMessage",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"patchMessageBoardMessage"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardMessage",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"putMessageBoardMessage"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardMessageBatch",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"putMessageBoardMessageBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardMessageMyRating",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"deleteMessageBoardMessageMyRating"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardMessageMyRating",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"postMessageBoardMessageMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardMessageMyRating",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"putMessageBoardMessageMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardMessagePermissionsPage",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"putMessageBoardMessagePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardMessageSubscribe",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"putMessageBoardMessageSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardMessageUnsubscribe",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"putMessageBoardMessageUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardMessageMessageBoardMessage",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"postMessageBoardMessageMessageBoardMessage"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardThreadMessageBoardMessage",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"postMessageBoardThreadMessageBoardMessage"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardThreadMessageBoardMessageBatch",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"postMessageBoardThreadMessageBoardMessageBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteMessageBoardMessageByExternalReferenceCode",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"deleteSiteMessageBoardMessageByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteMessageBoardMessageByExternalReferenceCode",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"putSiteMessageBoardMessageByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteMessageBoardMessagePermissionsPage",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"putSiteMessageBoardMessagePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardSection",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"deleteMessageBoardSection"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardSectionBatch",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"deleteMessageBoardSectionBatch"));
		_resourceMethodPairs.put(
			"mutation#patchMessageBoardSection",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"patchMessageBoardSection"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardSection",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"putMessageBoardSection"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardSectionBatch",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"putMessageBoardSectionBatch"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardSectionPermissionsPage",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"putMessageBoardSectionPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardSectionSubscribe",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"putMessageBoardSectionSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardSectionUnsubscribe",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"putMessageBoardSectionUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardSectionMessageBoardSection",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"postMessageBoardSectionMessageBoardSection"));
		_resourceMethodPairs.put(
			"mutation#createSiteMessageBoardSection",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"postSiteMessageBoardSection"));
		_resourceMethodPairs.put(
			"mutation#createSiteMessageBoardSectionBatch",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"postSiteMessageBoardSectionBatch"));
		_resourceMethodPairs.put(
			"mutation#updateSiteMessageBoardSectionPermissionsPage",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"putSiteMessageBoardSectionPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardSectionMessageBoardThread",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"postMessageBoardSectionMessageBoardThread"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardSectionMessageBoardThreadBatch",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"postMessageBoardSectionMessageBoardThreadBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardThread",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"deleteMessageBoardThread"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardThreadBatch",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"deleteMessageBoardThreadBatch"));
		_resourceMethodPairs.put(
			"mutation#patchMessageBoardThread",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"patchMessageBoardThread"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardThread",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class, "putMessageBoardThread"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardThreadBatch",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"putMessageBoardThreadBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteMessageBoardThreadMyRating",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"deleteMessageBoardThreadMyRating"));
		_resourceMethodPairs.put(
			"mutation#createMessageBoardThreadMyRating",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"postMessageBoardThreadMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardThreadMyRating",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"putMessageBoardThreadMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardThreadPermissionsPage",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"putMessageBoardThreadPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardThreadSubscribe",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"putMessageBoardThreadSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateMessageBoardThreadUnsubscribe",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"putMessageBoardThreadUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#createSiteMessageBoardThread",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"postSiteMessageBoardThread"));
		_resourceMethodPairs.put(
			"mutation#createSiteMessageBoardThreadBatch",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"postSiteMessageBoardThreadBatch"));
		_resourceMethodPairs.put(
			"mutation#updateSiteMessageBoardThreadPermissionsPage",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"putSiteMessageBoardThreadPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#deleteNavigationMenu",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class, "deleteNavigationMenu"));
		_resourceMethodPairs.put(
			"mutation#deleteNavigationMenuBatch",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class, "deleteNavigationMenuBatch"));
		_resourceMethodPairs.put(
			"mutation#updateNavigationMenu",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class, "putNavigationMenu"));
		_resourceMethodPairs.put(
			"mutation#updateNavigationMenuBatch",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class, "putNavigationMenuBatch"));
		_resourceMethodPairs.put(
			"mutation#updateNavigationMenuPermissionsPage",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class,
				"putNavigationMenuPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createSiteNavigationMenu",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class, "postSiteNavigationMenu"));
		_resourceMethodPairs.put(
			"mutation#createSiteNavigationMenuBatch",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class,
				"postSiteNavigationMenuBatch"));
		_resourceMethodPairs.put(
			"mutation#updateSiteNavigationMenuPermissionsPage",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class,
				"putSiteNavigationMenuPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryStructuredContent",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"postAssetLibraryStructuredContent"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryStructuredContentBatch",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"postAssetLibraryStructuredContentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAssetLibraryStructuredContentByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"deleteAssetLibraryStructuredContentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryStructuredContentByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"putAssetLibraryStructuredContentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryStructuredContentPermissionsPage",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"putAssetLibraryStructuredContentPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createSiteStructuredContent",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"postSiteStructuredContent"));
		_resourceMethodPairs.put(
			"mutation#createSiteStructuredContentBatch",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"postSiteStructuredContentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteStructuredContentByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"deleteSiteStructuredContentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteStructuredContentByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"putSiteStructuredContentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteStructuredContentPermissionsPage",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"putSiteStructuredContentPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createStructuredContentFolderStructuredContent",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"postStructuredContentFolderStructuredContent"));
		_resourceMethodPairs.put(
			"mutation#createStructuredContentFolderStructuredContentBatch",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"postStructuredContentFolderStructuredContentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteStructuredContent",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"deleteStructuredContent"));
		_resourceMethodPairs.put(
			"mutation#deleteStructuredContentBatch",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"deleteStructuredContentBatch"));
		_resourceMethodPairs.put(
			"mutation#patchStructuredContent",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class, "patchStructuredContent"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContent",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class, "putStructuredContent"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentBatch",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"putStructuredContentBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteStructuredContentMyRating",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"deleteStructuredContentMyRating"));
		_resourceMethodPairs.put(
			"mutation#createStructuredContentMyRating",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"postStructuredContentMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentMyRating",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"putStructuredContentMyRating"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentPermissionsPage",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"putStructuredContentPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentSubscribe",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"putStructuredContentSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentUnsubscribe",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"putStructuredContentUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryStructuredContentFolder",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"postAssetLibraryStructuredContentFolder"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryStructuredContentFolderBatch",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"postAssetLibraryStructuredContentFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAssetLibraryStructuredContentFolderByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"deleteAssetLibraryStructuredContentFolderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryStructuredContentFolderByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"putAssetLibraryStructuredContentFolderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryStructuredContentFolderPermissionsPage",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"putAssetLibraryStructuredContentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createSiteStructuredContentFolder",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"postSiteStructuredContentFolder"));
		_resourceMethodPairs.put(
			"mutation#createSiteStructuredContentFolderBatch",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"postSiteStructuredContentFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteStructuredContentFolderByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"deleteSiteStructuredContentFolderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteStructuredContentFolderByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"putSiteStructuredContentFolderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteStructuredContentFolderPermissionsPage",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"putSiteStructuredContentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentFolderPermissionsPage",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"putStructuredContentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createStructuredContentFolderStructuredContentFolder",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"postStructuredContentFolderStructuredContentFolder"));
		_resourceMethodPairs.put(
			"mutation#deleteStructuredContentFolder",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"deleteStructuredContentFolder"));
		_resourceMethodPairs.put(
			"mutation#deleteStructuredContentFolderBatch",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"deleteStructuredContentFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#patchStructuredContentFolder",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"patchStructuredContentFolder"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentFolder",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"putStructuredContentFolder"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentFolderBatch",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"putStructuredContentFolderBatch"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentFolderSubscribe",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"putStructuredContentFolderSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateStructuredContentFolderUnsubscribe",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"putStructuredContentFolderUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#createSiteWikiNode",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "postSiteWikiNode"));
		_resourceMethodPairs.put(
			"mutation#createSiteWikiNodeBatch",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "postSiteWikiNodeBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteWikiNodeByExternalReferenceCode",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class,
				"deleteSiteWikiNodeByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteWikiNodeByExternalReferenceCode",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class,
				"putSiteWikiNodeByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteWikiNodePermissionsPage",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "putSiteWikiNodePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#deleteWikiNode",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "deleteWikiNode"));
		_resourceMethodPairs.put(
			"mutation#deleteWikiNodeBatch",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "deleteWikiNodeBatch"));
		_resourceMethodPairs.put(
			"mutation#updateWikiNode",
			new ObjectValuePair<>(WikiNodeResourceImpl.class, "putWikiNode"));
		_resourceMethodPairs.put(
			"mutation#updateWikiNodeBatch",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "putWikiNodeBatch"));
		_resourceMethodPairs.put(
			"mutation#updateWikiNodePermissionsPage",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "putWikiNodePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateWikiNodeSubscribe",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "putWikiNodeSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateWikiNodeUnsubscribe",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "putWikiNodeUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteWikiPageByExternalReferenceCode",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class,
				"deleteSiteWikiPageByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteWikiPageByExternalReferenceCode",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class,
				"putSiteWikiPageByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createWikiNodeWikiPage",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "postWikiNodeWikiPage"));
		_resourceMethodPairs.put(
			"mutation#createWikiNodeWikiPageBatch",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "postWikiNodeWikiPageBatch"));
		_resourceMethodPairs.put(
			"mutation#createWikiPageWikiPage",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "postWikiPageWikiPage"));
		_resourceMethodPairs.put(
			"mutation#deleteWikiPage",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "deleteWikiPage"));
		_resourceMethodPairs.put(
			"mutation#deleteWikiPageBatch",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "deleteWikiPageBatch"));
		_resourceMethodPairs.put(
			"mutation#updateWikiPage",
			new ObjectValuePair<>(WikiPageResourceImpl.class, "putWikiPage"));
		_resourceMethodPairs.put(
			"mutation#updateWikiPageBatch",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "putWikiPageBatch"));
		_resourceMethodPairs.put(
			"mutation#updateWikiPagePermissionsPage",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "putWikiPagePermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#updateWikiPageSubscribe",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "putWikiPageSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateWikiPageUnsubscribe",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "putWikiPageUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode",
			new ObjectValuePair<>(
				WikiPageAttachmentResourceImpl.class,
				"deleteSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteWikiPageAttachment",
			new ObjectValuePair<>(
				WikiPageAttachmentResourceImpl.class,
				"deleteWikiPageAttachment"));
		_resourceMethodPairs.put(
			"mutation#deleteWikiPageAttachmentBatch",
			new ObjectValuePair<>(
				WikiPageAttachmentResourceImpl.class,
				"deleteWikiPageAttachmentBatch"));
		_resourceMethodPairs.put(
			"mutation#createWikiPageWikiPageAttachment",
			new ObjectValuePair<>(
				WikiPageAttachmentResourceImpl.class,
				"postWikiPageWikiPageAttachment"));
		_resourceMethodPairs.put(
			"mutation#createWikiPageWikiPageAttachmentBatch",
			new ObjectValuePair<>(
				WikiPageAttachmentResourceImpl.class,
				"postWikiPageWikiPageAttachmentBatch"));
		_resourceMethodPairs.put(
			"query#blogPosting",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "getBlogPosting"));
		_resourceMethodPairs.put(
			"query#blogPostingMyRating",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "getBlogPostingMyRating"));
		_resourceMethodPairs.put(
			"query#blogPostingPermissions",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class,
				"getBlogPostingPermissionsPage"));
		_resourceMethodPairs.put(
			"query#blogPostingRenderedContentByDisplayPageDisplayPageKey",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class,
				"getBlogPostingRenderedContentByDisplayPageDisplayPageKey"));
		_resourceMethodPairs.put(
			"query#blogPostings",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class, "getSiteBlogPostingsPage"));
		_resourceMethodPairs.put(
			"query#blogPostingByExternalReferenceCode",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class,
				"getSiteBlogPostingByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#siteBlogPostingPermissions",
			new ObjectValuePair<>(
				BlogPostingResourceImpl.class,
				"getSiteBlogPostingPermissionsPage"));
		_resourceMethodPairs.put(
			"query#blogPostingImage",
			new ObjectValuePair<>(
				BlogPostingImageResourceImpl.class, "getBlogPostingImage"));
		_resourceMethodPairs.put(
			"query#blogPostingImages",
			new ObjectValuePair<>(
				BlogPostingImageResourceImpl.class,
				"getSiteBlogPostingImagesPage"));
		_resourceMethodPairs.put(
			"query#blogPostingComments",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "getBlogPostingCommentsPage"));
		_resourceMethodPairs.put(
			"query#comment",
			new ObjectValuePair<>(CommentResourceImpl.class, "getComment"));
		_resourceMethodPairs.put(
			"query#commentComments",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "getCommentCommentsPage"));
		_resourceMethodPairs.put(
			"query#documentComments",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "getDocumentCommentsPage"));
		_resourceMethodPairs.put(
			"query#blogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"getSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#commentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"getSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#documentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"getSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#structuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode",
			new ObjectValuePair<>(
				CommentResourceImpl.class,
				"getSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#structuredContentComments",
			new ObjectValuePair<>(
				CommentResourceImpl.class, "getStructuredContentCommentsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryContentElements",
			new ObjectValuePair<>(
				ContentElementResourceImpl.class,
				"getAssetLibraryContentElementsPage"));
		_resourceMethodPairs.put(
			"query#contentElements",
			new ObjectValuePair<>(
				ContentElementResourceImpl.class,
				"getSiteContentElementsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryContentSetByKeyContentSetElements",
			new ObjectValuePair<>(
				ContentSetElementResourceImpl.class,
				"getAssetLibraryContentSetByKeyContentSetElementsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryContentSetByUuidContentSetElements",
			new ObjectValuePair<>(
				ContentSetElementResourceImpl.class,
				"getAssetLibraryContentSetByUuidContentSetElementsPage"));
		_resourceMethodPairs.put(
			"query#contentSetContentSetElements",
			new ObjectValuePair<>(
				ContentSetElementResourceImpl.class,
				"getContentSetContentSetElementsPage"));
		_resourceMethodPairs.put(
			"query#contentSetByKeyContentSetElements",
			new ObjectValuePair<>(
				ContentSetElementResourceImpl.class,
				"getSiteContentSetByKeyContentSetElementsPage"));
		_resourceMethodPairs.put(
			"query#contentSetByUuidContentSetElements",
			new ObjectValuePair<>(
				ContentSetElementResourceImpl.class,
				"getSiteContentSetByUuidContentSetElementsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryContentStructures",
			new ObjectValuePair<>(
				ContentStructureResourceImpl.class,
				"getAssetLibraryContentStructuresPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryContentStructurePermissions",
			new ObjectValuePair<>(
				ContentStructureResourceImpl.class,
				"getAssetLibraryContentStructurePermissionsPage"));
		_resourceMethodPairs.put(
			"query#contentStructure",
			new ObjectValuePair<>(
				ContentStructureResourceImpl.class, "getContentStructure"));
		_resourceMethodPairs.put(
			"query#contentStructurePermissions",
			new ObjectValuePair<>(
				ContentStructureResourceImpl.class,
				"getContentStructurePermissionsPage"));
		_resourceMethodPairs.put(
			"query#contentStructures",
			new ObjectValuePair<>(
				ContentStructureResourceImpl.class,
				"getSiteContentStructuresPage"));
		_resourceMethodPairs.put(
			"query#siteContentStructurePermissions",
			new ObjectValuePair<>(
				ContentStructureResourceImpl.class,
				"getSiteContentStructurePermissionsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryContentTemplates",
			new ObjectValuePair<>(
				ContentTemplateResourceImpl.class,
				"getAssetLibraryContentTemplatesPage"));
		_resourceMethodPairs.put(
			"query#contentTemplates",
			new ObjectValuePair<>(
				ContentTemplateResourceImpl.class,
				"getSiteContentTemplatesPage"));
		_resourceMethodPairs.put(
			"query#contentTemplate",
			new ObjectValuePair<>(
				ContentTemplateResourceImpl.class, "getSiteContentTemplate"));
		_resourceMethodPairs.put(
			"query#assetLibraryDocuments",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "getAssetLibraryDocumentsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryDocumentByExternalReferenceCode",
			new ObjectValuePair<>(
				DocumentResourceImpl.class,
				"getAssetLibraryDocumentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#assetLibraryDocumentPermissions",
			new ObjectValuePair<>(
				DocumentResourceImpl.class,
				"getAssetLibraryDocumentPermissionsPage"));
		_resourceMethodPairs.put(
			"query#documentFolderDocuments",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "getDocumentFolderDocumentsPage"));
		_resourceMethodPairs.put(
			"query#document",
			new ObjectValuePair<>(DocumentResourceImpl.class, "getDocument"));
		_resourceMethodPairs.put(
			"query#documentMyRating",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "getDocumentMyRating"));
		_resourceMethodPairs.put(
			"query#documentPermissions",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "getDocumentPermissionsPage"));
		_resourceMethodPairs.put(
			"query#documentRenderedContentByDisplayPageDisplayPageKey",
			new ObjectValuePair<>(
				DocumentResourceImpl.class,
				"getDocumentRenderedContentByDisplayPageDisplayPageKey"));
		_resourceMethodPairs.put(
			"query#documents",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "getSiteDocumentsPage"));
		_resourceMethodPairs.put(
			"query#documentByExternalReferenceCode",
			new ObjectValuePair<>(
				DocumentResourceImpl.class,
				"getSiteDocumentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#siteDocumentPermissions",
			new ObjectValuePair<>(
				DocumentResourceImpl.class, "getSiteDocumentPermissionsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryDocumentFolders",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"getAssetLibraryDocumentFoldersPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryDocumentFolderPermissions",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"getAssetLibraryDocumentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"query#documentFolder",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class, "getDocumentFolder"));
		_resourceMethodPairs.put(
			"query#documentFolderPermissions",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"getDocumentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"query#documentFolderDocumentFolders",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"getDocumentFolderDocumentFoldersPage"));
		_resourceMethodPairs.put(
			"query#documentFolders",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"getSiteDocumentFoldersPage"));
		_resourceMethodPairs.put(
			"query#siteDocumentFolderPermissions",
			new ObjectValuePair<>(
				DocumentFolderResourceImpl.class,
				"getSiteDocumentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseArticle",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"getKnowledgeBaseArticle"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseArticleMyRating",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"getKnowledgeBaseArticleMyRating"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseArticlePermissions",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"getKnowledgeBaseArticlePermissionsPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseArticleKnowledgeBaseArticles",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"getKnowledgeBaseArticleKnowledgeBaseArticlesPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseFolderKnowledgeBaseArticles",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"getKnowledgeBaseFolderKnowledgeBaseArticlesPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseArticles",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"getSiteKnowledgeBaseArticlesPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseArticleByExternalReferenceCode",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"getSiteKnowledgeBaseArticleByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#siteKnowledgeBaseArticlePermissions",
			new ObjectValuePair<>(
				KnowledgeBaseArticleResourceImpl.class,
				"getSiteKnowledgeBaseArticlePermissionsPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseArticleKnowledgeBaseAttachments",
			new ObjectValuePair<>(
				KnowledgeBaseAttachmentResourceImpl.class,
				"getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseAttachment",
			new ObjectValuePair<>(
				KnowledgeBaseAttachmentResourceImpl.class,
				"getKnowledgeBaseAttachment"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseFolder",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"getKnowledgeBaseFolder"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseFolderPermissions",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"getKnowledgeBaseFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseFolderKnowledgeBaseFolders",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"getKnowledgeBaseFolderKnowledgeBaseFoldersPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseFolders",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"getSiteKnowledgeBaseFoldersPage"));
		_resourceMethodPairs.put(
			"query#knowledgeBaseFolderByExternalReferenceCode",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"getSiteKnowledgeBaseFolderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#siteKnowledgeBaseFolderPermissions",
			new ObjectValuePair<>(
				KnowledgeBaseFolderResourceImpl.class,
				"getSiteKnowledgeBaseFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryLanguages",
			new ObjectValuePair<>(
				LanguageResourceImpl.class, "getAssetLibraryLanguagesPage"));
		_resourceMethodPairs.put(
			"query#languages",
			new ObjectValuePair<>(
				LanguageResourceImpl.class, "getSiteLanguagesPage"));
		_resourceMethodPairs.put(
			"query#messageBoardAttachment",
			new ObjectValuePair<>(
				MessageBoardAttachmentResourceImpl.class,
				"getMessageBoardAttachment"));
		_resourceMethodPairs.put(
			"query#messageBoardMessageMessageBoardAttachments",
			new ObjectValuePair<>(
				MessageBoardAttachmentResourceImpl.class,
				"getMessageBoardMessageMessageBoardAttachmentsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardThreadMessageBoardAttachments",
			new ObjectValuePair<>(
				MessageBoardAttachmentResourceImpl.class,
				"getMessageBoardThreadMessageBoardAttachmentsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardMessage",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"getMessageBoardMessage"));
		_resourceMethodPairs.put(
			"query#messageBoardMessageMyRating",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"getMessageBoardMessageMyRating"));
		_resourceMethodPairs.put(
			"query#messageBoardMessagePermissions",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"getMessageBoardMessagePermissionsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardMessageMessageBoardMessages",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"getMessageBoardMessageMessageBoardMessagesPage"));
		_resourceMethodPairs.put(
			"query#messageBoardThreadMessageBoardMessages",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"getMessageBoardThreadMessageBoardMessagesPage"));
		_resourceMethodPairs.put(
			"query#messageBoardMessages",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"getSiteMessageBoardMessagesPage"));
		_resourceMethodPairs.put(
			"query#messageBoardMessageByExternalReferenceCode",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"getSiteMessageBoardMessageByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#messageBoardMessageByFriendlyUrlPath",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"getSiteMessageBoardMessageByFriendlyUrlPath"));
		_resourceMethodPairs.put(
			"query#siteMessageBoardMessagePermissions",
			new ObjectValuePair<>(
				MessageBoardMessageResourceImpl.class,
				"getSiteMessageBoardMessagePermissionsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardSection",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"getMessageBoardSection"));
		_resourceMethodPairs.put(
			"query#messageBoardSectionPermissions",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"getMessageBoardSectionPermissionsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardSectionMessageBoardSections",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"getMessageBoardSectionMessageBoardSectionsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardSections",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"getSiteMessageBoardSectionsPage"));
		_resourceMethodPairs.put(
			"query#siteMessageBoardSectionPermissions",
			new ObjectValuePair<>(
				MessageBoardSectionResourceImpl.class,
				"getSiteMessageBoardSectionPermissionsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardSectionMessageBoardThreads",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"getMessageBoardSectionMessageBoardThreadsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardThreadsRanked",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"getMessageBoardThreadsRankedPage"));
		_resourceMethodPairs.put(
			"query#messageBoardThread",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class, "getMessageBoardThread"));
		_resourceMethodPairs.put(
			"query#messageBoardThreadMyRating",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"getMessageBoardThreadMyRating"));
		_resourceMethodPairs.put(
			"query#messageBoardThreadPermissions",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"getMessageBoardThreadPermissionsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardThreads",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"getSiteMessageBoardThreadsPage"));
		_resourceMethodPairs.put(
			"query#messageBoardThreadByFriendlyUrlPath",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"getSiteMessageBoardThreadByFriendlyUrlPath"));
		_resourceMethodPairs.put(
			"query#siteMessageBoardThreadPermissions",
			new ObjectValuePair<>(
				MessageBoardThreadResourceImpl.class,
				"getSiteMessageBoardThreadPermissionsPage"));
		_resourceMethodPairs.put(
			"query#navigationMenu",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class, "getNavigationMenu"));
		_resourceMethodPairs.put(
			"query#navigationMenuPermissions",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class,
				"getNavigationMenuPermissionsPage"));
		_resourceMethodPairs.put(
			"query#navigationMenus",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class,
				"getSiteNavigationMenusPage"));
		_resourceMethodPairs.put(
			"query#siteNavigationMenuPermissions",
			new ObjectValuePair<>(
				NavigationMenuResourceImpl.class,
				"getSiteNavigationMenuPermissionsPage"));
		_resourceMethodPairs.put(
			"query#sitePages",
			new ObjectValuePair<>(
				SitePageResourceImpl.class, "getSiteSitePagesPage"));
		_resourceMethodPairs.put(
			"query#sitePage",
			new ObjectValuePair<>(
				SitePageResourceImpl.class, "getSiteSitePage"));
		_resourceMethodPairs.put(
			"query#sitePagesExperiences",
			new ObjectValuePair<>(
				SitePageResourceImpl.class, "getSiteSitePagesExperiencesPage"));
		_resourceMethodPairs.put(
			"query#sitePageExperienceExperienceKey",
			new ObjectValuePair<>(
				SitePageResourceImpl.class,
				"getSiteSitePageExperienceExperienceKey"));
		_resourceMethodPairs.put(
			"query#sitePageExperienceExperienceKeyRenderedPage",
			new ObjectValuePair<>(
				SitePageResourceImpl.class,
				"getSiteSitePageExperienceExperienceKeyRenderedPage"));
		_resourceMethodPairs.put(
			"query#sitePageRenderedPage",
			new ObjectValuePair<>(
				SitePageResourceImpl.class, "getSiteSitePageRenderedPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryStructuredContents",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getAssetLibraryStructuredContentsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryStructuredContentByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getAssetLibraryStructuredContentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#assetLibraryStructuredContentPermissions",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getAssetLibraryStructuredContentPermissionsPage"));
		_resourceMethodPairs.put(
			"query#contentStructureStructuredContents",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getContentStructureStructuredContentsPage"));
		_resourceMethodPairs.put(
			"query#structuredContents",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getSiteStructuredContentsPage"));
		_resourceMethodPairs.put(
			"query#structuredContentByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getSiteStructuredContentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#structuredContentByKey",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getSiteStructuredContentByKey"));
		_resourceMethodPairs.put(
			"query#structuredContentByUuid",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getSiteStructuredContentByUuid"));
		_resourceMethodPairs.put(
			"query#siteStructuredContentPermissions",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getSiteStructuredContentPermissionsPage"));
		_resourceMethodPairs.put(
			"query#structuredContentFolderStructuredContents",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getStructuredContentFolderStructuredContentsPage"));
		_resourceMethodPairs.put(
			"query#structuredContent",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class, "getStructuredContent"));
		_resourceMethodPairs.put(
			"query#structuredContentMyRating",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getStructuredContentMyRating"));
		_resourceMethodPairs.put(
			"query#structuredContentPermissions",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getStructuredContentPermissionsPage"));
		_resourceMethodPairs.put(
			"query#structuredContentRenderedContentByDisplayPageDisplayPageKey",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getStructuredContentRenderedContentByDisplayPageDisplayPageKey"));
		_resourceMethodPairs.put(
			"query#structuredContentRenderedContentContentTemplate",
			new ObjectValuePair<>(
				StructuredContentResourceImpl.class,
				"getStructuredContentRenderedContentContentTemplate"));
		_resourceMethodPairs.put(
			"query#assetLibraryStructuredContentFolders",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"getAssetLibraryStructuredContentFoldersPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryStructuredContentFolderByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"getAssetLibraryStructuredContentFolderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#assetLibraryStructuredContentFolderPermissions",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"getAssetLibraryStructuredContentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"query#structuredContentFolders",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"getSiteStructuredContentFoldersPage"));
		_resourceMethodPairs.put(
			"query#structuredContentFolderByExternalReferenceCode",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"getSiteStructuredContentFolderByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#siteStructuredContentFolderPermissions",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"getSiteStructuredContentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"query#structuredContentFolderPermissions",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"getStructuredContentFolderPermissionsPage"));
		_resourceMethodPairs.put(
			"query#structuredContentFolderStructuredContentFolders",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"getStructuredContentFolderStructuredContentFoldersPage"));
		_resourceMethodPairs.put(
			"query#structuredContentFolder",
			new ObjectValuePair<>(
				StructuredContentFolderResourceImpl.class,
				"getStructuredContentFolder"));
		_resourceMethodPairs.put(
			"query#wikiNodes",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "getSiteWikiNodesPage"));
		_resourceMethodPairs.put(
			"query#wikiNodeByExternalReferenceCode",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class,
				"getSiteWikiNodeByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#siteWikiNodePermissions",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "getSiteWikiNodePermissionsPage"));
		_resourceMethodPairs.put(
			"query#wikiNode",
			new ObjectValuePair<>(WikiNodeResourceImpl.class, "getWikiNode"));
		_resourceMethodPairs.put(
			"query#wikiNodePermissions",
			new ObjectValuePair<>(
				WikiNodeResourceImpl.class, "getWikiNodePermissionsPage"));
		_resourceMethodPairs.put(
			"query#wikiPageByExternalReferenceCode",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class,
				"getSiteWikiPageByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#wikiNodeWikiPages",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "getWikiNodeWikiPagesPage"));
		_resourceMethodPairs.put(
			"query#wikiPageWikiPages",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "getWikiPageWikiPagesPage"));
		_resourceMethodPairs.put(
			"query#wikiPage",
			new ObjectValuePair<>(WikiPageResourceImpl.class, "getWikiPage"));
		_resourceMethodPairs.put(
			"query#wikiPagePermissions",
			new ObjectValuePair<>(
				WikiPageResourceImpl.class, "getWikiPagePermissionsPage"));
		_resourceMethodPairs.put(
			"query#wikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode",
			new ObjectValuePair<>(
				WikiPageAttachmentResourceImpl.class,
				"getSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#wikiPageAttachment",
			new ObjectValuePair<>(
				WikiPageAttachmentResourceImpl.class, "getWikiPageAttachment"));
		_resourceMethodPairs.put(
			"query#wikiPageWikiPageAttachments",
			new ObjectValuePair<>(
				WikiPageAttachmentResourceImpl.class,
				"getWikiPageWikiPageAttachmentsPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<BlogPostingResource>
		_blogPostingResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<BlogPostingImageResource>
		_blogPostingImageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CommentResource>
		_commentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContentStructureResource>
		_contentStructureResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DocumentResource>
		_documentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DocumentFolderResource>
		_documentFolderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<KnowledgeBaseArticleResource>
		_knowledgeBaseArticleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<KnowledgeBaseAttachmentResource>
		_knowledgeBaseAttachmentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<KnowledgeBaseFolderResource>
		_knowledgeBaseFolderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MessageBoardAttachmentResource>
		_messageBoardAttachmentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MessageBoardMessageResource>
		_messageBoardMessageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MessageBoardSectionResource>
		_messageBoardSectionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MessageBoardThreadResource>
		_messageBoardThreadResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<NavigationMenuResource>
		_navigationMenuResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<StructuredContentResource>
		_structuredContentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<StructuredContentFolderResource>
		_structuredContentFolderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WikiNodeResource>
		_wikiNodeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WikiPageResource>
		_wikiPageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WikiPageAttachmentResource>
		_wikiPageAttachmentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContentElementResource>
		_contentElementResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContentSetElementResource>
		_contentSetElementResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContentTemplateResource>
		_contentTemplateResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<LanguageResource>
		_languageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SitePageResource>
		_sitePageResourceComponentServiceObjects;

}