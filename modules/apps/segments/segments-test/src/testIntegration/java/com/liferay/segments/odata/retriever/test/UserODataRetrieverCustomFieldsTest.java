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

package com.liferay.segments.odata.retriever.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class UserODataRetrieverCustomFieldsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + ODataRetriever.class.getName() +
				")(model.class.name=com.liferay.portal.kernel.model.User))");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@After
	public void tearDown() {
		_serviceTracker.close();
	}

	@Test
	public void testGetUsersFilterByCustomFieldWithEqualsAndStringKeywordType()
		throws Exception {

		String columnName = "keywordColumn";

		_addExpandoColumn(
			User.class, columnName, ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		String columnValue = "Software Engineer";

		User user = _addUser(columnName, columnValue);

		_addUser(columnName, RandomTestUtil.randomString());

		_addUser();

		String filterString =
			"(customField/" + columnName + " eq '" + columnValue + "')";

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 5);

		Assert.assertEquals(user, users.get(0));
	}

	private void _addExpandoColumn(
			Class<?> clazz, String columnName, int indexType)
		throws Exception {

		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(clazz), "CUSTOM_FIELDS");

		if (expandoTable == null) {
			expandoTable = _expandoTableLocalService.addTable(
				TestPropsValues.getCompanyId(),
				_classNameLocalService.getClassNameId(clazz), "CUSTOM_FIELDS");

			_expandoTables.add(expandoTable);
		}

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			expandoTable, columnName, ExpandoColumnConstants.STRING);

		_expandoColumns.add(expandoColumn);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		_expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	private User _addUser() throws Exception {
		return _addUser(ServiceContextTestUtil.getServiceContext());
	}

	private User _addUser(ServiceContext serviceContext) throws Exception {
		long creatorUserId = TestPropsValues.getUserId();
		long companyId = TestPropsValues.getCompanyId();
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = false;
		String screenName = RandomTestUtil.randomString();
		String emailAddress = RandomTestUtil.randomString() + "@liferay.com";
		long facebookId = 0;
		String openId = null;
		Locale locale = LocaleUtil.getDefault();
		String firstName = RandomTestUtil.randomString();
		String middleName = RandomTestUtil.randomString();
		String lastName = RandomTestUtil.randomString();
		long prefixId = 0;
		long suffixId = 0;
		boolean male = false;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = null;
		long[] groupIds = {TestPropsValues.getGroupId()};
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		User user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendMail, serviceContext);

		_users.add(user);

		return user;
	}

	private User _addUser(String columnName, String columnValue)
		throws Exception {

		ServiceContext serviceContext = _getServiceContext(
			columnName, columnValue);

		return _addUser(serviceContext);
	}

	private ODataRetriever<User> _getODataRetriever() {
		return _serviceTracker.getService();
	}

	private ServiceContext _getServiceContext(
			String columnName, String columnValue)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		Map<String, Serializable> expandoBridgeAttributes = new HashMap<>();

		expandoBridgeAttributes.put(columnName, columnValue);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		return serviceContext;
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private static ExpandoTableLocalService _expandoTableLocalService;

	private static ServiceTracker<ODataRetriever<User>, ODataRetriever<User>>
		_serviceTracker;

	@DeleteAfterTestRun
	private final List<ExpandoColumn> _expandoColumns = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<ExpandoTable> _expandoTables = new ArrayList<>();

	@Inject(filter = "model.class.name=com.liferay.portal.kernel.model.User")
	private ODataRetriever<User> _oDataRetriever;

	@Inject
	private UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}