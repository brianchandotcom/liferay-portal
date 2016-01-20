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

package com.liferay.staging.security.spring;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManagerUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Tomas Polesovsky
 */
public abstract class LiveGroupStagingAdvice implements MethodInterceptor {

	public LiveGroupStagingAdvice(Class localServiceClass) {
		_localServiceClass = localServiceClass;
	}

	public void checkCoverage(List<String> falsePositivesList) {
		Method[] methods = _localServiceClass.getMethods();

		for (Method method : methods) {
			String methodName = method.getName();
			String methodNameWithoutUserGroup = StringUtil.replace(
				methodName, "UserGroup", "");

			if (methodNameWithoutUserGroup.contains("Group")) {
				if (_serviceBuilderGeneratedMethods.contains(method)) {
					continue;
				}

				if (_customMethods.containsKey(method)) {
					continue;
				}

				if (falsePositivesList.contains(methodName)) {
					continue;
				}

				_log.error(
					"Please update " + getClass().getName() + " with " +
					method + ". The method must be processed when it " +
					"contains groupId, otherwise it's safe to add the method " +
					"name to " + getClass().getSimpleName() +
					"._GROUP_METHODS_WHITELIST");
			}
		}
	}

	public void initCustomMethod(
			String methodName, Integer index, Class... parameterTypes)
		throws NoSuchMethodException {

		Method customMethod = _localServiceClass.getMethod(
			methodName, parameterTypes);

		if (_log.isDebugEnabled()) {
			_log.debug("Registering " + customMethod);
		}

		_customMethods.put(customMethod, index);
	}

	public void initGroupServiceBuilderMethods() {
		String className = _localServiceClass.getSimpleName();
		String relatedEntityName = className.substring(
			0, className.length() - _LOCAL_SERVICE.length());

		Method[] relatedEntityMethods = _localServiceClass.getMethods();

		for (String template : _SERVICE_BUILDER_GENERATED_GROUP_TEMPLATES) {
			String serviceBuilderMethodName = StringUtil.replace(
				template, "$1", relatedEntityName);

			for (Method method : relatedEntityMethods) {
				if (method.getName().equals(serviceBuilderMethodName)) {
					_serviceBuilderGeneratedMethods.add(method);

					if (_log.isDebugEnabled()) {
						_log.debug("Registering " + method);
					}
				}
			}
		}
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (!StagingAdvicesThreadLocal.isEnabled()) {
			return methodInvocation.proceed();
		}

		replaceStagingGroupIdsInServiceBuilderGeneratedMethod(methodInvocation);

		replaceStagingGroupIdsInCustomMethod(methodInvocation);

		return methodInvocation.proceed();
	}

	protected void registerAdvice(Object service) {
		try {
			Class<?> clazz = service.getClass();

			if (!ProxyUtil.isProxyClass(clazz)) {
				throw new RuntimeException(
					"Unable to register advice " + getClass().getName() +
						" for " + _localServiceClass.getName() +
						", Spring bean " + clazz.getName() +
						" is not proxy");
			}

			AdvisedSupport advisedSupport =
				ServiceBeanAopProxy.getAdvisedSupport(service);

			advisedSupport.addAdvice(this);

			ServiceBeanAopCacheManagerUtil.reset();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to register advice for " +
					_localServiceClass.getName(),
				e);
		}
	}

	protected void replaceArgument(Object[] arguments, int index) {
		if (arguments == null) {
			return;
		}

		Object object = arguments[index];

		if (object == null) {
			return;
		}

		if (object instanceof Long) {
			long groupId = (Long)object;

			arguments[index] = replaceGroupIdArgument(groupId);

			return;
		}

		if (object instanceof Group) {
			Group group = (Group)object;

			arguments[index] = replaceGroupArgument(group);

			return;
		}

		if (object instanceof long[]) {
			long[] groupIds = (long[])object;

			for (int i = 0; i < groupIds.length; i++) {
				groupIds[i] = replaceGroupIdArgument(groupIds[i]);
			}

			return;
		}

		if (object instanceof Long[]) {
			Long[] groupIds = (Long[])object;

			for (int i = 0; i < groupIds.length; i++) {
				groupIds[i] = replaceGroupIdArgument(groupIds[i]);
			}

			return;
		}

		if (object instanceof Group[]) {
			Group[] groups = (Group[])object;

			for (int i = 0; i < groups.length; i++) {
				groups[i] = replaceGroupArgument(groups[i]);
			}

			return;
		}

		if (object instanceof Collection) {
			Collection collection = (Collection)object;

			if (collection.size() == 0) {
				return;
			}

			Object[] collectionArray = collection.toArray(
				new Object[collection.size()]);

			for (int i = 0; i < collectionArray.length; i++) {
				replaceArgument(collectionArray, i);
			}

			if (object instanceof List) {
				arguments[index] = new ArrayList();
			}
			else if (object instanceof Set) {
				arguments[index] = new LinkedHashSet<>();
			}
			else {
				throw new UnsupportedOperationException(
					"Unknown collection type " + object.getClass());
			}

			Collection newCollection = (Collection)arguments[index];

			for (Object group : collectionArray) {
				newCollection.add(group);
			}

			return;
		}

		throw new UnsupportedOperationException(
			"Unknown type " + object.getClass());
	}

	protected Group replaceGroupArgument(Group group) {
		if (group == null) {
			return group;
		}

		return StagingUtil.getLiveGroup(group.getGroupId());
	}

	protected long replaceGroupIdArgument(long groupId) {
		if (groupId == 0) {
			return groupId;
		}

		return StagingUtil.getLiveGroupId(groupId);
	}

	protected void replaceStagingGroupIdsInCustomMethod(
		MethodInvocation methodInvocation) {

		Method method = methodInvocation.getMethod();

		Integer argumentIndex = _customMethods.get(method);

		if (argumentIndex == null) {
			return;
		}

		Object[] arguments = methodInvocation.getArguments();

		replaceArgument(arguments, argumentIndex);
	}

	protected void replaceStagingGroupIdsInServiceBuilderGeneratedMethod(
		MethodInvocation methodInvocation) {

		Method method = methodInvocation.getMethod();

		if (!_serviceBuilderGeneratedMethods.contains(method)) {
			return;
		}

		Object[] arguments = methodInvocation.getArguments();

		replaceArgument(arguments, 0);
	}

	protected void unregisterAdvice(Object service) {
		Class<?> clazz = service.getClass();

		if (!ProxyUtil.isProxyClass(clazz)) {
			return;
		}

		try {
			AdvisedSupport advisedSupport =
				ServiceBeanAopProxy.getAdvisedSupport(service);

			advisedSupport.removeAdvice(this);

			ServiceBeanAopCacheManagerUtil.reset();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to register advice " + getClass().getName() +
					" for " + _localServiceClass.getName(),
				e);
		}
	}

	private static final String _LOCAL_SERVICE = "LocalService";

	private static final String[] _SERVICE_BUILDER_GENERATED_GROUP_TEMPLATES =
		new String[] {
			"addGroup$1", "addGroup$1s", "clearGroup$1s", "deleteGroup$1",
			"deleteGroup$1s", "getGroup$1s", "getGroup$1sCount", "hasGroup$1",
			"hasGroup$1s", "setGroup$1s", "unsetGroup$1s"
		};

	private static final Log _log = LogFactoryUtil.getLog(
		LiveGroupStagingAdvice.class);

	private final Map<Method, Integer> _customMethods = new HashMap<>();
	private final Class _localServiceClass;
	private final List<Method> _serviceBuilderGeneratedMethods =
		new ArrayList<>();

}