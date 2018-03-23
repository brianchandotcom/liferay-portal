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

import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler.FrequencyEnum;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler.LevelsEnum;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler.PeriodsEnum;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowForecastScheduler.TargetsEnum;
import com.liferay.commerce.cloud.server.model.ForecastConfiguration.Frequency;
import com.liferay.commerce.cloud.server.model.ForecastConfiguration.Level;
import com.liferay.commerce.cloud.server.model.ForecastConfiguration.Period;
import com.liferay.commerce.cloud.server.model.ForecastConfiguration.Target;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowUtilTest {

	@Test
	public void testFromEleflow() {
		_testFromEleflow(FrequencyEnum.values(), FrequencyEnum.class);
		_testFromEleflow(LevelsEnum.values(), LevelsEnum.class);
		_testFromEleflow(PeriodsEnum.values(), PeriodsEnum.class);
		_testFromEleflow(TargetsEnum.values(), TargetsEnum.class);
	}

	@Test
	public void testToEleflow() {
		_testToEleflow(Frequency.values(), FrequencyEnum::fromValue);
		_testToEleflow(Level.values(), LevelsEnum::fromValue);
		_testToEleflow(Period.values(), PeriodsEnum::fromValue);
		_testToEleflow(Target.values(), TargetsEnum::fromValue);
	}

	private <E extends Enum<E>> void _testFromEleflow(
		E[] values, Class<E> clazz) {

		for (E value : values) {
			Assert.assertNotNull(EleflowUtil.fromEleflow(value, clazz));
		}
	}

	private <D extends Enum<D>, E extends Enum<E>> void _testToEleflow(
		D[] values, Function<String, E> function) {

		for (D value : values) {
			Assert.assertNotNull(EleflowUtil.toEleflow(value, function));
		}
	}

}