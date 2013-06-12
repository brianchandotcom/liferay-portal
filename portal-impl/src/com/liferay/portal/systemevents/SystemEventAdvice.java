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

package com.liferay.portal.systemevents;

import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.systemevents.SystemEvent;
import com.liferay.portal.kernel.systemevents.SystemEventHierarchyEntry;
import com.liferay.portal.kernel.systemevents.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.TypedModel;
import com.liferay.portal.service.SystemEventLocalServiceUtil;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Zsolt Berentey
 */
public class SystemEventAdvice
	extends AnnotationChainableMethodAdvice<SystemEvent> {

	@Override
	public void afterReturning(MethodInvocation methodInvocation, Object result)
		throws Throwable {

		SystemEvent systemEvent = findAnnotation(methodInvocation);

		if ((systemEvent == _nullSystemEvent) || !systemEvent.sendEvent()) {
			return;
		}

		if (!isValid(methodInvocation, false)) {
			return;
		}

		ClassedModel argument =
			(ClassedModel)methodInvocation.getArguments()[0];

		String className = getClassName(argument);

		String referrerClassName = null;

		if (argument instanceof TypedModel) {
			referrerClassName = ((TypedModel)argument).getClassName();
		}

		long classPK = getClassPK(argument);
		long groupId = getGroupId(argument);

		SystemEventHierarchyEntry hierarchyEntry =
			SystemEventHierarchyEntryThreadLocal.peek();

		if ((hierarchyEntry != null) &&
			hierarchyEntry.isCurrentAsset(className, classPK)) {

			if (groupId > 0) {
				SystemEventLocalServiceUtil.addSystemEvent(
					0, getGroupId(argument), hierarchyEntry.getClassName(),
					getClassPK(argument), hierarchyEntry.getUuid(),
					referrerClassName, systemEvent.type(),
					hierarchyEntry.getExtraData());
			}
			else {
				SystemEventLocalServiceUtil.addSystemEvent(
					getCompanyId(argument), hierarchyEntry.getClassName(),
					getClassPK(argument), hierarchyEntry.getUuid(),
					referrerClassName, systemEvent.type(),
					hierarchyEntry.getExtraData());
			}
		}
		else if (groupId > 0) {
			SystemEventLocalServiceUtil.addSystemEvent(
				0, getGroupId(argument), className, getClassPK(argument),
				getUuid(argument), referrerClassName, systemEvent.type(),
				StringPool.BLANK);
		}
		else {
			SystemEventLocalServiceUtil.addSystemEvent(
				getCompanyId(argument), className, getClassPK(argument),
				getUuid(argument), referrerClassName, systemEvent.type(),
				StringPool.BLANK);
		}
	}

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		SystemEvent systemEvent = findAnnotation(methodInvocation);

		if (systemEvent == _nullSystemEvent) {
			return null;
		}

		if (systemEvent.childAction() != SystemEventConstants.ACTION_NONE) {
			if (!isValid(methodInvocation, true)) {
				return null;
			}

			ClassedModel argument =
				(ClassedModel)methodInvocation.getArguments()[0];

			SystemEventHierarchyEntry hierarchyEntry =
				SystemEventHierarchyEntryThreadLocal.push(
					getClassName(argument), getClassPK(argument),
					systemEvent.childAction());

			if (hierarchyEntry != null) {
				hierarchyEntry.setUuid(getUuid(argument));
			}
		}

		return null;
	}

	@Override
	public void duringFinally(MethodInvocation methodInvocation) {
		SystemEvent systemEvent = findAnnotation(methodInvocation);

		if (systemEvent == _nullSystemEvent) {
			return;
		}

		Method method = methodInvocation.getMethod();

		Class<?>[] parameterTypes = method.getParameterTypes();

		if (parameterTypes.length == 0) {
			return;
		}

		Class<?> parameter = parameterTypes[0];

		if (!GroupedModel.class.isAssignableFrom(parameter)) {
			return;
		}

		if (systemEvent.childAction() != SystemEventConstants.ACTION_NONE) {
			SystemEventHierarchyEntry hierarchyEntry =
				SystemEventHierarchyEntryThreadLocal.peek();

			if (hierarchyEntry != null) {
				Object argument = methodInvocation.getArguments()[0];

				long classPK = getClassPK(argument);

				if (classPK == 0) {
					return;
				}

				String className = getClassName((ClassedModel)argument);

				if (hierarchyEntry.isCurrentAsset(className, classPK)) {
					SystemEventHierarchyEntryThreadLocal.pop();
				}
			}
		}
	}

	@Override
	public SystemEvent getNullAnnotation() {
		return _nullSystemEvent;
	}

	protected String getClassName(ClassedModel argument) {
		String className = argument.getModelClassName();

		if (argument instanceof StagedModel) {
			StagedModelType stagedModelType =
				((StagedModel)argument).getStagedModelType();

			className = stagedModelType.getClassName();
		}

		return className;
	}

	protected long getClassPK(Object argument) {
		if (!(argument instanceof ClassedModel)) {
			return 0;
		}

		Serializable primaryKeyObj =
			((ClassedModel)argument).getPrimaryKeyObj();

		if (!(primaryKeyObj instanceof Long)) {
			return 0;
		}

		return (Long)primaryKeyObj;
	}

	protected long getCompanyId(Object argument) {
		if (argument instanceof GroupedModel) {
			return ((GroupedModel)argument).getCompanyId();
		}

		if (argument instanceof AuditedModel) {
			return ((AuditedModel)argument).getCompanyId();
		}

		if (argument instanceof StagedModel) {
			return ((StagedModel)argument).getCompanyId();
		}

		return 0;
	}

	protected long getGroupId(Object argument) {
		if (!(argument instanceof GroupedModel)) {
			return 0;
		}

		return ((GroupedModel)argument).getGroupId();
	}

	protected String getUuid(ClassedModel argument) throws Exception {
		String uuid;

		if (argument instanceof StagedModel) {
			uuid = ((StagedModel)argument).getUuid();
		}
		else {
			Class<?> modelClass = argument.getClass();

			Method uuidMethod = modelClass.getMethod("getUuid", new Class[0]);

			if (uuidMethod != null) {
				uuid = (String)uuidMethod.invoke(argument, new Object[0]);
			}
			else {
				uuid = StringPool.BLANK;
			}
		}

		return uuid;
	}

	protected boolean isValid(
		MethodInvocation methodInvocation, boolean beforeCall) {

		Method method = methodInvocation.getMethod();

		Class<?>[] parameterTypes = method.getParameterTypes();

		if (parameterTypes.length == 0) {
			if (_log.isWarnEnabled() && beforeCall) {
				_log.warn(
					"The method " + methodInvocation +
						" must have at least one parameter");
			}

			return false;
		}

		Class<?> parameter = parameterTypes[0];

		if (!ClassedModel.class.isAssignableFrom(parameter)) {
			if (_log.isWarnEnabled() && beforeCall) {
				_log.warn(
					"The first parameter of " + methodInvocation +
						" must implement ClassedModel");
			}

			return false;
		}

		ClassedModel argument =
			(ClassedModel)methodInvocation.getArguments()[0];

		if (!(argument.getPrimaryKeyObj() instanceof Long)) {
			if (_log.isWarnEnabled() && beforeCall) {
				_log.warn(
					"The first parameter of " + methodInvocation +
						" must have a long primary key");
			}

			return false;
		}

		if (beforeCall) {
			return true;
		}

		if (!GroupedModel.class.isAssignableFrom(parameter) &&
			!AuditedModel.class.isAssignableFrom(parameter) &&
			!StagedModel.class.isAssignableFrom(parameter)) {

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("The first parameter of ");
				sb.append(methodInvocation);
				sb.append(" must implement one of GroupedMode, AuditedModel");
				sb.append(" or StagedModel");

				_log.warn(sb.toString());
			}

			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(SystemEventAdvice.class);

	private static SystemEvent _nullSystemEvent =
		new SystemEvent() {

			@Override
			public int childAction() {
				return 0;
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				return SystemEvent.class;
			}

			@Override
			public boolean sendEvent() {
				return false;
			}

			@Override
			public int type() {
				return 0;
			}

		};

}