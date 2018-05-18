/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service.impl;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderLocalServiceImplTest {

	@Test
	public void testAvailableOrderStatuses() {
		int[] availableOrderStatuses = ArrayUtil.clone(
			CommerceOrderLocalServiceImpl.AVAILABLE_ORDER_STATUSES);

		Arrays.sort(availableOrderStatuses);

		for (int i = 1; i < availableOrderStatuses.length; i++) {
			Assert.assertNotEquals(
				availableOrderStatuses[i - 1], availableOrderStatuses[i]);
		}
	}

}