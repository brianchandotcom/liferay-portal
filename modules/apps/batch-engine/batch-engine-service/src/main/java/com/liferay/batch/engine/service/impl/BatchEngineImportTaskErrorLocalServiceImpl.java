/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.impl;

import com.liferay.batch.engine.model.BatchEngineImportTaskError;
import com.liferay.batch.engine.service.base.BatchEngineImportTaskErrorLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.batch.engine.model.BatchEngineImportTaskError",
	service = AopService.class
)
public class BatchEngineImportTaskErrorLocalServiceImpl
	extends BatchEngineImportTaskErrorLocalServiceBaseImpl {

	@Override
	public BatchEngineImportTaskError addBatchEngineImportTaskError(
		long companyId, long userId, long batchEngineImportTaskId, String item,
		int itemIndex, String message) {

		BatchEngineImportTaskError batchEngineImportTaskError =
			batchEngineImportTaskErrorPersistence.create(
				counterLocalService.increment());

		batchEngineImportTaskError.setCompanyId(companyId);
		batchEngineImportTaskError.setUserId(userId);
		batchEngineImportTaskError.setBatchEngineImportTaskId(
			batchEngineImportTaskId);
		batchEngineImportTaskError.setItem(_getItem(item, itemIndex));
		batchEngineImportTaskError.setItemIndex(itemIndex);
		batchEngineImportTaskError.setMessage(_sanitize(message));

		return batchEngineImportTaskErrorPersistence.update(
			batchEngineImportTaskError);
	}

	@Override
	public List<BatchEngineImportTaskError> getBatchEngineImportTaskErrors(
		long batchEngineImportTaskId) {

		return batchEngineImportTaskErrorPersistence.
			findByBatchEngineImportTaskId(batchEngineImportTaskId);
	}

	@Override
	public int getBatchEngineImportTaskErrorsCount(
		long batchEngineImportTaskId) {

		return batchEngineImportTaskErrorPersistence.
			countByBatchEngineImportTaskId(batchEngineImportTaskId);
	}

	private String _getItem(String item, int itemIndex) {
		if (Validator.isNull(item)) {
			item = "Unable to read item at index " + itemIndex;
		}

		return item;
	}

	private String _sanitize(String message) {
		if (Validator.isNull(message)) {
			return StringPool.BLANK;
		}

		return message.replaceAll("\n|\r\n", StringPool.SPACE);
	}

}