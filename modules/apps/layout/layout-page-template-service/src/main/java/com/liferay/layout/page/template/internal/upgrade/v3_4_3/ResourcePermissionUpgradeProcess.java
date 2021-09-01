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

package com.liferay.layout.page.template.internal.upgrade.v3_4_3;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class ResourcePermissionUpgradeProcess extends UpgradeProcess {

	public ResourcePermissionUpgradeProcess(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_insertResourcePermissions();
	}

	private long _getLayoutPageTemplateEntryId(long layoutPrototypeId) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select layoutPageTemplateEntryId from ",
					"LayoutPageTemplateEntry where layoutPrototypeId = ? ",
					"order by name asc limit 1"))) {

			preparedStatement.setLong(1, layoutPrototypeId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					return 0;
				}

				return resultSet.getLong("layoutPageTemplateEntryId");
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return 0;
	}

	private void _insertResourcePermissions() {
		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				LayoutPrototype.class.getName());

		for (ResourcePermission resourcePermission : resourcePermissions) {
			String primKey = resourcePermission.getPrimKey();
			long primKeyId = resourcePermission.getPrimKeyId();

			if (!primKey.equals(LayoutPrototype.class.getName())) {
				long layoutPageTemplateEntryId = _getLayoutPageTemplateEntryId(
					primKeyId);

				primKey = String.valueOf(layoutPageTemplateEntryId);

				primKeyId = layoutPageTemplateEntryId;
			}
			else {
				primKey = LayoutPageTemplateEntry.class.getName();
			}

			ResourcePermission newResourcePermission =
				_resourcePermissionLocalService.createResourcePermission(
					increment());

			newResourcePermission.setCompanyId(
				resourcePermission.getCompanyId());
			newResourcePermission.setName(
				LayoutPageTemplateEntry.class.getName());
			newResourcePermission.setScope(resourcePermission.getScope());
			newResourcePermission.setPrimKey(primKey);
			newResourcePermission.setPrimKeyId(primKeyId);
			newResourcePermission.setRoleId(resourcePermission.getRoleId());
			newResourcePermission.setOwnerId(resourcePermission.getOwnerId());
			newResourcePermission.setActionIds(
				resourcePermission.getActionIds());
			newResourcePermission.setViewActionId(
				resourcePermission.getViewActionId());

			_resourcePermissionLocalService.addResourcePermission(
				newResourcePermission);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionUpgradeProcess.class);

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}