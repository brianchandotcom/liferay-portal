/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.instance.lifecycle.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.headless.builder.util.APIApplicationTestUtil;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

/**
 * @author Carlos Correa
 */
@FeatureFlags({"LPS-167253", "LPS-178642"})
public class APIApplicationPublisherPortalInstanceLifecycleListenerTest
	extends BaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		Class<?> clazz = _modelListener.getClass();

		ComponentDescriptionDTO modelListenerComponentDescriptionDTO =
			_serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(clazz), clazz.getName());

		clazz = _portalInstanceLifecycleListener.getClass();

		ComponentDescriptionDTO
			portalInstanceLifecycleListenerComponentDescriptionDTO =
				_serviceComponentRuntime.getComponentDescriptionDTO(
					FrameworkUtil.getBundle(clazz), clazz.getName());

		String baseURL = StringUtil.toLowerCase(RandomTestUtil.randomString());

		try {
			_disableComponentDescriptionDTO(
				modelListenerComponentDescriptionDTO);

			_disableComponentDescriptionDTO(
				portalInstanceLifecycleListenerComponentDescriptionDTO);

			String externalReferenceCode = RandomTestUtil.randomString();

			_addAPIApplication(baseURL, externalReferenceCode);

			APIApplicationTestUtil.assertNotDeployedAPIApplication(baseURL);

			_enableComponentDescriptionDTO(
				portalInstanceLifecycleListenerComponentDescriptionDTO);

			APIApplicationTestUtil.assertDeployedAPIApplication(baseURL);
		}
		finally {
			_enableComponentDescriptionDTO(
				modelListenerComponentDescriptionDTO);

			_enableComponentDescriptionDTO(
				portalInstanceLifecycleListenerComponentDescriptionDTO);
		}
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
						"path",
						StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
					).put(
						"retrieveType", "collection"
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

	private void _disableComponentDescriptionDTO(
			ComponentDescriptionDTO componentDescriptionDTO)
		throws Exception {

		if (!_serviceComponentRuntime.isComponentEnabled(
				componentDescriptionDTO)) {

			return;
		}

		Promise<Void> promise = _serviceComponentRuntime.disableComponent(
			componentDescriptionDTO);

		promise.getValue();
	}

	private void _enableComponentDescriptionDTO(
			ComponentDescriptionDTO componentDescriptionDTO)
		throws Exception {

		if (_serviceComponentRuntime.isComponentEnabled(
				componentDescriptionDTO)) {

			return;
		}

		Promise<Void> promise = _serviceComponentRuntime.enableComponent(
			componentDescriptionDTO);

		promise.getValue();
	}

	@Inject
	private static ServiceComponentRuntime _serviceComponentRuntime;

	@Inject(
		filter = "component.name=com.liferay.headless.builder.internal.model.listener.APIApplicationPublisherObjectEntryModelListener"
	)
	private ModelListener<ObjectEntry> _modelListener;

	@Inject(
		filter = "component.name=com.liferay.headless.builder.internal.instance.lifecycle.APIApplicationPublisherPortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

}