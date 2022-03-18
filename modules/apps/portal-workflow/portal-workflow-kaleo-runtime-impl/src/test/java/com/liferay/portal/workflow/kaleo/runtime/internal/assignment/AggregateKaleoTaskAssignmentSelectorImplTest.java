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

package com.liferay.portal.workflow.kaleo.runtime.internal.assignment;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rafael Praxedes
 */
public class AggregateKaleoTaskAssignmentSelectorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpAggregateKaleoTaskAssignmentSelectorImpl();
	}

	@Test
	public void testGetKaleoTaskAssignments() throws Exception {
		List<String> assigneeClassNames = new ArrayList<>(
			_kaleoTaskAssignmentSelectors.keySet());

		KaleoTaskAssignment kaleoTaskAssignment1 = _createKaleoTaskAssignment(
			assigneeClassNames.get(0), RandomTestUtil.randomLong());

		Collection<KaleoTaskAssignment> kaleoTaskAssignments1 =
			_aggregateKaleoTaskAssignmentSelectorImpl.getKaleoTaskAssignments(
				Arrays.asList(kaleoTaskAssignment1),
				Mockito.mock(ExecutionContext.class));

		Assert.assertEquals(
			kaleoTaskAssignments1.toString(), 2, kaleoTaskAssignments1.size());

		KaleoTaskAssignment kaleoTaskAssignment2 = _createKaleoTaskAssignment(
			assigneeClassNames.get(1), RandomTestUtil.randomLong());

		Collection<KaleoTaskAssignment> kaleoTaskAssignments2 =
			_aggregateKaleoTaskAssignmentSelectorImpl.getKaleoTaskAssignments(
				Arrays.asList(kaleoTaskAssignment2),
				Mockito.mock(ExecutionContext.class));

		Assert.assertEquals(
			kaleoTaskAssignments2.toString(), 2, kaleoTaskAssignments2.size());

		Collection<KaleoTaskAssignment> kaleoTaskAssignments3 =
			_aggregateKaleoTaskAssignmentSelectorImpl.getKaleoTaskAssignments(
				Arrays.asList(kaleoTaskAssignment1, kaleoTaskAssignment2),
				Mockito.mock(ExecutionContext.class));

		Assert.assertEquals(
			kaleoTaskAssignments3.toString(), 3, kaleoTaskAssignments3.size());

		for (KaleoTaskAssignment kaleoTaskAssignment : kaleoTaskAssignments1) {
			Assert.assertTrue(
				_containsKaleoTaskAssignment(
					kaleoTaskAssignments3, kaleoTaskAssignment));
		}

		for (KaleoTaskAssignment kaleoTaskAssignment : kaleoTaskAssignments2) {
			Assert.assertTrue(
				_containsKaleoTaskAssignment(
					kaleoTaskAssignments3, kaleoTaskAssignment));
		}
	}

	private boolean _containsKaleoTaskAssignment(
		Collection<KaleoTaskAssignment> kaleoTaskAssignments,
		KaleoTaskAssignment kaleoTaskAssignment) {

		for (KaleoTaskAssignment curKaleoTaskAssignment :
				kaleoTaskAssignments) {

			if (Objects.equals(
					curKaleoTaskAssignment.getAssigneeClassName(),
					kaleoTaskAssignment.getAssigneeClassName()) &&
				Objects.equals(
					curKaleoTaskAssignment.getAssigneeClassPK(),
					kaleoTaskAssignment.getAssigneeClassPK())) {

				return true;
			}
		}

		return false;
	}

	private KaleoTaskAssignment _createKaleoTaskAssignment(
		String assigneeClassName, long assigneeClassPK) {

		KaleoTaskAssignment kaleoTaskAssignment = Mockito.mock(
			KaleoTaskAssignment.class);

		Mockito.when(
			kaleoTaskAssignment.getAssigneeClassName()
		).thenReturn(
			assigneeClassName
		);

		Mockito.when(
			kaleoTaskAssignment.getAssigneeClassPK()
		).thenReturn(
			assigneeClassPK
		);

		return kaleoTaskAssignment;
	}

	private void _setUpAggregateKaleoTaskAssignmentSelectorImpl()
		throws Exception {

		KaleoTaskAssignmentSelectorTracker kaleoTaskAssignmentSelectorTracker =
			new KaleoTaskAssignmentSelectorTracker();

		for (Map.Entry<String, List<KaleoTaskAssignment>> entry :
				_kaleoTaskAssignmentSelectors.entrySet()) {

			kaleoTaskAssignmentSelectorTracker.addKaleoTaskAssignmentSelector(
				(kaleoTaskAssignment, executionContext) -> entry.getValue(),
				HashMapBuilder.<String, Object>put(
					"assignee.class.name", entry.getKey()
				).build());
		}

		ReflectionTestUtil.setFieldValue(
			_aggregateKaleoTaskAssignmentSelectorImpl,
			"_kaleoTaskAssignmentSelectorRegistry",
			kaleoTaskAssignmentSelectorTracker);
	}

	private final AggregateKaleoTaskAssignmentSelectorImpl
		_aggregateKaleoTaskAssignmentSelectorImpl =
			new AggregateKaleoTaskAssignmentSelectorImpl();
	private final Map<String, List<KaleoTaskAssignment>>
		_kaleoTaskAssignmentSelectors =
			HashMapBuilder.<String, List<KaleoTaskAssignment>>put(
				RandomTestUtil.randomString(),
				Arrays.asList(
					_createKaleoTaskAssignment("A", 1),
					_createKaleoTaskAssignment(
						RandomTestUtil.randomString(),
						RandomTestUtil.randomLong()))
			).put(
				RandomTestUtil.randomString(),
				Arrays.asList(
					_createKaleoTaskAssignment("A", 1),
					_createKaleoTaskAssignment(
						RandomTestUtil.randomString(),
						RandomTestUtil.randomLong()))
			).build();

}