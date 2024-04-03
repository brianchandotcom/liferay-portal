/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.resource.v2_0.test.util.content.type.test.util;

import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.io.InputStream;

import java.net.URL;

/**
 * @author Jiaxu Wei
 */
public class ModelResourceActionTestUtil {

	public static final String PORTLET_RESOURCE_NAME =
		"com_liferay_data_engine_test_portlet_DataEngineTestPortlet";

	public static void deleteModelResourceAction(
			ResourceActions resourceActions)
		throws Exception {

		try (InputStream inputStream =
				ModelResourceActionTestUtil.class.getResourceAsStream(
					"dependencies/resource-actions.xml")) {

			resourceActions.removeModelResources(
				UnsecureSAXReaderUtil.read(inputStream, true));
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