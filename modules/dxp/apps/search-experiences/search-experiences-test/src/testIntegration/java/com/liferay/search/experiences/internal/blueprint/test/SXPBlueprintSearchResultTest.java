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

package com.liferay.search.experiences.internal.blueprint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.search.experiences.blueprint.search.request.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ConfigurationUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalServiceUtil;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPBlueprintSearchResultTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		_sxpElements = SXPElementLocalServiceUtil.getSXPElements(
			TestPropsValues.getCompanyId(), true);
	}

	@AfterClass
	public static void tearDownClass() {
		WorkflowThreadLocal.setEnabled(true);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());

		_user = TestPropsValues.getUser();

		_sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			_user.getUserId(), _configurationJSONObject.toString(),
			Collections.singletonMap(LocaleUtil.US, ""), null, "",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			_serviceContext);
	}

	@Test
	public void testBoostAssetType() throws Exception {
		_updateConfigurationJSON(
			"generalConfiguration",
			JSONUtil.put(
				"searchableAssetTypes",
				JSONUtil.putAll(
					"com.liferay.journal.model.JournalArticle",
					"com.liferay.journal.model.JournalFolder")));

		_journalFolder = JournalFolderServiceUtil.addFolder(
			null, _group.getGroupId(), 0, "Folder cola", StringPool.BLANK,
			_serviceContext);

		_setUpJournalArticles(
			new String[] {"cola", ""},
			new String[] {"Article coca cola", "Article pepsi cola"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 10000
				).put(
					"entry_class_name",
					"com.liferay.journal.model.JournalArticle"
				).build()
			},
			new String[] {"Boost Asset Type"});

		_keywords = "cola";

		_assertSearch("[Article coca cola, Article pepsi cola, Folder cola]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Folder cola, Article coca cola, Article pepsi cola]");
	}

	@Test
	public void testBoostContentsForTheCurrentLanguage() throws Exception {
		_setUpJournalArticles(
			new String[] {"Article", ""},
			new String[] {"Article beta en_US", "Article delta en_US"});

		LocaleThreadLocal.setDefaultLocale(LocaleUtil.SPAIN);

		_setUpJournalArticles(
			new String[] {"Article Article", ""},
			new String[] {"Article alpha es_ES", "Article omega es_ES"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 1000
				).build()
			},
			new String[] {"Boost Contents for the Current Language"});

		_keywords = "Article";

		_assertSearch(
			"[Article alpha es_ES, Article omega es_ES, Article beta en_US, " +
				"Article delta en_US]");

		LocaleThreadLocal.setDefaultLocale(LocaleUtil.US);

		_assertSearch(
			"[Article beta en_US, Article delta en_US, Article alpha es_ES, " +
				"Article omega es_ES]");

		_updateElementInstancesJSON(null, null);

		_assertSearch(
			"[Article alpha es_ES, Article beta en_US, Article delta en_US, " +
				"Article omega es_ES]");
	}

	@Test
	public void testBoostContentsInACategory() throws Exception {
		_addAssetCategory("Important", _user);

		_addGroupAAndGroupB();

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Article", "Article With Category"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_ids",
					new String[] {
						String.valueOf(_assetCategory.getCategoryId())
					}
				).put(
					"boost", 100
				).build()
			},
			new String[] {"Boost Contents in a Category"});

		_keywords = "Article";

		_assertSearch("[Article With Category, Article]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[Article, Article With Category]");
	}

	@Test
	public void testBoostContentsInACategoryByKeywordMatch() throws Exception {
		_addAssetCategory("Promoted", _addGroupUser(_group, "Employee"));

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Article", "Article With Category"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).put(
					"boost", 100
				).put(
					"keywords", "Article"
				).build()
			},
			new String[] {"Boost Contents in a Category by Keyword Match"});

		_keywords = "Article";

		_assertSearch("[Article With Category, Article]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Article, Article With Category]");
	}

	@Test
	public void testBoostContentsInACategoryForAPeriodOfTime()
		throws Exception {

		_addAssetCategory("Promoted", _addGroupUser(_group, "Customers"));

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Article", "Article With Category"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).put(
					"boost", 1000
				).put(
					"end_date",
					DateUtil.getDate(
						new Date(System.currentTimeMillis() + Time.DAY),
						"yyyyMMdd", LocaleUtil.US)
				).put(
					"start_date",
					DateUtil.getDate(
						new Date(System.currentTimeMillis() - Time.DAY),
						"yyyyMMdd", LocaleUtil.US)
				).build()
			},
			new String[] {"Boost Contents in a Category for a Period of Time"});

		_keywords = "Article";

		_assertSearch("[Article With Category, Article]");

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).put(
					"boost", 1000
				).put(
					"end_date",
					DateUtil.getDate(
						new Date(System.currentTimeMillis() - Time.DAY),
						"yyyyMMdd", LocaleUtil.US)
				).put(
					"start_date",
					DateUtil.getDate(
						new Date(System.currentTimeMillis() - (Time.DAY * 2)),
						"yyyyMMdd", LocaleUtil.US)
				).build()
			},
			new String[] {"Boost Contents in a Category for a Period of Time"});

		_assertSearch("[Article, Article With Category]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Article, Article With Category]");
	}

	@Test
	public void testBoostContentsInACategoryForAUserSegment() throws Exception {
		_addAssetCategory("Promoted", _addGroupUser(_group, "Employee"));

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Article", "Article With Category"});

		_keywords = "Article";

		SegmentsEntry segmentsEntry = _addSegmentsEntry(_user);

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).put(
					"boost", 1000
				).put(
					"user_segment_ids", segmentsEntry.getSegmentsEntryId()
				).build()
			},
			new String[] {"Boost Contents in a Category for User Segments"});

		_assertSearch("[Article With Category, Article]");

		User user = UserTestUtil.addUser(_group.getGroupId());

		_serviceContext.setUserId(user.getUserId());

		_assertSearch("[Article, Article With Category]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Article, Article With Category]");
	}

	@Test
	public void testBoostContentsInACategoryForGuestUsers() throws Exception {
		_addAssetCategory("Promoted", _user);

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Article", "Article With Category"});

		User guestUser = _userLocalService.getDefaultUser(
			_group.getCompanyId());

		_serviceContext.setUserId(guestUser.getUserId());

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).put(
					"boost", 100
				).build()
			},
			new String[] {"Boost Contents in a Category for Guest Users"});

		_keywords = "Article";

		_assertSearch("[Article With Category, Article]");

		User user = UserTestUtil.addUser(_group.getGroupId());

		_serviceContext.setUserId(user.getUserId());

		_assertSearch("[Article, Article With Category]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Article, Article With Category]");
	}

	@Test
	public void testBoostContentsInACategoryForNewUserAccounts()
		throws Exception {

		_addAssetCategory("New User", _addGroupUser(_group, "Employee"));

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Article", "Article With Category"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).put(
					"boost", 1000
				).put(
					"time_range", "30d"
				).build()
			},
			new String[] {
				"Boost Contents in a Category for New User Accounts"
			});

		_keywords = "Article";

		_assertSearch("[Article With Category, Article]");

		User user = UserTestUtil.addUser(_group.getGroupId());

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.DAY_OF_MONTH, -60);

		Date date = calendar.getTime();

		user.setCreateDate(date);

		_userLocalService.updateUser(user);

		_serviceContext.setUserId(user.getUserId());

		_assertSearch("[Article, Article With Category]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Article, Article With Category]");
	}

	@Test
	public void testBoostContentsInACategoryForTheTimeOfDay() throws Exception {
		LocalDateTime localDateTime = LocalDateTime.now();

		_addAssetCategory("Time", _user);

		_setUpJournalArticles(
			new String[] {"", "", ""},
			new String[] {"Article", "Article With Category"});

		String[] timeOfDays = _getTimeOfDayAndNextTimeOfDay(
			localDateTime.toLocalTime());

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).put(
					"boost", 100
				).put(
					"time_of_day", timeOfDays[0]
				).build()
			},
			new String[] {"Boost Contents in a Category for the Time of Day"});

		_keywords = "Article";

		_assertSearch("[Article With Category, Article]");

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).put(
					"boost", 100
				).put(
					"time_of_day", timeOfDays[1]
				).build()
			},
			new String[] {"Boost Contents in a Category for the Time of Day"});

		_assertSearch("[Article, Article With Category]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Article, Article With Category]");
	}

	@Test
	public void testBoostContentsOnMySites() throws Exception {
		_addGroupAAndGroupB();

		_setUpJournalArticles(
			new String[] {"Site", ""},
			new String[] {"Site Default Group", "Site Group B"});

		User userSiteB = UserTestUtil.addUser(_groupB.getGroupId());

		_serviceContext.setUserId(userSiteB.getUserId());

		_keywords = "Site";

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 100
				).build()
			},
			new String[] {"Boost Contents on My Sites"});

		_assertSearch("[Site Group B, Site Default Group]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Site Default Group, Site Group B]");
	}

	@Test
	public void testBoostContentsWithMoreVersions() throws Exception {
		_setUpJournalArticles(
			new String[] {"Article", ""},
			new String[] {"Article 1.0", "Article 2.0"});

		_journalArticles.set(
			0,
			JournalTestUtil.updateArticle(
				_journalArticles.get(0), "Article 1.1"));

		_journalArticles.set(
			1,
			JournalTestUtil.updateArticle(
				_journalArticles.get(1), "Article 2.1"));

		_journalArticles.set(
			1,
			JournalTestUtil.updateArticle(
				_journalArticles.get(1), "Article 2.2"));

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 100
				).put(
					"factor", 10
				).put(
					"modifier", "sqrt"
				).build()
			},
			new String[] {"Boost Contents With More Versions"});

		_keywords = "Article";

		_assertSearch("[Article 2.2, Article 1.1]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Article 1.1, Article 2.2]");
	}

	@Test
	public void testBoostFreshness() throws Exception {
		_addJournalArticleSleep = 3;

		_setUpJournalArticles(
			new String[] {"Created", ""},
			new String[] {"First Created", "Second Created"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 100
				).put(
					"decay", 0.5
				).put(
					"offset", "0s"
				).put(
					"scale", "2s"
				).build()
			},
			new String[] {"Boost Freshness"});

		_keywords = "Created";

		_assertSearch("[Second Created, First Created]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[First Created, Second Created]");
	}

	@Test
	public void testBoostLongerContents() throws Exception {
		_setUpJournalArticles(
			new String[] {"Article", "Content Content"},
			new String[] {"Article 1", "Article 2"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 1000
				).put(
					"factor", 1.5
				).put(
					"modifier", "ln"
				).build()
			},
			new String[] {"Boost Longer Contents"});

		_keywords = "Article";

		_assertSearch("[Article 2, Article 1]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Article 1, Article 2]");
	}

	@Test
	public void testBoostProximity() throws Exception {
		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.fetchTable(
			_group.getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
			"CUSTOM_FIELDS");

		if (expandoTable == null) {
			expandoTable = ExpandoTableLocalServiceUtil.addTable(
				_group.getCompanyId(),
				ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
				"CUSTOM_FIELDS");

			_expandoTables.add(expandoTable);
		}

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			expandoTable, "location", ExpandoColumnConstants.GEOLOCATION);

		_expandoColumns.add(expandoColumn);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE,
			String.valueOf(ExpandoColumnConstants.GEOLOCATION));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		ExpandoColumnLocalServiceUtil.updateExpandoColumn(expandoColumn);

		_setUpJournalArticles(
			new String[] {"location", "location"},
			new String[] {"Branch SF", "Branch LA"},
			new double[] {64.01, 24.03}, new double[] {-117.42, -107.44});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 100
				).put(
					"decay", 0.3
				).put(
					"field", "expando__custom_fields__location_geolocation"
				).put(
					"ipstack.latitude", "24.03"
				).put(
					"ipstack.longitude", "-107.44"
				).put(
					"offset", "0"
				).put(
					"scale", "100km"
				).build()
			},
			new String[] {"Boost Proximity"});

		_keywords = "branch";

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				_getConfigurationTemporarySwapper(
					"2345", "34.94.32.240", "true")) {

			_assertSearch("[Branch LA, Branch SF]");
		}

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 100
				).put(
					"decay", 0.3
				).put(
					"field", "expando__custom_fields__location_geolocation"
				).put(
					"ipstack.latitude", "64.01"
				).put(
					"ipstack.longitude", "-117.42"
				).put(
					"offset", "0"
				).put(
					"scale", "100km"
				).build()
			},
			new String[] {"Boost Proximity"});

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				_getConfigurationTemporarySwapper(
					"2345", "64.225.32.7", "true")) {

			_assertSearch("[Branch SF, Branch LA]");
		}

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[Branch LA, Branch SF]");
	}

	@Test
	public void testBoostTaggedContents() throws Exception {
		_assetTag = AssetTagLocalServiceUtil.addTag(
			_user.getUserId(), _group.getGroupId(), "Boost", _serviceContext);

		_setUpJournalArticles(
			new String[] {"", ""}, new String[] {"Article", "Tagged Article"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_tags", new String[] {_assetTag.getName()}
				).put(
					"boost", 1000
				).build()
			},
			new String[] {"Boost Tagged Contents"});

		_keywords = "Article";

		_assertSearch("[Tagged Article, Article]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[Article, Tagged Article]");
	}

	@Test
	public void testBoostTagsMatch() throws Exception {
		_assetTag = AssetTagLocalServiceUtil.addTag(
			_user.getUserId(), _group.getGroupId(), "cola", _serviceContext);

		_setUpJournalArticles(
			new String[] {"", ""}, new String[] {"coca cola", "pepsi"});

		_keywords = "cola";

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 100
				).build()
			},
			new String[] {"Boost Tags Match"});

		_assertSearch("[pepsi, coca cola]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[coca cola, pepsi]");
	}

	@Test
	public void testBoostWebContentsByKeywordsMatch() throws Exception {
		_setUpJournalArticles(
			new String[] {"alpha alpha", ""},
			new String[] {"beta alpha", "charlie alpha"});

		_keywords = "alpha";

		JournalArticle journalArticle = _journalArticles.get(1);

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"article_ids", new String[] {journalArticle.getArticleId()}
				).put(
					"boost", 100
				).put(
					"values", "alpha"
				).build()
			},
			new String[] {"Boost Web Contents by Keyword Match"});

		_assertSearch("[charlie alpha, beta alpha]");

		_updateElementInstancesJSON(null, null);

		_assertSearch("[beta alpha, charlie alpha]");
	}

	@Test
	public void testCustomParameterWithinPasteAnyElasticSearchQueryElement()
		throws Exception {

		_updateConfigurationJSON(
			"parameterConfiguration",
			JSONUtil.put(
				"parameters",
				JSONUtil.put("myparam", JSONUtil.put("type", "String"))));

		_setUpJournalArticles(
			new String[] {"cola cola", ""},
			new String[] {"Coca Cola", "liferay"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must"
				).put(
					"query",
					JSONUtil.put(
						"match",
						JSONUtil.put(
							"title_en_US", JSONUtil.put("query", "${myparam}")))
				).build()
			},
			new String[] {"Paste Any Elasticsearch Query"});

		try {
			_assertSearchIgnoreRelevance("[Coca Cola, liferay]");
		}
		catch (RuntimeException runtimeException) {
			Throwable[] suppressed = runtimeException.getSuppressed();

			for (int i = 0; i < 3; i++) {
				suppressed = suppressed[0].getSuppressed();
			}

			Assert.assertEquals(
				"[com.liferay.search.experiences.blueprint.exception." +
					"UnresolvedTemplateVariableException: Unresolved " +
						"template variables: [myparam]]",
				Arrays.toString(suppressed));
		}

		_assertSearchIgnoreRelevance(
			"[liferay]",
			searchRequestBuilder -> searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"myparam", "liferay")));
	}

	@Test
	public void testFilterByExactTermsMatch() throws Exception {
		_setUpJournalArticles(
			new String[] {"", "", ""},
			new String[] {
				"coca cola filter", "pepsi cola filter", "sprite cola"
			});

		_keywords = "cola";

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"field", "title_en_US"
				).put(
					"values", new String[] {"filter"}
				).build()
			},
			new String[] {"Filter by Exact Terms Match"});

		_assertSearchIgnoreRelevance("[coca cola filter, pepsi cola filter]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance(
			"[coca cola filter, pepsi cola filter, sprite cola]");
	}

	@Test
	public void testHideByExactTermMatch() throws Exception {
		_journalFolder = JournalFolderServiceUtil.addFolder(
			null, _group.getGroupId(), 0, RandomTestUtil.randomString(),
			StringPool.BLANK, _serviceContext);

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Out of the folder", "In-Folder"});

		_keywords = "folder";

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"field", "folderId"
				).put(
					"value", String.valueOf(_journalFolder.getFolderId())
				).build()
			},
			new String[] {"Hide by Exact Term Match"});

		_assertSearchIgnoreRelevance("[Out of the folder]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[In-Folder, Out of the folder]");
	}

	@Test
	public void testHideComments() throws Exception {
		JournalArticle journalArticle = _addJournalArticle(
			_group.getGroupId(), 0, "Article", StringPool.BLANK, false, true);

		_journalArticles.add(journalArticle);

		CommentManagerUtil.addComment(
			_user.getUserId(), _serviceContext.getScopeGroupId(),
			JournalArticle.class.getName(), journalArticle.getResourcePrimKey(),
			"Article Comment",
			new IdentityServiceContextFunction(_serviceContext));

		_updateConfigurationJSON(
			"generalConfiguration",
			JSONUtil.put(
				"searchableAssetTypes",
				JSONUtil.putAll(
					"com.liferay.journal.model.JournalArticle",
					"com.liferay.message.boards.model.MBMessage")));

		_updateConfigurationJSON(
			"queryConfiguration", JSONUtil.put("applyIndexerClauses", false));

		_updateSXPBlueprint();

		_updateElementInstancesJSON(
			new Object[] {_getTextMatchOverMultipleFields()},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "Article";

		_assertSearchIgnoreRelevance("[Article, Article Comment]");

		_updateElementInstancesJSON(
			new Object[] {_getTextMatchOverMultipleFields(), null},
			new String[] {"Text Match Over Multiple Fields", "Hide Comments"});

		_assertSearchIgnoreRelevance("[Article]");
	}

	@Test
	public void testHideContentsInACategory() throws Exception {
		_addAssetCategory("Hidden", _addGroupUser(_group, "Employee"));

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Without Category", "Hidden Category"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).build()
			},
			new String[] {"Hide Contents in a Category"});

		_keywords = "Category";

		_assertSearchIgnoreRelevance("[Without Category]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[Hidden Category, Without Category]");
	}

	@Test
	public void testHideContentsInACategoryForGuestUsers() throws Exception {
		_user = _userLocalService.getDefaultUser(_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, _user.getUserId());

		_addAssetCategory("Hide from Guest Users", _user);

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Guest Users", "Non-Guest Users"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_category_id",
					String.valueOf(_assetCategory.getCategoryId())
				).build()
			},
			new String[] {"Hide Contents in a Category for Guest Users"});

		_keywords = "Guest";

		_assertSearchIgnoreRelevance("[Guest Users]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[Guest Users, Non-Guest Users]");
	}

	@Test
	public void testHideHiddenContents() throws Exception {
		_setUpJournalArticles(
			new String[] {
				"Los Angeles", "Orange County", "Los Angeles", "Los Angeles"
			},
			new String[] {
				"Cafe Rio", "Cloud Cafe", "Denny's", "Starbucks Cafe"
			});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must_not"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.getMatchQueryJSONObject(
						200, "los angeles")
				).build(),
				_getTextMatchOverMultipleFields(), null
			},
			new String[] {
				"Paste Any Elasticsearch Query",
				"Text Match Over Multiple Fields", "Hide Hidden Contents"
			});

		_keywords = "cafe";

		_assertSearchIgnoreRelevance("[Cloud Cafe]");

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must_not"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.getMatchQueryJSONObject(
						200, "orange county")
				).build(),
				_getTextMatchOverMultipleFields(), null
			},
			new String[] {
				"Paste Any Elasticsearch Query",
				"Text Match Over Multiple Fields", "Hide Hidden Contents"
			});

		_assertSearchIgnoreRelevance("[Cafe Rio, Starbucks Cafe]");
	}

	@Test
	public void testHideTaggedContents() throws Exception {
		_assetTag = AssetTestUtil.addTag(_group.getGroupId(), "hide");

		_journalFolder = JournalFolderServiceUtil.addFolder(
			null, _group.getGroupId(), 0, RandomTestUtil.randomString(),
			StringPool.BLANK, _serviceContext);

		_setUpJournalArticles(
			new String[] {"", ""}, new String[] {"do not hide me", "hide me"});

		_keywords = "hide me";

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"asset_tag", "hide"
				).build()
			},
			new String[] {"Hide Tagged Contents"});

		_assertSearchIgnoreRelevance("[do not hide me]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[do not hide me, hide me]");
	}

	@Test
	public void testLimitSearchToContentsCreatedWithinAPeriodOfTime()
		throws Exception {

		_addJournalArticleSleep = 2;

		_setUpJournalArticles(
			new String[] {"cola cola", "", ""},
			new String[] {"Coca Cola", "Pepsi Cola", "Sprite"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"end_date",
					DateUtil.getDate(
						new Date(System.currentTimeMillis()), "yyyyMMddHHmmss",
						LocaleUtil.US)
				).put(
					"start_date",
					DateUtil.getDate(
						new Date(System.currentTimeMillis() - 1000),
						"yyyyMMddHHmmss", LocaleUtil.US)
				).build()
			},
			new String[] {
				"Limit Search to Contents Created Within a Period of Time"
			});

		_assertSearchIgnoreRelevance("[Pepsi Cola, Sprite]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[Coca Cola, Pepsi Cola, Sprite]");
	}

	@Test
	public void testLimitSearchToHeadVersion() throws Exception {
		_updateConfigurationJSON(
			"queryConfiguration", JSONUtil.put("applyIndexerClauses", false));

		_journalArticles.add(
			_addJournalArticle(
				_group.getGroupId(), 0, "Article 1.0", StringPool.BLANK, false,
				true));

		_journalArticles.set(
			0,
			JournalTestUtil.updateArticle(
				_journalArticles.get(0), "Article 1.1"));

		_journalArticles.set(
			0,
			JournalTestUtil.updateArticle(
				_journalArticles.get(0), "Article 1.2"));

		_updateElementInstancesJSON(
			new Object[] {_getTextMatchOverMultipleFields()},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "Article";

		_assertSearchIgnoreRelevance("[Article 1.0, Article 1.1, Article 1.2]");

		_updateElementInstancesJSON(
			new Object[] {_getTextMatchOverMultipleFields(), null},
			new String[] {
				"Text Match Over Multiple Fields",
				"Limit Search to Head Version"
			});

		_assertSearch("[Article 1.2]");
	}

	@Test
	public void testLimitSearchToMyContents() throws Exception {
		User newUser = UserTestUtil.addUser(_group.getGroupId());

		_serviceContext.setUserId(newUser.getUserId());

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Article 1 New User", "Article 2 New User"});

		_serviceContext.setUserId(_user.getUserId());

		_setUpJournalArticles(
			new String[] {"", ""},
			new String[] {"Article 1 Default User", "Article 2 Default User"});

		_keywords = "Article";

		_updateElementInstancesJSON(
			null, new String[] {"Limit Search to My Contents"});

		_assertSearchIgnoreRelevance(
			"[Article 1 Default User, Article 2 Default User]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance(
			"[Article 1 Default User, Article 1 New User, Article 2 Default " +
				"User, Article 2 New User]");
	}

	@Test
	public void testLimitSearchToMySites() throws Exception {
		_addGroupAAndGroupB();

		_setUpJournalArticles(
			new String[] {"", "", ""},
			new String[] {"Site A", "Site B", "Current Site"});

		User user = UserTestUtil.addUser(_groupA.getGroupId());

		_serviceContext.setUserId(user.getUserId());

		_keywords = "Site";

		_updateElementInstancesJSON(
			null, new String[] {"Limit Search to My Sites"});

		_assertSearchIgnoreRelevance("[Site A]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[Current Site, Site A, Site B]");
	}

	@Test
	public void testLimitSearchToPDFFiles() throws Exception {
		_updateConfigurationJSON(
			"generalConfiguration",
			JSONUtil.put(
				"searchableAssetTypes",
				JSONUtil.putAll(
					"com.liferay.document.library.kernel.model.DLFileEntry",
					"com.liferay.journal.model.JournalArticle")));

		_addFileEntry("PDF file", ".pdf");

		_setUpJournalArticles(
			new String[] {"", "", ""},
			new String[] {"Article file 1", "Article file 2"});

		_updateElementInstancesJSON(
			null, new String[] {"Limit Search to PDF Files"});

		_keywords = "file";

		_assertSearch("[PDF file]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance(
			"[Article file 1, Article file 2, PDF file]");
	}

	@Test
	public void testLimitSearchToPublishedContents() throws Exception {
		_updateConfigurationJSON(
			"queryConfiguration", JSONUtil.put("applyIndexerClauses", false));

		_setUpJournalArticles(
			new String[] {"", "", ""}, new String[] {"Article 1", "Article 2"});

		_journalArticles.add(
			_addJournalArticle(
				_group.getGroupId(), 0, "Draft Article", StringPool.BLANK, true,
				false));

		_updateElementInstancesJSON(
			new Object[] {_getTextMatchOverMultipleFields()},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "Article";

		_assertSearchIgnoreRelevance("[Article 1, Article 2, Draft Article]");

		_updateElementInstancesJSON(
			new Object[] {_getTextMatchOverMultipleFields(), null},
			new String[] {
				"Text Match Over Multiple Fields",
				"Limit Search to Published Contents"
			});

		_assertSearchIgnoreRelevance("[Article 1, Article 2]");
	}

	@Test
	public void testLimitSearchToTheCurrentSite() throws Exception {
		_addGroupAAndGroupB();

		_setUpJournalArticles(
			new String[] {"", "", ""},
			new String[] {"Site A", "Site B", "Current Site"});

		_keywords = "Site";

		User user = UserTestUtil.addUser(_groupA.getGroupId());

		_serviceContext.setUserId(user.getUserId());

		_updateElementInstancesJSON(
			null, new String[] {"Limit Search to the Current Site"});

		_assertSearchIgnoreRelevance("[Current Site]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[Current Site, Site A, Site B]");
	}

	@Test
	public void testLimitSearchToTheseSites() throws Exception {
		_addGroupAAndGroupB();

		_setUpJournalArticles(
			new String[] {"", "", ""},
			new String[] {"Site A", "Site B", "Current Site"});

		_keywords = "Site";

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"scope_group_ids",
					new Long[] {_groupA.getGroupId(), _groupB.getGroupId()}
				).build()
			},
			new String[] {"Limit Search to These Sites"});

		_assertSearchIgnoreRelevance("[Site A, Site B]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[Current Site, Site A, Site B]");
	}

	@Test
	public void testMatch() throws Exception {
		_setUpJournalArticles(
			new String[] {
				"Los Angeles", "Orange County", "Los Angeles", "Los Angeles"
			},
			new String[] {
				"Cafe Rio", "Cloud Cafe", "Denny's", "Starbucks Cafe"
			});

		_updateElementInstancesJSON(null, null);

		_keywords = "cafe";

		_assertSearchIgnoreRelevance("[Cafe Rio, Cloud Cafe, Starbucks Cafe]");

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.getMatchQueryJSONObject(
						200, "los angeles")
				).build()
			},
			new String[] {"Paste Any Elasticsearch Query"});

		_assertSearchIgnoreRelevance("[Cafe Rio, Starbucks Cafe]");

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.getMatchQueryJSONObject(
						200, "orange county")
				).build()
			},
			new String[] {"Paste Any Elasticsearch Query"});

		_assertSearchIgnoreRelevance("[Cloud Cafe]");
	}

	@Test
	public void testPhraseMatch() throws Exception {
		_setUpJournalArticles(
			new String[] {"coca coca", ""},
			new String[] {
				"this coca looks like a kind of drink",
				"this looks like a kind of coca drink"
			});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.
						getMultiMatchQueryJSONObject(
							1, SXPBlueprintSearchResultTestUtil.FIELDS, null,
							"or", "${keywords}", "most_fields")
				).build(),
				HashMapBuilder.<String, Object>put(
					"boost", 100
				).put(
					"fields", SXPBlueprintSearchResultTestUtil.FIELDS
				).put(
					"keywords", "${keywords}"
				).put(
					"type", "phrase"
				).build()
			},
			new String[] {
				"Paste Any Elasticsearch Query", "Boost All Keywords Match"
			});

		_keywords = "coca drink";

		_assertSearch(
			"[this looks like a kind of coca drink, this coca looks like a " +
				"kind of drink]");

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.
						getMultiMatchQueryJSONObject(
							10, SXPBlueprintSearchResultTestUtil.FIELDS, null,
							"or", "${keywords}", "most_fields")
				).build()
			},
			new String[] {"Paste Any Elasticsearch Query"});

		_assertSearch(
			"[this coca looks like a kind of drink, this looks like a kind " +
				"of coca drink]");
	}

	@Test
	public void testSchedulingAware() throws Exception {
		_setUpJournalArticles(
			new String[] {"", ""}, new String[] {"Article 1", "Article 2"});

		_updateConfigurationJSON(
			"queryConfiguration", JSONUtil.put("applyIndexerClauses", false));

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.DAY_OF_MONTH, +1);

		Date displayDate = calendar.getTime();

		_journalArticles.add(
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				PortalUtil.getClassNameId(JournalArticle.class),
				StringPool.BLANK, true,
				HashMapBuilder.put(
					LocaleUtil.US, "Article Scheduled"
				).build(),
				null,
				HashMapBuilder.put(
					LocaleUtil.US, StringPool.BLANK
				).build(),
				null, LocaleUtil.getSiteDefault(), displayDate, null, false,
				true, _serviceContext));

		Map<String, Object> textMatchOverMultipleFields =
			_getTextMatchOverMultipleFields();

		textMatchOverMultipleFields.replace(
			"fields", new String[] {"title_${context.language_id}^2"});

		_updateElementInstancesJSON(
			new Object[] {textMatchOverMultipleFields},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "Article";

		_assertSearchIgnoreRelevance(
			"[Article 1, Article 2, Article Scheduled]");

		_updateElementInstancesJSON(
			new Object[] {textMatchOverMultipleFields, null},
			new String[] {
				"Text Match Over Multiple Fields", "Scheduling Aware"
			});

		_assertSearchIgnoreRelevance("[Article 1, Article 2]");
	}

	@Test
	public void testSearch() throws Exception {
		_setUpJournalArticles(
			new String[] {"Los Angeles", "Orange County"},
			new String[] {"Cafe Rio", "Cloud Cafe"});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "should"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.getMatchQueryJSONObject(
						200, "los angeles")
				).build(),
				_getTextMatchOverMultipleFields()
			},
			new String[] {
				"Paste Any Elasticsearch Query",
				"Text Match Over Multiple Fields"
			});

		_keywords = "cafe";

		_assertSearchIgnoreRelevance("[Cafe Rio, Cloud Cafe]");

		_updateElementInstancesJSON(null, null);

		_assertSearchIgnoreRelevance("[Cafe Rio, Cloud Cafe]");

		_setUpJournalArticles(
			new String[] {"", ""}, new String[] {"Coca Cola", "Pepsi Cola"});

		_keywords = "cola +coca";

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 1
				).put(
					"fields", SXPBlueprintSearchResultTestUtil.FIELDS
				).put(
					"operator", "and"
				).build()
			},
			new String[] {"Search with Query String Syntax"});

		_assertSearch("[Coca Cola]");

		_keywords = "cola -coca";

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"boost", 1
				).put(
					"fields", SXPBlueprintSearchResultTestUtil.FIELDS
				).put(
					"operator", "and"
				).build()
			},
			new String[] {"Search with Query String Syntax"});

		_assertSearch("[Pepsi Cola]");
	}

	@Test
	public void testStagingAware() throws Exception {
		_updateConfigurationJSON(
			"queryConfiguration", JSONUtil.put("applyIndexerClauses", false));

		_enableLocalStaging();

		Group stagingGroup = _group.getStagingGroup();

		_setUpJournalArticles(
			new String[] {"", "", ""}, new String[] {"Article 1", "Article 2"});

		_journalArticles.add(
			_addJournalArticle(
				stagingGroup.getGroupId(), 0, "Staged Article",
				StringPool.BLANK, false, true));

		Map<String, Object> textMatchOverMultipleFields =
			_getTextMatchOverMultipleFields();

		textMatchOverMultipleFields.replace(
			"fields", new String[] {"title_${context.language_id}^2"});

		_updateElementInstancesJSON(
			new Object[] {textMatchOverMultipleFields},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "Article";

		_assertSearchIgnoreRelevance("[Article 1, Article 2, Staged Article]");

		_updateElementInstancesJSON(
			new Object[] {textMatchOverMultipleFields, null},
			new String[] {"Text Match Over Multiple Fields", "Staging Aware"});

		_assertSearchIgnoreRelevance("[Article 1, Article 2]");
	}

	@Test
	public void testTextMatchOverMultipleFields_bestFields() throws Exception {
		_setUpJournalArticles(
			new String[] {
				"carbonated cola", "carbonated cola cola",
				"non-carbonated cola", "carbonated cola cola"
			},
			new String[] {
				"drink carbonated coca", "drink carbonated pepsi cola",
				"fruit punch", "sprite"
			});

		_getTextMatchOverMultipleFields();

		_updateElementInstancesJSON(
			new Object[] {_getTextMatchOverMultipleFields()},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "coca cola";

		_assertSearch(
			"[drink carbonated coca, drink carbonated pepsi cola, sprite, " +
				"fruit punch]");

		_setUpJournalArticles(
			new String[] {"ipsum sit", "ipsum sit sit", "non-lorem ipsum sit"},
			new String[] {"lorem ipsum dolor", "lorem ipsum sit", "nunquis"});

		_updateElementInstancesJSON(
			new Object[] {_getTextMatchOverMultipleFields()},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "ipsum sit sit";

		_assertSearch("[lorem ipsum sit, lorem ipsum dolor, nunquis]");
	}

	@Test
	public void testTextMatchOverMultipleFields_boolPrefix() throws Exception {
		_setUpJournalArticles(
			new String[] {
				"ipsum sit sit", "ipsum sit", "ipsum sit sit",
				"non-lorem ipsum sit"
			},
			new String[] {
				"lorem ipsum sit", "lorem ipsum dolor", "amet", "nunquis"
			});

		Map<String, Object> textMatchOverMultipleFields =
			_getTextMatchOverMultipleFields();

		textMatchOverMultipleFields.replace("fuzziness", "0");
		textMatchOverMultipleFields.replace("operator", "and");
		textMatchOverMultipleFields.replace("type", "bool_prefix");

		_updateElementInstancesJSON(
			new Object[] {textMatchOverMultipleFields},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "lorem dol";

		_assertSearchIgnoreRelevance("[lorem ipsum dolor]");

		textMatchOverMultipleFields.replace("operator", "or");

		_updateElementInstancesJSON(
			new Object[] {textMatchOverMultipleFields},
			new String[] {"Text Match Over Multiple Fields"});

		_assertSearchIgnoreRelevance(
			"[lorem ipsum dolor, lorem ipsum sit, nunquis]");
	}

	@Test
	public void testTextMatchOverMultipleFields_crossFields() throws Exception {
		_setUpJournalArticles(
			new String[] {"foxtrot, golf", "hotel golf", "alpha", "beta"},
			new String[] {
				"alpha beta", "alpha edison", "beta charlie", "edison india"
			});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.
						getMultiMatchQueryJSONObject(
							1, SXPBlueprintSearchResultTestUtil.FIELDS, null,
							"and", "${keywords}", "cross_fields")
				).build()
			},
			new String[] {"Paste Any Elasticsearch Query"});

		_keywords = "alpha golf";

		_assertSearchIgnoreRelevance("[alpha beta, alpha edison]");

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.
						getMultiMatchQueryJSONObject(
							1, SXPBlueprintSearchResultTestUtil.FIELDS, null,
							"or", "${keywords}", "cross_fields")
				).build()
			},
			new String[] {"Paste Any Elasticsearch Query"});

		_assertSearchIgnoreRelevance(
			"[alpha beta, alpha edison, beta charlie]");
	}

	@Test
	public void testTextMatchOverMultipleFields_mostFields() throws Exception {
		_setUpJournalArticles(
			new String[] {
				"ipsum sit sit", "ipsum sit", "ipsum sit sit",
				"non-lorem ipsum sit"
			},
			new String[] {
				"amet", "lorem ipsum dolor", "lorem ipsum sit", "nunquis"
			});

		Map<String, Object> textMatchOverMultipleFields =
			_getTextMatchOverMultipleFields();

		textMatchOverMultipleFields.replace("fuzziness", "0");
		textMatchOverMultipleFields.replace("operator", "and");
		textMatchOverMultipleFields.replace("type", "most_fields");

		_updateElementInstancesJSON(
			new Object[] {textMatchOverMultipleFields},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "sit lorem";

		_assertSearch("[lorem ipsum sit, nunquis]");

		textMatchOverMultipleFields.replace("operator", "or");

		_updateElementInstancesJSON(
			new Object[] {textMatchOverMultipleFields},
			new String[] {"Text Match Over Multiple Fields"});

		_keywords = "ipsum sit sit";

		_assertSearchIgnoreRelevance(
			"[amet, lorem ipsum dolor, lorem ipsum sit, nunquis]");
	}

	@Test
	public void testTextMatchOverMultipleFields_phrase() throws Exception {
		_setUpJournalArticles(
			new String[] {
				"do not listen to birds", "listen listen to birds",
				"listen to birds", "listen listen to birds"
			},
			new String[] {
				"listen something", "listen to birds", "listen to planes",
				"silence"
			});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.
						getMultiMatchQueryJSONObject(
							1, SXPBlueprintSearchResultTestUtil.FIELDS, null,
							null, "${keywords}", "phrase")
				).build()
			},
			new String[] {"Paste Any Elasticsearch Query"});

		_keywords = "listen listen";

		_assertSearch("[listen to birds, silence]");
	}

	@Test
	public void testTextMatchOverMultipleFields_phrasePrefix()
		throws Exception {

		_setUpJournalArticles(
			new String[] {
				"simple things are beautiful sometimes",
				"simple things are beautiful", "simple things are not good",
				"simple things are bad"
			},
			new String[] {
				"clouds", "watch birds on the sky", "watch planes on the sky",
				"watch trains"
			});

		_updateElementInstancesJSON(
			new Object[] {
				HashMapBuilder.<String, Object>put(
					"occur", "must"
				).put(
					"query",
					SXPBlueprintSearchResultTestUtil.
						getMultiMatchQueryJSONObject(
							1, SXPBlueprintSearchResultTestUtil.FIELDS, null,
							null, "${keywords}", "phrase_prefix")
				).build()
			},
			new String[] {"Paste Any Elasticsearch Query"});

		_keywords = "simple things are beau";

		_assertSearch("[watch birds on the sky, clouds]");
	}

	@Rule
	public TestName testName = new TestName();

	private void _addAssetCategory(String title, User user) throws Exception {
		if (_assetVocabulary == null) {
			_assetVocabulary =
				AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
					_group.getGroupId());
		}

		_assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			user.getUserId(), _group.getGroupId(), title,
			_assetVocabulary.getVocabularyId(), _serviceContext);
	}

	private void _addFileEntry(String sourceFileName, String extension)
		throws Exception {

		Class<?> clazz = getClass();

		String clazzName = clazz.getName();

		String fileName = StringBundler.concat(
			"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
			testName.getMethodName(), extension);

		DLAppLocalServiceUtil.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, sourceFileName,
			ContentTypes.APPLICATION_PDF,
			FileUtil.getBytes(
				SXPBlueprintSearchResultTest.class,
				StringUtils.replace(clazzName, ".", "/") + fileName),
			null, null, _serviceContext);
	}

	private void _addGroupAAndGroupB() throws Exception {
		_groupA = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(), _serviceContext);
		_groupB = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(), _serviceContext);
	}

	private User _addGroupUser(Group group, String roleName) throws Exception {
		Role role = RoleTestUtil.addRole(roleName, RoleConstants.TYPE_REGULAR);

		return UserTestUtil.addGroupUser(group, role.getName());
	}

	private JournalArticle _addJournalArticle(
			long groupId, long folderId, String name, String content,
			boolean workflowEnabled, boolean approved)
		throws Exception {

		return JournalTestUtil.addArticle(
			groupId, folderId, PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, name
			).build(),
			null,
			HashMapBuilder.put(
				LocaleUtil.US, content
			).build(),
			LocaleUtil.getSiteDefault(), workflowEnabled, approved,
			_serviceContext);
	}

	private SegmentsEntry _addSegmentsEntry(User user) throws Exception {
		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", user.getFirstName()),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());
	}

	private void _assertSearch(
			String expected,
			Consumer<SearchRequestBuilder>... searchRequestBuilderConsumer)
		throws Exception {

		SearchResponse searchResponse = _getSearchResponseSearchPage(
			searchRequestBuilderConsumer);

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), "title_en_US", expected);

		if (!Objects.equals("{}", _sxpBlueprint.getElementInstancesJSON())) {
			searchResponse = _getSearchResponsePreview(
				searchRequestBuilderConsumer);

			DocumentsAssert.assertValues(
				searchResponse.getRequestString(),
				searchResponse.getDocumentsStream(), "title_en_US", expected);
		}
	}

	private void _assertSearchIgnoreRelevance(
			String expected,
			Consumer<SearchRequestBuilder>... searchRequestBuilderConsumer)
		throws Exception {

		SearchResponse searchResponse = _getSearchResponseSearchPage(
			searchRequestBuilderConsumer);

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), "title_en_US", expected);

		if (!Objects.equals("{}", _sxpBlueprint.getElementInstancesJSON())) {
			searchResponse = _getSearchResponsePreview(
				searchRequestBuilderConsumer);

			DocumentsAssert.assertValuesIgnoreRelevance(
				searchResponse.getRequestString(),
				searchResponse.getDocumentsStream(), "title_en_US", expected);
		}
	}

	private void _enableLocalStaging() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Map<String, Serializable> attributes = serviceContext.getAttributes();

		attributes.putAll(
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), _group, false, false, serviceContext);
	}

	private ConfigurationTemporarySwapper _getConfigurationTemporarySwapper(
			String apiKey, String apiURL, String enabled)
		throws Exception {

		return new ConfigurationTemporarySwapper(
			"com.liferay.search.experiences.internal.configuration." +
				"IpstackConfiguration",
			HashMapDictionaryBuilder.put(
				"apiKey", (Object)apiKey
			).put(
				"apiURL", apiURL
			).put(
				"enabled", enabled
			).build());
	}

	private SearchResponse _getSearchResponsePreview(
			Consumer<SearchRequestBuilder>... searchRequestBuilderConsumer)
		throws Exception {

		com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint sxpBlueprint =
			new com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint() {
				{
					configuration = ConfigurationUtil.toConfiguration(
						_sxpBlueprint.getConfigurationJSON());
					elementInstances = ElementInstanceUtil.toElementInstances(
						_sxpBlueprint.getElementInstancesJSON());
				}
			};

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				TestPropsValues.getCompanyId()
			).queryString(
				_keywords
			).withSearchContext(
				_searchContext -> {
					_searchContext.setAttribute(
						"scope_group_id", _group.getGroupId());
					_searchContext.setAttribute(
						"search.experiences.scope.group.id",
						_group.getGroupId());
					_searchContext.setTimeZone(_user.getTimeZone());
					_searchContext.setUserId(_serviceContext.getUserId());
				}
			).withSearchRequestBuilder(
				searchRequestBuilderConsumer
			);

		_sxpBlueprintSearchRequestEnhancer.enhance(
			searchRequestBuilder,
			String.valueOf(SXPBlueprintUtil.unpack(sxpBlueprint)));

		return _searcher.search(searchRequestBuilder.build());
	}

	private SearchResponse _getSearchResponseSearchPage(
			Consumer<SearchRequestBuilder>... searchRequestBuilderConsumer)
		throws Exception {

		return _searcher.search(
			_searchRequestBuilderFactory.builder(
			).companyId(
				TestPropsValues.getCompanyId()
			).queryString(
				_keywords
			).withSearchContext(
				_searchContext -> {
					_searchContext.setAttribute(
						"search.experiences.blueprint.id",
						String.valueOf(_sxpBlueprint.getSXPBlueprintId()));
					_searchContext.setAttribute(
						"search.experiences.scope.group.id",
						_group.getGroupId());
					_searchContext.setTimeZone(_user.getTimeZone());
					_searchContext.setUserId(_serviceContext.getUserId());
				}
			).withSearchRequestBuilder(
				searchRequestBuilderConsumer
			).build());
	}

	private Map<String, Object> _getTextMatchOverMultipleFields() {
		return HashMapBuilder.<String, Object>put(
			"boost", 1
		).put(
			"fields", SXPBlueprintSearchResultTestUtil.FIELDS
		).put(
			"fuzziness", "AUTO"
		).put(
			"keywords", "${keywords}"
		).put(
			"minimum_should_match", 0
		).put(
			"operator", "or"
		).put(
			"slop", 0
		).put(
			"type", "best_fields"
		).build();
	}

	private String[] _getTimeOfDayAndNextTimeOfDay(LocalTime localTime) {
		if (_isBetween(localTime, _LOCAL_TIME_04, _LOCAL_TIME_12)) {
			return new String[] {"morning", "afternoon"};
		}
		else if (_isBetween(localTime, _LOCAL_TIME_12, _LOCAL_TIME_17)) {
			return new String[] {"afternoon", "evening"};
		}
		else if (_isBetween(localTime, _LOCAL_TIME_17, _LOCAL_TIME_20)) {
			return new String[] {"evening", "night"};
		}

		return new String[] {"night", "morning"};
	}

	private boolean _isBetween(
		LocalTime localTime, LocalTime startLocalTime, LocalTime endLocalTime) {

		if (!localTime.isBefore(startLocalTime) &&
			localTime.isBefore(endLocalTime)) {

			return true;
		}

		return false;
	}

	private void _setUpJournalArticles(
			String[] journalArticleContents, String[] journalArticleTitles)
		throws Exception {

		Group group = _group;

		if (_groupA != null) {
			group = _groupA;
		}

		_journalArticles.add(
			_addJournalArticle(
				group.getGroupId(), 0, journalArticleTitles[0],
				journalArticleContents[0], false, true));

		if (journalArticleTitles.length < 2) {
			return;
		}

		if (_groupB != null) {
			group = _groupB;
		}

		if (_assetCategory != null) {
			_serviceContext.setAssetCategoryIds(
				new long[] {_assetCategory.getCategoryId()});
		}

		if (_assetTag != null) {
			_serviceContext.setAssetTagNames(
				new String[] {_assetTag.getName()});
		}

		TimeUnit.SECONDS.sleep(_addJournalArticleSleep);

		long journalFolderId = 0;

		if (_journalFolder != null) {
			journalFolderId = _journalFolder.getFolderId();
		}

		_journalArticles.add(
			_addJournalArticle(
				group.getGroupId(), journalFolderId, journalArticleTitles[1],
				journalArticleContents[1], false, true));

		for (int i = 2;
			 (journalArticleTitles.length > 2) &&
			 (i < journalArticleTitles.length); i++) {

			_journalArticles.add(
				_addJournalArticle(
					_group.getGroupId(), 0, journalArticleTitles[i],
					journalArticleContents[i], false, true));
		}
	}

	private void _setUpJournalArticles(
			String[] expandoBridgeAttributeNames, String[] journalArticleTitles,
			double[] latitudes, double[] longitudes)
		throws Exception {

		for (int i = 0; i < journalArticleTitles.length; i++) {
			_serviceContext.setExpandoBridgeAttributes(
				Collections.singletonMap(
					expandoBridgeAttributeNames[i],
					JSONUtil.put(
						"latitude", latitudes[i]
					).put(
						"longitude", longitudes[i]
					).toString()));

			_setUpJournalArticles(
				new String[] {""}, new String[] {journalArticleTitles[i]});
		}
	}

	private void _updateConfigurationJSON(
			String configurationName, JSONObject jsonObject)
		throws Exception {

		JSONObject configurationJSONObject = JSONFactoryUtil.createJSONObject(
			_sxpBlueprint.getConfigurationJSON());

		_sxpBlueprint.setConfigurationJSON(
			configurationJSONObject.put(
				configurationName, jsonObject
			).toString());

		_updateSXPBlueprint();
	}

	private void _updateElementInstancesJSON(
			Object[] configurationValuesArray, String[] sxpElementNames)
		throws Exception {

		String elementInstancesJSON = "{}";

		if (sxpElementNames != null) {
			elementInstancesJSON =
				SXPBlueprintSearchResultTestUtil.getElementInstancesJSON(
					configurationValuesArray, sxpElementNames, _sxpElements);
		}

		_sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);

		_updateSXPBlueprint();
	}

	private void _updateSXPBlueprint() throws Exception {
		_sxpBlueprintLocalService.updateSXPBlueprint(
			_sxpBlueprint.getUserId(), _sxpBlueprint.getSXPBlueprintId(),
			_sxpBlueprint.getConfigurationJSON(),
			_sxpBlueprint.getDescriptionMap(),
			_sxpBlueprint.getElementInstancesJSON(),
			_sxpBlueprint.getSchemaVersion(), _sxpBlueprint.getTitleMap(),
			_serviceContext);
	}

	private static final LocalTime _LOCAL_TIME_04 = LocalTime.of(4, 0, 0);

	private static final LocalTime _LOCAL_TIME_12 = LocalTime.of(12, 0, 0);

	private static final LocalTime _LOCAL_TIME_17 = LocalTime.of(17, 0, 0);

	private static final LocalTime _LOCAL_TIME_20 = LocalTime.of(20, 0, 0);

	private static List<SXPElement> _sxpElements;

	@Inject
	private static UserLocalService _userLocalService;

	private int _addJournalArticleSleep;
	private AssetCategory _assetCategory;
	private AssetTag _assetTag;
	private AssetVocabulary _assetVocabulary;
	private final JSONObject _configurationJSONObject = JSONUtil.put(
		"generalConfiguration",
		JSONUtil.put(
			"searchableAssetTypes",
			JSONUtil.put("com.liferay.journal.model.JournalArticle"))
	).put(
		"queryConfiguration", JSONUtil.put("applyIndexerClauses", true)
	);

	@DeleteAfterTestRun
	private final List<ExpandoColumn> _expandoColumns = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<ExpandoTable> _expandoTables = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Group _groupA;

	@DeleteAfterTestRun
	private Group _groupB;

	private final List<JournalArticle> _journalArticles = new ArrayList<>();
	private JournalFolder _journalFolder;
	private String _keywords;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Inject
	private SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer;

	private User _user;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}