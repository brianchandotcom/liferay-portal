/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.util;

import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstanceWrapper;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceTokenWrapper;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

/**
 * @author Marcellus Tavares
 */
public class KaleoRuntimeTestUtil {

	public static void assertWorkflowTaskAssignee(
		String expectedAssigneeClassName, long expectedAssigneeClassPK,
		WorkflowTaskAssignee actualWorkflowTaskAssignee) {

		Assert.assertEquals(
			expectedAssigneeClassName,
			actualWorkflowTaskAssignee.getAssigneeClassName());

		Assert.assertEquals(
			expectedAssigneeClassPK,
			actualWorkflowTaskAssignee.getAssigneeClassPK());
	}

	public static KaleoTaskAssignmentInstance mockKaleoTaskAssignmentInstance(
		String assigneeClassName, long assigneeClassPK) {

		return new KaleoTaskAssignmentInstanceWrapper(null) {

			@Override
			public String getAssigneeClassName() {
				return assigneeClassName;
			}

			@Override
			public long getAssigneeClassPK() {
				return assigneeClassPK;
			}

		};
	}

	public static KaleoTaskInstanceToken mockKaleoTaskInstanceToken(
		KaleoTaskAssignmentInstance... kaleoTaskAssignmentInstances) {

		return new KaleoTaskInstanceTokenWrapper(null) {

			@Override
			public KaleoTaskAssignmentInstance
				getFirstKaleoTaskAssignmentInstance() {

				if ((kaleoTaskAssignmentInstances.length == 0) ||
					(kaleoTaskAssignmentInstances.length > 1)) {

					return null;
				}

				return kaleoTaskAssignmentInstances[0];
			}

			@Override
			public List<KaleoTaskAssignmentInstance>
				getKaleoTaskAssignmentInstances() {

				return Arrays.asList(kaleoTaskAssignmentInstances);
			}

		};
	}

}