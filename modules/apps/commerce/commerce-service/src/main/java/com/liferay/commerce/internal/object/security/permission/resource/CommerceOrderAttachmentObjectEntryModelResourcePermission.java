/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.security.permission.resource;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Stefano Motta
 */
public class CommerceOrderAttachmentObjectEntryModelResourcePermission
	implements ModelResourcePermission<ObjectEntry> {

	public CommerceOrderAttachmentObjectEntryModelResourcePermission(
		CommerceOrderLocalService commerceOrderLocalService,
		ModelResourcePermission<CommerceOrder>
			commerceOrderModelResourcePermission,
		ModelResourcePermission<ObjectEntry> modelResourcePermission,
		ObjectEntryLocalService objectEntryLocalService) {

		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceOrderModelResourcePermission =
			commerceOrderModelResourcePermission;
		_modelResourcePermission = modelResourcePermission;
		_objectEntryLocalService = objectEntryLocalService;
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, primaryKey, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _modelResourcePermission.getModelName(),
				primaryKey, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, ObjectEntry objectEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _modelResourcePermission.getModelName(),
				objectEntry.getObjectEntryId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		if (!Objects.equals(actionId, ActionKeys.DELETE) &&
			!Objects.equals(actionId, ActionKeys.UPDATE) &&
			!Objects.equals(actionId, ActionKeys.VIEW)) {

			return _modelResourcePermission.contains(
				permissionChecker, primaryKey, actionId);
		}

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			primaryKey);

		if (objectEntry == null) {
			return _modelResourcePermission.contains(
				permissionChecker, primaryKey, actionId);
		}

		return contains(permissionChecker, objectEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, ObjectEntry objectEntry,
			String actionId)
		throws PortalException {

		if (!Objects.equals(actionId, ActionKeys.DELETE) &&
			!Objects.equals(actionId, ActionKeys.UPDATE) &&
			!Objects.equals(actionId, ActionKeys.VIEW)) {

			return _modelResourcePermission.contains(
				permissionChecker, objectEntry, actionId);
		}

		Map<String, Serializable> values = objectEntry.getValues();

		long commerceOrderId = GetterUtil.getLong(
			values.get(
				"r_commerceOrderToCommerceOrderAttachments_commerceOrderId"));

		if (commerceOrderId <= 0) {
			return _modelResourcePermission.contains(
				permissionChecker, objectEntry, actionId);
		}

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);

		if (commerceOrder == null) {
			return _modelResourcePermission.contains(
				permissionChecker, objectEntry, actionId);
		}

		return _commerceOrderModelResourcePermission.contains(
			permissionChecker, commerceOrder, actionId);
	}

	@Override
	public String getModelName() {
		return _modelResourcePermission.getModelName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _modelResourcePermission.getPortletResourcePermission();
	}

	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;
	private final ModelResourcePermission<ObjectEntry> _modelResourcePermission;
	private final ObjectEntryLocalService _objectEntryLocalService;

}