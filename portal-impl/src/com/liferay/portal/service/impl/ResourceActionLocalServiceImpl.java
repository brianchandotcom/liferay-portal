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

import com.liferay.portal.NoSuchResourceActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.ResourceActionLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceActionLocalServiceImpl
	extends ResourceActionLocalServiceBaseImpl {

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkResourceActions() throws SystemException {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return;
		}

		List<ResourceAction> resourceActions =
			resourceActionPersistence.findAll();

		for (ResourceAction resourceAction : resourceActions) {
			String key = encodeKey(
				resourceAction.getName(), resourceAction.getActionId());

			_resourceActions.put(key, resourceAction);
		}
	}

	public void checkResourceActions(String name, List<String> actionIds)
		throws SystemException {

		checkResourceActions(name, actionIds, false);
	}

	public void checkResourceActions(
			String name, List<String> actionIds, boolean addDefaultActions)
		throws SystemException {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return;
		}

		List<ResourceAction> resourceActions =
			resourceActionPersistence.findByName(name);

		resourceActions = ListUtil.copy(resourceActions);

		checkResourceActions(
			name, actionIds, resourceActions, addDefaultActions);
	}

	public ResourceAction fetchResourceAction(String name, String actionId) {
		String key = encodeKey(name, actionId);

		return _resourceActions.get(key);
	}

	public ResourceAction getResourceAction(String name, String actionId)
		throws PortalException {

		String key = encodeKey(name, actionId);

		ResourceAction resourceAction = _resourceActions.get(key);

		if (resourceAction == null) {
			throw new NoSuchResourceActionException(key);
		}

		return resourceAction;
	}

	public List<ResourceAction> getResourceActions(String name)
		throws SystemException {

		return resourceActionPersistence.findByName(name);
	}

	protected void checkResourceActions(
			String name, List<String> actionIds,
			List<ResourceAction> resourceActions, boolean addDefaultActions)
		throws SystemException {

		long lastBitwiseValue = 1;

		if (!resourceActions.isEmpty()) {
			ResourceAction resourceAction = resourceActions.get(
				resourceActions.size() - 1);

			lastBitwiseValue = resourceAction.getBitwiseValue();
		}

		List<ResourceAction> newResourceActions =
			new ArrayList<ResourceAction>();

		int lastBitwiseLogValue = MathUtil.base2Log(lastBitwiseValue);

		for (String actionId : actionIds) {
			String key = encodeKey(name, actionId);

			ResourceAction resourceAction = _resourceActions.get(key);

			if (resourceAction != null) {
				continue;
			}

			resourceAction = resourceActionPersistence.fetchByN_A(
				name, actionId);

			if (resourceAction != null) {
				continue;
			}

			long bitwiseValue = 1;

			if (!actionId.equals(ActionKeys.VIEW)) {
				bitwiseValue = MathUtil.base2Pow(++lastBitwiseLogValue);
			}

			long resourceActionId = counterLocalService.increment(
				ResourceAction.class.getName());

			resourceAction = resourceActionPersistence.create(resourceActionId);

			resourceAction.setName(name);
			resourceAction.setActionId(actionId);
			resourceAction.setBitwiseValue(bitwiseValue);

			resourceActionPersistence.update(resourceAction, false);

			_resourceActions.put(key, resourceAction);

			newResourceActions.add(resourceAction);
		}

		if (addDefaultActions) {
			List<String> groupDefaultActions =
				ResourceActionsUtil.getModelResourceGroupDefaultActions(name);

			List<String> guestDefaultActions =
				ResourceActionsUtil.getModelResourceGuestDefaultActions(name);

			for (ResourceAction resourceAction : newResourceActions) {
				String actionId = resourceAction.getActionId();

				if (groupDefaultActions.contains(actionId)) {
					resourcePermissionLocalService.addResourcePermissions(
						name, RoleConstants.SITE_MEMBER,
						ResourceConstants.SCOPE_INDIVIDUAL,
						resourceAction.getBitwiseValue());
				}

				if (guestDefaultActions.contains(actionId)) {
					resourcePermissionLocalService.addResourcePermissions(
						name, RoleConstants.GUEST,
						ResourceConstants.SCOPE_INDIVIDUAL,
						resourceAction.getBitwiseValue());
				}

				resourcePermissionLocalService.addResourcePermissions(
					name, RoleConstants.OWNER,
					ResourceConstants.SCOPE_INDIVIDUAL,
					resourceAction.getBitwiseValue());
			}
		}
	}

	protected String encodeKey(String name, String actionId) {
		return name.concat(StringPool.POUND).concat(actionId).toLowerCase();
	}

	private static Map<String, ResourceAction> _resourceActions =
		new HashMap<String, ResourceAction>();

}