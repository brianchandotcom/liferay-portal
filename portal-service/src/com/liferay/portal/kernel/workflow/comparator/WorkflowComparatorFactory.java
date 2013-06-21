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

package com.liferay.portal.kernel.workflow.comparator;

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Michael C. Han
 */
@MessagingProxy(mode = ProxyMode.SYNC)
public interface WorkflowComparatorFactory {

	public OrderByComparator getDefinitionNameComparator(boolean ascending);

	public OrderByComparator getInstanceEndDateComparator(boolean ascending);

	public OrderByComparator getInstanceStartDateComparator(boolean ascending);

	public OrderByComparator getInstanceStateComparator(boolean ascending);

	public OrderByComparator getLogCreateDateComparator(boolean ascending);

	public OrderByComparator getLogUserIdComparator(boolean ascending);

	public OrderByComparator getTaskCompletionDateComparator(boolean ascending);

	public OrderByComparator getTaskCreateDateComparator(boolean ascending);

	public OrderByComparator getTaskDueDateComparator(boolean ascending);

	public OrderByComparator getTaskNameComparator(boolean ascending);

	public OrderByComparator getTaskUserIdComparator(boolean ascending);

}