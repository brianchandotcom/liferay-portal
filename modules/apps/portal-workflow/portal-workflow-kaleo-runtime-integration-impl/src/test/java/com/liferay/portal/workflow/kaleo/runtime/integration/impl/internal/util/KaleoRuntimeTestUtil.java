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

package com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.util;

import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.util.Arrays;

import org.junit.Assert;

import org.mockito.Mockito;

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

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance = Mockito.mock(
			KaleoTaskAssignmentInstance.class);

		Mockito.when(
			kaleoTaskAssignmentInstance.getAssigneeClassName()
		).thenReturn(
			returnAssigneeClassName
		);

		Mockito.when(
			kaleoTaskAssignmentInstance.getAssigneeClassPK()
		).thenReturn(
			returnAssigneeClassPK
		);

		return kaleoTaskAssignmentInstance;
	}

	public static KaleoTaskInstanceToken mockKaleoTaskInstanceToken(
		KaleoTaskAssignmentInstance... returnKaleoTaskAssignmentInstances) {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = Mockito.mock(
			KaleoTaskInstanceToken.class);

		Mockito.when(
			kaleoTaskInstanceToken.getKaleoTaskAssignmentInstances()
		).thenReturn(
			Arrays.asList(returnKaleoTaskAssignmentInstances)
		);

		if ((returnKaleoTaskAssignmentInstances.length == 0) ||
			(returnKaleoTaskAssignmentInstances.length > 1)) {

			return kaleoTaskInstanceToken;
		}

		Mockito.when(
			kaleoTaskInstanceToken.getFirstKaleoTaskAssignmentInstance()
		).thenReturn(
			returnKaleoTaskAssignmentInstances[0]
		);

		return kaleoTaskInstanceToken;
	}

}