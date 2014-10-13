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

package com.liferay.portal.kernel.search;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class HitsProcessorRegistryUtil {

	public static HitsProcessor getDefaultHitsProcessor() {
		return _defaultHitsProcessor;
	}

	public static HitsProcessor getHitsProcessor(String className) {
		HitsProcessor hitsProcessor = _hitsProcessors.get(className);

		if (hitsProcessor != null) {
			return hitsProcessor;
		}

		return _defaultHitsProcessor;
	}

	public void setDefaultHitsProcessor(HitsProcessor hitsProcessor) {
		_defaultHitsProcessor = hitsProcessor;
	}

	public void setHitsProcessors(Map<String, HitsProcessor> hitsProcessors) {
		_hitsProcessors.putAll(hitsProcessors);
	}

	private static HitsProcessor _defaultHitsProcessor;
	private static final Map<String, HitsProcessor> _hitsProcessors =
		new HashMap<String, HitsProcessor>();

}