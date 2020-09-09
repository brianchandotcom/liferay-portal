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

package com.liferay.portal.internal.increment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Preston Crary
 */
public class BufferedIncrementProcessorUtil {

	public static BufferedIncrementProcessor getBufferedIncrementProcessor(
		String configuration) {

		return _bufferedIncrementProcessors.computeIfAbsent(
			configuration,
			key -> {
				BufferedIncrementConfiguration bufferedIncrementConfiguration =
					new BufferedIncrementConfiguration(key);

				return new BufferedIncrementProcessor(
					bufferedIncrementConfiguration, key);
			});
	}

	public void destroy() {
		for (BufferedIncrementProcessor bufferedIncrementProcessor :
				_bufferedIncrementProcessors.values()) {

			bufferedIncrementProcessor.destroy();
		}
	}

	private static final Map<String, BufferedIncrementProcessor>
		_bufferedIncrementProcessors = new ConcurrentHashMap<>();

}