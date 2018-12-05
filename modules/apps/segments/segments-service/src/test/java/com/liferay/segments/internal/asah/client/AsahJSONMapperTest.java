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

package com.liferay.segments.internal.asah.client;

import com.liferay.segments.internal.asah.client.model.Author;
import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Results;

import java.io.IOException;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Arques
 */
public class AsahJSONMapperTest {

	@Test
	public void testMapToIndividualSegment() throws Exception {
		IndividualSegment individualSegment =
			_asahJSONMapper.mapToIndividualSegment(
				_read("get-individual-segment.json"));

		Assert.assertNotNull(individualSegment);

		Assert.assertEquals("324849894334623092", individualSegment.getId());
		Assert.assertEquals("British Developers", individualSegment.getName());
		Assert.assertEquals(8L, individualSegment.getIndividualCount());

		Author author = individualSegment.getAuthor();

		Assert.assertEquals("132184", author.getId());
	}

	@Test
	public void testMapToIndividualSegmentResults() throws Exception {
		Results<IndividualSegment> results =
			_asahJSONMapper.mapToIndividualSegmentResults(
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
	public void testMapToIndividualSegmentResultsWithInvalidJSON()
		throws Exception {

		_asahJSONMapper.mapToIndividualSegmentResults("invalid json");
	}

	@Test(expected = IOException.class)
	public void testMapToIndividualSegmentWithInvalidJSON() throws Exception {
		_asahJSONMapper.mapToIndividualSegment("invalid json");
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes, "UTF-8");
	}

	private static final AsahJSONMapper _asahJSONMapper = new AsahJSONMapper();

}