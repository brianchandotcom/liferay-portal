/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.lock.LockListener;
import com.liferay.portal.kernel.lock.LockListenerRegistry;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Chow
 */
public class LockListenerRegistryImpl implements LockListenerRegistry {

	public LockListenerRegistryImpl() {
		String[] lockListenerClassNames = PropsUtil.getArray(
			PropsKeys.LOCK_LISTENERS);

		for (String lockListenerClassName : lockListenerClassNames) {
			LockListener lockListener = (LockListener)InstancePool.get(
				lockListenerClassName);

			register(lockListener);
		}
	}

	public LockListener getLockListener(String className) {
		return _lockListenerMap.get(className);
	}

	public void register(LockListener lockListener) {
		_lockListenerMap.put(lockListener.getClassName(), lockListener);
	}

	public void unregister(LockListener lockListener) {
		_lockListenerMap.remove(lockListener.getClassName());
	}

	private Map<String, LockListener> _lockListenerMap =
		new ConcurrentHashMap<String, LockListener>();

}