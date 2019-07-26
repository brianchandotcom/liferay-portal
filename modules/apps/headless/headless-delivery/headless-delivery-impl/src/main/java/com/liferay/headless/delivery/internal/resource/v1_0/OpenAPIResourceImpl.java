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

import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	info = @Info(description = "", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "Headless Delivery", version = "v1.0")
)
@Path("/v1.0")
public class OpenAPIResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type)
		throws Exception {

		return _openAPIResource.getOpenAPI(_resourceClasses, type);
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	private final Set<Class<?>> _resourceClasses = new HashSet<Class<?>>() {
		{
			add(BlogPostingResourceImpl.class);

			add(BlogPostingImageResourceImpl.class);

			add(CommentResourceImpl.class);

			add(ContentSetElementResourceImpl.class);

			add(ContentStructureResourceImpl.class);

			add(DocumentResourceImpl.class);

			add(DocumentFolderResourceImpl.class);

			add(KnowledgeBaseArticleResourceImpl.class);

			add(KnowledgeBaseAttachmentResourceImpl.class);

			add(KnowledgeBaseFolderResourceImpl.class);

			add(MessageBoardAttachmentResourceImpl.class);

			add(MessageBoardMessageResourceImpl.class);

			add(MessageBoardSectionResourceImpl.class);

			add(MessageBoardThreadResourceImpl.class);

			add(StructuredContentResourceImpl.class);

			add(StructuredContentFolderResourceImpl.class);

			add(WikiNodeResourceImpl.class);

			add(WikiPageResourceImpl.class);

			add(WikiPageAttachmentResourceImpl.class);

			add(OpenAPIResourceImpl.class);
		}
	};

}