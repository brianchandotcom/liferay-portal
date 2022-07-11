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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.headless.delivery.dto.v1_0.ContentElement;
import com.liferay.headless.delivery.dto.v1_0.ContentSetElement;
import com.liferay.headless.delivery.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.dto.v1_0.ContentTemplate;
import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.dto.v1_0.Language;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardAttachment;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardSection;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.dto.v1_0.SitePage;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.dto.v1_0.WikiNode;
import com.liferay.headless.delivery.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.dto.v1_0.WikiPageAttachment;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(description = "A Java client JAR is available for use with the group ID 'com.liferay', artifact ID 'com.liferay.headless.delivery.client', and version '4.0.34'.", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "Headless Delivery", version = "v1.0")
)
@Path("/v1.0")
public class OpenAPIResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type)
		throws Exception {

		try {
			Class<? extends OpenAPIResource> clazz =
				_openAPIResource.getClass();

			clazz.getMethod(
				"getOpenAPI", long.class, Map.class, String.class,
				UriInfo.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			return _openAPIResource.getOpenAPI(_resourceClasses.keySet(), type);
		}

		return _openAPIResource.getOpenAPI(
			_company.getCompanyId(), _resourceClasses, type, _uriInfo);
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	@Context
	private UriInfo _uriInfo;

	private final Map<Class<?>, Class<?>> _resourceClasses =
		new HashMap<Class<?>, Class<?>>() {
			{
				put(BlogPostingImageResourceImpl.class, BlogPostingImage.class);
				put(BlogPostingResourceImpl.class, BlogPosting.class);
				put(CommentResourceImpl.class, Comment.class);
				put(ContentElementResourceImpl.class, ContentElement.class);
				put(
					ContentSetElementResourceImpl.class,
					ContentSetElement.class);
				put(ContentStructureResourceImpl.class, ContentStructure.class);
				put(ContentTemplateResourceImpl.class, ContentTemplate.class);
				put(DocumentFolderResourceImpl.class, DocumentFolder.class);
				put(DocumentResourceImpl.class, Document.class);
				put(
					KnowledgeBaseArticleResourceImpl.class,
					KnowledgeBaseArticle.class);
				put(
					KnowledgeBaseAttachmentResourceImpl.class,
					KnowledgeBaseAttachment.class);
				put(
					KnowledgeBaseFolderResourceImpl.class,
					KnowledgeBaseFolder.class);
				put(LanguageResourceImpl.class, Language.class);
				put(
					MessageBoardAttachmentResourceImpl.class,
					MessageBoardAttachment.class);
				put(
					MessageBoardMessageResourceImpl.class,
					MessageBoardMessage.class);
				put(
					MessageBoardSectionResourceImpl.class,
					MessageBoardSection.class);
				put(
					MessageBoardThreadResourceImpl.class,
					MessageBoardThread.class);
				put(NavigationMenuResourceImpl.class, NavigationMenu.class);
				put(SitePageResourceImpl.class, SitePage.class);
				put(
					StructuredContentFolderResourceImpl.class,
					StructuredContentFolder.class);
				put(
					StructuredContentResourceImpl.class,
					StructuredContent.class);
				put(WikiNodeResourceImpl.class, WikiNode.class);
				put(
					WikiPageAttachmentResourceImpl.class,
					WikiPageAttachment.class);
				put(WikiPageResourceImpl.class, WikiPage.class);

				put(OpenAPIResourceImpl.class, null);
			}
		};

	@Context
	private Company _company;

}