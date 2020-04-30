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

package com.liferay.depot.web.internal.workflow;

import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerWrapper;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = DepotWorkflowHandlerRegistry.class)
public class DepotWorkflowHandlerRegistry {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			"(&(objectClass=com.liferay.portal.kernel.workflow." +
				"WorkflowHandler)(!(depot.workflow.handler.wrapper=*)))",
			new DepotWorkflowHandlerServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference
	private DepotApplicationController _depotApplicationController;

	private ServiceTracker<WorkflowHandler<?>, ServiceRegistration<?>>
		_serviceTracker;

	private final class DepotWorkflowHandlerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<WorkflowHandler<?>, ServiceRegistration<?>> {

		public DepotWorkflowHandlerServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ServiceRegistration<?> addingService(
			ServiceReference<WorkflowHandler<?>> serviceReference) {

			WorkflowHandler<?> workflowHandler = _bundleContext.getService(
				serviceReference);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			for (String key : serviceReference.getPropertyKeys()) {
				if (!key.equals(Constants.SERVICE_RANKING)) {
					properties.put(key, serviceReference.getProperty(key));
				}
			}

			int serviceRanking = GetterUtil.getInteger(
				serviceReference.getProperty(Constants.SERVICE_RANKING));

			properties.put(Constants.SERVICE_RANKING, serviceRanking + 1);

			properties.put("depot.workflow.handler.wrapper", Boolean.TRUE);

			return _bundleContext.registerService(
				WorkflowHandler.class,
				new DepotWorkflowHandlerWrapper<>(
					serviceReference, workflowHandler),
				properties);
		}

		@Override
		public void modifiedService(
			ServiceReference<WorkflowHandler<?>> serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			removedService(serviceReference, serviceRegistration);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<WorkflowHandler<?>> serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			WorkflowHandler<?> workflowHandler = _bundleContext.getService(
				serviceReference);

			try {
				if (workflowHandler instanceof DepotWorkflowHandlerWrapper) {
					DepotWorkflowHandlerWrapper<?> depotWorkflowHandlerWrapper =
						(DepotWorkflowHandlerWrapper<?>)workflowHandler;

					_bundleContext.ungetService(
						depotWorkflowHandlerWrapper.getServiceReference());
				}
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}

			serviceRegistration.unregister();
		}

		private final BundleContext _bundleContext;

	}

	private final class DepotWorkflowHandlerWrapper<T>
		extends WorkflowHandlerWrapper<T> {

		public DepotWorkflowHandlerWrapper(
			ServiceReference<?> serviceReference,
			WorkflowHandler<T> workflowHandler) {

			super(workflowHandler);

			_serviceReference = serviceReference;
		}

		public ServiceReference<?> getServiceReference() {
			return _serviceReference;
		}

		@Override
		public boolean isVisible(Group group) {
			if ((group.getType() != GroupConstants.TYPE_DEPOT) ||
				_depotApplicationController.isClassNameEnabled(
					getClassName(), group.getGroupId())) {

				return super.isVisible(group);
			}

			return false;
		}

		private final ServiceReference<?> _serviceReference;

	}

}