/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.url;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.internal.BaseSystemFDSSerializerTestCase;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Sanz
 */
public class APIURLSystemFDSSerializerImplTest
	extends BaseSystemFDSSerializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		ReflectionTestUtil.setFieldValue(
			_fdsAPIURLBuilderFactoryImpl, "_fdsAPIURLResolverRegistry",
			_fdsAPIURLResolverRegistry);

		_fdsAPIURLResolverServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, FDSAPIURLResolver.class,
				"fds.rest.application.key",
				ServiceTrackerCustomizerFactory.
					<FDSAPIURLResolver>serviceWrapper(bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsAPIURLResolverRegistry, "_serviceTrackerMap",
			_fdsAPIURLResolverServiceTrackerMap);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsAPIURLBuilderFactory",
			_fdsAPIURLBuilderFactoryImpl);
		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_systemFDSEntryRegistry",
			systemFDSEntryRegistryImpl);
	}

	@After
	public void tearDown() {
		super.tearDown();

		_fdsAPIURLResolverServiceTrackerMap.close();
	}

	@Test
	public void testSerialize() throws Exception {

		// Different resolvers

		ServiceRegistration<FDSAPIURLResolver> fdsAPIURLServiceRegistration =
			_registerFDSAPIURLResolver(
				"/app1", "schema", new String[] {"{foo}"},
				new String[] {"bar"});
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry(
				"fdsName1", "/app1", "/endpoint/{foo}", "schema");
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			registerSystemFDSEntry(
				"fdsName2", "/app2", "/endpoint/{foo}", "schema");

		Assert.assertEquals(
			"/o/app1/endpoint/bar",
			_fdsSerializer.serialize("fdsName1", httpServletRequest));
		Assert.assertEquals(
			"/o/app2/endpoint/{foo}",
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		fdsAPIURLServiceRegistration.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		// No resolver, URL

		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"fdsName", "/app", "/endpoint", "schema");

		Assert.assertEquals(
			"/o/app/endpoint",
			_fdsSerializer.serialize("fdsName", httpServletRequest));

		systemFDSEntryServiceRegistration1.unregister();

		// No resolver, URL with parameters

		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"param=3", "fdsName", "/app", "/endpoint", "schema");

		Assert.assertEquals(
			"/o/app/endpoint?param=3",
			_fdsSerializer.serialize("fdsName", httpServletRequest));

		systemFDSEntryServiceRegistration1.unregister();

		// Resolver with interpolation

		fdsAPIURLServiceRegistration = _registerFDSAPIURLResolver(
			"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});
		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"{foo}=3", "fdsName", "/app", "/endpoint/{foo}", "schema");

		Assert.assertEquals(
			"/o/app/endpoint/bar?bar=3",
			_fdsSerializer.serialize("fdsName", httpServletRequest));

		fdsAPIURLServiceRegistration.unregister();
		systemFDSEntryServiceRegistration1.unregister();

		// Shared resolver

		fdsAPIURLServiceRegistration = _registerFDSAPIURLResolver(
			"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});
		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"fdsName1", "/app", "/endpoint/{foo}", "schema");
		systemFDSEntryServiceRegistration2 = registerSystemFDSEntry(
			"fdsName2", "/app", "/endpoint/{foo}", "schema");

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_fdsSerializer.serialize("fdsName1", httpServletRequest));
		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		fdsAPIURLServiceRegistration.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();
	}

	private ServiceRegistration<FDSAPIURLResolver> _registerFDSAPIURLResolver(
		String restApplication, String restSchema, String[] tokens,
		String[] values) {

		return bundleContext.registerService(
			FDSAPIURLResolver.class,
			new FDSAPIURLResolver() {

				@Override
				public String getSchema() {
					return restSchema;
				}

				@Override
				public String resolve(
						String baseURL, HttpServletRequest httpServletRequest)
					throws PortalException {

					return StringUtil.replace(baseURL, tokens, values);
				}

			},
			MapUtil.singletonDictionary(
				"fds.rest.application.key",
				restApplication + "/" + restSchema));
	}

	private static final FDSAPIURLBuilderFactoryImpl
		_fdsAPIURLBuilderFactoryImpl = new FDSAPIURLBuilderFactoryImpl();
	private static final FDSAPIURLResolverRegistry _fdsAPIURLResolverRegistry =
		new FDSAPIURLResolverRegistryImpl();
	private static ServiceTrackerMap<String, ServiceWrapper<FDSAPIURLResolver>>
		_fdsAPIURLResolverServiceTrackerMap;
	private static final FDSSerializer<String> _fdsSerializer =
		new APIURLSystemFDSSerializerImpl();

}