/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.operation.constants.SharepointConstants;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.Batch;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.BatchField;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.BatchMethod;

/**
 * @author Iván Zaera
 */
public final class AddFolderOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_batchOperation = getOperation(BatchOperation.class);
	}

	public void execute(String folderPath, String folderName)
		throws SharepointException {

		_batchOperation.execute(
			new Batch(
				Batch.OnError.CONTINUE, toFullPath(folderPath),
				new BatchMethod(
					SharepointConstants.BATCH_METHOD_ID_DEFAULT,
					BatchMethod.Command.NEW, new BatchField("ID", "New"),
					new BatchField(
						"FSObjType", SharepointConstants.FS_OBJ_TYPE_FOLDER),
					new BatchField("BaseName", folderName))));
	}

	private BatchOperation _batchOperation;

}