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

import com.liferay.portal.kernel.exception.NoSuchResourceActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceBlockPermission;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.ResourceTypePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.spring.aop.Skip;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.base.ResourceActionLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ResourceActionLocalServiceImpl
	extends ResourceActionLocalServiceBaseImpl {

	@Override
	public ResourceAction addResourceAction(
		String name, String actionId, long bitwiseValue) {

		ResourceAction resourceAction = resourceActionPersistence.fetchByN_A(
			name, actionId);

		if (resourceAction == null) {
			long resourceActionId = counterLocalService.increment(
				ResourceAction.class.getName());

			resourceAction = resourceActionPersistence.create(resourceActionId);

			resourceAction.setName(name);
			resourceAction.setActionId(actionId);
			resourceAction.setBitwiseValue(bitwiseValue);

			resourceActionPersistence.update(resourceAction);
		}

		return resourceAction;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkResourceActions() {
		List<ResourceAction> resourceActions =
			resourceActionPersistence.findAll();

		for (ResourceAction resourceAction : resourceActions) {
			String key = encodeKey(
				resourceAction.getName(), resourceAction.getActionId());

			_resourceActions.put(key, resourceAction);
		}
	}

	@Override
	public void checkResourceActions(String name, List<String> actionIds) {
		checkResourceActions(name, actionIds, false);
	}

	@Override
	public void checkResourceActions(
		String name, List<String> actionIds, boolean addDefaultActions) {

		List<ResourceAction> resourceActions = getResourceActions(name);
		LinkedList<Long> availableBitwiseValues = new LinkedList<>();

		long bitwiseValue = 2;

		for (int i = 0; i < Long.SIZE - 1; i++) {
			availableBitwiseValues.add(bitwiseValue);

			bitwiseValue = bitwiseValue << 1;
		}

		for (ResourceAction resourceAction : resourceActions) {
			availableBitwiseValues.remove(resourceAction.getBitwiseValue());
		}

		List<ResourceAction> newResourceActions = null;

		for (String actionId : actionIds) {
			String key = encodeKey(name, actionId);

			ResourceAction resourceAction = _resourceActions.get(key);

			if (resourceAction != null) {
				continue;
			}

			resourceAction = resourceActionPersistence.fetchByN_A(
				name, actionId);

			if (resourceAction == null) {
				if (!actionId.equals(ActionKeys.VIEW)) {
					if (availableBitwiseValues.isEmpty()) {
						throw new SystemException(
							"There are more than 64 actions for resource " +
								name);
					}

					bitwiseValue = availableBitwiseValues.pop();
				}
				else {
					bitwiseValue = 1;
				}

				try {
					resourceAction =
						resourceActionLocalService.addResourceAction(
							name, actionId, bitwiseValue);
				}
				catch (Throwable t) {
					resourceAction =
						resourceActionLocalService.addResourceAction(
							name, actionId, bitwiseValue);
				}

				if (newResourceActions == null) {
					newResourceActions = new ArrayList<>();
				}

				newResourceActions.add(resourceAction);
			}

			_resourceActions.put(key, resourceAction);
		}

		if (!addDefaultActions || (newResourceActions == null)) {
			return;
		}

		List<String> groupDefaultActions =
			ResourceActionsUtil.getModelResourceGroupDefaultActions(name);

		List<String> guestDefaultActions =
			ResourceActionsUtil.getModelResourceGuestDefaultActions(name);

		long guestBitwiseValue = 0;
		long ownerBitwiseValue = 0;
		long siteMemberBitwiseValue = 0;

		for (ResourceAction resourceAction : newResourceActions) {
			String actionId = resourceAction.getActionId();

			if (guestDefaultActions.contains(actionId)) {
				guestBitwiseValue |= resourceAction.getBitwiseValue();
			}

			ownerBitwiseValue |= resourceAction.getBitwiseValue();

			if (groupDefaultActions.contains(actionId)) {
				siteMemberBitwiseValue |= resourceAction.getBitwiseValue();
			}
		}

		if (guestBitwiseValue > 0) {
			resourcePermissionLocalService.addResourcePermissions(
				name, RoleConstants.GUEST, ResourceConstants.SCOPE_INDIVIDUAL,
				guestBitwiseValue);
		}

		if (ownerBitwiseValue > 0) {
			resourcePermissionLocalService.addResourcePermissions(
				name, RoleConstants.OWNER, ResourceConstants.SCOPE_INDIVIDUAL,
				ownerBitwiseValue);
		}

		if (siteMemberBitwiseValue > 0) {
			resourcePermissionLocalService.addResourcePermissions(
				name, RoleConstants.SITE_MEMBER,
				ResourceConstants.SCOPE_INDIVIDUAL, siteMemberBitwiseValue);
		}
	}

	@Override
	public ResourceAction deleteResourceAction(long resourceActionId)
		throws PortalException {

		return deleteResourceAction(
			resourceActionPersistence.findByPrimaryKey(resourceActionId));
	}

	@Override
	public ResourceAction deleteResourceAction(ResourceAction resourceAction) {
		_resourceActions.remove(
			encodeKey(resourceAction.getName(), resourceAction.getActionId()));

		List<ResourcePermission> resourcePermissions =
			resourcePermissionLocalService.getResourceResourcePermissions(
				resourceAction.getName());

		for (ResourcePermission resourcePermission : resourcePermissions) {
			long actionIds = resourcePermission.getActionIds();

			if ((actionIds & resourceAction.getBitwiseValue()) != 0) {
				actionIds = actionIds ^ resourceAction.getBitwiseValue();

				resourcePermission.setActionIds(actionIds);

				resourcePermissionPersistence.update(resourcePermission);
			}
		}

		List<ResourceTypePermission> resourceTypePermissions =
			new ArrayList<>();

		List<ResourceBlockPermission> resourceBlockPermissions =
			new ArrayList<>();

		for (Company company : companyLocalService.getCompanies()) {
			long companyId = company.getCompanyId();

			for (Role role : roleLocalService.getRoles(companyId)) {
				resourceTypePermissions.addAll(
					resourceTypePermissionLocalService.
						getGroupScopeResourceTypePermissions(
							companyId, resourceAction.getName(),
							role.getRoleId()));
			}

			resourceBlockPermissions.addAll(
				resourceBlockPermissionLocalService.
					getResourceResourceBlockPermissions(
						companyId, resourceAction.getName()));
		}

		for (ResourceTypePermission resourceTypePermission :
				resourceTypePermissions) {

			long actionIds = resourceTypePermission.getActionIds();

			if ((actionIds & resourceAction.getBitwiseValue()) != 0) {
				actionIds = actionIds ^ resourceAction.getBitwiseValue();

				resourceTypePermission.setActionIds(actionIds);

				resourceTypePermissionPersistence.update(
					resourceTypePermission);
			}
		}

		for (ResourceBlockPermission resourceBlockPermission :
				resourceBlockPermissions) {

			long actionIds = resourceBlockPermission.getActionIds();

			if ((actionIds & resourceAction.getBitwiseValue()) != 0) {
				actionIds = actionIds ^ resourceAction.getBitwiseValue();

				resourceBlockPermission.setActionIds(actionIds);

				resourceBlockPermissionPersistence.update(
					resourceBlockPermission);
			}
		}

		return resourceActionPersistence.remove(resourceAction);
	}

	@Override
	@Skip
	public ResourceAction fetchResourceAction(String name, String actionId) {
		String key = encodeKey(name, actionId);

		return _resourceActions.get(key);
	}

	@Override
	@Skip
	public ResourceAction getResourceAction(String name, String actionId)
		throws PortalException {

		String key = encodeKey(name, actionId);

		ResourceAction resourceAction = _resourceActions.get(key);

		if (resourceAction == null) {
			throw new NoSuchResourceActionException(key);
		}

		return resourceAction;
	}

	@Override
	public List<ResourceAction> getResourceActions(String name) {
		return resourceActionPersistence.findByName(name);
	}

	@Override
	public int getResourceActionsCount(String name) {
		return resourceActionPersistence.countByName(name);
	}

	protected String encodeKey(String name, String actionId) {
		return name.concat(StringPool.POUND).concat(actionId);
	}

	private static final Map<String, ResourceAction> _resourceActions =
		new ConcurrentHashMap<>();

}