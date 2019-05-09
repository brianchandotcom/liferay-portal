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

package com.liferay.talend.service;

import com.liferay.talend.data.store.GenericDataStore;
import com.liferay.talend.data.store.OutputDataStore;
import com.liferay.talend.dataset.InputDataSet;
import com.liferay.talend.dataset.OutputDataSet;
import com.liferay.talend.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.record.Schema;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.asyncvalidation.AsyncValidation;
import org.talend.sdk.component.api.service.asyncvalidation.ValidationResult;
import org.talend.sdk.component.api.service.completion.SuggestionValues;
import org.talend.sdk.component.api.service.completion.Suggestions;
import org.talend.sdk.component.api.service.healthcheck.HealthCheck;
import org.talend.sdk.component.api.service.healthcheck.HealthCheckStatus;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;
import org.talend.sdk.component.api.service.schema.DiscoverSchema;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 * @author Matija Petanjek
 */
@Service
public class UIActionService {

	@HealthCheck(family = "Liferay", value = "checkGenericDataStore")
	public HealthCheckStatus checkGenericDataStore(
		@Option GenericDataStore genericDataStore) {

		return _dataStoreChecker.checkGenericDataStore(genericDataStore);
	}

	@Suggestions(family = "Liferay", value = "fetchInputEndpoints")
	public SuggestionValues fetchInputEndpoints(
		@Option("genericDataStore") final GenericDataStore genericDataStore) {

		InputDataSet inputDataSet = new InputDataSet();

		inputDataSet.setGenericDataStore(genericDataStore);

		return _toSuggestionValues(
			_liferayService.getReadableEndpoints(inputDataSet));
	}

	@Suggestions(family = "Liferay", value = "fetchOutputEndpoints")
	public SuggestionValues fetchOutputEndpoints(
		@Option("outputDataStore") final OutputDataStore outputDataStore) {

		OutputDataSet outputDataSet = new OutputDataSet();

		outputDataSet.setOutputDataStore(outputDataStore);

		return _toSuggestionValues(
			_liferayService.getUpdatableEndpoints(outputDataSet));
	}

	@DiscoverSchema(family = "Liferay", value = "guessInputSchema")
	public Schema guessInputSchema(
		final InputDataSet inputDataSet,
		final RecordBuilderFactory recordBuilderFactory) {

		return _liferayService.getEndpointTalendSchema(
			inputDataSet, recordBuilderFactory);
	}

	@AsyncValidation("validateEndpoint")
	public ValidationResult validateEndpoint(final String endpoint) {
		if (StringUtils.isNull(endpoint)) {
			return new ValidationResult(
				ValidationResult.Status.KO, "Please select an endpoint.");
		}

		return new ValidationResult(ValidationResult.Status.OK, null);
	}

	private SuggestionValues _toSuggestionValues(List<String> values) {
		List<SuggestionValues.Item> items = new ArrayList<>();

		values.forEach(
			path -> {
				items.add(new SuggestionValues.Item(path, path));
			});

		return new SuggestionValues(true, items);
	}

	@Service
	private DataStoreChecker _dataStoreChecker;

	@Service
	private LiferayService _liferayService;

}