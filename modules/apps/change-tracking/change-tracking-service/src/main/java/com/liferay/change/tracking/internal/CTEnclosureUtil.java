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

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.reference.closure.CTClosure;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class CTEnclosureUtil {

	public static Map<Long, Set<Long>> getEnclosureMap(
		CTClosure ctClosure, long modelClassNameId, long modelClassPK) {

		Map<Long, Set<Long>> enclosureMap = new HashMap<>();

		Queue<Map.Entry<Long, List<Long>>> queue = new LinkedList<>();

		queue.add(
			new AbstractMap.SimpleImmutableEntry<>(
				modelClassNameId, Collections.singletonList(modelClassPK)));

		Map.Entry<Long, List<Long>> entry = null;

		while ((entry = queue.poll()) != null) {
			long classNameId = entry.getKey();

			for (long classPK : entry.getValue()) {
				Set<Long> classPKs = enclosureMap.computeIfAbsent(
					classNameId, key -> new HashSet<>());

				if (classPKs.add(classPK)) {
					Map<Long, List<Long>> childPKsMap =
						ctClosure.getChildPKsMap(classNameId, classPK);

					if (!childPKsMap.isEmpty()) {
						queue.addAll(childPKsMap.entrySet());
					}
				}
			}
		}

		return enclosureMap;
	}

	public static Set<Map.Entry<Long, Long>> getEnclosureParentEntries(
		CTClosure ctClosure, Map<Long, Set<Long>> ctEntryMap) {

		Set<Map.Entry<Long, Long>> parentEntries = new HashSet<>();

		_collectParentEntries(
			ctClosure, ctEntryMap, new LinkedList<>(),
			ctClosure.getRootPKsMap(), parentEntries);

		return parentEntries;
	}

	private static void _collectParentEntries(
		CTClosure ctClosure, Map<Long, Set<Long>> ctEntryMap,
		Deque<Map.Entry<Long, Long>> backtraceEntries,
		Map<Long, List<Long>> childPKsMap,
		Set<Map.Entry<Long, Long>> parentEntries) {

		for (Map.Entry<Long, List<Long>> entry : childPKsMap.entrySet()) {
			long classNameId = entry.getKey();

			for (long classPK : entry.getValue()) {
				Set<Long> classPKs = ctEntryMap.get(classNameId);

				if ((classPKs != null) && classPKs.contains(classPK)) {
					parentEntries.addAll(backtraceEntries);
				}
				else {
					backtraceEntries.push(
						new AbstractMap.SimpleImmutableEntry<>(
							classNameId, classPK));

					_collectParentEntries(
						ctClosure, ctEntryMap, backtraceEntries,
						ctClosure.getChildPKsMap(classNameId, classPK),
						parentEntries);

					backtraceEntries.pop();
				}
			}
		}
	}

}