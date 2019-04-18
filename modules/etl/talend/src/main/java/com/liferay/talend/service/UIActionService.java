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
import com.liferay.talend.dataset.InputDataSet;

import java.util.ArrayList;
import java.util.List;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.completion.SuggestionValues;
import org.talend.sdk.component.api.service.completion.Suggestions;
import org.talend.sdk.component.api.service.healthcheck.HealthCheck;
import org.talend.sdk.component.api.service.healthcheck.HealthCheckStatus;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 */
@Service
public class UIActionService {

	@HealthCheck(family = "Liferay", value = "checkGenericDataStore")
	public HealthCheckStatus checkGenericDataStore(
		@Option GenericDataStore genericDataStore) {

		return _dataStoreChecker.checkGenericDataStore(genericDataStore);
	}

	@Suggestions(family = "Liferay", value = "fetchEndpoints")
	public SuggestionValues fetchEndpoints(
		@Option("genericDataStore") final GenericDataStore genericDataStore) {

		InputDataSet inputDataSet = new InputDataSet();

		inputDataSet.setGenericDataStore(genericDataStore);

		List<String> endpoints = _liferayService.getPageableEndpoints(
			inputDataSet);

		List<SuggestionValues.Item> items = new ArrayList<>();

		endpoints.forEach(
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