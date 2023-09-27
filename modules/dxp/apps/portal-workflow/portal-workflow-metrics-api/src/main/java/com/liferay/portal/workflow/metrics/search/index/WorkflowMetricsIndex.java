/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.search.index;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Rafael Praxedes
 */
public interface WorkflowMetricsIndex {

	public boolean createIndex(long companyId) throws PortalException;

	public boolean exists(long companyId);

	public String getIndexName(long companyId);

	public String getIndexType();

	public boolean removeIndex(long companyId) throws PortalException;

}