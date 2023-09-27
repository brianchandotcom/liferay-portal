/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.data.binding;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.Author;
import com.liferay.segments.asah.connector.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.internal.client.model.Results;

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
 * @author David Arques
 */
public class IndividualSegmentJSONObjectMapperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMap() throws Exception {
		IndividualSegment individualSegment =
			_individualSegmentJSONObjectMapper.map(
				_read("get-individual-segment.json"));

		Assert.assertNotNull(individualSegment);

		Assert.assertEquals("324849894334623092", individualSegment.getId());
		Assert.assertEquals("British Developers", individualSegment.getName());
		Assert.assertEquals(8L, individualSegment.getIndividualCount());

		Author author = individualSegment.getAuthor();

		Assert.assertEquals("132184", author.getId());
	}

	@Test(expected = IOException.class)
	public void testMapThrowsIOException() throws Exception {
		_individualSegmentJSONObjectMapper.map("invalid json");
	}

	@Test
	public void testMapToResults() throws Exception {
		Results<IndividualSegment> results =
			_individualSegmentJSONObjectMapper.mapToResults(
				_read("get-individual-segments.json"));

		Assert.assertEquals(2, results.getTotal());

		List<IndividualSegment> individualSegments = results.getItems();

		IndividualSegment individualSegment = individualSegments.get(0);

		Assert.assertEquals("324849894334623092", individualSegment.getId());
		Assert.assertEquals("British Developers", individualSegment.getName());
		Assert.assertEquals(8L, individualSegment.getIndividualCount());

		Author author = individualSegment.getAuthor();

		Assert.assertEquals("132184", author.getId());
	}

	@Test(expected = IOException.class)
	public void testMapToResultsThrowsIOException() throws Exception {
		_individualSegmentJSONObjectMapper.mapToResults("invalid json");
	}

	@Test
	public void testMapToResultsWithNoResults() throws Exception {
		Results<IndividualSegment> results =
			_individualSegmentJSONObjectMapper.mapToResults(
				_read("get-individual-segments-no-results.json"));

		Assert.assertEquals(0, results.getTotal());

		List<IndividualSegment> individualSegments = results.getItems();

		Assert.assertEquals(
			individualSegments.toString(), 0, individualSegments.size());
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes, StandardCharsets.UTF_8);
	}

	private static final IndividualSegmentJSONObjectMapper
		_individualSegmentJSONObjectMapper =
			new IndividualSegmentJSONObjectMapper();

}