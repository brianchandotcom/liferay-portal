/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.DefinedDefaultOrderEntry;
import com.liferay.portal.tools.service.builder.test.model.UndefinedDefaultOrderEntry;
import com.liferay.portal.tools.service.builder.test.service.persistence.DefinedDefaultOrderEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.UndefinedDefaultOrderEntryPersistence;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class DefaultOrderEntityTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Test
	public void testDefaultOrderEntry() {

		// Test 1, order by primary key by default

		String name = RandomTestUtil.randomString();

		long currentTime = System.currentTimeMillis();

		Object[] testData1 = {1L, name, new Date(currentTime)};
		Object[] testData2 = {2L, name, new Date(currentTime - 1000)};

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry1 =
			_createUndefinedDefaultOrderEntry(testData1);
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry2 =
			_createUndefinedDefaultOrderEntry(testData2);

		Assert.assertTrue(
			undefinedDefaultOrderEntry2.compareTo(undefinedDefaultOrderEntry1) >
				0);

		Assert.assertEquals(
			undefinedDefaultOrderEntry2,
			_undefinedDefaultOrderEntryPersistence.fetchByName(name));

		Assert.assertEquals(
			_undefinedDefaultOrderEntryPersistence.fetchByName(name),
			_undefinedDefaultOrderEntryPersistence.fetchByName_Collection_Last(
				name, null));

		// Test 2, order by modifiedDate as defined

		DefinedDefaultOrderEntry definedDefaultOrderEntry1 =
			_createDefinedDefaultOrderEntry(testData1);
		DefinedDefaultOrderEntry definedDefaultOrderEntry2 =
			_createDefinedDefaultOrderEntry(testData2);

		Assert.assertTrue(
			definedDefaultOrderEntry1.compareTo(definedDefaultOrderEntry2) > 0);

		Assert.assertEquals(
			definedDefaultOrderEntry1,
			_definedDefaultOrderEntryPersistence.fetchByName(name));
		Assert.assertEquals(
			_definedDefaultOrderEntryPersistence.fetchByName(name),
			_definedDefaultOrderEntryPersistence.fetchByName_Collection_Last(
				name, null));
	}

	private DefinedDefaultOrderEntry _createDefinedDefaultOrderEntry(
		Object[] testData) {

		DefinedDefaultOrderEntry definedDefaultOrderEntry =
			_definedDefaultOrderEntryPersistence.create((long)testData[0]);

		definedDefaultOrderEntry.setModifiedDate((Date)testData[2]);
		definedDefaultOrderEntry.setName((String)testData[1]);

		return _definedDefaultOrderEntryPersistence.update(
			definedDefaultOrderEntry);
	}

	private UndefinedDefaultOrderEntry _createUndefinedDefaultOrderEntry(
		Object[] testData) {

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
			_undefinedDefaultOrderEntryPersistence.create((long)testData[0]);

		undefinedDefaultOrderEntry.setModifiedDate((Date)testData[2]);
		undefinedDefaultOrderEntry.setName((String)testData[1]);

		return _undefinedDefaultOrderEntryPersistence.update(
			undefinedDefaultOrderEntry);
	}

	@Inject
	private DefinedDefaultOrderEntryPersistence
		_definedDefaultOrderEntryPersistence;

	@Inject
	private UndefinedDefaultOrderEntryPersistence
		_undefinedDefaultOrderEntryPersistence;

}