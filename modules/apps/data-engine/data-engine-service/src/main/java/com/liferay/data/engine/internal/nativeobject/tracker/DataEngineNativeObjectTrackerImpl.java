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

package com.liferay.data.engine.internal.nativeobject.tracker;

import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.nativeobject.tracker.DataEngineNativeObjectTracker;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DataEngineNativeObjectTracker.class)
public class DataEngineNativeObjectTrackerImpl
	implements DataEngineNativeObjectTracker {

	@Override
	public DataEngineNativeObject getDataEngineNativeObject(String className) {
		return _dataEngineNativeObjects.get(className);
	}

	@Override
	public Collection<DataEngineNativeObject> getDataEngineNativeObjects() {
		return _dataEngineNativeObjects.values();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataEngineNativeObject(
			DataEngineNativeObject dataEngineNativeObject)
		throws Exception {

		_dataEngineNativeObjects.put(
			dataEngineNativeObject.getClassName(), dataEngineNativeObject);
	}

	protected void removeDataEngineNativeObject(
		DataEngineNativeObject dataEngineNativeObject) {

		_dataEngineNativeObjects.remove(dataEngineNativeObject.getClassName());
	}

	private final Map<String, DataEngineNativeObject> _dataEngineNativeObjects =
		new ConcurrentHashMap<>();

}