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

package com.liferay.segments.asah.connector.internal.client.data.binding;

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
import org.junit.Test;

/**
 * @author David Arques
 */
public class IndividualSegmentJSONObjectMapperTest {

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