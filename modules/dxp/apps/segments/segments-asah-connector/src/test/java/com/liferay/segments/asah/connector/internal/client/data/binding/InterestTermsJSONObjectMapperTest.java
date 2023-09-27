/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.data.binding;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.model.Topic;

import java.io.IOException;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sarai Díaz
 */
public class InterestTermsJSONObjectMapperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMap() throws Exception {
		Topic topic = _interestTermsJSONObjectMapper.map(
			_read("get-interests-term.json"));

		Assert.assertNotNull(topic);
		Assert.assertEquals(7, topic.getId());
		Assert.assertEquals(0.08653350323695352, topic.getId(), _DELTA);

		List<Topic.TopicTerm> topicTerms = topic.getTerms();

		Assert.assertEquals(topicTerms.toString(), 3, topicTerms.size());

		Topic.TopicTerm topicTerm = topicTerms.get(0);

		Assert.assertEquals("javascript", topicTerm.getKeyword());
		Assert.assertEquals(0.0945945945945946, topicTerm.getWeight(), _DELTA);
	}

	@Test(expected = IOException.class)
	public void testMapThrowsIOException() throws Exception {
		_interestTermsJSONObjectMapper.map("invalid json");
	}

	@Test
	public void testMapToResults() throws Exception {
		Results<Topic> results = _interestTermsJSONObjectMapper.mapToResults(
			_read("get-interests-terms.json"));

		Assert.assertEquals(3, results.getTotal());

		List<Topic> topics = results.getItems();

		Topic topic = topics.get(0);

		Assert.assertEquals(7, topic.getId());
		Assert.assertEquals(0.08653350323695352, topic.getWeight(), _DELTA);

		List<Topic.TopicTerm> topicTerms = topic.getTerms();

		Topic.TopicTerm topicTerm = topicTerms.get(0);

		Assert.assertEquals("javascript", topicTerm.getKeyword());
		Assert.assertEquals(0.0945945945945946, topicTerm.getWeight(), _DELTA);
	}

	@Test
	public void testMapToResultsWithNoResults() throws Exception {
		Results<Topic> results = _interestTermsJSONObjectMapper.mapToResults(
			_read("get-interests-terms-no-results.json"));

		Assert.assertEquals(0, results.getTotal());

		List<Topic> topics = results.getItems();

		Assert.assertEquals(topics.toString(), 0, topics.size());
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes, StandardCharsets.UTF_8);
	}

	private static final int _DELTA = 100;

	private static final InterestTermsJSONObjectMapper
		_interestTermsJSONObjectMapper = new InterestTermsJSONObjectMapper();

}