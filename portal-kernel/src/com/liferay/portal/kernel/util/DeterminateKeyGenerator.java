/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6872.
 * </p>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class DeterminateKeyGenerator {

	public static String generate(String input) {
		return generate(input, _DEFAULT_LENGTH);
	}

	public static String generate(String input, int length) {
		if (input == null) {
			throw new IllegalArgumentException("Input is null");
		}

		if (length <= 0) {
			throw new IllegalArgumentException(
				"Length is less than or equal to 0");
		}

		Map<String, Integer> seedMap = _seedMap.get();

		Integer previousSeed = seedMap.get(input);

		int seed = 0;

		if (previousSeed == null) {
			seed = input.hashCode();
		}
		else {
			seed = previousSeed;
		}

		StringBundler sb = new StringBundler(length);

		for (int i = 0; i < length; i++) {
			int index = 0;

			if (seed > 0) {
				index = seed % 26;
			}
			else {
				index = -seed % 26;
			}

			sb.append(_CHARACTERS[index]);

			seed = _nextRandom(seed);
		}

		seedMap.put(input, seed);

		return sb.toString();
	}

	public static void reset() {
		Map<String, Integer> seedMap = _seedMap.get();

		seedMap.clear();
	}

	public static void reset(String key) {
		Map<String, Integer> seedMap = _seedMap.get();

		seedMap.remove(key);
	}

	private static int _nextRandom(int seed) {
		return ((seed % 127773) * 16807) - ((seed / 127773) * 2836);
	}

	private static final char[] _CHARACTERS =
		"abcdefghijklmnopqrstuvwxyz".toCharArray();

	private static final int _DEFAULT_LENGTH = 4;

	private static final ThreadLocal<Map<String, Integer>> _seedMap =
		new CentralizedThreadLocal<>(
			DeterminateKeyGenerator.class + "._seedMap", HashMap::new);

}