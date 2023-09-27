/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry;
import com.liferay.portal.tools.service.builder.test.service.NullConvertibleEntryLocalService;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class NullConvertibleEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testFetchNullConvertibleEntry() {
		NullConvertibleEntry nullConvertibleEntry =
			_nullConvertibleEntryLocalService.addNullConvertibleEntry(
				(String)null);

		Assert.assertEquals(
			nullConvertibleEntry,
			_nullConvertibleEntryLocalService.fetchNullConvertibleEntry(null));
	}

	@Test
	public void testGetNullConvertibleEntries() {
		int initialCount =
			_nullConvertibleEntryLocalService.getNullConvertibleEntries(null);

		NullConvertibleEntry nullConvertibleEntry =
			_nullConvertibleEntryLocalService.addNullConvertibleEntry(
				(String)null);

		Assert.assertEquals(
			initialCount + 1,
			_nullConvertibleEntryLocalService.getNullConvertibleEntries(null));

		_nullConvertibleEntryLocalService.deleteNullConvertibleEntry(
			nullConvertibleEntry);

		Assert.assertEquals(
			initialCount,
			_nullConvertibleEntryLocalService.getNullConvertibleEntries(null));
	}

	@Inject
	private NullConvertibleEntryLocalService _nullConvertibleEntryLocalService;

}