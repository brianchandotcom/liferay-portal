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

package com.liferay.portal.kernel.jmx;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

/**
 * @author Michael C. Han
 */
public class MBeanRegistry {

	public void destroy() throws Exception {
		synchronized (_objectNameCache) {
			for (ObjectName objectName : _objectNameCache.values()) {
				try {
					_mBeanServer.unregisterMBean(objectName);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to unregister MBean" +
								objectName.getCanonicalName(),
							e);
					}
				}
			}

			_objectNameCache.clear();
		}
	}

	public ObjectName getObjectName(String objectNameCacheKey) {
		return _objectNameCache.get(objectNameCacheKey);
	}

	public ObjectInstance register(
			String objectNameCacheKey, Object object, ObjectName objectName)
		throws InstanceAlreadyExistsException, MBeanRegistrationException,
			   NotCompliantMBeanException {

		ObjectInstance objectInstance = _mBeanServer.registerMBean(
			object, objectName);

		synchronized (_objectNameCache) {
			_objectNameCache.put(
				objectNameCacheKey, objectInstance.getObjectName());
		}

		return objectInstance;
	}

	public void replace(
			String objectCacheKey, Object object, ObjectName objectName)
		throws Exception {

		try {
			register(objectCacheKey, object, objectName);
		}
		catch (InstanceAlreadyExistsException iaee) {
			unregister(objectCacheKey, objectName);

			register(objectCacheKey, object, objectName);
		}
	}

	public void setMBeanServer(MBeanServer mBeanServer) {
		_mBeanServer = mBeanServer;
	}

	public void unregister(
			String objectNameCacheKey, ObjectName defaultObjectName)
		throws InstanceNotFoundException, MBeanRegistrationException {

		synchronized (_objectNameCache) {
			ObjectName objectName = _objectNameCache.get(objectNameCacheKey);

			if (objectName == null) {
				try {
					_mBeanServer.unregisterMBean(defaultObjectName);
				}
				catch (InstanceNotFoundException infe) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to unregister " + defaultObjectName, infe);
					}
				}
			}
			else {
				_objectNameCache.remove(objectNameCacheKey);

				_mBeanServer.unregisterMBean(objectName);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(MBeanRegistry.class);

	private MBeanServer _mBeanServer;
	private final Map<String, ObjectName> _objectNameCache =
		new ConcurrentHashMap<String, ObjectName>();

}