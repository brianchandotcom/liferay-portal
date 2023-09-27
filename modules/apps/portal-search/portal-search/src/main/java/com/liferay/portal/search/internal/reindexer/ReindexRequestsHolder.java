/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.reindexer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author André de Oliveira
 */
public class ReindexRequestsHolder {

	public synchronized void addAll(String className, long... classPKs) {
		Set<Long> set = _map.computeIfAbsent(className, key -> new HashSet<>());

		for (long classPK : classPKs) {
			set.add(classPK);
		}
	}

	public synchronized Collection<Long> drain(String className) {
		Set<Long> set = _map.remove(className);

		if (set != null) {
			return set;
		}

		return Collections.emptySet();
	}

	private final Map<String, Set<Long>> _map = new HashMap<>();

}