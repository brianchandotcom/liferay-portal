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

package com.liferay.portal.kernel.workflow;

import java.io.InputStream;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public interface WorkflowDefinition extends WorkflowModel {

	public default long getCompanyId() {
		return 0;
	}

	public String getContent();

	public default Date getCreateDate() {
		return null;
	}

	public default String getDescription() {
		return "";
	}

	public InputStream getInputStream();

	public default Date getModifiedDate() {
		return null;
	}

	public String getName();

	public Map<String, Object> getOptionalAttributes();

	public default String getScope() {
		return "";
	}

	public String getTitle();

	public String getTitle(String languageId);

	public default long getUserId() {
		return 0;
	}

	public int getVersion();

	public default long getWorkflowDefinitionId() {
		return 0;
	}

	public List<WorkflowNode> getWorkflowNodes();

	public List<WorkflowTransition> getWorkflowTransitions();

	public boolean isActive();

}