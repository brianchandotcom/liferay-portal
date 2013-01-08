/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.postprocess;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class HitsPostProcessorRegistry {

	public static HitsPostProcessor getDefaultHitsPostProcessor() {
		return _defaultHitsPostProcessor;
	}

	public static HitsPostProcessor getHitsPostProcessor(String className) {
		HitsPostProcessor hitsPostProcessor = _entityHitsPostProcessorMap.get(
			className);

		if (hitsPostProcessor != null) {
			return hitsPostProcessor;
		}

		return _defaultHitsPostProcessor;
	}

	public void setEntityHitsPostProcessorMap(
		Map<String, HitsPostProcessor> entityHitsPostProcessorMap) {

		_entityHitsPostProcessorMap.putAll(entityHitsPostProcessorMap);
	}

	public void setGlobalHitsPostProcessor(
		HitsPostProcessor globalHitsPostProcessor) {

		_defaultHitsPostProcessor = globalHitsPostProcessor;
	}

	private static HitsPostProcessor _defaultHitsPostProcessor;

	private static Map<String, HitsPostProcessor> _entityHitsPostProcessorMap =
		new HashMap<String, HitsPostProcessor>();

}