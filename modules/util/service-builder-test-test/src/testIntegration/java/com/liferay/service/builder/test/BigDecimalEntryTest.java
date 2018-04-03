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

package com.liferay.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.service.builder.test.model.BigDecimalEntry;
import com.liferay.service.builder.test.service.persistence.BigDecimalEntryPersistence;
import com.liferay.service.builder.test.service.persistence.BigDecimalEntryUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class BigDecimalEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.service.builder.test.service"));

	@Before
	public void setUp() throws Exception {
		_persistence = BigDecimalEntryUtil.getPersistence();

		_addBigDecimalEntry(new BigDecimal("-2.718281828"));
		_addBigDecimalEntry(new BigDecimal("-0.12345678912345"));
		_addBigDecimalEntry(new BigDecimal("1"));
		_addBigDecimalEntry(new BigDecimal("3.141592654"));
		_addBigDecimalEntry(new BigDecimal("10000000000000.1"));

		EntityCacheUtil.clearCache();
	}

	@After
	public void tearDown() throws Exception {
		for (BigDecimalEntry bigDecimalEntry : _bigDecimalEntries) {
			_persistence.remove(bigDecimalEntry);
		}

		_bigDecimalEntries.clear();
	}

	@Test
	public void testFindByBigDecimalValue() throws Exception {
		List<BigDecimalEntry> bigDecimalEntries =
			_persistence.findByBigDecimalValue(new BigDecimal("3.141592654"));

		_assertEquals(_bigDecimalEntries.subList(3, 4), bigDecimalEntries);
	}

	@Test
	public void testFindByGreaterThanBigDecimalValue() throws Exception {
		List<BigDecimalEntry> bigDecimalEntries =
			_persistence.findByGreaterThanBigDecimalValue(new BigDecimal(0));

		_assertEquals(_bigDecimalEntries.subList(2, 5), bigDecimalEntries);
	}

	@Test
	public void testFindByLessThanBigDecimalValue() throws Exception {
		List<BigDecimalEntry> bigDecimalEntries =
			_persistence.findByLessThanBigDecimalValue(new BigDecimal(0));

		_assertEquals(_bigDecimalEntries.subList(0, 2), bigDecimalEntries);
	}

	private void _addBigDecimalEntry(BigDecimal bigDecimalValue)
		throws Exception {

		BigDecimalEntry bigDecimalEntry = _persistence.create(
			RandomTestUtil.nextLong());

		bigDecimalEntry.setBigDecimalValue(bigDecimalValue);

		_bigDecimalEntries.add(_persistence.update(bigDecimalEntry));
	}

	private void _assertEquals(
		List<BigDecimalEntry> expectedBigDecimalEntries,
		List<BigDecimalEntry> actualBigDecimalEntries) {

		Assert.assertEquals(expectedBigDecimalEntries, actualBigDecimalEntries);

		for (int i = 0; i < expectedBigDecimalEntries.size(); i++) {
			BigDecimalEntry expected = expectedBigDecimalEntries.get(i);
			BigDecimalEntry actual = actualBigDecimalEntries.get(i);

			Assert.assertEquals(
				expected.getBigDecimalValue(), actual.getBigDecimalValue());
		}
	}

	private final List<BigDecimalEntry> _bigDecimalEntries = new ArrayList<>();
	private BigDecimalEntryPersistence _persistence;

}