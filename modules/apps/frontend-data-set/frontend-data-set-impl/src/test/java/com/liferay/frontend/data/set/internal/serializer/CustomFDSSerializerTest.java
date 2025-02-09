/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.frontend.data.set.internal.url.FDSAPIURLResolverRegistryImpl;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URLDecoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CustomFDSSerializerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_bundleContext = SystemBundleUtil.getBundleContext();

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

		_resetFDSSerializer();
	}

	@After
	public void tearDown() {
		_serviceTrackerMap.close();
	}

	@Test
	public void testSerializeAPIURL() {

		// Interpolation

		_mockSerializeAPIURL("fdsName", "/app", "/endpoint/{foo}", "schema");

		ServiceRegistration<FDSAPIURLResolver> serviceRegistration =
			_registerFDSAPIURLResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_customFDSSerializer.serializeAPIURL(
				"fdsName", _httpServletRequest));

		serviceRegistration.unregister();

		_resetFDSSerializer();

		// REST application: /app

		_mockSerializeAPIURL("fdsName1", "/app", "/endpoint/{foo}", "schema");
		_mockSerializeAPIURL("fdsName2", "/app", "/endpoint/{foo}", "schema");

		serviceRegistration = _registerFDSAPIURLResolver(
			"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_customFDSSerializer.serializeAPIURL(
				"fdsName1", _httpServletRequest));
		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_customFDSSerializer.serializeAPIURL(
				"fdsName2", _httpServletRequest));

		serviceRegistration.unregister();

		_resetFDSSerializer();

		// REST application: /app1 and /app2

		_mockSerializeAPIURL("fdsName1", "/app1", "/endpoint/{foo}", "schema");
		_mockSerializeAPIURL("fdsName2", "/app2", "/endpoint/{foo}", "schema");

		serviceRegistration = _registerFDSAPIURLResolver(
			"/app1", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		Assert.assertEquals(
			"/o/app1/endpoint/bar",
			_customFDSSerializer.serializeAPIURL(
				"fdsName1", _httpServletRequest));
		Assert.assertEquals(
			"/o/app2/endpoint/{foo}",
			_customFDSSerializer.serializeAPIURL(
				"fdsName2", _httpServletRequest));

		serviceRegistration.unregister();

		_resetFDSSerializer();

		// Nested fields: creator.name

		_mockSerializeAPIURL(
			"fdsName", new String[] {"creator.name"}, "/app", "/endpoint",
			"schema");

		Assert.assertEquals(
			"/o/app/endpoint?nestedFields=creator",
			_customFDSSerializer.serializeAPIURL(
				"fdsName", _httpServletRequest));

		_resetFDSSerializer();

		// Nested fields: creator.name and status.id

		_mockSerializeAPIURL(
			"fdsName", new String[] {"creator.name", "status.id"}, "/app",
			"/endpoint", "schema");

		String url = _customFDSSerializer.serializeAPIURL(
			"fdsName", _httpServletRequest);

		Assert.assertTrue(url.startsWith("/o/app/endpoint?"));

		Map<String, String> parameterMap = _getParameterMap(url);

		String nestedFields = parameterMap.get("nestedFields");

		Assert.assertTrue(nestedFields.contains("creator"));
		Assert.assertTrue(nestedFields.contains("status"));
		Assert.assertTrue(nestedFields.split(",").length == 2);

		_resetFDSSerializer();

		// Nested fields depth

		_mockSerializeAPIURL(
			"fdsName",
			new String[] {"creator.name", "status.id", "relation.creator.name"},
			"/app", "/endpoint", "schema");

		url = _customFDSSerializer.serializeAPIURL(
			"fdsName", _httpServletRequest);

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

	@Test
	public void testSerializeCreationMenu() throws Exception {

		// Different creation menu

		_mockSerializeCreationMenu(
			"fdsName1", new String[] {"New 1.1", "New 1.2"});
		_mockSerializeCreationMenu("fdsName2", new String[] {"New 2"});

		CreationMenu creationMenu1 = _customFDSSerializer.serializeCreationMenu(
			"fdsName1", _httpServletRequest);

		Assert.assertEquals(2, _getPrimaryItemsSize(creationMenu1));
		Assert.assertFalse(_containsTitle(creationMenu1, "New 2"));
		Assert.assertTrue(_containsTitle(creationMenu1, "New 1.1"));
		Assert.assertTrue(_containsTitle(creationMenu1, "New 1.2"));

		CreationMenu creationMenu2 = _customFDSSerializer.serializeCreationMenu(
			"fdsName2", _httpServletRequest);

		Assert.assertEquals(1, _getPrimaryItemsSize(creationMenu2));
		Assert.assertFalse(_containsTitle(creationMenu2, "New 1.1"));
		Assert.assertFalse(_containsTitle(creationMenu2, "New 1.2"));
		Assert.assertTrue(_containsTitle(creationMenu2, "New 2"));

		_resetFDSSerializer();

		// No creation menu

		_mockSerializeCreationMenu("fdsName", null);

		Assert.assertTrue(
			_customFDSSerializer.serializeCreationMenu(
				"fdsName", _httpServletRequest
			).isEmpty());

		_resetFDSSerializer();

		// Shared creation menu

		String[] titles = {"New A", "New B"};

		_testSerializeCreationMenu("fdsName1", titles);
		_testSerializeCreationMenu("fdsName2", titles);
	}

	@Test
	public void testSerializeItemsActions() throws Exception {

		// Different items actions

		_mockSerializeItemsActions(
			"fdsName1", new String[] {"New 1.1", "New 1.2"});
		_mockSerializeItemsActions("fdsName2", new String[] {"New 2"});

		List<FDSActionDropdownItem> fdsActionDropdownItems1 =
			_customFDSSerializer.serializeItemsActions(
				"fdsName1", _httpServletRequest);

		Assert.assertFalse(_containsLabel(fdsActionDropdownItems1, "New 2"));
		Assert.assertTrue(_containsLabel(fdsActionDropdownItems1, "New 1.1"));
		Assert.assertTrue(_containsLabel(fdsActionDropdownItems1, "New 1.2"));
		Assert.assertTrue(fdsActionDropdownItems1.size() == 2);

		List<FDSActionDropdownItem> fdsActionDropdownItems2 =
			_customFDSSerializer.serializeItemsActions(
				"fdsName2", _httpServletRequest);

		Assert.assertFalse(_containsLabel(fdsActionDropdownItems2, "New 1.1"));
		Assert.assertFalse(_containsLabel(fdsActionDropdownItems2, "New 1.2"));
		Assert.assertTrue(_containsLabel(fdsActionDropdownItems2, "New 2"));
		Assert.assertTrue(fdsActionDropdownItems2.size() == 1);

		_resetFDSSerializer();

		// No items actions

		_mockSerializeItemsActions("fdsName", null);

		Assert.assertTrue(
			_customFDSSerializer.serializeItemsActions(
				"fdsName", _httpServletRequest
			).isEmpty());

		_resetFDSSerializer();

		// Shared items actions

		String[] labels = {"New A", "New B"};

		_testSerializeItemsActions("fdsName1", labels);
		_testSerializeItemsActions("fdsName2", labels);
	}

	private boolean _containsLabel(
		List<FDSActionDropdownItem> fdsActionDropdownItems, String label) {

		for (DropdownItem dropdownItem : fdsActionDropdownItems) {
			if (label.equals((String)dropdownItem.get("label"))) {
				return true;
			}
		}

		return false;
	}

	private boolean _containsTitle(CreationMenu creationMenu, String title) {
		for (DropdownItem dropdownItem :
				(List<DropdownItem>)creationMenu.get("primaryItems")) {

			Map<String, Object> data = (Map<String, Object>)dropdownItem.get(
				"data");

			if (title.equals((String)data.get("title"))) {
				return true;
			}
		}

		return false;
	}

	private Map<String, String> _getParameterMap(String relativeURL) {
		Map<String, String> parameterMap = new HashMap<>();

		try {
			String queryString = relativeURL.split("\\?")[1];

			String[] queryStringParts = queryString.split("&");

			for (String queryStringPart : queryStringParts) {
				String[] keyValuePair = queryStringPart.split("=");

				String key = keyValuePair[0];

				String value = "";

				if (keyValuePair.length > 1) {
					value = URLDecoder.decode(keyValuePair[1], "UTF-8");
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

	private int _getPrimaryItemsSize(CreationMenu creationMenu) {
		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		return dropdownItems.size();
	}

	private void _mockSerializeAPIURL(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema) {

		_mockSerializeAPIURL(
			fdsName, null, restApplication, restEndpoint, restSchema);
	}

	private void _mockSerializeAPIURL(
		String fdsName, String[] fieldNames, String restApplication,
		String restEndpoint, String restSchema) {

		Mockito.when(
			_customFDSSerializer.createFDSAPIURLBuilder(
				_httpServletRequest, restApplication, restEndpoint, restSchema)
		).thenCallRealMethod();

		Mockito.when(
			_customFDSSerializer.getDataSetObjectEntryProperties(
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

		List<ObjectEntry> objectEntries = TransformUtil.transformToList(
			fieldNames,
			fieldName -> {
				ObjectEntry objectEntry = new ObjectEntry();

				objectEntry.setProperties(
					HashMapBuilder.put(
						"fieldName", (Object)fieldName
					).build());

				return objectEntry;
			});

		Mockito.when(
			_customFDSSerializer.getSortedRelatedObjectEntries(
				fdsName, _httpServletRequest, null, "tableSectionsOrder",
				"dataSetToDataSetTableSections")
		).thenReturn(
			objectEntries
		);

		Mockito.when(
			_customFDSSerializer.serializeAPIURL(fdsName, _httpServletRequest)
		).thenCallRealMethod();
	}

	private void _mockSerializeCreationMenu(String fdsName, String[] titles) {
		List<ObjectEntry> objectEntries = TransformUtil.transformToList(
			titles,
			title -> {
				ObjectEntry objectEntry = new ObjectEntry();

				objectEntry.setProperties(
					HashMapBuilder.put(
						"title", (Object)title
					).build());

				return objectEntry;
			});

		Mockito.when(
			_customFDSSerializer.getSortedRelatedObjectEntries(
				Mockito.eq(fdsName), Mockito.eq(_httpServletRequest),
				Mockito.any(), Mockito.eq("creationActionsOrder"),
				Mockito.eq("dataSetToDataSetActions"))
		).thenReturn(
			objectEntries
		);

		Mockito.when(
			_customFDSSerializer.serializeCreationMenu(
				fdsName, _httpServletRequest)
		).thenCallRealMethod();
	}

	private void _mockSerializeItemsActions(String fdsName, String[] labels) {
		List<ObjectEntry> objectEntries = TransformUtil.transformToList(
			labels,
			label -> {
				ObjectEntry objectEntry = new ObjectEntry();

				objectEntry.setProperties(
					HashMapBuilder.put(
						"label", (Object)label
					).build());

				return objectEntry;
			});

		Mockito.when(
			_customFDSSerializer.getSortedRelatedObjectEntries(
				Mockito.eq(fdsName), Mockito.eq(_httpServletRequest),
				Mockito.any(), Mockito.eq("itemActionsOrder"),
				Mockito.eq("dataSetToDataSetActions"))
		).thenReturn(
			objectEntries
		);

		Mockito.when(
			_customFDSSerializer.serializeItemsActions(
				fdsName, _httpServletRequest)
		).thenCallRealMethod();
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

	private void _resetFDSSerializer() {
		_customFDSSerializer = Mockito.mock(CustomFDSSerializer.class);

		ReflectionTestUtil.setFieldValue(
			_customFDSSerializer, "fdsAPIURLResolverRegistry",
			_fdsAPIURLResolverRegistry);
	}

	private void _testSerializeCreationMenu(String fdsName, String[] titles) {
		_mockSerializeCreationMenu(fdsName, titles);

		CreationMenu creationMenu = _customFDSSerializer.serializeCreationMenu(
			fdsName, _httpServletRequest);

		for (String title : titles) {
			Assert.assertTrue(_containsTitle(creationMenu, title));
		}

		Assert.assertEquals(titles.length, _getPrimaryItemsSize(creationMenu));
	}

	private void _testSerializeItemsActions(String fdsName, String[] labels) {
		_mockSerializeItemsActions(fdsName, labels);

		List<FDSActionDropdownItem> fdsActionDropdownItems =
			_customFDSSerializer.serializeItemsActions(
				fdsName, _httpServletRequest);

		for (String label : labels) {
			Assert.assertTrue(_containsLabel(fdsActionDropdownItems, label));
		}

		Assert.assertTrue(labels.length == fdsActionDropdownItems.size());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomFDSSerializerTest.class);

	private static BundleContext _bundleContext;
	private static CustomFDSSerializer _customFDSSerializer;
	private static final FDSAPIURLResolverRegistry _fdsAPIURLResolverRegistry =
		new FDSAPIURLResolverRegistryImpl();
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private static ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSAPIURLResolver>>
			_serviceTrackerMap;

}