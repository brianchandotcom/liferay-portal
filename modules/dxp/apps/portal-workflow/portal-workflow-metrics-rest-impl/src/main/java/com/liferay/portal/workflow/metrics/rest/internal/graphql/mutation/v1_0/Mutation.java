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
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLField
	@GraphQLInvokeDetached
	public SLA postProcessSlas(
	@GraphQLName("process-id") Long processId,@GraphQLName("SLA") SLA sLA)
			throws Exception {

				return _getSLAResource().postProcessSlas(
					processId,sLA);
	}
	@GraphQLInvokeDetached
	public boolean deleteProcessSla(
	@GraphQLName("process-id") Long processId,@GraphQLName("sla-id") Long slaId)
			throws Exception {

				return _getSLAResource().deleteProcessSla(
					processId,slaId);
	}
	@GraphQLInvokeDetached
	public SLA putProcessSla(
	@GraphQLName("process-id") Long processId,@GraphQLName("sla-id") Long slaId,@GraphQLName("SLA") SLA sLA)
			throws Exception {

				return _getSLAResource().putProcessSla(
					processId,slaId,sLA);
	}

	private static SLAResource _getSLAResource() {
			return _sLAResourceServiceTracker.getService();
	}

	private static final ServiceTracker<SLAResource, SLAResource>
			_sLAResourceServiceTracker;

		static {
			Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

				ServiceTracker<SLAResource, SLAResource>
					sLAResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							SLAResource.class, null);

				sLAResourceServiceTracker.open();

				_sLAResourceServiceTracker =
					sLAResourceServiceTracker;
	}

}