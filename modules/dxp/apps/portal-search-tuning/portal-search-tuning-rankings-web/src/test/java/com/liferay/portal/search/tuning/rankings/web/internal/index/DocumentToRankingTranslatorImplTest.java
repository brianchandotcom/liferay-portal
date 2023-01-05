/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class DocumentToRankingTranslatorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_documentToRankingTranslatorImpl =
			new DocumentToRankingTranslatorImpl();
	}

	@Test
	public void testTranslate() {
		Document document = _setUpDocumentWithGetStrings();

		Ranking ranking = _documentToRankingTranslatorImpl.translate(
			document, "rankingDocumentId");

		Assert.assertEquals(
			document.getStrings(RankingFields.ALIASES), ranking.getAliases());

		Assert.assertEquals("theAlias1", ranking.getName());
		Assert.assertEquals("theAlias1", ranking.getQueryString());
	}

	private Document _setUpDocumentWithGetStrings() {
		Document document = Mockito.mock(Document.class);

		Mockito.when(
			document.getStrings(Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> {
				String argument = (String)invocationOnMock.getArguments()[0];

				if (argument.equals(RankingFields.ALIASES)) {
					return Arrays.asList("theAlias1", "theAlias2");
				}
				else if (argument.equals(RankingFields.QUERY_STRINGS)) {
					return Arrays.asList("theQueryString1", "theQueryString2");
				}
				else if (argument.equals(RankingFields.BLOCKS)) {
					return Arrays.asList("theBlock1", "theBlock2");
				}

				return Collections.emptyList();
			}
		);

		return document;
	}

	private DocumentToRankingTranslatorImpl _documentToRankingTranslatorImpl;

}