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

package com.liferay.portal.kernel.events;

import com.liferay.portal.kernel.events.IntervalAction;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jonathan McCann
 * @author Sergio González
 */
public class IntervalActionTest {

	@Test
	public void testIntervalActionEndCalculation() throws Exception {
		final IntervalAction intervalAction = new IntervalAction(125);

		intervalAction.setPerformActionMethod(
			new IntervalAction.PerformIntervalActionMethod() {

				@Override
				public void performAction(int start, int end)
					throws PortalException {

					for (int i = start; i < end; i++) {
						_count.incrementAndGet();
					}

					intervalAction.incrementStart(end - start);
				}

			});

		intervalAction.performActions();

		Assert.assertEquals(125, _count.get());
	}

	@Test
	public void testIntervalActionEndCalculationWithInterval()
		throws Exception {

		final IntervalAction intervalAction = new IntervalAction(125, 200);

		intervalAction.setPerformActionMethod(
			new IntervalAction.PerformIntervalActionMethod() {

				@Override
				public void performAction(int start, int end)
					throws PortalException {

					for (int i = start; i < end; i++) {
						_count.incrementAndGet();
					}

					intervalAction.incrementStart(end - start);
				}

			});

		intervalAction.performActions();

		Assert.assertEquals(125, _count.get());
	}

	@Test
	public void testIntervalActionPageCalculation() throws Exception {
		final IntervalAction intervalAction = new IntervalAction(125);

		intervalAction.setPerformActionMethod(
			new IntervalAction.PerformIntervalActionMethod() {

				@Override
				public void performAction(int start, int end)
					throws PortalException {

					_count.incrementAndGet();

					intervalAction.incrementStart(end - start);
				}

			});

		intervalAction.performActions();

		Assert.assertEquals(2, _count.get());
	}

	@Test
	public void testIntervalActionPageCalculationWithInterval()
		throws Exception {

		final IntervalAction intervalAction = new IntervalAction(125, 200);

		intervalAction.setPerformActionMethod(
			new IntervalAction.PerformIntervalActionMethod() {

				@Override
				public void performAction(int start, int end)
					throws PortalException {

					_count.incrementAndGet();

					intervalAction.incrementStart(end - start);
				}

			});

		intervalAction.performActions();

		Assert.assertEquals(1, _count.get());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithNegativeIncrementStart()
		throws Exception {

		final IntervalAction intervalAction = new IntervalAction(125, 200);

		intervalAction.setPerformActionMethod(
			new IntervalAction.PerformIntervalActionMethod() {

				@Override
				public void performAction(int start, int end)
					throws PortalException {

					intervalAction.incrementStart(start - end);
				}

			});

		intervalAction.performActions();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithNegativeInterval() throws Exception {
		new IntervalAction(125, -10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithNegativeTotal1() throws Exception {
		new IntervalAction(-10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithNegativeTotal2() throws Exception {
		new IntervalAction(-10, 200);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithZeroInterval() throws Exception {
		new IntervalAction(125, 0);
	}

	@Test
	public void testIntervalActionWithZeroTotal() throws Exception {
		final IntervalAction intervalAction = new IntervalAction(0);

		intervalAction.setPerformActionMethod(
			new IntervalAction.PerformIntervalActionMethod() {

				@Override
				public void performAction(int start, int end)
					throws PortalException {

					for (int i = start; i < end; i++) {
						_count.incrementAndGet();
					}

					intervalAction.incrementStart(end - start);
				}

			});

		intervalAction.performActions();

		Assert.assertEquals(0, _count.get());
	}

	private final AtomicInteger _count = new AtomicInteger();

}