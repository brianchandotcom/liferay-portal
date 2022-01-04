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
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class RankingToDocumentTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_documentToRankingTranslator = _createDocumentToRankingTranslator();
		_rankingToDocumentTranslator = _createRankingToDocumentTranslator();
	}

	@Test
	public void testBlocks() {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		rankingBuilder.hiddenDocumentIds(
			Arrays.asList("142857", "285714", "428571"));

		Document document = translate(rankingBuilder.build());

		Map<String, Field> fieldsMap = document.getFields();

		Assert.assertEquals(
			"{blocks=[142857, 285714, 428571]}", fieldsMap.toString());

		Ranking ranking2 = _documentToRankingTranslator.translate(
			document, null);

		Assert.assertEquals(
			"[142857, 285714, 428571]",
			String.valueOf(ranking2.getHiddenDocumentIds()));
	}

	@Test
	public void testDefaults() {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		Document document = translate(rankingBuilder.build());

		Map<String, Field> fieldsMap = document.getFields();

		Assert.assertEquals("{}", fieldsMap.toString());

		Ranking ranking2 = _documentToRankingTranslator.translate(
			document, null);

		Assert.assertEquals("[]", String.valueOf(ranking2.getAliases()));
		Assert.assertEquals(
			"[]", String.valueOf(ranking2.getHiddenDocumentIds()));
		Assert.assertEquals("[]", String.valueOf(ranking2.getPins()));
		Assert.assertEquals("[]", String.valueOf(ranking2.getQueryStrings()));
	}

	@Test
	public void testPins() {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		rankingBuilder.pins(
			Collections.singletonList(new Ranking.Pin(142857, "uid")));

		Document document = translate(rankingBuilder.build());

		Map<String, Field> fieldsMap = document.getFields();

		Assert.assertEquals(
			"{pins={position=142857, uid=uid}}", fieldsMap.toString());

		Ranking ranking2 = _documentToRankingTranslator.translate(
			document, null);

		Assert.assertEquals("[142857=uid]", _toString(ranking2.getPins()));
	}

	@Test
	public void testQueryStrings() {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		rankingBuilder.aliases(Arrays.asList("142857", "285714", "428571"));

		Document document = translate(rankingBuilder.build());

		Map<String, Field> fieldsMap = document.getFields();

		Assert.assertEquals(
			"{aliases=[142857, 285714, 428571], queryStrings=[142857, " +
				"285714, 428571]}",
			fieldsMap.toString());

		Ranking ranking2 = _documentToRankingTranslator.translate(
			document, null);

		Assert.assertEquals(
			"[142857, 285714, 428571]",
			String.valueOf(ranking2.getQueryStrings()));
	}

	protected Document translate(Ranking ranking) {
		return _rankingToDocumentTranslator.translate(ranking);
	}

	private DocumentToRankingTranslator _createDocumentToRankingTranslator() {
		return new DocumentToRankingTranslatorImpl();
	}

	private RankingToDocumentTranslatorImpl
		_createRankingToDocumentTranslator() {

		return new RankingToDocumentTranslatorImpl() {
			{
				setDocumentBuilderFactory(new DocumentBuilderFactoryImpl());
			}
		};
	}

	private String _toString(List<Ranking.Pin> pins) {
		Stream<Ranking.Pin> stream = pins.stream();

		return String.valueOf(
			stream.map(
				pin -> pin.getPosition() + "=" + pin.getDocumentId()
			).collect(
				Collectors.toList()
			));
	}

	private DocumentToRankingTranslator _documentToRankingTranslator;
	private RankingToDocumentTranslator _rankingToDocumentTranslator;

}