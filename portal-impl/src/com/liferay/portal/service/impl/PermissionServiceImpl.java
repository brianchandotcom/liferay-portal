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

import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides the remote service for checking permissions.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PermissionServiceImpl extends PermissionServiceBaseImpl {

	@Override
	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(model.class.name=*)(objectClass=" +
				BaseModelPermissionChecker.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new BaseModelPermissionCheckerServiceTrackerCustomizer());

		_serviceTracker.open();
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
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void checkPermission(long groupId, String name, long primKey)
		throws PortalException {

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
	 */
	@Override
	public void checkPermission(long groupId, String name, String primKey)
		throws PortalException {

		checkPermission(getPermissionChecker(), groupId, name, primKey);
	}

	protected boolean checkBaseModelPermission(
			PermissionChecker permissionChecker, long groupId, String className,
			long classPK)
		throws PortalException {

		String actionId = ActionKeys.PERMISSIONS;

		if (className.equals(Team.class.getName())) {
			className = Group.class.getName();

			Team team = teamLocalService.fetchTeam(classPK);

			groupId = team.getGroupId();

			actionId = ActionKeys.MANAGE_TEAMS;
		}

		BaseModelPermissionChecker baseModelPermissionChecker =
			_baseModelPermissionCheckers.get(className);

		if (baseModelPermissionChecker != null) {
			baseModelPermissionChecker.checkBaseModel(
				permissionChecker, groupId, classPK, actionId);

			return true;
		}

		return false;
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, long groupId, String name,
			String primKey)
		throws PortalException {

		if (checkBaseModelPermission(
				permissionChecker, groupId, name,
				GetterUtil.getLong(primKey))) {

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

	private final Map<String, BaseModelPermissionChecker>
		_baseModelPermissionCheckers =
			new ConcurrentHashMap<String, BaseModelPermissionChecker>();
	private ServiceTracker
		<BaseModelPermissionChecker, BaseModelPermissionChecker>
			_serviceTracker;

	private class BaseModelPermissionCheckerServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer
				<BaseModelPermissionChecker, BaseModelPermissionChecker> {

		@Override
		public BaseModelPermissionChecker addingService(
			ServiceReference<BaseModelPermissionChecker> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			BaseModelPermissionChecker baseModelPermissionChecker =
				registry.getService(serviceReference);

			String modelClassName = GetterUtil.getString(
				serviceReference.getProperty("model.class.name"));

			_baseModelPermissionCheckers.put(
				modelClassName, baseModelPermissionChecker);

			return baseModelPermissionChecker;
		}

		@Override
		public void modifiedService(
			ServiceReference<BaseModelPermissionChecker> serviceReference,
			BaseModelPermissionChecker baseModelPermissionChecker) {
		}

		@Override
		public void removedService(
			ServiceReference<BaseModelPermissionChecker> serviceReference,
			BaseModelPermissionChecker baseModelPermissionChecker) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String modelClassName = GetterUtil.getString(
				serviceReference.getProperty("model.class.name"));

			_baseModelPermissionCheckers.remove(modelClassName);
		}

	}

}