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
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class ExternalReferenceCodeModelDocumentContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_serviceContext = ServiceContextTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		_blogsEntry = BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), false,
			_serviceContext);
		_journalArticle = JournalTestUtil.addArticle(
			TestPropsValues.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null,
			HashMapBuilder.put(
				LocaleUtil.US, StringPool.BLANK
			).build(),
			LocaleUtil.getSiteDefault(), false, true, _serviceContext);
		_journalFolder = JournalFolderServiceUtil.addFolder(
			null, TestPropsValues.getGroupId(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, _serviceContext);

		_user = UserTestUtil.addUser(TestPropsValues.getGroupId());
	}

	@Test
	public void testContribute() throws Exception {
		_testContribute(_blogsEntry, _EXTERNAL_REFERENCE_CODE_FIELD_NAME);
		_testContribute(_journalArticle, _EXTERNAL_REFERENCE_CODE_FIELD_NAME);
		_testContribute(_journalFolder, _EXTERNAL_REFERENCE_CODE_FIELD_NAME);
		_testContribute(_user, _EXTERNAL_REFERENCE_CODE_FIELD_NAME);
	}

	private boolean _isSearchEngineSolr() {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		return Objects.equals(searchEngine.getVendor(), "Solr");
	}

	private void _testContribute(
			ExternalReferenceCodeModel externalReferenceCodeModel,
			String fieldName)
		throws Exception {

		_testContribute(
			externalReferenceCodeModel,
			externalReferenceCodeModel.getExternalReferenceCode(), fieldName);
	}

	private void _testContribute(
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
				_queries.term(Field.GROUP_ID, TestPropsValues.getGroupId()));
		}

		countSearchRequest.setQuery(booleanQuery);

		CountSearchResponse countSearchResponse = _searchEngineAdapter.execute(
			countSearchRequest);

		Assert.assertTrue(
			StringBundler.concat(
				"Expected to find 1 document containing field ", fieldName,
				" with value ", externalReferenceCode, ". Instead found ",
				countSearchResponse.getCount(), "."),
			countSearchResponse.getCount() == 1);
	}

	private static final String _EXTERNAL_REFERENCE_CODE_FIELD_NAME =
		"externalReferenceCode";

	private BlogsEntry _blogsEntry;
	private JournalArticle _journalArticle;
	private JournalFolder _journalFolder;

	@Inject
	private Queries _queries;

	@Inject
	private SearchEngineAdapter _searchEngineAdapter;

	@Inject
	private SearchEngineHelper _searchEngineHelper;

	private ServiceContext _serviceContext;
	private User _user;

}