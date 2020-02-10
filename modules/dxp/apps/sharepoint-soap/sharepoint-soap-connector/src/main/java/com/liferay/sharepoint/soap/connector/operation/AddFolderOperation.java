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

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.schema.batch.Batch;
import com.liferay.sharepoint.soap.connector.schema.batch.BatchField;
import com.liferay.sharepoint.soap.connector.schema.batch.BatchMethod;

/**
 * @author Iván Zaera
 */
public class AddFolderOperation extends BaseOperation {

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