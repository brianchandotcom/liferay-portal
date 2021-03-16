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

package com.liferay.portal.search.tuning.blueprints.searchrequest.contributor.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
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
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilder;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilderFactory;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter.ParameterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.cache.JSONDataProviderCache;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintsEngineHelper;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class BlueprintsSearchRequestContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testIPStack() throws Exception {
		_addCompanyBlueprint(readConfiguration());

		_addJournalArticles("walnut", "diamond Bar");

		_setupJsonDataProviderCache();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				getIPStackConfigurationTemporarySwapper("91.233.116.229")) {

			_assertSearch(
				"[walnut]", "localized_title_en_US", "91.233.116.229", "");
		}

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				getIPStackConfigurationTemporarySwapper("104.172.41.95")) {

			_assertSearch(
				"[diamond bar]", "localized_title_en_US", "104.172.41.95", "");
		}
	}

	@Test
	public void testQuery() throws Exception {
		_addCompanyBlueprint(readConfiguration());

		_addJournalArticles("alpha", "beta", "charlie");

		_assertSearch("[beta]", "localized_title_en_US", "", "beta");
	}

	@Test
	public void testSort() throws Exception {
		_addCompanyBlueprint(readConfiguration());

		_addJournalArticles("alpha delta", "beta delta", "charlie delta");

		_assertSearch(
			"[charlie delta, beta delta, alpha delta]", "localized_title_en_US",
			"127.0.0.1", "delta");
	}

	@Rule
	public TestName testName = new TestName();

	protected BlueprintsAttributes getBlueprintsAttributes(
			String keywords, String ipAddress)
		throws Exception {

		TimeZone timeZone = _user.getTimeZone();

		BlueprintsAttributesBuilder blueprintsAttributesBuilder =
			blueprintsAttributesBuilderFactory.builder();

		blueprintsAttributesBuilder.companyId(
			_group.getCompanyId()
		).keywords(
			keywords
		).locale(
			LocaleUtil.US
		).userId(
			_user.getUserId()
		).addAttribute(
			ParameterConfigurationKeys.PAGE.getJsonKey(), 1
		).addAttribute(
			ReservedParameterNames.IP_ADDRESS.getKey(), ipAddress
		).addAttribute(
			ReservedParameterNames.PLID.getKey(), TestPropsValues.getPlid()
		).addAttribute(
			ReservedParameterNames.SCOPE_GROUP_ID.getKey(), _group.getGroupId()
		).addAttribute(
			ReservedParameterNames.TIMEZONE_ID.getKey(), timeZone.getID()
		);

		return blueprintsAttributesBuilder.build();
	}

	protected ConfigurationTemporarySwapper
			getIPStackConfigurationTemporarySwapper(String ip)
		throws Exception {

		return new ConfigurationTemporarySwapper(
			"com.liferay.portal.search.tuning.blueprints.ipstack.internal." +
				"configuration.IPStackConfiguration",
			_toDictionary(
				HashMapBuilder.put(
					"apiKey", "2345"
				).put(
					"isEnabled", "true"
				).put(
					"testIpAddress", ip
				).build()));
	}

	protected String readConfiguration() {
		Class<?> clazz = getClass();

		StringBundler sb = new StringBundler(5);

		sb.append(clazz.getSimpleName());
		sb.append(CharPool.PERIOD);
		sb.append(testName.getMethodName());
		sb.append(CharPool.PERIOD);
		sb.append("json");

		return StringUtil.read(clazz, sb.toString());
	}

	@Inject
	protected BlueprintsAttributesBuilderFactory
		blueprintsAttributesBuilderFactory;

	private Blueprint _addCompanyBlueprint(String configurationString)
		throws Exception {

		Blueprint blueprint = _blueprintService.addCompanyBlueprint(
			Collections.singletonMap(LocaleUtil.US, "testTitle"),
			Collections.singletonMap(LocaleUtil.US, "testDescription"),
			configurationString, "", 1, _getServiceContext());

		_blueprint = blueprint;

		return blueprint;
	}

	private void _addJournalArticles(String... titles) throws Exception {
		for (String title : titles) {
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				PortalUtil.getClassNameId(JournalArticle.class),
				HashMapBuilder.put(
					LocaleUtil.US, title
				).build(),
				null,
				HashMapBuilder.put(
					LocaleUtil.US, ""
				).build(),
				LocaleUtil.getSiteDefault(), false, true, _serviceContext);
		}
	}

	private void _assertSearch(
			String expected, String fieldName, String ipAddress,
			String keywords)
		throws Exception {

		SearchResponse searchResponse = _blueprintsEngineHelper.search(
			_blueprint, getBlueprintsAttributes(keywords, ipAddress),
			new Messages());

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), fieldName, expected);
	}

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());
	}

	private void _setupJsonDataProviderCache() {
		_jsonDataProviderCache.put(
			"91.233.116.229",
			JSONUtil.put(
				"city", "walnut"
			).put(
				"latitude", 123
			).put(
				"longitude", 102
			));

		_jsonDataProviderCache.put(
			"104.172.41.95",
			JSONUtil.put(
				"city", "diamond bar"
			).put(
				"latitude", 123
			).put(
				"longitude", 102
			));
	}

	private Dictionary<String, Object> _toDictionary(Map<String, String> map) {
		return new HashMapDictionary<>(new HashMap<String, Object>(map));
	}

	private Blueprint _blueprint;

	@Inject
	private BlueprintsEngineHelper _blueprintsEngineHelper;

	@Inject
	private BlueprintService _blueprintService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JSONDataProviderCache _jsonDataProviderCache;

	private ServiceContext _serviceContext;
	private User _user;

}