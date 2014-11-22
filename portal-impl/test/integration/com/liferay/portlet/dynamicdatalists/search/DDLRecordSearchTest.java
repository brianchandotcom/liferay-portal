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
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.SearchContextTestUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordSetTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestHelper;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
@ExecutionTestListeners(
	listeners = {
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DDLRecordSearchTest {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		DDLRecordSet recordSet = addRecordSet();

		_recordTestHelper = new DDLRecordTestHelper(_group, recordSet);
		_searchContext = getSearchContext(_group, recordSet);
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

	protected static SearchContext getSearchContext(
			Group group, DDLRecordSet recordSet)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setAttribute("recordSetId", recordSet.getRecordSetId());
		searchContext.setAttribute("status", WorkflowConstants.STATUS_ANY);

		return searchContext;
	}

	protected void addRecord(String name, String description) throws Exception {
		DDMFormValues ddmFormValues = createDDMFormValues();

		DDMFormFieldValue nameDDMFormFieldValue =
			createLocalizedTextDDMFormFieldValue("name", name);

		ddmFormValues.addDDMFormFieldValue(nameDDMFormFieldValue);

		DDMFormFieldValue descriptionDDMFormFieldValue =
			createLocalizedTextDDMFormFieldValue("description", description);

		ddmFormValues.addDDMFormFieldValue(descriptionDDMFormFieldValue);

		_recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);
	}

	protected DDLRecordSet addRecordSet() throws Exception {
		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			_group);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(_group);

		return recordSetTestHelper.addRecordSet(
			ddmStructureTestHelper.addStructureXsd(this.getClass()));
	}

	protected void assertSearch(String keywords, int length) {
		_searchContext.setKeywords(keywords);

		Hits hits = DDLRecordLocalServiceUtil.search(_searchContext);

		Assert.assertEquals(length, hits.getLength());
	}

	protected DDMFormValues createDDMFormValues() throws Exception {
		DDLRecordSet recordSet = _recordTestHelper.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return DDLRecordTestUtil.createDDMFormValues(ddmStructure.getDDMForm());
	}

	protected DDMFormFieldValue createLocalizedTextDDMFormFieldValue(
		String name, String enValue) {

		return DDLRecordTestUtil.createLocalizedTextDDMFormFieldValue(
			name, enValue);
	}

	protected boolean isExactPhraseQueryImplementedForSearchEngine() {
		SearchEngine searchEngine = SearchEngineUtil.getSearchEngine(
			SearchEngineUtil.getDefaultSearchEngineId());

		String vendor = searchEngine.getVendor();

		if (vendor.equals("Lucene") || vendor.equals("SOLR")) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private Group _group;

	private DDLRecordTestHelper _recordTestHelper;
	private SearchContext _searchContext;

}