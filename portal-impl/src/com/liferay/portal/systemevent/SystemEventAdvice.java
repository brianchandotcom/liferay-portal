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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.TypedModel;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.SystemEventLocalServiceUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntry;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class SystemEventAdvice extends ChainableMethodAdvice {

	@Override
	public Object before(
			AopMethodInvocation aopMethodInvocation, Object[] arguments)
		throws Throwable {

		SystemEvent systemEvent = aopMethodInvocation.getAdviceMethodContext();

		if (systemEvent.action() != SystemEventConstants.ACTION_NONE) {
			if (!isValid(aopMethodInvocation, arguments, _PHASE_BEFORE)) {
				return null;
			}

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
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		return annotations.get(SystemEvent.class);
	}

	@Override
	protected void afterReturning(
			AopMethodInvocation aopMethodInvocation, Object[] arguments,
			Object result)
		throws Throwable {

		SystemEvent systemEvent = aopMethodInvocation.getAdviceMethodContext();

		if (!systemEvent.send() ||
			!isValid(aopMethodInvocation, arguments, _PHASE_AFTER_RETURNING)) {

			return;
		}

		ClassedModel classedModel = (ClassedModel)arguments[0];

		long groupId = getGroupId(classedModel);

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

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

			if (group != null) {
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
		else if (group != null) {
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
	protected void duringFinally(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {

		SystemEvent systemEvent = aopMethodInvocation.getAdviceMethodContext();

		if (!isValid(aopMethodInvocation, arguments, _PHASE_DURING_FINALLY) ||
			(systemEvent.action() == SystemEventConstants.ACTION_NONE)) {

			return;
		}

		ClassedModel classedModel = (ClassedModel)arguments[0];

		long classPK = getClassPK(classedModel);

		if (classPK == 0) {
			return;
		}

		SystemEventHierarchyEntryThreadLocal.pop(
			getClassName(classedModel), classPK);
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

			getUuidMethod = modelClass.getMethod("getUuid", new Class<?>[0]);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return StringPool.BLANK;
		}

		return (String)getUuidMethod.invoke(classedModel, new Object[0]);
	}

	protected boolean isValid(
		AopMethodInvocation aopMethodInvocation, Object[] arguments,
		int phase) {

		Method method = aopMethodInvocation.getMethod();

		Class<?>[] parameterTypes = method.getParameterTypes();

		if (parameterTypes.length == 0) {
			if (_log.isDebugEnabled() && (phase == _PHASE_BEFORE)) {
				_log.debug(
					"The method " + aopMethodInvocation +
						" must have at least one parameter");
			}

			return false;
		}

		Class<?> parameterType = parameterTypes[0];

		if (!ClassedModel.class.isAssignableFrom(parameterType)) {
			if (_log.isDebugEnabled() && (phase == _PHASE_BEFORE)) {
				_log.debug(
					"The first parameter of " + aopMethodInvocation +
						" must implement ClassedModel");
			}

			return false;
		}

		ClassedModel classedModel = (ClassedModel)arguments[0];

		if ((classedModel == null) ||
			!(classedModel.getPrimaryKeyObj() instanceof Long)) {

			if (_log.isDebugEnabled() && (phase == _PHASE_BEFORE)) {
				_log.debug(
					"The first parameter of " + aopMethodInvocation +
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
				_log.debug(
					StringBundler.concat(
						"If send is true, the first parameter of ",
						aopMethodInvocation,
						" must implement AuditedModel, GroupedModel, or ",
						"StagedModel"));
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

}