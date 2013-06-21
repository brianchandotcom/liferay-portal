/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;

/**
 * @author Shuyang Zhou
 */
public class WorkflowComparatorFactoryProxyBean
	extends BaseProxyBean implements WorkflowComparatorFactory {

	@Override
	public OrderByComparator getDefinitionNameComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getInstanceEndDateComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getInstanceStartDateComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getInstanceStateComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getLogCreateDateComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getLogUserIdComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getTaskCompletionDateComparator(
		boolean ascending) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getTaskCreateDateComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getTaskDueDateComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getTaskNameComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OrderByComparator getTaskUserIdComparator(boolean ascending) {
		throw new UnsupportedOperationException();
	}

}