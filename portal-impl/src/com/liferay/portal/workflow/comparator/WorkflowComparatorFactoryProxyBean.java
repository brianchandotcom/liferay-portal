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

package com.liferay.portal.workflow.comparator;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;

/**
 * @author Shuyang Zhou
 */
@OSGiBeanProperties(
	property = "proxy.bean=true", service = WorkflowComparatorFactory.class
)
public class WorkflowComparatorFactoryProxyBean
	extends BaseProxyBean implements WorkflowComparatorFactory {

	@Override
	public OrderByComparator<WorkflowDefinition> getDefinitionNameComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowInstance> getInstanceCompletedComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowInstance> getInstanceEndDateComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowInstance> getInstanceStartDateComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowInstance> getInstanceStateComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowLog> getLogCreateDateComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowLog> getLogUserIdComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskCompletionDateComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskCreateDateComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskDueDateComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskModifiedDateComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskNameComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskUserIdComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

}