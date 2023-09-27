/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.closure;

import com.liferay.change.tracking.closure.CTClosure;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

import java.util.AbstractMap;
import java.util.ArrayList;
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

	@Override
	public String toString() {
		StringBundler sb1 = new StringBundler();

		sb1.append("{\n");

		Map<Long, List<Long>> pksMap = getRootPKsMap();

		Deque<Map.Entry<Map.Entry<Long, ? extends Collection<Long>>, Integer>>
			queue = new LinkedList<>();

		for (Map.Entry<Long, ? extends Collection<Long>> entry :
				pksMap.entrySet()) {

			queue.add(new AbstractMap.SimpleImmutableEntry<>(entry, 1));
		}

		Map.Entry<Map.Entry<Long, ? extends Collection<Long>>, Integer>
			indentEntry = null;

		while ((indentEntry = queue.poll()) != null) {
			Map.Entry<Long, ? extends Collection<Long>> entry =
				indentEntry.getKey();

			long classNameId = entry.getKey();

			int indent = indentEntry.getValue();

			StringBundler sb2 = new StringBundler(indent);

			for (int i = 0; i < indent; i++) {
				sb2.append(CharPool.TAB);
			}

			String indentString = sb2.toString();

			for (long classPK : entry.getValue()) {
				sb1.append(indentString);
				sb1.append("(classNameId=");
				sb1.append(classNameId);
				sb1.append(", classPK=");
				sb1.append(classPK);
				sb1.append(")\n");

				Map<Long, ? extends Collection<Long>> childPKsMap =
					getChildPKsMap(classNameId, classPK);

				for (Map.Entry<Long, ? extends Collection<Long>> childEntry :
						childPKsMap.entrySet()) {

					queue.addFirst(
						new AbstractMap.SimpleImmutableEntry<>(
							childEntry, indent + 1));
				}
			}
		}

		sb1.append("}");

		return sb1.toString();
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