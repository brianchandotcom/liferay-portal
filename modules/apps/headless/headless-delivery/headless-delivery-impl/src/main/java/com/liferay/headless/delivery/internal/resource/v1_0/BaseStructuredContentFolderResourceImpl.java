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

import com.liferay.headless.delivery.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
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
public abstract class BaseStructuredContentFolderResourceImpl
	implements EntityModelResource, StructuredContentFolderResource,
			   VulcanBatchEngineTaskItemDelegate<StructuredContentFolder> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "assetLibraryId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "flatten"
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
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/asset-libraries/{assetLibraryId}/structured-content-folders"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<StructuredContentFolder>
			getAssetLibraryStructuredContentFoldersPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("assetLibraryId")
				Long assetLibraryId,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("flatten")
				Boolean flatten,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("search")
				String search,
				@javax.ws.rs.core.Context
					com.liferay.portal.vulcan.aggregation.Aggregation
						aggregation,
				@javax.ws.rs.core.Context Filter filter,
				@javax.ws.rs.core.Context Pagination pagination,
				@javax.ws.rs.core.Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders' -d $'{"customFields": ___, "description": ___, "name": ___, "parentStructuredContentFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "assetLibraryId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path(
		"/asset-libraries/{assetLibraryId}/structured-content-folders"
	)
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public StructuredContentFolder postAssetLibraryStructuredContentFolder(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("assetLibraryId")
			Long assetLibraryId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		return new StructuredContentFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/batch'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "assetLibraryId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "callbackURL"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path(
		"/asset-libraries/{assetLibraryId}/structured-content-folders/batch"
	)
	@javax.ws.rs.POST
	@javax.ws.rs.Produces("application/json")
	@Override
	public Response postAssetLibraryStructuredContentFolderBatch(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("assetLibraryId")
			Long assetLibraryId,
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
				StructuredContentFolder.class.getName(), callbackURL, null,
				object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "assetLibraryId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "roleNames"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/asset-libraries/{assetLibraryId}/structured-content-folders/permissions"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getAssetLibraryStructuredContentFolderPermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("assetLibraryId")
				Long assetLibraryId,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("roleNames")
				String roleNames)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(assetLibraryId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName,
			assetLibraryId, assetLibraryId);

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getAssetLibraryStructuredContentFolderPermissionsPage",
					portletName, assetLibraryId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putAssetLibraryStructuredContentFolderPermissionsPage",
					portletName, assetLibraryId)
			).build(),
			assetLibraryId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "assetLibraryId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Path(
		"/asset-libraries/{assetLibraryId}/structured-content-folders/permissions"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putAssetLibraryStructuredContentFolderPermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("assetLibraryId")
				Long assetLibraryId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(assetLibraryId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName,
			assetLibraryId, assetLibraryId);

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(), assetLibraryId, portletName,
			String.valueOf(assetLibraryId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, assetLibraryId,
				portletName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getAssetLibraryStructuredContentFolderPermissionsPage",
					portletName, assetLibraryId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putAssetLibraryStructuredContentFolderPermissionsPage",
					portletName, assetLibraryId)
			).build(),
			assetLibraryId, portletName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-content-folders'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the site's structured content folders. Results can be paginated, filtered, searched, flattened, and sorted."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "flatten"
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
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/sites/{siteId}/structured-content-folders")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<StructuredContentFolder> getSiteStructuredContentFoldersPage(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("flatten")
			Boolean flatten,
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-content-folders' -d $'{"customFields": ___, "description": ___, "name": ___, "parentStructuredContentFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Creates a new structured content folder."
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
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/sites/{siteId}/structured-content-folders")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public StructuredContentFolder postSiteStructuredContentFolder(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteId")
			Long siteId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		return new StructuredContentFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/batch'  -u 'test@liferay.com:test'
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
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path("/sites/{siteId}/structured-content-folders/batch")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces("application/json")
	@Override
	public Response postSiteStructuredContentFolderBatch(
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
				StructuredContentFolder.class.getName(), callbackURL, null,
				object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/permissions'  -u 'test@liferay.com:test'
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
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/sites/{siteId}/structured-content-folders/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteStructuredContentFolderPermissionsPage(
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
					ActionKeys.PERMISSIONS,
					"getSiteStructuredContentFolderPermissionsPage",
					portletName, siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteStructuredContentFolderPermissionsPage",
					portletName, siteId)
			).build(),
			siteId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/permissions'  -u 'test@liferay.com:test'
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
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Path("/sites/{siteId}/structured-content-folders/permissions")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteStructuredContentFolderPermissionsPage(
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
					ActionKeys.PERMISSIONS,
					"getSiteStructuredContentFolderPermissionsPage",
					portletName, siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteStructuredContentFolderPermissionsPage",
					portletName, siteId)
			).build(),
			siteId, portletName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folder/{structuredContentFolderId}/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "structuredContentFolderId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "roleNames"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/structured-content-folder/{structuredContentFolderId}/permissions"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getStructuredContentFolderPermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("structuredContentFolderId")
				Long structuredContentFolderId,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("roleNames")
				String roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			structuredContentFolderId);
		Long resourceId = getPermissionCheckerResourceId(
			structuredContentFolderId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(structuredContentFolderId));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getStructuredContentFolderPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putStructuredContentFolderPermissionsPage", resourceName,
					resourceId)
			).build(),
			resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folder/{structuredContentFolderId}/permissions'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "structuredContentFolderId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Path(
		"/structured-content-folder/{structuredContentFolderId}/permissions"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putStructuredContentFolderPermissionsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("structuredContentFolderId")
				Long structuredContentFolderId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			structuredContentFolderId);
		Long resourceId = getPermissionCheckerResourceId(
			structuredContentFolderId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(structuredContentFolderId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(structuredContentFolderId),
			resourceName, String.valueOf(resourceId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, resourceId,
				resourceName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getStructuredContentFolderPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putStructuredContentFolderPermissionsPage", resourceName,
					resourceId)
			).build(),
			resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the parent structured content folder's subfolders. Results can be paginated, filtered, searched, and sorted."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "parentStructuredContentFolderId"
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
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<StructuredContentFolder>
			getStructuredContentFolderStructuredContentFoldersPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("parentStructuredContentFolderId")
				Long parentStructuredContentFolderId,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("search")
				String search,
				@javax.ws.rs.core.Context
					com.liferay.portal.vulcan.aggregation.Aggregation
						aggregation,
				@javax.ws.rs.core.Context Filter filter,
				@javax.ws.rs.core.Context Pagination pagination,
				@javax.ws.rs.core.Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders' -d $'{"customFields": ___, "description": ___, "name": ___, "parentStructuredContentFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Creates a new structured content folder in an existing folder."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "parentStructuredContentFolderId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path(
		"/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders"
	)
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public StructuredContentFolder
			postStructuredContentFolderStructuredContentFolder(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("parentStructuredContentFolderId")
				Long parentStructuredContentFolderId,
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return new StructuredContentFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Deletes the structured content folder and returns a 204 if the operation succeeds."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "structuredContentFolderId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path("/structured-content-folders/{structuredContentFolderId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public void deleteStructuredContentFolder(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("structuredContentFolderId")
			Long structuredContentFolderId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/batch'  -u 'test@liferay.com:test'
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
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path("/structured-content-folders/batch")
	@javax.ws.rs.Produces("application/json")
	@Override
	public Response deleteStructuredContentFolderBatch(
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
				StructuredContentFolder.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the structured content folder."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "structuredContentFolderId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/structured-content-folders/{structuredContentFolderId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public StructuredContentFolder getStructuredContentFolder(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("structuredContentFolderId")
			Long structuredContentFolderId)
		throws Exception {

		return new StructuredContentFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}' -d $'{"customFields": ___, "description": ___, "name": ___, "parentStructuredContentFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "structuredContentFolderId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.PATCH
	@javax.ws.rs.Path("/structured-content-folders/{structuredContentFolderId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public StructuredContentFolder patchStructuredContentFolder(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("structuredContentFolderId")
			Long structuredContentFolderId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		StructuredContentFolder existingStructuredContentFolder =
			getStructuredContentFolder(structuredContentFolderId);

		if (structuredContentFolder.getActions() != null) {
			existingStructuredContentFolder.setActions(
				structuredContentFolder.getActions());
		}

		if (structuredContentFolder.getAssetLibraryKey() != null) {
			existingStructuredContentFolder.setAssetLibraryKey(
				structuredContentFolder.getAssetLibraryKey());
		}

		if (structuredContentFolder.getDateCreated() != null) {
			existingStructuredContentFolder.setDateCreated(
				structuredContentFolder.getDateCreated());
		}

		if (structuredContentFolder.getDateModified() != null) {
			existingStructuredContentFolder.setDateModified(
				structuredContentFolder.getDateModified());
		}

		if (structuredContentFolder.getDescription() != null) {
			existingStructuredContentFolder.setDescription(
				structuredContentFolder.getDescription());
		}

		if (structuredContentFolder.getName() != null) {
			existingStructuredContentFolder.setName(
				structuredContentFolder.getName());
		}

		if (structuredContentFolder.getNumberOfStructuredContentFolders() !=
				null) {

			existingStructuredContentFolder.setNumberOfStructuredContentFolders(
				structuredContentFolder.getNumberOfStructuredContentFolders());
		}

		if (structuredContentFolder.getNumberOfStructuredContents() != null) {
			existingStructuredContentFolder.setNumberOfStructuredContents(
				structuredContentFolder.getNumberOfStructuredContents());
		}

		if (structuredContentFolder.getParentStructuredContentFolderId() !=
				null) {

			existingStructuredContentFolder.setParentStructuredContentFolderId(
				structuredContentFolder.getParentStructuredContentFolderId());
		}

		if (structuredContentFolder.getSiteId() != null) {
			existingStructuredContentFolder.setSiteId(
				structuredContentFolder.getSiteId());
		}

		if (structuredContentFolder.getSubscribed() != null) {
			existingStructuredContentFolder.setSubscribed(
				structuredContentFolder.getSubscribed());
		}

		if (structuredContentFolder.getViewableBy() != null) {
			existingStructuredContentFolder.setViewableBy(
				structuredContentFolder.getViewableBy());
		}

		preparePatch(structuredContentFolder, existingStructuredContentFolder);

		return putStructuredContentFolder(
			structuredContentFolderId, existingStructuredContentFolder);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}' -d $'{"customFields": ___, "description": ___, "name": ___, "parentStructuredContentFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Replaces the structured content folder with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "structuredContentFolderId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/structured-content-folders/{structuredContentFolderId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public StructuredContentFolder putStructuredContentFolder(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("structuredContentFolderId")
			Long structuredContentFolderId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		return new StructuredContentFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/batch'  -u 'test@liferay.com:test'
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
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path("/structured-content-folders/batch")
	@javax.ws.rs.Produces("application/json")
	@javax.ws.rs.PUT
	@Override
	public Response putStructuredContentFolderBatch(
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
				StructuredContentFolder.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/subscribe'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "structuredContentFolderId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Path(
		"/structured-content-folders/{structuredContentFolderId}/subscribe"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public void putStructuredContentFolderSubscribe(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("structuredContentFolderId")
			Long structuredContentFolderId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "structuredContentFolderId"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(
				name = "StructuredContentFolder"
			)
		}
	)
	@javax.ws.rs.Path(
		"/structured-content-folders/{structuredContentFolderId}/unsubscribe"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public void putStructuredContentFolderUnsubscribe(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("structuredContentFolderId")
			Long structuredContentFolderId)
		throws Exception {
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<StructuredContentFolder>
				structuredContentFolders,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<StructuredContentFolder, Exception>
			structuredContentFolderUnsafeConsumer = structuredContentFolder -> {
			};

		if (parameters.containsKey("assetLibraryId")) {
			structuredContentFolderUnsafeConsumer =
				structuredContentFolder ->
					postAssetLibraryStructuredContentFolder(
						(Long)parameters.get("assetLibraryId"),
						structuredContentFolder);
		}
		else if (parameters.containsKey("siteId")) {
			structuredContentFolderUnsafeConsumer =
				structuredContentFolder -> postSiteStructuredContentFolder(
					(Long)parameters.get("siteId"), structuredContentFolder);
		}

		if (contextBatchUnsafeConsumer != null) {
			contextBatchUnsafeConsumer.accept(
				structuredContentFolders,
				structuredContentFolderUnsafeConsumer);
		}
		else {
			for (StructuredContentFolder structuredContentFolder :
					structuredContentFolders) {

				structuredContentFolderUnsafeConsumer.accept(
					structuredContentFolder);
			}
		}
	}

	@Override
	public void delete(
			java.util.Collection<StructuredContentFolder>
				structuredContentFolders,
			Map<String, Serializable> parameters)
		throws Exception {

		for (StructuredContentFolder structuredContentFolder :
				structuredContentFolders) {

			deleteStructuredContentFolder(structuredContentFolder.getId());
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
	public Page<StructuredContentFolder> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (parameters.containsKey("assetLibraryId")) {
			return getAssetLibraryStructuredContentFoldersPage(
				(Long)parameters.get("assetLibraryId"),
				Boolean.parseBoolean((String)parameters.get("flatten")), search,
				null, filter, pagination, sorts);
		}
		else if (parameters.containsKey("siteId")) {
			return getSiteStructuredContentFoldersPage(
				(Long)parameters.get("siteId"),
				Boolean.parseBoolean((String)parameters.get("flatten")), search,
				null, filter, pagination, sorts);
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
			java.util.Collection<StructuredContentFolder>
				structuredContentFolders,
			Map<String, Serializable> parameters)
		throws Exception {

		for (StructuredContentFolder structuredContentFolder :
				structuredContentFolders) {

			putStructuredContentFolder(
				structuredContentFolder.getId() != null ?
					structuredContentFolder.getId() :
						Long.parseLong(
							(String)parameters.get(
								"structuredContentFolderId")),
				structuredContentFolder);
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
			<java.util.Collection<StructuredContentFolder>,
			 UnsafeConsumer<StructuredContentFolder, Exception>, Exception>
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
		StructuredContentFolder structuredContentFolder,
		StructuredContentFolder existingStructuredContentFolder) {
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
		<java.util.Collection<StructuredContentFolder>,
		 UnsafeConsumer<StructuredContentFolder, Exception>, Exception>
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
		LogFactoryUtil.getLog(BaseStructuredContentFolderResourceImpl.class);

}