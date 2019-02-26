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

package com.liferay.portal.workflow.metrics.rest.internal.graphql.mutation.v1_0;

import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.SLAResourceImpl;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLInvokeDetached
	public boolean deleteProcessSla(
			@GraphQLName("process-id") Long processId,
			@GraphQLName("sla-id") Long slaId)
		throws Exception {

		SLAResource sLAResource = _createSLAResource();

		return sLAResource.deleteProcessSla(processId, slaId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public SLA postProcessSlas(
			@GraphQLName("process-id") Long processId,
			@GraphQLName("SLA") SLA sLA)
		throws Exception {

		SLAResource sLAResource = _createSLAResource();

		return sLAResource.postProcessSlas(processId, sLA);
	}

	@GraphQLInvokeDetached
	public SLA putProcessSla(
			@GraphQLName("process-id") Long processId,
			@GraphQLName("sla-id") Long slaId, @GraphQLName("SLA") SLA sLA)
		throws Exception {

		SLAResource sLAResource = _createSLAResource();

		return sLAResource.putProcessSla(processId, slaId, sLA);
	}

	private static SLAResource _createSLAResource() {
		return new SLAResourceImpl();
	}

}