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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.AggregateTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelectorRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rafael Praxedes
 */
@RunWith(PowerMockRunner.class)
public class AggregateTaskAssignmentSelectorImplTest {

	@Before
	public void setUp() throws Exception {
		_aggregateTaskAssignmentSelector =
			new AggregateTaskAssignmentSelectorImpl();

		_setUpTaskAssignmentSelectorRegistry();
	}

	@Test
	public void testGetTaskAssignments() throws Exception {
		ArrayList<String> assigneeClassNames = new ArrayList<>(
			_taskAssignmentSelectors.keySet());

		KaleoTaskAssignment kaleoTaskAssignment1 = _createKaleoTaskAssignment(
			assigneeClassNames.get(0), RandomTestUtil.randomLong());

		Collection<KaleoTaskAssignment> kaleoTaskAssignments1 =
			_aggregateTaskAssignmentSelector.getTaskAssignments(
				Arrays.asList(kaleoTaskAssignment1),
				Mockito.mock(ExecutionContext.class));

		Assert.assertEquals(
			kaleoTaskAssignments1.toString(), 2, kaleoTaskAssignments1.size());

		KaleoTaskAssignment kaleoTaskAssignment2 = _createKaleoTaskAssignment(
			assigneeClassNames.get(1), RandomTestUtil.randomLong());

		Collection<KaleoTaskAssignment> kaleoTaskAssignments2 =
			_aggregateTaskAssignmentSelector.getTaskAssignments(
				Arrays.asList(kaleoTaskAssignment2),
				Mockito.mock(ExecutionContext.class));

		Assert.assertEquals(
			kaleoTaskAssignments2.toString(), 2, kaleoTaskAssignments2.size());

		Collection<KaleoTaskAssignment> kaleoTaskAssignments3 =
			_aggregateTaskAssignmentSelector.getTaskAssignments(
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

	private void _setUpTaskAssignmentSelectorRegistry() throws Exception {
		TaskAssignmentSelectorRegistry taskAssignmentSelectorRegistry =
			Mockito.mock(TaskAssignmentSelectorRegistry.class);

		for (Map.Entry<String, List<KaleoTaskAssignment>> entry :
				_taskAssignmentSelectors.entrySet()) {

			Mockito.when(
				taskAssignmentSelectorRegistry.getTaskAssignmentSelector(
					Matchers.eq(entry.getKey()))
			).thenReturn(
				new TaskAssignmentSelector() {

					@Override
					public Collection<KaleoTaskAssignment> getTaskAssignments(
							KaleoTaskAssignment kaleoTaskAssignment,
							ExecutionContext executionContext)
						throws PortalException {

						return entry.getValue();
					}

				}
			);
		}

		MemberMatcher.field(
			AggregateTaskAssignmentSelectorImpl.class,
			"_taskAssignmentSelectorRegistry"
		).set(
			_aggregateTaskAssignmentSelector, taskAssignmentSelectorRegistry
		);
	}

	private AggregateTaskAssignmentSelector _aggregateTaskAssignmentSelector;
	private final Map<String, List<KaleoTaskAssignment>>
		_taskAssignmentSelectors =
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