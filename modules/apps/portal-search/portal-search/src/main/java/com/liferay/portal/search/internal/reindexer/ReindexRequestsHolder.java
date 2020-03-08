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