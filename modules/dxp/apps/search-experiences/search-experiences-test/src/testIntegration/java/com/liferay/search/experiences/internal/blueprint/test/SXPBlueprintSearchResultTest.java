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
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
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
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		WorkflowThreadLocal.setEnabled(true);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());

		_user = TestPropsValues.getUser();

		_sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			_user.getUserId(), "{}",
			Collections.singletonMap(LocaleUtil.US, ""), null, "",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			_serviceContext);
	}

	@Test
	public void testBoostContents() throws Exception {
		_setUp(
			new String[] {"cola cola", ""},
			new String[] {"coca cola", "pepsi cola"},
			() -> {
				_addAssetCategory("Important", _user);
				_addGroupAAndGroupB();
			});

		_test(
			new String[] {"${configuration.asset_category_ids}"},
			new String[] {String.valueOf(_assetCategory.getCategoryId())},
			"inCategory",
			() -> _assertSearch("[pepsi cola, coca cola]", "cola"));
		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[coca cola, pepsi cola]", "cola"));

		User user = UserTestUtil.addUser(_groupB.getGroupId());

		_serviceContext.setUserId(user.getUserId());

		_test(
			null, null, "onMySites",
			() -> _assertSearch("[pepsi cola, coca cola]", "cola"));
	}

	@Test
	public void testBoostFreshness() throws Exception {
		_setUp(
			new String[] {"cola cola", ""},
			new String[] {"coca cola", "pepsi cola"},
			() -> _addJournalArticleSleep = 3);

		_test(
			new String[] {"${time.current_date}"},
			new String[] {
				DateUtil.getCurrentDate("yyyyMMddHHmmss", LocaleUtil.US)
			},
			"withFunctionScore",
			() -> _assertSearch("[pepsi cola, coca cola]", "cola"));
		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[coca cola, pepsi cola]", "cola"));
	}

	@Test
	public void testBoostProximity() throws Exception {
		_setUp(
			new String[] {"location", "location"},
			new String[] {"Branch SF", "Branch LA"},
			new double[] {64.01, 24.03}, new double[] {-117.42, -107.44},
			() -> {
				ExpandoTable expandoTable =
					ExpandoTableLocalServiceUtil.fetchTable(
						_group.getCompanyId(),
						ClassNameLocalServiceUtil.getClassNameId(
							JournalArticle.class),
						"CUSTOM_FIELDS");

				if (expandoTable == null) {
					expandoTable = ExpandoTableLocalServiceUtil.addTable(
						_group.getCompanyId(),
						ClassNameLocalServiceUtil.getClassNameId(
							JournalArticle.class),
						"CUSTOM_FIELDS");

					_expandoTables.add(expandoTable);
				}

				ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
					expandoTable, "location",
					ExpandoColumnConstants.GEOLOCATION);

				_expandoColumns.add(expandoColumn);

				UnicodeProperties unicodeProperties =
					expandoColumn.getTypeSettingsProperties();

				unicodeProperties.setProperty(
					ExpandoColumnConstants.INDEX_TYPE,
					String.valueOf(ExpandoColumnConstants.GEOLOCATION));

				expandoColumn.setTypeSettingsProperties(unicodeProperties);

				ExpandoColumnLocalServiceUtil.updateExpandoColumn(
					expandoColumn);
			});

		_test(
			new String[] {"${configuration.lat}", "${configuration.lon}"},
			new String[] {"24.03", "-107.44"}, "withFunctionScore",
			() -> {
				try (ConfigurationTemporarySwapper
						configurationTemporarySwapper =
							_getConfigurationTemporarySwapper(
								"2345", "34.94.32.240", "true")) {

					_assertSearch("[branch la, branch sf]", "branch");
				}
			});
		_test(
			new String[] {"${configuration.lat}", "${configuration.lon}"},
			new String[] {"64.01", "-117.42"}, "withFunctionScore",
			() -> {
				try (ConfigurationTemporarySwapper
						configurationTemporarySwapper =
							_getConfigurationTemporarySwapper(
								"2345", "64.225.32.7", "true")) {

					_assertSearch("[branch sf, branch la]", "branch");
				}
			});
		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[branch la, branch sf]", "branch"));
	}

	@Test
	public void testConditionContains() throws Exception {
		_setUp(
			new String[] {"alpha alpha", ""},
			new String[] {"beta alpha", "charlie alpha"},
			() -> _addAssetCategory(
				"Promoted", _addGroupUser(_group, "employee")));

		_test(
			new String[] {
				"${configuration.asset_category_id}",
				"${configuration.keywords}"
			},
			new String[] {
				String.valueOf(_assetCategory.getCategoryId()), "alpha"
			},
			"withAssetCategory",
			() -> _assertSearch("[charlie alpha, beta alpha]", "alpha"));
		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[beta alpha, charlie alpha]", "alpha"));

		JournalArticle journalArticle = _journalArticles.get(1);

		_test(
			new String[] {"${articleId}"},
			new String[] {journalArticle.getArticleId()}, "withContains",
			() -> _assertSearch("[charlie alpha, beta alpha]", "alpha"));
		_test(
			new String[] {"${articleId}"},
			new String[] {journalArticle.getArticleId()}, "withNotContains",
			() -> _assertSearch("[beta alpha, charlie alpha]", "alpha"));

		SegmentsEntry segmentsEntry = _addSegmentsEntry(_user);

		_test(
			new String[] {
				"${configuration.asset_category_id}",
				"${configuration.user_segment_ids}"
			},
			new String[] {
				String.valueOf(_assetCategory.getCategoryId()),
				String.valueOf(segmentsEntry.getSegmentsEntryId())
			},
			"withSegmentsEntry",
			() -> _assertSearch("[charlie alpha, beta alpha]", "alpha"));
	}

	@Test
	public void testConditionRange() throws Exception {
		_setUp(
			new String[] {"cola cola", ""},
			new String[] {"Coca Cola", "Pepsi Cola"},
			() -> _addAssetCategory(
				"Promoted", _addGroupUser(_group, "Custmers")));

		_test(
			new String[] {
				"${configuration.asset_category_id}",
				"${configuration.start_date}", "${configuration.end_date}"
			},
			new String[] {
				String.valueOf(_assetCategory.getCategoryId()),
				DateUtil.getDate(
					new Date(System.currentTimeMillis() - Time.DAY), "yyyyMMdd",
					LocaleUtil.US),
				DateUtil.getDate(
					new Date(System.currentTimeMillis() + Time.DAY), "yyyyMMdd",
					LocaleUtil.US)
			},
			"withPeriodOfTime",
			() -> _assertSearch("[pepsi cola, coca cola]", "cola"));
		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[coca cola, pepsi cola]", "cola"));

		_setUp(
			new String[] {"policies policies", ""},
			new String[] {
				"Company policies for All Employees Recruits",
				"Company Policies for New Recruits"
			},
			() -> _addAssetCategory(
				"For New Recruits", _addGroupUser(_group, "Employee")));

		_test(
			new String[] {"${configuration.asset_category_id}"},
			new String[] {String.valueOf(_assetCategory.getCategoryId())},
			"withNewUserAccount",
			() -> _assertSearch(
				"[company policies for new recruits, company policies for " +
					"all employees recruits]",
				"policies"));
	}

	@Test
	public void testHideSearch() throws Exception {
		_setUp(
			new String[] {"", ""}, new String[] {"do not hide me", "hide me"},
			() -> {
				_assetTag = AssetTestUtil.addTag(_group.getGroupId(), "hide");
				_journalFolder = JournalFolderServiceUtil.addFolder(
					_group.getGroupId(), 0, RandomTestUtil.randomString(),
					StringPool.BLANK, _serviceContext);
			});

		_test(
			null, null, "taggedContent",
			() -> _assertSearch("[do not hide me]", "hide me"));
		_test(
			new String[] {"${configuration.value}"},
			new String[] {String.valueOf(_journalFolder.getFolderId())},
			"byExactTermMatch",
			() -> _assertSearch("[do not hide me]", "hide me"));
		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[do not hide me, hide me]", "hide me"));

		_assetTag = null;
		_journalFolder = null;

		_setUp(
			new String[] {
				"Los Angeles", "Orange County", "Los Angeles", "Los Angeles"
			},
			new String[] {
				"Cafe Rio", "Cloud Cafe", "Denny's", "Starbucks Cafe"
			});

		_test(
			new String[] {"${configuration.query}", "${configuration.occur}"},
			new String[] {"los angeles", "must_not"}, "pasteESQueryMustNot",
			() -> _assertSearchIgnoreRelevance("[cloud cafe]", "cafe"));
		_test(
			new String[] {"${configuration.query}", "${configuration.occur}"},
			new String[] {"orange county", "must_not"}, "pasteESQueryMustNot",
			() -> _assertSearchIgnoreRelevance(
				"[cafe rio, starbucks cafe]", "cafe"));
	}

	@Test
	public void testKeywoardMatch() throws Exception {
		_setUp(
			new String[] {"", ""}, new String[] {"coca cola", "pepsi cola"},
			() ->
				_assetTag = AssetTagLocalServiceUtil.addTag(
					_user.getUserId(), _group.getGroupId(), "cola",
					_serviceContext));

		_test(
			null, null, "withAssetTagName",
			() -> _assertSearch("[pepsi cola, coca cola]", "cola"));
		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[coca cola, pepsi cola]", "cola"));
	}

	@Test
	public void testLimitSearch() throws Exception {
		_setUp(
			new String[] {"", "", ""},
			new String[] {"cola coca", "cola pepsi", "cola sprite"},
			() -> _addGroupAAndGroupB());

		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[cola coca, cola pepsi, cola sprite]", "cola"));
		_test(
			new String[] {"${configuration.value1}", "${configuration.value2}"},
			new String[] {
				String.valueOf(_groupA.getGroupId()),
				String.valueOf(_groupB.getGroupId())
			},
			"withFilterByExactTermMatch",
			() -> _assertSearchIgnoreRelevance(
				"[cola coca, cola pepsi]", "cola"));

		User user = UserTestUtil.addUser(_groupA.getGroupId());

		_serviceContext.setUserId(user.getUserId());

		_test(
			null, null, "withInAGroup",
			() -> _assertSearchIgnoreRelevance("[cola coca]", "cola"));
		_test(
			new String[] {"${configuration.value1}", "${configuration.value2}"},
			new String[] {
				String.valueOf(_groupA.getGroupId()),
				String.valueOf(_groupB.getGroupId())
			},
			"withTheseSites",
			() -> _assertSearchIgnoreRelevance(
				"[cola coca, cola pepsi]", "cola"));
	}

	@Test
	public void testMatch() throws Exception {
		_setUp(
			new String[] {
				"Los Angeles", "Orange County", "Los Angeles", "Los Angeles"
			},
			new String[] {
				"Cafe Rio", "Cloud Cafe", "Denny's", "Starbucks Cafe"
			});

		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[cafe rio, cloud cafe, starbucks cafe]", "cafe"));
		_test(
			new String[] {"${configuration.query}"},
			new String[] {"los angeles"}, "fromScratch",
			() -> _assertSearchIgnoreRelevance(
				"[cafe rio, starbucks cafe]", "cafe"));
		_test(
			new String[] {"${configuration.query}"},
			new String[] {"orange county"}, "fromScratch",
			() -> _assertSearchIgnoreRelevance("[cloud cafe]", "cafe"));
	}

	@Test
	public void testPhraseMatch() throws Exception {
		_setUp(
			new String[] {"coca coca", ""},
			new String[] {
				"This coca looks like a kind of drink",
				"This looks like a kind of coca drink"
			});

		_test(
			null, null, "withMultiAllKeywordsMatch",
			() -> _assertSearch(
				"[this looks like a kind of coca drink, this coca looks like " +
					"a kind of drink]",
				"coca drink"));
		_test(
			null, null, "withMultiMatch",
			() -> _assertSearch(
				"[this coca looks like a kind of drink, this looks like a " +
					"kind of coca drink]",
				"coca drink"));
	}

	@Test
	public void testSearch() throws Exception {
		_setUp(
			new String[] {"Los Angeles", "Orange County"},
			new String[] {"Cafe Rio", "Cloud Cafe"});

		_test(
			null, null, "withDefaultBehavior",
			() -> _assertSearch("[cafe rio, cloud cafe]", "cafe"));
		_test(
			null, null, null,
			() -> _assertSearchIgnoreRelevance(
				"[cafe rio, cloud cafe]", "cafe"));

		_setUp(new String[] {"", ""}, new String[] {"Coca Cola", "Pepsi Cola"});

		_test(
			null, null, "withLuceneSyntax",
			() -> _assertSearch("[coca cola]", "cola +coca"));
		_test(
			null, null, "withLuceneSyntax",
			() -> _assertSearch("[pepsi cola]", "cola -coca"));
	}

	@Test
	public void testTextMatchOverMultipleFields_bestFields() throws Exception {
		_setUp(
			new String[] {
				"carbonated cola", "carbonated cola cola",
				"non-carbonated cola", "carbonated cola cola"
			},
			new String[] {
				"drink carbonated coca", "drink carbonated pepsi cola",
				"fruit punch", "sprite"
			});

		_test(
			new String[] {
				"${configuration.fuzziness}", "${configuration.operator}"
			},
			new String[] {"AUTO", "and"}, "withOperator",
			() -> _assertSearch(
				"[drink carbonated coca, drink carbonated pepsi cola, " +
					"sprite, fruit punch]",
				"coca cola"));

		_setUp(
			new String[] {"ipsum sit", "ipsum sit sit", "non-lorem ipsum sit"},
			new String[] {"lorem ipsum dolor", "lorem ipsum sit", "nunquis"});

		_test(
			new String[] {
				"${configuration.fuzziness}", "${configuration.operator}"
			},
			new String[] {"0", "or"}, "withOperator",
			() -> _assertSearch(
				"[lorem ipsum sit, lorem ipsum dolor, nunquis]",
				"ipsum sit sit"));
	}

	@Test
	public void testTextMatchOverMultipleFields_boolPrefix() throws Exception {
		_setUp(
			new String[] {
				"ipsum sit sit", "ipsum sit", "ipsum sit sit",
				"non-lorem ipsum sit"
			},
			new String[] {
				"lorem ipsum sit", "lorem ipsum dolor", "amet", "nunquis"
			});

		_test(
			new String[] {"${configuration.operator}"}, new String[] {"and"},
			"withOperator",
			() -> _assertSearchIgnoreRelevance(
				"[lorem ipsum dolor]", "lorem dol"));
		_test(
			new String[] {"${configuration.operator}"}, new String[] {"or"},
			"withOperator",
			() -> _assertSearchIgnoreRelevance(
				"[lorem ipsum dolor, lorem ipsum sit, nunquis]", "lorem dol"));
	}

	@Test
	public void testTextMatchOverMultipleFields_crossFields() throws Exception {
		_setUp(
			new String[] {"foxtrot, golf", "hotel golf", "alpha", "beta"},
			new String[] {
				"alpha beta", "alpha edison", "beta charlie", "edison india"
			});

		_test(
			new String[] {"${configuration.operator}"}, new String[] {"and"},
			"withOperator",
			() -> _assertSearchIgnoreRelevance(
				"[alpha beta, alpha edison]", "alpha golf"));
		_test(
			new String[] {"${configuration.operator}"}, new String[] {"or"},
			"withOperator",
			() -> _assertSearchIgnoreRelevance(
				"[alpha beta, alpha edison, beta charlie]", "alpha golf"));
	}

	@Test
	public void testTextMatchOverMultipleFields_mostFields() throws Exception {
		_setUp(
			new String[] {
				"ipsum sit sit", "ipsum sit", "ipsum sit sit",
				"non-lorem ipsum sit"
			},
			new String[] {
				"amet", "lorem ipsum dolor", "lorem ipsum sit", "nunquis"
			});

		_test(
			new String[] {"${configuration.operator}"}, new String[] {"and"},
			"withOperator",
			() -> _assertSearch("[lorem ipsum sit, nunquis]", "sit lorem"));
		_test(
			new String[] {"${configuration.operator}"}, new String[] {"or"},
			"withOperator",
			() -> _assertSearch(
				"[lorem ipsum sit, lorem ipsum dolor, amet, nunquis]",
				"ipsum sit sit"));
	}

	@Test
	public void testTextMatchOverMultipleFields_phase() throws Exception {
		_setUp(
			new String[] {
				"do not listen to birds", "listen listen to birds",
				"listen to birds", "listen listen to birds"
			},
			new String[] {
				"listen something", "listen to birds", "listen to planes",
				"silence"
			});

		_test(
			null, null, "withKeywords",
			() -> _assertSearch("[listen to birds, silence]", "listen listen"));
	}

	@Test
	public void testTextMatchOverMultipleFields_phasePrefix() throws Exception {
		_setUp(
			new String[] {
				"simple things are beautiful sometimes",
				"simple things are beautiful", "simple things are not good",
				"simple things are bad"
			},
			new String[] {
				"clouds", "watch birds on the sky", "watch planes on the sky",
				"watch trains"
			});

		_test(
			null, null, "withKeywords",
			() -> _assertSearch(
				"[watch birds on the sky, clouds]", "simple things are beau"));
	}

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

	private SegmentsEntry _addSegmentsEntry(User user) throws Exception {
		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", user.getFirstName()),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());
	}

	private void _assertSearch(String expected, String keywords)
		throws Exception {

		SearchResponse searchResponse = _getSearchResponse(keywords);

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), "localized_title_en_US",
			expected);
	}

	private void _assertSearchIgnoreRelevance(String expected, String keywords)
		throws Exception {

		SearchResponse searchResponse = _getSearchResponse(keywords);

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), "localized_title_en_US",
			expected);
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

	private SearchResponse _getSearchResponse(String keywords)
		throws Exception {

		return _searcher.search(
			_searchRequestContributor.contribute(
				_searchRequestBuilderFactory.builder(
				).companyId(
					TestPropsValues.getCompanyId()
				).queryString(
					keywords
				).withSearchContext(
					_searchContext -> {
						_searchContext.setAttribute(
							"search.experiences.blueprint.id",
							String.valueOf(_sxpBlueprint.getSXPBlueprintId()));
						_searchContext.setAttribute(
							"search.experiences.current.group.id",
							_group.getGroupId());
						_searchContext.setTimeZone(_user.getTimeZone());
						_searchContext.setUserId(_serviceContext.getUserId());
					}
				).build()));
	}

	private void _setUp(
			String[] journalArticleContents, String[] journalArticleTitles)
		throws Exception {

		_setUp(
			journalArticleContents, journalArticleTitles,
			() -> {
			});
	}

	private void _setUp(
			String[] expandoBridgeAttributeNames, String[] journalArticleTitles,
			double[] latitudes, double[] longitudes,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		unsafeRunnable.run();

		for (int i = 0; i < journalArticleTitles.length; i++) {
			_serviceContext.setExpandoBridgeAttributes(
				Collections.singletonMap(
					expandoBridgeAttributeNames[i],
					JSONUtil.put(
						"latitude", latitudes[i]
					).put(
						"longitude", longitudes[i]
					).toString()));

			_setUp(new String[] {""}, new String[] {journalArticleTitles[i]});
		}
	}

	private void _setUp(
			String[] journalArticleContents, String[] journalArticleTitles,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		unsafeRunnable.run();

		Group group = _group;

		if (_groupA != null) {
			group = _groupA;
		}

		_journalArticles.add(
			JournalTestUtil.addArticle(
				group.getGroupId(), 0,
				PortalUtil.getClassNameId(JournalArticle.class),
				HashMapBuilder.put(
					LocaleUtil.US, journalArticleTitles[0]
				).build(),
				null,
				HashMapBuilder.put(
					LocaleUtil.US, journalArticleContents[0]
				).build(),
				LocaleUtil.getSiteDefault(), false, true, _serviceContext));

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
			JournalTestUtil.addArticle(
				group.getGroupId(), journalFolderId,
				PortalUtil.getClassNameId(JournalArticle.class),
				HashMapBuilder.put(
					LocaleUtil.US, journalArticleTitles[1]
				).build(),
				null,
				HashMapBuilder.put(
					LocaleUtil.US, journalArticleContents[1]
				).build(),
				LocaleUtil.getSiteDefault(), false, true, _serviceContext));

		for (int i = 2;
			 (journalArticleTitles.length > 2) &&
			 (i < journalArticleTitles.length); i++) {

			_journalArticles.add(
				JournalTestUtil.addArticle(
					_group.getGroupId(), 0,
					PortalUtil.getClassNameId(JournalArticle.class),
					HashMapBuilder.put(
						LocaleUtil.US, journalArticleTitles[i]
					).build(),
					null,
					HashMapBuilder.put(
						LocaleUtil.US, journalArticleContents[i]
					).build(),
					LocaleUtil.getSiteDefault(), false, true, _serviceContext));
		}
	}

	private void _test(
			String[] configurationNames, String[] configurationValues,
			String resourceName, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		String json = "{}";

		if (!Validator.isBlank(resourceName)) {
			Thread currentThread = Thread.currentThread();

			Class<?> clazz = getClass();

			StackTraceElement[] stackTraceElements =
				currentThread.getStackTrace();

			json = StringUtil.read(
				clazz.getResourceAsStream(
					StringBundler.concat(
						"dependencies/", clazz.getSimpleName(),
						StringPool.PERIOD,
						stackTraceElements[2].getMethodName(), "_",
						resourceName, ".json")));
		}

		if (configurationNames != null) {
			for (int i = 0; i < configurationNames.length; i++) {
				json = StringUtil.replace(
					json, configurationNames[i], configurationValues[i]);
			}
		}

		_sxpBlueprintLocalService.updateSXPBlueprint(
			_sxpBlueprint.getUserId(), _sxpBlueprint.getSXPBlueprintId(), json,
			_sxpBlueprint.getDescriptionMap(),
			_sxpBlueprint.getElementInstancesJSON(),
			_sxpBlueprint.getSchemaVersion(), _sxpBlueprint.getTitleMap(),
			_serviceContext);

		unsafeRunnable.run();
	}

	private int _addJournalArticleSleep;
	private AssetCategory _assetCategory;
	private AssetTag _assetTag;
	private AssetVocabulary _assetVocabulary;

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

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Inject
	private SearchRequestContributor _searchRequestContributor;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	private User _user;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}