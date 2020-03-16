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

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseKaleoModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	protected KaleoDefinitionVersion getKaleoDefinitionVersion(
		long kaleoDefinitionVersionId) {

		return kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
			kaleoDefinitionVersionId);
	}

	@Reference
	protected KaleoDefinitionVersionLocalService
		kaleoDefinitionVersionLocalService;

}