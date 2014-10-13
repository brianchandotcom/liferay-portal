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

package com.liferay.portal.systemevent;

import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntry;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
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

		if ((systemEvent == _nullSystemEvent) || !systemEvent.send()) {
			return;
		}

		if (!isValid(methodInvocation, _PHASE_AFTER_RETURNING)) {
			return;
		}

		Object[] arguments = methodInvocation.getArguments();

		ClassedModel classedModel = (ClassedModel)arguments[0];

		long groupId = getGroupId(classedModel);

		String className = getClassName(classedModel);

		String referrerClassName = null;

		if (classedModel instanceof TypedModel) {
			TypedModel typedModel = (TypedModel)classedModel;

			referrerClassName = typedModel.getClassName();
		}

		long classPK = getClassPK(classedModel);

		SystemEventHierarchyEntry systemEventHierarchyEntry =
			SystemEventHierarchyEntryThreadLocal.peek();

		if ((systemEventHierarchyEntry != null) &&
			systemEventHierarchyEntry.hasTypedModel(className, classPK)) {

			if (groupId > 0) {
				SystemEventLocalServiceUtil.addSystemEvent(
					0, groupId, systemEventHierarchyEntry.getClassName(),
					classPK, systemEventHierarchyEntry.getUuid(),
					referrerClassName, systemEvent.type(),
					systemEventHierarchyEntry.getExtraData());
			}
			else {
				SystemEventLocalServiceUtil.addSystemEvent(
					getCompanyId(classedModel),
					systemEventHierarchyEntry.getClassName(), classPK,
					systemEventHierarchyEntry.getUuid(), referrerClassName,
					systemEvent.type(),
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
				getCompanyId(classedModel), className, classPK,
				getUuid(classedModel), referrerClassName, systemEvent.type(),
				StringPool.BLANK);
		}
	}

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		SystemEvent systemEvent = findAnnotation(methodInvocation);

		if (systemEvent == _nullSystemEvent) {
			return null;
		}

		if (systemEvent.action() != SystemEventConstants.ACTION_NONE) {
			if (!isValid(methodInvocation, _PHASE_BEFORE)) {
				return null;
			}

			Object[] arguments = methodInvocation.getArguments();

			ClassedModel classedModel = (ClassedModel)arguments[0];

			SystemEventHierarchyEntry systemEventHierarchyEntry =
				SystemEventHierarchyEntryThreadLocal.push(
					getClassName(classedModel), getClassPK(classedModel),
					systemEvent.action());

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

		if (!isValid(methodInvocation, _PHASE_DURING_FINALLY)) {
			return;
		}

		if (systemEvent.action() == SystemEventConstants.ACTION_NONE) {
			return;
		}

		Object[] arguments = methodInvocation.getArguments();

		ClassedModel classedModel = (ClassedModel)arguments[0];

		long classPK = getClassPK(classedModel);

		if (classPK == 0) {
			return;
		}

		SystemEventHierarchyEntryThreadLocal.pop(
			getClassName(classedModel), classPK);
	}

	@Override
	public SystemEvent getNullAnnotation() {
		return _nullSystemEvent;
	}

	protected String getClassName(ClassedModel classedModel) {
		String className = classedModel.getModelClassName();

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			StagedModelType stagedModelType = stagedModel.getStagedModelType();

			className = stagedModelType.getClassName();
		}

		return className;
	}

	protected long getClassPK(ClassedModel classedModel) {
		Serializable primaryKeyObj = classedModel.getPrimaryKeyObj();

		if (!(primaryKeyObj instanceof Long)) {
			return 0;
		}

		return (Long)primaryKeyObj;
	}

	protected long getCompanyId(ClassedModel classedModel) {
		if (classedModel instanceof AuditedModel) {
			AuditedModel auditedModel = (AuditedModel)classedModel;

			return auditedModel.getCompanyId();
		}

		if (classedModel instanceof GroupedModel) {
			GroupedModel groupedModel = (GroupedModel)classedModel;

			return groupedModel.getCompanyId();
		}

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			return stagedModel.getCompanyId();
		}

		return 0;
	}

	protected long getGroupId(ClassedModel classedModel) {
		if (!(classedModel instanceof GroupedModel)) {
			return 0;
		}

		GroupedModel groupedModel = (GroupedModel)classedModel;

		return groupedModel.getGroupId();
	}

	protected String getUuid(ClassedModel classedModel) throws Exception {
		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			return stagedModel.getUuid();
		}

		Method getUuidMethod = null;

		try {
			Class<?> modelClass = classedModel.getClass();

			getUuidMethod = modelClass.getMethod("getUuid", new Class[0]);
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}

		return (String)getUuidMethod.invoke(classedModel, new Object[0]);
	}

	protected boolean isValid(MethodInvocation methodInvocation, int phase) {
		Method method = methodInvocation.getMethod();

		Class<?>[] parameterTypes = method.getParameterTypes();

		if (parameterTypes.length == 0) {
			if (_log.isDebugEnabled() && (phase == _PHASE_BEFORE)) {
				_log.debug(
					"The method " + methodInvocation +
						" must have at least one parameter");
			}

			return false;
		}

		Class<?> parameterType = parameterTypes[0];

		if (!ClassedModel.class.isAssignableFrom(parameterType)) {
			if (_log.isDebugEnabled() && (phase == _PHASE_BEFORE)) {
				_log.debug(
					"The first parameter of " + methodInvocation +
						" must implement ClassedModel");
			}

			return false;
		}

		Object[] arguments = methodInvocation.getArguments();

		ClassedModel classedModel = (ClassedModel)arguments[0];

		if (!(classedModel.getPrimaryKeyObj() instanceof Long)) {
			if (_log.isDebugEnabled() && (phase == _PHASE_BEFORE)) {
				_log.debug(
					"The first parameter of " + methodInvocation +
						" must be a long");
			}

			return false;
		}

		if (phase != _PHASE_AFTER_RETURNING) {
			return true;
		}

		if (!AuditedModel.class.isAssignableFrom(parameterType) &&
			!GroupedModel.class.isAssignableFrom(parameterType) &&
			!StagedModel.class.isAssignableFrom(parameterType)) {

			if (_log.isDebugEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("If send is true, the first parameter of ");
				sb.append(methodInvocation);
				sb.append(" must implement AuditedModel, GroupedModel, or ");
				sb.append("StagedModel");

				_log.debug(sb.toString());
			}

			return false;
		}

		return true;
	}

	private static final int _PHASE_AFTER_RETURNING = 1;

	private static final int _PHASE_BEFORE = 0;

	private static final int _PHASE_DURING_FINALLY = 2;

	private static final Log _log = LogFactoryUtil.getLog(
		SystemEventAdvice.class);

	private static final SystemEvent _nullSystemEvent = new SystemEvent() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return SystemEvent.class;
		}

		@Override
		public int action() {
			return SystemEventConstants.ACTION_NONE;
		}

		@Override
		public boolean send() {
			return false;
		}

		@Override
		public int type() {
			return 0;
		}

	};

}