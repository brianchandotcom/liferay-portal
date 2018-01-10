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

package com.liferay.lcs.util;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Igor Beslic
 */
@RunWith(PowerMockRunner.class)
public class LCSClusterNodeStatusTest extends PowerMockito {

	@Test
	public void testGetObjectArrays() {
		int status = LCSClusterNodeStatus.ACTIVE.merge(
			LCSClusterNodeStatus.DATA_INITIALIZED.getStatus());

		Map<LCSClusterNodeStatus, Object[]> lcsClusterNodeStatusObjectArrays =
			LCSClusterNodeStatus.getObjectArrays(status);

		for (Map.Entry<LCSClusterNodeStatus, Object[]> entry :
				lcsClusterNodeStatusObjectArrays.entrySet()) {

			Object[] objectArray = entry.getValue();

			String label = (String)objectArray[0];
			Boolean statusEnabled = (Boolean)objectArray[1];
			Boolean statusExpected = (Boolean)objectArray[2];

			LCSClusterNodeStatus lcsClusterNodeStatus = entry.getKey();

			if ((lcsClusterNodeStatus == LCSClusterNodeStatus.ACTIVE) ||
				(lcsClusterNodeStatus ==
					LCSClusterNodeStatus.DATA_INITIALIZED)) {

				Assert.assertEquals(lcsClusterNodeStatus.getLabel(), label);
				Assert.assertTrue(statusEnabled);
			}
			else {
				Assert.assertEquals(
					lcsClusterNodeStatus.getOppositeLabel(), label);
				Assert.assertFalse(statusEnabled);
			}

			if (lcsClusterNodeStatus ==
					LCSClusterNodeStatus.HEARTBEAT_DELAYED) {

				Assert.assertFalse(statusExpected);
			}
			else {
				Assert.assertTrue(statusExpected);
			}
		}
	}

}