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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutSetUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.File;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

		if (permissionChecker == null) {
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

				mergeLayoutProtypeLayout(group, layout);
				mergeLayoutSetProtypeLayouts(group, layoutSet);
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

					mergeLayoutSetProtypeLayouts(group, layoutSet);
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

	protected Map<String, String[]> getLayoutTemplatesParameters(
		boolean firstTime) {

		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE
			});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		if (firstTime) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.TRUE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.TRUE.toString()});
		}
		else {
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.FALSE.toString()});
		}

		return parameterMap;
	}

	protected void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, long groupId,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		File file = null;

		File cacheFile = new File(
			_TEMP_DIR.concat(layoutSetPrototype.getUuid()).concat(".lar"));

		if (cacheFile.exists()) {
			Date modifiedDate = layoutSetPrototype.getModifiedDate();

			if (cacheFile.lastModified() >= modifiedDate.getTime()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Using cached layout set prototype LAR file " +
							cacheFile.getAbsolutePath());
				}

				file = cacheFile;
			}
		}

		boolean newFile = false;

		if (file == null) {
			Group layoutSetPrototypeGroup = layoutSetPrototype.getGroup();

			file = LayoutLocalServiceUtil.exportLayoutsAsFile(
				layoutSetPrototypeGroup.getGroupId(), true, null,
				parameterMap, null, null);

			newFile = true;
		}

		long userId = UserLocalServiceUtil.getDefaultUserId(
			layoutSetPrototype.getCompanyId());

		LayoutLocalServiceUtil.importLayouts(
			userId, groupId, privateLayout, parameterMap, file);

		if (newFile) {
			try {
				FileUtil.copyFile(file, cacheFile);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Copied " + file.getAbsolutePath() + " to " +
							cacheFile.getAbsolutePath());
				}
			}
			catch (Exception e) {
				_log.error(
					"Unable to copy file " + file.getAbsolutePath() + " to " +
						cacheFile.getAbsolutePath(),
					e);
			}
		}
	}

	protected void mergeLayoutProtypeLayout(Group group, Layout layout)
		throws Exception {

		if (!layout.isLayoutPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.isLayoutSetPrototype() ||
			(group.hasStagingGroup())) {

			return;
		}

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		long lastMergeTime = GetterUtil.getLong(
			typeSettingsProperties.getProperty("last-merge-time"));

		LayoutPrototype layoutPrototype =
			LayoutPrototypeLocalServiceUtil.getLayoutPrototypeByUuid(
				layout.getLayoutPrototypeUuid());

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		Date modifiedDate = layoutPrototypeLayout.getModifiedDate();

		if (lastMergeTime >= modifiedDate.getTime()) {
			return;
		}

		UnicodeProperties prototypeTypeSettingsProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			prototypeTypeSettingsProperties.getProperty("merge-fail-count"));

		if (mergeFailCount >
			PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			return;
		}

		String owner = PortalUUIDUtil.generate();

		try {
			Lock lock = LockLocalServiceUtil.lock(
				LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
				String.valueOf(layout.getPlid()), owner, false);

			// Double deep check

			if (!owner.equals(lock.getOwner())) {
				Date createDate = lock.getCreateDate();

				if (((System.currentTimeMillis() - createDate.getTime()) >=
					PropsValues.LAYOUT_PROTOTYPE_MERGE_LOCK_MAX_TIME)) {

					// Acquire lock if the lock is older than the lock max time

					lock = LockLocalServiceUtil.lock(
						LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
						String.valueOf(layout.getPlid()), lock.getOwner(),
						owner, false);

					// Check if acquiring the lock succeeded or if another
					// process has the lock

					if (!owner.equals(lock.getOwner())) {
						return;
					}
				}
				else {
					return;
				}
			}
		}
		catch (Exception e) {
			return;
		}

		try {
			SitesUtil.applyLayoutPrototype(layoutPrototype, layout, true);
		}
		catch (Exception e) {
			_log.error(e, e);

			prototypeTypeSettingsProperties.setProperty(
				"merge-fail-count", String.valueOf(++mergeFailCount));

			// Invoke updateImpl so that we do not trigger the listeners

			LayoutUtil.updateImpl(layoutPrototypeLayout, false);
		}
		finally {
			LockLocalServiceUtil.unlock(
				LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
				String.valueOf(layout.getPlid()), owner, false);
		}
	}

	protected void mergeLayoutSetProtypeLayouts(
			Group group, LayoutSet layoutSet)
		throws Exception {

		if (!layoutSet.isLayoutSetPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.isLayoutSetPrototype()) {

			return;
		}

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		long lastMergeTime = GetterUtil.getLong(
			settingsProperties.getProperty("last-merge-time"));

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(
				layoutSet.getLayoutSetPrototypeUuid());

		Date modifiedDate = layoutSetPrototype.getModifiedDate();

		if (lastMergeTime >= modifiedDate.getTime()) {
			return;
		}

		LayoutSet layoutSetPrototypeLayoutSet =
			layoutSetPrototype.getLayoutSet();

		UnicodeProperties layoutSetPrototypeSettingsProperties =
			layoutSetPrototypeLayoutSet.getSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			layoutSetPrototypeSettingsProperties.getProperty(
				"merge-fail-count"));

		if (mergeFailCount >
				PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			return;
		}

		String owner = PortalUUIDUtil.generate();

		try {
			Lock lock = LockLocalServiceUtil.lock(
				LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
				String.valueOf(layoutSet.getLayoutSetId()), owner, false);

			// Double deep check

			if (!owner.equals(lock.getOwner())) {
				Date createDate = lock.getCreateDate();

				if (((System.currentTimeMillis() - createDate.getTime()) >=
						PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_LOCK_MAX_TIME)) {

					// Acquire lock if the lock is older than the lock max time

					lock = LockLocalServiceUtil.lock(
						LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
						String.valueOf(layoutSet.getLayoutSetId()),
						lock.getOwner(), owner, false);

					// Check if acquiring the lock succeeded or if another
					// process has the lock

					if (!owner.equals(lock.getOwner())) {
						return;
					}
				}
				else {
					return;
				}
			}
		}
		catch (Exception e) {
			return;
		}

		try {
			Map<String, String[]> parameterMap = null;

			if (lastMergeTime > 0) {
				parameterMap = getLayoutTemplatesParameters(false);
			}
			else {
				parameterMap = getLayoutTemplatesParameters(true);
			}

			importLayoutSetPrototype(
				layoutSetPrototype, layoutSet.getGroupId(),
				layoutSet.isPrivateLayout(), parameterMap);

			settingsProperties.setProperty(
				"last-merge-time", String.valueOf(modifiedDate.getTime()));

			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet, false);
		}
		catch (Exception e) {
			_log.error(e, e);

			layoutSetPrototypeSettingsProperties.setProperty(
				"merge-fail-count", String.valueOf(++mergeFailCount));

			// Invoke updateImpl so that we do not trigger the listeners

			LayoutSetUtil.updateImpl(layoutSetPrototypeLayoutSet, false);
		}
		finally {
			LockLocalServiceUtil.unlock(
				LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
				String.valueOf(layoutSet.getLayoutSetId()), owner, false);
		}
	}

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/layout_set_prototype/";

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