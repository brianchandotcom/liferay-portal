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

package com.liferay.headless.builder.internal.operation;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carlos Correa
 */
@Component(service = OperationRegistry.class)
public class OperationRegistry {

	public List<Operation> getOperations() {
		return _serviceTrackerList.toList();
	}

	public void register(Operation operation) {
		Operation.PathConfiguration pathConfiguration =
			operation.getPathConfiguration();

		Pattern pattern = pathConfiguration.getPattern();

		for (Operation existingOperation : _serviceTrackerList) {
			Operation.PathConfiguration existingPathConfiguration =
				existingOperation.getPathConfiguration();

			Pattern existingPattern = existingPathConfiguration.getPattern();

			if (!Objects.equals(existingPattern.pattern(), pattern.pattern()) ||
				(operation.getCompanyId() !=
					existingOperation.getCompanyId())) {

				continue;
			}

			throw new IllegalStateException(
				StringBundler.concat(
					"There is already an operation for the company ",
					operation.getCompanyId(), " and the pattern ",
					existingPattern));
		}

		_serviceRegistrations.put(
			operation.getKey(),
			_bundleContext.registerService(
				Operation.class, operation,
				HashMapDictionaryBuilder.<String, Object>put(
					"companyId", operation::getCompanyId
				).put(
					"operation.key", operation.getKey()
				).build()));
	}

	public void unregister(Operation operation) {
		ServiceRegistration<Operation> serviceRegistration =
			_serviceRegistrations.remove(operation.getKey());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, Operation.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private BundleContext _bundleContext;
	private final Map<String, ServiceRegistration<Operation>>
		_serviceRegistrations = new HashMap<>();
	private ServiceTrackerList<Operation> _serviceTrackerList;

}