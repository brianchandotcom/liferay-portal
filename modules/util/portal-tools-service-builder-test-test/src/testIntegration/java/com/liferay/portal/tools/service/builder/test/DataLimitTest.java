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

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.exception.DataLimitExceededException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.model.DataLimitEntry;
import com.liferay.portal.tools.service.builder.test.service.DataLimitEntryLocalService;
import com.liferay.portal.tools.service.builder.test.service.persistence.DataLimitEntryPersistence;
import com.liferay.portal.util.PropsUtil;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class DataLimitTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDataLimit() {
		_initializeDataLimit(3);

		try {
			_testDataLimit();

			// Asserting limit is per company

			long companyId = CompanyThreadLocal.getCompanyId();

			CompanyThreadLocal.setCompanyId(companyId + 1);

			try {
				_testDataLimit();
			}
			finally {
				CompanyThreadLocal.setCompanyId(companyId);
			}
		}
		finally {
			_initializeDataLimit(0);
		}
	}

	private void _initializeDataLimit(long dataLimit) {
		ReflectionTestUtil.setFieldValue(
			_dataLimitEntryPersistence, "_dataLimitModelMaxCount", 0);

		PropsUtil.set(
			"data.limit.model.max.count[" + DataLimitEntry.class.getName() +
				"]",
			String.valueOf(dataLimit));

		ReflectionTestUtil.invoke(
			_dataLimitEntryPersistence, "setModelClass",
			new Class<?>[] {Class.class}, DataLimitEntry.class);
	}

	private void _testDataLimit() {

		// Within data limit

		DataLimitEntry dataLimitEntry1 = _dataLimitEntryPersistence.create(
			_counterLocalService.increment());

		_dataLimitEntryLocalService.updateDataLimitEntry(dataLimitEntry1);

		DataLimitEntry dataLimitEntry2 = _dataLimitEntryPersistence.create(
			_counterLocalService.increment());

		_dataLimitEntryLocalService.updateDataLimitEntry(dataLimitEntry2);

		DataLimitEntry dataLimitEntry3 = _dataLimitEntryPersistence.create(
			_counterLocalService.increment());

		dataLimitEntry3 = _dataLimitEntryLocalService.updateDataLimitEntry(
			dataLimitEntry3);

		// Exceeding data limit

		try {
			_dataLimitEntryLocalService.updateDataLimitEntry(
				_dataLimitEntryPersistence.create(
					_counterLocalService.increment()));

			Assert.fail();
		}
		catch (DataLimitExceededException dataLimitExceededException) {
			Assert.assertEquals(
				"Unable to exceed maximum number of allowed " +
					DataLimitEntry.class.getName(),
				dataLimitExceededException.getMessage());
		}

		// Modification is always allowed

		dataLimitEntry3.setModifiedDate(new Date());

		_dataLimitEntryLocalService.updateDataLimitEntry(dataLimitEntry3);
	}

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private DataLimitEntryLocalService _dataLimitEntryLocalService;

	@Inject
	private DataLimitEntryPersistence _dataLimitEntryPersistence;

}