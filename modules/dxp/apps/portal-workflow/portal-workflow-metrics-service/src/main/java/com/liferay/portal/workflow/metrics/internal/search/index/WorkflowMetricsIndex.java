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

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Rafael Praxedes
 */
public interface WorkflowMetricsIndex {

	public void createIndex(long companyId) throws PortalException;

	public String getIndexName(long companyId);

	public String getIndexType();

	public void removeIndex(long companyId) throws PortalException;

}