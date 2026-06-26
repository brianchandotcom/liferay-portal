/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.workflow.node;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Iliyan Peychev
 */
public interface ServiceNodeDelegate {

	public String execute(
			Map<String, String> inputVariables,
			Map<String, Serializable> workflowContext)
		throws Exception;

	public String getKey();

}