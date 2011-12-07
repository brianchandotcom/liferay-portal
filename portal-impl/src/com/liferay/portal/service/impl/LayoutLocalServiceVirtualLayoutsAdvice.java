/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

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
 */
@Order(2)
public class LayoutLocalServiceVirtualLayoutsAdvice
	implements MethodInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();

		String methodName = method.getName();

		Object[] arguments = methodInvocation.getArguments();

		Class<?>[] parameterTypes = method.getParameterTypes();

		if ((methodName.equals("getLayouts") &&
			 Arrays.equals(parameterTypes, _TYPES_L_B_L)) ||
			 Arrays.equals(parameterTypes, _TYPES_L_B_L_B_I_I)) {

			long groupId = (Long)arguments[0];
			boolean privateLayout = (Boolean)arguments[1];
			long parentLayoutId = (Long)arguments[2];

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			try {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);

				mergeLayoutSetProtypeLayouts(
					permissionChecker, group, layoutSet);

				if (!PropsValues.
						USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE &&
					group.isUser() &&
					(parentLayoutId ==
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)) {

					Object returnValue = methodInvocation.proceed();

					return addUserGroupLayouts(
						permissionChecker, group, layoutSet,
						(List<Layout>)returnValue);
				}
			}
			catch (Exception e) {
				_log.error(e, e);

				throw e;
			}
		}
		else if (methodName.equals("getLayout") &&
			 (Arrays.equals(parameterTypes, _TYPES_L) ||
			  Arrays.equals(parameterTypes, _TYPES_L_B_L))) {

			Layout layout = (Layout)methodInvocation.proceed();

			if (Validator.isNull(layout.getTemplateLayoutUuid())) {
				return layout;
			}

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			Group group = layout.getGroup();
			LayoutSet layoutSet = layout.getLayoutSet();

			mergeLayoutSetProtypeLayouts(
				permissionChecker, group, layoutSet);
		}

		return methodInvocation.proceed();
	}

	protected List<Layout> addUserGroupLayouts(
			PermissionChecker permissionChecker, Group group,
			LayoutSet layoutSet, List<Layout> layouts)
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
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_INHERITED,
			new String[] {Boolean.TRUE.toString()});
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

	protected long getOwnerUserId(
			PermissionChecker permissionChecker,
			LayoutSetPrototype layoutSetPrototype)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				layoutSetPrototype.getCompanyId(),
				LayoutSetPrototype.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()),
				permissionChecker.getOwnerRoleId());

		return resourcePermission.getOwnerId();
	}

	protected void importLayoutSetPrototype(
			PermissionChecker permissionChecker,
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

		long userId = getOwnerUserId(permissionChecker, layoutSetPrototype);

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

	protected void mergeLayoutSetProtypeLayouts(
			PermissionChecker permissionChecker, Group group,
			LayoutSet layoutSet)
		throws Exception {

		if ((permissionChecker == null) || !permissionChecker.isSignedIn() ||
			!layoutSet.getLayoutSetPrototypeLinkEnabled() ||
			Validator.isNull(layoutSet.getLayoutSetPrototypeUuid()) ||
			group.isLayoutPrototype() || group.isLayoutSetPrototype()) {

			return;
		}

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(
				layoutSet.getLayoutSetPrototypeUuid());

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		long lastMergeTime = GetterUtil.getLong(
			settingsProperties.getProperty("last-merge-time"));

		Date modifiedDate = layoutSetPrototype.getModifiedDate();

		if (lastMergeTime >= modifiedDate.getTime()) {
			return;
		}

		Map<String, String[]> parameterMap = null;

		if (lastMergeTime > 0) {
			parameterMap = getLayoutTemplatesParameters(false);
		}
		else {
			parameterMap = getLayoutTemplatesParameters(true);
		}

		importLayoutSetPrototype(
			permissionChecker, layoutSetPrototype, layoutSet.getGroupId(),
			layoutSet.isPrivateLayout(), parameterMap);

		settingsProperties.setProperty(
			"last-merge-time", String.valueOf(modifiedDate.getTime()));

		LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet, false);
	}

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/layout_set_prototype/";

	private static final Class<?>[] _TYPES_L = new Class<?>[]{Long.TYPE};

	private static final Class<?>[] _TYPES_L_B_L = new Class<?>[] {
		Long.TYPE, Boolean.TYPE, Long.TYPE
	};

	private static final Class<?>[] _TYPES_L_B_L_B_I_I = new Class<?>[] {
		Long.TYPE, Boolean.TYPE, Long.TYPE, Boolean.TYPE, Integer.TYPE,
		Integer.TYPE
	};

	private static Log _log = LogFactoryUtil.getLog(
		LayoutLocalServiceVirtualLayoutsAdvice.class);

}