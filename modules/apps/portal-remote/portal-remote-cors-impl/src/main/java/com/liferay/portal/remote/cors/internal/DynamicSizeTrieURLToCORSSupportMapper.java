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

package com.liferay.portal.remote.cors.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public class DynamicSizeTrieURLToCORSSupportMapper
	extends BaseTrieURLToCORSSupportMapper {

	public DynamicSizeTrieURLToCORSSupportMapper(
		Map<String, CORSSupport> corsSupports) {

		_trieNodeHeap = new TrieNodeHeap();

		_extensionTrieNode = _trieNodeHeap.nextAvailableNode();
		_wildCardTrieNode = _trieNodeHeap.nextAvailableNode();

		for (Map.Entry<String, CORSSupport> entry : corsSupports.entrySet()) {
			put(entry.getValue(), entry.getKey());
		}
	}

	@Override
	protected CORSSupport getExtensionCORSSupport(String urlPath) {
		TrieNode currentTrieNode = null;
		TrieNode previousTrieNode = _extensionTrieNode;

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
				TrieNode nextTrieNode = currentTrieNode.next('*');

				if ((nextTrieNode != null) && nextTrieNode.isEnd()) {
					return nextTrieNode.get();
				}
			}

			previousTrieNode = currentTrieNode;
		}

		return null;
	}

	@Override
	protected CORSSupport getWildcardCORSSupport(String urlPath) {
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

		CORSSupport corsSupport = null;
		TrieNode currentTrieNode = null;
		TrieNode previousTrieNode = _wildCardTrieNode;

		for (int i = 0; i < urlPath.length(); ++i) {
			currentTrieNode = previousTrieNode.next(urlPath.charAt(i));

			if (currentTrieNode == null) {
				break;
			}

			if (!onlyExact && (urlPath.charAt(i) == '/')) {
				TrieNode nextTrieNode = currentTrieNode.next('*');

				if ((nextTrieNode != null) && nextTrieNode.isEnd()) {
					corsSupport = nextTrieNode.get();
				}
			}

			previousTrieNode = currentTrieNode;
		}

		if (currentTrieNode != null) {
			if (onlyExact) {
				if (!currentTrieNode.isEnd()) {
					return null;
				}

				return currentTrieNode.get();
			}

			if (!onlyWildcard && currentTrieNode.isEnd()) {
				return currentTrieNode.get();
			}

			currentTrieNode = currentTrieNode.next('/');

			if (currentTrieNode != null) {
				currentTrieNode = currentTrieNode.next('*');

				if ((currentTrieNode != null) && currentTrieNode.isEnd()) {
					corsSupport = currentTrieNode.get();
				}
			}
		}

		return corsSupport;
	}

	@Override
	protected void put(
		CORSSupport corsSupport, String urlPattern, boolean wildcard) {

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

			currentTrieNode = previousTrieNode.next(urlPattern.charAt(index));

			if (currentTrieNode == null) {
				TrieNode nextNode = _trieNodeHeap.nextAvailableNode();

				currentTrieNode = previousTrieNode.setNext(
					urlPattern.charAt(index), nextNode);
			}

			previousTrieNode = currentTrieNode;
		}

		if (currentTrieNode != null) {
			currentTrieNode.set(corsSupport);
		}
	}

	private final TrieNode _extensionTrieNode;
	private final TrieNodeHeap _trieNodeHeap;
	private final TrieNode _wildCardTrieNode;

	private static class TrieNode {

		public TrieNode() {
			_trieNodes = new ArrayList<>(ASCII_CHARACTER_RANGE);

			for (int i = 0; i < ASCII_CHARACTER_RANGE; ++i) {
				_trieNodes.add(null);
			}
		}

		public CORSSupport get() {
			return _corsSupport;
		}

		public boolean isEnd() {
			if (_corsSupport != null) {
				return true;
			}

			return false;
		}

		public TrieNode next(char character) {
			return _trieNodes.get(character - ASCII_PRINTABLE_OFFSET);
		}

		public void set(CORSSupport corsSupport) {
			_corsSupport = corsSupport;
		}

		public TrieNode setNext(char character, TrieNode nextNode) {
			_trieNodes.set(character - ASCII_PRINTABLE_OFFSET, nextNode);

			return nextNode;
		}

		private CORSSupport _corsSupport;
		private final List<TrieNode> _trieNodes;

	}

	private static class TrieNodeHeap {

		public TrieNodeHeap() {
			_trieNodes = new ArrayList<>(_INIT_SIZE);

			for (int i = 0; i < _INIT_SIZE; ++i) {
				_trieNodes.add(new TrieNode());
			}
		}

		public TrieNode nextAvailableNode() {
			if (_nextAvailableNodeIndex >= _trieNodes.size()) {
				_trieNodes.ensureCapacity(_trieNodes.size() + _INIT_SIZE);

				for (int i = 0; i < _INIT_SIZE; ++i) {
					_trieNodes.add(new TrieNode());
				}
			}

			return _trieNodes.get(_nextAvailableNodeIndex++);
		}

		private static final int _INIT_SIZE = 1024;

		private int _nextAvailableNodeIndex;
		private ArrayList<TrieNode> _trieNodes;

	}

}