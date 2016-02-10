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

import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

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

/**
 * @author Tomas Polesovsky
 */
public abstract class LiveGroupStagingAdvice implements MethodInterceptor {

	public LiveGroupStagingAdvice(Class localServiceClass) {
		_localServiceClass = localServiceClass;
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (!StagingAdvicesThreadLocal.isEnabled()) {
			return methodInvocation.proceed();
		}

		/* noop in this commit */

		return methodInvocation.proceed();
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

			Collection<Object> newCollection;

			if (object instanceof List) {
				newCollection = new ArrayList<>();
			}
			else if (object instanceof Set) {
				newCollection = new LinkedHashSet<>();
			}
			else {
				throw new UnsupportedOperationException(
					"Unknown collection type " + object.getClass());
			}

			Object[] collectionArray = collection.toArray();

			for (int i = 0; i < collectionArray.length; i++) {
				replaceArgument(collectionArray, i);

				newCollection.add(collectionArray[i]);
			}

			arguments[index] = newCollection;

			return;
		}

		throw new UnsupportedOperationException(
			"Unknown type " + object.getClass());
	}

	protected Group replaceGroupArgument(Group group) {
		if (group == null) {
			return group;
		}

		return _staging.getLiveGroup(group.getGroupId());
	}

	protected long replaceGroupIdArgument(long groupId) {
		if (groupId == 0) {
			return groupId;
		}

		return _staging.getLiveGroupId(groupId);
	}

	protected void setStaging(Staging staging) {
		_staging = staging;
	}

	private final Class _localServiceClass;
	private Staging _staging;

}