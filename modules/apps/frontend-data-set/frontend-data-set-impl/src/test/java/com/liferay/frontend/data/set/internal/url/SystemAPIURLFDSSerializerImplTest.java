/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.url;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.internal.SystemFDSEntryRegistryImpl;
import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Sanz
 */
public class SystemFDSAPIURLSerializerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_bundleContext = SystemBundleUtil.getBundleContext();

		_systemFDSEntryserviceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, SystemFDSEntry.class, "frontend.data.set.name");

		_fdsAPIURLResolverServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, FDSAPIURLResolver.class,
				"fds.rest.application.key",
				ServiceTrackerCustomizerFactory.
					<FDSAPIURLResolver>serviceWrapper(_bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsAPIURLResolverRegistry, "_serviceTrackerMap",
			_fdsAPIURLResolverServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsAPIURLBuilderFactoryImpl, "_fdsAPIURLResolverRegistry",
			_fdsAPIURLResolverRegistry);

		ReflectionTestUtil.setFieldValue(
			_systemFDSEntryRegistryImpl, "_serviceTrackerMap",
			_systemFDSEntryserviceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_systemFDSAPIURLSerializerImpl, "_fdsAPIURLBuilderFactory",
			_fdsAPIURLBuilderFactoryImpl);
		ReflectionTestUtil.setFieldValue(
			_systemFDSAPIURLSerializerImpl, "_systemFDSEntryRegistry",
			_systemFDSEntryRegistryImpl);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);
	}

	@After
	public void tearDown() {
		_fdsAPIURLResolverServiceTrackerMap.close();
		_systemFDSEntryserviceTrackerMap.close();
	}

	@Test
	public void testFDSAPIURLSerialization() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			_registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		Assert.assertEquals(
			"/o/app/endpoint",
			_systemFDSAPIURLSerializerImpl.serialize(
				"fdsName", _httpServletRequest));

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSAPIURLSerializationSeveralSystemFDSDifferentResolvers()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry(
				"fdsName1", "/app1", "/endpoint/{foo}", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry(
				"fdsName2", "/app", "/endpoint/{foo}", "schema");

		ServiceRegistration<FDSAPIURLResolver> fdsAPIURLServiceRegistration1 =
			_registerResolver(
				"/app1", "schema", new String[] {"{foo}"},
				new String[] {"bar"});

		Assert.assertEquals(
			"/o/app1/endpoint/bar",
			_systemFDSAPIURLSerializerImpl.serialize(
				"fdsName1", _httpServletRequest));

		Assert.assertEquals(
			"/o/app/endpoint/{foo}",
			_systemFDSAPIURLSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();

		fdsAPIURLServiceRegistration1.unregister();
	}

	@Test
	public void testFDSAPIURLSerializationSeveralSystemFDSSameResolvers()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry(
				"fdsName1", "/app", "/endpoint/{foo}", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry(
				"fdsName2", "/app", "/endpoint/{foo}", "schema");

		ServiceRegistration<FDSAPIURLResolver> fdsAPIURLServiceRegistration =
			_registerResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_systemFDSAPIURLSerializerImpl.serialize(
				"fdsName1", _httpServletRequest));

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_systemFDSAPIURLSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();

		fdsAPIURLServiceRegistration.unregister();
	}

	@Test
	public void testFDSAPIURLSerializationWithParameters() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			_registerSystemFDSEntry(
				"fdsName", "/app", "/endpoint", "schema", "param=3");

		Assert.assertEquals(
			"/o/app/endpoint?param=3",
			_systemFDSAPIURLSerializerImpl.serialize(
				"fdsName", _httpServletRequest));

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSAPIURLSerializationWithParametersAndResolvers()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			_registerSystemFDSEntry(
				"fdsName", "/app", "/endpoint/{foo}", "schema", "{foo}=3");

		ServiceRegistration<FDSAPIURLResolver> fdsAPIURLServiceRegistration =
			_registerResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app/endpoint/bar?bar=3",
			_systemFDSAPIURLSerializerImpl.serialize(
				"fdsName", _httpServletRequest));

		systemFDSEntryServiceRegistration.unregister();

		fdsAPIURLServiceRegistration.unregister();
	}

	private ServiceRegistration<FDSAPIURLResolver> _registerResolver(
		String restApplication, String restSchema, String[] tokens,
		String[] values) {

		return _bundleContext.registerService(
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

	private ServiceRegistration<SystemFDSEntry> _registerSystemFDSEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema) {

		return _registerSystemFDSEntry(
			fdsName, restApplication, restEndpoint, restSchema, null);
	}

	private ServiceRegistration<SystemFDSEntry> _registerSystemFDSEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema, String additionalURLParameters) {

		return _bundleContext.registerService(
			SystemFDSEntry.class,
			new SystemFDSEntry() {

				@Override
				public String getAdditionalAPIURLParameters() {
					return additionalURLParameters;
				}

				@Override
				public String getDescription() {
					return "";
				}

				@Override
				public String getName() {
					return fdsName;
				}

				@Override
				public String getRESTApplication() {
					return restApplication;
				}

				@Override
				public String getRESTEndpoint() {
					return restEndpoint;
				}

				@Override
				public String getRESTSchema() {
					return restSchema;
				}

				@Override
				public String getTitle() {
					return "";
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private static BundleContext _bundleContext;
	private static final FDSAPIURLBuilderFactoryImpl
		_fdsAPIURLBuilderFactoryImpl = new FDSAPIURLBuilderFactoryImpl();
	private static final FDSAPIURLResolverRegistry _fdsAPIURLResolverRegistry =
		new FDSAPIURLResolverRegistryImpl();
	private static ServiceTrackerMap<String, ServiceWrapper<FDSAPIURLResolver>>
		_fdsAPIURLResolverServiceTrackerMap;
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private static final SystemFDSAPIURLSerializerImpl
		_systemFDSAPIURLSerializerImpl = new SystemFDSAPIURLSerializerImpl();
	private static final SystemFDSEntryRegistryImpl
		_systemFDSEntryRegistryImpl = new SystemFDSEntryRegistryImpl();
	private static ServiceTrackerMap<String, SystemFDSEntry>
		_systemFDSEntryserviceTrackerMap;

}