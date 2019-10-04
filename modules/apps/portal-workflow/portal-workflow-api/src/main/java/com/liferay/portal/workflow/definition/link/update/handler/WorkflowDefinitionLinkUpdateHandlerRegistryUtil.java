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

package com.liferay.portal.workflow.definition.link.update.handler;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Máté Thurzó
 */
public class WorkflowDefinitionLinkUpdateHandlerRegistryUtil {

	public static WorkflowDefinitionLinkUpdateHandler
		getWorkflowDefinitionLinkUpdateHandler(String modelClassName) {

		return _workflowDefinitionLinkUpdateHandlerRegistryUtil.
			_getWorkflowDefinitionLinkUpdateHandler(modelClassName);
	}

	private WorkflowDefinitionLinkUpdateHandlerRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowDefinitionLinkUpdateHandlerRegistryUtil.class);

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext, WorkflowDefinitionLinkUpdateHandler.class,
			new ExportImportContentProcessorServiceTrackerCustomizer());
	}

	private WorkflowDefinitionLinkUpdateHandler
		_getWorkflowDefinitionLinkUpdateHandler(String modelClassName) {

		return _workflowDefinitionLinkUpdateHandlers.get(modelClassName);
	}

	private static final WorkflowDefinitionLinkUpdateHandlerRegistryUtil
		_workflowDefinitionLinkUpdateHandlerRegistryUtil =
			new WorkflowDefinitionLinkUpdateHandlerRegistryUtil();

	private final BundleContext _bundleContext;
	private final ServiceTracker
		<WorkflowDefinitionLinkUpdateHandler,
		 WorkflowDefinitionLinkUpdateHandler> _serviceTracker;
	private final Map<String, WorkflowDefinitionLinkUpdateHandler>
		_workflowDefinitionLinkUpdateHandlers = new ConcurrentHashMap<>();

	private class ExportImportContentProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<WorkflowDefinitionLinkUpdateHandler,
			 WorkflowDefinitionLinkUpdateHandler> {

		@Override
		public WorkflowDefinitionLinkUpdateHandler addingService(
			ServiceReference<WorkflowDefinitionLinkUpdateHandler>
				serviceReference) {

			WorkflowDefinitionLinkUpdateHandler
				workflowDefinitionLinkUpdateHandler = _bundleContext.getService(
					serviceReference);

			String modelClassName = GetterUtil.getString(
				serviceReference.getProperty("model.class.name"));

			_workflowDefinitionLinkUpdateHandlers.put(
				modelClassName, workflowDefinitionLinkUpdateHandler);

			return workflowDefinitionLinkUpdateHandler;
		}

		@Override
		public void modifiedService(
			ServiceReference<WorkflowDefinitionLinkUpdateHandler>
				serviceReference,
			WorkflowDefinitionLinkUpdateHandler
				workflowDefinitionLinkUpdateHandler) {

			removedService(
				serviceReference, workflowDefinitionLinkUpdateHandler);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<WorkflowDefinitionLinkUpdateHandler>
				serviceReference,
			WorkflowDefinitionLinkUpdateHandler
				workflowDefinitionLinkUpdateHandler) {

			_bundleContext.ungetService(serviceReference);

			String modelClassName = GetterUtil.getString(
				serviceReference.getProperty("model.class.name"));

			_workflowDefinitionLinkUpdateHandlers.remove(modelClassName);
		}

	}

}