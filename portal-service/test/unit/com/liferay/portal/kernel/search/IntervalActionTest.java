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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.events.IntervalAction;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jonathan McCann
 */
public class IntervalActionTest {

	@Test
	public void testIndexerIntervalEndCalculation() throws Exception {
		IntervalAction intervalAction = new IntervalAction(125);

		intervalAction.setPerformActionMethod(
			new IntervalAction.PerformIntervalActionMethod() {

				@Override
				public void performAction(int start, int end)
					throws PortalException {

					for (int i = 0; i < end; i++) {
						_count.incrementAndGet();
					}
				}

			});

		intervalAction.performActions();

		Assert.assertEquals(200, _count.get());
	}

	@Test
	public void testIndexerIntervalPageCalculation() throws Exception {
		IntervalAction intervalAction = new IntervalAction(125);

		intervalAction.setPerformActionMethod(
			new IntervalAction.PerformIntervalActionMethod() {

				@Override
				public void performAction(int start, int end)
					throws PortalException {

					_count.incrementAndGet();
				}

			});

		intervalAction.performActions();

		Assert.assertEquals(2, _count.get());
	}

	private final AtomicInteger _count = new AtomicInteger();

}