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

import com.liferay.headless.delivery.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.resource.v1_0.WikiPageResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
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
public abstract class BaseWikiPageResourceImpl
	implements EntityModelResource, VulcanBatchEngineTaskItemDelegate<WikiPage>,
			   WikiPageResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the wiki page by external reference code."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.PATH, name = "siteId")
		}
	)
	@Path(
		"/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public void deleteSiteWikiPageByExternalReferenceCode(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the wiki page by external reference code"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.PATH, name = "siteId")
		}
	)
	@Path(
		"/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public WikiPage getSiteWikiPageByExternalReferenceCode(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId)
		throws Exception {

		return new WikiPage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}' -d $'{"content": ___, "customFields": ___, "description": ___, "encodingFormat": ___, "externalReferenceCode": ___, "headline": ___, "keywords": ___, "parentWikiPageId": ___, "taxonomyCategoryIds": ___, "viewableBy": ___, "wikiNodeId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the wiki page with the given external reference code, or creates it if it not exists."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.PATH, name = "siteId")
		}
	)
	@Path(
		"/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "WikiPage")})
	public WikiPage putSiteWikiPageByExternalReferenceCode(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			WikiPage wikiPage)
		throws Exception {

		return new WikiPage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/wiki-pages'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the wiki page's of a node. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "wikiNodeId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/wiki-nodes/{wikiNodeId}/wiki-pages")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public Page<WikiPage> getWikiNodeWikiPagesPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiNodeId") Long
				wikiNodeId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context com.liferay.portal.vulcan.aggregation.Aggregation
				aggregation,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/wiki-pages' -d $'{"content": ___, "customFields": ___, "description": ___, "encodingFormat": ___, "externalReferenceCode": ___, "headline": ___, "keywords": ___, "parentWikiPageId": ___, "taxonomyCategoryIds": ___, "viewableBy": ___, "wikiNodeId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new wiki page")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiNodeId")}
	)
	@Path("/wiki-nodes/{wikiNodeId}/wiki-pages")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public WikiPage postWikiNodeWikiPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiNodeId") Long
				wikiNodeId,
			WikiPage wikiPage)
		throws Exception {

		return new WikiPage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/wiki-pages/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "wikiNodeId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/wiki-nodes/{wikiNodeId}/wiki-pages/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WikiPage")})
	public Response postWikiNodeWikiPageBatch(
			@NotNull @Parameter(hidden = true) @PathParam("wikiNodeId") Long
				wikiNodeId,
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
				WikiPage.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{parentWikiPageId}/wiki-pages'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the child wiki page's of a wiki page.")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "parentWikiPageId")}
	)
	@Path("/wiki-pages/{parentWikiPageId}/wiki-pages")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public Page<WikiPage> getWikiPageWikiPagesPage(
			@NotNull @Parameter(hidden = true) @PathParam("parentWikiPageId")
				Long parentWikiPageId)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{parentWikiPageId}/wiki-pages' -d $'{"content": ___, "customFields": ___, "description": ___, "encodingFormat": ___, "externalReferenceCode": ___, "headline": ___, "keywords": ___, "parentWikiPageId": ___, "taxonomyCategoryIds": ___, "viewableBy": ___, "wikiNodeId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a child wiki page of the parent wiki page."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "parentWikiPageId")}
	)
	@Path("/wiki-pages/{parentWikiPageId}/wiki-pages")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public WikiPage postWikiPageWikiPage(
			@NotNull @Parameter(hidden = true) @PathParam("parentWikiPageId")
				Long parentWikiPageId,
			WikiPage wikiPage)
		throws Exception {

		return new WikiPage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the wiki page and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public void deleteWikiPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/wiki-pages/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WikiPage")})
	public Response deleteWikiPageBatch(
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
				WikiPage.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the wiki page")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public WikiPage getWikiPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId)
		throws Exception {

		return new WikiPage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}' -d $'{"content": ___, "customFields": ___, "description": ___, "encodingFormat": ___, "externalReferenceCode": ___, "headline": ___, "keywords": ___, "parentWikiPageId": ___, "taxonomyCategoryIds": ___, "viewableBy": ___, "wikiNodeId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the wiki page with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "WikiPage")})
	public WikiPage putWikiPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId,
			WikiPage wikiPage)
		throws Exception {

		return new WikiPage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/wiki-pages/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "WikiPage")})
	public Response putWikiPageBatch(
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
			vulcanBatchEngineImportTaskResource.putImportTask(
				WikiPage.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "wikiPageId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/wiki-pages/{wikiPageId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getWikiPagePermissionsPage(
				@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
					wikiPageId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(wikiPageId);
		Long resourceId = getPermissionCheckerResourceId(wikiPageId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(wikiPageId));

		return toPermissionPage(resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "WikiPage")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putWikiPagePermission(
				@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
					wikiPageId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(wikiPageId);
		Long resourceId = getPermissionCheckerResourceId(wikiPageId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(wikiPageId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(wikiPageId), resourceName,
			String.valueOf(resourceId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, resourceId,
				resourceName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));

		return toPermissionPage(resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}/subscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "WikiPage")})
	public void putWikiPageSubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-pages/{wikiPageId}/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}/unsubscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "WikiPage")})
	public void putWikiPageUnsubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId)
		throws Exception {
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<WikiPage> wikiPages,
			Map<String, Serializable> parameters)
		throws Exception {

		for (WikiPage wikiPage : wikiPages) {
			postWikiNodeWikiPage(
				Long.parseLong((String)parameters.get("wikiNodeId")), wikiPage);
		}
	}

	@Override
	public void delete(
			java.util.Collection<WikiPage> wikiPages,
			Map<String, Serializable> parameters)
		throws Exception {

		for (WikiPage wikiPage : wikiPages) {
			deleteWikiPage(wikiPage.getId());
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
	public Page<WikiPage> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return getWikiNodeWikiPagesPage(
			Long.parseLong((String)parameters.get("wikiNodeId")), search, null,
			filter, pagination, sorts);
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
			java.util.Collection<WikiPage> wikiPages,
			Map<String, Serializable> parameters)
		throws Exception {

		for (WikiPage wikiPage : wikiPages) {
			putWikiPage(
				wikiPage.getId() != null ? wikiPage.getId() :
					Long.parseLong((String)parameters.get("wikiPageId")),
				wikiPage);
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
			toPermissionPage(long id, String resourceName, String roleNames)
		throws Exception {

		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(resourceName);

		if (Validator.isNotNull(roleNames)) {
			return Page.of(
				transform(
					PermissionUtil.getRoles(
						contextCompany, roleLocalService,
						StringUtil.split(roleNames)),
					role -> PermissionUtil.toPermission(
						contextCompany.getCompanyId(), id, resourceActions,
						resourceName, resourcePermissionLocalService, role)));
		}

		return Page.of(
			transform(
				resourcePermissionLocalService.getResourcePermissions(
					contextCompany.getCompanyId(), resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(id)),
				resourcePermission -> PermissionUtil.toPermission(
					resourceActions, resourcePermission,
					roleLocalService.getRole(resourcePermission.getRoleId()))));
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