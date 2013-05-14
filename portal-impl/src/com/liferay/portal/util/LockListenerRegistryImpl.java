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

package com.liferay.portal.util;

import com.liferay.portal.kernel.lock.LockListener;
import com.liferay.portal.kernel.lock.LockListenerRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Chow
 */
@DoPrivileged
public class LockListenerRegistryImpl implements LockListenerRegistry {

	public LockListenerRegistryImpl() {
		String[] lockListenerClassNames = PropsUtil.getArray(
			PropsKeys.LOCK_LISTENERS);

		for (String lockListenerClassName : lockListenerClassNames) {
			try {
				LockListener lockListener =
					(LockListener)InstanceFactory.newInstance(
						lockListenerClassName);

				register(lockListener);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Override
	public LockListener getLockListener(String className) {
		return _lockListeners.get(className);
	}

	@Override
	public void register(LockListener lockListener) {
		_lockListeners.put(lockListener.getClassName(), lockListener);
	}

	@Override
	public void unregister(LockListener lockListener) {
		_lockListeners.remove(lockListener.getClassName());
	}

	private static Log _log = LogFactoryUtil.getLog(
		LockListenerRegistryImpl.class);

	private Map<String, LockListener> _lockListeners =
		new ConcurrentHashMap<String, LockListener>();

}