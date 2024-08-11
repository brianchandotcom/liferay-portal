/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class BaseModelDocumentContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		user = UserTestUtil.addUser(group.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			group, user.getUserId());

		blogsEntry = BlogsTestUtil.addEntryWithWorkflow(
			user.getUserId(), RandomTestUtil.randomString(), false,
			_serviceContext);

		journalArticle = JournalTestUtil.addArticle(
			group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null,
			HashMapBuilder.put(
				LocaleUtil.US, StringPool.BLANK
			).build(),
			LocaleUtil.getSiteDefault(), false, true, _serviceContext);
		journalFolder = _journalFolderLocalService.addFolder(
			null, user.getUserId(), group.getGroupId(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, _serviceContext);
	}

	protected void testContribute(
			ExternalReferenceCodeModel externalReferenceCodeModel,
			String fieldName)
		throws Exception {

		testContribute(
			externalReferenceCodeModel,
			externalReferenceCodeModel.getExternalReferenceCode(), fieldName);
	}

	protected void testContribute(
			ExternalReferenceCodeModel externalReferenceCodeModel,
			String externalReferenceCode, String fieldName)
		throws Exception {

		CountSearchRequest countSearchRequest = new CountSearchRequest();

		if (_isSearchEngineSolr()) {
			countSearchRequest.setIndexNames("liferay");
		}
		else {
			countSearchRequest.setIndexNames(
				"liferay-" + TestPropsValues.getCompanyId());
		}

		BooleanQuery booleanQuery = _queries.booleanQuery();

		ClassedModel classedModel = (ClassedModel)externalReferenceCodeModel;

		booleanQuery.addMustQueryClauses(
			_queries.term(Field.COMPANY_ID, TestPropsValues.getCompanyId()),
			_queries.term(
				Field.ENTRY_CLASS_NAME, classedModel.getModelClassName()),
			_queries.term(fieldName, externalReferenceCode));

		if (externalReferenceCodeModel instanceof GroupedModel) {
			booleanQuery.addMustQueryClauses(
				_queries.term(Field.GROUP_ID, group.getGroupId()));
		}

		countSearchRequest.setQuery(booleanQuery);

		CountSearchResponse countSearchResponse = _searchEngineAdapter.execute(
			countSearchRequest);

		Assert.assertTrue(countSearchResponse.getCount() == 1);
	}

	protected BlogsEntry blogsEntry;
	protected Group group;
	protected JournalArticle journalArticle;
	protected JournalFolder journalFolder;
	protected User user;

	private boolean _isSearchEngineSolr() {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		return Objects.equals(searchEngine.getVendor(), "Solr");
	}

	@Inject
	private JournalFolderLocalService _journalFolderLocalService;

	@Inject
	private Queries _queries;

	@Inject
	private SearchEngineAdapter _searchEngineAdapter;

	@Inject
	private SearchEngineHelper _searchEngineHelper;

	private ServiceContext _serviceContext;

}