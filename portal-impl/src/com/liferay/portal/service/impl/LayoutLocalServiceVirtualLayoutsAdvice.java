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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.sites.util.SitesUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
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

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (MergeLayoutPrototypesThreadLocal.isInProgress()) {
			return methodInvocation.proceed();
		}

		Method method = methodInvocation.getMethod();

		String methodName = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();

		Object[] arguments = methodInvocation.getArguments();

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		if (methodName.equals("getLayout") &&
			(Arrays.equals(parameterTypes, _TYPES_L) ||
			 Arrays.equals(parameterTypes, _TYPES_L_B_L))) {

			Layout layout = (Layout)methodInvocation.proceed();

			Group group = layout.getGroup();

			if (isMergeComplete(method, arguments, group)) {
				return layout;
			}

			if ((Validator.isNull(layout.getLayoutPrototypeUuid()) &&
				 Validator.isNull(layout.getSourcePrototypeLayoutUuid())) ||
				!layout.getLayoutPrototypeLinkEnabled()) {

				return layout;
			}

			LayoutSet layoutSet = layout.getLayoutSet();

			try {
				WorkflowThreadLocal.setEnabled(false);

				SitesUtil.mergeLayoutPrototypeLayout(group, layout);

				if (Validator.isNotNull(
						layout.getSourcePrototypeLayoutUuid())) {

					SitesUtil.mergeLayoutSetPrototypeLayouts(group, layoutSet);
				}
			}
			finally {
				MergeLayoutPrototypesThreadLocal.setMergeComplete(
					method, arguments);
				WorkflowThreadLocal.setEnabled(workflowEnabled);
			}
		}
		else if (methodName.equals("getLayouts") &&
				 (Arrays.equals(parameterTypes, _TYPES_L_B_L) ||
				  Arrays.equals(parameterTypes, _TYPES_L_B_L_B_I_I))) {

			long groupId = (Long)arguments[0];

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (isMergeComplete(method, arguments, group)) {
				return methodInvocation.proceed();
			}

			boolean privateLayout = (Boolean)arguments[1];
			long parentLayoutId = (Long)arguments[2];

			try {
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);

				mergeLayoutSetPrototypeLayouts(
					method, arguments, group, layoutSet, privateLayout,
					workflowEnabled);

				List<Layout> layouts = (List<Layout>)methodInvocation.proceed();

				if (PropsValues.
						USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {

					return layouts;
				}

				if (group.isUser()) {
					_virtualLayoutTargetGroupId.set(group.getGroupId());

					if (parentLayoutId ==
							LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

						return addUserGroupLayouts(
							group, layoutSet, layouts, parentLayoutId);
					}
					else {
						return addChildUserGroupLayouts(group, layouts);
					}
				}
				else if (group.isUserGroup() &&
						 (parentLayoutId !=
							 LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)) {

					long targetGroupId = _virtualLayoutTargetGroupId.get();

					if (targetGroupId != GroupConstants.DEFAULT_LIVE_GROUP_ID) {
						Group targetGroup = GroupLocalServiceUtil.getGroup(
							targetGroupId);

						return addChildUserGroupLayouts(targetGroup, layouts);
					}
				}

				return layouts;
			}
			catch (Exception e) {
				_log.error(e, e);

				throw e;
			}
		}

		return methodInvocation.proceed();
	}

	protected List<Layout> addChildUserGroupLayouts(
			Group group, List<Layout> layouts)
		throws Exception {

		layouts = ListUtil.copy(layouts);

		List<Layout> childLayouts = new ArrayList<Layout>();

		for (Layout layout : layouts) {
			Layout childLayout = layout;

			Group layoutGroup = layout.getGroup();

			if (layoutGroup.isUserGroup()) {
				childLayout = new VirtualLayout(layout, group);
			}

			childLayouts.add(childLayout);
		}

		return childLayouts;
	}

	protected List<Layout> addUserGroupLayouts(
			Group group, LayoutSet layoutSet, List<Layout> layouts,
			long parentLayoutId)
		throws Exception {

		layouts = ListUtil.copy(layouts);

		List<UserGroup> userUserGroups =
			UserGroupLocalServiceUtil.getUserUserGroups(group.getClassPK());

		for (UserGroup userGroup : userUserGroups) {
			Group userGroupGroup = userGroup.getGroup();

			List<Layout> userGroupLayouts = LayoutLocalServiceUtil.getLayouts(
				userGroupGroup.getGroupId(), layoutSet.isPrivateLayout(),
				parentLayoutId);

			for (Layout userGroupLayout : userGroupLayouts) {
				Layout virtualLayout = new VirtualLayout(
					userGroupLayout, group);

				layouts.add(virtualLayout);
			}
		}

		return layouts;
	}

	protected List<Layout> getPrototypeLinkedLayouts(
		long groupId, boolean privateLayout) {

		Class<?> clazz = getClass();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Layout.class, clazz.getClassLoader());

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		Property layoutPrototypeUuidProperty = PropertyFactoryUtil.forName(
			"layoutPrototypeUuid");

		dynamicQuery.add(layoutPrototypeUuidProperty.isNotNull());

		Property privateLayoutProperty = PropertyFactoryUtil.forName(
			"privateLayout");

		dynamicQuery.add(privateLayoutProperty.eq(privateLayout));

		Property sourcePrototypeLayoutUuidProperty =
			PropertyFactoryUtil.forName("sourcePrototypeLayoutUuid");

		dynamicQuery.add(sourcePrototypeLayoutUuidProperty.isNotNull());

		return LayoutLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

	protected boolean isMergeComplete(
		Method method, Object[] arguments, Group group) {

		if (MergeLayoutPrototypesThreadLocal.isMergeComplete(
				method, arguments) &&
			(!group.isUser() ||
			 PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected void mergeLayoutSetPrototypeLayouts(
		Method method, Object[] arguments, Group group, LayoutSet layoutSet,
		boolean privateLayout, boolean workflowEnabled) {

		try {
			if (!SitesUtil.isLayoutSetMergeable(group, layoutSet)) {
				return;
			}

			WorkflowThreadLocal.setEnabled(false);

			SitesUtil.mergeLayoutSetPrototypeLayouts(group, layoutSet);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to merge layouts for site template", e);
			}
		}
		finally {
			MergeLayoutPrototypesThreadLocal.setMergeComplete(
				method, arguments);
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	private static final Class<?>[] _TYPES_L = {Long.TYPE};

	private static final Class<?>[] _TYPES_L_B_L = {
		Long.TYPE, Boolean.TYPE, Long.TYPE
	};

	private static final Class<?>[] _TYPES_L_B_L_B_I_I = {
		Long.TYPE, Boolean.TYPE, Long.TYPE, Boolean.TYPE, Integer.TYPE,
		Integer.TYPE
	};

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutLocalServiceVirtualLayoutsAdvice.class);

	private static final ThreadLocal<Long> _virtualLayoutTargetGroupId =
		new AutoResetThreadLocal<Long>(
			LayoutLocalServiceVirtualLayoutsAdvice.class +
				"._virtualLayoutTargetGroupId",
			GroupConstants.DEFAULT_LIVE_GROUP_ID);

}