/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionLinkResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Victor Kammerer
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-definition-link.properties",
	scope = ServiceScope.PROTOTYPE,
	service = WorkflowDefinitionLinkResource.class
)
public class WorkflowDefinitionLinkResourceImpl
	extends BaseWorkflowDefinitionLinkResourceImpl {
}