/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alberto Javier Moreno Lage
 */
@RunWith(Arquillian.class)
public class ObjectEntryLazyPropertiesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			ObjectEntryLazyPropertiesTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			Application.class, new TestApplication(),
			HashMapDictionaryBuilder.<String, Object>put(
				"liferay.auth.verifier", false
			).put(
				"liferay.jackson", false
			).put(
				"liferay.oauth2", false
			).put(
				"osgi.jaxrs.application.base", "/object-rest-test/"
			).put(
				"osgi.jaxrs.extension.select",
				"(osgi.jaxrs.name=Liferay.Vulcan)"
			).build());
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testingLazyProperties() throws Exception {
		HTTPTestUtil.invokeToJSONObject(
			null, "/object-rest-test/testSupplier?fields=property1",
			Http.Method.GET);

		Assert.assertTrue(
			"property1 should have been computed", _property1Computed);
		Assert.assertFalse(
			"property2 should not have been computed", _property2Computed);

		HTTPTestUtil.invokeToJSONObject(
			null, "/object-rest-test/testSupplier", Http.Method.GET);

		Assert.assertTrue(
			"property1 should have been computed", _property1Computed);
		Assert.assertTrue(
			"property2 should have been computed", _property2Computed);
	}

	public static class TestApplication extends Application {

		@Override
		public Set<Object> getSingletons() {
			return Collections.singleton(this);
		}

		@GET
		@Path("/testSupplier")
		@Produces({"application/json", "application/xml"})
		public ObjectEntry getSomething() {
			return new ObjectEntry() {
				{
					setLazyProperties(
						HashMapBuilder.
							<String, UnsafeSupplier<Object, Exception>>put(
								"property1",
								() -> {
									_property1Computed = true;

									return _property1Computed;
								}
							).put(
								"property2",
								() -> {
									_property2Computed = true;

									return _property2Computed;
								}
							).build());
				}
			};
		}

	}

	private static boolean _property1Computed;
	private static boolean _property2Computed;
	private static ServiceRegistration<?> _serviceRegistration;

}