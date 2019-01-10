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

package com.liferay.bulk.selection.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.bulk.selection.test.util.TestBulkSelectionAction;
import com.liferay.bulk.selection.test.util.TestBulkSelectionFactory;
import com.liferay.bulk.selection.test.util.TestBusyBulkSelectionAction;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class BulkSelectionRunnerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Sync
	@Test
	public void testIsBusyWhenActionsAreRunning() throws Exception {
		User user = TestPropsValues.getUser();

		Assert.assertFalse(_bulkSelectionRunner.isBusy(user));

		_bulkSelectionRunner.run(
			user, _testBulkSelectionFactory.create(new HashMap<>()),
			_testBusyBulkSelectionAction, new HashMap<>());

		Assert.assertFalse(_bulkSelectionRunner.isBusy(user));
	}

	@Test
	public void testRunsABulkSelectionAction() throws Exception {
		HashMap<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put("integers", new String[] {"1", "2", "3", "4"});

		HashMap<String, Serializable> inputMap = new HashMap<>();

		inputMap.put("integer", 10);

		_bulkSelectionRunner.run(
			TestPropsValues.getUser(),
			_testBulkSelectionFactory.create(parameterMap),
			_testBulkSelectionAction, inputMap);

		Assert.assertEquals(
			(Integer)100, TestBulkSelectionAction.getLastResult());
	}

	@Inject
	private BulkSelectionRunner _bulkSelectionRunner;

	@Inject
	private TestBulkSelectionAction _testBulkSelectionAction;

	@Inject
	private TestBulkSelectionFactory _testBulkSelectionFactory;

	@Inject
	private TestBusyBulkSelectionAction _testBusyBulkSelectionAction;

}