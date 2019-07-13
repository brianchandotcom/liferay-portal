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

package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import java.util.Map;

/**
 * @author Rodolfo Roza Miranda
 */
public class BrowserModuleNameMapperCache {

	public BrowserModuleNameMapperCache(
		Map<String, String> exactMatchMap, Map<String, String> partialMatchMap,
		long modifiedCount) {

		_exactMatchMap = exactMatchMap;
		_partialMatchMap = partialMatchMap;
		_modifiedCount = modifiedCount;
	}

	public Map<String, String> getExactMatchMap() {
		return _exactMatchMap;
	}

	public Map<String, String> getPartialMatchMap() {
		return _partialMatchMap;
	}

	public boolean isOlderThan(long modifiedCount) {
		if (_modifiedCount < modifiedCount) {
			return true;
		}

		return false;
	}

	private final Map<String, String> _exactMatchMap;
	private final long _modifiedCount;
	private final Map<String, String> _partialMatchMap;

}