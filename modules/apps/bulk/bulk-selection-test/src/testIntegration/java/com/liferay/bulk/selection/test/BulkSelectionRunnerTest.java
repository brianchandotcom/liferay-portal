/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.selection.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.bulk.selection.test.util.TestBulkSelectionAction;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
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
		_bulkSelectionRunner.run(
			TestPropsValues.getUser(),
			_testBulkSelectionFactory.create(
				HashMapBuilder.put(
					"integers", new String[] {"1", "2", "3", "4"}
				).build()),
			_testBulkSelectionAction,
			HashMapBuilder.<String, Serializable>put(
				"integer", 10
			).build());

		Assert.assertEquals(
			(Integer)100, TestBulkSelectionAction.getLastResult());
	}

	@Inject
	private BulkSelectionRunner _bulkSelectionRunner;

	@Inject(
		filter = "component.name=com.liferay.bulk.selection.test.util.TestBulkSelectionAction"
	)
	private BulkSelectionAction<Integer> _testBulkSelectionAction;

	@Inject(
		filter = "component.name=com.liferay.bulk.selection.test.util.TestBulkSelectionFactory"
	)
	private BulkSelectionFactory<Integer> _testBulkSelectionFactory;

	@Inject(
		filter = "component.name=com.liferay.bulk.selection.test.util.TestBusyBulkSelectionAction"
	)
	private BulkSelectionAction<Integer> _testBusyBulkSelectionAction;

}