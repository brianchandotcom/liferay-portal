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

package com.liferay.portal.search.web.internal.search.results.portlet;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class SearchResultsSummariesHolderTest {

	@Test
	public void testOrder() {
		int capacity = 1000;

		List<Document> expectedDocuments = new ArrayList<>(capacity);

		SearchResultsSummariesHolder searchResultsSummariesHolder =
			new SearchResultsSummariesHolder(capacity);

		for (int i = 0; i < capacity; i++) {
			Document document = Mockito.mock(Document.class);

			expectedDocuments.add(document);

			searchResultsSummariesHolder.put(
				document, new SearchResultSummaryDisplayContext());
		}

		List<Document> actualDocuments = new ArrayList<>(
			searchResultsSummariesHolder.getDocuments());

		for (int i = 0; i < capacity; i++) {
			Assert.assertSame(expectedDocuments.get(i), actualDocuments.get(i));
		}
	}

}