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

package com.liferay.portal.remote.cors.internal.path.pattern.matcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public class DynamicURLPathPatternMatcher<T>
	implements URLPathPatternMatcher<T> {

	public DynamicURLPathPatternMatcher() {
		_trieNodeArrayList = new TrieNodeArrayList<>();

		_extensionTrieNode = _trieNodeArrayList.nextNode();
		_wildCardTrieNode = _trieNodeArrayList.nextNode();
	}

	@Override
	public T getValue(String urlPath) {
		T value = getWildcardValue(urlPath);

		if (value != null) {
			return value;
		}

		return getExtensionValue(urlPath);
	}

	public void putValue(String urlPathPattern, T value)
		throws IllegalArgumentException {

		if (value == null) {
			throw new IllegalArgumentException("Value can not be null");
		}

		if (URLPathPatternMatcher.isValidWildCardPattern(urlPathPattern)) {
			putValue(urlPathPattern, value, _wildCardTrieNode, true);

			return;
		}

		if (URLPathPatternMatcher.isValidExtensionPattern(urlPathPattern)) {
			putValue(urlPathPattern, value, _extensionTrieNode, false);

			return;
		}

		putValue(urlPathPattern, value, _wildCardTrieNode, true);
	}

	protected T getExtensionValue(String urlPath) {
		TrieNode<T> currentTrieNode = null;
		TrieNode<T> previousTrieNode = _extensionTrieNode;

		for (int i = 0; i < urlPath.length(); ++i) {
			int index = urlPath.length() - 1 - i;

			char character = urlPath.charAt(index);

			if (character == '/') {
				break;
			}

			currentTrieNode = previousTrieNode.next(character);

			if (currentTrieNode == null) {
				break;
			}

			if (urlPath.charAt(index) == '.') {
				TrieNode<T> nextTrieNode = currentTrieNode.next('*');

				if ((nextTrieNode != null) && nextTrieNode.isEnd()) {
					return nextTrieNode.getValue();
				}
			}

			previousTrieNode = currentTrieNode;
		}

		return null;
	}

	protected T getWildcardValue(String urlPath) {
		boolean onlyExact = false;
		boolean onlyWildcard = false;

		if (urlPath.charAt(0) != '/') {
			onlyExact = true;
		}
		else if ((urlPath.length() > 1) &&
				 (urlPath.charAt(urlPath.length() - 2) == '/') &&
				 (urlPath.charAt(urlPath.length() - 1) == '*')) {

			onlyWildcard = true;
		}

		T value = null;

		TrieNode<T> currentTrieNode = null;
		TrieNode<T> previousTrieNode = _wildCardTrieNode;

		for (int i = 0; i < urlPath.length(); ++i) {
			currentTrieNode = previousTrieNode.next(urlPath.charAt(i));

			if (currentTrieNode == null) {
				break;
			}

			if (!onlyExact && (urlPath.charAt(i) == '/')) {
				TrieNode<T> nextTrieNode = currentTrieNode.next('*');

				if ((nextTrieNode != null) && nextTrieNode.isEnd()) {
					value = nextTrieNode.getValue();
				}
			}

			previousTrieNode = currentTrieNode;
		}

		if (currentTrieNode != null) {
			if (onlyExact) {
				if (!currentTrieNode.isEnd()) {
					return null;
				}

				return currentTrieNode.getValue();
			}

			if (!onlyWildcard && currentTrieNode.isEnd()) {
				return currentTrieNode.getValue();
			}

			currentTrieNode = currentTrieNode.next('/');

			if (currentTrieNode != null) {
				currentTrieNode = currentTrieNode.next('*');

				if ((currentTrieNode != null) && currentTrieNode.isEnd()) {
					value = currentTrieNode.getValue();
				}
			}
		}

		return value;
	}

	protected void putValue(
		String urlPathPattern, T value, TrieNode<T> previousTrieNode,
		boolean forward) {

		TrieNode<T> currentTrieNode = null;

		for (int i = 0; i < urlPathPattern.length(); ++i) {
			int index = i;

			if (!forward) {
				index = urlPathPattern.length() - 1 - i;
			}

			currentTrieNode = previousTrieNode.next(
				urlPathPattern.charAt(index));

			if (currentTrieNode == null) {
				currentTrieNode = previousTrieNode.setNext(
					urlPathPattern.charAt(index), _trieNodeArrayList);
			}

			previousTrieNode = currentTrieNode;
		}

		if (currentTrieNode != null) {
			currentTrieNode.setValue(value);
		}
	}

	private final TrieNode<T> _extensionTrieNode;
	private final TrieNodeArrayList<T> _trieNodeArrayList;
	private final TrieNode<T> _wildCardTrieNode;

	private static class TrieNode<T> {

		public TrieNode() {
			_trieNodes = new ArrayList<>(ASCII_CHARACTER_RANGE);

			for (int i = 0; i < ASCII_CHARACTER_RANGE; ++i) {
				_trieNodes.add(null);
			}
		}

		public T getValue() {
			return _value;
		}

		public boolean isEnd() {
			if (_value != null) {
				return true;
			}

			return false;
		}

		public TrieNode<T> next(char character) {
			int index = character - ASCII_PRINTABLE_OFFSET;

			if ((index < 0) ||
				(index >= (ASCII_CHARACTER_RANGE + ASCII_PRINTABLE_OFFSET))) {

				throw new IllegalArgumentException();
			}

			return _trieNodes.get(index);
		}

		public TrieNode<T> setNext(
			char character, TrieNodeArrayList<T> trieNodeArrayList) {

			TrieNode<T> trieNode = trieNodeArrayList.nextNode();

			int index = character - ASCII_PRINTABLE_OFFSET;

			if ((index < 0) ||
				(index >= (ASCII_CHARACTER_RANGE + ASCII_PRINTABLE_OFFSET))) {

				throw new IllegalArgumentException();
			}

			_trieNodes.set(index, trieNode);

			return trieNode;
		}

		public void setValue(T value) {
			_value = value;
		}

		private final List<TrieNode<T>> _trieNodes;
		private T _value;

	}

	private static class TrieNodeArrayList<T> {

		public TrieNodeArrayList() {
			_trieNodes = new ArrayList<>(_INIT_SIZE);

			for (int i = 0; i < _INIT_SIZE; ++i) {
				_trieNodes.add(new TrieNode<>());
			}
		}

		public TrieNode<T> nextNode() {
			if (_index >= _trieNodes.size()) {
				_trieNodes.ensureCapacity(_trieNodes.size() + _INIT_SIZE);

				for (int i = 0; i < _INIT_SIZE; ++i) {
					_trieNodes.add(new TrieNode<>());
				}
			}

			return _trieNodes.get(_index++);
		}

		private static final int _INIT_SIZE = 1024;

		private int _index;
		private ArrayList<TrieNode<T>> _trieNodes;

	}

}