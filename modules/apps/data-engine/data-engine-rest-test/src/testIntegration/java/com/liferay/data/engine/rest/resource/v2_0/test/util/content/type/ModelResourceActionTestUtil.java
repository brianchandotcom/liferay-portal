/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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