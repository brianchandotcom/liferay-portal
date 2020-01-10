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

package com.liferay.dispatch.internal.advisor;

import com.liferay.dispatch.advisor.Dispatch;
import com.liferay.dispatch.advisor.DispatchAdvisor;

import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class DispatchAdvisorImplTest {

	@Test
	public void testAddDispatch() {
		DispatchAdvisor dispatchAdvisor = new DispatchAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		dispatchAdvisor.addDispatch(
			_TEST_DISPATCH_TRIGGER_ID, "1 2 4 * M", new Date(), new Date());

		Optional<Dispatch> dispatchOptional = dispatchAdvisor.getDispatch(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertTrue("Dispatch exists", dispatchOptional.isPresent());

		Dispatch dispatch = dispatchOptional.get();

		Assert.assertEquals(
			"Dispatch trigger ID value", _TEST_DISPATCH_TRIGGER_ID,
			dispatch.getDispatchTriggerId());
	}

	@Test
	public void testDeleteDispatch() {
		DispatchAdvisor dispatchAdvisor = new DispatchAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		dispatchAdvisor.addDispatch(
			_TEST_DISPATCH_TRIGGER_ID, "1 2 4 * M", new Date(), new Date());

		Optional<Dispatch> dispatchOptional = dispatchAdvisor.getDispatch(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertTrue("Dispatch exists", dispatchOptional.isPresent());

		dispatchAdvisor.deleteDispatch(_TEST_DISPATCH_TRIGGER_ID);

		dispatchOptional = dispatchAdvisor.getDispatch(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse("Dispatch exists", dispatchOptional.isPresent());
	}

	@Test
	public void testDeleteDispatchIfNoSuchDispatchScheduled() {
		DispatchAdvisor dispatchAdvisor = new DispatchAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		Optional<Dispatch> dispatchOptional = dispatchAdvisor.getDispatch(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse("Dispatch exists", dispatchOptional.isPresent());

		dispatchAdvisor.deleteDispatch(_TEST_DISPATCH_TRIGGER_ID);
	}

	@Test
	public void testGetNextFireTime() {
		DispatchAdvisor dispatchAdvisor = new DispatchAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		Date triggerStartDate = new Date();
		Date triggerEndDate = new Date();

		dispatchAdvisor.addDispatch(
			_TEST_DISPATCH_TRIGGER_ID, "1 2 4 * M", triggerStartDate,
			triggerEndDate);

		Date expectedNextFireTime = new Date(triggerStartDate.getTime() + 1000);

		Optional<Date> nextFireTime = dispatchAdvisor.getNextFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertEquals(
			"Dispatch trigger next fire date", expectedNextFireTime,
			nextFireTime.get());

		dispatchAdvisor.deleteDispatch(_TEST_DISPATCH_TRIGGER_ID);

		nextFireTime = dispatchAdvisor.getNextFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse(
			"Dispatch trigger next fire date does not exist",
			nextFireTime.isPresent());

		dispatchAdvisor.addDispatch(
			_TEST_DISPATCH_TRIGGER_ID, null, triggerStartDate, triggerEndDate);

		nextFireTime = dispatchAdvisor.getNextFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse(
			"Dispatch trigger next fire date does not exist",
			nextFireTime.isPresent());
	}

	@Test
	public void testGetPreviousFireTime() {
		DispatchAdvisor dispatchAdvisor = new DispatchAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		Date triggerStartDate = new Date();
		Date triggerEndDate = new Date();

		dispatchAdvisor.addDispatch(
			_TEST_DISPATCH_TRIGGER_ID, "1 2 4 * M", triggerStartDate,
			triggerEndDate);

		Date expectedPreviousFireTime = new Date(
			triggerEndDate.getTime() - 1000);

		Optional<Date> previousFireTime = dispatchAdvisor.getPreviousFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertEquals(
			"Dispatch trigger next fire date", expectedPreviousFireTime,
			previousFireTime.get());

		dispatchAdvisor.deleteDispatch(_TEST_DISPATCH_TRIGGER_ID);

		previousFireTime = dispatchAdvisor.getPreviousFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse(
			"Dispatch trigger previous fire date does not exist",
			previousFireTime.isPresent());

		dispatchAdvisor.addDispatch(
			_TEST_DISPATCH_TRIGGER_ID, null, triggerStartDate, triggerEndDate);

		previousFireTime = dispatchAdvisor.getPreviousFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse(
			"Dispatch trigger previous fire date does not exist",
			previousFireTime.isPresent());
	}

	private static final long _TEST_DISPATCH_TRIGGER_ID = 197779;

}