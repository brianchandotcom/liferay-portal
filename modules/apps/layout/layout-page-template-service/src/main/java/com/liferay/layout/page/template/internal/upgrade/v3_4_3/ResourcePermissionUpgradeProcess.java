/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		_addResourcePermissions();
	}

	private void _addResourcePermissions() {
		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				LayoutPrototype.class.getName());

		for (ResourcePermission resourcePermission : resourcePermissions) {
			String primKey = resourcePermission.getPrimKey();
			long primKeyId = resourcePermission.getPrimKeyId();

			if (!primKey.equals(LayoutPrototype.class.getName())) {
				long layoutPageTemplateEntryId = _getLayoutPageTemplateEntryId(
					primKeyId);

				if (layoutPageTemplateEntryId == 0) {
					continue;
				}

				primKey = String.valueOf(layoutPageTemplateEntryId);

				primKeyId = layoutPageTemplateEntryId;
			}
			else {
				primKey = LayoutPageTemplateEntry.class.getName();
			}

			ResourcePermission existingResourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					resourcePermission.getCompanyId(),
					LayoutPageTemplateEntry.class.getName(),
					resourcePermission.getScope(), primKey,
					resourcePermission.getRoleId());

			if (existingResourcePermission != null) {
				continue;
			}

			ResourcePermission newResourcePermission =
				_resourcePermissionLocalService.createResourcePermission(
					increment(ResourcePermission.class.getName()));

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
				_log.debug(exception);
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionUpgradeProcess.class);

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}