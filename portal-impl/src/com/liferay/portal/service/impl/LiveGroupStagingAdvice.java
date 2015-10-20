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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Tomas Polesovsky
 */
public abstract class LiveGroupStagingAdvice implements MethodInterceptor {

	public void initCustomMethod(Method method, Integer[] argumentIndexes) {
		_customMethods.put(method, argumentIndexes);
	}

	public void initGroupServiceBuilderMethods(Class relatedEntityClass) {
		if (Validator.isNull(relatedEntityClass)) {
			return;
		}

		String className = relatedEntityClass.getName();
		String relatedEntityName = className.substring(
			0, className.length() - _LOCAL_SERVICE.length());

		Method[] relatedEntityMethods = relatedEntityClass.getMethods();

		String[] templates = getServiceBuilderTemplates();

		for (String template : templates) {
			String serviceBuilderMethodName = StringUtil.replace(
				template, "$1", relatedEntityName);

			for (Method method : relatedEntityMethods) {
				if (method.getName().equals(serviceBuilderMethodName)) {
					_serviceBuilderGeneratedMethods.add(method);
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

	protected String[] getServiceBuilderTemplates() {
		return SERVICE_BUILDER_GENERATED_GROUP_TEMPLATES;
	}

	protected int getServiceBuilderTemplatesArgumentIndex() {
		return 0;
	}

	protected Group replace(Group group) {
		if ((group != null) && group.isStagingGroup() &&
			!group.isStagedRemotely()) {

			return group.getLiveGroup();
		}

		return group;
	}

	protected long replace(long groupId) {
		if (groupId == 0) {
			return groupId;
		}

		Group group = groupLocalService.fetchGroup(groupId);

		if ((group != null) && group.isStagingGroup() &&
			!group.isStagedRemotely()) {

			groupId = group.getLiveGroupId();
		}

		return groupId;
	}

	protected void replace(Object[] arguments, int index) {
		if (arguments == null) {
			return;
		}

		Object object = arguments[index];

		if (object == null) {
			return;
		}

		if (object instanceof Long) {
			long groupId = (Long)object;

			arguments[index] = replace(groupId);

			return;
		}

		if (object instanceof Group) {
			Group group = (Group)object;

			arguments[index] = replace(group);

			return;
		}

		if (object instanceof long[]) {
			long[] groupIds = (long[])object;

			for (int i = 0; i < groupIds.length; i++) {
				groupIds[i] = replace(groupIds[i]);
			}

			return;
		}

		if (object instanceof Long[]) {
			Long[] groupIds = (Long[])object;

			for (int i = 0; i < groupIds.length; i++) {
				groupIds[i] = replace(groupIds[i]);
			}

			return;
		}

		if (object instanceof Group[]) {
			Group[] groups = (Group[])object;

			for (int i = 0; i < groups.length; i++) {
				groups[i] = replace(groups[i]);
			}

			return;
		}

		if (object instanceof Collection) {
			Collection collection = (Collection)object;

			if (collection.size() == 0) {
				return;
			}

			Iterator it = collection.iterator();

			if (it.hasNext() && (it.next() instanceof Group)) {
				ArrayList<Group> groups = new ArrayList(collection);

				collection.clear();

				for (Group group : groups) {
					collection.add(replace(group));
				}

				return;
			}
		}

		throw new IllegalArgumentException("Unknown type " + object.getClass());
	}

	protected void replaceStagingGroupIdsInCustomMethod(
		MethodInvocation methodInvocation) {

		Method method = methodInvocation.getMethod();

		Integer[] argumentIndexes = _customMethods.get(method);

		if (argumentIndexes == null) {
			return;
		}

		Object[] arguments = methodInvocation.getArguments();

		for (Integer argumentIndex : argumentIndexes) {
			replace(arguments, argumentIndex);
		}
	}

	protected void replaceStagingGroupIdsInServiceBuilderGeneratedMethod(
		MethodInvocation methodInvocation) {

		Method method = methodInvocation.getMethod();

		if (!_serviceBuilderGeneratedMethods.contains(method)) {
			return;
		}

		Object[] arguments = methodInvocation.getArguments();

		replace(arguments, getServiceBuilderTemplatesArgumentIndex());
	}

	protected static final String[] SERVICE_BUILDER_GENERATED_GROUP_TEMPLATES =
		new String[] {
			"addGroup$1", "addGroup$1s", "clearGroup$1s", "deleteGroup$1",
			"deleteGroup$1s", "getGroup$1s", "getGroup$1sCount", "hasGroup$1",
			"hasGroup$1s", "setGroup$1s", "unsetGroup$1s"
		};

	@BeanReference(type = GroupLocalService.class)
	protected GroupLocalService groupLocalService;

	private static final String _LOCAL_SERVICE = "LocalService";

	private final Map<Method, Integer[]> _customMethods = new HashMap<>();
	private final List<Method> _serviceBuilderGeneratedMethods =
		new ArrayList<>();

}