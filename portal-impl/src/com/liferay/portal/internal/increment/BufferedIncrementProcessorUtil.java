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

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Preston Crary
 */
public class BufferedIncrementProcessorUtil {

	public static BufferedIncrementProcessor getBufferedIncrementProcessor(
		String configuration) {

		Map.Entry<BufferedIncrementConfiguration, BufferedIncrementProcessor>
			entry = _bufferedIncrementProcessors.computeIfAbsent(
				configuration,
				key -> {
					BufferedIncrementConfiguration
						bufferedIncrementConfiguration =
							new BufferedIncrementConfiguration(key);

					BufferedIncrementProcessor bufferedIncrementProcessor =
						null;

					if (bufferedIncrementConfiguration.isEnabled()) {
						bufferedIncrementProcessor =
							new BufferedIncrementProcessor(
								bufferedIncrementConfiguration, key);
					}

					return new AbstractMap.SimpleImmutableEntry<>(
						bufferedIncrementConfiguration,
						bufferedIncrementProcessor);
				});

		return entry.getValue();
	}

	public void destroy() {
		for (Map.Entry<?, BufferedIncrementProcessor> entry :
				_bufferedIncrementProcessors.values()) {

			BufferedIncrementProcessor bufferedIncrementProcessor =
				entry.getValue();

			if (bufferedIncrementProcessor != null) {
				bufferedIncrementProcessor.destroy();
			}
		}
	}

	private static final Map
		<String,
		 Map.Entry<BufferedIncrementConfiguration, BufferedIncrementProcessor>>
			_bufferedIncrementProcessors = new ConcurrentHashMap<>();

}