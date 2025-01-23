/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.url;

import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URLDecoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public class CustomFDSAPIURLSerializerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_bundleContext = SystemBundleUtil.getBundleContext();

		_customFDSAPIURLSerializerImpl = Mockito.mock(
			CustomFDSAPIURLSerializerImpl.class);

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
			_customFDSAPIURLSerializerImpl, "_fdsAPIURLBuilderFactory",
			_fdsAPIURLBuilderFactoryImpl);

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
	}

	@Test
	public void testFDSAPIURLSerialization() throws Exception {
		_mockFDSObjectEntry("fdsName", "/app", "/endpoint/{foo}", "schema");

		ServiceRegistration<FDSAPIURLResolver> fdsAPIURLServiceRegistration =
			_registerResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_customFDSAPIURLSerializerImpl.serialize(
				"fdsName", _httpServletRequest));

		fdsAPIURLServiceRegistration.unregister();
	}

	@Test
	public void testFDSAPIURLSerializationSeveralSystemFDSDifferentResolvers()
		throws Exception {

		_mockFDSObjectEntry("fdsName1", "/app1", "/endpoint/{foo}", "schema");

		_mockFDSObjectEntry("fdsName2", "/app", "/endpoint/{foo}", "schema");

		ServiceRegistration<FDSAPIURLResolver> fdsAPIURLServiceRegistration1 =
			_registerResolver(
				"/app1", "schema", new String[] {"{foo}"},
				new String[] {"bar"});

		Assert.assertEquals(
			"/o/app1/endpoint/bar",
			_customFDSAPIURLSerializerImpl.serialize(
				"fdsName1", _httpServletRequest));

		Assert.assertEquals(
			"/o/app/endpoint/{foo}",
			_customFDSAPIURLSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		fdsAPIURLServiceRegistration1.unregister();
	}

	@Test
	public void testFDSAPIURLSerializationSeveralSystemFDSSameResolvers()
		throws Exception {

		_mockFDSObjectEntry("fdsName1", "/app", "/endpoint/{foo}", "schema");

		_mockFDSObjectEntry("fdsName2", "/app", "/endpoint/{foo}", "schema");

		ServiceRegistration<FDSAPIURLResolver> fdsAPIURLServiceRegistration =
			_registerResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_customFDSAPIURLSerializerImpl.serialize(
				"fdsName1", _httpServletRequest));

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_customFDSAPIURLSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		fdsAPIURLServiceRegistration.unregister();
	}

	@Test
	public void testFDSAPIURLSerializationWithNestedField() throws Exception {
		_mockFDSObjectEntry(
			"fdsName", "/app", "/endpoint", "schema",
			new String[] {"creator.name"});

		Assert.assertEquals(
			"/o/app/endpoint?nestedFields=creator",
			_customFDSAPIURLSerializerImpl.serialize(
				"fdsName", _httpServletRequest));
	}

	@Test
	public void testFDSAPIURLSerializationWithNestedFields() throws Exception {
		_mockFDSObjectEntry(
			"fdsName", "/app", "/endpoint", "schema",
			new String[] {"creator.name", "status.id"});

		String url = _customFDSAPIURLSerializerImpl.serialize(
			"fdsName", _httpServletRequest);

		Map<String, String> queryParams = _parseQueryParams(url);

		String nestedFields = queryParams.get("nestedFields");

		Assert.assertTrue(url.startsWith("/o/app/endpoint?"));
		Assert.assertNotNull(nestedFields);
		Assert.assertTrue(nestedFields.contains("creator"));
		Assert.assertTrue(nestedFields.contains("status"));
		Assert.assertTrue(nestedFields.split(",").length == 2);
	}

	@Test
	public void testFDSAPIURLSerializationWithNestedFieldsAndDepth()
		throws Exception {

		_mockFDSObjectEntry(
			"fdsName", "/app", "/endpoint", "schema",
			new String[] {
				"creator.name", "status.id", "relation.creator.name"
			});

		String url = _customFDSAPIURLSerializerImpl.serialize(
			"fdsName", _httpServletRequest);

		Map<String, String> queryParams = _parseQueryParams(url);

		String nestedFields = queryParams.get("nestedFields");

		String nestedFieldsDepth = queryParams.get("nestedFieldsDepth");

		Assert.assertTrue(url.startsWith("/o/app/endpoint?"));
		Assert.assertNotNull(nestedFields);
		Assert.assertTrue(nestedFields.contains("creator"));
		Assert.assertTrue(nestedFields.contains("status"));
		Assert.assertTrue(nestedFields.contains("relation"));
		Assert.assertTrue(nestedFields.split(",").length == 3);
		Assert.assertTrue(nestedFieldsDepth.equals("2"));
	}

	private void _mockFDSObjectEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema) {

		_mockFDSObjectEntry(
			fdsName, restApplication, restEndpoint, restSchema, null);
	}

	private void _mockFDSObjectEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema, String[] fieldNames) {

		Mockito.when(
			_customFDSAPIURLSerializerImpl.serialize(
				fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseCustomFDSSerializer baseCustomFDSSerializer =
			(BaseCustomFDSSerializer)_customFDSAPIURLSerializerImpl;

		Mockito.when(
			baseCustomFDSSerializer.getDataSetObjectEntryProperties(
				fdsName, _httpServletRequest)
		).thenReturn(
			HashMapBuilder.put(
				"restApplication", (Object)restApplication
			).put(
				"restEndpoint", restEndpoint
			).put(
				"restSchema", restSchema
			).build()
		);

		if (ArrayUtil.isEmpty(fieldNames)) {
			Mockito.when(
				baseCustomFDSSerializer.getDataSetTableSectionObjectEntries(
					fdsName, _httpServletRequest)
			).thenReturn(
				Collections.emptySet()
			);

			return;
		}

		Set<ObjectEntry> objectEntries = new HashSet<>();

		for (String fieldName : fieldNames) {
			ObjectEntry objectEntry = new ObjectEntry();

			objectEntry.setProperties(
				HashMapBuilder.put(
					"fieldName", (Object)fieldName
				).build());

			objectEntries.add(objectEntry);
		}

		Mockito.when(
			baseCustomFDSSerializer.getDataSetTableSectionObjectEntries(
				fdsName, _httpServletRequest)
		).thenReturn(
			objectEntries
		);
	}

	private Map<String, String> _parseQueryParams(String relativeURL) {
		Map<String, String> queryParams = new HashMap<>();

		try {
			String query = relativeURL.split("\\?")[1];

			String[] pairs = query.split("&");

			for (String pair : pairs) {
				String[] keyValue = pair.split("=");

				String key = keyValue[0];

				String value = "";

				if (keyValue.length > 1) {
					value = URLDecoder.decode(keyValue[1], "UTF-8");
				}

				queryParams.put(key, value);
			}

			return queryParams;
		}
		catch (Exception exception) {
			System.err.println(exception);

			return Collections.emptyMap();
		}
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

	private static BundleContext _bundleContext;
	private static CustomFDSAPIURLSerializerImpl
		_customFDSAPIURLSerializerImpl = Mockito.mock(
			CustomFDSAPIURLSerializerImpl.class);
	private static final FDSAPIURLBuilderFactoryImpl
		_fdsAPIURLBuilderFactoryImpl = new FDSAPIURLBuilderFactoryImpl();
	private static final FDSAPIURLResolverRegistry _fdsAPIURLResolverRegistry =
		new FDSAPIURLResolverRegistryImpl();
	private static ServiceTrackerMap<String, ServiceWrapper<FDSAPIURLResolver>>
		_fdsAPIURLResolverServiceTrackerMap;
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);

}