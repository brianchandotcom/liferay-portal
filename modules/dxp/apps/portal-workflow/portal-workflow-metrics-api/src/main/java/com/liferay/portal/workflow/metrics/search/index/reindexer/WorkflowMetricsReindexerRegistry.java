/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.search.index.reindexer;

import java.util.Set;

/**
 * @author Jiaxu Wei
 */
public interface WorkflowMetricsReindexerRegistry {

	public boolean containsKey(String indexEntityName);

	public Set<String> getIndexEntityNames();

	public WorkflowMetricsReindexer getWorkflowMetricsReindexer(
		String indexEntityName);

}