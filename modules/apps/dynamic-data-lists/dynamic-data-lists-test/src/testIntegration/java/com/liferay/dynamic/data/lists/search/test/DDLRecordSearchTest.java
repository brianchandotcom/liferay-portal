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

package com.liferay.dynamic.data.lists.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
@Sync
public class DDLRecordSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpPermissionThreadLocal();
		setUpPrincipalThreadLocal();

		_group = GroupTestUtil.addGroup();

		DDLRecordSet recordSet = addRecordSet();

		_recordTestHelper = new DDLRecordTestHelper(_group, recordSet);
		_searchContext = getSearchContext(_group, recordSet);
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
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
	public void testExactPhraseMixedWithWords() throws Exception {
		addRecord("One Two Three Four Five Six", RandomTestUtil.randomString());
		addRecord(RandomTestUtil.randomString(), RandomTestUtil.randomString());

		assertSearch("\"Two Three\" Five", 1);
		assertSearch("\"Two Three\" Nine", 1);
		assertSearch("\"Two  Four\" Five", 1);
		assertSearch("\"Two  Four\" Nine", 0);
		assertSearch("Three \"Five Six\"", 1);
		assertSearch("Zero  \"Five Six\"", 1);
		assertSearch("Three \"Four Six\"", 1);
		assertSearch("Zero  \"Four Six\"", 0);
		assertSearch("One  \"Three Four\" Six ", 1);
		assertSearch("Zero \"Three Four\" Nine", 1);
		assertSearch("One  \"Three Five\" Six ", 1);
		assertSearch("Zero \"Three Five\" Nine", 0);
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
		Assume.assumeTrue(isExactPhraseQueryImplementedForSearchEngine());

		addRecord(RandomTestUtil.randomString());
		addRecord("how with create the coupon");

		assertSearch("\"how ? create ? coupon\"", 0);
		assertSearch("\"how to create a coupon\"", 1);
		assertSearch("\"how with create the coupon\"", 1);
	}

	@Test
	public void testQuestionMarksVersusStopwords3() throws Exception {
		Assume.assumeTrue(isExactPhraseQueryImplementedForSearchEngine());

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
		Assume.assumeTrue(isExactPhraseQueryImplementedForSearchEngine());

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

	protected static SearchContext getSearchContext(
			Group group, DDLRecordSet recordSet)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setAttribute("recordSetId", recordSet.getRecordSetId());
		searchContext.setAttribute("status", WorkflowConstants.STATUS_ANY);

		return searchContext;
	}

	protected void addRecord(String name) throws Exception {
		addRecord(name, RandomTestUtil.randomString());
	}

	protected void addRecord(String name, String description) throws Exception {
		DDMFormValues ddmFormValues = createDDMFormValues();

		DDMFormFieldValue nameDDMFormFieldValue =
			createLocalizedDDMFormFieldValue("name", name);

		ddmFormValues.addDDMFormFieldValue(nameDDMFormFieldValue);

		DDMFormFieldValue descriptionDDMFormFieldValue =
			createLocalizedDDMFormFieldValue("description", description);

		ddmFormValues.addDDMFormFieldValue(descriptionDDMFormFieldValue);

		_recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);
	}

	protected DDLRecordSet addRecordSet() throws Exception {
		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			_group);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDLRecordSet.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			createDDMForm(), StorageType.JSON.toString());

		return recordSetTestHelper.addRecordSet(ddmStructure);
	}

	protected void assertSearch(final String keywords, final int length)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_searchContext.setKeywords(keywords);

					Hits hits = DDLRecordLocalServiceUtil.search(
						_searchContext);

					Assert.assertEquals(length, hits.getLength());

					return null;
				}

			});
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", true, false, false);

		nameDDMFormField.setIndexType("keyword");

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormField descriptionDDMFormField =
			DDMFormTestUtil.createTextDDMFormField(
				"description", true, false, false);

		descriptionDDMFormField.setIndexType("text");

		ddmForm.addDDMFormField(descriptionDDMFormField);

		return ddmForm;
	}

	protected DDMFormValues createDDMFormValues() throws Exception {
		DDLRecordSet recordSet = _recordTestHelper.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return DDMFormValuesTestUtil.createDDMFormValues(
			ddmStructure.getDDMForm(),
			DDMFormValuesTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);
	}

	protected DDMFormFieldValue createLocalizedDDMFormFieldValue(
		String name, String enValue) {

		return DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
			name, enValue);
	}

	protected boolean isExactPhraseQueryImplementedForSearchEngine() {
		SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine(
			SearchEngineHelperUtil.getDefaultSearchEngineId());

		String vendor = searchEngine.getVendor();

		if (vendor.equals("Elasticsearch") || vendor.equals("Solr")) {
			return false;
		}

		return true;
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new SimplePermissionChecker() {

				@Override
				public boolean hasOwnerPermission(
					long companyId, String name, String primKey, long ownerId,
					String actionId) {

					return true;
				}

			});
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	@DeleteAfterTestRun
	private Group _group;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private DDLRecordTestHelper _recordTestHelper;
	private SearchContext _searchContext;

}