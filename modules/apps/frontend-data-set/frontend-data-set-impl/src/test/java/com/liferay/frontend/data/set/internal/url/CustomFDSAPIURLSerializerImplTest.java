/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.url;

import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

		ReflectionTestUtil.setFieldValue(
			_fdsAPIURLBuilderFactoryImpl, "_fdsAPIURLResolverRegistry",
			_fdsAPIURLResolverRegistry);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, FDSAPIURLResolver.class, "fds.rest.application.key",
			ServiceTrackerCustomizerFactory.<FDSAPIURLResolver>serviceWrapper(
				_bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsAPIURLResolverRegistry, "_serviceTrackerMap",
			_serviceTrackerMap);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		_resetSerializer();
	}

	@After
	public void tearDown() {
		_serviceTrackerMap.close();
	}

	@Test
	public void testSerialize() throws Exception {

		// Interpolation

		_mockFDSObjectEntry("fdsName", "/app", "/endpoint/{foo}", "schema");

		ServiceRegistration<FDSAPIURLResolver> serviceRegistration =
			_registerFDSAPIURLResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_fdsSerializer.serialize("fdsName", _httpServletRequest));

		serviceRegistration.unregister();

		_resetSerializer();

		// REST application: /app1 and /app2

		_mockFDSObjectEntry("fdsName1", "/app1", "/endpoint/{foo}", "schema");
		_mockFDSObjectEntry("fdsName2", "/app2", "/endpoint/{foo}", "schema");

		serviceRegistration = _registerFDSAPIURLResolver(
			"/app1", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app1/endpoint/bar",
			_fdsSerializer.serialize("fdsName1", _httpServletRequest));
		Assert.assertEquals(
			"/o/app2/endpoint/{foo}",
			_fdsSerializer.serialize("fdsName2", _httpServletRequest));

		serviceRegistration.unregister();

		_resetSerializer();

		// REST application: /app

		_mockFDSObjectEntry("fdsName1", "/app", "/endpoint/{foo}", "schema");
		_mockFDSObjectEntry("fdsName2", "/app", "/endpoint/{foo}", "schema");

		serviceRegistration = _registerFDSAPIURLResolver(
			"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_fdsSerializer.serialize("fdsName1", _httpServletRequest));
		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_fdsSerializer.serialize("fdsName2", _httpServletRequest));

		serviceRegistration.unregister();

		_resetSerializer();

		// Nested fields: creator.name

		_mockFDSObjectEntry(
			"fdsName", new String[] {"creator.name"}, "/app", "/endpoint",
			"schema");

		Assert.assertEquals(
			"/o/app/endpoint?nestedFields=creator",
			_fdsSerializer.serialize("fdsName", _httpServletRequest));

		_resetSerializer();

		// Nested fields: creator.name and status.id

		_mockFDSObjectEntry(
			"fdsName", new String[] {"creator.name", "status.id"}, "/app",
			"/endpoint", "schema");

		String url = _fdsSerializer.serialize("fdsName", _httpServletRequest);

		Assert.assertTrue(url.startsWith("/o/app/endpoint?"));

		Map<String, String> parameterMap = _getParameterMap(url);

		String nestedFields = parameterMap.get("nestedFields");

		Assert.assertTrue(nestedFields.contains("creator"));
		Assert.assertTrue(nestedFields.contains("status"));
		Assert.assertTrue(nestedFields.split(",").length == 2);

		_resetSerializer();

		// Nested fields depth

		_mockFDSObjectEntry(
			"fdsName",
			new String[] {"creator.name", "status.id", "relation.creator.name"},
			"/app", "/endpoint", "schema");

		url = _fdsSerializer.serialize("fdsName", _httpServletRequest);

		Assert.assertTrue(url.startsWith("/o/app/endpoint?"));

		parameterMap = _getParameterMap(url);

		nestedFields = parameterMap.get("nestedFields");

		Assert.assertTrue(nestedFields.contains("creator"));
		Assert.assertTrue(nestedFields.contains("relation"));
		Assert.assertTrue(nestedFields.contains("status"));
		Assert.assertTrue(nestedFields.split(",").length == 3);

		String nestedFieldsDepth = parameterMap.get("nestedFieldsDepth");

		Assert.assertTrue(nestedFieldsDepth.equals("2"));
	}

	private Map<String, String> _getParameterMap(String relativeURL) {
		Map<String, String> parameterMap = new HashMap<>();

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

				parameterMap.put(key, value);
			}

			return parameterMap;
		}
		catch (Exception exception) {
			_log.error(exception);

			return Collections.emptyMap();
		}
	}

	private void _mockFDSObjectEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema) {

		_mockFDSObjectEntry(
			fdsName, null, restApplication, restEndpoint, restSchema);
	}

	private void _mockFDSObjectEntry(
		String fdsName, String[] fieldNames, String restApplication,
		String restEndpoint, String restSchema) {

		Mockito.when(
			_fdsSerializer.serialize(fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseCustomFDSSerializer baseCustomFDSSerializer =
			(BaseCustomFDSSerializer)_fdsSerializer;

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

	private ServiceRegistration<FDSAPIURLResolver> _registerFDSAPIURLResolver(
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

	private void _resetSerializer() {
		_fdsSerializer = Mockito.mock(CustomFDSAPIURLSerializerImpl.class);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsAPIURLBuilderFactory",
			_fdsAPIURLBuilderFactoryImpl);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomFDSAPIURLSerializerImplTest.class);

	private static BundleContext _bundleContext;
	private static final FDSAPIURLBuilderFactoryImpl
		_fdsAPIURLBuilderFactoryImpl = new FDSAPIURLBuilderFactoryImpl();
	private static final FDSAPIURLResolverRegistry _fdsAPIURLResolverRegistry =
		new FDSAPIURLResolverRegistryImpl();
	private static FDSSerializer<String> _fdsSerializer;
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private static ServiceTrackerMap<String, ServiceWrapper<FDSAPIURLResolver>>
		_serviceTrackerMap;

}