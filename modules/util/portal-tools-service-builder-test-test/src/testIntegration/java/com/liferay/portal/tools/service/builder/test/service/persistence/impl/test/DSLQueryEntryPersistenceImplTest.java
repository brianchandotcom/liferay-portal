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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryEntryTable;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntryTable;
import com.liferay.portal.tools.service.builder.test.service.persistence.DSLQueryEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.DSLQueryStatusEntryPersistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class DSLQueryEntryPersistenceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() {
		_testDSLQueryEntry1 = _addDSLQueryEntry();
		_testDSLQueryEntry2 = _addDSLQueryEntry();
		_testDSLQueryEntry3 = _addDSLQueryEntry();
	}

	@Test
	public void testDSLQuery() {
		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			DSLQueryEntryTable.INSTANCE.name
		).from(
			DSLQueryEntryTable.INSTANCE
		).where(
			DSLQueryEntryTable.INSTANCE.dslQueryEntryId.neq(
				_testDSLQueryEntry1.getDslQueryEntryId())
		).orderBy(
			DSLQueryEntryTable.INSTANCE.dslQueryEntryId.ascending()
		);

		Assert.assertEquals(
			Arrays.asList(
				_testDSLQueryEntry2.getName(), _testDSLQueryEntry3.getName()),
			_dslQueryEntryPersistence.dslQuery(dslQuery));

		_dslQueryEntryPersistence.remove(_testDSLQueryEntry2);

		Assert.assertEquals(
			Arrays.asList(_testDSLQueryEntry3.getName()),
			_dslQueryEntryPersistence.dslQuery(dslQuery));

		_testDSLQueryEntry3.setName("updated.name");

		_testDSLQueryEntry3 = _dslQueryEntryPersistence.updateImpl(
			_testDSLQueryEntry3);

		Assert.assertEquals(
			Arrays.asList("updated.name"),
			_dslQueryEntryPersistence.dslQuery(dslQuery));

		DSLQueryEntry dslQueryEntry = _addDSLQueryEntry();

		Assert.assertEquals(
			Arrays.asList(
				_testDSLQueryEntry3.getName(), dslQueryEntry.getName()),
			_dslQueryEntryPersistence.dslQuery(dslQuery));
	}

	@Test
	public void testDSLQueryWithMultiTables() {
		long currentTime = System.currentTimeMillis();

		_addDSLQueryStatusEntry(_testDSLQueryEntry1, currentTime - 1);
		_addDSLQueryStatusEntry(_testDSLQueryEntry1, currentTime);
		_addDSLQueryStatusEntry(_testDSLQueryEntry2, currentTime - 1);
		_addDSLQueryStatusEntry(_testDSLQueryEntry3, currentTime - 1);

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			DSLQueryEntryTable.INSTANCE
		).from(
			DSLQueryEntryTable.INSTANCE
		).leftJoinOn(
			DSLQueryStatusEntryTable.INSTANCE,
			DSLQueryEntryTable.INSTANCE.dslQueryEntryId.eq(
				DSLQueryStatusEntryTable.INSTANCE.dslQueryEntryId)
		).where(
			DSLQueryStatusEntryTable.INSTANCE.statusDate.gte(
				new Date(currentTime))
		);

		Assert.assertEquals(
			Arrays.asList(_testDSLQueryEntry1),
			_dslQueryEntryPersistence.dslQuery(dslQuery));

		_dslQueryEntryPersistence.remove(_testDSLQueryEntry1);

		Assert.assertEquals(
			Collections.emptyList(),
			_dslQueryEntryPersistence.dslQuery(dslQuery));

		_addDSLQueryStatusEntry(_testDSLQueryEntry2, currentTime);

		Assert.assertEquals(
			Arrays.asList(_testDSLQueryEntry2),
			_dslQueryEntryPersistence.dslQuery(dslQuery));
	}

	private DSLQueryEntry _addDSLQueryEntry() {
		DSLQueryEntry dslQueryEntry = _dslQueryEntryPersistence.create(
			RandomTestUtil.nextLong());

		dslQueryEntry.setName(RandomTestUtil.randomString());

		dslQueryEntry = _dslQueryEntryPersistence.updateImpl(dslQueryEntry);

		_dslQueryEntries.add(dslQueryEntry);

		return dslQueryEntry;
	}

	private DSLQueryStatusEntry _addDSLQueryStatusEntry(
		DSLQueryEntry dslQueryEntry, long time) {

		DSLQueryStatusEntry dslQueryStatusEntry =
			_dslQueryStatusEntryPersistence.create(RandomTestUtil.nextLong());

		dslQueryStatusEntry.setDslQueryEntryId(
			dslQueryEntry.getDslQueryEntryId());
		dslQueryStatusEntry.setStatus(RandomTestUtil.randomString());
		dslQueryStatusEntry.setStatusDate(new Date(time));

		dslQueryStatusEntry = _dslQueryStatusEntryPersistence.updateImpl(
			dslQueryStatusEntry);

		_dslQueryStatusEntries.add(dslQueryStatusEntry);

		return dslQueryStatusEntry;
	}

	@DeleteAfterTestRun
	private final List<DSLQueryEntry> _dslQueryEntries = new ArrayList<>();

	@Inject
	private DSLQueryEntryPersistence _dslQueryEntryPersistence;

	@DeleteAfterTestRun
	private final List<DSLQueryStatusEntry> _dslQueryStatusEntries =
		new ArrayList<>();

	@Inject
	private DSLQueryStatusEntryPersistence _dslQueryStatusEntryPersistence;

	private DSLQueryEntry _testDSLQueryEntry1;
	private DSLQueryEntry _testDSLQueryEntry2;
	private DSLQueryEntry _testDSLQueryEntry3;

}