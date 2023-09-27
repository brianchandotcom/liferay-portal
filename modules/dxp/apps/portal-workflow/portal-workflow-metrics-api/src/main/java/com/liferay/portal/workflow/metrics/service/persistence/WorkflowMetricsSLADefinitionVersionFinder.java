/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface WorkflowMetricsSLADefinitionVersionFinder {

	public java.util.List
		<com.liferay.portal.workflow.metrics.model.
			WorkflowMetricsSLADefinitionVersion> findByC_CD_P_S(
				long companyId, java.util.Date createDate, Long processId,
				int status, int start, int end);

}