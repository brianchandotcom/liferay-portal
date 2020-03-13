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

package com.liferay.portal.workflow.metrics.search.index;

import com.liferay.portal.search.document.Document;

import java.util.Date;

/**
 * @author Rafael Praxedes
 */
public interface TransitionWorkflowMetricsIndexer {

	public Document addTransition(
		long companyId, Date createDate, Date modifiedDate, String name,
		long nodeId, long processId, String processVersion, long sourceNodeId,
		String sourceNodeName, long targetNodeId, String targetNodeName,
		long transitionId, long userId);

	public void deleteTransition(long companyId, long transitionId);

}