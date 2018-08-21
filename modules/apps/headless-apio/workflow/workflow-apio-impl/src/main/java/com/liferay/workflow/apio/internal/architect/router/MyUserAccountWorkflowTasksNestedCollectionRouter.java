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

package com.liferay.workflow.apio.internal.architect.router;

import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.person.apio.architect.identifier.MyUserAccountIdentifier;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.workflow.apio.architect.identifier.WorkflowTaskIdentifier;
import com.liferay.workflow.apio.internal.architect.router.base.BaseUserAccountWorkflowTasksNestedCollectionRouter;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the information necessary to expose user WorkflowTask resources
 * through a web API. The resources are mapped from the internal model {@link
 * WorkflowTask}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class MyUserAccountWorkflowTasksNestedCollectionRouter extends
	BaseUserAccountWorkflowTasksNestedCollectionRouter<MyUserAccountIdentifier>
	implements NestedCollectionRouter
		<WorkflowTask, Long, WorkflowTaskIdentifier, Long,
			MyUserAccountIdentifier> {
}