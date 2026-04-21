/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.business.type;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Leite
 */
public class MultiselectPicklistObjectFieldBusinessTypeTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDBType() {
		MultiselectPicklistObjectFieldBusinessType
			multiselectPicklistObjectFieldBusinessType =
				new MultiselectPicklistObjectFieldBusinessType();

		Assert.assertEquals(
			ObjectFieldConstants.DB_TYPE_CLOB,
			multiselectPicklistObjectFieldBusinessType.getDBType());
	}

}