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

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Igor Beslic
 */
public class EditBatchPlannerPlanDisplayContext {

	public EditBatchPlannerPlanDisplayContext(
		Map<String, String> headlessEndpoints) {

		_headlessEndpoints = Collections.unmodifiableMap(headlessEndpoints);
	}

	public List<SelectOption> getExternalTypeSelectOptions() {
		List<SelectOption> selectOptions = new ArrayList<>();

		for (BatchEngineTaskContentType batchEngineTaskContentType :
				BatchEngineTaskContentType.values()) {

			selectOptions.add(
				new SelectOption(
					batchEngineTaskContentType.toString(),
					batchEngineTaskContentType.toString()));
		}

		return selectOptions;
	}

	public Map<String, String> getHeadlessEndpoints() {
		return _headlessEndpoints;
	}

	public List<SelectOption> getSelectOptions() {
		Set<Map.Entry<String, String>> entries = _headlessEndpoints.entrySet();

		Stream<Map.Entry<String, String>> stream = entries.stream();

		List<SelectOption> selectOptions = new ArrayList<>();

		selectOptions.add(new SelectOption(StringPool.BLANK, StringPool.BLANK));

		selectOptions.addAll(
			stream.map(
				entry -> new SelectOption(entry.getKey(), entry.getValue())
			).collect(
				Collectors.toList()
			));

		return selectOptions;
	}

	private final Map<String, String> _headlessEndpoints;

}