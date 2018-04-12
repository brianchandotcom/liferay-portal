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