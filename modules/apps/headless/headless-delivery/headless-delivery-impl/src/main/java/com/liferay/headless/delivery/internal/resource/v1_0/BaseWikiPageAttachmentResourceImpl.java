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

import com.liferay.headless.delivery.dto.v1_0.WikiPageAttachment;
import com.liferay.headless.delivery.resource.v1_0.WikiPageAttachmentResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseWikiPageAttachmentResourceImpl
	implements WikiPageAttachmentResource, EntityModelResource,
			   VulcanBatchEngineTaskItemDelegate<WikiPageAttachment> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-page-attachments/{wikiPageAttachmentId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Operation(
		description = "Deletes the wiki page attachment and returns a 204 if the operation succeeds."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "wikiPageAttachmentId")
		}
	)
	@Path("/wiki-page-attachments/{wikiPageAttachmentId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPageAttachment")})
	public void deleteWikiPageAttachment(
			@NotNull @Parameter(hidden = true)
			@PathParam("wikiPageAttachmentId") Long wikiPageAttachmentId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-page-attachments/batch'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes("application/json")
	@DELETE
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/wiki-page-attachments/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WikiPageAttachment")})
	public Response deleteWikiPageAttachmentBatch(
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
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
				WikiPageAttachment.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-page-attachments/{wikiPageAttachmentId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(description = "Retrieves the wiki page attachment.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "wikiPageAttachmentId")
		}
	)
	@Path("/wiki-page-attachments/{wikiPageAttachmentId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPageAttachment")})
	public WikiPageAttachment getWikiPageAttachment(
			@NotNull @Parameter(hidden = true)
			@PathParam("wikiPageAttachmentId") Long wikiPageAttachmentId)
		throws Exception {

		return new WikiPageAttachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(description = "Retrieves the wiki page's attachments.")
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}/wiki-page-attachments")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPageAttachment")})
	public Page<WikiPageAttachment> getWikiPageWikiPageAttachmentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes("multipart/form-data")
	@Operation(
		description = "Creates an attachment for the wiki page. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`WikiPageAttachment`) with the metadata."
	)
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}/wiki-page-attachments")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPageAttachment")})
	public WikiPageAttachment postWikiPageWikiPageAttachment(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId,
			MultipartBody multipartBody)
		throws Exception {

		return new WikiPageAttachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments/batch'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "wikiPageId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/wiki-pages/{wikiPageId}/wiki-page-attachments/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WikiPageAttachment")})
	public Response postWikiPageWikiPageAttachmentBatch(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId,
			MultipartBody multipartBody,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
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
				WikiPageAttachment.class.getName(), callbackURL, null, object)
		).build();
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<WikiPageAttachment> wikiPageAttachments,
			Map<String, Serializable> parameters)
		throws Exception {

		for (WikiPageAttachment wikiPageAttachment : wikiPageAttachments) {
			postWikiPageWikiPageAttachment(
				Long.valueOf((String)parameters.get("wikiPageId")), null);
		}
	}

	@Override
	public void delete(
			java.util.Collection<WikiPageAttachment> wikiPageAttachments,
			Map<String, Serializable> parameters)
		throws Exception {

		for (WikiPageAttachment wikiPageAttachment : wikiPageAttachments) {
			deleteWikiPageAttachment(wikiPageAttachment.getId());
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
	public Page<WikiPageAttachment> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return getWikiPageWikiPageAttachmentsPage(
			(Long)parameters.get("wikiPageId"));
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
			java.util.Collection<WikiPageAttachment> wikiPageAttachments,
			Map<String, Serializable> parameters)
		throws Exception {
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
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

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
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
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected VulcanBatchEngineImportTaskResource
		vulcanBatchEngineImportTaskResource;

}