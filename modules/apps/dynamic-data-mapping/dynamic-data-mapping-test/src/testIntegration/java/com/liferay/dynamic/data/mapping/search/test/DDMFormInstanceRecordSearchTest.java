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

package com.liferay.dynamic.data.mapping.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceRecordTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lino Alves
 * @author Marcela Cunha
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceRecordSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupAdminUser(_group);

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		keyValuePairs.add(new KeyValuePair("name", "keyword"));
		keyValuePairs.add(new KeyValuePair("description", "text"));

		_ddmFormInstance = DDMFormInstanceTestUtil.addDDMFormInstance(
			createDDMForm(keyValuePairs, LocaleUtil.US), _group,
			_user.getUserId());

		_searchContext = getSearchContext(_group, _user, _ddmFormInstance);
	}

	@Test
	public void testBasicSearchWithGroupUser() throws Exception {
		User user = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_MEMBER);

		addDDMFormInstanceRecord(
			_ddmFormInstance, "Simple description", "Joe Bloggs");

		_searchContext.setKeywords("Simple description");
		_searchContext.setUserId(user.getUserId());

		assertSearch("description", 0);
	}

	@Test
	public void testBasicSearchWithGuestUser() throws Exception {
		User user = UserTestUtil.addUser();

		addDDMFormInstanceRecord(
			_ddmFormInstance, "Simple description", "Joe Bloggs");

		_searchContext.setKeywords("Simple description");

		_searchContext.setUserId(user.getUserId());

		assertSearch("description", 0);
	}

	@Test
	public void testBasicSearchWithJustOneTerm() throws Exception {
		addDDMFormInstanceRecord(
			_ddmFormInstance, "Simple description", "Joe Bloggs");
		addDDMFormInstanceRecord(
			_ddmFormInstance, "Another description example", "Bloggs");
		addDDMFormInstanceRecord(
			_ddmFormInstance, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		assertSearch("example", 1);
		assertSearch("description", 2);
	}

	@Test
	public void testExactPhrase() throws Exception {
		addDDMFormInstanceRecord(
			_ddmFormInstance, "Simple description", "Joe Bloggs");
		addDDMFormInstanceRecord(
			_ddmFormInstance, "Another description example", "Bloggs");
		addDDMFormInstanceRecord(
			_ddmFormInstance, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		assertSearch("\"Joe Bloggs\"", 1);
		assertSearch("Bloggs", 2);
	}

	@Test
	public void testNonindexableField() throws Exception {
		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		keyValuePairs.add(new KeyValuePair("name", "keyword"));
		keyValuePairs.add(new KeyValuePair("description", ""));

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				createDDMForm(keyValuePairs, LocaleUtil.US), _group,
				_user.getUserId());

		_searchContext = getSearchContext(_group, _user, ddmFormInstance);

		addDDMFormInstanceRecord(
			ddmFormInstance, "Not indexable name", "Liferay");

		assertSearch("Liferay", 1);
		assertSearch("Not indexable name", 0);

		DDMFormInstanceTestUtil.deleteFormInstance(ddmFormInstance);
	}

	@Test
	public void testStopwords() throws Exception {
		addDDMFormInstanceRecord(
			_ddmFormInstance, "Simple text", RandomTestUtil.randomString());
		addDDMFormInstanceRecord(
			_ddmFormInstance, "Another description example",
			RandomTestUtil.randomString());

		assertSearch("Another The Example", 1);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected static SearchContext getSearchContext(
			Group group, User user, DDMFormInstance ddmFormInstance)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setAttribute(
			"formInstanceId", ddmFormInstance.getFormInstanceId());
		searchContext.setAttribute("status", WorkflowConstants.STATUS_ANY);
		searchContext.setUserId(user.getUserId());

		return searchContext;
	}

	protected void addDDMFormInstanceRecord(
			DDMFormInstance ddmFormInstance, String description, String name)
		throws Exception {

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.US, name
		).build();

		Set<Locale> localesKeySet = nameMap.keySet();

		Locale[] locales = new Locale[nameMap.size()];

		localesKeySet.toArray(locales);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmFormInstance.getDDMForm(),
			DDMFormValuesTestUtil.createAvailableLocales(locales), locales[0]);

		DDMFormFieldValue nameDDMFormFieldValue =
			createLocalizedDDMFormFieldValue("name", nameMap);

		ddmFormValues.addDDMFormFieldValue(nameDDMFormFieldValue);

		DDMFormFieldValue descriptionDDMFormFieldValue =
			createLocalizedDDMFormFieldValue(
				"description",
				HashMapBuilder.put(
					LocaleUtil.US, description
				).build());

		ddmFormValues.addDDMFormFieldValue(descriptionDDMFormFieldValue);

		DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
			ddmFormInstance, ddmFormValues, _group, _user.getUserId());
	}

	protected void assertSearch(String keywords, int length) throws Exception {
		_searchContext.setKeywords(keywords);

		BaseModelSearchResult<DDMFormInstanceRecord> result =
			DDMFormInstanceRecordLocalServiceUtil.searchFormInstanceRecords(
				_searchContext);

		Assert.assertEquals(length, result.getLength());
	}

	protected DDMForm createDDMForm(
		List<KeyValuePair> keyValuePairs, Locale... locales) {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(locales), locales[0]);

		keyValuePairs.forEach(
			keyValuePair -> ddmForm.addDDMFormField(
				_createDDMFormFieldWithCustomIndexType(keyValuePair)));

		return ddmForm;
	}

	protected DDMFormFieldValue createLocalizedDDMFormFieldValue(
		String name, Map<Locale, String> values) {

		Value localizedValue = new LocalizedValue(LocaleUtil.US);

		for (Map.Entry<Locale, String> value : values.entrySet()) {
			localizedValue.addString(value.getKey(), value.getValue());
		}

		return DDMFormValuesTestUtil.createDDMFormFieldValue(
			name, localizedValue);
	}

	private DDMFormField _createDDMFormFieldWithCustomIndexType(
		KeyValuePair keyValuePair) {

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			keyValuePair.getKey(), true, false, false);

		ddmFormField.setIndexType(keyValuePair.getValue());

		return ddmFormField;
	}

	@DeleteAfterTestRun
	private DDMFormInstance _ddmFormInstance;

	@DeleteAfterTestRun
	private Group _group;

	private SearchContext _searchContext;

	@DeleteAfterTestRun
	private User _user;

}