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

package com.liferay.vulcan.wiring.osgi.manager;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.filter.FilterProvider;
import com.liferay.vulcan.filter.QueryParamFilterType;

import java.lang.reflect.Type;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides methods to provide instances of {@link QueryParamFilterType} that
 * have a valid {@link FilterProvider}.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = FilterProviderManager.class)
public class FilterProviderManager extends BaseManager<FilterProvider> {

	/**
	 * Returns the filter name of a {@link QueryParamFilterType} if a {@link
	 * FilterProvider} for it exists. Return <code>Optional#empty()</code>
	 * otherwise.
	 *
	 * @param  queryParamFilterType an instance of a filter
	 * @return the name of the filter, if a valid {@link FilterProvider} is
	 *         present; <code>Optional#empty()</code> otherwise.
	 */
	public Optional<String> getFilterNameOptional(
		QueryParamFilterType queryParamFilterType) {

		Optional<FilterProvider<QueryParamFilterType>> optional =
			getFilterProviderOptional(queryParamFilterType);

		return optional.map(FilterProvider::getFilterName);
	}

	/**
	 * Returns the {@link FilterProvider} of a filter class. Returns
	 * <code>Optional#empty()</code> if the {@link FilterProvider} isn't
	 * present.
	 *
	 * @param  clazz the filter class.
	 * @return the {@link FilterProvider}, if present;
	 *         <code>Optional#empty()</code> otherwise.
	 */
	public <T extends QueryParamFilterType> Optional<FilterProvider<T>>
		getFilterProviderOptional(Class<T> clazz) {

		Optional<FilterProvider> optional = getServiceOptional(clazz);

		return optional.map(service -> (FilterProvider<T>)service);
	}

	/**
	 * Returns the {@link FilterProvider} of a filter. Returns
	 * <code>Optional#empty()</code> if the {@link FilterProvider} isn't
	 * present.
	 *
	 * @param  queryParamFilterType the filter instance.
	 * @return the {@link FilterProvider}, if present;
	 *         <code>Optional#empty()</code> otherwise.
	 */
	public <Q extends QueryParamFilterType> Optional<FilterProvider<Q>>
		getFilterProviderOptional(Q queryParamFilterType) {

		Class<Q> filterClass = (Class<Q>)queryParamFilterType.getClass();

		Optional<FilterProvider<Q>> filterProviderOptional =
			getFilterProviderOptional(filterClass);

		if (filterProviderOptional.isPresent()) {
			return filterProviderOptional;
		}

		Type[] genericInterfaces = filterClass.getGenericInterfaces();

		Stream<Type> stream = Arrays.stream(genericInterfaces);

		return stream.findFirst(
		).flatMap(
			type -> {
				try {
					return Optional.of((Class<Q>)type);
				}
				catch (ClassCastException cce) {
					return Optional.empty();
				}
			}
		).flatMap(
			this::getFilterProviderOptional
		);
	}

	/**
	 * Returns a filter of type T if a valid {@link FilterProvider} can be
	 * found. Returns <code>Optional#empty()</code> otherwise.
	 *
	 * @param  clazz the filter class to be provided.
	 * @param  httpServletRequest the current request.
	 * @return the instance of T, if a valid {@link FilterProvider} is present;
	 *         <code>Optional#empty()</code> otherwise.
	 */
	public <T extends QueryParamFilterType> Optional<T> provide(
		Class<T> clazz, HttpServletRequest httpServletRequest) {

		Optional<FilterProvider<T>> optional = getFilterProviderOptional(clazz);

		return optional.map(provider -> provider.provide(httpServletRequest));
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<FilterProvider> serviceReference) {

		addService(serviceReference, FilterProvider.class);
	}

	protected void unsetServiceReference(
		ServiceReference<FilterProvider> serviceReference) {

		removeService(serviceReference, FilterProvider.class);
	}

}