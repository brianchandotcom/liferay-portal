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
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Igor Beslic
 * @author Matija Petanjek
 */
public class EditBatchPlannerPlanDisplayContext {

	public EditBatchPlannerPlanDisplayContext(
			List<BatchPlannerPlan> batchPlannerPlans,
			Map<String, String> headlessEndpoints,
			BatchPlannerPlan selectedBatchPlannerPlan)
		throws PortalException {

		_headlessEndpoints = Collections.unmodifiableMap(headlessEndpoints);

		if (selectedBatchPlannerPlan == null) {
			_selectedBatchPlannerMappings = new HashMap<>();
			_selectedBatchPlannerPlanId = 0;
			_selectedBatchPlannerPlanName = StringPool.BLANK;
			_selectedExternalType = StringPool.BLANK;
			_selectedHeadlessEndpoint = StringPool.BLANK;
			_selectedInternalClassName = StringPool.BLANK;
		}
		else {
			_selectedBatchPlannerMappings = _getSelectedBatchPlannerMappings(
				selectedBatchPlannerPlan);
			_selectedBatchPlannerPlanId =
				selectedBatchPlannerPlan.getBatchPlannerPlanId();
			_selectedBatchPlannerPlanName = selectedBatchPlannerPlan.getName();
			_selectedExternalType = selectedBatchPlannerPlan.getExternalType();
			_selectedHeadlessEndpoint = _getSelectedHeadlessEndpoint(
				selectedBatchPlannerPlan);
			_selectedInternalClassName =
				selectedBatchPlannerPlan.getInternalClassName();
		}

		_templateSelectOptions = _getTemplateSelectOptions(batchPlannerPlans);
	}

	public List<SelectOption> getExternalTypeSelectOptions() {
		List<SelectOption> selectOptions = new ArrayList<>();

		for (BatchEngineTaskContentType batchEngineTaskContentType :
				BatchEngineTaskContentType.values()) {

			if ((batchEngineTaskContentType ==
					BatchEngineTaskContentType.XLS) ||
				(batchEngineTaskContentType ==
					BatchEngineTaskContentType.XLSX)) {

				continue;
			}

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

	public long getSelectedBatchPlannerPlanId() {
		return _selectedBatchPlannerPlanId;
	}

	public Map<String, String> getSelectedBatchPlannerPlanMappings() {
		return _selectedBatchPlannerMappings;
	}

	public String getSelectedBatchPlannerPlanName() {
		return _selectedBatchPlannerPlanName;
	}

	public String getSelectedExternalType() {
		return _selectedExternalType;
	}

	public String getSelectedHeadlessEndpoint() {
		return _selectedHeadlessEndpoint;
	}

	public String getSelectedInternalClassName() {
		return _selectedInternalClassName;
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

	public List<SelectOption> getTemplateSelectOptions() {
		return _templateSelectOptions;
	}

	private Map<String, String> _getSelectedBatchPlannerMappings(
		BatchPlannerPlan selectedBatchPlannerPlan) {

		Map<String, String> selectedBatchPlannerMappings = new HashMap<>();

		for (BatchPlannerMapping batchPlannerMapping :
				selectedBatchPlannerPlan.getBatchPlannerMappings()) {

			selectedBatchPlannerMappings.put(
				batchPlannerMapping.getInternalFieldName(),
				batchPlannerMapping.getExternalFieldName());
		}

		return selectedBatchPlannerMappings;
	}

	private String _getSelectedHeadlessEndpoint(
			BatchPlannerPlan batchPlannerPlan)
		throws PortalException {

		BatchPlannerPolicy batchPlannerPolicy =
			batchPlannerPlan.getBatchPlannerPolicy("headlessEndpoint");

		return batchPlannerPolicy.getValue();
	}

	private List<SelectOption> _getTemplateSelectOptions(
		List<BatchPlannerPlan> batchPlannerPlans) {

		List<SelectOption> templateSelectOptions = new ArrayList<>();

		for (BatchPlannerPlan batchPlannerPlan : batchPlannerPlans) {
			boolean selected = false;

			if (batchPlannerPlan.getBatchPlannerPlanId() ==
					_selectedBatchPlannerPlanId) {

				selected = true;
			}

			templateSelectOptions.add(
				new SelectOption(
					batchPlannerPlan.getName(),
					String.valueOf(batchPlannerPlan.getBatchPlannerPlanId()),
					selected));
		}

		return templateSelectOptions;
	}

	private final Map<String, String> _headlessEndpoints;
	private final Map<String, String> _selectedBatchPlannerMappings;
	private final long _selectedBatchPlannerPlanId;
	private final String _selectedBatchPlannerPlanName;
	private final String _selectedExternalType;
	private final String _selectedHeadlessEndpoint;
	private final String _selectedInternalClassName;
	private final List<SelectOption> _templateSelectOptions;

}