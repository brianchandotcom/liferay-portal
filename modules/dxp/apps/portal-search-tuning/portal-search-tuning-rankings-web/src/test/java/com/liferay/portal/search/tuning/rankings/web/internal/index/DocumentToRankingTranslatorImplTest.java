/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.petra.string.StringPool;
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
		Assert.assertEquals(
			"theGroupExternalReferenceCode",
			ranking.getGroupExternalReferenceCode());
		Assert.assertEquals("theAlias1", ranking.getName());
		Assert.assertEquals("theAlias1", ranking.getQueryString());
		Assert.assertEquals(
			"theSXPBlueprintExternalReferenceCode",
			ranking.getSXPBlueprintExternalReferenceCode());
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
				else if (argument.equals(RankingFields.BLOCKS)) {
					return Arrays.asList("theBlock1", "theBlock2");
				}
				else if (argument.equals(RankingFields.QUERY_STRINGS)) {
					return Arrays.asList("theQueryString1", "theQueryString2");
				}

				return Collections.emptyList();
			}
		);

		Mockito.when(
			document.getString(Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> {
				String argument = (String)invocationOnMock.getArguments()[0];

				if (argument.equals(
						RankingFields.GROUP_EXTERNAL_REFERENCE_CODE)) {

					return "theGroupExternalReferenceCode";
				}
				else if (argument.equals(
							RankingFields.
								SXP_BLUEPRINT_EXTERNAL_REFERENCE_CODE)) {

					return "theSXPBlueprintExternalReferenceCode";
				}

				return StringPool.BLANK;
			}
		);

		return document;
	}

	private DocumentToRankingTranslatorImpl _documentToRankingTranslatorImpl;

}