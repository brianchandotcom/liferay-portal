/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.sort;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.function.Consumer;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseSortUnmappedFieldsTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testDefaultIsTextMappingWithEmptyResults() {
		String fieldName = RandomTestUtil.randomString();

		addDocument(
			builder().setString(fieldName, RandomTestUtil.randomString()));

		assertSearch(fieldName, "[]");
	}

	@Test
	public void testDefaultOnNewIndexIsEmptyResults() {
		assertSearch(RandomTestUtil.randomString(), "[]");
	}

	@Test
	public void testEmailAddressIsKeywordInLiferayTypeMappings() {
		addDocument(
			builder(
			).setString(
				"emailAddress", "dxp@liferay.com"
			).setString(
				"emailAddressDomain", "liferay.com"
			));

		assertSearch("emailAddress", "[dxp@liferay.com]");
		assertSearch("emailAddress", "[]", withTerm("emailAddress", "dxp"));
		assertSearch(
			"emailAddress", "[]", withTerm("emailAddress", "liferay.com"));
		assertSearch(
			"emailAddress", "[dxp@liferay.com]",
			withTerm("emailAddress", "dxp@liferay.com"));

		assertSearch("emailAddressDomain", "[liferay.com]");
		assertSearch(
			"emailAddressDomain", "[]",
			withTerm("emailAddressDomain", "liferay"));
		assertSearch(
			"emailAddressDomain", "[]", withTerm("emailAddressDomain", "com"));
		assertSearch(
			"emailAddressDomain", "[liferay.com]",
			withTerm("emailAddressDomain", "liferay.com"));
	}

	@Test
	public void testEmailAddressOnNewIndex() {
		assertSearch("emailAddress", "[]");
	}

	@Test
	public void testFirstNameIsTextMappingWithEmptyResults() {
		addDocument(builder().setString("firstName", "Liferay DXP"));

		assertSearch("firstName", "[]");
		assertSearch("firstName", "[]", withTerm("firstName", "Liferay DXP"));
	}

	@Test
	public void testFirstNameOnNewIndex() {
		assertSearch("firstName", "[]");
	}

	@Test
	public void testFirstNameStringSortable() {
		addDocument(
			builder(
			).setString(
				"firstName", "Liferay DXP"
			).setString(
				"firstName_String_sortable", "Liferay DXP"
			));

		assertSearch("firstName_String_sortable", "[Liferay DXP]");

		assertSearch(
			"firstName_String_sortable", "[Liferay DXP]",
			withTerm("firstName", "dxp"));
		assertSearch(
			"firstName_String_sortable", "[]",
			withTerm("firstName", "Liferay DXP"));

		assertSearch(
			"firstName_String_sortable", "[]",
			withTerm("firstName_String_sortable", "dxp"));
		assertSearch(
			"firstName_String_sortable", "[Liferay DXP]",
			withTerm("firstName_String_sortable", "Liferay DXP"));
	}

	@Test
	public void testFirstNameStringSortableOnNewIndex() {
		assertSearch("firstName_String_sortable", "[]");
	}

	@Test
	public void testSortableKeyword() {
		String fieldName1 = RandomTestUtil.randomString();

		String fieldName2 = fieldName1 + "_String_sortable";

		String fieldValue = RandomTestUtil.randomString();

		addDocument(
			builder(
			).setString(
				fieldName1, fieldValue
			).setString(
				fieldName2, fieldValue
			));

		assertSearch(fieldName1, "[]");
		assertSearch(fieldName2, "[" + fieldValue + "]");
	}

	@Test
	public void testSortableNumber() throws Throwable {
		String fieldName1 = RandomTestUtil.randomString();

		String fieldName2 = fieldName1 + "_Number_sortable";

		int fieldValue = RandomTestUtil.randomInt();

		addDocument(
			builder(
			).setInteger(
				fieldName1, fieldValue
			).setInteger(
				fieldName2, fieldValue
			));

		assertSearch(fieldName1, "[]");
		assertSearch(fieldName2, "[" + fieldValue + "]");
	}

	protected void assertSearch(
		String fieldName, String expected,
		Consumer<SearchRequestBuilder>... searchRequestBuilderConsumers) {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.sorts(
						sorts.field(fieldName)
					).withSearchRequestBuilder(
						searchRequestBuilderConsumers
					));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> DocumentsAssert.assertValues(
						searchResponse.getRequestString(),
						searchResponse.getDocuments(), fieldName, expected));
			});
	}

	protected DocumentBuilder builder() {
		return newDocumentBuilder();
	}

	protected Consumer<SearchRequestBuilder> withTerm(
		String fieldName, String term) {

		return searchRequestBuilder -> searchRequestBuilder.addComplexQueryPart(
			complexQueryPartBuilderFactory.builder(
			).query(
				queries.term(fieldName, term)
			).build());
	}

	protected final ComplexQueryPartBuilderFactory
		complexQueryPartBuilderFactory =
			new ComplexQueryPartBuilderFactoryImpl();

}