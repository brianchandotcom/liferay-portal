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

package com.liferay.portal.workflow.metrics.rest.internal.graphql.query.v1_0;

import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Process> getProcessesPage( @GraphQLName("title") String title , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getProcessResource().getProcessesPage( title , Pagination.of(perPage, page) ).getItems();

	}

	private static ProcessResource _getProcessResource() {
			return _processResourceServiceTracker.getService();
	}

	private static final ServiceTracker<ProcessResource, ProcessResource> _processResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

		ServiceTracker<ProcessResource, ProcessResource> processResourceServiceTracker =
			new ServiceTracker<ProcessResource, ProcessResource>(bundle.getBundleContext(), ProcessResource.class, null);

		processResourceServiceTracker.open();

		_processResourceServiceTracker = processResourceServiceTracker;

	}

}