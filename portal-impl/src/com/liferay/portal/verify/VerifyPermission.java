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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl;
import com.liferay.portal.util.PortalInstances;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Tobias Kaefer
 * @author Douglas Wong
 * @author Matthew Kong
 * @author Raymond Augé
 */
public class VerifyPermission extends VerifyProcess {

	protected void checkPermissions() throws Exception {
		List<String> modelNames = ResourceActionsUtil.getModelNames();
		int total = 0;

		ExecutorService executorService = null;

		try {
			for (String modelName : modelNames) {
				List<String> actionIds =
					ResourceActionsUtil.getModelResourceActions(modelName);
				List<String> newActionIds = new ArrayList<String>();

				for (String actionId : actionIds) {
					ResourceAction resourceAction =
						ResourceActionLocalServiceUtil.fetchResourceAction(
							modelName, actionId);

					if (resourceAction == null) {
						newActionIds.add(actionId);
					}
				}

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, actionIds);

				if (newActionIds.isEmpty()) {
					continue;
				}

				total = total + fixResourcePermissions(
					modelName, newActionIds, executorService);
			}
		}
		finally {
			if (executorService != null) {
				executorService.shutdown();
			}
		}

		if (total == 0) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Fixed " + total + " resource permissions");
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			try {
				deleteDefaultPrivateLayoutPermissions_6(companyId);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions_6(long companyId)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(
				role.getRoleId());

		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (isPrivateLayout(
					resourcePermission.getName(),
					resourcePermission.getPrimKey())) {

				ResourcePermissionLocalServiceUtil.deleteResourcePermission(
					resourcePermission.getResourcePermissionId());
			}
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteDefaultPrivateLayoutPermissions();

		checkPermissions();
		fixOrganizationRolePermissions();
	}

	protected void fixOrganizationRolePermissions() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ResourcePermission.class);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("name", Organization.class.getName()));

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			ResourcePermission groupResourcePermission = null;

			try {
				groupResourcePermission =
					ResourcePermissionLocalServiceUtil.getResourcePermission(
						resourcePermission.getCompanyId(),
						Group.class.getName(), resourcePermission.getScope(),
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId());
			}
			catch (Exception e) {
				ResourcePermissionLocalServiceUtil.setResourcePermissions(
					resourcePermission.getCompanyId(), Group.class.getName(),
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(),
					ResourcePermissionLocalServiceImpl.EMPTY_ACTION_IDS);

				groupResourcePermission =
					ResourcePermissionLocalServiceUtil.getResourcePermission(
						resourcePermission.getCompanyId(),
						Group.class.getName(), resourcePermission.getScope(),
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId());
			}

			long organizationActions = resourcePermission.getActionIds();
			long groupActions = groupResourcePermission.getActionIds();

			for (Object[] actionIdToMask : _ORGANIZATION_ACTION_IDS_TO_MASKS) {
				long organizationActionMask = (Long)actionIdToMask[1];
				long groupActionMask = (Long)actionIdToMask[2];

				if ((organizationActions & organizationActionMask) ==
						organizationActionMask) {

					organizationActions =
						organizationActions & (~organizationActionMask);
					groupActions = groupActions | groupActionMask;
				}
			}

			try {
				resourcePermission.resetOriginalValues();

				resourcePermission.setActionIds(organizationActions);

				ResourcePermissionLocalServiceUtil.updateResourcePermission(
					resourcePermission, false);

				groupResourcePermission.resetOriginalValues();
				groupResourcePermission.setActionIds(groupActions);

				ResourcePermissionLocalServiceUtil.updateResourcePermission(
					groupResourcePermission, false);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	protected int fixResourcePermissions(
			String modelName, String roleName, String[] actionIds)
		throws Exception {

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();
		int total = 0;

		for (long companyId : companyIds) {
			Role role = RoleLocalServiceUtil.getRole(companyId, roleName);

			int count =
				ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
					companyId, modelName, ResourceConstants.SCOPE_INDIVIDUAL,
					role.getRoleId());

			if (count == 0) {
				continue;
			}

			StringBundler sb = new StringBundler(10);

			sb.append(StringPool.OPEN_CURLY_BRACE);
			sb.append("companyId=");
			sb.append(companyId);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append("model=");
			sb.append(modelName);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append("role=");
			sb.append(roleName);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			String key = sb.toString();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Fixing " + count + " resource permissions for key " + key);
			}

			total = total + count;

			int pages = count / _FIX_RESOURCE_PERMISSION_INTERVAL;

			for (int i = 0; i <= pages; i++) {
				int start = (i * _FIX_RESOURCE_PERMISSION_INTERVAL);
				int end = start + _FIX_RESOURCE_PERMISSION_INTERVAL;

				ResourcePermissionLocalServiceUtil.addResourcePermissions(
					role, modelName, ResourceConstants.SCOPE_INDIVIDUAL,
					actionIds, start, end);

				if (i == pages) {
					continue;
				}

				if (_log.isInfoEnabled()) {
					sb.setIndex(0);

					sb.append("Fixed ");
					sb.append(start);
					sb.append(" - ");
					sb.append(end);
					sb.append(" resource permissions for key ");
					sb.append(key);

					_log.info(sb.toString());
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Fixed " + count + " resource permissions for key " + key);
			}
		}

		return total;
	}

	protected int fixResourcePermissions(
			String modelName, List<String> newActionIds,
			ExecutorService executorService)
		throws Exception {

		List<String> groupDefaultNewActionIds = ListUtil.copy(newActionIds);
		List<String> guestDefaultNewActionIds = ListUtil.copy(newActionIds);
		List<String> ownerDefaultNewActionIds = ListUtil.copy(newActionIds);

		groupDefaultNewActionIds.retainAll(
			ResourceActionsUtil.getModelResourceGuestDefaultActions(modelName));
		guestDefaultNewActionIds.retainAll(
			ResourceActionsUtil.getModelResourceGuestDefaultActions(modelName));

		List<Callable<Integer>> callables = new ArrayList<Callable<Integer>>();

		if (!groupDefaultNewActionIds.isEmpty()) {
			String[] actionIds = groupDefaultNewActionIds.toArray(
				new String[groupDefaultNewActionIds.size()]);

			callables.add(new FixResourcePermissionCallable(
				modelName, RoleConstants.SITE_MEMBER, actionIds));
		}

		if (!guestDefaultNewActionIds.isEmpty()) {
			String[] actionIds = guestDefaultNewActionIds.toArray(
				new String[guestDefaultNewActionIds.size()]);

			callables.add(new FixResourcePermissionCallable(
				modelName, RoleConstants.GUEST, actionIds));
		}

		if (!ownerDefaultNewActionIds.isEmpty()) {
			String[] actionIds = ownerDefaultNewActionIds.toArray(
				new String[ownerDefaultNewActionIds.size()]);

			callables.add(new FixResourcePermissionCallable(
				modelName, RoleConstants.OWNER, actionIds));
		}

		List<Future<Integer>> results = new ArrayList<Future<Integer>>();

		executorService = Executors.newFixedThreadPool(callables.size());

		for (Callable<Integer> callable : callables) {
			results.add(executorService.submit(callable));
		}

		int count = 0;

		for (Future<Integer> result : results) {
			count = count + result.get();
		}

		if (count == 0) {
			return 0;
		}

		if (_log.isInfoEnabled()) {
			if (!groupDefaultNewActionIds.isEmpty()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Added ");
				sb.append(StringUtil.merge(groupDefaultNewActionIds));
				sb.append(" to ");
				sb.append(RoleConstants.SITE_MEMBER);
				sb.append(" for ");
				sb.append(modelName);

				_log.info(sb.toString());
			}

			if (!guestDefaultNewActionIds.isEmpty()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Added ");
				sb.append(StringUtil.merge(guestDefaultNewActionIds));
				sb.append(" to ");
				sb.append(RoleConstants.GUEST);
				sb.append(" for ");
				sb.append(modelName);

				_log.info(sb.toString());
			}

			if (!ownerDefaultNewActionIds.isEmpty()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Added ");
				sb.append(StringUtil.merge(ownerDefaultNewActionIds));
				sb.append(" to ");
				sb.append(RoleConstants.OWNER);
				sb.append(" for ");
				sb.append(modelName);

				_log.info(sb.toString());
			}

			_log.info(
				"Fixed " + count + " resource permissions for " + modelName);
		}

		return count;
	}

	protected boolean isPrivateLayout(String name, String primKey)
		throws Exception {

		if (!name.equals(Layout.class.getName())) {
			return false;
		}

		long plid = GetterUtil.getLong(primKey);

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (layout.isPublicLayout() || layout.isTypeControlPanel()) {
			return false;
		}

		return true;
	}

	private class FixResourcePermissionCallable implements Callable<Integer> {

		public FixResourcePermissionCallable(
			String modelName, String roleName, String[] actionIds) {

			_modelName = modelName;
			_roleName = roleName;
			_actionIds = actionIds;
		}

		public Integer call() throws Exception {
			return fixResourcePermissions(_modelName, _roleName, _actionIds);
		}

		private String[] _actionIds;
		private String _modelName;
		private String _roleName;

	}

	private static final int _FIX_RESOURCE_PERMISSION_INTERVAL = 1000;

	private static final Object[][] _ORGANIZATION_ACTION_IDS_TO_MASKS =
		new Object[][] {
			new Object[] {"APPROVE_PROPOSAL", 2L, 0L},
			new Object[] {ActionKeys.ASSIGN_MEMBERS, 4L, 4L},
			new Object[] {"ASSIGN_REVIEWER", 8L, 0L},
			new Object[] {ActionKeys.MANAGE_ARCHIVED_SETUPS, 128L, 128L},
			new Object[] {ActionKeys.MANAGE_LAYOUTS, 256L, 256L},
			new Object[] {ActionKeys.MANAGE_STAGING, 512L, 512L},
			new Object[] {ActionKeys.MANAGE_TEAMS, 2048L, 1024L},
			new Object[] {ActionKeys.PUBLISH_STAGING, 16384L, 4096L}
		};

	private static Log _log = LogFactoryUtil.getLog(VerifyPermission.class);

}