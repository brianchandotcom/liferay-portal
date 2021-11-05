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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.search.experiences.internal.blueprint.util.SXPBlueprintTestUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalServiceUtil;

import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * @author Wade Cao
 */
public abstract class BaseSXPBlueprintsTestCase {

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		group = GroupTestUtil.addGroup();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group, TestPropsValues.getUserId());

		user = TestPropsValues.getUser();
	}

	@Rule
	public TestName testName = new TestName();

	protected AssetCategory addAssetCategory(
			String categoryTitle, Group group, User user)
		throws Exception {

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				group.getGroupId());

		return AssetCategoryLocalServiceUtil.addCategory(
			user.getUserId(), group.getGroupId(), categoryTitle,
			assetVocabulary.getVocabularyId(), serviceContext);
	}

	protected Group addGroup(String name) throws Exception {
		return GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, name, serviceContext);
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

	protected JournalArticle addJournalArticle(
			String title, String expandoColumn, double latitude,
			double longitude)
		throws Exception {

		serviceContext.setExpandoBridgeAttributes(
			Collections.singletonMap(
				expandoColumn,
				SXPBlueprintTestUtil.getGeolocationValue(latitude, longitude)));

		return addJournalArticle(group.getGroupId(), title, "");
	}

	protected JournalFolder addJournalFolder(long groupId) throws Exception {
		return JournalFolderServiceUtil.addFolder(
			groupId, 0, RandomTestUtil.randomString(), StringPool.BLANK,
			serviceContext);
	}

	protected void assertSearch(String expected, String keywords)
		throws Exception {

		SearchResponse searchResponse = _getSearchResponse(keywords);

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), FIELD_NAME, expected);
	}

	protected void assertSearchIgnoreRelevance(String expected, String keywords)
		throws Exception {

		SearchResponse searchResponse = _getSearchResponse(keywords);

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), FIELD_NAME, expected);
	}

	protected String getConfigurationJSONString(Class<?> clazz) {
		return getConfigurationJSONString(clazz, null);
	}

	protected String getConfigurationJSONString(Class<?> clazz, String name) {
		String subpath = StringPool.PERIOD;

		if (!Validator.isBlank(name)) {
			subpath += name + StringPool.PERIOD;
		}

		return StringUtil.read(
			clazz,
			StringBundler.concat(
				"dependencies/", clazz.getSimpleName(), subpath, "json"));
	}

	protected String getEmptyConfigurationJSONString() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		return jsonObject.toString();
	}

	protected void setUpSXPBlueprint(Class<?> clazz) throws Exception {
		_sxpBlueprint = SXPBlueprintLocalServiceUtil.addSXPBlueprint(
			user.getUserId(), getEmptyConfigurationJSONString(),
			Collections.singletonMap(LocaleUtil.US, ""), null,
			Collections.singletonMap(
				LocaleUtil.US, clazz.getName() + "-Blueprint"),
			serviceContext);
	}

	protected SXPBlueprint updateSXPBlueprint(String configurationJSON)
		throws Exception {

		return SXPBlueprintLocalServiceUtil.updateSXPBlueprint(
			_sxpBlueprint.getUserId(), _sxpBlueprint.getSXPBlueprintId(),
			configurationJSON, _sxpBlueprint.getDescriptionMap(),
			_sxpBlueprint.getElementInstancesJSON(),
			_sxpBlueprint.getTitleMap(), serviceContext);
	}

	protected static final String FIELD_NAME = "localized_title_en_US";

	@DeleteAfterTestRun
	protected Group group;

	protected ServiceContext serviceContext;
	protected User user;

	private SearchResponse _getSearchResponse(String keywords)
		throws Exception {

		SearchRequest searchRequest = _searchRequestBuilderFactory.builder(
		).companyId(
			TestPropsValues.getCompanyId()
		).queryString(
			keywords
		).withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"search.experiences.blueprint.id",
					_sxpBlueprint.getSXPBlueprintId());
				searchContext.setUserId(user.getUserId());
			}
		).build();

		return _searcher.search(searchRequest);
	}

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

}