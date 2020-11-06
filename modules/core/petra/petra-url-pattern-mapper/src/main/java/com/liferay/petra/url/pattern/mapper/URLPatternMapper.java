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

package com.liferay.petra.url.pattern.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public interface URLPatternMapper<T> {

	public void consumeValues(Consumer<T> consumer, String urlPath);

	public T getValue(String urlPath);

	public default Set<T> getValues(String urlPath) {
		Set<T> values = new HashSet<>(Long.SIZE);

		consumeValues(values::add, urlPath);

		return values;
	}

}