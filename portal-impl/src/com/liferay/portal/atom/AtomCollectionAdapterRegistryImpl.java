/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.atom;

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomCollectionAdapterRegistry;
import com.liferay.portal.kernel.atom.AtomException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Igor Spasic
 */
public class AtomCollectionAdapterRegistryImpl
	implements AtomCollectionAdapterRegistry {

	public AtomCollectionAdapter<?> getAtomCollectionAdapter(
		String collectionName) {

		return _atomCollectionAdapters.get(collectionName);
	}

	public List<AtomCollectionAdapter<?>> getAtomCollectionAdapters() {
		return ListUtil.fromMapValues(_atomCollectionAdapters);
	}

	public void register(AtomCollectionAdapter<?> atomCollectionAdapter)
		throws AtomException {

		if (_atomCollectionAdapters.containsKey(
				atomCollectionAdapter.getCollectionName())) {

			throw new AtomException(
				"Duplicate collection name " +
					atomCollectionAdapter.getCollectionName());
		}

		_atomCollectionAdapters.put(
			atomCollectionAdapter.getCollectionName(), atomCollectionAdapter);
	}

	public void unregister(AtomCollectionAdapter<?> atomCollectionAdapter) {
		_atomCollectionAdapters.remove(
			atomCollectionAdapter.getCollectionName());
	}

	private Map<String, AtomCollectionAdapter<?>> _atomCollectionAdapters =
		new ConcurrentHashMap<String, AtomCollectionAdapter<?>>();

}