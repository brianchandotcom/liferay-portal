/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.dynamicdatalists.search;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.SearchContextTestUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordSetTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestHelper;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestHelper;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DDLRecordSearchTest {

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();

		DDLRecordSetTestHelper ddlRecordSetTestHelper =
			new DDLRecordSetTestHelper(group);
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(group);
		DDLRecordSet ddlRecordSet = ddlRecordSetTestHelper.addRecordSet(
			ddmStructureTestHelper.addStructure(this.getClass()));

		_ddlRecordTestHelper = new DDLRecordTestHelper(ddlRecordSet, group);
		_group = group;
		_searchContext = _getSearchContext(group, ddlRecordSet);
	}

	@Test
	public void testBasicSearchWithJustOneTerm() throws Exception {
		addRecord("Joe Bloggs", "Simple description");
		addRecord("Bloggs", "Another description example");
		addRecord(RandomTestUtil.randomString(), RandomTestUtil.randomString());

		assertSearch("example", 1);
		assertSearch("description", 2);
	}

	@Test
	public void testExactPhrase() throws Exception {
		Assume.assumeTrue(isExactPhraseQueryImplementedForSearchEngine());

		addRecord("Joe Bloggs", "Simple description");
		addRecord("Bloggs", "Another description example");
		addRecord(RandomTestUtil.randomString(), RandomTestUtil.randomString());

		assertSearch("\"Joe Bloggs\"", 1);
		assertSearch("Bloggs", 2);
	}

	protected void addRecord(String name, String description) throws Exception {
		_ddlRecordTestHelper.addRecord(
			name, description, WorkflowConstants.ACTION_PUBLISH);
	}

	protected void assertSearch(String keywords, int length) {
		_searchContext.setKeywords(keywords);

		Hits hits = DDLRecordLocalServiceUtil.search(_searchContext);

		Assert.assertEquals(length, hits.getLength());
	}

	protected boolean isExactPhraseQueryImplementedForSearchEngine() {
		SearchEngine searchEngine = SearchEngineUtil.getSearchEngine(
			SearchEngineUtil.getDefaultSearchEngineId());

		String vendor = searchEngine.getVendor();

		if ("SOLR".equals(vendor) || "Lucene".equals(vendor)) {
			return true;
		}

		return false;
	}

	private static SearchContext _getSearchContext(
			Group group, DDLRecordSet recordSet)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setAttribute("recordSetId", recordSet.getRecordSetId());
		searchContext.setAttribute("status", WorkflowConstants.STATUS_ANY);

		return searchContext;
	}

	private DDLRecordTestHelper _ddlRecordTestHelper;

	@DeleteAfterTestRun
	private Group _group;

	private SearchContext _searchContext;

}