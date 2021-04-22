/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.test;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilder;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilderFactory;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.BlueprintKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.advanced.AdvancedConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.advanced.QueryProcessingConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.advanced.SourceConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.framework.FrameworkConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter.ParameterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.cache.JSONDataProviderCache;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.exception.BlueprintsEngineException;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintsEngineHelper;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsBlueprintKeys;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Rule;

/**
 * @author Wade Cao
 */
public abstract class BaseBlueprintsTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group, TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		user = TestPropsValues.getUser();
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Blueprint addCompanyBlueprint(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String configuration, String selectedElements)
		throws Exception {

		return blueprintService.addCompanyBlueprint(
			titleMap, descriptionMap, configuration, selectedElements,
			serviceContext);
	}

	protected void addExpandoColumn(int indexType, String... columns)
		throws Exception {

		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			group.getCompanyId(),
			_classNameLocalService.getClassNameId(JournalArticle.class),
			"CUSTOM_FIELDS");

		if (expandoTable == null) {
			expandoTable = _expandoTableLocalService.addTable(
				group.getCompanyId(),
				_classNameLocalService.getClassNameId(JournalArticle.class),
				"CUSTOM_FIELDS");

			_expandoTables.add(expandoTable);
		}

		for (String column : columns) {
			ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
				expandoTable, column, indexType);

			_expandoColumns.add(expandoColumn);

			UnicodeProperties unicodeProperties =
				expandoColumn.getTypeSettingsProperties();

			unicodeProperties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));

			expandoColumn.setTypeSettingsProperties(unicodeProperties);

			_expandoColumnLocalService.updateExpandoColumn(expandoColumn);
		}
	}

	protected Blueprint addGroupBlueprint(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String configuration, String selectedElements)
		throws Exception {

		return blueprintService.addGroupBlueprint(
			titleMap, descriptionMap, configuration, selectedElements,
			serviceContext);
	}

	protected JournalArticle addJournalArticle(
			long groupId, long folderId, String title, String content)
		throws Exception {

		return JournalTestUtil.addArticle(
			groupId, folderId, PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, title
			).build(),
			null,
			HashMapBuilder.put(
				LocaleUtil.US, content
			).build(),
			LocaleUtil.getSiteDefault(), false, true, serviceContext);
	}

	protected JournalArticle addJournalArticle(long groupId, String title)
		throws Exception {

		return addJournalArticle(groupId, title, "");
	}

	protected JournalArticle addJournalArticle(
			long groupId, String title, String content)
		throws Exception {

		return JournalTestUtil.addArticle(
			groupId, 0, PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, title
			).build(),
			null,
			HashMapBuilder.put(
				LocaleUtil.US, content
			).build(),
			LocaleUtil.getSiteDefault(), false, true, serviceContext);
	}

	protected JournalArticle addJournalArticle(String title, String content)
		throws Exception {

		return addJournalArticle(group.getGroupId(), title, content);
	}

	protected void assertSearch(
			Blueprint blueprint, String configurationString, String expected,
			String keywords, String selectedElementString)
		throws Exception {

		assertSearch(
			blueprint, configurationString, expected, "127.0.0.1", keywords,
			selectedElementString);
	}

	protected void assertSearch(
			Blueprint blueprint, String configurationString, String expected,
			String ipAddress, String keywords, String selectedElementString)
		throws Exception {

		SearchResponse searchResponse = _getSearchResponse(
			blueprint, configurationString, ipAddress, keywords,
			selectedElementString);

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), "localized_title_en_US",
			expected);
	}

	protected void assertSearchIgnoreRelevance(
			Blueprint blueprint, String configurationString, String expected,
			String keywords, String selectedElementString)
		throws Exception {

		SearchResponse searchResponse = _getSearchResponse(
			blueprint, configurationString, "127.0.0.1", keywords,
			selectedElementString);

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), "localized_title_en_US",
			expected);
	}

	protected JSONArray createJSONArray() {
		return JSONFactoryUtil.createJSONArray();
	}

	protected JSONObject getAdvancedConfiguration() {
		return JSONUtil.put(
			AdvancedConfigurationKeys.QUERY_PROCESSING.getJsonKey(),
			JSONUtil.put(
				QueryProcessingConfigurationKeys.EXCLUDE_QUERY_CONTRIBUTORS.
					getJsonKey(),
				""
			).put(
				QueryProcessingConfigurationKeys.EXCLUDE_QUERY_POST_PROCESSORS.
					getJsonKey(),
				""
			)
		).put(
			"source",
			JSONUtil.put(
				SourceConfigurationKeys.FETCH_SOURCE.getJsonKey(), true
			).put(
				SourceConfigurationKeys.SOURCE_EXCLUDES.getJsonKey(), ""
			).put(
				SourceConfigurationKeys.SOURCE_INCLUDES.getJsonKey(), ""
			)
		);
	}

	protected JSONArray getAggregationConfiguration() {
		return createJSONArray();
	}

	protected BlueprintsAttributes getBlueprintsAttributes(
			String ipAddress, String keywords)
		throws Exception {

		BlueprintsAttributesBuilder blueprintsAttributesBuilder =
			blueprintsAttributesBuilderFactory.builder();

		blueprintsAttributesBuilder.companyId(
			group.getCompanyId()
		).keywords(
			keywords
		).locale(
			LocaleUtil.US
		).userId(
			user.getUserId()
		).addAttribute(
			ReservedParameterNames.EXPLAIN.getKey(), true
		).addAttribute(
			ParameterConfigurationKeys.PAGE.getJsonKey(), 1
		).addAttribute(
			ReservedParameterNames.IP_ADDRESS.getKey(), ipAddress
		).addAttribute(
			ReservedParameterNames.PLID.getKey(), TestPropsValues.getPlid()
		).addAttribute(
			ReservedParameterNames.SCOPE_GROUP_ID.getKey(), group.getGroupId()
		).addAttribute(
			ReservedParameterNames.TIMEZONE_ID.getKey(), getTimeZoneID()
		);

		return blueprintsAttributesBuilder.build();
	}

	protected JSONObject getConfigurationJSONObject(JSONArray queryJSONArray) {
		return JSONUtil.put(
			BlueprintKeys.ADVANCED_CONFIGURATION.getJsonKey(),
			getAdvancedConfiguration()
		).put(
			BlueprintKeys.AGGREGATION_CONFIGURATION.getJsonKey(),
			getAggregationConfiguration()
		).put(
			FacetsBlueprintKeys.CONFIGURATION_SECTION,
			getConfigurationSection()
		).put(
			BlueprintKeys.FRAMEWORK_CONFIGURATION.getJsonKey(),
			getFrameworkConfiguration()
		).put(
			BlueprintKeys.PARAMETER_CONFIGURATION.getJsonKey(),
			JSONUtil.put(null, null)
		).put(
			BlueprintKeys.QUERY_CONFIGURATION.getJsonKey(), queryJSONArray
		).put(
			BlueprintKeys.SORT_CONFIGURATION.getJsonKey(),
			getSortConfiguration()
		);
	}

	protected JSONArray getConfigurationSection() {
		return createJSONArray();
	}

	protected String getConfigurationString(JSONObject... jsonObjects) {
		JSONArray jsonArray = createJSONArray();

		if (jsonObjects != null) {
			for (JSONObject jsonObject : jsonObjects) {
				jsonArray.put(jsonObject);
			}
		}

		JSONObject configurationJSONObject = getConfigurationJSONObject(
			jsonArray);

		return configurationJSONObject.toString();
	}

	protected JSONObject getElementTemplateJSONObject(String resourceName)
		throws Exception {

		String boostWebContentsByKeywordMatchJsonString = StringUtil.read(
			getClass(), resourceName);

		return JSONFactoryUtil.createJSONObject(
			boostWebContentsByKeywordMatchJsonString);
	}

	protected JSONObject getFrameworkConfiguration() {
		return JSONUtil.put(
			FrameworkConfigurationKeys.APPLY_INDEXER_CLAUSES.getJsonKey(),
			true);
	}

	protected String getGeolocationValue(double latitude, double longitude) {
		return StringBundler.concat(
			"{\"latitude\":", String.valueOf(latitude), ", \"longitude\":",
			String.valueOf(longitude), "}");
	}

	protected ConfigurationTemporarySwapper
			getIPStackConfigurationTemporarySwapper(
				String apiKey, String isEnabled, String ip)
		throws Exception {

		return new ConfigurationTemporarySwapper(
			"com.liferay.portal.search.tuning.blueprints.ipstack.internal." +
				"configuration.IPStackConfiguration",
			_toDictionary(
				HashMapBuilder.put(
					"apiKey", apiKey
				).put(
					"isEnabled", isEnabled
				).put(
					"testIpAddress", ip
				).build()));
	}

	protected JSONObject getParameterConfiguration() {
		return JSONUtil.put(null, null);
	}

	protected JSONObject getSortConfiguration() {
		return JSONUtil.put(null, null);
	}

	protected String getTimeZoneID() throws Exception {
		TimeZone timeZone = user.getTimeZone();

		return timeZone.getID();
	}

	protected void setupJsonDataProviderCache(
		String ipAddress, String city, double latitude, double longitude) {

		_jsonDataProviderCache.put(
			ipAddress,
			JSONUtil.put(
				"city", city
			).put(
				"latitude", latitude
			).put(
				"longitude", longitude
			));
	}

	protected JournalArticle updateJournalArticle(
			JournalArticle journalArticle, String title, String content)
		throws Exception {

		return JournalTestUtil.updateArticle(
			journalArticle, title, content, false, true, serviceContext);
	}

	@Inject
	protected BlueprintsAttributesBuilderFactory
		blueprintsAttributesBuilderFactory;

	@Inject
	protected BlueprintsEngineHelper blueprintsEngineHelper;

	@Inject
	protected BlueprintService blueprintService;

	@DeleteAfterTestRun
	protected Group group;

	protected ServiceContext serviceContext;
	protected User user;

	private SearchResponse _getSearchResponse(
			Blueprint blueprint, String configurationString, String ipAddress,
			String keywords, String selectedElementString)
		throws BlueprintsEngineException, Exception, PortalException {

		if (!Validator.isBlank(configurationString) &&
			!Validator.isBlank(selectedElementString)) {

			blueprint = blueprintService.updateBlueprint(
				blueprint.getBlueprintId(), blueprint.getTitleMap(),
				blueprint.getDescriptionMap(), configurationString,
				selectedElementString, serviceContext);
		}

		return blueprintsEngineHelper.search(
			blueprint, getBlueprintsAttributes(ipAddress, keywords),
			new Messages());
	}

	private Dictionary<String, Object> _toDictionary(Map<String, String> map) {
		return new HashMapDictionary<>(new HashMap<String, Object>(map));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@DeleteAfterTestRun
	private List<ExpandoColumn> _expandoColumns = new ArrayList<>();

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@DeleteAfterTestRun
	private List<ExpandoTable> _expandoTables = new ArrayList<>();

	@Inject
	private JSONDataProviderCache _jsonDataProviderCache;

}