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

package com.liferay.portal.jmx.internal;

import com.liferay.portal.jmx.MBeanRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.management.ManagementFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = MBeanRegistry.class)
public class MBeanRegistryImpl implements MBeanRegistry {

	@Override
	public MBeanServer getMBeanServer() {
		return _mBeanServer;
	}

	@Override
	public ObjectName getObjectName(String objectNameCacheKey) {
		return _objectNameCache.get(objectNameCacheKey);
	}

	@Override
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

	@Override
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

	@Override
	public void unregister(
			String objectNameCacheKey, ObjectName defaultObjectName)
		throws InstanceNotFoundException, MBeanRegistrationException {

		synchronized (_objectNameCache) {
			ObjectName objectName = _objectNameCache.remove(objectNameCacheKey);

			try {
				if (objectName == null) {
					_mBeanServer.unregisterMBean(defaultObjectName);
				}
				else {
					_mBeanServer.unregisterMBean(objectName);
				}
			}
			catch (InstanceNotFoundException infe) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to unregister " + defaultObjectName, infe);
				}
			}
		}
	}

	@Activate
	protected void activate() {
		_mBeanServer = ManagementFactory.getPlatformMBeanServer();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDynamicMBean(
		DynamicMBean dynamicMBean, Map<String, Object> properties) {

		String objectName = GetterUtil.getString(properties.get("object-name"));

		String objectNameCacheKey = GetterUtil.getString(
			properties.get("object-name-cache-key"));

		if (Validator.isNull(objectNameCacheKey)) {
			objectNameCacheKey = objectName;
		}

		try {
			register(
				objectNameCacheKey, dynamicMBean, new ObjectName(objectName));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to register mbean", e);
			}
		}
	}

	@Deactivate
	protected void deactivate() throws Exception {
		synchronized (_objectNameCache) {
			for (ObjectName objectName : _objectNameCache.values()) {
				try {
					_mBeanServer.unregisterMBean(objectName);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to unregister mbean" +
								objectName.getCanonicalName(),
							e);
					}
				}
			}

			_objectNameCache.clear();
		}
	}

	protected void removeDynamicMBean(
		DynamicMBean dynamicMBean, Map<String, Object> properties) {

		String objectName = GetterUtil.getString(properties.get("object-name"));

		String objectNameCacheKey = GetterUtil.getString(
			properties.get("object-name-cache-key"));

		if (Validator.isNull(objectNameCacheKey)) {
			objectNameCacheKey = objectName;
		}

		try {
			unregister(objectNameCacheKey, new ObjectName(objectName));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to register mbean", e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBeanRegistryImpl.class);

	private MBeanServer _mBeanServer;
	private final Map<String, ObjectName> _objectNameCache =
		new ConcurrentHashMap<>();

}