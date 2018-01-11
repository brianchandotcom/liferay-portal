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

package com.liferay.document.library.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.util.DLFileEntryIndexer;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dylan Rebelak
 */
@RunWith(Arquillian.class)
public class DLFileEntryIndexerLocalizedContentTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		_indexer = new DLFileEntryIndexer();
	}

	@Test
	public void testJapaneseContent() throws Exception {
		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, LocaleUtil.JAPAN);

		addFileEntry("content_search.txt");

		List<String> contentStrings = new ArrayList<>(
			Collections.singletonList("content_ja_JP"));

		String word1 = "新規";
		String word2 = "作成";
		String prefix1 = "新";
		String prefix2 = "作";

		Stream<String> searchTerms = Stream.of(word1, word2, prefix1, prefix2);

		searchTerms.forEach(
			searchTerm -> {
				Document document = _search(searchTerm, LocaleUtil.JAPAN);

				assertLocalization(contentStrings, document);
			});
	}

	@Test
	public void testJapaneseContentFullWordOnly() throws Exception {
		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, LocaleUtil.JAPAN);

		addFileEntry("japanese_1.txt");
		addFileEntry("japanese_2.txt");
		addFileEntry("japanese_3.txt");

		List<String> contentStrings = new ArrayList<>(
			Collections.singletonList("content_ja_JP"));

		String word1 = "新規";
		String word2 = "作成";

		Stream<String> searchTerms = Stream.of(word1, word2);

		searchTerms.forEach(
			searchTerm -> {
				Document document = _search(searchTerm, LocaleUtil.JAPAN);

				assertLocalization(contentStrings, document);
			});
	}

	@Test
	public void testSiteLocale() throws Exception {
		Group testGroup = GroupTestUtil.addGroup();

		List<String> japaneseContentStrings = new ArrayList<>(
			Collections.singletonList("content_ja_JP"));
		List<String> englishContentStrings = new ArrayList<>(
			Collections.singletonList("content_en_US"));

		try {
			GroupTestUtil.updateDisplaySettings(
				_group.getGroupId(), null, LocaleUtil.JAPAN);
			GroupTestUtil.updateDisplaySettings(
				testGroup.getGroupId(), null, LocaleUtil.US);

			addFileEntry("locale_ja.txt", _group.getGroupId());
			addFileEntry("locale_en.txt", testGroup.getGroupId());

			Document japenseDocument = _search(
				"新規", LocaleUtil.JAPAN, _group.getGroupId());

			assertLocalization(japaneseContentStrings, japenseDocument);

			Document englishDocument = _search(
				"Locale Test", LocaleUtil.ENGLISH, testGroup.getGroupId());

			assertLocalization(englishContentStrings, englishDocument);
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(testGroup);
		}
	}

	protected FileEntry addFileEntry(String fileName) throws Exception {
		return addFileEntry(fileName, _group.getGroupId());
	}

	protected FileEntry addFileEntry(String fileName, long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		File file = null;
		FileEntry fileEntry = null;

		try (InputStream inputStream =
				DLFileEntrySearchTest.class.getResourceAsStream(
					"dependencies/" + fileName)) {

			String mimeType = MimeTypesUtil.getContentType(file, fileName);

			file = FileUtil.createTempFile(inputStream);

			fileEntry = DLAppLocalServiceUtil.addFileEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName, mimeType,
				fileName, StringPool.BLANK, StringPool.BLANK, file,
				serviceContext);
		}
		finally {
			FileUtil.delete(file);
		}

		return fileEntry;
	}

	protected void assertLocalization(
		List<String> contentStrings, Document document) {

		List<String> fields = _getFieldValues("content", document);

		Assert.assertEquals(contentStrings.toString(), fields.toString());
	}

	private static List<String> _getFieldValues(
		String prefix, Document document) {

		List<String> filteredFields = new ArrayList<>();

		Map<String, Field> fields = document.getFields();

		for (String field : fields.keySet()) {
			if (field.contains(prefix)) {
				filteredFields.add(field);
			}
		}

		return filteredFields;
	}

	private SearchContext _getSearchContext(
			String searchTerm, Locale locale, long groupId)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			groupId);

		searchContext.setKeywords(searchTerm);
		searchContext.setLocale(locale);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(StringPool.STAR);

		return searchContext;
	}

	private Document _getSingleDocument(String searchTerm, Hits hits) {
		List<Document> documents = hits.toList();

		if (documents.size() == 1) {
			return documents.get(0);
		}

		throw new AssertionError(searchTerm + "->" + documents);
	}

	private Document _search(String searchTerm, Locale locale) {
		return _search(searchTerm, locale, _group.getGroupId());
	}

	private Document _search(String searchTerm, Locale locale, long groupId) {
		try {
			SearchContext searchContext = _getSearchContext(
				searchTerm, locale, groupId);

			Hits hits = _indexer.search(searchContext);

			return _getSingleDocument(searchTerm, hits);
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<?> _indexer;

}