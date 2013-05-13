/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Igor Spasic
 */
@DoPrivileged
public class AtomCollectionAdapterRegistryImpl
	implements AtomCollectionAdapterRegistry {

	@Override
	public AtomCollectionAdapter<?> getAtomCollectionAdapter(
		String collectionName) {

		return _atomCollectionAdapters.get(collectionName);
	}

	@Override
	public List<AtomCollectionAdapter<?>> getAtomCollectionAdapters() {
		return ListUtil.fromMapValues(_atomCollectionAdapters);
	}

	@Override
	public void register(AtomCollectionAdapter<?> atomCollectionAdapter) {
		if (_atomCollectionAdapters.containsKey(
				atomCollectionAdapter.getCollectionName())) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Duplicate collection name " +
						atomCollectionAdapter.getCollectionName());
			}

			return;
		}

		_atomCollectionAdapters.put(
			atomCollectionAdapter.getCollectionName(), atomCollectionAdapter);
	}

	@Override
	public void unregister(AtomCollectionAdapter<?> atomCollectionAdapter) {
		_atomCollectionAdapters.remove(
			atomCollectionAdapter.getCollectionName());
	}

	private static Log _log = LogFactoryUtil.getLog(
		AtomCollectionAdapterRegistryImpl.class);

	private Map<String, AtomCollectionAdapter<?>> _atomCollectionAdapters =
		new ConcurrentHashMap<String, AtomCollectionAdapter<?>>();

}