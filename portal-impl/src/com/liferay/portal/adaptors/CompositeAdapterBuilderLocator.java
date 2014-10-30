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

package com.liferay.portal.adaptors;

import com.liferay.portal.kernel.adaptors.AdapterBuilder;
import com.liferay.portal.kernel.adaptors.AdapterBuilderLocator;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.java8.util.Optional;

import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class CompositeAdapterBuilderLocator implements AdapterBuilderLocator {

	@Override
	public <R, T> Optional<AdapterBuilder<R, T>> locate(Class<R> from, Class<T> to) {
		for (AdapterBuilderLocator adapterBuilderLocator : _adapterBuilderLocators) {
			Optional<AdapterBuilder<R, T>> maybeAdaptor = adapterBuilderLocator.locate(
				from, to);

			if (maybeAdaptor.isPresent()) {
				return maybeAdaptor;
			}
		}

		return Optional.empty();
	}

	public void register(AdapterBuilderLocator adapterBuilderLocator) {
		_adapterBuilderLocators.add(adapterBuilderLocator);
	}

	private List<AdapterBuilderLocator> _adapterBuilderLocators =
		ServiceTrackerCollections.list(AdapterBuilderLocator.class);

}
