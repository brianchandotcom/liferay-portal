/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

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
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseSLAResourceImpl
	implements SLAResource, EntityModelResource,
			   VulcanBatchEngineTaskItemDelegate<SLA> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/processes/{processId}/slas'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "processId"),
			@Parameter(in = ParameterIn.QUERY, name = "status"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/processes/{processId}/slas")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "SLA")})
	public Page<SLA> getProcessSLAsPage(
			@NotNull @Parameter(hidden = true) @PathParam("processId") Long
				processId,
			@Parameter(hidden = true) @QueryParam("status") Integer status,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/processes/{processId}/slas' -d $'{"calendarKey": ___, "dateModified": ___, "description": ___, "duration": ___, "id": ___, "name": ___, "pauseNodeKeys": ___, "processId": ___, "startNodeKeys": ___, "status": ___, "stopNodeKeys": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "processId")})
	@Path("/processes/{processId}/slas")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "SLA")})
	public SLA postProcessSLA(
			@NotNull @Parameter(hidden = true) @PathParam("processId") Long
				processId,
			SLA sla)
		throws Exception {

		return new SLA();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/processes/{processId}/slas/batch' -d $'{"calendarKey": ___, "dateModified": ___, "description": ___, "duration": ___, "id": ___, "name": ___, "pauseNodeKeys": ___, "processId": ___, "startNodeKeys": ___, "status": ___, "stopNodeKeys": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "processId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/processes/{processId}/slas/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "SLA")})
	public Response postProcessSLABatch(
			@NotNull @Parameter(hidden = true) @PathParam("processId") Long
				processId,
			SLA sla,
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
				SLA.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/slas/{slaId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "slaId")})
	@Path("/slas/{slaId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "SLA")})
	public void deleteSLA(
			@NotNull @Parameter(hidden = true) @PathParam("slaId") Long slaId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/slas/{slaId}/batch'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes("application/json")
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "slaId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/slas/{slaId}/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "SLA")})
	public Response deleteSLABatch(
			@NotNull @Parameter(hidden = true) @PathParam("slaId") Long slaId,
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
				SLA.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/slas/{slaId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "slaId")})
	@Path("/slas/{slaId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "SLA")})
	public SLA getSLA(
			@NotNull @Parameter(hidden = true) @PathParam("slaId") Long slaId)
		throws Exception {

		return new SLA();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/slas/{slaId}' -d $'{"calendarKey": ___, "dateModified": ___, "description": ___, "duration": ___, "id": ___, "name": ___, "pauseNodeKeys": ___, "processId": ___, "startNodeKeys": ___, "status": ___, "stopNodeKeys": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@PUT
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "slaId")})
	@Path("/slas/{slaId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "SLA")})
	public SLA putSLA(
			@NotNull @Parameter(hidden = true) @PathParam("slaId") Long slaId,
			SLA sla)
		throws Exception {

		return new SLA();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/slas/{slaId}/batch' -d $'{"calendarKey": ___, "dateModified": ___, "description": ___, "duration": ___, "id": ___, "name": ___, "pauseNodeKeys": ___, "processId": ___, "startNodeKeys": ___, "status": ___, "stopNodeKeys": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "slaId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/slas/{slaId}/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "SLA")})
	public Response putSLABatch(
			@NotNull @Parameter(hidden = true) @PathParam("slaId") Long slaId,
			SLA sla,
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
				SLA.class.getName(), callbackURL, object)
		).build();
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<SLA> slas,
			Map<String, Serializable> parameters)
		throws Exception {

		for (SLA sla : slas) {
			postProcessSLA(
				Long.valueOf((String)parameters.get("processId")), sla);
		}
	}

	@Override
	public void delete(
			java.util.Collection<SLA> slas,
			Map<String, Serializable> parameters)
		throws Exception {

		for (SLA sla : slas) {
			deleteSLA(sla.getId());
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
	public Page<SLA> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return getProcessSLAsPage(
			(Long)parameters.get("processId"),
			(Integer)parameters.get("status"), pagination);
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
			java.util.Collection<SLA> slas,
			Map<String, Serializable> parameters)
		throws Exception {

		for (SLA sla : slas) {
			putSLA(
				sla.getId() != null ? sla.getId() :
				(Long)parameters.get("slaId"),
				sla);
		}
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