/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.util;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class WorkflowTaskAssigneesSupplierTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetWhenKaleoTaskAssignmentsIsEmpty() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken();

		WorkflowTaskAssigneesSupplier workflowTaskAssigneesSupplier =
			new WorkflowTaskAssigneesSupplier(kaleoTaskInstanceToken);

		List<WorkflowTaskAssignee> workflowTaskAssignees =
			workflowTaskAssigneesSupplier.get();

		Assert.assertTrue(
			workflowTaskAssignees.toString(), workflowTaskAssignees.isEmpty());
	}

	@Test
	public void testGetWhenKaleoTaskAssignmentsIsNotEmpty() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken(
				new KaleoTaskAssignmentInstance[] {
					KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
						Role.class.getName(), 1),
					KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
						User.class.getName(), 2)
				});

		WorkflowTaskAssigneesSupplier workflowTaskAssigneesSupplier =
			new WorkflowTaskAssigneesSupplier(kaleoTaskInstanceToken);

		List<WorkflowTaskAssignee> workflowTaskAssignees =
			workflowTaskAssigneesSupplier.get();

		int actualSize = workflowTaskAssignees.size();

		Assert.assertEquals(2, actualSize);

		KaleoRuntimeTestUtil.assertWorkflowTaskAssignee(
			Role.class.getName(), 1, workflowTaskAssignees.get(0));
		KaleoRuntimeTestUtil.assertWorkflowTaskAssignee(
			User.class.getName(), 2, workflowTaskAssignees.get(1));
	}

}