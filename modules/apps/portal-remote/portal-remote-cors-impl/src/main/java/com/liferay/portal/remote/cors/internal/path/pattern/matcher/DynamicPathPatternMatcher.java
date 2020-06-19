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
public class DynamicPathPatternMatcher<T> extends PathPatternMatcher<T> {

	public DynamicPathPatternMatcher() {
		_trieNodeArrayList = new TrieNodeArrayList<>();

		_extensionTrieNode = _trieNodeArrayList.nextNode();
		_wildCardTrieNode = _trieNodeArrayList.nextNode();
	}

	@Override
	public PatternTuple<T> getPatternTuple(String path) {
		PatternTuple<T> patternTuple = getWildcardPatternTuple(path);

		if (patternTuple != null) {
			return patternTuple;
		}

		return getExtensionPatternTuple(path);
	}

	@Override
	public List<PatternTuple<T>> getPatternTuples(String path) {
		List<PatternTuple<T>> patternTuples = getWildcardPatternTuples(path);

		PatternTuple<T> patternTuple = getExtensionPatternTuple(path);

		if (patternTuple != null) {
			patternTuples.add(patternTuple);
		}

		return patternTuples;
	}

	public void insert(String pathPattern, T value)
		throws IllegalArgumentException {

		if (value == null) {
			throw new IllegalArgumentException("Value can not be null");
		}

		if (isValidWildCardPattern(pathPattern)) {
			insert(pathPattern, value, _wildCardTrieNode, true);

			return;
		}

		if (isValidExtensionPattern(pathPattern)) {
			insert(pathPattern, value, _extensionTrieNode, false);

			return;
		}

		insert(pathPattern, value, _wildCardTrieNode, true);
	}

	protected PatternTuple<T> getExtensionPatternTuple(String path) {
		TrieNode<T> currentTrieNode = null;
		TrieNode<T> previousTrieNode = _extensionTrieNode;

		for (int i = 0; i < path.length(); ++i) {
			int index = path.length() - 1 - i;

			char character = path.charAt(index);

			if (character == '/') {
				break;
			}

			currentTrieNode = previousTrieNode.next(character);

			if (currentTrieNode == null) {
				break;
			}

			if (path.charAt(index) == '.') {
				TrieNode<T> nextTrieNode = currentTrieNode.next('*');

				if ((nextTrieNode != null) && nextTrieNode.isEnd()) {
					return nextTrieNode.getPatternTuple();
				}
			}

			previousTrieNode = currentTrieNode;
		}

		return null;
	}

	protected PatternTuple<T> getWildcardPatternTuple(String path) {
		boolean onlyExact = false;
		boolean onlyWildcard = false;

		if (path.charAt(0) != '/') {
			onlyExact = true;
		}
		else if ((path.length() > 1) &&
				 (path.charAt(path.length() - 2) == '/') &&
				 (path.charAt(path.length() - 1) == '*')) {

			onlyWildcard = true;
		}

		PatternTuple<T> patternTuple = null;

		TrieNode<T> currentTrieNode = null;
		TrieNode<T> previousTrieNode = _wildCardTrieNode;

		for (int i = 0; i < path.length(); ++i) {
			currentTrieNode = previousTrieNode.next(path.charAt(i));

			if (currentTrieNode == null) {
				break;
			}

			if (!onlyExact && (path.charAt(i) == '/')) {
				TrieNode<T> nextTrieNode = currentTrieNode.next('*');

				if ((nextTrieNode != null) && nextTrieNode.isEnd()) {
					patternTuple = nextTrieNode.getPatternTuple();
				}
			}

			previousTrieNode = currentTrieNode;
		}

		// if current node is null, it means trie travesaling
		// did not match every character.

		if (currentTrieNode != null) {
			if (onlyExact) {
				if (!currentTrieNode.isEnd()) {
					return null;
				}

				return currentTrieNode.getPatternTuple();
			}

			if (!onlyWildcard && currentTrieNode.isEnd()) {
				return currentTrieNode.getPatternTuple();
			}

			currentTrieNode = currentTrieNode.next('/');

			if (currentTrieNode != null) {
				currentTrieNode = currentTrieNode.next('*');

				if ((currentTrieNode != null) && currentTrieNode.isEnd()) {
					patternTuple = currentTrieNode.getPatternTuple();
				}
			}
		}

		return patternTuple;
	}

	protected List<PatternTuple<T>> getWildcardPatternTuples(String path) {
		boolean onlyExact = false;
		boolean onlyWildcard = false;

		if (path.charAt(0) != '/') {
			onlyExact = true;
		}
		else if ((path.length() > 1) &&
				 (path.charAt(path.length() - 2) == '/') &&
				 (path.charAt(path.length() - 1) == '*')) {

			onlyWildcard = true;
		}

		List<PatternTuple<T>> patternTuples = new ArrayList<>(64);

		TrieNode<T> currentTrieNode = null;
		TrieNode<T> previousTrieNode = _wildCardTrieNode;

		for (int i = 0; i < path.length(); ++i) {
			currentTrieNode = previousTrieNode.next(path.charAt(i));

			if (currentTrieNode == null) {
				break;
			}

			if (!onlyExact && (path.charAt(i) == '/')) {
				TrieNode<T> nextTrieNode = currentTrieNode.next('*');

				if ((nextTrieNode != null) && nextTrieNode.isEnd()) {
					patternTuples.add(nextTrieNode.getPatternTuple());
				}
			}

			previousTrieNode = currentTrieNode;
		}

		// if current node is null, it means trie travesaling
		// did not match every character.

		if (currentTrieNode != null) {
			if (onlyExact) {
				if (currentTrieNode.isEnd()) {
					patternTuples.add(currentTrieNode.getPatternTuple());
				}

				return patternTuples;
			}

			if (!onlyWildcard && currentTrieNode.isEnd()) {
				patternTuples.add(currentTrieNode.getPatternTuple());
			}

			currentTrieNode = currentTrieNode.next('/');

			if (currentTrieNode != null) {
				currentTrieNode = currentTrieNode.next('*');

				if ((currentTrieNode != null) && currentTrieNode.isEnd()) {
					patternTuples.add(currentTrieNode.getPatternTuple());
				}
			}
		}

		return patternTuples;
	}

	protected void insert(
		String pathPattern, T value, TrieNode<T> previousTrieNode,
		boolean forward) {

		TrieNode<T> currentTrieNode = null;

		for (int i = 0; i < pathPattern.length(); ++i) {
			int index = i;

			if (!forward) {
				index = pathPattern.length() - 1 - i;
			}

			currentTrieNode = previousTrieNode.next(pathPattern.charAt(index));

			if (currentTrieNode == null) {
				currentTrieNode = previousTrieNode.setNext(
					pathPattern.charAt(index), _trieNodeArrayList);
			}

			previousTrieNode = currentTrieNode;
		}

		if (currentTrieNode != null) {
			currentTrieNode.setPatternTuple(
				new PatternTuple<>(pathPattern, value));
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

		public PatternTuple<T> getPatternTuple() {
			return _patternTuple;
		}

		public boolean isEnd() {
			if (_patternTuple != null) {
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

		public void setPatternTuple(PatternTuple<T> patternTuple) {
			_patternTuple = patternTuple;
		}

		private PatternTuple<T> _patternTuple;

		/**
		 * Use list over hashMap for better performance
		 * There is around 70% performance increase for best match,
		 * and 40% performance increase for all matches.
		 */
		private final List<TrieNode<T>> _trieNodes;

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