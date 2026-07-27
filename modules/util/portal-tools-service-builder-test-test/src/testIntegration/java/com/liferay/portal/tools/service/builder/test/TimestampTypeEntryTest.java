/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry;
import com.liferay.portal.tools.service.builder.test.service.TimestampTypeEntryLocalService;
import com.liferay.portal.tools.service.builder.test.service.persistence.TimestampTypeEntryPersistence;

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
public class TimestampTypeEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE);

	@Test
	public void test() throws Exception {
		TimestampTypeEntry timestampTypeEntry =
			_timestampTypeEntryLocalService.createTimestampTypeEntry(
				RandomTestUtil.nextLong());

		Date date = new Date();

		timestampTypeEntry.setDateValue(date);

		timestampTypeEntry =
			_timestampTypeEntryLocalService.addTimestampTypeEntry(
				timestampTypeEntry);

		_assertDate(date, timestampTypeEntry.getDateValue());

		timestampTypeEntry =
			_timestampTypeEntryLocalService.fetchTimestampTypeEntry(
				timestampTypeEntry.getTimestampTypeEntryId());

		_assertDate(date, timestampTypeEntry.getDateValue());

		_timestampTypeEntryPersistence.clearCache();

		timestampTypeEntry =
			_timestampTypeEntryLocalService.fetchTimestampTypeEntry(
				timestampTypeEntry.getTimestampTypeEntryId());

		_assertDate(date, timestampTypeEntry.getDateValue());

		Date newDate = new Date(date.getTime() + 1000);

		timestampTypeEntry.setDateValue(newDate);

		timestampTypeEntry =
			_timestampTypeEntryLocalService.updateTimestampTypeEntry(
				timestampTypeEntry);

		_assertDate(newDate, timestampTypeEntry.getDateValue());

		timestampTypeEntry =
			_timestampTypeEntryLocalService.getTimestampTypeEntry(
				timestampTypeEntry.getTimestampTypeEntryId());

		_assertDate(newDate, timestampTypeEntry.getDateValue());

		_timestampTypeEntryPersistence.clearCache();

		timestampTypeEntry =
			_timestampTypeEntryLocalService.getTimestampTypeEntry(
				timestampTypeEntry.getTimestampTypeEntryId());

		_assertDate(newDate, timestampTypeEntry.getDateValue());
	}

	private void _assertDate(Date date1, Date date2) {
		Assert.assertEquals(Date.class, date1.getClass());
		Assert.assertEquals(Date.class, date2.getClass());

		Assert.assertEquals(date1, date2);
	}

	@Inject
	private TimestampTypeEntryLocalService _timestampTypeEntryLocalService;

	@Inject
	private TimestampTypeEntryPersistence _timestampTypeEntryPersistence;

}