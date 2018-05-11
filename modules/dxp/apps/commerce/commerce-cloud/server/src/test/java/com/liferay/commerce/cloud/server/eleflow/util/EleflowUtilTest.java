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

package com.liferay.commerce.cloud.server.eleflow.util;

import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastForecasts;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastSchedulerForecasts;
import com.liferay.commerce.cloud.server.model.ForecastFrequency;
import com.liferay.commerce.cloud.server.model.ForecastLevel;
import com.liferay.commerce.cloud.server.model.ForecastPeriod;
import com.liferay.commerce.cloud.server.model.ForecastTarget;

import java.time.LocalDate;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowUtilTest {

	@Test
	public void testFromEleflow() {
		_testFromEleflow(EleflowForecastForecasts.LevelEnum.class);
		_testFromEleflow(EleflowForecastForecasts.PeriodEnum.class);
		_testFromEleflow(EleflowForecastForecasts.TargetEnum.class);
		_testFromEleflow(EleflowForecastScheduler.FrequencyEnum.class);
		_testFromEleflow(EleflowForecastSchedulerForecasts.LevelEnum.class);
		_testFromEleflow(EleflowForecastSchedulerForecasts.PeriodEnum.class);
		_testFromEleflow(EleflowForecastSchedulerForecasts.TargetEnum.class);
	}

	@Test
	public void testGetDateString() {
		Assert.assertEquals(
			"1982-01-08", EleflowUtil.getDateString(379296000000L));
	}

	@Test
	public void testGetTime() {
		Assert.assertEquals(379296000000L, EleflowUtil.getTime("1982-01-08"));
		Assert.assertEquals(
			379296000000L, EleflowUtil.getTime(LocalDate.of(1982, 1, 8)));
	}

	@Test
	public void testToEleflow() {
		_testToEleflow(
			ForecastFrequency.class,
			EleflowForecastScheduler.FrequencyEnum::fromValue);

		_testToEleflow(
			ForecastLevel.class, EleflowForecastForecasts.LevelEnum::fromValue);
		_testToEleflow(
			ForecastLevel.class,
			EleflowForecastSchedulerForecasts.LevelEnum::fromValue);

		_testToEleflow(
			ForecastPeriod.class,
			EleflowForecastForecasts.PeriodEnum::fromValue);
		_testToEleflow(
			ForecastPeriod.class,
			EleflowForecastSchedulerForecasts.PeriodEnum::fromValue);

		_testToEleflow(
			ForecastTarget.class,
			EleflowForecastForecasts.TargetEnum::fromValue);
		_testToEleflow(
			ForecastTarget.class,
			EleflowForecastSchedulerForecasts.TargetEnum::fromValue);
	}

	private <E extends Enum<E>> void _testFromEleflow(Class<E> clazz) {
		Set<E> values = EnumSet.allOf(clazz);

		for (E value : values) {
			Assert.assertNotNull(EleflowUtil.fromEleflow(value, clazz));
		}
	}

	private <D extends Enum<D>, E extends Enum<E>> void _testToEleflow(
		Class<D> clazz, Function<String, E> function) {

		Set<D> values = EnumSet.allOf(clazz);

		for (D value : values) {
			Assert.assertNotNull(EleflowUtil.toEleflow(value, function));
		}
	}

}