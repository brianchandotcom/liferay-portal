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

package com.liferay.headless.builder.instance.lifecycle.test;

import com.liferay.headless.builder.application.publisher.test.APIApplicationPublisherTest;
import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.object.model.ObjectEntry;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.ws.rs.core.Application;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Correa
 */
@FeatureFlags({"LPS-167253", "LPS-184413", "LPS-186757"})
public class APIApplicationPublisherPortalInstanceLifecycleListenerTest
	extends BaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

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

		String baseURL = RandomTestUtil.randomString();

		Bundle testBundle = FrameworkUtil.getBundle(
			APIApplicationPublisherTest.class);

		ServiceTracker<Application, Application> serviceTracker =
			ServiceTrackerFactory.open(
				testBundle.getBundleContext(),
				StringBundler.concat(
					"(&(objectClass=", Application.class.getName(),
					")(liferay.headless.builder.application=true)",
					"(osgi.jaxrs.application.base=", baseURL, "))"));

		try {
			_disableComponentDescriptionDTO(
				modelListenerComponentDescriptionDTO);

			_disableComponentDescriptionDTO(
				portalInstanceLifecycleListenerComponentDescriptionDTO);

			String externalReferenceCode = RandomTestUtil.randomString();

			_addAPIApplication(baseURL, externalReferenceCode);

			Assert.assertEquals(0, serviceTracker.size());

			_enableComponentDescriptionDTO(
				portalInstanceLifecycleListenerComponentDescriptionDTO);

			Assert.assertNotNull(
				"The API Application has not been deployed",
				serviceTracker.waitForService(10000));
		}
		finally {
			_enableComponentDescriptionDTO(
				modelListenerComponentDescriptionDTO);

			_enableComponentDescriptionDTO(
				portalInstanceLifecycleListenerComponentDescriptionDTO);

			serviceTracker.close();
		}
	}

	private void _addAPIApplication(
			String baseURL, String externalReferenceCode)
		throws Exception {

		String apiEndpointExternalReferenceCode = RandomTestUtil.randomString();
		String apiSchemaExternalReferenceCode = RandomTestUtil.randomString();

		HTTPTestUtil.invoke(
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
						"path", RandomTestUtil.randomString()
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

		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/requestAPISchemaToAPIEndpoints/",
				apiEndpointExternalReferenceCode),
			Http.Method.PUT);
		HTTPTestUtil.invoke(
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

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(
		filter = "component.name=com.liferay.headless.builder.internal.model.listener.APIApplicationPublisherObjectEntryModelListener"
	)
	private ModelListener<ObjectEntry> _modelListener;

	@Inject(
		filter = "component.name=com.liferay.headless.builder.internal.instance.lifecycle.APIApplicationPublisherPortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}