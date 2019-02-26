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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/portal-workflow-metrics-rest/v1.0
 *
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public interface SLAResource {

	public Page<SLA> getProcessSLAsPage(
				Long processId,Pagination pagination)
			throws Exception;
	public SLA postProcessSlas(
				Long processId,SLA sLA)
			throws Exception;
	public boolean deleteProcessSla(
				Long processId,Long slaId)
			throws Exception;
	public SLA getProcessSla(
				Long processId,Long slaId)
			throws Exception;
	public SLA putProcessSla(
				Long processId,Long slaId,SLA sLA)
			throws Exception;

	public void setContextCompany(Company contextCompany);

}