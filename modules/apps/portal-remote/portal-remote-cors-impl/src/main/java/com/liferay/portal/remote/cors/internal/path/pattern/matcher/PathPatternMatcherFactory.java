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

package com.liferay.portal.remote.cors.internal.path.pattern.matcher;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = PathPatternMatcherFactory.class)
public class PathPatternMatcherFactory {

	public <T> PathPatternMatcher<T> createPatternMatcher(
		HashMap<String, T> patternsHeadersMap) {

		PathPatternMatcher<T> pathPatternMatcher;

		if (patternsHeadersMap.size() > 64) {
			pathPatternMatcher = new DynamicPathPatternMatcher<>();
		}
		else {
			Set<String> keySet = patternsHeadersMap.keySet();

			Stream<String> stream = keySet.stream();

			pathPatternMatcher = new StaticPathPatternMatcher<>(
				stream.map(
					String::length
				).max(
					Comparator.naturalOrder()
				).orElse(
					0
				));
		}

		for (Map.Entry<String, T> entry : patternsHeadersMap.entrySet()) {
			pathPatternMatcher.insert(entry.getKey(), entry.getValue());
		}

		return pathPatternMatcher;
	}

}