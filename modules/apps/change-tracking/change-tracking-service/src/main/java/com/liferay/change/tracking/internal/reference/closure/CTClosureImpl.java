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

package com.liferay.change.tracking.internal.reference.closure;

import com.liferay.change.tracking.reference.closure.CTClosure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
public class CTClosureImpl implements CTClosure {

	public CTClosureImpl(
		long ctCollectionId, Map<Node, Collection<Node>> closureMap) {

		_ctCollectionId = ctCollectionId;
		_closureMap = closureMap;
	}

	@Override
	public Map<Long, List<Long>> getChildPKsMap(
		long classNameId, long classPK) {

		Collection<Node> nodes = _closureMap.get(
			new Node(classNameId, classPK));

		if (nodes == null) {
			return Collections.emptyMap();
		}

		Set<Node> excludedNodes = new HashSet<>();

		Queue<Node> queue = new LinkedList<>(nodes);

		while (queue.size() > 0) {
			Collection<Node> childNodes = _closureMap.get(queue.poll());

			if (childNodes != null) {
				for (Node childNode : childNodes) {
					if (excludedNodes.add(childNode)) {
						queue.add(childNode);
					}
				}
			}
		}

		return _getPrimaryKeysMap(nodes, excludedNodes);
	}

	@Override
	public long getCTCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public Map<Long, List<Long>> getRootPKsMap() {
		return _getPrimaryKeysMap(
			_closureMap.get(Node.ROOT_NODE), Collections.emptySet());
	}

	private Map<Long, List<Long>> _getPrimaryKeysMap(
		Collection<Node> nodes, Set<Node> excludedNodes) {

		Map<Long, List<Long>> primaryKeysMap = new HashMap<>();

		for (Node node : nodes) {
			if (excludedNodes.contains(node)) {
				continue;
			}

			List<Long> primaryKeys = primaryKeysMap.computeIfAbsent(
				node.getClassNameId(), key -> new ArrayList<>());

			primaryKeys.add(node.getPrimaryKey());
		}

		return primaryKeysMap;
	}

	private final Map<Node, Collection<Node>> _closureMap;
	private final long _ctCollectionId;

}