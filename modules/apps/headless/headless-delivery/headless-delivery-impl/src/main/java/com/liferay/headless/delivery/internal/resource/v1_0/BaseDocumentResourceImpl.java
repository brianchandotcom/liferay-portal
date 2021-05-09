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

import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.vulcan.multipart.MultipartBody;
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
import javax.ws.rs.PATCH;
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
public abstract class BaseDocumentResourceImpl
	implements DocumentResource, EntityModelResource,
			   VulcanBatchEngineTaskItemDelegate<Document> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "assetLibraryId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/asset-libraries/{assetLibraryId}/documents")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Page<Document> getAssetLibraryDocumentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId") Long
				assetLibraryId,
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents'  -u 'test@liferay.com:test'
	 */
	@Consumes("multipart/form-data")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "assetLibraryId")}
	)
	@Path("/asset-libraries/{assetLibraryId}/documents")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Document postAssetLibraryDocument(
			@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId") Long
				assetLibraryId,
			MultipartBody multipartBody)
		throws Exception {

		return new Document();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "assetLibraryId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/asset-libraries/{assetLibraryId}/documents/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Response postAssetLibraryDocumentBatch(
			@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId") Long
				assetLibraryId,
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
				Document.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "assetLibraryId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/asset-libraries/{assetLibraryId}/documents/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getAssetLibraryDocumentPermissionsPage(
				@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId")
					Long assetLibraryId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(assetLibraryId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName,
			assetLibraryId, assetLibraryId);

		return toPermissionPage(assetLibraryId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "assetLibraryId")}
	)
	@Path("/asset-libraries/{assetLibraryId}/documents/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "Document")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putAssetLibraryDocumentPermission(
				@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId")
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

		return toPermissionPage(assetLibraryId, portletName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/document-folders/{documentFolderId}/documents'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the folder's documents. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "documentFolderId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/document-folders/{documentFolderId}/documents")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Page<Document> getDocumentFolderDocumentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("documentFolderId")
				Long documentFolderId,
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/document-folders/{documentFolderId}/documents'  -u 'test@liferay.com:test'
	 */
	@Consumes("multipart/form-data")
	@Operation(
		description = "Creates a new document inside the folder identified by `documentFolderId`. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentFolderId")}
	)
	@Path("/document-folders/{documentFolderId}/documents")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Document postDocumentFolderDocument(
			@NotNull @Parameter(hidden = true) @PathParam("documentFolderId")
				Long documentFolderId,
			MultipartBody multipartBody)
		throws Exception {

		return new Document();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/document-folders/{documentFolderId}/documents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "documentFolderId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/document-folders/{documentFolderId}/documents/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Response postDocumentFolderDocumentBatch(
			@NotNull @Parameter(hidden = true) @PathParam("documentFolderId")
				Long documentFolderId,
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
				Document.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the document and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@Path("/documents/{documentId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public void deleteDocument(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/documents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/documents/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Response deleteDocumentBatch(
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
				Document.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the document.")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@Path("/documents/{documentId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Document getDocument(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId)
		throws Exception {

		return new Document();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}'  -u 'test@liferay.com:test'
	 */
	@Consumes("multipart/form-data")
	@Operation(
		description = "Updates only the fields received in the request body, leaving any other fields untouched. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@PATCH
	@Path("/documents/{documentId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Document patchDocument(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId,
			MultipartBody multipartBody)
		throws Exception {

		return new Document();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}'  -u 'test@liferay.com:test'
	 */
	@Consumes("multipart/form-data")
	@Operation(
		description = "Replaces the document with the information sent in the request body. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@Path("/documents/{documentId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "Document")})
	public Document putDocument(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId,
			MultipartBody multipartBody)
		throws Exception {

		return new Document();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/documents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/documents/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "Document")})
	public Response putDocumentBatch(
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
			vulcanBatchEngineImportTaskResource.putImportTask(
				Document.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the document's rating and returns a 204 if the operation succeeded."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@Path("/documents/{documentId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public void deleteDocumentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the document's rating.")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@Path("/documents/{documentId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Rating getDocumentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a new rating for the document, by the user who authenticated the request."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@Path("/documents/{documentId}/my-rating")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Rating postDocumentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@Path("/documents/{documentId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "Document")})
	public Rating putDocumentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "documentId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/documents/{documentId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getDocumentPermissionsPage(
				@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
					documentId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(documentId);
		Long resourceId = getPermissionCheckerResourceId(documentId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(documentId));

		return toPermissionPage(resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@Path("/documents/{documentId}/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "Document")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putDocumentPermission(
				@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
					documentId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(documentId);
		Long resourceId = getPermissionCheckerResourceId(documentId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(documentId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(documentId), resourceName,
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
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/documents/{documentId}/rendered-content-by-display-page/{displayPageKey}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the document's rendered display page")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "documentId"),
			@Parameter(in = ParameterIn.PATH, name = "displayPageKey")
		}
	)
	@Path(
		"/documents/{documentId}/rendered-content-by-display-page/{displayPageKey}"
	)
	@Produces("text/html")
	@Tags(value = {@Tag(name = "Document")})
	public String getDocumentRenderedContentByDisplayPageDisplayPageKey(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId,
			@NotNull @Parameter(hidden = true) @PathParam("displayPageKey")
				String displayPageKey)
		throws Exception {

		return StringPool.BLANK;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/documents'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the documents in the site's root folder. Results can be paginated, filtered, searched, flattened, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/sites/{siteId}/documents")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Page<Document> getSiteDocumentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/documents'  -u 'test@liferay.com:test'
	 */
	@Consumes("multipart/form-data")
	@Operation(
		description = "Creates a new document. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata."
	)
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/documents")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Document postSiteDocument(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			MultipartBody multipartBody)
		throws Exception {

		return new Document();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/documents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/sites/{siteId}/documents/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Response postSiteDocumentBatch(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
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
				Document.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/documents/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/sites/{siteId}/documents/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Document")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteDocumentPermissionsPage(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(siteId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName, siteId,
			siteId);

		return toPermissionPage(siteId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/documents/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/documents/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "Document")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteDocumentPermission(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
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

		return toPermissionPage(siteId, portletName, null);
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<Document> documents,
			Map<String, Serializable> parameters)
		throws Exception {

		for (Document document : documents) {
			postSiteDocument(
				Long.parseLong((String)parameters.get("siteId")), null);
		}
	}

	@Override
	public void delete(
			java.util.Collection<Document> documents,
			Map<String, Serializable> parameters)
		throws Exception {

		for (Document document : documents) {
			deleteDocument(document.getId());
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
	public Page<Document> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return getSiteDocumentsPage(
			Long.parseLong((String)parameters.get("siteId")),
			Boolean.parseBoolean((String)parameters.get("flatten")), search,
			null, filter, pagination, sorts);
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
			java.util.Collection<Document> documents,
			Map<String, Serializable> parameters)
		throws Exception {

		for (Document document : documents) {
			putDocument(
				document.getId() != null ? document.getId() :
					Long.parseLong((String)parameters.get("documentId")),
				null);
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