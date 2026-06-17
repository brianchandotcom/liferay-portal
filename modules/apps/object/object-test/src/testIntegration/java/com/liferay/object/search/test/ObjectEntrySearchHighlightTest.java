/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
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
	public void testHighlightDefaultLocaleFallback() throws Exception {
		SearchHit searchHit = _search(
			_localizedObjectDefinition, LocaleUtil.HUNGARY);

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			_localizedObjectDefinition.getDefaultLanguageId());

		_assertHighlight(searchHit, _getContentFieldName(defaultLocale));
		_assertHighlight(searchHit, _getTitleFieldName(defaultLocale));
	}

	@Test
	public void testHighlightLocalized() throws Exception {
		for (Locale locale : new Locale[] {LocaleUtil.SPAIN, LocaleUtil.US}) {
			SearchHit searchHit = _search(_localizedObjectDefinition, locale);

			_assertHighlight(searchHit, _getContentFieldName(locale));
			_assertHighlight(searchHit, _getTitleFieldName(locale));
		}
	}

	@Test
	public void testHighlightNonlocalized() throws Exception {
		Locale defaultLocale = LocaleUtil.fromLanguageId(
			_nonlocalizedObjectDefinition.getDefaultLanguageId());

		for (Locale locale : new Locale[] {LocaleUtil.SPAIN, LocaleUtil.US}) {
			SearchHit searchHit = _search(
				_nonlocalizedObjectDefinition, locale);

			_assertHighlight(searchHit, _getContentFieldName(defaultLocale));

			_assertHighlight(searchHit, "objectEntryTitle");

			_assertNoHighlight(searchHit, _getTitleFieldName(LocaleUtil.SPAIN));
			_assertNoHighlight(searchHit, _getTitleFieldName(LocaleUtil.US));
		}
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Inject
	protected Searcher searcher;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private static ObjectDefinition _addObjectDefinition(boolean localized)
		throws Exception {

		List<ObjectField> objectFields = new ArrayList<>();

		objectFields.add(
			_buildTextObjectField(localized, _CONTENT_OBJECT_FIELD_NAME));
		objectFields.add(
			_buildTextObjectField(localized, _TITLE_OBJECT_FIELD_NAME));

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(objectFields);

		ObjectField titleObjectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(), _TITLE_OBJECT_FIELD_NAME);

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
				_CONTENT_OBJECT_FIELD_NAME + "_i18n",
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
					"Spanish content " + _KEYWORD
				).put(
					LocaleUtil.toLanguageId(LocaleUtil.US),
					"English content " + _KEYWORD
				).build()
			).put(
				_TITLE_OBJECT_FIELD_NAME + "_i18n",
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
					"Spanish title " + _KEYWORD
				).put(
					LocaleUtil.toLanguageId(LocaleUtil.US),
					"English title " + _KEYWORD
				).build()
			).build();
		}
		else {
			values = HashMapBuilder.<String, Serializable>put(
				_CONTENT_OBJECT_FIELD_NAME, "Nonlocalized content " + _KEYWORD
			).put(
				_TITLE_OBJECT_FIELD_NAME, "Nonlocalized title " + _KEYWORD
			).build();
		}

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null, values, ServiceContextTestUtil.getServiceContext());
	}

	private static ObjectField _buildTextObjectField(
		boolean localized, String name) {

		return new TextObjectFieldBuilder(
		).indexed(
			true
		).indexedAsKeyword(
			false
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).localized(
			localized
		).name(
			name
		).build();
	}

	private void _assertHighlight(
		SearchHit searchHit, String highlightFieldName) {

		HighlightField highlightField = searchHit.getHighlightFieldsMap(
		).get(
			highlightFieldName
		);

		Assert.assertTrue(_hasHighlight(highlightField.getFragments()));
	}

	private void _assertNoHighlight(
		SearchHit searchHit, String highlightFieldName) {

		Map<String, HighlightField> highlightFieldsMap =
			searchHit.getHighlightFieldsMap();

		Assert.assertFalse(highlightFieldsMap.containsKey(highlightFieldName));
	}

	private String _getContentFieldName(Locale locale) {
		return Field.getLocalizedName(locale, "nestedFieldArray.value");
	}

	private String _getTitleFieldName(Locale locale) {
		return Field.getLocalizedName(locale, "objectEntryTitle");
	}

	private boolean _hasHighlight(List<String> fragments) {
		for (String fragment : fragments) {
			if (fragment.contains(_HIGHLIGHTED_KEYWORD)) {
				return true;
			}
		}

		return false;
	}

	private SearchHit _search(ObjectDefinition objectDefinition, Locale locale)
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

		return searchHitList.get(0);
	}

	private static final String _CONTENT_OBJECT_FIELD_NAME =
		"a" + RandomTestUtil.randomString();

	private static final String _HIGHLIGHTED_KEYWORD =
		HighlightUtil.HIGHLIGHT_TAG_OPEN +
			ObjectEntrySearchHighlightTest._KEYWORD +
				HighlightUtil.HIGHLIGHT_TAG_CLOSE;

	private static final String _KEYWORD = "alpha";

	private static final String _TITLE_OBJECT_FIELD_NAME =
		"a" + RandomTestUtil.randomString();

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