/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.interval;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jonathan McCann
 * @author Sergio González
 */
public class IntervalActionProcessorTest {

	@Test
	public void testIntervalActionEndCalculation() throws Exception {
		final IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(125);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Void>() {

				@Override
				public Void performIntervalAction(int start, int end) {
					for (int i = start; i < end; i++) {
						_count.incrementAndGet();
					}

					intervalActionProcessor.incrementStart(end - start);

					return null;
				}

			});

		intervalActionProcessor.performIntervalActions();

		Assert.assertEquals(125, _count.get());
	}

	@Test
	public void testIntervalActionEndCalculationWithInterval()
		throws Exception {

		final IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(125, 200);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Void>() {

				@Override
				public Void performIntervalAction(int start, int end) {
					for (int i = start; i < end; i++) {
						_count.incrementAndGet();
					}

					intervalActionProcessor.incrementStart(end - start);

					return null;
				}

			});

		intervalActionProcessor.performIntervalActions();

		Assert.assertEquals(125, _count.get());
	}

	@Test
	public void testIntervalActionPageCalculation() throws Exception {
		final IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(125);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Void>() {

				@Override
				public Void performIntervalAction(int start, int end) {
					_count.incrementAndGet();

					intervalActionProcessor.incrementStart(end - start);

					return null;
				}

			});

		intervalActionProcessor.performIntervalActions();

		Assert.assertEquals(2, _count.get());
	}

	@Test
	public void testIntervalActionPageCalculationWithInterval()
		throws Exception {

		final IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(125, 200);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Void>() {

				@Override
				public Void performIntervalAction(int start, int end) {
					_count.incrementAndGet();

					intervalActionProcessor.incrementStart(end - start);

					return null;
				}

			});

		intervalActionProcessor.performIntervalActions();

		Assert.assertEquals(1, _count.get());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithNegativeIncrementStart()
		throws Exception {

		final IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(125, 200);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Void>() {

				@Override
				public Void performIntervalAction(int start, int end) {
					intervalActionProcessor.incrementStart(start - end);

					return null;
				}

			});

		intervalActionProcessor.performIntervalActions();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithNegativeInterval() throws Exception {
		new IntervalActionProcessor<Void>(125, -10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithNegativeTotal1() throws Exception {
		new IntervalActionProcessor<Void>(-10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithNegativeTotal2() throws Exception {
		new IntervalActionProcessor<Void>(-10, 200);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntervalActionWithZeroInterval() throws Exception {
		new IntervalActionProcessor<Void>(125, 0);
	}

	@Test
	public void testIntervalActionWithZeroTotal() throws Exception {
		final IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(0);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Void>() {

				@Override
				public Void performIntervalAction(int start, int end) {
					for (int i = start; i < end; i++) {
						_count.incrementAndGet();
					}

					intervalActionProcessor.incrementStart(end - start);

					return null;
				}

			});

		intervalActionProcessor.performIntervalActions();

		Assert.assertEquals(0, _count.get());
	}

	@Test
	public void testIntervalWithBooleanType() throws Exception {
		final IntervalActionProcessor<Boolean> intervalActionProcessor =
			new IntervalActionProcessor<>(3, 1);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Boolean>() {

				@Override
				public Boolean performIntervalAction(int start, int end) {
					if ((start == 1) && (end == 2)) {
						return true;
					}

					intervalActionProcessor.incrementStart();

					return null;
				}

			});

		Assert.assertTrue(intervalActionProcessor.performIntervalActions());
	}

	@Test
	public void testIntervalWithBooleanTypeAndZeroTotal() throws Exception {
		IntervalActionProcessor<Boolean> intervalSearcher =
			new IntervalActionProcessor<>(0, 1);

		intervalSearcher.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Boolean>() {

				@Override
				public Boolean performIntervalAction(int start, int end) {
					return false;
				}

			});

		Assert.assertNull(intervalSearcher.performIntervalActions());
	}

	private final AtomicInteger _count = new AtomicInteger();

}