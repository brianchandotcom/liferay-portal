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

package com.liferay.portal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.util.DLSearcher;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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
 * @author Bryan Engler
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class ExpandoSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_indexer = _indexerRegistry.getIndexer(User.class);

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
	}

	@After
	public void tearDown() throws Exception {
		for (FileEntry fileEntry : _fileEntries) {
			DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
		}

		_fileEntries.clear();

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testIndexerWithMultipleSearchClassNames() throws Exception {
		String columnName = "searchClassNamesColumn";

		addExpandoColumn(
			DLFileEntry.class, columnName,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		String columnValue = "Software Engineer";

		addDLFileEntry(columnName, columnValue);

		Indexer<?> indexer = DLSearcher.getInstance();

		Assert.assertEquals(
			_toString(
				Arrays.asList(
					DLFileEntry.class.getName(), DLFolder.class.getName())),
			_toString(Arrays.asList(indexer.getSearchClassNames())));

		assertSearch(indexer, columnValue, columnValue);
	}

	@Test
	public void testKeyword() throws Exception {
		String columnName = "keywordColumn";

		addExpandoColumn(
			User.class, columnName, ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		String columnValue = "Software Engineer";

		addUser(columnName, columnValue);

		addUser(columnName, RandomTestUtil.randomString());

		addUser();

		assertSearch("Software Engineer", columnValue);
		assertSearch("\"Software Engineer\"", columnValue);
		assertSearch("software engineer", columnValue);
		assertSearch("\"software engineer\"", columnValue);
		assertSearch("Software", columnValue);
		assertSearch("\"Software\"", columnValue);
		assertSearch("software", columnValue);
		assertSearch("\"software\"", columnValue);
		assertSearch("Engineer", columnValue);
		assertSearch("\"Engineer\"", columnValue);
		assertSearch("engineer", columnValue);
		assertSearch("\"engineer\"", columnValue);
		assertSearch("oftware Engineer", columnValue);
		assertSearch("\"oftware Engineer\"", columnValue);
		assertSearch("oftware engineer", columnValue);
		assertSearch("\"oftware engineer\"", columnValue);
		assertSearch("Software Enginee", columnValue);
		assertSearch("\"Software Enginee\"", columnValue);
		assertSearch("software Enginee", columnValue);
		assertSearch("\"software Enginee\"", columnValue);
		assertSearch("Software enginee", columnValue);
		assertSearch("\"Software enginee\"", columnValue);
		assertSearch("software enginee", columnValue);
		assertSearch("\"software enginee\"", columnValue);
		assertSearch("oftware", columnValue);
		assertSearch("\"oftware\"", columnValue);
		assertSearch("ngineer", columnValue);
		assertSearch("\"ngineer\"", columnValue);
	}

	@Test
	public void testKeywordCaseInsensitive() throws Exception {
		String columnName = "keywordColumn";

		addExpandoColumn(
			User.class, columnName, ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		String[] columnValues = {
			"Software", "SoftWare", "softWare", "software"
		};

		for (String columnValue : columnValues) {
			addUser(columnName, columnValue);
		}

		addUser(columnName, RandomTestUtil.randomString());
		addUser();

		assertSearch("Software", columnValues);
		assertSearch("\"Software\"", columnValues);
		assertSearch("SoftWare", columnValues);
		assertSearch("\"SoftWare\"", columnValues);
		assertSearch("softWare", columnValues);
		assertSearch("\"softWare\"", columnValues);
		assertSearch("software", columnValues);
		assertSearch("\"software\"", columnValues);
		assertSearch("oftware ", columnValues);
		assertSearch("\"oftware\"", columnValues);
		assertSearch("oftWare ", columnValues);
		assertSearch("\"oftWare\"", columnValues);
	}

	@Test
	public void testNotSearchable() throws Exception {
		String columnName = "notSearchableColumn";

		addExpandoColumn(
			User.class, columnName, ExpandoColumnConstants.INDEX_TYPE_NONE);

		String columnValue = "Software Engineer";

		addUser(columnName, columnValue);

		addUser(columnName, RandomTestUtil.randomString());

		addUser();

		assertNoHits("Software Engineer");
		assertNoHits("\"Software Engineer\"");
		assertNoHits("software engineer");
		assertNoHits("\"software engineer\"");
		assertNoHits("Software");
		assertNoHits("\"Software\"");
		assertNoHits("software");
		assertNoHits("\"software\"");
		assertNoHits("Engineer");
		assertNoHits("\"Engineer\"");
		assertNoHits("engineer");
		assertNoHits("\"engineer\"");
		assertNoHits("oftware Engineer");
		assertNoHits("\"oftware Engineer\"");
		assertNoHits("oftware engineer");
		assertNoHits("\"oftware engineer\"");
		assertNoHits("Software Enginee");
		assertNoHits("\"Software Enginee\"");
		assertNoHits("software Enginee");
		assertNoHits("\"software Enginee\"");
		assertNoHits("Software enginee");
		assertNoHits("\"Software enginee\"");
		assertNoHits("software enginee");
		assertNoHits("\"software enginee\"");
		assertNoHits("oftware");
		assertNoHits("\"oftware\"");
		assertNoHits("ngineer");
		assertNoHits("\"ngineer\"");
	}

	@Test
	public void testText() throws Exception {
		String columnName = "textColumn";

		addExpandoColumn(
			User.class, columnName, ExpandoColumnConstants.INDEX_TYPE_TEXT);

		String columnValue = "Software Engineer";

		addUser(columnName, columnValue);

		addUser(columnName, RandomTestUtil.randomString());

		addUser();

		assertSearch("Software Engineer", columnValue);
		assertSearch("\"Software Engineer\"", columnValue);
		assertSearch("software engineer", columnValue);
		assertSearch("\"software engineer\"", columnValue);
		assertSearch("Software", columnValue);
		assertSearch("\"Software\"", columnValue);
		assertSearch("software", columnValue);
		assertSearch("\"software\"", columnValue);
		assertSearch("Engineer", columnValue);
		assertSearch("\"Engineer\"", columnValue);
		assertSearch("engineer", columnValue);
		assertSearch("\"engineer\"", columnValue);
		assertSearch("oftware Engineer", columnValue);
		assertSearch("oftware engineer", columnValue);
		assertSearch("Software Enginee", columnValue);
		assertSearch("software Enginee", columnValue);
		assertSearch("Software enginee", columnValue);
		assertSearch("software enginee", columnValue);

		assertNoHits("\"oftware Engineer\"");
		assertNoHits("\"oftware engineer\"");
		assertNoHits("\"Software Enginee\"");
		assertNoHits("\"software Enginee\"");
		assertNoHits("\"Software enginee\"");
		assertNoHits("\"software enginee\"");
		assertNoHits("oftware");
		assertNoHits("\"oftware\"");
		assertNoHits("ngineer");
		assertNoHits("\"ngineer\"");
	}

	protected FileEntry addDLFileEntry(String columnName, String columnValue)
		throws Exception {

		ServiceContext serviceContext = getServiceContext(
			columnName, columnValue);

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(), 0,
			StringPool.BLANK, RandomTestUtil.randomString(), true,
			serviceContext);

		_fileEntries.add(fileEntry);

		return fileEntry;
	}

	protected void addExpandoColumn(
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

	protected User addUser() throws Exception {
		return addUser(ServiceContextTestUtil.getServiceContext());
	}

	protected User addUser(ServiceContext serviceContext) throws Exception {
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

	protected User addUser(String columnName, String columnValue)
		throws Exception {

		ServiceContext serviceContext = getServiceContext(
			columnName, columnValue);

		return addUser(serviceContext);
	}

	protected void assertNoHits(String keywords) throws Exception {
		assertSearch(keywords);
	}

	protected void assertSearch(
			Indexer<?> indexer, String keywords, String... expectedColumnValues)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(Field.ANY);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(
			hits.toString(), _toString(Arrays.asList(expectedColumnValues)),
			_toString(getExpandoColumnValues(hits)));
	}

	protected void assertSearch(String keywords, String... expectedColumnValues)
		throws Exception {

		assertSearch(_indexer, keywords, expectedColumnValues);
	}

	protected String getExpandoColumnValue(Document document) {
		Map<String, Field> fields = document.getFields();

		for (Field field : fields.values()) {
			if (StringUtil.startsWith(field.getName(), "expando")) {
				return field.getValue();
			}
		}

		return null;
	}

	protected List<String> getExpandoColumnValues(Hits hits) {
		List<Document> documents = hits.toList();

		List<String> values = new ArrayList<>(documents.size());

		for (Document document : documents) {
			values.add(getExpandoColumnValue(document));
		}

		return values;
	}

	protected ServiceContext getServiceContext(
			String columnName, String columnValue)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		Map<String, Serializable> expandoBridgeAttributes = new HashMap<>();

		expandoBridgeAttributes.put(columnName, columnValue);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		return serviceContext;
	}

	private static String _toString(List<String> list) {
		List<String> sorted = new ArrayList<>(list);

		Collections.sort(sorted);

		return sorted.toString();
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private static ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@Inject
	private static UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private final List<ExpandoColumn> _expandoColumns = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<ExpandoTable> _expandoTables = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<FileEntry> _fileEntries = new ArrayList<>();

	private Indexer<User> _indexer;
	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}