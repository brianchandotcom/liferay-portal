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

import com.liferay.dispatch.advisor.DispatchJobAdvisor;
import com.liferay.dispatch.advisor.DispatchJobProperties;

import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class DispatchJobAdvisorImplTest {

	@Test
	public void testAddDispatch() {
		DispatchJobAdvisor dispatchJobAdvisor = new DispatchJobAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		dispatchJobAdvisor.submitDispatchJob(
			_TEST_DISPATCH_TRIGGER_ID, "1 2 4 * M", new Date(), new Date());

		Optional<DispatchJobProperties> dispatchOptional =
			dispatchJobAdvisor.getDispatchJobProperties(
				_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertTrue("Dispatch exists", dispatchOptional.isPresent());

		DispatchJobProperties dispatchJobProperties = dispatchOptional.get();

		Assert.assertEquals(
			"Dispatch trigger ID value", _TEST_DISPATCH_TRIGGER_ID,
			dispatchJobProperties.getDispatchTriggerId());
	}

	@Test
	public void testDeleteDispatch() {
		DispatchJobAdvisor dispatchJobAdvisor = new DispatchJobAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		dispatchJobAdvisor.submitDispatchJob(
			_TEST_DISPATCH_TRIGGER_ID, "1 2 4 * M", new Date(), new Date());

		Optional<DispatchJobProperties> dispatchOptional =
			dispatchJobAdvisor.getDispatchJobProperties(
				_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertTrue("Dispatch exists", dispatchOptional.isPresent());

		dispatchJobAdvisor.deleteDispatchJob(_TEST_DISPATCH_TRIGGER_ID);

		dispatchOptional = dispatchJobAdvisor.getDispatchJobProperties(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse("Dispatch exists", dispatchOptional.isPresent());
	}

	@Test
	public void testDeleteDispatchIfNoSuchDispatchScheduled() {
		DispatchJobAdvisor dispatchJobAdvisor = new DispatchJobAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		Optional<DispatchJobProperties> dispatchOptional =
			dispatchJobAdvisor.getDispatchJobProperties(
				_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse("Dispatch exists", dispatchOptional.isPresent());

		dispatchJobAdvisor.deleteDispatchJob(_TEST_DISPATCH_TRIGGER_ID);
	}

	@Test
	public void testGetNextFireTime() {
		DispatchJobAdvisor dispatchJobAdvisor = new DispatchJobAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		Date triggerStartDate = new Date();
		Date triggerEndDate = new Date();

		dispatchJobAdvisor.submitDispatchJob(
			_TEST_DISPATCH_TRIGGER_ID, "1 2 4 * M", triggerStartDate,
			triggerEndDate);

		Date expectedNextFireTime = new Date(triggerStartDate.getTime() + 1000);

		Optional<Date> nextFireTime = dispatchJobAdvisor.getNextFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertEquals(
			"Dispatch trigger next fire date", expectedNextFireTime,
			nextFireTime.get());

		dispatchJobAdvisor.deleteDispatchJob(_TEST_DISPATCH_TRIGGER_ID);

		nextFireTime = dispatchJobAdvisor.getNextFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse(
			"Dispatch trigger next fire date does not exist",
			nextFireTime.isPresent());

		dispatchJobAdvisor.submitDispatchJob(
			_TEST_DISPATCH_TRIGGER_ID, null, triggerStartDate, triggerEndDate);

		nextFireTime = dispatchJobAdvisor.getNextFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse(
			"Dispatch trigger next fire date does not exist",
			nextFireTime.isPresent());
	}

	@Test
	public void testGetPreviousFireTime() {
		DispatchJobAdvisor dispatchJobAdvisor = new DispatchJobAdvisorImpl(
			new MockSchedulerEngineHelperImpl(), new MockTriggerFactoryImpl());

		Date triggerStartDate = new Date();
		Date triggerEndDate = new Date();

		dispatchJobAdvisor.submitDispatchJob(
			_TEST_DISPATCH_TRIGGER_ID, "1 2 4 * M", triggerStartDate,
			triggerEndDate);

		Date expectedPreviousFireTime = new Date(
			triggerEndDate.getTime() - 1000);

		Optional<Date> previousFireTime =
			dispatchJobAdvisor.getPreviousFireDate(_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertEquals(
			"Dispatch trigger next fire date", expectedPreviousFireTime,
			previousFireTime.get());

		dispatchJobAdvisor.deleteDispatchJob(_TEST_DISPATCH_TRIGGER_ID);

		previousFireTime = dispatchJobAdvisor.getPreviousFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse(
			"Dispatch trigger previous fire date does not exist",
			previousFireTime.isPresent());

		dispatchJobAdvisor.submitDispatchJob(
			_TEST_DISPATCH_TRIGGER_ID, null, triggerStartDate, triggerEndDate);

		previousFireTime = dispatchJobAdvisor.getPreviousFireDate(
			_TEST_DISPATCH_TRIGGER_ID);

		Assert.assertFalse(
			"Dispatch trigger previous fire date does not exist",
			previousFireTime.isPresent());
	}

	private static final long _TEST_DISPATCH_TRIGGER_ID = 197779;

}