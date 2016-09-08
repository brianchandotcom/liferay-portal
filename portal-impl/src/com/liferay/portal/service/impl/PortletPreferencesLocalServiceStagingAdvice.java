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

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.exportimport.staging.ProxiedLayoutsThreadLocal;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Raymond Augé
 */
public class PortletPreferencesLocalServiceStagingAdvice
	implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (!StagingAdvicesThreadLocal.isEnabled()) {
			return methodInvocation.proceed();
		}

		try {
			Object[] arguments = methodInvocation.getArguments();

			if (arguments == null) {
				return methodInvocation.proceed();
			}

			Method method = methodInvocation.getMethod();

			String methodName = method.getName();

			if (methodName.equals("getPortletPreferences") &&
				((arguments.length == 2) || (arguments.length == 3) ||
				 (arguments.length == 4))) {

				return getPortletPreferences(methodInvocation);
			}
			else if (methodName.equals("updatePreferences")) {
				return updatePreferences(methodInvocation);
			}

			return methodInvocation.proceed();
		}
		catch (InvocationTargetException ite) {
			throw ite.getCause();
		}
		catch (Throwable throwable) {
			throw throwable;
		}
	}

	protected LayoutRevision getLayoutRevision(long plid) {
		if (plid <= 0) {
			return null;
		}

		LayoutRevision layoutRevision = LayoutRevisionUtil.fetchByPrimaryKey(
			plid);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout == null) {
			return null;
		}

		if (!LayoutStagingUtil.isBranchingLayout(layout)) {
			return null;
		}

		return LayoutStagingUtil.getLayoutRevision(layout);
	}

	protected Object getPortletPreferences(MethodInvocation methodInvocation)
		throws Throwable {

		Object[] arguments = methodInvocation.getArguments();

		int index = -1;

		if ((arguments.length == 2) && (arguments[0] instanceof Long) &&
			(arguments[1] instanceof String)) {

			index = 0;
		}
		else if ((arguments.length == 3) && (arguments[0] instanceof Integer) &&
				 (arguments[1] instanceof Long) &&
				 (arguments[2] instanceof String)) {

			index = 1;
		}
		else if (((arguments.length == 3) || (arguments.length == 4)) &&
				 (arguments[2] instanceof Long)) {

			index = 2;
		}

		long plid = 0;

		if (index >= 0) {
			plid = (Long)arguments[index];
		}

		LayoutRevision layoutRevision = getLayoutRevision(plid);

		if (layoutRevision == null) {
			return methodInvocation.proceed();
		}

		arguments[index] = layoutRevision.getLayoutRevisionId();

		return methodInvocation.proceed();
	}

	protected Object updatePreferences(MethodInvocation methodInvocation)
		throws Throwable {

		Object[] arguments = methodInvocation.getArguments();

		long plid = (Long)arguments[2];

		LayoutRevision layoutRevision = getLayoutRevision(plid);

		if (layoutRevision == null) {
			return methodInvocation.proceed();
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return methodInvocation.proceed();
		}

		boolean exporting = ParamUtil.getBoolean(serviceContext, "exporting");

		if (exporting) {
			return methodInvocation.proceed();
		}

		if (!MergeLayoutPrototypesThreadLocal.isInProgress()) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		layoutRevision = LayoutRevisionLocalServiceUtil.updateLayoutRevision(
			serviceContext.getUserId(), layoutRevision.getLayoutRevisionId(),
			layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
			layoutRevision.getTitle(), layoutRevision.getDescription(),
			layoutRevision.getKeywords(), layoutRevision.getRobots(),
			layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
			layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
			layoutRevision.getColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		arguments[2] = layoutRevision.getLayoutRevisionId();

		ProxiedLayoutsThreadLocal.clearProxiedLayouts();

		return methodInvocation.proceed();
	}

}