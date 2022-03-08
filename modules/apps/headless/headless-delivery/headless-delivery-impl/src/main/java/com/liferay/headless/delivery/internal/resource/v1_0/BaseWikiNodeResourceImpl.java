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

import com.liferay.headless.delivery.dto.v1_0.WikiNode;
import com.liferay.headless.delivery.resource.v1_0.WikiNodeResource;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
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
public abstract class BaseWikiNodeResourceImpl
	implements EntityModelResource, VulcanBatchEngineTaskItemDelegate<WikiNode>,
			   WikiNodeResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-nodes'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the wiki node's of a site. Results can be paginated, filtered, searched, and sorted."
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/sites/{siteId}/wiki-nodes")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<WikiNode> getSiteWikiNodesPage(
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-nodes' -d $'{"description": ___, "externalReferenceCode": ___, "name": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Creates a new wiki node"
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/sites/{siteId}/wiki-nodes")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public WikiNode postSiteWikiNode(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			WikiNode wikiNode)
		throws Exception {

		return new WikiNode();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/batch'  -u 'test@liferay.com:test'
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path("/sites/{siteId}/wiki-nodes/batch")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces("application/json")
	@Override
	public Response postSiteWikiNodeBatch(
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
				WikiNode.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Deletes the site's wiki node by external reference code."
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path(
		"/sites/{siteId}/wiki-nodes/by-external-reference-code/{externalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public void deleteSiteWikiNodeByExternalReferenceCode(
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
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the site's wiki node by external reference code."
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/sites/{siteId}/wiki-nodes/by-external-reference-code/{externalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public WikiNode getSiteWikiNodeByExternalReferenceCode(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {

		return new WikiNode();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/by-external-reference-code/{externalReferenceCode}' -d $'{"description": ___, "externalReferenceCode": ___, "name": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Updates the site's wiki node with the given external reference code, or creates it if it not exists."
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path(
		"/sites/{siteId}/wiki-nodes/by-external-reference-code/{externalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public WikiNode putSiteWikiNodeByExternalReferenceCode(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("externalReferenceCode")
			String externalReferenceCode,
			WikiNode wikiNode)
		throws Exception {

		return new WikiNode();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/permissions'  -u 'test@liferay.com:test'
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/sites/{siteId}/wiki-nodes/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteWikiNodePermissionsPage(
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
					ActionKeys.PERMISSIONS, "getSiteWikiNodePermissionsPage",
					portletName, siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putSiteWikiNodePermissionsPage",
					portletName, siteId)
			).build(),
			siteId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/permissions'  -u 'test@liferay.com:test'
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Path("/sites/{siteId}/wiki-nodes/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteWikiNodePermissionsPage(
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
					ActionKeys.PERMISSIONS, "getSiteWikiNodePermissionsPage",
					portletName, siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putSiteWikiNodePermissionsPage",
					portletName, siteId)
			).build(),
			siteId, portletName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Deletes the wiki node and returns a 204 if the operation succeeds."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "wikiNodeId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path("/wiki-nodes/{wikiNodeId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public void deleteWikiNode(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("wikiNodeId")
			Long wikiNodeId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/batch'  -u 'test@liferay.com:test'
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path("/wiki-nodes/batch")
	@javax.ws.rs.Produces("application/json")
	@Override
	public Response deleteWikiNodeBatch(
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
				WikiNode.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the wiki node"
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "wikiNodeId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/wiki-nodes/{wikiNodeId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public WikiNode getWikiNode(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("wikiNodeId")
			Long wikiNodeId)
		throws Exception {

		return new WikiNode();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}' -d $'{"description": ___, "externalReferenceCode": ___, "name": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Replaces the wiki node with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "wikiNodeId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/wiki-nodes/{wikiNodeId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public WikiNode putWikiNode(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("wikiNodeId")
			Long wikiNodeId,
			WikiNode wikiNode)
		throws Exception {

		return new WikiNode();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/batch'  -u 'test@liferay.com:test'
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
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path("/wiki-nodes/batch")
	@javax.ws.rs.Produces("application/json")
	@javax.ws.rs.PUT
	@Override
	public Response putWikiNodeBatch(
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
				WikiNode.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "wikiNodeId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "roleNames"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/wiki-nodes/{wikiNodeId}/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getWikiNodePermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("wikiNodeId")
				Long wikiNodeId,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("roleNames")
				String roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(wikiNodeId);
		Long resourceId = getPermissionCheckerResourceId(wikiNodeId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(wikiNodeId));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS, "getWikiNodePermissionsPage",
					resourceName, resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putWikiNodePermissionsPage",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "wikiNodeId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Path("/wiki-nodes/{wikiNodeId}/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putWikiNodePermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("wikiNodeId")
				Long wikiNodeId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(wikiNodeId);
		Long resourceId = getPermissionCheckerResourceId(wikiNodeId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(wikiNodeId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(wikiNodeId), resourceName,
			String.valueOf(resourceId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, resourceId,
				resourceName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS, "getWikiNodePermissionsPage",
					resourceName, resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putWikiNodePermissionsPage",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/subscribe'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "wikiNodeId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Path("/wiki-nodes/{wikiNodeId}/subscribe")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public void putWikiNodeSubscribe(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("wikiNodeId")
			Long wikiNodeId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "wikiNodeId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "WikiNode")}
	)
	@javax.ws.rs.Path("/wiki-nodes/{wikiNodeId}/unsubscribe")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public void putWikiNodeUnsubscribe(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("wikiNodeId")
			Long wikiNodeId)
		throws Exception {
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<WikiNode> wikiNodes,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<WikiNode, Exception> wikiNodeUnsafeConsumer =
			wikiNode -> {
			};

		if (parameters.containsKey("siteId")) {
			wikiNodeUnsafeConsumer = wikiNode -> postSiteWikiNode(
				(Long)parameters.get("siteId"), wikiNode);
		}

		if (contextBatchUnsafeConsumer != null) {
			contextBatchUnsafeConsumer.accept(
				wikiNodes, wikiNodeUnsafeConsumer);
		}
		else {
			for (WikiNode wikiNode : wikiNodes) {
				wikiNodeUnsafeConsumer.accept(wikiNode);
			}
		}
	}

	@Override
	public void delete(
			java.util.Collection<WikiNode> wikiNodes,
			Map<String, Serializable> parameters)
		throws Exception {

		for (WikiNode wikiNode : wikiNodes) {
			deleteWikiNode(wikiNode.getId());
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
	public Page<WikiNode> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (parameters.containsKey("siteId")) {
			return getSiteWikiNodesPage(
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
			java.util.Collection<WikiNode> wikiNodes,
			Map<String, Serializable> parameters)
		throws Exception {

		for (WikiNode wikiNode : wikiNodes) {
			putWikiNode(
				wikiNode.getId() != null ? wikiNode.getId() :
					Long.parseLong((String)parameters.get("wikiNodeId")),
				wikiNode);
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
			<java.util.Collection<WikiNode>,
			 UnsafeConsumer<WikiNode, Exception>, Exception>
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
		<java.util.Collection<WikiNode>, UnsafeConsumer<WikiNode, Exception>,
		 Exception> contextBatchUnsafeConsumer;
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
		LogFactoryUtil.getLog(BaseWikiNodeResourceImpl.class);

}