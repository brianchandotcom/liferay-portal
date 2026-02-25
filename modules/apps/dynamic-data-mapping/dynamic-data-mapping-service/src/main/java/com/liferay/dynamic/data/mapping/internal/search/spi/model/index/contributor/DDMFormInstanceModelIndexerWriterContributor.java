/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.internal.search.DDMFormInstanceRecordBatchReindexer;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Rafael Praxedes
 */
public class DDMFormInstanceModelIndexerWriterContributor
	extends ModelIndexerWriterContributor<DDMFormInstance> {

	public DDMFormInstanceModelIndexerWriterContributor(
		DDMFormInstanceLocalService ddmFormInstanceLocalService,
		DDMFormInstanceRecordBatchReindexer
			ddmFormInstanceRecordBatchReindexer) {

		super(ddmFormInstanceLocalService::getIndexableActionableDynamicQuery);

		_ddmFormInstanceRecordBatchReindexer =
			ddmFormInstanceRecordBatchReindexer;
	}

	@Override
	public void modelIndexed(DDMFormInstance ddmFormInstance) {
		_ddmFormInstanceRecordBatchReindexer.reindex(
			ddmFormInstance.getFormInstanceId(),
			ddmFormInstance.getCompanyId());
	}

	private final DDMFormInstanceRecordBatchReindexer
		_ddmFormInstanceRecordBatchReindexer;

}