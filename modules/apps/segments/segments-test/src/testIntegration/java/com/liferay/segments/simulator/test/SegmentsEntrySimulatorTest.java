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

package com.liferay.segments.simulator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.simulator.SegmentsEntrySimulator;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class SegmentsEntrySimulatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testDeactivateSimulation() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		_segmentsEntrySimulator.setSimulatedSegmentsEntryIds(
			_user.getUserId(), new long[] {segmentsEntry.getSegmentsEntryId()});

		_segmentsEntrySimulator.deactivateSimulation(_user.getUserId());

		Assert.assertFalse(
			_segmentsEntrySimulator.isSimulationActive(_user.getUserId()));

		long[] simulatedSegmentsEntryIds =
			_segmentsEntrySimulator.getSimulatedSegmentsEntryIds(
				_user.getUserId());

		Assert.assertEquals(
			Arrays.toString(simulatedSegmentsEntryIds), 0,
			simulatedSegmentsEntryIds.length);
	}

	@Test
	public void testSimulateSegmentsEntryIds() throws Exception {
		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		_segmentsEntrySimulator.setSimulatedSegmentsEntryIds(
			_user.getUserId(),
			new long[] {segmentsEntry1.getSegmentsEntryId()});

		Assert.assertTrue(
			_segmentsEntrySimulator.isSimulationActive(_user.getUserId()));

		long[] simulatedSegmentsEntryIds =
			_segmentsEntrySimulator.getSimulatedSegmentsEntryIds(
				_user.getUserId());

		Assert.assertTrue(
			ArrayUtil.contains(
				simulatedSegmentsEntryIds,
				segmentsEntry1.getSegmentsEntryId()));
		Assert.assertFalse(
			ArrayUtil.contains(
				simulatedSegmentsEntryIds,
				segmentsEntry2.getSegmentsEntryId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "model.class.name=com.liferay.portal.kernel.model.User",
		type = SegmentsEntrySimulator.class
	)
	private SegmentsEntrySimulator _segmentsEntrySimulator;

	@DeleteAfterTestRun
	private User _user;

}