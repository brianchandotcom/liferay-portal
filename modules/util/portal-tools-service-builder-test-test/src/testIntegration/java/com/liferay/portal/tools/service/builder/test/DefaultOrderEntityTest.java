/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
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
	public void test() {

		// Defined

		String name = RandomTestUtil.randomString();
		Date modifiedDate1 = new Date();

		DefinedDefaultOrderEntry definedDefaultOrderEntry1 =
			_createDefinedDefaultOrderEntry(1, modifiedDate1, name);

		Date modifiedDate2 = new Date(modifiedDate1.getTime() - 1000);

		DefinedDefaultOrderEntry definedDefaultOrderEntry2 =
			_createDefinedDefaultOrderEntry(2, modifiedDate2, name);

		Assert.assertTrue(
			definedDefaultOrderEntry1.compareTo(definedDefaultOrderEntry2) > 0);

		Assert.assertEquals(
			definedDefaultOrderEntry1,
			_definedDefaultOrderEntryPersistence.fetchByName(name));
		Assert.assertEquals(
			_definedDefaultOrderEntryPersistence.fetchByName(name),
			_definedDefaultOrderEntryPersistence.fetchByName_Collection_First(
				name,
				new OrderByComparator<DefinedDefaultOrderEntry>() {

					@Override
					public int compare(
						DefinedDefaultOrderEntry entry1,
						DefinedDefaultOrderEntry entry2) {

						return entry2.getModifiedDate(
						).compareTo(
							entry1.getModifiedDate()
						);
					}

					@Override
					public String getOrderBy() {
						return "DefinedDefaultOrderEntry.modifiedDate DESC";
					}

				}));

		// Undefined

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry1 =
			_createUndefinedDefaultOrderEntry(1, modifiedDate1, name);
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry2 =
			_createUndefinedDefaultOrderEntry(2, modifiedDate2, name);

		Assert.assertTrue(
			undefinedDefaultOrderEntry2.compareTo(undefinedDefaultOrderEntry1) >
				0);
		Assert.assertEquals(
			undefinedDefaultOrderEntry2,
			_undefinedDefaultOrderEntryPersistence.fetchByName(name));

		Assert.assertEquals(
			_undefinedDefaultOrderEntryPersistence.fetchByName(name),
			_undefinedDefaultOrderEntryPersistence.fetchByName_Collection_First(
				name,
				new OrderByComparator<UndefinedDefaultOrderEntry>() {

					@Override
					public int compare(
						UndefinedDefaultOrderEntry entry1,
						UndefinedDefaultOrderEntry entry2) {

						return Long.compare(
							entry2.getUndefinedDefaultOrderEntryId(),
							entry1.getUndefinedDefaultOrderEntryId());
					}

					@Override
					public String getOrderBy() {
						return "UndefinedDefaultOrderEntry." +
							"undefinedDefaultOrderEntryId DESC";
					}

				}));
	}

	private DefinedDefaultOrderEntry _createDefinedDefaultOrderEntry(
		long definedDefaultOrderEntryId, Date modifiedDate, String name) {

		DefinedDefaultOrderEntry definedDefaultOrderEntry =
			_definedDefaultOrderEntryPersistence.create(
				definedDefaultOrderEntryId);

		definedDefaultOrderEntry.setModifiedDate(modifiedDate);
		definedDefaultOrderEntry.setName(name);

		return _definedDefaultOrderEntryPersistence.update(
			definedDefaultOrderEntry);
	}

	private UndefinedDefaultOrderEntry _createUndefinedDefaultOrderEntry(
		long undefinedDefaultOrderEntryId, Date modifiedDate, String name) {

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
			_undefinedDefaultOrderEntryPersistence.create(
				undefinedDefaultOrderEntryId);

		undefinedDefaultOrderEntry.setModifiedDate(modifiedDate);
		undefinedDefaultOrderEntry.setName(name);

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