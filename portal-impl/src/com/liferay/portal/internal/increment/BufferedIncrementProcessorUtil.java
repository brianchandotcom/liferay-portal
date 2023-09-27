/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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