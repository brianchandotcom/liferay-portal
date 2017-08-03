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

import com.liferay.vulcan.converter.Converter;

import java.util.Optional;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides methods to convert String identifiers to custom types.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = ConverterManager.class)
public class ConverterManager extends BaseManager<Converter> {

	/**
	 * Converts a string identifier to its equivalent of type T if a valid
	 * {@link Converter} can be found. Returns <code>Optional#empty()</code>
	 * otherwise.
	 *
	 * @param  clazz the type class to be converted to.
	 * @param  id the string identifier.
	 * @return the converted identifier, if a valid {@link Converter} is
	 *         present; <code>Optional#empty()</code> otherwise.
	 */
	public <T> Optional<T> convert(Class<T> clazz, String id) {
		Optional<Converter> optional = getServiceOptional(clazz);

		return optional.map(
			converter -> (Converter<T>)converter
		).map(
			converter -> converter.convert(id)
		);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<Converter> serviceReference) {

		addService(serviceReference, Converter.class);
	}

	protected void unsetServiceReference(
		ServiceReference<Converter> serviceReference) {

		removeService(serviceReference, Converter.class);
	}

}