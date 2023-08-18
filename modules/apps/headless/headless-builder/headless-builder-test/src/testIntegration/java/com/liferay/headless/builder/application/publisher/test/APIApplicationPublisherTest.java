/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.application.publisher.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.headless.builder.util.APIApplicationTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Test;

/**
 * @author Luis Miguel Barcos
 */
@FeatureFlags({"LPS-167253", "LPS-184413", "LPS-186757"})
public class APIApplicationPublisherTest extends BaseTestCase {

	@Test
	public void testPublish() throws Exception {
		_addAPIApplication(_BASE_URL_1, _API_APPLICATION_ERC_1);

		APIApplicationTestUtil.assertDeployedAPIApplication(_BASE_URL_1);

		_addAPIApplication(_BASE_URL_2, _API_APPLICATION_ERC_2);

		APIApplicationTestUtil.assertDeployedAPIApplication(_BASE_URL_2);

		_deleteAPIApplication(_API_APPLICATION_ERC_1);

		APIApplicationTestUtil.assertNotDeployedAPIApplication(_BASE_URL_1);

		_deleteAPIApplication(_API_APPLICATION_ERC_2);

		APIApplicationTestUtil.assertNotDeployedAPIApplication(_BASE_URL_2);
	}

	private void _addAPIApplication(
			String baseURL, String externalReferenceCode)
		throws Exception {

		String apiEndpointExternalReferenceCode = RandomTestUtil.randomString();
		String apiSchemaExternalReferenceCode = RandomTestUtil.randomString();

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				JSONUtil.put(
					JSONUtil.put(
						"description", "description"
					).put(
						"externalReferenceCode",
						apiEndpointExternalReferenceCode
					).put(
						"httpMethod", "get"
					).put(
						"name", "name"
					).put(
						"path", "/path"
					).put(
						"scope", "company"
					))
			).put(
				"apiApplicationToAPISchemas",
				JSONUtil.put(
					JSONUtil.put(
						"apiSchemaToAPIProperties",
						JSONUtil.put(
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "name"
							).put(
								"objectFieldERC", "APPLICATION_STATUS"
							))
					).put(
						"description", "description"
					).put(
						"externalReferenceCode", apiSchemaExternalReferenceCode
					).put(
						"mainObjectDefinitionERC", "L_API_APPLICATION"
					).put(
						"name", "name"
					))
			).put(
				"applicationStatus", "published"
			).put(
				"baseURL", baseURL
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/requestAPISchemaToAPIEndpoints/",
				apiEndpointExternalReferenceCode),
			Http.Method.PUT);
		HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/responseAPISchemaToAPIEndpoints/",
				apiEndpointExternalReferenceCode),
			Http.Method.PUT);
	}

	private void _deleteAPIApplication(String externalReferenceCode)
		throws Exception {

		HTTPTestUtil.invokeToJSONObject(
			null,
			"headless-builder/applications/by-external-reference-code/" +
				externalReferenceCode,
			Http.Method.DELETE);
	}

	private static final String _API_APPLICATION_ERC_1 =
		RandomTestUtil.randomString();

	private static final String _API_APPLICATION_ERC_2 =
		RandomTestUtil.randomString();

	private static final String _BASE_URL_1 = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private static final String _BASE_URL_2 = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

}