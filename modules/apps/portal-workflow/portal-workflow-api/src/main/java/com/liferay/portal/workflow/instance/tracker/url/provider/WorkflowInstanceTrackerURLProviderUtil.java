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

package com.liferay.portal.workflow.instance.tracker.url.provider;

import com.liferay.osgi.util.ServiceTrackerFactory;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Feliphe Marinho
 */
public class WorkflowInstanceTrackerURLProviderUtil {

	public static String getURL(
			Object bean, HttpServletRequest httpServletRequest,
			Class<?> modelClass, boolean useDialog)
		throws Exception {

		WorkflowInstanceTrackerURLProvider workflowInstanceTrackerURLProvider =
			_serviceTracker.getService();

		return workflowInstanceTrackerURLProvider.getURL(
			bean, httpServletRequest, modelClass, useDialog);
	}

	private static final ServiceTracker
		<WorkflowInstanceTrackerURLProvider, WorkflowInstanceTrackerURLProvider>
			_serviceTracker = ServiceTrackerFactory.open(
				FrameworkUtil.getBundle(
					WorkflowInstanceTrackerURLProviderUtil.class),
				WorkflowInstanceTrackerURLProvider.class);

}