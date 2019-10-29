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

package com.liferay.headless.admin.workflow.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.workflow.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.workflow.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.workflow.resource.v1_0.CreatorResource;
import com.liferay.headless.admin.workflow.resource.v1_0.TransitionsResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowInstanceResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowLogResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setWorkflowInstanceResourceComponentServiceObjects(
			_workflowInstanceResourceComponentServiceObjects);
		Mutation.setWorkflowTaskResourceComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects);

		Query.setCreatorResourceComponentServiceObjects(
			_creatorResourceComponentServiceObjects);
		Query.setTransitionsResourceComponentServiceObjects(
			_transitionsResourceComponentServiceObjects);
		Query.setWorkflowInstanceResourceComponentServiceObjects(
			_workflowInstanceResourceComponentServiceObjects);
		Query.setWorkflowLogResourceComponentServiceObjects(
			_workflowLogResourceComponentServiceObjects);
		Query.setWorkflowTaskResourceComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects);
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated
	public String getPath() {
		return "/headless-admin-workflow-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WorkflowInstanceResource>
		_workflowInstanceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WorkflowTaskResource>
		_workflowTaskResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CreatorResource>
		_creatorResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TransitionsResource>
		_transitionsResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WorkflowLogResource>
		_workflowLogResourceComponentServiceObjects;

}