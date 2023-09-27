/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry;
import com.liferay.portal.tools.service.builder.test.service.persistence.BigDecimalEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.BigDecimalEntryUtil;

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
				"com.liferay.portal.tools.service.builder.test.service"));

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
	public void testFindByGtBigDecimalValue() throws Exception {
		List<BigDecimalEntry> bigDecimalEntries =
			_persistence.findByGtBigDecimalValue(new BigDecimal(0));

		_assertEquals(_bigDecimalEntries.subList(2, 5), bigDecimalEntries);
	}

	@Test
	public void testFindByLtBigDecimalValue() throws Exception {
		List<BigDecimalEntry> bigDecimalEntries =
			_persistence.findByLtBigDecimalValue(new BigDecimal(0));

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