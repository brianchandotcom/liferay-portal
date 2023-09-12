/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.data.binding;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.Metric;
import com.liferay.segments.asah.connector.internal.util.comparator.MetricProcessedDateComparator;

import java.io.IOException;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Riccardo Ferrari
 */
public class ExperimentJSONObjectMapperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMap() throws Exception {
		Experiment experiment = _experimentJSONObjectMapper.map(
			_read("get-experiment.json"));

		Assert.assertNotNull(experiment);

		Assert.assertEquals("637850400632477181", experiment.getChannelId());

		Assert.assertEquals(
			Double.valueOf(95.0), experiment.getConfidenceLevel());

		Assert.assertEquals("MyExperience", experiment.getDXPExperienceName());

		List<Metric> metrics = experiment.getMetrics();

		Assert.assertEquals(metrics.toString(), 4, metrics.size());

		Metric metric = Collections.max(
			metrics, new MetricProcessedDateComparator(true));

		Assert.assertEquals("61", metric.getId());
		Assert.assertEquals(Integer.valueOf(4), metric.getElapsedDays());
	}

	@Test(expected = IOException.class)
	public void testMapThrowsIOException() throws Exception {
		_experimentJSONObjectMapper.map("invalid json");
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes, StandardCharsets.UTF_8);
	}

	private static final ExperimentJSONObjectMapper
		_experimentJSONObjectMapper = new ExperimentJSONObjectMapper();

}