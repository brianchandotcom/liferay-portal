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

package com.liferay.batch.planner.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Igor Beslic
 */
public class SelectHeadlessEndpointDisplayContext {

	public SelectHeadlessEndpointDisplayContext(
		Map<String, String> headlessEndpoints) {

		Objects.requireNonNull(headlessEndpoints);

		_headlessEndpoints = Collections.unmodifiableMap(headlessEndpoints);
	}

	public Map<String, String> getHeadlessEndpoints() {
		return _headlessEndpoints;
	}

	public List<SelectOption> getHeadlessEndpointSelectOptions() {
		Set<Map.Entry<String, String>> entries = _headlessEndpoints.entrySet();

		Stream<Map.Entry<String, String>> stream = entries.stream();

		return stream.map(
			stringStringEntry -> new SelectOption(
				stringStringEntry.getKey(), stringStringEntry.getValue())
		).collect(
			Collectors.toList()
		);
	}

	private final Map<String, String> _headlessEndpoints;

}