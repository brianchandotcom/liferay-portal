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
			WorkflowMetricsSLADefinitionVersion> findByC_WMSLAD_V(
				long companyId, java.util.Date createDate, int status,
				int start, int end);

}