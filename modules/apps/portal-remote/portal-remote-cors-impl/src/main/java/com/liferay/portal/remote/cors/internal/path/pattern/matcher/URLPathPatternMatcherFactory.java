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
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = URLPathPatternMatcherFactory.class)
public class URLPathPatternMatcherFactory {

	public <T> URLPathPatternMatcher<T> createPatternMatcher(
		Map<String, T> urlPathPatternsMap) {

		URLPathPatternMatcher<T> urlPathPatternMatcher;

		if (urlPathPatternsMap.size() > 64) {
			urlPathPatternMatcher = new DynamicURLPathPatternMatcher<>();
		}
		else {
			Set<String> urlPathPatterns = urlPathPatternsMap.keySet();

			Stream<String> stream = urlPathPatterns.stream();

			urlPathPatternMatcher = new StaticURLPathPatternMatcher<>(
				stream.map(
					String::length
				).max(
					Comparator.naturalOrder()
				).orElse(
					0
				));
		}

		for (Map.Entry<String, T> entry : urlPathPatternsMap.entrySet()) {
			urlPathPatternMatcher.putValue(entry.getKey(), entry.getValue());
		}

		return urlPathPatternMatcher;
	}

}