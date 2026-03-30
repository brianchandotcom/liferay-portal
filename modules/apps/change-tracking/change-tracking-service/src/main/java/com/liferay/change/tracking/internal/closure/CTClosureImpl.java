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

		Map<Node, Node> nodesMap = new HashMap<>();

		for (Map.Entry<Node, Collection<Node>> entry : closureMap.entrySet()) {
			Node node = entry.getKey();

			nodesMap.putIfAbsent(node, node);

			for (Node childNode : entry.getValue()) {
				nodesMap.putIfAbsent(childNode, childNode);
			}
		}

		for (Map.Entry<Node, Collection<Node>> entry : closureMap.entrySet()) {
			Node node = nodesMap.get(entry.getKey());

			if (node.equals(Node.ROOT_NODE)) {
				for (Node childNode : entry.getValue()) {
					_rootNodes.add(nodesMap.get(childNode));
				}

				continue;
			}

			Collection<Node> childNodes = entry.getValue();

			Set<Node> excludedNodes = new HashSet<>();

			Queue<Node> queue = new LinkedList<>(childNodes);

			while (queue.size() > 0) {
				Collection<Node> grandchildNodes = closureMap.get(
					queue.poll());

				if (grandchildNodes != null) {
					for (Node grandchildNode : grandchildNodes) {
						if (excludedNodes.add(grandchildNode)) {
							queue.add(grandchildNode);
						}
					}
				}
			}

			for (Node childNode : childNodes) {
				if (excludedNodes.contains(childNode)) {
					continue;
				}

				childNode = nodesMap.get(childNode);

				childNode.addParent(node);

				node.addChild(childNode);
			}
		}

		for (Node node : nodesMap.values()) {
			if (!node.equals(Node.ROOT_NODE)) {
				_nodeMap.put(node, node);
			}
		}
	}

	@Override
	public Map<Long, List<Long>> getChildPKsMap(
		long classNameId, long classPK) {

		Node node = _nodeMap.get(new Node(classNameId, classPK));

		if (node == null) {
			return Collections.emptyMap();
		}

		List<Node> childNodes = node.getChildren();

		if (childNodes.isEmpty()) {
			return Collections.emptyMap();
		}

		return _toMap(childNodes);
	}

	@Override
	public long getCTCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public Map<Long, List<Long>> getParentPKsMap(
		long classNameId, long classPK) {

		Node node = _nodeMap.get(new Node(classNameId, classPK));

		if (node == null) {
			return Collections.emptyMap();
		}

		List<Node> parentNodes = node.getParents();

		if (parentNodes.isEmpty()) {
			return Collections.emptyMap();
		}

		return _toMap(parentNodes);
	}

	@Override
	public Map<Long, List<Long>> getRootPKsMap() {
		if (_rootNodes.isEmpty()) {
			return Collections.emptyMap();
		}

		return _toMap(_rootNodes);
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

	private Map<Long, List<Long>> _toMap(List<Node> nodes) {
		Map<Long, List<Long>> map = new HashMap<>();

		for (Node node : nodes) {
			List<Long> primaryKeys = map.computeIfAbsent(
				node.getClassNameId(), key -> new ArrayList<>());

			primaryKeys.add(node.getPrimaryKey());
		}

		return map;
	}

	private final long _ctCollectionId;
	private final Map<Node, Node> _nodeMap = new HashMap<>();
	private final List<Node> _rootNodes = new ArrayList<>();

}