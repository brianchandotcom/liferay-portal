/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.util;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceTokenWrapper;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalServiceWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class LazyWorkflowTaskAssigneeListTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetSizeWhenWorkflowTaskAssigneesIsLoaded() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken(
				KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
					Role.class.getName(), 1),
				KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
					User.class.getName(), 2));

		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService =
				new KaleoTaskAssignmentInstanceLocalServiceWrapper() {

					@Override
					public int getKaleoTaskAssignmentInstancesCount(
						long kaleoTaskInstanceTokenId) {

						_executedMethodsNames.add(
							"getKaleoTaskAssignmentInstancesCount");

						return -1;
					}

				};

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				kaleoTaskAssignmentInstanceLocalService);

		lazyWorkflowTaskAssigneeList.initWorkflowTaskAssignees();

		Assert.assertEquals(2, lazyWorkflowTaskAssigneeList.size());

		Assert.assertFalse(
			_executedMethodsNames.contains(
				"getKaleoTaskAssignmentInstancesCount"));
	}

	@Test
	public void testGetSizeWhenWorkflowTaskAssigneesIsNotLoaded() {
		long kaleoTaskInstanceTokenId = RandomTestUtil.randomLong();

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			new KaleoTaskInstanceTokenWrapper(
				KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken()) {

				@Override
				public long getKaleoTaskInstanceTokenId() {
					return kaleoTaskInstanceTokenId;
				}

			};

		int kaleoTaskAssignmentInstancesCount = RandomTestUtil.randomInt();

		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService =
				new KaleoTaskAssignmentInstanceLocalServiceWrapper() {

					@Override
					public int getKaleoTaskAssignmentInstancesCount(
						long kaleoTaskInstanceTokenId) {

						_executedMethodsNames.add(
							"getKaleoTaskAssignmentInstancesCount");

						if (kaleoTaskInstanceTokenId ==
								kaleoTaskInstanceToken.
									getKaleoTaskInstanceTokenId()) {

							return kaleoTaskAssignmentInstancesCount;
						}

						return -1;
					}

				};

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				kaleoTaskAssignmentInstanceLocalService);

		Assert.assertEquals(
			kaleoTaskAssignmentInstancesCount,
			lazyWorkflowTaskAssigneeList.size());

		Assert.assertTrue(
			_executedMethodsNames.contains(
				"getKaleoTaskAssignmentInstancesCount"));
	}

	@Test
	public void testGetWhenIndexIsGreaterThanZero() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_getKaleoTaskInstanceToken(
				KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
					Role.class.getName(), 1),
				KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
					User.class.getName(), 2));

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(kaleoTaskInstanceToken, null);

		KaleoRuntimeTestUtil.assertWorkflowTaskAssignee(
			User.class.getName(), 2, lazyWorkflowTaskAssigneeList.get(1));

		Assert.assertFalse(
			_executedMethodsNames.contains(
				"getFirstKaleoTaskAssignmentInstance"));
		Assert.assertTrue(
			_executedMethodsNames.contains("getKaleoTaskAssignmentInstances"));
	}

	@Test
	public void testGetWhenIndexIsZeroAndAssignmentIsNotNull() {
		String assigneeClassName = StringUtil.randomString();
		long assigneeClassPK = RandomTestUtil.randomLong();

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_getKaleoTaskInstanceToken(
				KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
					assigneeClassName, assigneeClassPK));

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(kaleoTaskInstanceToken, null);

		KaleoRuntimeTestUtil.assertWorkflowTaskAssignee(
			assigneeClassName, assigneeClassPK,
			lazyWorkflowTaskAssigneeList.get(0));

		Assert.assertTrue(
			_executedMethodsNames.contains(
				"getFirstKaleoTaskAssignmentInstance"));
		Assert.assertFalse(
			_executedMethodsNames.contains("getKaleoTaskAssignmentInstances"));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetWhenIndexIsZeroAndAssignmentIsNull() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken();

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(kaleoTaskInstanceToken, null);

		lazyWorkflowTaskAssigneeList.get(0);
	}

	private KaleoTaskInstanceToken _getKaleoTaskInstanceToken(
		KaleoTaskAssignmentInstance... kaleoTaskAssignmentInstances) {

		return new KaleoTaskInstanceTokenWrapper(
			KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken(
				kaleoTaskAssignmentInstances)) {

			@Override
			public KaleoTaskAssignmentInstance
				getFirstKaleoTaskAssignmentInstance() {

				_executedMethodsNames.add(
					"getFirstKaleoTaskAssignmentInstance");

				return super.getFirstKaleoTaskAssignmentInstance();
			}

			@Override
			public List<KaleoTaskAssignmentInstance>
				getKaleoTaskAssignmentInstances() {

				_executedMethodsNames.add("getKaleoTaskAssignmentInstances");

				return super.getKaleoTaskAssignmentInstances();
			}

		};
	}

	private final Set<String> _executedMethodsNames = new HashSet<>();

}