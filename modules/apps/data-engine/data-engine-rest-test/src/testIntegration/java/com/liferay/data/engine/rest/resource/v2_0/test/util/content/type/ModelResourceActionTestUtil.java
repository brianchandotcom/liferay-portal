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

package com.liferay.data.engine.rest.resource.v2_0.test.util.content.type;

import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;

import java.net.URL;

import java.util.List;

/**
 * @author Jiaxu Wei
 */
public class ModelResourceActionTestUtil {

	public static final String PORTLET_RESOURCE_NAME =
		"com_liferay_data_engine_test_portlet_DataEngineTestPortlet";

	public static void deleteModelResourceAction(
		ResourceActionLocalService resourceActionLocalService,
		ResourceActions resourceActions) {

		List<ResourceAction> portletResourceActions =
			resourceActionLocalService.getResourceActions(
				PORTLET_RESOURCE_NAME);

		for (ResourceAction portletResourceAction : portletResourceActions) {
			resourceActionLocalService.deleteResourceAction(
				portletResourceAction);
		}

		List<String> modelResourceNames =
			resourceActions.getPortletModelResources(PORTLET_RESOURCE_NAME);

		for (String modelResourceName : modelResourceNames) {
			List<ResourceAction> modelResourceActions =
				resourceActionLocalService.getResourceActions(
					modelResourceName);

			for (ResourceAction modelResourceAction : modelResourceActions) {
				resourceActionLocalService.deleteResourceAction(
					modelResourceAction);
			}
		}
	}

	public static void populateModelResourceAction(
			ResourceActions resourceActions)
		throws Exception {

		URL url = ModelResourceActionTestUtil.class.getResource(
			"dependencies/resource-actions.xml");

		resourceActions.populateModelResources(
			ModelResourceActionTestUtil.class.getClassLoader(), url.getPath());
	}

}