/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.display;

import com.liferay.change.tracking.closure.CTClosure;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @author Preston Crary
 */
public class CTClosureUtil {

	public static Set<Long> getClassNameIds(CTClosure ctClosure) {
		Set<Long> classNameIds = new HashSet<>();

		_navigateCTClosure(
			ctClosure,
			(parentClassNameId, entry) -> classNameIds.add(entry.getKey()));

		return classNameIds;
	}

	public static Map<Long, List<Long>> getFamilyPKsMap(
		CTClosure ctClosure, long classNameId, long classPK) {

		Map<Long, List<Long>> familyPKsMap = new HashMap<>();

		_navigateCTClosure(
			ctClosure,
			(entry, childEntry) -> {
				List<Long> primaryKeys = childEntry.getValue();

				if ((childEntry.getKey() == classNameId) &&
					primaryKeys.contains(classPK)) {

					familyPKsMap.put(entry.getKey(), entry.getValue());
				}
			});

		familyPKsMap.putAll(ctClosure.getChildPKsMap(classNameId, classPK));

		List<Long> primaryKeys = familyPKsMap.computeIfAbsent(
			classNameId, key -> new ArrayList<>());

		primaryKeys.add(classPK);

		return familyPKsMap;
	}

	public static Set<Long> getParentClassNameIds(
		CTClosure ctClosure, long classNameId) {

		Set<Long> parentClassNameIds = new HashSet<>();

		_navigateCTClosure(
			ctClosure,
			(entry, childEntry) -> {
				if (childEntry.getKey() == classNameId) {
					parentClassNameIds.add(childEntry.getKey());
				}
			});

		return parentClassNameIds;
	}

	public static Set<Long> getPKs(CTClosure ctClosure, long classNameId) {
		Set<Long> primaryKeys = new HashSet<>();

		_navigateCTClosure(
			ctClosure,
			(entry, childEntry) -> {
				if (childEntry.getKey() == classNameId) {
					primaryKeys.addAll(childEntry.getValue());
				}
			});

		return primaryKeys;
	}

	private static void _navigateCTClosure(
		CTClosure ctClosure,
		BiConsumer<Map.Entry<Long, List<Long>>, Map.Entry<Long, List<Long>>>
			biConsumer) {

		Queue<Map.Entry<Long, List<Long>>> queue = new LinkedList<>();

		queue.add(
			new AbstractMap.SimpleImmutableEntry<>(
				0L, Collections.singletonList(0L)));

		Map.Entry<Long, List<Long>> entry = null;

		while ((entry = queue.poll()) != null) {
			long parentClassNameId = entry.getKey();

			for (long pk : entry.getValue()) {
				Map<Long, List<Long>> childPKsMap = ctClosure.getChildPKsMap(
					parentClassNameId, pk);

				for (Map.Entry<Long, List<Long>> childEntry :
						childPKsMap.entrySet()) {

					biConsumer.accept(entry, childEntry);

					queue.add(childEntry);
				}
			}
		}
	}

	private CTClosureUtil() {
	}

}