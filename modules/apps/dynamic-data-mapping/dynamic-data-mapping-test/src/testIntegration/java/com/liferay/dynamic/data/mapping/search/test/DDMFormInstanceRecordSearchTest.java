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
import com.liferay.dynamic.data.mapping.helper.DDMFormInstanceRecordTestHelper;
import com.liferay.dynamic.data.mapping.helper.DDMFormInstanceTestHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashMap;
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
		_user = UserTestUtil.addUser();

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		DDMFormInstance ddmFormInstance = addDDMFormInstance();

		_ddmFormInstanceRecordTestHelper = new DDMFormInstanceRecordTestHelper(
			_group, ddmFormInstance);
		_searchContext = getSearchContext(_group, _user, ddmFormInstance);
	}

	@Test
	public void testBasicSearchWithDefaultUser() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		User user = UserLocalServiceUtil.getDefaultUser(companyId);

		addDDMFormInstanceRecord("Joe Bloggs", "Simple description");

		_searchContext.setKeywords("Simple description");

		_searchContext.setUserId(user.getUserId());

		assertSearch("description", 1);
	}

	@Test
	public void testBasicSearchWithJustOneTerm() throws Exception {
		addDDMFormInstanceRecord("Joe Bloggs", "Simple description");
		addDDMFormInstanceRecord("Bloggs", "Another description example");
		addDDMFormInstanceRecord(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		assertSearch("example", 1);
		assertSearch("description", 2);
	}

	@Test
	public void testExactPhrase() throws Exception {
		addDDMFormInstanceRecord("Joe Bloggs", "Simple description");
		addDDMFormInstanceRecord("Bloggs", "Another description example");
		addDDMFormInstanceRecord(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		assertSearch("\"Joe Bloggs\"", 1);
		assertSearch("Bloggs", 2);
	}

	@Test
	public void testNonindexableField() throws Exception {
		Locale[] locales = {LocaleUtil.US};

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(locales), locales[0]);

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", true, false, false);

		nameDDMFormField.setIndexType("keyword");

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormField notIndexableDDMFormField =
			DDMFormTestUtil.createTextDDMFormField(
				"notIndexable", true, false, false);

		notIndexableDDMFormField.setIndexType("");

		DDMFormInstanceTestHelper ddmFormInstanceTestHelper =
			new DDMFormInstanceTestHelper(_group);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDMFormInstance.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.toString());

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceTestHelper.addDDMFormInstance(ddmStructure);

		_ddmFormInstanceRecordTestHelper = new DDMFormInstanceRecordTestHelper(
			_group, ddmFormInstance);
		_searchContext = getSearchContext(_group, _user, ddmFormInstance);

		addDDMFormInstanceRecord("Liferay", "Not indexable name");

		assertSearch("Liferay", 1);
		assertSearch("Not indexable name", 0);
	}

	@Test
	public void testStopwords() throws Exception {
		addDDMFormInstanceRecord(RandomTestUtil.randomString(), "Simple text");
		addDDMFormInstanceRecord(
			RandomTestUtil.randomString(), "Another description example");

		assertSearch("Another The Example", 1);
	}

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

	protected DDMFormInstance addDDMFormInstance() throws Exception {
		DDMFormInstanceTestHelper ddmFormInstanceTestHelper =
			new DDMFormInstanceTestHelper(_group);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDMFormInstance.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			createDDMForm(LocaleUtil.US), StorageType.JSON.toString());

		return ddmFormInstanceTestHelper.addDDMFormInstance(ddmStructure);
	}

	protected void addDDMFormInstanceRecord(
			Map<Locale, String> name, Map<Locale, String> description)
		throws Exception {

		Locale[] locales = new Locale[name.size()];

		Set<Locale> localesKeySet = name.keySet();

		localesKeySet.toArray(locales);

		DDMFormValues ddmFormValues = createDDMFormValues(locales);

		DDMFormFieldValue nameDDMFormFieldValue =
			createLocalizedDDMFormFieldValue("name", name);

		ddmFormValues.addDDMFormFieldValue(nameDDMFormFieldValue);

		DDMFormFieldValue descriptionDDMFormFieldValue =
			createLocalizedDDMFormFieldValue("description", description);

		ddmFormValues.addDDMFormFieldValue(descriptionDDMFormFieldValue);

		_ddmFormInstanceRecordTestHelper.addDDMFormInstanceRecord(
			ddmFormValues);
	}

	protected void addDDMFormInstanceRecord(String name, String description)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.US, description);

		addDDMFormInstanceRecord(nameMap, descriptionMap);
	}

	protected void assertSearch(String keywords, int length) throws Exception {
		_searchContext.setKeywords(keywords);

		BaseModelSearchResult<DDMFormInstanceRecord> result =
			DDMFormInstanceRecordLocalServiceUtil.searchFormInstanceRecords(
				_searchContext);

		Assert.assertEquals(length, result.getLength());
	}

	protected DDMForm createDDMForm(Locale... locales) {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(locales), locales[0]);

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

	protected DDMFormValues createDDMFormValues(Locale... locales)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceRecordTestHelper.getDDMFormInstance();

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return DDMFormValuesTestUtil.createDDMFormValues(
			ddmStructure.getDDMForm(),
			DDMFormValuesTestUtil.createAvailableLocales(locales), locales[0]);
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

	private DDMFormInstanceRecordTestHelper _ddmFormInstanceRecordTestHelper;

	@DeleteAfterTestRun
	private Group _group;

	private SearchContext _searchContext;

	@DeleteAfterTestRun
	private User _user;

}