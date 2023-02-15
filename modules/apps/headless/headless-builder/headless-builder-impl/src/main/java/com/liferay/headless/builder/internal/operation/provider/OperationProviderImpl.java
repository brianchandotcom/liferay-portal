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

package com.liferay.headless.builder.internal.operation.provider;

import com.liferay.headless.builder.exception.NoSuchOperationException;
import com.liferay.headless.builder.operation.Method;
import com.liferay.headless.builder.operation.Operation;
import com.liferay.headless.builder.operation.PathConfiguration;
import com.liferay.headless.builder.operation.provider.OperationProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.UnsafeTriFunction;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carlos Correa
 */
@Component(service = OperationProvider.class)
public class OperationProviderImpl implements OperationProvider {

	@Override
	public Operation getOperation(long companyId, Method method, String path)
		throws NoSuchOperationException {

		for (UnsafeTriFunction
				<Long, Method, String, Boolean, NoSuchOperationException>
					unsafeTriFunction : _operationServiceTrackerMap.keySet()) {

			if (unsafeTriFunction.apply(companyId, method, path)) {
				return _operationServiceTrackerMap.getService(
					unsafeTriFunction);
			}
		}

		throw new NoSuchOperationException();
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_operationServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, Operation.class, null,
				(serviceReference, emitter) -> {
					Operation operation = bundleContext.getService(
						serviceReference);

					PathConfiguration pathConfiguration =
						operation.getPathConfiguration();

					emitter.emit(
						(companyId, method, path) -> {
							if (companyId != operation.getCompanyId()) {
								return false;
							}

							Method operationMethod = operation.getMethod();

							if (!Objects.equals(operationMethod, method)) {
								return false;
							}

							Pattern pattern = pathConfiguration.getPattern();

							Matcher matcher = pattern.matcher(path);

							if (matcher.matches()) {
								return true;
							}

							return false;
						});
				});
	}

	@Deactivate
	protected void deactivate() {
		_operationServiceTrackerMap.close();
	}

	private ServiceTrackerMap
		<UnsafeTriFunction
			<Long, Method, String, Boolean, NoSuchOperationException>,
		 Operation> _operationServiceTrackerMap;

}