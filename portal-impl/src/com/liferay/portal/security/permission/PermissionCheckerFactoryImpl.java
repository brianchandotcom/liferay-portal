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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerDecorator;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.contributor.RoleContributor;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PermissionCheckerFactoryImpl implements PermissionCheckerFactory {

	public PermissionCheckerFactoryImpl() throws Exception {
		Class<PermissionChecker> clazz =
			(Class<PermissionChecker>)Class.forName(
				PropsValues.PERMISSIONS_CHECKER);

		_permissionChecker = clazz.newInstance();

		_decoratedPermissionChecker = _permissionChecker;
	}

	public void afterPropertiesSet() {
		_roleContributors = ServiceTrackerCollections.openList(
			RoleContributor.class);

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PermissionCheckerDecorator.class,
			new PermissionCheckerDecoratorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public PermissionChecker create(User user) {
		PermissionChecker permissionChecker =
			_decoratedPermissionChecker.clone();

		permissionChecker.init(
			user, _roleContributors.toArray(new RoleContributor[0]));

		permissionChecker.setPermissionChecker(permissionChecker);

		return permissionChecker;
	}

	public void destroy() {
		_serviceTracker.close();

		_roleContributors.close();
	}

	public static class ServiceReferenceServiceTuple<T>
		implements Comparable<ServiceReferenceServiceTuple<T>> {

		public ServiceReferenceServiceTuple(
			ServiceReference<T> serviceReference, T service) {

			_serviceReference = serviceReference;
			_service = service;
		}

		@Override
		public int compareTo(
			ServiceReferenceServiceTuple<T> serviceReferenceServiceTuple) {

			return _serviceReference.compareTo(
				serviceReferenceServiceTuple.getServiceReference());
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof ServiceReferenceServiceTuple)) {
				return false;
			}

			ServiceReferenceServiceTuple<?> serviceReferenceServiceTuple =
				(ServiceReferenceServiceTuple<?>)object;

			return Objects.equals(
				_serviceReference,
				serviceReferenceServiceTuple._serviceReference);
		}

		public T getService() {
			return _service;
		}

		public ServiceReference<T> getServiceReference() {
			return _serviceReference;
		}

		@Override
		public int hashCode() {
			return Objects.hash(_serviceReference);
		}

		private final T _service;
		private final ServiceReference<T> _serviceReference;

	}

	private volatile PermissionChecker _decoratedPermissionChecker;
	private final PermissionChecker _permissionChecker;
	private ServiceTrackerList<RoleContributor> _roleContributors;
	private List<ServiceReferenceServiceTuple<PermissionCheckerDecorator>>
		_serviceReferenceServiceTuplesList = new ArrayList<>();
	private ServiceTracker<PermissionCheckerDecorator, ?> _serviceTracker;

	private class PermissionCheckerDecoratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PermissionCheckerDecorator,
			 ServiceReferenceServiceTuple<PermissionCheckerDecorator>> {

		@Override
		public synchronized ServiceReferenceServiceTuple
			<PermissionCheckerDecorator> addingService(
				ServiceReference<PermissionCheckerDecorator> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ServiceReferenceServiceTuple<PermissionCheckerDecorator>
				serviceReferenceServiceTuple =
					new ServiceReferenceServiceTuple<>(
						serviceReference,
						registry.getService(serviceReference));

			_serviceReferenceServiceTuplesList.add(
				serviceReferenceServiceTuple);

			_rebuild();

			return serviceReferenceServiceTuple;
		}

		@Override
		public synchronized void modifiedService(
			ServiceReference<PermissionCheckerDecorator> serviceReference,
			ServiceReferenceServiceTuple<PermissionCheckerDecorator> service) {

			_rebuild();
		}

		@Override
		public synchronized void removedService(
			ServiceReference<PermissionCheckerDecorator> serviceReference,
			ServiceReferenceServiceTuple<PermissionCheckerDecorator> service) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_serviceReferenceServiceTuplesList.remove(service);

			_rebuild();
		}

		private void _rebuild() {
			_serviceReferenceServiceTuplesList.sort(Comparator.naturalOrder());

			PermissionChecker permissionChecker = _permissionChecker;

			for (ServiceReferenceServiceTuple<PermissionCheckerDecorator>
					serviceReferenceServiceTuple :
						_serviceReferenceServiceTuplesList) {

				PermissionCheckerDecorator permissionCheckerDecorator =
					serviceReferenceServiceTuple.getService();

				permissionChecker = permissionCheckerDecorator.decorate(
					permissionChecker);
			}

			_decoratedPermissionChecker = permissionChecker;
		}

	}

}