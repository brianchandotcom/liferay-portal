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

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.normalizer.Normalizer;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
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
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_expandoTable = ExpandoTestUtil.addTable(
			PortalUtil.getClassNameId(User.class), "CUSTOM_FIELDS");

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			StringBundler.concat(
				"(&(model.class.name=com.liferay.portal.kernel.model.User)",
				"(objectClass=", ODataRetriever.class.getName(), "))"));

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@After
	public void tearDown() {
		_serviceTracker.close();
	}

	@Test
	public void testGetUsersFilterByCustomFieldWithEqualsAndBooleanKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.BOOLEAN,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		Boolean columnValue = Boolean.TRUE;

		User user1 = _addUser(expandoColumn.getName(), columnValue);

		_users.add(user1);

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			String.valueOf(columnValue));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByCustomFieldWithEqualsAndBooleanTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.BOOLEAN,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		Boolean columnValue = Boolean.TRUE;

		User user1 = _addUser(expandoColumn.getName(), columnValue);

		_users.add(user1);

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			String.valueOf(columnValue));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByCustomFieldWithEqualsAndDateKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.DATE,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		Date columnValue = new Date();

		User user1 = _addUser(expandoColumn.getName(), columnValue);

		_users.add(user1);

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			ISO8601Utils.format(columnValue));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByCustomFieldWithEqualsAndDateTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.DATE,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		Date columnValue = new Date();

		User user1 = _addUser(expandoColumn.getName(), columnValue);

		_users.add(user1);

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			ISO8601Utils.format(columnValue));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByCustomFieldWithEqualsAndLocalizedStringKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING_LOCALIZED,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		Locale esLocale = LocaleUtil.fromLanguageId("es_ES");

		Map<Locale, String> columnValueMap = HashMapBuilder.put(
			esLocale, RandomTestUtil.randomString()
		).put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		Serializable columnValue = (Serializable)columnValueMap;

		User user1 = _addUser(expandoColumn.getName(), columnValue);

		_users.add(user1);

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		String filterString = String.format(
			"(customField/%s eq '%s')", _encodeName(expandoColumn),
			columnValueMap.get(esLocale));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString, esLocale);

		Assert.assertEquals(1, count);

		List<User> users = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString, esLocale, 0, 1);

		Assert.assertEquals(user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByCustomFieldWithEqualsAndLocalizedStringTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING_LOCALIZED,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		Locale esLocale = LocaleUtil.fromLanguageId("es_ES");

		Map<Locale, String> columnValueMap = HashMapBuilder.put(
			esLocale, "Hola Mundo!"
		).put(
			LocaleUtil.getDefault(), "Hello World!"
		).build();

		Serializable columnValue = (Serializable)columnValueMap;

		User user1 = _addUser(expandoColumn.getName(), columnValue);

		_users.add(user1);

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		String filterString = String.format(
			"(customField/%s eq '%s')", _encodeName(expandoColumn),
			columnValueMap.get(esLocale));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString, esLocale);

		Assert.assertEquals(0, count);
	}

	@Test
	public void testGetUsersFilterByCustomFieldWithEqualsAndStringKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		String columnValue = RandomTestUtil.randomString();

		User user1 = _addUser(expandoColumn.getName(), columnValue);

		_users.add(user1);

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		String filterString = StringBundler.concat(
			"(customField/", _encodeName(expandoColumn), " eq '", columnValue,
			"')");

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByCustomFieldWithEqualsAndStringTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		String columnValue = "Hello World!";

		User user1 = _addUser(expandoColumn.getName(), columnValue);

		_users.add(user1);

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		String filterString = StringBundler.concat(
			"(customField/", _encodeName(expandoColumn), " eq '", columnValue,
			"')");

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(0, count);
	}

	private ExpandoColumn _addExpandoColumn(
			ExpandoTable expandoTable, String columnName, int columnType,
			int indexType)
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			expandoTable, columnName, columnType);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	private User _addUser(String columnName, Serializable columnValue)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		Map<String, Serializable> expandoBridgeAttributes =
			HashMapBuilder.<String, Serializable>put(
				columnName, columnValue
			).build();

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		return UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			serviceContext);
	}

	private String _encodeName(ExpandoColumn expandoColumn) {
		return StringBundler.concat(
			StringPool.UNDERLINE, expandoColumn.getColumnId(),
			StringPool.UNDERLINE,
			Normalizer.normalizeIdentifier(expandoColumn.getName()));
	}

	private ODataRetriever<User> _getODataRetriever() {
		return _serviceTracker.getService();
	}

	@Inject
	private static ExpandoColumnLocalService _expandoColumnLocalService;

	private static ServiceTracker<ODataRetriever<User>, ODataRetriever<User>>
		_serviceTracker;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}