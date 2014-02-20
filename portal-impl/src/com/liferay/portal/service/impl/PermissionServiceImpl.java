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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.PermissionServiceBaseImpl;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.TeamPermissionUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service for checking permissions.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PermissionServiceImpl extends PermissionServiceBaseImpl {

	/**
	 * Checks to see if the group has permission to the service.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the service name
	 * @param  primKey the primary key of the service
	 * @throws PortalException if the group did not have permission to the
	 *         service, if a group with the primary key could not be found or if
	 *         the permission information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void checkPermission(long groupId, String name, long primKey)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, name, String.valueOf(primKey));
	}

	/**
	 * Checks to see if the group has permission to the service.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the service name
	 * @param  primKey the primary key of the service
	 * @throws PortalException if the group did not have permission to the
	 *         service, if a group with the primary key could not be found or if
	 *         the permission information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void checkPermission(long groupId, String name, String primKey)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, name, primKey);
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, long groupId, String name,
			String primKey)
		throws PortalException, SystemException {

		boolean isCheckedModel = _checkModelPermission(
			permissionChecker, groupId, name, primKey);

		if (isCheckedModel) {
			return;
		}

		if ((primKey != null) &&
			primKey.contains(PortletConstants.LAYOUT_SEPARATOR)) {

			int pos = primKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

			long plid = GetterUtil.getLong(primKey.substring(0, pos));

			String portletId = primKey.substring(
				pos + PortletConstants.LAYOUT_SEPARATOR.length());

			PortletPermissionUtil.check(
				permissionChecker, plid, portletId, ActionKeys.CONFIGURATION);
		}
		else if (!permissionChecker.hasPermission(
					groupId, name, primKey, ActionKeys.PERMISSIONS)) {

			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(name);

			if (assetRendererFactory != null) {
				try {
					if (assetRendererFactory.hasPermission(
							permissionChecker, GetterUtil.getLong(primKey),
							ActionKeys.PERMISSIONS)) {

						return;
					}
				}
				catch (Exception e) {
				}
			}

			long ownerId = 0;

			if (resourceBlockLocalService.isSupported(name)) {
				PermissionedModel permissionedModel =
					resourceBlockLocalService.getPermissionedModel(
						name, GetterUtil.getLong(primKey));

				if (permissionedModel instanceof GroupedModel) {
					GroupedModel groupedModel = (GroupedModel)permissionedModel;

					ownerId = groupedModel.getUserId();
				}
				else if (permissionedModel instanceof AuditedModel) {
					AuditedModel auditedModel = (AuditedModel)permissionedModel;

					ownerId = auditedModel.getUserId();
				}
			}
			else {
				ResourcePermission resourcePermission =
					resourcePermissionLocalService.getResourcePermission(
						permissionChecker.getCompanyId(), name,
						ResourceConstants.SCOPE_INDIVIDUAL, primKey,
						permissionChecker.getOwnerRoleId());

				ownerId = resourcePermission.getOwnerId();
			}

			if (permissionChecker.hasOwnerPermission(
					permissionChecker.getCompanyId(), name, primKey, ownerId,
					ActionKeys.PERMISSIONS)) {

				return;
			}

			Role role = null;

			if (name.equals(Role.class.getName())) {
				long roleId = GetterUtil.getLong(primKey);

				role = rolePersistence.findByPrimaryKey(roleId);
			}

			if ((role != null) && role.isTeam()) {
				Team team = teamPersistence.findByPrimaryKey(role.getClassPK());

				TeamPermissionUtil.check(
					permissionChecker, team, ActionKeys.PERMISSIONS);
			}
			else {
				List<String> resourceActions =
					ResourceActionsUtil.getResourceActions(name);

				if (!resourceActions.contains(ActionKeys.DEFINE_PERMISSIONS) ||
					!permissionChecker.hasPermission(
						groupId, name, primKey,
						ActionKeys.DEFINE_PERMISSIONS)) {

					throw new PrincipalException();
				}
			}
		}
	}

	private boolean _checkModelPermission(
			PermissionChecker permissionChecker, long groupId,
			String modelResource, String resourcePrimKey)
		throws PortalException, SystemException {

		String actionId = ActionKeys.PERMISSIONS;

		long primKey = GetterUtil.getLong(resourcePrimKey);

		if (Team.class.getName().equals(modelResource)) {
			actionId = ActionKeys.MANAGE_TEAMS;
			groupId = teamLocalService.fetchTeam(primKey).getGroupId();
			modelResource = Group.class.getName();
		}

		BaseModelPermissionChecker entityPermissionChecker =
			_entityPermissionCheckerMap.get(
				modelResource + ".permission.checker");

		if (entityPermissionChecker != null) {
			entityPermissionChecker.checkPermission(
				permissionChecker, groupId, primKey, actionId);

			return true;
		}

		return false;
	}

	@BeanReference(name = "entityPermissionCheckers")
	private Map<String, BaseModelPermissionChecker> _entityPermissionCheckerMap;

}