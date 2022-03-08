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
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingResource;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.PermissionUtil;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@javax.ws.rs.Path("/v1.0")
public abstract class BaseBlogPostingResourceImpl
	implements BlogPostingResource, EntityModelResource,
			   VulcanBatchEngineTaskItemDelegate<BlogPosting> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Deletes the blog post and returns a 204 if the operation succeeds."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public void deleteBlogPosting(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("blogPostingId")
			Long blogPostingId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/batch'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "callbackURL"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path("/blog-postings/batch")
	@javax.ws.rs.Produces("application/json")
	@Override
	public Response deleteBlogPostingBatch(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("callbackURL")
			String callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.deleteImportTask(
				BlogPosting.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the blog post."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public BlogPosting getBlogPosting(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("blogPostingId")
			Long blogPostingId)
		throws Exception {

		return new BlogPosting();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}' -d $'{"alternativeHeadline": ___, "articleBody": ___, "customFields": ___, "datePublished": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "headline": ___, "image": ___, "keywords": ___, "taxonomyCategoryIds": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Updates the blog post using only the fields received in the request body. Any other fields are left untouched. Returns the updated blog post."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.PATCH
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public BlogPosting patchBlogPosting(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("blogPostingId")
			Long blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		BlogPosting existingBlogPosting = getBlogPosting(blogPostingId);

		if (blogPosting.getActions() != null) {
			existingBlogPosting.setActions(blogPosting.getActions());
		}

		if (blogPosting.getAlternativeHeadline() != null) {
			existingBlogPosting.setAlternativeHeadline(
				blogPosting.getAlternativeHeadline());
		}

		if (blogPosting.getArticleBody() != null) {
			existingBlogPosting.setArticleBody(blogPosting.getArticleBody());
		}

		if (blogPosting.getDateCreated() != null) {
			existingBlogPosting.setDateCreated(blogPosting.getDateCreated());
		}

		if (blogPosting.getDateModified() != null) {
			existingBlogPosting.setDateModified(blogPosting.getDateModified());
		}

		if (blogPosting.getDatePublished() != null) {
			existingBlogPosting.setDatePublished(
				blogPosting.getDatePublished());
		}

		if (blogPosting.getDescription() != null) {
			existingBlogPosting.setDescription(blogPosting.getDescription());
		}

		if (blogPosting.getEncodingFormat() != null) {
			existingBlogPosting.setEncodingFormat(
				blogPosting.getEncodingFormat());
		}

		if (blogPosting.getExternalReferenceCode() != null) {
			existingBlogPosting.setExternalReferenceCode(
				blogPosting.getExternalReferenceCode());
		}

		if (blogPosting.getFriendlyUrlPath() != null) {
			existingBlogPosting.setFriendlyUrlPath(
				blogPosting.getFriendlyUrlPath());
		}

		if (blogPosting.getHeadline() != null) {
			existingBlogPosting.setHeadline(blogPosting.getHeadline());
		}

		if (blogPosting.getKeywords() != null) {
			existingBlogPosting.setKeywords(blogPosting.getKeywords());
		}

		if (blogPosting.getNumberOfComments() != null) {
			existingBlogPosting.setNumberOfComments(
				blogPosting.getNumberOfComments());
		}

		if (blogPosting.getSiteId() != null) {
			existingBlogPosting.setSiteId(blogPosting.getSiteId());
		}

		if (blogPosting.getTaxonomyCategoryIds() != null) {
			existingBlogPosting.setTaxonomyCategoryIds(
				blogPosting.getTaxonomyCategoryIds());
		}

		if (blogPosting.getViewableBy() != null) {
			existingBlogPosting.setViewableBy(blogPosting.getViewableBy());
		}

		preparePatch(blogPosting, existingBlogPosting);

		return putBlogPosting(blogPostingId, existingBlogPosting);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}' -d $'{"alternativeHeadline": ___, "articleBody": ___, "customFields": ___, "datePublished": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "headline": ___, "image": ___, "keywords": ___, "taxonomyCategoryIds": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Replaces the blog post with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public BlogPosting putBlogPosting(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("blogPostingId")
			Long blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/batch'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "callbackURL"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path("/blog-postings/batch")
	@javax.ws.rs.Produces("application/json")
	@javax.ws.rs.PUT
	@Override
	public Response putBlogPostingBatch(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("callbackURL")
			String callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.putImportTask(
				BlogPosting.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Deletes the blog post rating of the user who authenticated the request."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}/my-rating")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public void deleteBlogPostingMyRating(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("blogPostingId")
			Long blogPostingId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the blog post rating of the user who authenticated the request."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}/my-rating")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Rating getBlogPostingMyRating(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("blogPostingId")
			Long blogPostingId)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Creates a new blog post rating by the user who authenticated the request."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}/my-rating")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Rating postBlogPostingMyRating(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("blogPostingId")
			Long blogPostingId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Replaces an existing blog post rating by the user who authenticated the request."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}/my-rating")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public Rating putBlogPostingMyRating(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("blogPostingId")
			Long blogPostingId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "roleNames"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getBlogPostingPermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("blogPostingId")
				Long blogPostingId,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("roleNames")
				String roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(blogPostingId);
		Long resourceId = getPermissionCheckerResourceId(blogPostingId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(blogPostingId));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS, "getBlogPostingPermissionsPage",
					resourceName, resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putBlogPostingPermissionsPage",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Path("/blog-postings/{blogPostingId}/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putBlogPostingPermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("blogPostingId")
				Long blogPostingId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(blogPostingId);
		Long resourceId = getPermissionCheckerResourceId(blogPostingId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(blogPostingId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(blogPostingId), resourceName,
			String.valueOf(resourceId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, resourceId,
				resourceName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS, "getBlogPostingPermissionsPage",
					resourceName, resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putBlogPostingPermissionsPage",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/rendered-content-by-display-page/{displayPageKey}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the blog post's rendered display page"
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "blogPostingId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "displayPageKey"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/blog-postings/{blogPostingId}/rendered-content-by-display-page/{displayPageKey}"
	)
	@javax.ws.rs.Produces("text/html")
	@Override
	public String getBlogPostingRenderedContentByDisplayPageDisplayPageKey(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("blogPostingId")
			Long blogPostingId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("displayPageKey")
			String displayPageKey)
		throws Exception {

		return StringPool.BLANK;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the site's blog postings. Results can be paginated, filtered, searched, and sorted."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "search"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "filter"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "page"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "pageSize"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "sort"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/sites/{siteId}/blog-postings")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<BlogPosting> getSiteBlogPostingsPage(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("search")
			String search,
			@javax.ws.rs.core.Context
				com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			@javax.ws.rs.core.Context Filter filter,
			@javax.ws.rs.core.Context Pagination pagination,
			@javax.ws.rs.core.Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings' -d $'{"alternativeHeadline": ___, "articleBody": ___, "customFields": ___, "datePublished": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "headline": ___, "image": ___, "keywords": ___, "taxonomyCategoryIds": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Creates a new blog post."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/sites/{siteId}/blog-postings")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public BlogPosting postSiteBlogPosting(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/batch'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "callbackURL"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path("/sites/{siteId}/blog-postings/batch")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces("application/json")
	@Override
	public Response postSiteBlogPostingBatch(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("callbackURL")
			String callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.postImportTask(
				BlogPosting.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Deletes the blog post by external reference code."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "externalReferenceCode"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path(
		"/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public void deleteSiteBlogPostingByExternalReferenceCode(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the site's blog post by external reference code."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "externalReferenceCode"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public BlogPosting getSiteBlogPostingByExternalReferenceCode(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {

		return new BlogPosting();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}' -d $'{"alternativeHeadline": ___, "articleBody": ___, "customFields": ___, "datePublished": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "headline": ___, "image": ___, "keywords": ___, "taxonomyCategoryIds": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Updates the site's blog post with the given external reference code, or creates it if it not exists."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "externalReferenceCode"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path(
		"/sites/{siteId}/blog-postings/by-external-reference-code/{externalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public BlogPosting putSiteBlogPostingByExternalReferenceCode(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("externalReferenceCode")
			String externalReferenceCode,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "roleNames"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/sites/{siteId}/blog-postings/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteBlogPostingPermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("siteId")
				Long siteId,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("roleNames")
				String roleNames)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(siteId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName, siteId,
			siteId);

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS, "getSiteBlogPostingPermissionsPage",
					portletName, siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putSiteBlogPostingPermissionsPage",
					portletName, siteId)
			).build(),
			siteId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Path("/sites/{siteId}/blog-postings/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteBlogPostingPermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("siteId")
				Long siteId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(siteId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName, siteId,
			siteId);

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(), siteId, portletName,
			String.valueOf(siteId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, siteId, portletName,
				resourceActionLocalService, resourcePermissionLocalService,
				roleLocalService));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS, "getSiteBlogPostingPermissionsPage",
					portletName, siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putSiteBlogPostingPermissionsPage",
					portletName, siteId)
			).build(),
			siteId, portletName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/subscribe'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Path("/sites/{siteId}/blog-postings/subscribe")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public void putSiteBlogPostingSubscribe(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogPosting")}
	)
	@javax.ws.rs.Path("/sites/{siteId}/blog-postings/unsubscribe")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public void putSiteBlogPostingUnsubscribe(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId)
		throws Exception {
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<BlogPosting> blogPostings,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<BlogPosting, Exception> blogPostingUnsafeConsumer =
			blogPosting -> {
			};

		if (parameters.containsKey("siteId")) {
			blogPostingUnsafeConsumer = blogPosting -> postSiteBlogPosting(
				(Long)parameters.get("siteId"), blogPosting);
		}

		if (contextBatchUnsafeConsumer != null) {
			contextBatchUnsafeConsumer.accept(
				blogPostings, blogPostingUnsafeConsumer);
		}
		else {
			for (BlogPosting blogPosting : blogPostings) {
				blogPostingUnsafeConsumer.accept(blogPosting);
			}
		}
	}

	@Override
	public void delete(
			java.util.Collection<BlogPosting> blogPostings,
			Map<String, Serializable> parameters)
		throws Exception {

		for (BlogPosting blogPosting : blogPostings) {
			deleteBlogPosting(blogPosting.getId());
		}
	}

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return getEntityModel(
			new MultivaluedHashMap<String, Object>(multivaluedMap));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return null;
	}

	@Override
	public Page<BlogPosting> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (parameters.containsKey("siteId")) {
			return getSiteBlogPostingsPage(
				(Long)parameters.get("siteId"), search, null, filter,
				pagination, sorts);
		}
		else {
			return null;
		}
	}

	@Override
	public void setLanguageId(String languageId) {
		this.contextAcceptLanguage = new AcceptLanguage() {

			@Override
			public List<Locale> getLocales() {
				return null;
			}

			@Override
			public String getPreferredLanguageId() {
				return languageId;
			}

			@Override
			public Locale getPreferredLocale() {
				return LocaleUtil.fromLanguageId(languageId);
			}

		};
	}

	@Override
	public void update(
			java.util.Collection<BlogPosting> blogPostings,
			Map<String, Serializable> parameters)
		throws Exception {

		for (BlogPosting blogPosting : blogPostings) {
			putBlogPosting(
				blogPosting.getId() != null ? blogPosting.getId() :
					Long.parseLong((String)parameters.get("blogPostingId")),
				blogPosting);
		}
	}

	protected String getPermissionCheckerActionsResourceName(Object id)
		throws Exception {

		return getPermissionCheckerResourceName(id);
	}

	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String getPermissionCheckerPortletName(Object id)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long getPermissionCheckerResourceId(Object id) throws Exception {
		return GetterUtil.getLong(id);
	}

	protected String getPermissionCheckerResourceName(Object id)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<com.liferay.portal.vulcan.permission.Permission>
			toPermissionPage(
				Map<String, Map<String, String>> actions, long id,
				String resourceName, String roleNames)
		throws Exception {

		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(resourceName);

		if (Validator.isNotNull(roleNames)) {
			return Page.of(
				actions,
				transform(
					PermissionUtil.getRoles(
						contextCompany, roleLocalService,
						StringUtil.split(roleNames)),
					role -> PermissionUtil.toPermission(
						contextCompany.getCompanyId(), id, resourceActions,
						resourceName, resourcePermissionLocalService, role)));
		}

		return Page.of(
			actions,
			transform(
				PermissionUtil.getResourcePermissions(
					contextCompany.getCompanyId(), id, resourceName,
					resourcePermissionLocalService),
				resourcePermission -> PermissionUtil.toPermission(
					resourceActions, resourcePermission,
					roleLocalService.getRole(resourcePermission.getRoleId()))));
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextBatchUnsafeConsumer(
		UnsafeBiConsumer
			<java.util.Collection<BlogPosting>,
			 UnsafeConsumer<BlogPosting, Exception>, Exception>
				contextBatchUnsafeConsumer) {

		this.contextBatchUnsafeConsumer = contextBatchUnsafeConsumer;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setExpressionConvert(
		ExpressionConvert<Filter> expressionConvert) {

		this.expressionConvert = expressionConvert;
	}

	public void setFilterParserProvider(
		FilterParserProvider filterParserProvider) {

		this.filterParserProvider = filterParserProvider;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		this.resourceActionLocalService = resourceActionLocalService;
	}

	public void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	@Override
	public Filter toFilter(
		String filterString, Map<String, List<String>> multivaluedMap) {

		try {
			EntityModel entityModel = getEntityModel(multivaluedMap);

			FilterParser filterParser = filterParserProvider.provide(
				entityModel);

			com.liferay.portal.odata.filter.Filter oDataFilter =
				new com.liferay.portal.odata.filter.Filter(
					filterParser.parse(filterString));

			return expressionConvert.convert(
				oDataFilter.getExpression(),
				contextAcceptLanguage.getPreferredLocale(), entityModel);
		}
		catch (Exception exception) {
			_log.error("Invalid filter " + filterString, exception);
		}

		return null;
	}

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
	}

	protected void preparePatch(
		BlogPosting blogPosting, BlogPosting existingBlogPosting) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected UnsafeBiConsumer
		<java.util.Collection<BlogPosting>,
		 UnsafeConsumer<BlogPosting, Exception>, Exception>
			contextBatchUnsafeConsumer;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected ExpressionConvert<Filter> expressionConvert;
	protected FilterParserProvider filterParserProvider;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected VulcanBatchEngineImportTaskResource
		vulcanBatchEngineImportTaskResource;

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseBlogPostingResourceImpl.class);

}