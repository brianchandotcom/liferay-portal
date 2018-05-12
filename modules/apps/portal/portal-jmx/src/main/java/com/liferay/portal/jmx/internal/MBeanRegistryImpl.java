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

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.jmx.MBeanRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.management.ManagementFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

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
		throws MBeanRegistrationException {

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
	protected void activate(ComponentContext componentContext) {
		_bundleContext = componentContext.getBundleContext();

		_mBeanServer = ManagementFactory.getPlatformMBeanServer();

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext, "(&(jmx.objectname=*)(objectClass=*MBean))",
			new MBeanServiceTrackerCustomizer());
	}

	@Deactivate
	protected void deactivate() throws Exception {
		_serviceTracker.close();

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

	private static final Log _log = LogFactoryUtil.getLog(
		MBeanRegistryImpl.class);

	private BundleContext _bundleContext;
	private MBeanServer _mBeanServer;
	private final Map<String, ObjectName> _objectNameCache =
		new ConcurrentHashMap<>();
	private ServiceTracker<Object, Object> _serviceTracker;

	private class MBeanServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			String objectName = GetterUtil.getString(
				serviceReference.getProperty("jmx.objectname"));

			String objectNameCacheKey = GetterUtil.getString(
				serviceReference.getProperty("jmx.objectname.cache.key"));

			if (Validator.isNull(objectNameCacheKey)) {
				objectNameCacheKey = objectName;
			}

			Object service = _bundleContext.getService(serviceReference);

			try {
				return register(
					objectNameCacheKey, service, new ObjectName(objectName));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to register mbean", e);
				}
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object service) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object service) {

			String objectName = GetterUtil.getString(
				serviceReference.getProperty("jmx.objectname"));

			String objectNameCacheKey = GetterUtil.getString(
				serviceReference.getProperty("jmx.objectname.cache.key"));

			if (Validator.isNull(objectNameCacheKey)) {
				objectNameCacheKey = objectName;
			}

			_bundleContext.ungetService(serviceReference);

			try {
				unregister(objectNameCacheKey, new ObjectName(objectName));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to register mbean", e);
				}
			}
		}

	}

}