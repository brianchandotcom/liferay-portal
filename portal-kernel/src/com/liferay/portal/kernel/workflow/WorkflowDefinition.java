/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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