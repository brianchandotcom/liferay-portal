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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class UpgradeResourceAction extends UpgradeProcess {

	public UpgradeResourceAction(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(
				"com_liferay_dynamic_data_lists_form_web_portlet_" +
					"DDLFormAdminPortlet");

		for (ResourceAction resourceAction : resourceActions) {
			_resourceActionLocalService.deleteResourceAction(resourceAction);
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;

}