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

package com.liferay.portal.fabric.status;

import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;

import java.io.Serializable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.PlatformManagedObject;
import java.lang.management.ThreadInfo;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;

/**
 * @author Shuyang Zhou
 */
public class JMXProxyUtil {

	public static <T> T newProxy(
		ObjectName objectName, Class<T> interfaceClass,
		ProcessCallableExecutor processCallableExecutor) {

		ClassLoader classLoader = interfaceClass.getClassLoader();

		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
		}

		return (T)Proxy.newProxyInstance(
			classLoader, new Class<?>[] {interfaceClass},
			new JMXProxyInvocationHandler(objectName, processCallableExecutor));
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Optional {
	}

	public interface ProcessCallableExecutor {

		public <V extends Serializable> NoticeableFuture<V> execute(
			ProcessCallable<V> processCallable);

	}

	protected static Object decode(
		Class<?> decodedClass, Serializable serializable) {

		if (serializable instanceof CompositeData) {
			return decodeCompositeData(
				decodedClass, (CompositeData)serializable);
		}

		if ((serializable instanceof CompositeData[]) &&
			decodedClass.isArray()) {

			return decodeCompositeDataArray(
				decodedClass, (CompositeData[])serializable);
		}

		if (decodedClass == List.class) {
			Class<?> clazz = serializable.getClass();

			if (clazz.isArray()) {
				return decodeArrayToList(serializable);
			}
		}

		return serializable;
	}

	protected static Object decodeArrayToList(Object array) {
		List<Object> list = new ArrayList<>();

		for (int i = 0; i < Array.getLength(array); i++) {
			list.add(Array.get(array, i));
		}

		return list;
	}

	protected static Object decodeCompositeData(
		Class<?> decodedClass, CompositeData compositeData) {

		if (decodedClass == MemoryUsage.class) {
			return MemoryUsage.from(compositeData);
		}

		if (decodedClass == ThreadInfo.class) {
			return ThreadInfo.from(compositeData);
		}

		return compositeData;
	}

	protected static Object decodeCompositeDataArray(
		Class<?> decodedClass, CompositeData[] compositeDatas) {

		Object array = Array.newInstance(
			decodedClass.getComponentType(), compositeDatas.length);

		for (int i = 0; i < compositeDatas.length; i++) {
			Array.set(
				array, i,
				decodeCompositeData(
					decodedClass.getComponentType(), compositeDatas[i]));
		}

		return array;
	}

	protected static boolean equals(ObjectName objectName, Object target) {
		if (target instanceof PlatformManagedObject) {
			PlatformManagedObject platformManagedObject =
				(PlatformManagedObject)target;

			return objectName.equals(platformManagedObject.getObjectName());
		}

		if (Proxy.isProxyClass(target.getClass())) {
			InvocationHandler invocationHandler = Proxy.getInvocationHandler(
				target);

			if (invocationHandler instanceof JMXProxyInvocationHandler) {
				JMXProxyInvocationHandler jmxProxyInvocationHandler =
					(JMXProxyInvocationHandler)invocationHandler;

				return objectName.equals(jmxProxyInvocationHandler._objectName);
			}
		}

		return false;
	}

	protected static boolean isGetGetter(
		String methodName, Class<?>... parameterTypes) {

		if (methodName.startsWith("get") && (parameterTypes.length == 0)) {
			return true;
		}

		return false;
	}

	protected static boolean isIsGetter(
		String methodName, Class<?>... parameterTypes) {

		if (methodName.startsWith("is") && (parameterTypes.length == 0)) {
			return true;
		}

		return false;
	}

	protected static boolean isObjectEquals(Method method) {
		if (method.getDeclaringClass() == Object.class) {
			String methodName = method.getName();

			if (methodName.equals("equals")) {
				return true;
			}
		}

		return false;
	}

	protected static boolean isObjectHashCode(Method method) {
		if (method.getDeclaringClass() == Object.class) {
			String methodName = method.getName();

			if (methodName.equals("hashCode")) {
				return true;
			}
		}

		return false;
	}

	protected static boolean isObjectToString(Method method) {
		if (method.getDeclaringClass() == Object.class) {
			String methodName = method.getName();

			if (methodName.equals("toString")) {
				return true;
			}
		}

		return false;
	}

	protected static boolean isOptional(Method method) {
		if (method.getAnnotation(Optional.class) == null) {
			return false;
		}

		return true;
	}

	protected static boolean isSetter(
		String methodName, Class<?>... parameterTypes) {

		if (methodName.startsWith("set") && (parameterTypes.length == 1)) {
			return true;
		}

		return false;
	}

	protected static class GetAttributeProcessCallable
		implements ProcessCallable<Serializable> {

		public GetAttributeProcessCallable(
			ObjectName objectName, String attributeName, boolean optional) {

			_objectName = objectName;
			_attributeName = attributeName;
			_optional = optional;
		}

		@Override
		public Serializable call() throws ProcessException {
			MBeanServer mBeanServer =
				ManagementFactory.getPlatformMBeanServer();

			try {
				return (Serializable)mBeanServer.getAttribute(
					_objectName, _attributeName);
			}
			catch (AttributeNotFoundException anfe) {
				if (_optional) {
					return null;
				}

				throw new ProcessException(anfe);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}
		}

		private static final long serialVersionUID = 1L;

		private final String _attributeName;
		private final ObjectName _objectName;
		private final boolean _optional;

	}

	protected static class JMXProxyInvocationHandler
		implements InvocationHandler {

		public JMXProxyInvocationHandler(
			ObjectName objectName,
			ProcessCallableExecutor processCallableExecutor) {

			_objectName = objectName;
			_processCallableExecutor = processCallableExecutor;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (isObjectEquals(method)) {
				return JMXProxyUtil.equals(_objectName, args[0]);
			}

			if (isObjectHashCode(method)) {
				return _objectName.hashCode();
			}

			if (isObjectToString(method)) {
				return _objectName.toString();
			}

			String methodName = method.getName();

			Class<?>[] parameterTypes = method.getParameterTypes();

			ProcessCallable<Serializable> processCallable = null;

			if (isGetGetter(methodName, parameterTypes)) {
				processCallable = new GetAttributeProcessCallable(
					_objectName, methodName.substring(3), isOptional(method));
			}
			else if (isIsGetter(methodName, parameterTypes)) {
				processCallable = new GetAttributeProcessCallable(
					_objectName, methodName.substring(2), isOptional(method));
			}
			else if (isSetter(methodName, parameterTypes)) {
				processCallable = new SetAttributeProcessCallable(
					_objectName, methodName.substring(3), (Serializable)args[0],
					isOptional(method));
			}
			else {
				String[] parameterTypeNames = new String[parameterTypes.length];

				for (int i = 0; i < parameterTypes.length; i++) {
					parameterTypeNames[i] = parameterTypes[i].getName();
				}

				processCallable = new OperationProcessCallable(
					_objectName, methodName, args, parameterTypeNames);
			}

			Future<? extends Serializable> future =
				_processCallableExecutor.execute(processCallable);

			return decode(method.getReturnType(), future.get());
		}

		private final ObjectName _objectName;
		private final ProcessCallableExecutor _processCallableExecutor;

	}

	protected static class OperationProcessCallable
		implements ProcessCallable<Serializable> {

		public OperationProcessCallable(
			ObjectName objectName, String operationName, Object[] arguments,
			String[] parameterTypeNames) {

			_objectName = objectName;
			_operationName = operationName;
			_arguments = arguments;
			_parameterTypeNames = parameterTypeNames;
		}

		@Override
		public Serializable call() throws ProcessException {
			MBeanServer mBeanServer =
				ManagementFactory.getPlatformMBeanServer();

			try {
				return (Serializable)mBeanServer.invoke(
					_objectName, _operationName, _arguments,
					_parameterTypeNames);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}
		}

		private static final long serialVersionUID = 1L;

		private final Object[] _arguments;
		private final ObjectName _objectName;
		private final String _operationName;
		private final String[] _parameterTypeNames;

	}

	protected static class SetAttributeProcessCallable
		implements ProcessCallable<Serializable> {

		public SetAttributeProcessCallable(
			ObjectName objectName, String attributeName,
			Serializable attributeValue, boolean optional) {

			_objectName = objectName;
			_attributeName = attributeName;
			_attributeValue = attributeValue;
			_optional = optional;
		}

		@Override
		public Serializable call() throws ProcessException {
			MBeanServer mBeanServer =
				ManagementFactory.getPlatformMBeanServer();

			try {
				mBeanServer.setAttribute(
					_objectName,
					new Attribute(_attributeName, _attributeValue));
			}
			catch (AttributeNotFoundException anfe) {
				if (!_optional) {
					throw new ProcessException(anfe);
				}
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		private static final long serialVersionUID = 1L;

		private final String _attributeName;
		private final Serializable _attributeValue;
		private final ObjectName _objectName;
		private final boolean _optional;

	}

}