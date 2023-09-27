/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public interface WorkflowInstance extends WorkflowModel {

	public void addChildWorkflowInstance(
		WorkflowInstance childWorkflowInstance);

	public int getChildrenWorkflowInstanceCount();

	public List<WorkflowInstance> getChildrenWorkflowInstances();

	public List<WorkflowNode> getCurrentWorkflowNodes();

	public Date getEndDate();

	public WorkflowInstance getParentWorkflowInstance();

	public long getParentWorkflowInstanceId();

	public Date getStartDate();

	public Map<String, Serializable> getWorkflowContext();

	public String getWorkflowDefinitionName();

	public int getWorkflowDefinitionVersion();

	public long getWorkflowInstanceId();

	public default boolean isActive() {
		return true;
	}

	public boolean isComplete();

	public void setParentWorkflowInstance(
		WorkflowInstance parentWorkflowInstance);

}