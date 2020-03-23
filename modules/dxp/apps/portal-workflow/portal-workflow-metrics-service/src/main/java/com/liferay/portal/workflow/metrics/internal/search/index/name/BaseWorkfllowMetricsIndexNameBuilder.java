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

package com.liferay.portal.workflow.metrics.internal.search.index.name;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseWorkfllowMetricsIndexNameBuilder
	implements WorkflowMetricsIndexNameBuilder {

	@Override
	public String getIndexName(long companyId) {
		return StringUtil.replace(
			indexNameBuilder.getIndexName(companyId), String.valueOf(companyId),
			getIndexNamePrefix() + companyId);
	}

	public abstract String getIndexNamePrefix();

	@Reference
	protected IndexNameBuilder indexNameBuilder;

}