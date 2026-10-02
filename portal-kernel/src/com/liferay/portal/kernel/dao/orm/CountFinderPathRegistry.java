/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Shuyang Zhou
 */
public class CountFinderPathRegistry {

	public static List<FinderPath> getCountFinderPaths(String className) {
		List<FinderPath> countFinderPaths = _countFinderPathsMap.get(className);

		if (countFinderPaths == null) {
			return Collections.emptyList();
		}

		return countFinderPaths;
	}

	public static void register(FinderPath finderPath) {
		List<FinderPath> countFinderPaths =
			_countFinderPathsMap.computeIfAbsent(
				finderPath.getEntityClassName(),
				key -> new CopyOnWriteArrayList<>());

		countFinderPaths.add(finderPath);
	}

	public static void unregister(String className) {
		_countFinderPathsMap.remove(className);
	}

	private static final Map<String, List<FinderPath>> _countFinderPathsMap =
		new ConcurrentHashMap<>();

}