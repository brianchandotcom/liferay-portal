/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.sites.util.SitesUtil;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.core.annotation.Order;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
@Order(2)
public class LayoutLocalServiceVirtualLayoutsAdvice
	implements MethodInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) ||
			MergeLayoutPrototypesThreadLocal.isInProgress()) {

			return methodInvocation.proceed();
		}

		Method method = methodInvocation.getMethod();

		String methodName = method.getName();

		Object[] arguments = methodInvocation.getArguments();

		Class<?>[] parameterTypes = method.getParameterTypes();

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		if (methodName.equals("getLayout") &&
			(Arrays.equals(parameterTypes, _TYPES_L) ||
			 Arrays.equals(parameterTypes, _TYPES_L_B_L))) {

			Layout layout = (Layout)methodInvocation.proceed();

			if (Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {
				return layout;
			}

			Group group = layout.getGroup();
			LayoutSet layoutSet = layout.getLayoutSet();

			try {
				MergeLayoutPrototypesThreadLocal.setInProgress(true);
				WorkflowThreadLocal.setEnabled(false);

				SitesUtil.mergeLayoutProtypeLayout(group, layout);
				SitesUtil.mergeLayoutSetProtypeLayouts(group, layoutSet);
			}
			finally {
				MergeLayoutPrototypesThreadLocal.setInProgress(false);
				WorkflowThreadLocal.setEnabled(workflowEnabled);
			}
		}
		else if (methodName.equals("getLayouts") &&
				 (Arrays.equals(parameterTypes, _TYPES_L_B_L) ||
				  Arrays.equals(parameterTypes, _TYPES_L_B_L_B_I_I))) {

			long groupId = (Long)arguments[0];
			boolean privateLayout = (Boolean)arguments[1];
			long parentLayoutId = (Long)arguments[2];

			try {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);

				try {
					MergeLayoutPrototypesThreadLocal.setInProgress(true);
					WorkflowThreadLocal.setEnabled(false);

					SitesUtil.mergeLayoutSetProtypeLayouts(group, layoutSet);
				}
				finally {
					MergeLayoutPrototypesThreadLocal.setInProgress(false);
					WorkflowThreadLocal.setEnabled(workflowEnabled);
				}

				if (!PropsValues.
						USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE &&
					group.isUser() &&
					(parentLayoutId ==
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)) {

					Object returnValue = methodInvocation.proceed();

					return addUserGroupLayouts(
						group, layoutSet, (List<Layout>)returnValue);
				}
			}
			catch (Exception e) {
				_log.error(e, e);

				throw e;
			}
		}

		return methodInvocation.proceed();
	}

	protected List<Layout> addUserGroupLayouts(
			Group group, LayoutSet layoutSet, List<Layout> layouts)
		throws Exception {

		layouts = ListUtil.copy(layouts);

		List<UserGroup> userUserGroups =
			UserGroupLocalServiceUtil.getUserUserGroups(group.getClassPK());

		for (UserGroup userGroup : userUserGroups) {
			Group userGroupGroup = userGroup.getGroup();

			List<Layout> userGroupLayouts = LayoutLocalServiceUtil.getLayouts(
				userGroupGroup.getGroupId(), layoutSet.isPrivateLayout());

			for (Layout userGroupLayout : userGroupLayouts) {
				Layout virtualLayout = new VirtualLayout(
					userGroupLayout, group);

				layouts.add(virtualLayout);
			}
		}

		return layouts;
	}

	private static final Class<?>[] _TYPES_L = {Long.TYPE};

	private static final Class<?>[] _TYPES_L_B_L = {
		Long.TYPE, Boolean.TYPE, Long.TYPE
	};

	private static final Class<?>[] _TYPES_L_B_L_B_I_I = {
		Long.TYPE, Boolean.TYPE, Long.TYPE, Boolean.TYPE, Integer.TYPE,
		Integer.TYPE
	};

	private static Log _log = LogFactoryUtil.getLog(
		LayoutLocalServiceVirtualLayoutsAdvice.class);

}