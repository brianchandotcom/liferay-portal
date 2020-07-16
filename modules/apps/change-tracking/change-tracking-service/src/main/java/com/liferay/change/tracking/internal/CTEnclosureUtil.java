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

import com.liferay.change.tracking.closure.CTClosure;

import java.util.AbstractMap;
import java.util.Collection;
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

		return getEnclosureMap(
			ctClosure,
			Collections.singleton(
				new AbstractMap.SimpleImmutableEntry<>(
					modelClassNameId, Collections.singleton(modelClassPK))));
	}

	public static Map<Long, Set<Long>> getEnclosureMap(
		CTClosure ctClosure, Set<Map.Entry<Long, Set<Long>>> rootEntries) {

		Map<Long, Set<Long>> enclosureMap = new HashMap<>();

		Queue<Map.Entry<Long, ? extends Collection<Long>>> queue =
			new LinkedList<>(rootEntries);

		Map.Entry<Long, ? extends Collection<Long>> entry = null;

		while ((entry = queue.poll()) != null) {
			long classNameId = entry.getKey();

			for (long classPK : entry.getValue()) {
				Set<Long> classPKs = enclosureMap.computeIfAbsent(
					classNameId, key -> new HashSet<>());

				if (classPKs.add(classPK)) {
					Map<Long, ? extends Collection<Long>> childPKsMap =
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
		CTClosure ctClosure, Map<Long, Set<Long>> enclosureMap) {

		Set<Map.Entry<Long, Long>> parentEntries = new HashSet<>();

		visitParentEntries(
			ctClosure,
			(classNameId, classPK, backtraceEntries) -> {
				Set<Long> classPKs = enclosureMap.get(classNameId);

				if ((classPKs != null) && classPKs.contains(classPK)) {
					parentEntries.addAll(backtraceEntries);

					return true;
				}

				return false;
			});

		return parentEntries;
	}

	public static void visitParentEntries(
		CTClosure ctClosure, BacktraceVisitor backtraceVisitor) {

		_visitParentEntries(
			ctClosure, ctClosure.getRootPKsMap(), new LinkedList<>(),
			backtraceVisitor);
	}

	public interface BacktraceVisitor {

		public boolean visit(
			long classNameId, long classPK,
			Deque<Map.Entry<Long, Long>> backtraceEntries);

	}

	private static void _visitParentEntries(
		CTClosure ctClosure, Map<Long, List<Long>> childPKsMap,
		Deque<Map.Entry<Long, Long>> backtraceEntries,
		BacktraceVisitor backtraceVisitor) {

		for (Map.Entry<Long, List<Long>> entry : childPKsMap.entrySet()) {
			long classNameId = entry.getKey();

			for (long classPK : entry.getValue()) {
				if (backtraceVisitor.visit(
						classNameId, classPK, backtraceEntries)) {

					continue;
				}

				backtraceEntries.push(
					new AbstractMap.SimpleImmutableEntry<>(
						classNameId, classPK));

				_visitParentEntries(
					ctClosure, ctClosure.getChildPKsMap(classNameId, classPK),
					backtraceEntries, backtraceVisitor);

				backtraceEntries.pop();
			}
		}
	}

}