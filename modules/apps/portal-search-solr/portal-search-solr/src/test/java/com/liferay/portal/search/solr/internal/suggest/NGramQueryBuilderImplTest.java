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

package com.liferay.portal.search.solr.internal.suggest;

import com.liferay.portal.kernel.search.suggest.NGramHolderBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.solr.suggest.NGramQueryBuilder;

import org.apache.solr.client.solrj.SolrQuery;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class NGramQueryBuilderImplTest {

	@Test
	public void testGetNGramQuery() throws Exception {
		NGramQueryBuilder nGramQueryBuilder = createNGramQueryBuilder();

		SolrQuery nGramQuery = nGramQueryBuilder.getNGramQuery("LIFERAY ROCKS");

		Assert.assertEquals(
			concat(
				"q=gram3%3Aife+OR+gram3%3Afer+OR+gram3%3Aera+OR+gram3%3Aray+OR",
				"+gram3%3A%28ay+%29+OR+gram3%3A%28y+r%29+OR+gram3%3A%28+ro%29+",
				"OR+gram3%3Aroc+OR+gram3%3Aock+OR+gram4%3Aifer+OR+gram4%3Afera",
				"+OR+gram4%3Aeray+OR+gram4%3A%28ray+%29+OR+gram4%3A%28ay+r%29+",
				"OR+gram4%3A%28y+ro%29+OR+gram4%3A%28+roc%29+OR+gram4%3Arock+O",
				"R+end4%3Aocks+OR+end3%3Acks+OR+start3%3Alif+OR+start4%3Alife+",
				"OR+spellCheckWord%3A%28liferay+rocks%29"),
			nGramQuery.toString());
	}

	@Test
	public void testLuceneUnfriendlyTerms() throws Exception {
		NGramQueryBuilder nGramQueryBuilder = createNGramQueryBuilder();

		SolrQuery nGramQuery = nGramQueryBuilder.getNGramQuery(
			"+alpha AND -bravo");

		Assert.assertEquals(
			concat(
				"q=gram3%3Aalp+OR+gram3%3Alph+OR+gram3%3Apha+OR+gram3%3A%28ha+",
				"%29+OR+gram3%3A%28a+a%29+OR+gram3%3A%28+an%29+OR+gram3%3Aand+",
				"OR+gram3%3A%28nd+%29+OR+gram3%3A%28d+%5C-%29+OR+gram3%3A%28+%",
				"5C-b%29+OR+gram3%3A%5C-br+OR+gram3%3Abra+OR+gram3%3Arav+OR+gr",
				"am4%3Aalph+OR+gram4%3Alpha+OR+gram4%3A%28pha+%29+OR+gram4%3A%",
				"28ha+a%29+OR+gram4%3A%28a+an%29+OR+gram4%3A%28+and%29+OR+gram",
				"4%3A%28and+%29+OR+gram4%3A%28nd+%5C-%29+OR+gram4%3A%28d+%5C-b",
				"%29+OR+gram4%3A%28+%5C-br%29+OR+gram4%3A%5C-bra+OR+gram4%3Abr",
				"av+OR+end4%3Aravo+OR+end3%3Aavo+OR+start3%3A%5C%2Bal+OR+start",
				"4%3A%5C%2Balp+OR+spellCheckWord%3A%28%5C%2Balpha+and+%5C-brav",
				"o%29"),
			nGramQuery.toString());
	}

	protected String concat(String s1, String s2, String... stringArray) {
		StringBundler sb = new StringBundler(2 + stringArray.length);

		sb.append(s1);
		sb.append(s2);
		sb.append(stringArray);

		return sb.toString();
	}

	protected NGramQueryBuilder createNGramQueryBuilder() {
		NGramQueryBuilderImpl nGramQueryBuilderImpl =
			new NGramQueryBuilderImpl();

		NGramHolderBuilder nGramHolderBuilder = new NGramHolderBuilderImpl();

		nGramQueryBuilderImpl.setNGramHolderBuilder(nGramHolderBuilder);

		return nGramQueryBuilderImpl;
	}

}