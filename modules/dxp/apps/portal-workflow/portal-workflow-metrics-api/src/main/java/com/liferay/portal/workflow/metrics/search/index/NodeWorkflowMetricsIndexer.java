/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.search.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.workflow.metrics.model.AddNodeRequest;
import com.liferay.portal.workflow.metrics.model.DeleteNodeRequest;

/**
 * @author Rafael Praxedes
 */
public interface NodeWorkflowMetricsIndexer {

	public Document addNode(AddNodeRequest addNodeRequest);

	public void deleteNode(DeleteNodeRequest deleteNodeRequest);

}