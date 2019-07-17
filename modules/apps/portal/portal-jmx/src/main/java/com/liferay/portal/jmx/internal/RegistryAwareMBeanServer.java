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

import java.io.ObjectInputStream;

import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.loading.ClassLoaderRepository;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = MBeanServer.class)
public class RegistryAwareMBeanServer implements MBeanServer {

	@Override
	public void addNotificationListener(
			ObjectName objectName, NotificationListener notificationListener,
			NotificationFilter notificationFilter, Object handback)
		throws InstanceNotFoundException {

		_mBeanServer.addNotificationListener(
			getPlatformObjectName(objectName), notificationListener,
			notificationFilter, handback);
	}

	@Override
	public void addNotificationListener(
			ObjectName objectName, ObjectName listenerObjectName,
			NotificationFilter notificationFilter, Object handback)
		throws InstanceNotFoundException {

		ObjectName platformObjectName = getPlatformObjectName(objectName);
		ObjectName platformListenerObjectName = getPlatformObjectName(
			listenerObjectName);

		_mBeanServer.addNotificationListener(
			platformObjectName, platformListenerObjectName, notificationFilter,
			handback);
	}

	@Override
	public ObjectInstance createMBean(String className, ObjectName objectName)
		throws InstanceAlreadyExistsException, MBeanException,
			   NotCompliantMBeanException, ReflectionException {

		return _mBeanServer.createMBean(className, objectName);
	}

	@Override
	public ObjectInstance createMBean(
			String className, ObjectName objectName, Object[] params,
			String[] signature)
		throws InstanceAlreadyExistsException, MBeanException,
			   NotCompliantMBeanException, ReflectionException {

		return _mBeanServer.createMBean(
			className, objectName, params, signature);
	}

	@Override
	public ObjectInstance createMBean(
			String className, ObjectName objectName, ObjectName loaderName)
		throws InstanceAlreadyExistsException, InstanceNotFoundException,
			   MBeanException, NotCompliantMBeanException, ReflectionException {

		return _mBeanServer.createMBean(className, objectName, loaderName);
	}

	@Override
	public ObjectInstance createMBean(
			String className, ObjectName objectName,
			ObjectName loaderObjectName, Object[] params, String[] signature)
		throws InstanceAlreadyExistsException, InstanceNotFoundException,
			   MBeanException, NotCompliantMBeanException, ReflectionException {

		return _mBeanServer.createMBean(
			className, objectName, loaderObjectName, params, signature);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public ObjectInputStream deserialize(ObjectName objectName, byte[] data)
		throws OperationsException {

		return _mBeanServer.deserialize(
			getPlatformObjectName(objectName), data);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public ObjectInputStream deserialize(String className, byte[] data)
		throws OperationsException, ReflectionException {

		return _mBeanServer.deserialize(className, data);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public ObjectInputStream deserialize(
			String className, ObjectName loaderObjectName, byte[] data)
		throws OperationsException, ReflectionException {

		return _mBeanServer.deserialize(className, loaderObjectName, data);
	}

	@Override
	public Object getAttribute(ObjectName objectName, String attribute)
		throws AttributeNotFoundException, InstanceNotFoundException,
			   MBeanException, ReflectionException {

		return _mBeanServer.getAttribute(
			getPlatformObjectName(objectName), attribute);
	}

	@Override
	public AttributeList getAttributes(
			ObjectName objectName, String[] attributes)
		throws InstanceNotFoundException, ReflectionException {

		return _mBeanServer.getAttributes(
			getPlatformObjectName(objectName), attributes);
	}

	@Override
	public ClassLoader getClassLoader(ObjectName loaderObjectName)
		throws InstanceNotFoundException {

		return _mBeanServer.getClassLoader(loaderObjectName);
	}

	@Override
	public ClassLoader getClassLoaderFor(ObjectName objectName)
		throws InstanceNotFoundException {

		return _mBeanServer.getClassLoaderFor(
			getPlatformObjectName(objectName));
	}

	@Override
	public ClassLoaderRepository getClassLoaderRepository() {
		return _mBeanServer.getClassLoaderRepository();
	}

	@Override
	public String getDefaultDomain() {
		return _mBeanServer.getDefaultDomain();
	}

	@Override
	public String[] getDomains() {
		return _mBeanServer.getDomains();
	}

	@Override
	public Integer getMBeanCount() {
		return _mBeanServer.getMBeanCount();
	}

	@Override
	public MBeanInfo getMBeanInfo(ObjectName objectName)
		throws InstanceNotFoundException, IntrospectionException,
			   ReflectionException {

		return _mBeanServer.getMBeanInfo(getPlatformObjectName(objectName));
	}

	@Override
	public ObjectInstance getObjectInstance(ObjectName objectName)
		throws InstanceNotFoundException {

		return _mBeanServer.getObjectInstance(
			getPlatformObjectName(objectName));
	}

	@Override
	public Object instantiate(String className)
		throws MBeanException, ReflectionException {

		return _mBeanServer.instantiate(className);
	}

	@Override
	public Object instantiate(
			String className, Object[] params, String[] signature)
		throws MBeanException, ReflectionException {

		return _mBeanServer.instantiate(className, params, signature);
	}

	@Override
	public Object instantiate(String className, ObjectName loaderObjectName)
		throws InstanceNotFoundException, MBeanException, ReflectionException {

		return _mBeanServer.instantiate(className, loaderObjectName);
	}

	@Override
	public Object instantiate(
			String className, ObjectName loaderName, Object[] params,
			String[] signature)
		throws InstanceNotFoundException, MBeanException, ReflectionException {

		return _mBeanServer.instantiate(
			className, loaderName, params, signature);
	}

	@Override
	public Object invoke(
			ObjectName objectName, String operationName, Object[] params,
			String[] signature)
		throws InstanceNotFoundException, MBeanException, ReflectionException {

		return _mBeanServer.invoke(
			getPlatformObjectName(objectName), operationName, params,
			signature);
	}

	@Override
	public boolean isInstanceOf(ObjectName objectName, String className)
		throws InstanceNotFoundException {

		return _mBeanServer.isInstanceOf(
			getPlatformObjectName(objectName), className);
	}

	@Override
	public boolean isRegistered(ObjectName objectName) {
		return _mBeanServer.isRegistered(getPlatformObjectName(objectName));
	}

	@Override
	public Set<ObjectInstance> queryMBeans(
		ObjectName objectName, QueryExp queryExp) {

		return _mBeanServer.queryMBeans(objectName, queryExp);
	}

	@Override
	public Set<ObjectName> queryNames(
		ObjectName objectName, QueryExp queryExp) {

		return _mBeanServer.queryNames(objectName, queryExp);
	}

	@Override
	public ObjectInstance registerMBean(Object object, ObjectName objectName)
		throws InstanceAlreadyExistsException, MBeanRegistrationException,
			   NotCompliantMBeanException {

		return _mBeanRegistry.register(
			objectName.getCanonicalName(), object, objectName);
	}

	@Override
	public void removeNotificationListener(
			ObjectName name, NotificationListener notificationListener)
		throws InstanceNotFoundException, ListenerNotFoundException {

		_mBeanServer.removeNotificationListener(
			getPlatformObjectName(name), notificationListener);
	}

	@Override
	public void removeNotificationListener(
			ObjectName objectName, NotificationListener notificationListener,
			NotificationFilter notificationFilter, Object handback)
		throws InstanceNotFoundException, ListenerNotFoundException {

		_mBeanServer.removeNotificationListener(
			getPlatformObjectName(objectName), notificationListener,
			notificationFilter, handback);
	}

	@Override
	public void removeNotificationListener(
			ObjectName objectName, ObjectName listenerObjectName)
		throws InstanceNotFoundException, ListenerNotFoundException {

		ObjectName platformObjectName = getPlatformObjectName(objectName);
		ObjectName platformListenerObjectName = getPlatformObjectName(
			listenerObjectName);

		_mBeanServer.removeNotificationListener(
			platformObjectName, platformListenerObjectName);
	}

	@Override
	public void removeNotificationListener(
			ObjectName objectName, ObjectName listenerObjectName,
			NotificationFilter notificationFilter, Object handback)
		throws InstanceNotFoundException, ListenerNotFoundException {

		ObjectName platformObjectName = getPlatformObjectName(objectName);
		ObjectName platformListenerObjectName = getPlatformObjectName(
			listenerObjectName);

		_mBeanServer.removeNotificationListener(
			platformObjectName, platformListenerObjectName, notificationFilter,
			handback);
	}

	@Override
	public void setAttribute(ObjectName objectName, Attribute attribute)
		throws AttributeNotFoundException, InstanceNotFoundException,
			   InvalidAttributeValueException, MBeanException,
			   ReflectionException {

		_mBeanServer.setAttribute(getPlatformObjectName(objectName), attribute);
	}

	@Override
	public AttributeList setAttributes(
			ObjectName objectName, AttributeList attributeList)
		throws InstanceNotFoundException, ReflectionException {

		return _mBeanServer.setAttributes(
			getPlatformObjectName(objectName), attributeList);
	}

	@Override
	public void unregisterMBean(ObjectName objectName)
		throws InstanceNotFoundException, MBeanRegistrationException {

		_mBeanRegistry.unregister(objectName.getCanonicalName(), objectName);
	}

	protected ObjectName getPlatformObjectName(ObjectName objectName) {
		ObjectName platformObjectName = _mBeanRegistry.getObjectName(
			objectName.getCanonicalName());

		if (platformObjectName == null) {
			platformObjectName = objectName;
		}

		return platformObjectName;
	}

	@Reference(unbind = "-")
	protected void setMBeanRegistry(MBeanRegistry mBeanRegistry) {
		_mBeanRegistry = mBeanRegistry;

		_mBeanServer = _mBeanRegistry.getMBeanServer();
	}

	private MBeanRegistry _mBeanRegistry;
	private MBeanServer _mBeanServer;

}