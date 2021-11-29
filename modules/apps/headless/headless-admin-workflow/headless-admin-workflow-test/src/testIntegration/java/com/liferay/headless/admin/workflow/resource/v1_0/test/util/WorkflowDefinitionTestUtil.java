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

package com.liferay.headless.admin.workflow.resource.v1_0.test.util;

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Rafael Praxedes
 */
public class WorkflowDefinitionTestUtil {

	public static WorkflowDefinition addWorkflowDefinition() throws Exception {
		return addWorkflowDefinition("workflow-definition.xml");
	}

	public static WorkflowDefinition addWorkflowDefinition(
			String workflowDefinitionFileName)
		throws Exception {

		WorkflowDefinitionResource.Builder builder =
			WorkflowDefinitionResource.builder();

		WorkflowDefinitionResource workflowDefinitionResource =
			builder.authentication(
				"test@liferay.com", "test"
			).locale(
				LocaleUtil.getDefault()
			).build();

		String workflowDefinitionName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		return workflowDefinitionResource.postWorkflowDefinitionDeploy(
			new WorkflowDefinition() {
				{
					active = true;
					content = WorkflowDefinitionTestUtil.getContent(
						RandomTestUtil.randomString(),
						workflowDefinitionFileName, workflowDefinitionName);
					dateCreated = RandomTestUtil.nextDate();
					dateModified = RandomTestUtil.nextDate();
					description = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					name = workflowDefinitionName;
					title = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					version = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
				}
			});
	}

	public static String getContent(
		String workflowDefinitionDescription, String workflowDefinitionFileName,
		String workflowDefinitionName) {

		return StringUtil.replace(
			StringUtil.read(
				WorkflowDefinitionTestUtil.class,
				"dependencies/" + workflowDefinitionFileName),
			new String[] {
				"[$WORKFLOW-DEFINITION-DESCRIPTION$]",
				"[$WORKFLOW-DEFINITION-NAME$]"
			},
			new String[] {
				workflowDefinitionDescription, workflowDefinitionName
			});
	}

}