/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.client.extension.type.FDSCellRendererCET;
import com.liferay.client.extension.type.FDSFilterCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Daniel Sanz
 */
public class CustomFDSSerializerTest extends BaseFDSSerializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_bundleContext = SystemBundleUtil.getBundleContext();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			0L
		);

		_resetFDSSerializer();
	}

	@Test
	public void testSerializeAPIURL() {

		// Nested fields: creator.name

		ServiceTrackerMap
			<String,
			 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSAPIURLResolver>>
				serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext, FDSAPIURLResolver.class,
					"fds.rest.application.key",
					ServiceTrackerCustomizerFactory.
						<FDSAPIURLResolver>serviceWrapper(_bundleContext));

		FDSAPIURLResolverRegistry fdsAPIURLResolverRegistry =
			new FDSAPIURLResolverRegistryImpl(serviceTrackerMap);

		_resetFDSSerializer(fdsAPIURLResolverRegistry);

		_mockSerializeAPIURL("fdsName", new String[] {"creator.name"});

		Assert.assertEquals(
			"/o/app/endpoint?nestedFields=creator",
			_customFDSSerializer.serializeAPIURL(
				"fdsName", httpServletRequest));

		_resetFDSSerializer(fdsAPIURLResolverRegistry);

		// Nested fields: creator.name and status.id

		_mockSerializeAPIURL(
			"fdsName", new String[] {"creator.name", "status.id"});

		String url = _customFDSSerializer.serializeAPIURL(
			"fdsName", httpServletRequest);

		Assert.assertTrue(url.startsWith("/o/app/endpoint?"));

		Map<String, String> parameterMap = _getParameterMap(url);

		String nestedFields = parameterMap.get("nestedFields");

		Assert.assertTrue(nestedFields.contains("creator"));
		Assert.assertTrue(nestedFields.contains("status"));
		Assert.assertTrue(nestedFields.split(",").length == 2);

		_resetFDSSerializer(fdsAPIURLResolverRegistry);

		// Nested fields depth

		_mockSerializeAPIURL(
			"fdsName",
			new String[] {
				"creator.name", "status.id", "relation.creator.name"
			});

		url = _customFDSSerializer.serializeAPIURL(
			"fdsName", httpServletRequest);

		Assert.assertTrue(url.startsWith("/o/app/endpoint?"));

		parameterMap = _getParameterMap(url);

		nestedFields = parameterMap.get("nestedFields");

		Assert.assertTrue(nestedFields.contains("creator"));
		Assert.assertTrue(nestedFields.contains("relation"));
		Assert.assertTrue(nestedFields.contains("status"));
		Assert.assertTrue(nestedFields.split(",").length == 3);

		String nestedFieldsDepth = parameterMap.get("nestedFieldsDepth");

		Assert.assertTrue(nestedFieldsDepth.equals("2"));

		_resetFDSSerializer(fdsAPIURLResolverRegistry);

		// No parameters

		_mockSerializeAPIURL("fdsName", null);

		Assert.assertEquals(
			"/o/app/endpoint",
			_customFDSSerializer.serializeAPIURL(
				"fdsName", httpServletRequest));

		serviceTrackerMap.close();
	}

	@Test
	public void testSerializeCreationMenu() throws Exception {

		// Different creation menu

		_mockSerializeCreationMenu(
			"fdsName1", new String[] {TITLES[0], TITLES[1]});
		_mockSerializeCreationMenu("fdsName2", new String[] {TITLES[2]});

		CreationMenu creationMenu1 = _customFDSSerializer.serializeCreationMenu(
			"fdsName1", httpServletRequest);

		Assert.assertEquals(2, _getPrimaryItemsSize(creationMenu1));
		Assert.assertFalse(_containsTitle(creationMenu1, TITLES[2]));
		Assert.assertTrue(_containsTitle(creationMenu1, TITLES[0]));
		Assert.assertTrue(_containsTitle(creationMenu1, TITLES[1]));

		CreationMenu creationMenu2 = _customFDSSerializer.serializeCreationMenu(
			"fdsName2", httpServletRequest);

		Assert.assertEquals(1, _getPrimaryItemsSize(creationMenu2));
		Assert.assertFalse(_containsTitle(creationMenu2, TITLES[0]));
		Assert.assertFalse(_containsTitle(creationMenu2, TITLES[1]));
		Assert.assertTrue(_containsTitle(creationMenu2, TITLES[2]));

		_resetFDSSerializer();

		// No creation menu

		_mockSerializeCreationMenu("fdsName", null);

		Assert.assertTrue(
			_customFDSSerializer.serializeCreationMenu(
				"fdsName", httpServletRequest
			).isEmpty());

		_resetFDSSerializer();

		// Shared creation menu

		_testSerializeCreationMenu("fdsName1", TITLES);
		_testSerializeCreationMenu("fdsName2", TITLES);
	}

	@Test
	public void testSerializeFilters() throws Exception {

		// Client extension filter

		CETManager cetManager = Mockito.mock(CETManager.class);

		String cetExternalReferenceCode = RandomTestUtil.randomString();

		Mockito.when(
			cetManager.getCET(
				Mockito.anyLong(), Mockito.eq(cetExternalReferenceCode))
		).thenAnswer(
			invocation -> new FDSFilterCET() {

				@Override
				public String getBaseURL() {
					return "";
				}

				@Override
				public long getCompanyId() {
					return invocation.getArgument(0, long.class);
				}

				@Override
				public Date getCreateDate() {
					return null;
				}

				@Override
				public String getDescription() {
					return "";
				}

				@Override
				public String getEditJSP() {
					return "";
				}

				@Override
				public String getExternalReferenceCode() {
					return cetExternalReferenceCode;
				}

				@Override
				public Date getModifiedDate() {
					return null;
				}

				@Override
				public String getName() {
					return "";
				}

				@Override
				public String getName(Locale locale) {
					return "";
				}

				@Override
				public Properties getProperties() {
					return null;
				}

				@Override
				public String getSourceCodeURL() {
					return "";
				}

				@Override
				public int getStatus() {
					return 0;
				}

				@Override
				public String getType() {
					return "";
				}

				@Override
				public String getTypeSettings() {
					return "";
				}

				@Override
				public String getURL() {
					return "/o/" + cetExternalReferenceCode + "/index.js";
				}

				@Override
				public boolean hasProperties() {
					return false;
				}

				@Override
				public boolean isReadOnly() {
					return false;
				}

			}
		);

		_customFDSSerializer.cetManager = cetManager;

		_mockSerializeFilters(
			"fdsName",
			HashMapBuilder.<String, Object>put(
				"clientExtensionEntryERC", cetExternalReferenceCode
			).put(
				"fieldName", FIELD_NAMES[0]
			).put(
				"label", LABELS[0]
			).build());

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"clientExtensionFilterURL",
					"/o/" + cetExternalReferenceCode + "/index.js"
				).put(
					"entityFieldType", "string"
				).put(
					"id", FIELD_NAMES[0]
				).put(
					"label", LABELS[0]
				).put(
					"type", "clientExtension"
				)
			).toString(),
			_customFDSSerializer.serializeFilters(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_resetFDSSerializer();

		// Date range filter

		_mockSerializeFilters(
			"fdsName",
			HashMapBuilder.<String, Object>put(
				"fieldName", FIELD_NAMES[0]
			).put(
				"from", "2000-12-31T00:00:00.000Z"
			).put(
				"label", LABELS[0]
			).put(
				"to", "2025-10-03T00:00:00.000Z"
			).put(
				"type", FDSEntityFieldTypes.DATE
			).build());

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"active", true
				).put(
					"entityFieldType", FDSEntityFieldTypes.DATE
				).put(
					"id", FIELD_NAMES[0]
				).put(
					"label", LABELS[0]
				).put(
					"preloadedData",
					JSONUtil.put(
						"from",
						JSONUtil.put(
							"day", 31
						).put(
							"month", 12
						).put(
							"year", 2000
						)
					).put(
						"to",
						JSONUtil.put(
							"day", 3
						).put(
							"month", 10
						).put(
							"year", 2025
						)
					)
				).put(
					"type", "dateRange"
				)
			).toString(),
			_customFDSSerializer.serializeFilters(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_resetFDSSerializer();

		// Different filters

		_mockSerializeFilters(
			"fdsName1",
			HashMapBuilder.<String, Object>put(
				"fieldName", FIELD_NAMES[0]
			).put(
				"from", "2000-12-31T00:00:00.000Z"
			).put(
				"label", LABELS[0]
			).put(
				"type", FDSEntityFieldTypes.DATE
			).build());
		_mockSerializeFilters(
			"fdsName2",
			HashMapBuilder.<String, Object>put(
				"fieldName", FIELD_NAMES[1]
			).put(
				"label", LABELS[1]
			).put(
				"to", "2025-10-03T00:00:00.000Z"
			).put(
				"type", FDSEntityFieldTypes.DATE
			).build());

		JSONAssert.assertNotEquals(
			_customFDSSerializer.serializeFilters(
				"fdsName1", httpServletRequest
			).toString(),
			_customFDSSerializer.serializeFilters(
				"fdsName2", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_resetFDSSerializer();

		// No filter

		_mockSerializeFilters("fdsName", null);

		JSONAssert.assertEquals(
			"[]",
			_customFDSSerializer.serializeFilters(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_resetFDSSerializer();

		// Selection filter

		_mockSerializeFilters(
			"fdsName",
			HashMapBuilder.<String, Object>put(
				"fieldName", FIELD_NAMES[0]
			).put(
				"include", true
			).put(
				"itemKey", ITEM_KEY
			).put(
				"itemLabel", LABELS[0]
			).put(
				"label", LABELS[1]
			).put(
				"multiple", true
			).put(
				"preselectedValues",
				StringBundler.concat(
					"[{\"label\":\"", LABELS[2], "\",\"value\":\"", IDS[2],
					"\"}]")
			).put(
				"restApplication", "/analytics-settings-rest/v1.0"
			).put(
				"restEndpoint", "/v1.0/channels"
			).put(
				"restSchema", "Channel"
			).put(
				"source", "/o/analytics-settings-rest/v1.0/channels"
			).put(
				"sourceType", "API_REST_APPLICATION"
			).build());

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"apiURL", "/o/analytics-settings-rest/v1.0/channels"
				).put(
					"autocompleteEnabled", true
				).put(
					"entityFieldType", "string"
				).put(
					"id", FIELD_NAMES[0]
				).put(
					"itemKey", ITEM_KEY
				).put(
					"itemLabel", LABELS[0]
				).put(
					"label", LABELS[1]
				).put(
					"multiple", true
				).put(
					"preloadedData",
					JSONUtil.put(
						"exclude", false
					).put(
						"selectedItems",
						JSONUtil.putAll(
							JSONUtil.put(
								"label", LABELS[2]
							).put(
								"value", IDS[2]
							))
					)
				).put(
					"type", "selection"
				)
			).toString(),
			_customFDSSerializer.serializeFilters(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);
	}

	@Test
	public void testSerializeItemsActions() throws Exception {

		// Different items actions

		_mockSerializeItemsActions(
			"fdsName1", new String[] {LABELS[0], LABELS[1]});
		_mockSerializeItemsActions("fdsName2", new String[] {LABELS[2]});

		List<FDSActionDropdownItem> fdsActionDropdownItems1 =
			_customFDSSerializer.serializeItemsActions(
				"fdsName1", httpServletRequest);

		Assert.assertFalse(_containsLabel(fdsActionDropdownItems1, LABELS[2]));
		Assert.assertTrue(_containsLabel(fdsActionDropdownItems1, LABELS[0]));
		Assert.assertTrue(_containsLabel(fdsActionDropdownItems1, LABELS[1]));
		Assert.assertTrue(fdsActionDropdownItems1.size() == 2);

		List<FDSActionDropdownItem> fdsActionDropdownItems2 =
			_customFDSSerializer.serializeItemsActions(
				"fdsName2", httpServletRequest);

		Assert.assertFalse(_containsLabel(fdsActionDropdownItems2, LABELS[1]));
		Assert.assertFalse(_containsLabel(fdsActionDropdownItems2, LABELS[0]));
		Assert.assertTrue(_containsLabel(fdsActionDropdownItems2, LABELS[2]));
		Assert.assertTrue(fdsActionDropdownItems2.size() == 1);

		_resetFDSSerializer();

		// No items actions

		_mockSerializeItemsActions("fdsName", null);

		Assert.assertTrue(
			_customFDSSerializer.serializeItemsActions(
				"fdsName", httpServletRequest
			).isEmpty());

		_resetFDSSerializer();

		// Shared items actions

		_testSerializeItemsActions("fdsName1", LABELS);
		_testSerializeItemsActions("fdsName2", LABELS);
	}

	@Test
	public void testSerializeViews() throws Exception {

		// Cards view

		mockLanguage();

		_mockSerializeViewsCardsOrList(
			"fdsName",
			HashMapBuilder.put(
				"image", IMAGES[0]
			).put(
				"title", TITLES[0]
			).build(),
			"dataSetToDataSetCardsSections");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"contentRenderer", "cards"
				).put(
					"default", false
				).put(
					"name", "cards"
				).put(
					"schema",
					JSONUtil.put(
						"image", IMAGES[0]
					).put(
						"title", TITLES[0]
					)
				).put(
					"thumbnail", "cards2"
				)
			).toString(),
			_customFDSSerializer.serializeViews(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_resetFDSSerializer();

		// Different views

		_mockSerializeViewsCardsOrList(
			"fdsName1",
			HashMapBuilder.put(
				"image", IMAGES[0]
			).put(
				"title", TITLES[0]
			).build(),
			"dataSetToDataSetCardsSections");

		_mockSerializeViewsCardsOrList(
			"fdsName2",
			HashMapBuilder.put(
				"image", IMAGES[1]
			).put(
				"title", TITLES[1]
			).build(),
			"dataSetToDataSetCardsSections");

		JSONAssert.assertNotEquals(
			_customFDSSerializer.serializeViews(
				"fdsName1", httpServletRequest
			).toString(),
			_customFDSSerializer.serializeViews(
				"fdsName2", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_resetFDSSerializer();

		// Empty view

		_mockSerializeViewsCardsOrList(
			"fdsName", Collections.emptyMap(), "dataSetToDataSetListSections");

		JSONAssert.assertEquals(
			"[]",
			_customFDSSerializer.serializeViews(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_resetFDSSerializer();

		// List view

		_mockSerializeViewsCardsOrList(
			"fdsName",
			HashMapBuilder.put(
				"image", IMAGES[0]
			).put(
				"title", TITLES[0]
			).build(),
			"dataSetToDataSetListSections");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"contentRenderer", "list"
				).put(
					"default", false
				).put(
					"name", "list"
				).put(
					"schema",
					JSONUtil.put(
						"image", IMAGES[0]
					).put(
						"title", TITLES[0]
					)
				).put(
					"thumbnail", "list"
				)
			).toString(),
			_customFDSSerializer.serializeViews(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_resetFDSSerializer();

		// Shared view

		Map<String, String> sectionsMap = HashMapBuilder.put(
			"image", IMAGES[0]
		).put(
			"title", TITLES[0]
		).build();

		_mockSerializeViewsCardsOrList(
			"fdsName1", sectionsMap, "dataSetToDataSetListSections");

		_mockSerializeViewsCardsOrList(
			"fdsName2", sectionsMap, "dataSetToDataSetListSections");

		JSONAssert.assertEquals(
			_customFDSSerializer.serializeViews(
				"fdsName1", httpServletRequest
			).toString(),
			_customFDSSerializer.serializeViews(
				"fdsName2", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		// Table view

		List<Map<String, Object>> tableSectionObjectEntriesProperties =
			new ArrayList<>();

		tableSectionObjectEntriesProperties.add(
			HashMapBuilder.<String, Object>put(
				"fieldName", FIELD_NAMES[0]
			).put(
				"label", LABELS[0]
			).put(
				"renderer", "ActionLink"
			).put(
				"rendererType", "internal"
			).put(
				"sortable", true
			).put(
				"type", "String"
			).build());

		String cetExternalReferenceCode = RandomTestUtil.randomString();

		tableSectionObjectEntriesProperties.add(
			HashMapBuilder.<String, Object>put(
				"fieldName", FIELD_NAMES[1]
			).put(
				"label", LABELS[1]
			).put(
				"renderer", cetExternalReferenceCode
			).put(
				"rendererType", "clientExtension"
			).put(
				"sortable", false
			).put(
				"type", "String"
			).build());

		tableSectionObjectEntriesProperties.add(
			HashMapBuilder.<String, Object>put(
				"fieldName", FIELD_NAMES[2]
			).put(
				"label", LABELS[2]
			).put(
				"renderer", "default"
			).put(
				"rendererType", "internal"
			).put(
				"sortable", false
			).put(
				"type", "String"
			).build());

		_mockSerializeViewsTable(
			"fdsName", tableSectionObjectEntriesProperties);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"contentRenderer", "table"
				).put(
					"default", false
				).put(
					"name", "table"
				).put(
					"schema",
					JSONUtil.put(
						"fields",
						JSONUtil.putAll(
							JSONUtil.put(
								"contentRenderer", "ActionLink"
							).put(
								"fieldName", FIELD_NAMES[0]
							).put(
								"label", LABELS[0]
							).put(
								"sortable", true
							),
							JSONUtil.put(
								"contentRenderer", cetExternalReferenceCode
							).put(
								"contentRendererClientExtension", true
							).put(
								"contentRendererModuleURL",
								"default from /o/" + cetExternalReferenceCode +
									"/index.js"
							).put(
								"fieldName", FIELD_NAMES[1]
							).put(
								"label", LABELS[1]
							).put(
								"sortable", false
							),
							JSONUtil.put(
								"contentRenderer", "default"
							).put(
								"fieldName", FIELD_NAMES[2]
							).put(
								"label", LABELS[2]
							).put(
								"sortable", false
							))
					).put(
						"thumbnail", "table"
					)
				)
			).toString(),
			_customFDSSerializer.serializeViews(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_resetFDSSerializer();
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

	private void _mockSerializeAPIURL(String fdsName, String[] fieldNames) {
		String restApplication = "/app";

		String restEndpoint = "/endpoint";

		String restSchema = "schema";

		Mockito.when(
			_customFDSSerializer.createFDSAPIURLBuilder(
				httpServletRequest, restApplication, restEndpoint, restSchema)
		).thenCallRealMethod();

		Mockito.when(
			_customFDSSerializer.getDataSetObjectEntryProperties(
				fdsName, httpServletRequest)
		).thenReturn(
			HashMapBuilder.<String, Object>put(
				"restApplication", restApplication
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
					HashMapBuilder.<String, Object>put(
						"fieldName", fieldName
					).build());

				return objectEntry;
			});

		Mockito.when(
			_customFDSSerializer.getSortedRelatedObjectEntries(
				fdsName, httpServletRequest, null, "tableSectionsOrder",
				"dataSetToDataSetTableSections")
		).thenReturn(
			objectEntries
		);

		Mockito.when(
			_customFDSSerializer.serializeAPIURL(fdsName, httpServletRequest)
		).thenCallRealMethod();
	}

	private void _mockSerializeCreationMenu(String fdsName, String[] titles) {
		List<ObjectEntry> objectEntries = TransformUtil.transformToList(
			titles,
			title -> {
				ObjectEntry objectEntry = new ObjectEntry();

				objectEntry.setProperties(
					HashMapBuilder.<String, Object>put(
						"title", title
					).build());

				return objectEntry;
			});

		Mockito.when(
			_customFDSSerializer.getSortedRelatedObjectEntries(
				Mockito.eq(fdsName), Mockito.eq(httpServletRequest),
				Mockito.any(), Mockito.eq("creationActionsOrder"),
				Mockito.eq("dataSetToDataSetActions"))
		).thenReturn(
			objectEntries
		);

		Mockito.when(
			_customFDSSerializer.serializeCreationMenu(
				fdsName, httpServletRequest)
		).thenCallRealMethod();
	}

	private void _mockSerializeFilters(
		String fdsName, Map<String, Object> properties) {

		Mockito.when(
			_customFDSSerializer.serializeFilters(fdsName, httpServletRequest)
		).thenCallRealMethod();

		List<ObjectEntry> objectEntries = new ArrayList<>();

		if (properties != null) {
			ObjectEntry objectEntry = new ObjectEntry();

			objectEntry.setProperties(properties);

			objectEntries.add(objectEntry);
		}

		Mockito.when(
			_customFDSSerializer.getSortedRelatedObjectEntries(
				Mockito.eq(fdsName), Mockito.eq(httpServletRequest),
				Mockito.any(), Mockito.eq("filtersOrder"),
				Mockito.eq("dataSetToDataSetClientExtensionFilters"),
				Mockito.eq("dataSetToDataSetDateFilters"),
				Mockito.eq("dataSetToDataSetSelectionFilters"))
		).thenReturn(
			objectEntries
		);
	}

	private void _mockSerializeItemsActions(String fdsName, String[] labels) {
		List<ObjectEntry> objectEntries = TransformUtil.transformToList(
			labels,
			label -> {
				ObjectEntry objectEntry = new ObjectEntry();

				objectEntry.setProperties(
					HashMapBuilder.<String, Object>put(
						"label", label
					).build());

				return objectEntry;
			});

		Mockito.when(
			_customFDSSerializer.getSortedRelatedObjectEntries(
				Mockito.eq(fdsName), Mockito.eq(httpServletRequest),
				Mockito.any(), Mockito.eq("itemActionsOrder"),
				Mockito.eq("dataSetToDataSetActions"))
		).thenReturn(
			objectEntries
		);

		Mockito.when(
			_customFDSSerializer.serializeItemsActions(
				fdsName, httpServletRequest)
		).thenCallRealMethod();
	}

	private void _mockSerializeViewsCardsOrList(
		String fdsName, Map<String, String> sectionMap, String relationship) {

		Mockito.when(
			_customFDSSerializer.serializeViews(fdsName, httpServletRequest)
		).thenCallRealMethod();

		List<ObjectEntry> objectEntries = new ArrayList<>();

		for (Map.Entry<String, String> sectionMapEntry :
				sectionMap.entrySet()) {

			ObjectEntry objectEntry = new ObjectEntry();

			objectEntry.setProperties(
				HashMapBuilder.<String, Object>put(
					"fieldName", sectionMapEntry.getValue()
				).put(
					"name", sectionMapEntry.getKey()
				).build());

			objectEntries.add(objectEntry);
		}

		Mockito.when(
			_customFDSSerializer.getRelatedObjectEntries(
				Mockito.eq(fdsName), Mockito.eq(httpServletRequest),
				Mockito.eq((Predicate)null), Mockito.eq(relationship))
		).thenReturn(
			objectEntries
		);
	}

	private void _mockSerializeViewsTable(
		String fdsName, List<Map<String, Object>> propertiesMapList) {

		Mockito.when(
			_customFDSSerializer.serializeViews(fdsName, httpServletRequest)
		).thenCallRealMethod();

		List<ObjectEntry> objectEntries = new ArrayList<>();

		for (Map<String, Object> propertiesMap : propertiesMapList) {
			ObjectEntry objectEntry = new ObjectEntry();

			objectEntry.setProperties(propertiesMap);

			objectEntries.add(objectEntry);

			String rendererType = String.valueOf(
				propertiesMap.get("rendererType"));

			if (Validator.isNotNull(rendererType) &&
				rendererType.equals("clientExtension")) {

				String clientExtensionEntryERC = String.valueOf(
					propertiesMap.get("renderer"));

				CETManager cetManager = Mockito.mock(CETManager.class);

				Mockito.when(
					cetManager.getCET(
						Mockito.anyLong(), Mockito.eq(clientExtensionEntryERC))
				).thenAnswer(
					invocation -> new FDSCellRendererCET() {

						@Override
						public String getBaseURL() {
							return "";
						}

						@Override
						public long getCompanyId() {
							return invocation.getArgument(0, long.class);
						}

						@Override
						public Date getCreateDate() {
							return null;
						}

						@Override
						public String getDescription() {
							return "";
						}

						@Override
						public String getEditJSP() {
							return "";
						}

						@Override
						public String getExternalReferenceCode() {
							return clientExtensionEntryERC;
						}

						@Override
						public Date getModifiedDate() {
							return null;
						}

						@Override
						public String getName() {
							return "";
						}

						@Override
						public String getName(Locale locale) {
							return "";
						}

						@Override
						public Properties getProperties() {
							return null;
						}

						@Override
						public String getSourceCodeURL() {
							return "";
						}

						@Override
						public int getStatus() {
							return 0;
						}

						@Override
						public String getType() {
							return "";
						}

						@Override
						public String getTypeSettings() {
							return "";
						}

						@Override
						public String getURL() {
							return "/o/" + clientExtensionEntryERC +
								"/index.js";
						}

						@Override
						public boolean hasProperties() {
							return false;
						}

						@Override
						public boolean isReadOnly() {
							return false;
						}

					}
				);

				_customFDSSerializer.cetManager = cetManager;
			}
		}

		Mockito.when(
			_customFDSSerializer.getSortedRelatedObjectEntries(
				fdsName, httpServletRequest, (Predicate)null,
				"tableSectionsOrder", "dataSetToDataSetTableSections")
		).thenReturn(
			objectEntries
		);
	}

	private void _resetFDSSerializer() {
		_customFDSSerializer = Mockito.mock(CustomFDSSerializer.class);

		ReflectionTestUtil.setFieldValue(
			_customFDSSerializer, "_jsonFactory", new JSONFactoryImpl());
	}

	private void _resetFDSSerializer(
		FDSAPIURLResolverRegistry fdsAPIURLResolverRegistry) {

		_resetFDSSerializer();

		_customFDSSerializer.fdsAPIURLResolverRegistry =
			fdsAPIURLResolverRegistry;
	}

	private void _testSerializeCreationMenu(String fdsName, String[] titles) {
		_mockSerializeCreationMenu(fdsName, titles);

		CreationMenu creationMenu = _customFDSSerializer.serializeCreationMenu(
			fdsName, httpServletRequest);

		for (String title : titles) {
			Assert.assertTrue(_containsTitle(creationMenu, title));
		}

		Assert.assertEquals(titles.length, _getPrimaryItemsSize(creationMenu));
	}

	private void _testSerializeItemsActions(String fdsName, String[] labels) {
		_mockSerializeItemsActions(fdsName, labels);

		List<FDSActionDropdownItem> fdsActionDropdownItems =
			_customFDSSerializer.serializeItemsActions(
				fdsName, httpServletRequest);

		for (String label : labels) {
			Assert.assertTrue(_containsLabel(fdsActionDropdownItems, label));
		}

		Assert.assertTrue(labels.length == fdsActionDropdownItems.size());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomFDSSerializerTest.class);

	private static BundleContext _bundleContext;
	private static CustomFDSSerializer _customFDSSerializer;

}