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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_2_1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDLRecordSetTest {

	@Before
	public void setUp() {
		setUpUpgradeDDLRecordSetSettings();
	}

	@Test
	public void testActionIdsConversion1() {
		Assert.assertEquals(31L, _upgradeDDLRecordSet.getNewActionIds(31L));
	}

	@Test
	public void testActionIdsConversion2() {
		Assert.assertEquals(8L, _upgradeDDLRecordSet.getNewActionIds(16L));
	}

	@Test
	public void testActionIdsConversion3() {
		Assert.assertEquals(16L, _upgradeDDLRecordSet.getNewActionIds(8L));
	}

	@Test
	public void testActionIdsConversion4() {
		Assert.assertEquals(15L, _upgradeDDLRecordSet.getNewActionIds(23L));
	}

	protected void setUpUpgradeDDLRecordSetSettings() {
		_upgradeDDLRecordSet = new UpgradeDDLRecordSet(null, null, null, null);
	}

	private UpgradeDDLRecordSet _upgradeDDLRecordSet;

}