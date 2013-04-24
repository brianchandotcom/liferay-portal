/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.upgrade.BaseUpgradeProcessTestCase;
import com.liferay.portal.upgrade.UpgradeProcessTestUtil;
import com.liferay.portal.upgrade.UpgradeProcess_6_2_0;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class UpgradeProcess_6_1_1_to_6_2_0_Test
	extends BaseUpgradeProcessTestCase {

	@Test
	public void testDoUpgrade() throws Exception {
		UpgradeProcessTestUtil.doUpgrade(UpgradeProcess_6_2_0.class);
	}

	protected String getOriginVersion() {
		return "6.1.1";
	}

}