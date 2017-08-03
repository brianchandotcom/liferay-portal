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

package com.liferay.portal.workflow.kaleo.designer.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author Sam Ziemer
 */
public class UpgradeResourceActions extends UpgradeProcess {

	public UpgradeResourceActions(
		ResourceActionLocalService resourceActionLocalService,
		ResourceActions resourceActions) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourceActions = resourceActions;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_resourceActions.read(
			null, UpgradeResourceActions.class.getClassLoader(),
			"/META-INF/resource-actions/default.xml");

		List<String> modelNames = _resourceActions.getPortletModelResources(
			_kaleoDesignerPortketKey);

		for (String modelName : modelNames) {
			List<String> modelActions =
				_resourceActions.getModelResourceActions(modelName);

			_resourceActionLocalService.checkResourceActions(
				modelName, modelActions);
		}
	}

	private static final String _kaleoDesignerPortketKey =
		"com_liferay_portal_workflow_kaleo_designer_web_portlet_" +
			"KaleoDesignerPortlet";

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceActions _resourceActions;

}