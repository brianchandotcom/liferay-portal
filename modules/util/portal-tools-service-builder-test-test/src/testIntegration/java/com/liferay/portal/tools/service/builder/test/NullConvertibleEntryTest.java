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
	public void testCountNullConvertibleString() {
		int initialCount = _nullConvertibleEntryLocalService.countByName(null);

		NullConvertibleEntry nullConvertibleEntry =
			_nullConvertibleEntryLocalService.addNullConvertibleEntry(
				(String)null);

		Assert.assertEquals(
			initialCount + 1,
			_nullConvertibleEntryLocalService.countByName(null));

		_nullConvertibleEntryLocalService.deleteNullConvertibleEntry(
			nullConvertibleEntry);

		Assert.assertEquals(
			initialCount, _nullConvertibleEntryLocalService.countByName(null));
	}

	@Test
	public void testFetchNullConvertibleString() {
		NullConvertibleEntry nullConvertibleEntry =
			_nullConvertibleEntryLocalService.addNullConvertibleEntry(
				(String)null);

		Assert.assertEquals(
			nullConvertibleEntry,
			_nullConvertibleEntryLocalService.fetchByName(null));
	}

	@Inject
	private NullConvertibleEntryLocalService _nullConvertibleEntryLocalService;

}