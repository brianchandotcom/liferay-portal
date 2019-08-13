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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class RankingToDocumentTranslatorTest {

	@Before
	public void setUp() {
		_documentToRankingTranslator = createDocumentToRankingTranslator();
		_rankingToDocumentTranslator = createRankingToDocumentTranslator();
	}

	@Test
	public void testBlocks() {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		rankingBuilder.blocks(Arrays.asList("142857", "285714", "428571"));

		Ranking ranking1 = rankingBuilder.build();

		Document document = translate(ranking1);

		Map<String, Field> fieldsMap = document.getFields();

		Assert.assertEquals(
			"{blocks=[142857, 285714, 428571]}", fieldsMap.toString());

		Ranking ranking2 = _documentToRankingTranslator.translate(
			document, null);

		Assert.assertEquals(
			"[142857, 285714, 428571]", String.valueOf(ranking2.getBlockIds()));
	}

	@Test
	public void testDefaults() {
		Ranking ranking1 = new Ranking.RankingBuilder().build();

		Document document = translate(ranking1);

		Map<String, Field> fieldsMap = document.getFields();

		Assert.assertEquals("{}", fieldsMap.toString());

		Ranking ranking2 = _documentToRankingTranslator.translate(
			document, null);

		Assert.assertEquals("[]", String.valueOf(ranking2.getBlockIds()));
		Assert.assertEquals("[]", String.valueOf(ranking2.getPins()));
		Assert.assertEquals("[]", String.valueOf(ranking2.getQueryStrings()));
	}

	@Test
	public void testPins() {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		rankingBuilder.pins(
			Collections.singletonList(new Ranking.Pin(142857, "uid")));

		Ranking ranking1 = rankingBuilder.build();

		Document document = translate(ranking1);

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

		rankingBuilder.queryStrings(
			Arrays.asList("142857", "285714", "428571"));

		Ranking ranking1 = rankingBuilder.build();

		Document document = translate(ranking1);

		Map<String, Field> fieldsMap = document.getFields();

		Assert.assertEquals(
			"{queryStrings=[142857, 285714, 428571]}", fieldsMap.toString());

		Ranking ranking2 = _documentToRankingTranslator.translate(
			document, null);

		Assert.assertEquals(
			"[142857, 285714, 428571]",
			String.valueOf(ranking2.getQueryStrings()));
	}

	protected static DocumentToRankingTranslator
		createDocumentToRankingTranslator() {

		return new DocumentToRankingTranslatorImpl();
	}

	protected static RankingToDocumentTranslatorImpl
		createRankingToDocumentTranslator() {

		return new RankingToDocumentTranslatorImpl() {
			{
				setDocumentBuilderFactory(new DocumentBuilderFactoryImpl());
			}
		};
	}

	protected Document translate(Ranking ranking) {
		return _rankingToDocumentTranslator.translate(ranking);
	}

	private String _toString(List<Ranking.Pin> pins) {
		Stream<Ranking.Pin> stream = pins.stream();

		return String.valueOf(
			stream.map(
				pin -> pin.getPosition() + "=" + pin.getId()
			).collect(
				Collectors.toList()
			));
	}

	private DocumentToRankingTranslator _documentToRankingTranslator;
	private RankingToDocumentTranslator _rankingToDocumentTranslator;

}