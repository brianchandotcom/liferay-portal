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

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.resource.OpenAPIResource;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeMetric;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Calendar;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.HistogramMetric;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Index;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.NodeMetric;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.ProcessMetric;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.ProcessVersion;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.ReindexStatus;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Role;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLAResult;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.TimeRange;

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
 * @author Rafael Praxedes
 * @generated
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(description = "A Java client JAR is available for use with the group ID 'com.liferay', artifact ID 'com.liferay.portal.workflow.metrics.rest.client', and version '3.0.17'.", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "", version = "v1.0")
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
				put(AssigneeMetricResourceImpl.class, AssigneeMetric.class);
				put(AssigneeResourceImpl.class, Assignee.class);
				put(CalendarResourceImpl.class, Calendar.class);
				put(HistogramMetricResourceImpl.class, HistogramMetric.class);
				put(IndexResourceImpl.class, Index.class);
				put(InstanceResourceImpl.class, Instance.class);
				put(NodeMetricResourceImpl.class, NodeMetric.class);
				put(NodeResourceImpl.class, Node.class);
				put(ProcessMetricResourceImpl.class, ProcessMetric.class);
				put(ProcessResourceImpl.class, Process.class);
				put(ProcessVersionResourceImpl.class, ProcessVersion.class);
				put(ReindexStatusResourceImpl.class, ReindexStatus.class);
				put(RoleResourceImpl.class, Role.class);
				put(SLAResourceImpl.class, SLA.class);
				put(SLAResultResourceImpl.class, SLAResult.class);
				put(TaskResourceImpl.class, Task.class);
				put(TimeRangeResourceImpl.class, TimeRange.class);

				put(OpenAPIResourceImpl.class, null);
			}
		};

	@Context
	private Company _company;

}