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

package com.liferay.portal.workflow.metrics.resource.v1_0;

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.dto.v1_0.Process;

import javax.annotation.Generated;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/portal-workflow-metrics/v1.0
 *
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface ProcessResource {

	@GET
	@Path("/process")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Process> getProcessPage( @QueryParam("companyId") Long companyId , @QueryParam("title") String title , @Context Pagination pagination ) throws Exception;
}