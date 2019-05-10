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

package com.liferay.segments.asah.internal.client.data.binding;

import com.liferay.segments.asah.internal.client.model.Individual;
import com.liferay.segments.asah.internal.client.model.Results;

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
public class IndividualJSONObjectMapperTest {

	@Test
	public void testMap() throws Exception {
		Individual individual = _individualJSONObjectMapper.map(
			_read("get-individual.json"));

		Assert.assertNotNull(individual);

		Assert.assertEquals("352412408151322817", individual.getId());

		List<Individual.DataSourceIndividualPK> dataSourceIndividualPKs =
			individual.getDataSourceIndividualPKs();

		Assert.assertEquals(
			dataSourceIndividualPKs.toString(), 1,
			dataSourceIndividualPKs.size());
	}

	@Test(expected = IOException.class)
	public void testMapThrowsIOException() throws Exception {
		_individualJSONObjectMapper.map("invalid json");
	}

	@Test
	public void testMapToResults() throws Exception {
		Results<Individual> results = _individualJSONObjectMapper.mapToResults(
			_read("get-individuals.json"));

		Assert.assertEquals(2, results.getTotal());

		List<Individual> individuals = results.getItems();

		Individual individual = individuals.get(0);

		Assert.assertEquals("337338657439093764", individual.getId());

		List<Individual.DataSourceIndividualPK> dataSourceIndividualPKs =
			individual.getDataSourceIndividualPKs();

		Assert.assertEquals(
			dataSourceIndividualPKs.toString(), 8,
			dataSourceIndividualPKs.size());

		Individual.DataSourceIndividualPK dataSourceIndividualPK =
			dataSourceIndividualPKs.get(0);

		Assert.assertEquals(
			"335470356976861111", dataSourceIndividualPK.getDataSourceId());
		Assert.assertEquals(
			"LIFERAY", dataSourceIndividualPK.getDataSourceType());

		List<String> individualPKs = dataSourceIndividualPK.getIndividualPKs();

		Assert.assertEquals(individualPKs.toString(), 13, individualPKs.size());

		Assert.assertTrue(
			individualPKs.contains("bd537758-b907-f00f-91c2-b18dd46e3b32"));
	}

	@Test(expected = IOException.class)
	public void testMapToResultsThrowsIOException() throws Exception {
		_individualJSONObjectMapper.mapToResults("invalid json");
	}

	@Test
	public void testMapToResultsWithNoResults() throws Exception {
		Results<Individual> results = _individualJSONObjectMapper.mapToResults(
			_read("get-individuals-no-results.json"));

		Assert.assertEquals(0, results.getTotal());

		List<Individual> individuals = results.getItems();

		Assert.assertEquals(individuals.toString(), 0, individuals.size());
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes, "UTF-8");
	}

	private static final IndividualJSONObjectMapper
		_individualJSONObjectMapper = new IndividualJSONObjectMapper();

}