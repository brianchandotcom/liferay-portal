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

package com.liferay.portal.workflow.uad.test;

import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, service = WorkflowDefinitionLinkUADTestHelper.class
)
public class WorkflowDefinitionLinkUADTestHelper {

	public WorkflowDefinitionLink addWorkflowDefinitionLink(long userId)
		throws Exception {

		return _workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
			userId, TestPropsValues.getCompanyId(),
			TestPropsValues.getGroupId(), null, 0, 0,
			RandomTestUtil.randomString(), 0);
	}

	public void cleanUpDependencies(
			List<WorkflowDefinitionLink> workflowDefinitionLinks)
		throws Exception {
	}

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}