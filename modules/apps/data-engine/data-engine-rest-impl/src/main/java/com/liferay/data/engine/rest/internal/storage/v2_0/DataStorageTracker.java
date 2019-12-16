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

package com.liferay.data.engine.rest.internal.storage.v2_0;

import com.liferay.data.engine.storage.DataStorage;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DataStorageTracker.class)
public class DataStorageTracker {

	public DataStorage getDataStorage(String dataStorageType) {
		return _dataStorages.get(dataStorageType);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataStorage(
		DataStorage dataStorage, Map<String, Object> properties) {

		String dataStorageType = MapUtil.getString(
			properties, "data.storage.type");

		_dataStorages.put(dataStorageType, dataStorage);
	}

	@Deactivate
	protected void deactivate() {
		_dataStorages.clear();
	}

	protected void removeDataStorage(
		DataStorage dataStorage, Map<String, Object> properties) {

		String dataStorageType = MapUtil.getString(
			properties, "data.storage.type");

		_dataStorages.remove(dataStorageType);
	}

	private final Map<String, DataStorage> _dataStorages = new TreeMap<>();

}