/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.bar.web.internal.upgrade.v1_0_1;

import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.staging.bar.web.internal.portlet.constants.StagingBarPortletKeys;

import java.util.List;

/**
 * @author Jorge Díaz
 */
public class ResourcePermissionsUpgradeProcess extends UpgradeProcess {

	public ResourcePermissionsUpgradeProcess(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			ResourcePermissionTable.INSTANCE.resourcePermissionId
		).from(
			ResourcePermissionTable.INSTANCE
		).where(
			ResourcePermissionTable.INSTANCE.name.eq(
				StagingBarPortletKeys.STAGING_BAR
			).and(
				ResourcePermissionTable.INSTANCE.primKey.like(
					'%' + PortletConstants.LAYOUT_SEPARATOR +
						StagingBarPortletKeys.STAGING_BAR)
			)
		);

		List<Long> resourcePermissionIds =
			_resourcePermissionLocalService.dslQuery(dslQuery);

		for (Long resourcePermissionId : resourcePermissionIds) {
			_resourcePermissionLocalService.deleteResourcePermission(
				resourcePermissionId);
		}
	}

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}