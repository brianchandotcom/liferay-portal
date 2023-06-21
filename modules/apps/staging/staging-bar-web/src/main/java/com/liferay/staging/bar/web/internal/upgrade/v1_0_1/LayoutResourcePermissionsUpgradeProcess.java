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
public class LayoutResourcePermissionsUpgradeProcess extends UpgradeProcess {

	public LayoutResourcePermissionsUpgradeProcess(
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