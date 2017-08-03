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

package com.liferay.lcs.subscription;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Igor Beslic
 */
@RunWith(PowerMockRunner.class)
public class SubscriptionInstanceSizeTest extends PowerMockito {

	@Test
	public void testToSubscriptionInstanceSize() {
		for (SubscriptionInstanceSize subscriptionInstanceSize :
				SubscriptionInstanceSize.values()) {

			if (subscriptionInstanceSize ==
					SubscriptionInstanceSize.SIZE_UNDEFINED) {

				continue;
			}

			Assert.assertEquals(
				subscriptionInstanceSize,
				SubscriptionInstanceSize.getSubscriptionInstanceSize(
					subscriptionInstanceSize.getProcessorCoresAllowed()));

			Assert.assertEquals(
				subscriptionInstanceSize,
				SubscriptionInstanceSize.getSubscriptionInstanceSize(
					subscriptionInstanceSize.getProcessorCoresAllowed() - 2));
		}

		try {
			SubscriptionInstanceSize.getSubscriptionInstanceSize(-1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

}