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
		String returnAssigneeClassName, long returnAssigneeClassPK) {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			new KaleoTaskAssignmentInstanceWrapper(null) {

				@Override
				public String getAssigneeClassName() {
					return returnAssigneeClassName;
				}

				@Override
				public long getAssigneeClassPK() {
					return returnAssigneeClassPK;
				}

			};

		return kaleoTaskAssignmentInstance;
	}

	public static KaleoTaskInstanceToken mockKaleoTaskInstanceToken(
		KaleoTaskAssignmentInstance... returnKaleoTaskAssignmentInstances) {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			new KaleoTaskInstanceTokenWrapper(null) {

				@Override
				public KaleoTaskAssignmentInstance
					getFirstKaleoTaskAssignmentInstance() {

					if ((returnKaleoTaskAssignmentInstances.length == 0) ||
						(returnKaleoTaskAssignmentInstances.length > 1)) {

						return null;
					}

					return returnKaleoTaskAssignmentInstances[0];
				}

				@Override
				public List<KaleoTaskAssignmentInstance>
					getKaleoTaskAssignmentInstances() {

					return Arrays.asList(returnKaleoTaskAssignmentInstances);
				}

			};

		return kaleoTaskInstanceToken;
	}

}