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

package com.liferay.petra.url.pattern.mapper.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public class DynamicSizeTrieURLPatternMapper<T>
	extends BaseTrieURLPatternMapper<T> {

	public DynamicSizeTrieURLPatternMapper(Map<String, T> values) {
		_extensionTrieNode = _trieNodeHeap.nextAvailableTrieNode();
		_wildCardTrieNode = _trieNodeHeap.nextAvailableTrieNode();

		for (Map.Entry<String, T> entry : values.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	protected void consumeWildcardValues(Consumer<T> consumer, String urlPath) {
		boolean exact = false;
		boolean wildcard = false;

		if (urlPath.charAt(0) != '/') {
			exact = true;
		}
		else if ((urlPath.length() > 1) &&
				 (urlPath.charAt(urlPath.length() - 2) == '/') &&
				 (urlPath.charAt(urlPath.length() - 1) == '*')) {

			wildcard = true;
		}

		TrieNode currentTrieNode = null;
		TrieNode previousTrieNode = _wildCardTrieNode;

		for (int i = 0; i < urlPath.length(); ++i) {
			currentTrieNode = previousTrieNode.getNextTrieNode(
				urlPath.charAt(i));

			if (currentTrieNode == null) {
				break;
			}

			if (!exact && (urlPath.charAt(i) == '/')) {
				TrieNode nextTrieNode = currentTrieNode.getNextTrieNode('*');

				if ((nextTrieNode != null) && nextTrieNode.isEnd()) {
					consumer.accept(nextTrieNode.getValue());
				}
			}

			previousTrieNode = currentTrieNode;
		}

		if (currentTrieNode != null) {
			if (exact) {
				if (currentTrieNode.isEnd()) {
					consumer.accept(currentTrieNode.getValue());
				}

				return;
			}

			if (!wildcard && currentTrieNode.isEnd()) {
				consumer.accept(currentTrieNode.getValue());
			}

			currentTrieNode = currentTrieNode.getNextTrieNode('/');

			if (currentTrieNode != null) {
				currentTrieNode = currentTrieNode.getNextTrieNode('*');

				if ((currentTrieNode != null) && currentTrieNode.isEnd()) {
					consumer.accept(currentTrieNode.getValue());
				}
			}
		}
	}

	@Override
	protected T getExtensionValue(String urlPath) {
		TrieNode currentTrieNode = null;
		TrieNode previousTrieNode = _extensionTrieNode;

		for (int i = 0; i < urlPath.length(); ++i) {
			int index = urlPath.length() - 1 - i;

			char character = urlPath.charAt(index);

			if (character == '/') {
				break;
			}

			currentTrieNode = previousTrieNode.getNextTrieNode(character);

			if (Objects.isNull(currentTrieNode)) {
				break;
			}

			if (urlPath.charAt(index) == '.') {
				TrieNode nextTrieNode = currentTrieNode.getNextTrieNode('*');

				return nextTrieNode.getValue();
			}

			previousTrieNode = currentTrieNode;
		}

		return null;
	}

	@Override
	protected T getWildcardValue(String urlPath) {
		boolean exact = false;
		boolean wildcard = false;

		if (urlPath.charAt(0) != '/') {
			exact = true;
		}
		else if ((urlPath.length() > 1) &&
				 (urlPath.charAt(urlPath.length() - 2) == '/') &&
				 (urlPath.charAt(urlPath.length() - 1) == '*')) {

			wildcard = true;
		}

		T value = null;
		TrieNode currentTrieNode = null;
		TrieNode previousTrieNode = _wildCardTrieNode;

		for (int i = 0; i < urlPath.length(); ++i) {
			currentTrieNode = previousTrieNode.getNextTrieNode(
				urlPath.charAt(i));

			if (Objects.isNull(currentTrieNode)) {
				break;
			}

			if (!exact && (urlPath.charAt(i) == '/')) {
				TrieNode nextTrieNode = currentTrieNode.getNextTrieNode('*');

				if (Objects.nonNull(nextTrieNode) && nextTrieNode.isEnd()) {
					value = nextTrieNode.getValue();
				}
			}

			previousTrieNode = currentTrieNode;
		}

		if (Objects.nonNull(currentTrieNode)) {
			if (exact) {
				if (!currentTrieNode.isEnd()) {
					return null;
				}

				return currentTrieNode.getValue();
			}

			if (!wildcard && currentTrieNode.isEnd()) {
				return currentTrieNode.getValue();
			}

			currentTrieNode = currentTrieNode.getNextTrieNode('/');

			if (Objects.nonNull(currentTrieNode)) {
				currentTrieNode = currentTrieNode.getNextTrieNode('*');

				if (Objects.nonNull(currentTrieNode) &&
					currentTrieNode.isEnd()) {

					value = currentTrieNode.getValue();
				}
			}
		}

		return value;
	}

	@Override
	protected void put(String urlPattern, T value, boolean wildcard) {
		TrieNode previousTrieNode = null;

		if (wildcard) {
			previousTrieNode = _wildCardTrieNode;
		}
		else {
			previousTrieNode = _extensionTrieNode;
		}

		TrieNode currentTrieNode = null;

		for (int i = 0; i < urlPattern.length(); ++i) {
			int index = i;

			if (!wildcard) {
				index = urlPattern.length() - 1 - i;
			}

			currentTrieNode = previousTrieNode.getNextTrieNode(
				urlPattern.charAt(index));

			if (Objects.isNull(currentTrieNode)) {
				TrieNode nextTrieNode = _trieNodeHeap.nextAvailableTrieNode();

				currentTrieNode = previousTrieNode.setNextTrieNode(
					urlPattern.charAt(index), nextTrieNode);
			}

			previousTrieNode = currentTrieNode;
		}

		currentTrieNode.setValue(value);
	}

	private final TrieNode _extensionTrieNode;
	private final TrieNodeHeap _trieNodeHeap = new TrieNodeHeap();
	private final TrieNode _wildCardTrieNode;

	private class TrieNode {

		public TrieNode() {
			_trieNodes = new ArrayList<>(ASCII_CHARACTER_RANGE);

			for (int i = 0; i < ASCII_CHARACTER_RANGE; ++i) {
				_trieNodes.add(null);
			}
		}

		public TrieNode getNextTrieNode(char character) {
			return _trieNodes.get(character - ASCII_PRINTABLE_OFFSET);
		}

		public T getValue() {
			return _value;
		}

		public boolean isEnd() {
			if (Objects.nonNull(_value)) {
				return true;
			}

			return false;
		}

		public TrieNode setNextTrieNode(char character, TrieNode nextTrieNode) {
			_trieNodes.set(character - ASCII_PRINTABLE_OFFSET, nextTrieNode);

			return nextTrieNode;
		}

		public void setValue(T value) {
			_value = value;
		}

		private final List<TrieNode> _trieNodes;
		private T _value;

	}

	private class TrieNodeHeap {

		public TrieNodeHeap() {
			for (int i = 0; i < _SIZE; ++i) {
				_trieNodes.add(new TrieNode());
			}
		}

		public TrieNode nextAvailableTrieNode() {
			if (_nextAvailableTrieNodeIndex >= _trieNodes.size()) {
				_trieNodes.ensureCapacity(_trieNodes.size() + _SIZE);

				for (int i = 0; i < _SIZE; ++i) {
					_trieNodes.add(new TrieNode());
				}
			}

			return _trieNodes.get(_nextAvailableTrieNodeIndex++);
		}

		private static final int _SIZE = 1024;

		private int _nextAvailableTrieNodeIndex;
		private ArrayList<TrieNode> _trieNodes = new ArrayList<>(_SIZE);

	}

}