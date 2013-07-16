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

		if (!isValid(methodInvocation, PHASE_AFTER_RETURNING)) {
			return;
		}

		Object[] arguments = methodInvocation.getArguments();

		ClassedModel classedModel = (ClassedModel)arguments[0];

		String className = getClassName(classedModel);
		String referrerClassName = null;

		if (classedModel instanceof TypedModel) {
			referrerClassName = ((TypedModel)classedModel).getClassName();
		}

		long classPK = getClassPK(classedModel);
		long companyId = getCompanyId(classedModel);
		long groupId = getGroupId(classedModel);

		SystemEventHierarchyEntry systemEventHierarchyEntry =
			SystemEventHierarchyEntryThreadLocal.peek();

		if ((systemEventHierarchyEntry != null) &&
			systemEventHierarchyEntry.isCurrentAsset(className, classPK)) {

			if (groupId > 0) {
				SystemEventLocalServiceUtil.addSystemEvent(
					0, groupId, systemEventHierarchyEntry.getClassName(),
					classPK, systemEventHierarchyEntry.getUuid(),
					referrerClassName, systemEvent.type(),
					systemEventHierarchyEntry.getExtraData());
			}
			else {
				SystemEventLocalServiceUtil.addSystemEvent(
					companyId, systemEventHierarchyEntry.getClassName(),
					classPK, systemEventHierarchyEntry.getUuid(),
					referrerClassName, systemEvent.type(),
					systemEventHierarchyEntry.getExtraData());
			}
		}
		else if (groupId > 0) {
			SystemEventLocalServiceUtil.addSystemEvent(
				0, groupId, className, classPK, getUuid(classedModel),
				referrerClassName, systemEvent.type(), StringPool.BLANK);
		}
		else {
			SystemEventLocalServiceUtil.addSystemEvent(
				companyId, className, classPK, getUuid(classedModel),
				referrerClassName, systemEvent.type(), StringPool.BLANK);
		}
	}

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		SystemEvent systemEvent = findAnnotation(methodInvocation);

		if (systemEvent == _nullSystemEvent) {
			return null;
		}

		if (systemEvent.childAction() != SystemEventConstants.ACTION_NONE) {
			if (!isValid(methodInvocation, PHASE_BEFORE)) {
				return null;
			}

			Object[] arguments = methodInvocation.getArguments();

			ClassedModel classedModel = (ClassedModel)arguments[0];

			SystemEventHierarchyEntry systemEventHierarchyEntry =
				SystemEventHierarchyEntryThreadLocal.push(
					getClassName(classedModel), getClassPK(classedModel),
					systemEvent.childAction());

			if (systemEventHierarchyEntry != null) {
				systemEventHierarchyEntry.setUuid(getUuid(classedModel));
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

		if (!isValid(methodInvocation, PHASE_DURING_FINALLY)) {
			return;
		}

		if (systemEvent.childAction() != SystemEventConstants.ACTION_NONE) {
			SystemEventHierarchyEntry systemEventHierarchyEntry =
				SystemEventHierarchyEntryThreadLocal.peek();

			if (systemEventHierarchyEntry != null) {
				Object[] arguments = methodInvocation.getArguments();

				ClassedModel classedModel = (ClassedModel)arguments[0];

				long classPK = getClassPK(classedModel);

				if (classPK == 0) {
					return;
				}

				if (systemEventHierarchyEntry.isCurrentAsset(
						getClassName(classedModel), classPK)) {

					SystemEventHierarchyEntryThreadLocal.pop();
				}
			}
		}
	}

	@Override
	public SystemEvent getNullAnnotation() {
		return _nullSystemEvent;
	}

	protected String getClassName(ClassedModel classedModel) {
		String className = classedModel.getModelClassName();

		if (classedModel instanceof StagedModel) {
			StagedModelType stagedModelType =
				((StagedModel)classedModel).getStagedModelType();

			className = stagedModelType.getClassName();
		}

		return className;
	}

	protected long getClassPK(Object object) {
		if (!(object instanceof ClassedModel)) {
			return 0;
		}

		Serializable primaryKeyObj = ((ClassedModel)object).getPrimaryKeyObj();

		if (!(primaryKeyObj instanceof Long)) {
			return 0;
		}

		return (Long)primaryKeyObj;
	}

	protected long getCompanyId(Object object) {
		if (object instanceof GroupedModel) {
			return ((GroupedModel)object).getCompanyId();
		}

		if (object instanceof AuditedModel) {
			return ((AuditedModel)object).getCompanyId();
		}

		if (object instanceof StagedModel) {
			return ((StagedModel)object).getCompanyId();
		}

		return 0;
	}

	protected long getGroupId(Object object) {
		if (!(object instanceof GroupedModel)) {
			return 0;
		}

		return ((GroupedModel)object).getGroupId();
	}

	protected String getUuid(ClassedModel classedModel) throws Exception {
		String uuid;

		if (classedModel instanceof StagedModel) {
			uuid = ((StagedModel)classedModel).getUuid();
		}
		else {
			Class<?> modelClass = classedModel.getClass();

			Method uuidMethod = modelClass.getMethod("getUuid", new Class[0]);

			if (uuidMethod != null) {
				uuid = (String)uuidMethod.invoke(classedModel, new Object[0]);
			}
			else {
				uuid = StringPool.BLANK;
			}
		}

		return uuid;
	}

	protected boolean isValid(MethodInvocation methodInvocation, int phase) {
		Method method = methodInvocation.getMethod();

		Class<?>[] parameterTypes = method.getParameterTypes();

		if (parameterTypes.length == 0) {
			if (_log.isDebugEnabled() && (phase == PHASE_BEFORE)) {
				_log.debug(
					"The method " + methodInvocation +
						" must have at least one parameter");
			}

			return false;
		}

		Class<?> parameter = parameterTypes[0];

		if (!ClassedModel.class.isAssignableFrom(parameter)) {
			if (_log.isDebugEnabled() && (phase == PHASE_BEFORE)) {
				_log.debug(
					"The first parameter of " + methodInvocation +
						" must implement ClassedModel");
			}

			return false;
		}

		Object[] arguments = methodInvocation.getArguments();

		ClassedModel classedModel = (ClassedModel)arguments[0];

		if (!(classedModel.getPrimaryKeyObj() instanceof Long)) {
			if (_log.isDebugEnabled() && (phase == PHASE_BEFORE)) {
				_log.debug(
					"The first parameter of " + methodInvocation +
						" must have a long primary key");
			}

			return false;
		}

		if (phase != PHASE_AFTER_RETURNING) {
			return true;
		}

		if (!GroupedModel.class.isAssignableFrom(parameter) &&
			!AuditedModel.class.isAssignableFrom(parameter) &&
			!StagedModel.class.isAssignableFrom(parameter)) {

			if (_log.isDebugEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("If sendEvent is true, the first parameter of ");
				sb.append(methodInvocation);
				sb.append(" must implement one of GroupedModel, AuditedModel");
				sb.append(" or StagedModel");

				_log.debug(sb.toString());
			}

			return false;
		}

		return true;
	}

	private static final int PHASE_AFTER_RETURNING = 1;

	private static final int PHASE_BEFORE = 0;

	private static final int PHASE_DURING_FINALLY = 2;

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