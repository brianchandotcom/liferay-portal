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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.field.FieldQueryFactory;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseLiferayFieldQueryFactoryTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testContentField() {
		assertSearch(
			"content", getEnglishBlueprint(),
			getDescriptionStyleExpectations());
	}

	@Test
	public void testContentFieldEnglish() {
		assertSearch(
			"content_en_US", getEnglishBlueprint(),
			getDescriptionStyleExpectations());
	}

	@Test
	public void testContentFieldJapanese() {
		assertSearch(
			"content_ja_JP", getJapaneseBlueprint(),
			getDescriptionStyleExpectations());
	}

	@Test
	public void testContentFieldPortuguese() {
		assertSearch(
			"content_pt_BR", getPortugueseBlueprint(),
			new Expectations() {
				{
					phrasePrefix = true;
					word1 = true;
					word2 = true;
					wordPrefix1 = true;
					wordPrefix2 = false;
				}
			});
	}

	@Test
	public void testDescriptionField() {
		assertSearch(
			"description", getEnglishBlueprint(),
			getDescriptionStyleExpectations());
	}

	@Test
	public void testDescriptionFieldEnglish() {
		assertSearch(
			"description_en_US", getEnglishBlueprint(),
			getDescriptionStyleExpectations());
	}

	@Test
	public void testDescriptionFieldJapanese() {
		assertSearch(
			"description_ja_JP", getJapaneseBlueprint(),
			getDescriptionStyleExpectations());
	}

	@Test
	public void testDescriptionFieldPortuguese() {
		assertSearch(
			"description_pt_BR", getPortugueseBlueprint(),
			new Expectations() {
				{
					phrasePrefix = true;
					word1 = true;
					word2 = true;
					wordPrefix1 = true;
					wordPrefix2 = false;
				}
			});
	}

	@Test
	public void testKeywordMappedField() {
		assertSearch(
			"properties", getEnglishBlueprint(),
			new Expectations() {
				{
					phrasePrefix = false;
					word1 = false;
					word2 = false;
					wordPrefix1 = false;
					wordPrefix2 = false;
				}
			});
	}

	@Test
	public void testKeywordMappedFieldEnglish() {
		assertSearch(
			"properties_en_US", getEnglishBlueprint(),
			getDescriptionStyleExpectations());
	}

	@Test
	public void testKeywordMappedFieldJapanese() {
		assertSearch(
			"properties_ja_JP", getJapaneseBlueprint(),
			getDescriptionStyleExpectations());
	}

	@Test
	public void testKeywordMappedFieldPortuguese() {
		assertSearch(
			"properties_pt_BR", getPortugueseBlueprint(),
			new Expectations() {
				{
					phrasePrefix = true;
					word1 = true;
					word2 = true;
					wordPrefix1 = true;
					wordPrefix2 = false;
				}
			});
	}

	@Test
	public void testNameField() {
		assertSearch(
			"name", getEnglishBlueprint(), getTitleStyleExpectations());
	}

	@Test
	public void testNameFieldEnglish() {
		assertSearch(
			"name_en_US", getEnglishBlueprint(), getTitleStyleExpectations());
	}

	@Test
	public void testNameFieldJapanese() {
		assertSearch(
			"name_ja_JP", getJapaneseBlueprint(), getTitleStyleExpectations());
	}

	@Test
	public void testNameFieldPortuguese() {
		assertSearch(
			"name_pt_BR", getPortugueseBlueprint(),
			getTitleStyleExpectations());
	}

	@Test
	public void testTitleField() {
		assertSearch(
			"title", getEnglishBlueprint(), getTitleStyleExpectations());
	}

	@Test
	public void testTitleFieldEnglish() {
		assertSearch(
			"title_en_US", getEnglishBlueprint(), getTitleStyleExpectations());
	}

	@Test
	public void testTitleFieldJapanese() {
		assertSearch(
			"title_ja_JP", getJapaneseBlueprint(), getTitleStyleExpectations());
	}

	@Test
	public void testTitleFieldPortuguese() {
		assertSearch(
			"title_pt_BR", getPortugueseBlueprint(),
			getTitleStyleExpectations());
	}

	protected static Expectations getDescriptionStyleExpectations() {
		return new Expectations() {
			{
				phrasePrefix = true;
				word1 = true;
				word2 = true;
				wordPrefix1 = false;
				wordPrefix2 = false;
			}
		};
	}

	protected static Blueprint getEnglishBlueprint() {
		return new Blueprint() {
			{
				phrase = "alpha bravo";
				phrasePrefix = "alpha brav";
				word1 = "alpha";
				word2 = "bravo";
				wordPrefix1 = "alph";
				wordPrefix2 = "brav";
			}
		};
	}

	protected static Blueprint getJapaneseBlueprint() {
		return new Blueprint() {
			{
				phrase = "新規作成";
				phrasePrefix = "新規作";
				word1 = "新規";
				word2 = "作成";
				wordPrefix1 = "新";
				wordPrefix2 = "作";
			}
		};
	}

	protected static Blueprint getPortugueseBlueprint() {
		return new Blueprint() {
			{
				phrase = "João Ninguém";
				phrasePrefix = "João Ningué";
				word1 = "João";
				word2 = "Ninguém";
				wordPrefix1 = "Joã";
				wordPrefix2 = "Ningué";
			}
		};
	}

	protected static Expectations getTitleStyleExpectations() {
		return new Expectations() {
			{
				phrasePrefix = true;
				word1 = true;
				word2 = true;
				wordPrefix1 = true;
				wordPrefix2 = true;
			}
		};
	}

	protected void assertSearch(
		String field, Blueprint blueprint, Expectations expectations) {

		setFieldName(field);
		setFieldValue(blueprint.phrase);

		indexOneDocument();

		assertSearch(blueprint.phrase, true);
		assertSearch(blueprint.word1, expectations.word1);
		assertSearch(blueprint.word2, expectations.word2);
		assertSearch(blueprint.wordPrefix1, expectations.wordPrefix1);
		assertSearch(blueprint.wordPrefix2, expectations.wordPrefix2);
		assertSearch(blueprint.phrasePrefix, expectations.phrasePrefix);
	}

	protected void assertSearch(String value, boolean hit) {
		FieldQueryFactory fieldQueryFactory =
			_liferayFieldQueryFactoryFixture.getFieldQueryFactory();

		Query query = fieldQueryFactory.createQuery(
			_fieldName, value, false, false);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.query(query));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> DocumentsAssert.assertValues(
						searchResponse.getRequestString(),
						searchResponse.getDocumentsStream(), _fieldName,
						StringPool.OPEN_BRACKET + getExpected(hit) +
							StringPool.CLOSE_BRACKET));
			});
	}

	protected String getExpected(boolean hit) {
		if (hit) {
			return _fieldValue;
		}

		return StringPool.BLANK;
	}

	protected void indexOneDocument() {
		addDocument(
			DocumentCreationHelpers.singleText(_fieldName, _fieldValue));
	}

	protected void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	protected void setFieldValue(String fieldValue) {
		_fieldValue = fieldValue;
	}

	protected static class Blueprint {

		protected String phrase;
		protected String phrasePrefix;
		protected String word1;
		protected String word2;
		protected String wordPrefix1;
		protected String wordPrefix2;

	}

	protected static class Expectations {

		protected boolean phrasePrefix;
		protected boolean word1;
		protected boolean word2;
		protected boolean wordPrefix1;
		protected boolean wordPrefix2;

	}

	private String _fieldName;
	private String _fieldValue;
	private final LiferayFieldQueryFactoryFixture
		_liferayFieldQueryFactoryFixture =
			new LiferayFieldQueryFactoryFixture();

}