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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.SearchContextTestUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestHelper;

import org.junit.Assert;
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
		ddlRecordTestHelper = new DDLRecordTestHelper(this);
		group = ddlRecordTestHelper.getGroup();
		searchContext = getSearchContext(null);
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
		addRecord("Joe Bloggs", "Simple description");
		addRecord("Bloggs", "Another description example");
		addRecord(RandomTestUtil.randomString(), RandomTestUtil.randomString());

		assertSearch("\"Joe Bloggs\"", 1);
		assertSearch("Bloggs", 2);
	}

	@Test
	public void testPunctuationInExactPhrase() throws Exception {
		addRecord("Joe? Bloggs!");
		addRecord("Joe! Bloggs?");
		addRecord("Joe Bloggs");
		addRecord("Bloggs");

		assertSearch("\"Joe? Bloggs!\"", 3);
		assertSearch("\"Joe! Bloggs?\"", 3);
	}

	@Test
	public void testQuestionMarksVersusStopwords1() throws Exception {
		addRecord(RandomTestUtil.randomString());
		addRecord("how ? create ? coupon");

		assertSearch("\"how ? create ? coupon\"", 1);
		assertSearch("\"how to create a coupon\"", 0);
		assertSearch("\"how with create the coupon\"", 0);
	}

	@Test
	public void testQuestionMarksVersusStopwords2() throws Exception {
		addRecord(RandomTestUtil.randomString());
		addRecord("how with create the coupon");

		assertSearch("\"how ? create ? coupon\"", 0);
		assertSearch("\"how to create a coupon\"", 1);
		assertSearch("\"how with create the coupon\"", 1);
	}

	@Test
	public void testQuestionMarksVersusStopwords3() throws Exception {
		addRecord(RandomTestUtil.randomString());
		addRecord("how to create a coupon");

		assertSearch("\"how ? create ? coupon\"", 0);
		assertSearch("\"how to create a coupon\"", 1);
		assertSearch("\"how with create the coupon\"", 1);
	}

	@Test
	public void testQuestionMarksVersusStopwords4() throws Exception {
		addRecord(RandomTestUtil.randomString());
		addRecord("how ! create ! coupon");

		assertSearch("\"how ? create ? coupon\"", 1);
		assertSearch("\"how to create a coupon\"", 0);
		assertSearch("\"how with create the coupon\"", 0);
	}

	@Test
	public void testStopwords() throws Exception {
		addRecord(RandomTestUtil.randomString());
		addRecord(RandomTestUtil.randomString(), "Another description example");

		assertSearch("Another The Example", 1);
	}

	@Test
	public void testStopwordsInExactPhrase() throws Exception {
		addRecord("how to create a coupon");
		addRecord("Joe Of Bloggs");
		addRecord("Joe Bloggs");
		addRecord("Bloggs");

		assertSearch("\"how to create a coupon\"", 1);
		assertSearch("\"how with create the coupon\"", 1);
		assertSearch("\"how Liferay create Liferay coupon\"", 0);
		assertSearch("\"how create coupon\"", 0);
		assertSearch("\"Joe Of Bloggs\"", 1);
		assertSearch("\"Joe The Bloggs\"", 1);
		assertSearch("\"Bloggs A\"", 3);
		assertSearch("\"Of Bloggs\"", 3);
		assertSearch("\"The Bloggs\"", 3);
	}

	protected void addRecord(String name) throws Exception {
		addRecord(name, RandomTestUtil.randomString());
	}

	protected void addRecord(String name, String description) throws Exception {
		ddlRecordTestHelper.addRecord(
			name, description, WorkflowConstants.ACTION_PUBLISH);
	}

	protected void assertSearch(String keywords, int length) {
		searchContext.setKeywords(keywords);

		Hits hits = DDLRecordLocalServiceUtil.search(searchContext);

		Assert.assertEquals(length, hits.getLength());
	}

	protected SearchContext getSearchContext(String keywords) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		DDLRecordSet recordSet = ddlRecordTestHelper.getRecordSet();

		searchContext.setAttribute("recordSetId", recordSet.getRecordSetId());
		searchContext.setAttribute("status", WorkflowConstants.STATUS_ANY);
		searchContext.setKeywords(keywords);

		return searchContext;
	}

	protected DDLRecordTestHelper ddlRecordTestHelper;

	@DeleteAfterTestRun
	protected Group group;

	protected SearchContext searchContext;

}