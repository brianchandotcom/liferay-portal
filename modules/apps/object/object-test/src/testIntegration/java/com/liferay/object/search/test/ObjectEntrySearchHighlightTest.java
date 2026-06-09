/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectEntrySearchConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.rule.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class ObjectEntrySearchHighlightTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_localizedObjectDefinition = _addObjectDefinition(true);

		_localizedObjectEntry = _addObjectEntry(
			true, _localizedObjectDefinition);

		_nonlocalizedObjectDefinition = _addObjectDefinition(false);

		_nonlocalizedObjectEntry = _addObjectEntry(
			false, _nonlocalizedObjectDefinition);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_localizedObjectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_localizedObjectDefinition);
		}

		if (_nonlocalizedObjectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_nonlocalizedObjectDefinition);
		}
	}

	@Test
	public void testDefaultLocaleFallback() throws Exception {
		SearchHit searchHit = _search(
			LocaleUtil.HUNGARY, _localizedObjectDefinition,
			_localizedObjectEntry);

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			_localizedObjectDefinition.getDefaultLanguageId());

		_assertHighlight(_getContentFieldName(defaultLocale), searchHit);
		_assertHighlight(_getTitleFieldName(defaultLocale), searchHit);
	}

	@Test
	public void testLocalizedContent() throws Exception {
		SearchHit searchHit = _search(
			LocaleUtil.US, _localizedObjectDefinition, _localizedObjectEntry);

		_assertHighlight(_getContentFieldName(LocaleUtil.US), searchHit);

		searchHit = _search(
			LocaleUtil.SPAIN, _localizedObjectDefinition,
			_localizedObjectEntry);

		_assertHighlight(_getContentFieldName(LocaleUtil.SPAIN), searchHit);
	}

	@Test
	public void testLocalizedTitle() throws Exception {
		SearchHit searchHit = _search(
			LocaleUtil.US, _localizedObjectDefinition, _localizedObjectEntry);

		_assertHighlight(_getTitleFieldName(LocaleUtil.US), searchHit);

		searchHit = _search(
			LocaleUtil.SPAIN, _localizedObjectDefinition,
			_localizedObjectEntry);

		_assertHighlight(_getTitleFieldName(LocaleUtil.SPAIN), searchHit);
	}

	@Test
	public void testNonlocalizedContent() throws Exception {
		SearchHit searchHit = _search(
			LocaleUtil.US, _nonlocalizedObjectDefinition,
			_nonlocalizedObjectEntry);

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			_nonlocalizedObjectDefinition.getDefaultLanguageId());

		_assertHighlight(_getContentFieldName(defaultLocale), searchHit);
	}

	@Test
	public void testNonlocalizedTitle() throws Exception {
		SearchHit searchHit = _search(
			LocaleUtil.US, _nonlocalizedObjectDefinition,
			_nonlocalizedObjectEntry);

		_assertHighlight(
			ObjectEntrySearchConstants.OBJECT_ENTRY_TITLE, searchHit);
		_assertNoLocalizedHighlight(
			ObjectEntrySearchConstants.OBJECT_ENTRY_TITLE, searchHit);

		searchHit = _search(
			LocaleUtil.SPAIN, _nonlocalizedObjectDefinition,
			_nonlocalizedObjectEntry);

		_assertHighlight(
			ObjectEntrySearchConstants.OBJECT_ENTRY_TITLE, searchHit);
		_assertNoLocalizedHighlight(
			ObjectEntrySearchConstants.OBJECT_ENTRY_TITLE, searchHit);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Inject
	protected Searcher searcher;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private static ObjectDefinition _addObjectDefinition(boolean localized)
		throws Exception {

		String contentFieldLabel = RandomTestUtil.randomString();
		String contentFieldName = _NONLOCALIZED_CONTENT_FIELD_NAME;
		String titleFieldLabel = RandomTestUtil.randomString();
		String titleFieldName = _NONLOCALIZED_TITLE_FIELD_NAME;

		if (localized) {
			contentFieldName = _LOCALIZED_CONTENT_FIELD_NAME;
			titleFieldName = _LOCALIZED_TITLE_FIELD_NAME;
		}

		List<ObjectField> objectFields = new ArrayList<>();

		objectFields.add(
			_buildTextObjectField(
				contentFieldLabel, localized, contentFieldName));

		objectFields.add(
			_buildTextObjectField(titleFieldLabel, localized, titleFieldName));

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(objectFields);

		ObjectField titleObjectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(), titleFieldName);

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			objectDefinition.getObjectDefinitionId(),
			titleObjectField.getObjectFieldId());

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private static ObjectEntry _addObjectEntry(
			boolean localized, ObjectDefinition objectDefinition)
		throws Exception {

		Map<String, Serializable> values = null;

		if (localized) {
			values = HashMapBuilder.<String, Serializable>put(
				_LOCALIZED_CONTENT_FIELD_NAME + "_i18n",
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.US),
					RandomTestUtil.randomString() + " " + _KEYWORD
				).put(
					LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
					RandomTestUtil.randomString() + " " + _KEYWORD
				).build()
			).put(
				_LOCALIZED_TITLE_FIELD_NAME + "_i18n",
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.US),
					RandomTestUtil.randomString() + " " + _KEYWORD
				).put(
					LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
					RandomTestUtil.randomString() + " " + _KEYWORD
				).build()
			).build();
		}
		else {
			values = HashMapBuilder.<String, Serializable>put(
				_NONLOCALIZED_CONTENT_FIELD_NAME,
				RandomTestUtil.randomString() + " " + _KEYWORD
			).put(
				_NONLOCALIZED_TITLE_FIELD_NAME,
				RandomTestUtil.randomString() + " " + _KEYWORD
			).build();
		}

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null, values, ServiceContextTestUtil.getServiceContext());
	}

	private static ObjectField _buildTextObjectField(
		String label, boolean localized, String name) {

		return new TextObjectFieldBuilder(
		).indexed(
			true
		).indexedAsKeyword(
			false
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(label)
		).localized(
			localized
		).name(
			name
		).build();
	}

	private void _assertHighlight(
		String highlightFieldName, SearchHit searchHit) {

		Map<String, HighlightField> highlightFieldsMap =
			searchHit.getHighlightFieldsMap();

		HighlightField highlightField = highlightFieldsMap.get(
			highlightFieldName);

		List<String> fragments = highlightField.getFragments();

		Assert.assertFalse(
			"Highlight fragments missing for " + highlightFieldName,
			fragments.isEmpty());

		String highlightedResult = StringBundler.concat(
			HighlightUtil.HIGHLIGHT_TAG_OPEN, _KEYWORD,
			HighlightUtil.HIGHLIGHT_TAG_CLOSE);

		for (String fragment : fragments) {
			Assert.assertTrue(
				"Missing highlight markup in fragment: " + fragment,
				fragment.contains(highlightedResult));
		}
	}

	private void _assertNoLocalizedHighlight(
		String fieldName, SearchHit searchHit) {

		Map<String, HighlightField> highlightFieldsMap =
			searchHit.getHighlightFieldsMap();

		Locale[] locales = {LocaleUtil.US, LocaleUtil.SPAIN};

		for (Locale locale : locales) {
			String localizedFieldName =
				fieldName + "_" + LocaleUtil.toLanguageId(locale);

			Assert.assertFalse(
				"Unexpected localized highlight " + localizedFieldName,
				highlightFieldsMap.containsKey(localizedFieldName));
		}
	}

	private String _getContentFieldName(Locale locale) {
		return Field.getLocalizedName(
			locale, ObjectEntrySearchConstants.NESTED_FIELD_ARRAY_VALUE);
	}

	private String _getTitleFieldName(Locale locale) {
		return Field.getLocalizedName(
			locale, ObjectEntrySearchConstants.OBJECT_ENTRY_TITLE);
	}

	private SearchHit _search(
			Locale locale, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry)
		throws Exception {

		SearchResponse searchResponse = searcher.search(
			searchRequestBuilderFactory.builder(
			).companyId(
				TestPropsValues.getCompanyId()
			).entryClassNames(
				objectDefinition.getClassName()
			).highlightEnabled(
				true
			).locale(
				locale
			).queryString(
				_KEYWORD
			).size(
				1
			).build());

		SearchHits searchHits = searchResponse.getSearchHits();

		List<SearchHit> searchHitList = searchHits.getSearchHits();

		SearchHit searchHit = searchHitList.get(0);

		Document document = searchHit.getDocument();

		Assert.assertEquals(
			String.valueOf(objectEntry.getObjectEntryId()),
			document.getString(Field.ENTRY_CLASS_PK));

		return searchHit;
	}

	private static final String _KEYWORD = RandomTestUtil.randomString();

	private static final String _LOCALIZED_CONTENT_FIELD_NAME = "localizedText";

	private static final String _LOCALIZED_TITLE_FIELD_NAME = "localizedTitle";

	private static final String _NONLOCALIZED_CONTENT_FIELD_NAME =
		"nonlocalizedText";

	private static final String _NONLOCALIZED_TITLE_FIELD_NAME = "title";

	private static ObjectDefinition _localizedObjectDefinition;
	private static ObjectEntry _localizedObjectEntry;
	private static ObjectDefinition _nonlocalizedObjectDefinition;
	private static ObjectEntry _nonlocalizedObjectEntry;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private static ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private static ObjectFieldLocalService _objectFieldLocalService;

}