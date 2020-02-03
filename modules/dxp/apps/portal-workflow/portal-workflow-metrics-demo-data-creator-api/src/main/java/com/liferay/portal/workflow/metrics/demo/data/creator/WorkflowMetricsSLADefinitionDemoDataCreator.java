/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.demo.data.creator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Inácio Nery
 */
@ProviderType
public interface WorkflowMetricsSLADefinitionDemoDataCreator {

	public void create(
			long companyId, Date createDate, long userId,
			long workflowDefinitionId)
		throws Exception;

	public WorkflowMetricsSLADefinition create(
			long companyId, String calendarKey, Date createDate,
			String description, long duration, String name,
			String[] pauseNodeKeys, long processId, String[] startNodeKeys,
			String[] stopNodeKeys, long userId)
		throws PortalException;

	public void delete() throws PortalException;

}