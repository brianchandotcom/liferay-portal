/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.model.ClassedModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class ManifestSummary {

	public void addModelCount(
		Class<? extends ClassedModel> clazz, long modelCount) {

		addModelCount(clazz.getName(), modelCount);
	}

	public void addModelCount(String modelName, long modelCount) {
		_modelCounters.put(modelName, modelCount);
	}

	public long getModelCount(Class<? extends ClassedModel> clazz) {
		return getModelCount(clazz.getName());
	}

	public long getModelCount(String modelName) {
		if (!_modelCounters.containsKey(modelName)) {
			return -1;
		}

		return _modelCounters.get(modelName);
	}

	public Map<String, Long> getModelCounters() {
		return _modelCounters;
	}

	public void incrementModelCount(Class<? extends ClassedModel> clazz) {
		incrementModelCount(clazz.getName());
	}

	public void incrementModelCount(String modelName) {
		if (!_modelCounters.containsKey(modelName)) {
			_modelCounters.put(modelName, 1L);

			return;
		}

		long modelCounter = _modelCounters.get(modelName);

		_modelCounters.put(modelName, modelCounter + 1);
	}

	private Map<String, Long> _modelCounters = new HashMap<String, Long>();

}