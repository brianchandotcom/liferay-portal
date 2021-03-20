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

package com.liferay.portal.workflow.kaleo.runtime.internal.assignment;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelectorRegistry;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelectorRegistry;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Leonardo Barros
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *              KaleoTaskAssignmentSelectorTracker}
 */
@Component(immediate = true, service = TaskAssignmentSelectorRegistry.class)
@Deprecated
public class TaskAssignmentSelectorTracker
	implements TaskAssignmentSelectorRegistry {

	@Override
	public TaskAssignmentSelector getTaskAssignmentSelector(
		String assigneeClassName) {

		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector =
			_kaleoTaskAssignmentSelectorRegistry.getKaleoTaskAssignmentSelector(
				assigneeClassName);

		if (kaleoTaskAssignmentSelector instanceof
				TaskAssignmentSelectorWrapper) {

			TaskAssignmentSelectorWrapper taskAssignmentSelectorWrapper =
				(TaskAssignmentSelectorWrapper)kaleoTaskAssignmentSelector;

			return taskAssignmentSelectorWrapper._taskAssignmentSelector;
		}

		return new TaskAssignmentSelector() {

			@Override
			public Collection<KaleoTaskAssignment> calculateTaskAssignments(
					KaleoTaskAssignment kaleoTaskAssignment,
					ExecutionContext executionContext)
				throws PortalException {

				return kaleoTaskAssignmentSelector.getKaleoTaskAssignments(
					kaleoTaskAssignment, executionContext);
			}

		};
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			"(&(objectClass=com.liferay.portal.workflow.kaleo.runtime." +
				"assignment.TaskAssignmentSelector)(assignee.class.name=*))",
			new TaskAssignmentSelectorServiceTrackerCustomizer(bundleContext));
	}

	@Reference
	private KaleoTaskAssignmentSelectorRegistry
		_kaleoTaskAssignmentSelectorRegistry;

	private final Map<String, ServiceRegistration<?>> _serviceReferenceMap =
		new ConcurrentHashMap<>();
	private ServiceTracker<TaskAssignmentSelector, ServiceRegistration<?>>
		_serviceTracker;

	private final class TaskAssignmentSelectorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<TaskAssignmentSelector, ServiceRegistration<?>> {

		public TaskAssignmentSelectorServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ServiceRegistration<?> addingService(
			ServiceReference<TaskAssignmentSelector> serviceReference) {

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put(
				"assignee.class.name",
				serviceReference.getProperty("assignee.class.name"));

			TaskAssignmentSelector taskAssignmentSelector =
				_bundleContext.getService(serviceReference);

			ServiceRegistration<KaleoTaskAssignmentSelector>
				serviceRegistration = _bundleContext.registerService(
					KaleoTaskAssignmentSelector.class,
					new TaskAssignmentSelectorWrapper(taskAssignmentSelector),
					properties);

			Class<?> taskAssignmentSelectorClass =
				taskAssignmentSelector.getClass();

			_serviceReferenceMap.put(
				taskAssignmentSelectorClass.getName(), serviceRegistration);

			return serviceRegistration;
		}

		@Override
		public void modifiedService(
			ServiceReference<TaskAssignmentSelector> serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			removedService(serviceReference, serviceRegistration);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<TaskAssignmentSelector> serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			TaskAssignmentSelector taskAssignmentSelector =
				_bundleContext.getService(serviceReference);

			Class<? extends TaskAssignmentSelector>
				taskAssignmentSelectorClass = taskAssignmentSelector.getClass();

			try {
				if (_serviceReferenceMap.containsKey(
						taskAssignmentSelectorClass.getName())) {

					ServiceRegistration<?>
						kaleoTaskAssignmentSelectorServiceRegistration =
							_serviceReferenceMap.remove(
								taskAssignmentSelectorClass.getName());

					_bundleContext.ungetService(
						kaleoTaskAssignmentSelectorServiceRegistration.
							getReference());

					kaleoTaskAssignmentSelectorServiceRegistration.unregister();
				}
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

		private final BundleContext _bundleContext;

	}

	private final class TaskAssignmentSelectorWrapper
		implements KaleoTaskAssignmentSelector {

		@Override
		public Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
				KaleoTaskAssignment kaleoTaskAssignment,
				ExecutionContext executionContext)
			throws PortalException {

			return _taskAssignmentSelector.calculateTaskAssignments(
				kaleoTaskAssignment, executionContext);
		}

		private TaskAssignmentSelectorWrapper(
			TaskAssignmentSelector taskAssignmentSelector) {

			_taskAssignmentSelector = taskAssignmentSelector;
		}

		private final TaskAssignmentSelector _taskAssignmentSelector;

	}

}