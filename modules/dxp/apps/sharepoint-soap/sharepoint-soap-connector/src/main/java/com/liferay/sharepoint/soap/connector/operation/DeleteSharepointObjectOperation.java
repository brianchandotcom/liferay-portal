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
import com.liferay.sharepoint.soap.connector.SharepointObject;
import com.liferay.sharepoint.soap.connector.schema.batch.Batch;
import com.liferay.sharepoint.soap.connector.schema.batch.BatchField;
import com.liferay.sharepoint.soap.connector.schema.batch.BatchMethod;

/**
 * @author Iván Zaera
 */
public final class DeleteSharepointObjectOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_batchOperation = getOperation(BatchOperation.class);
		_getSharepointObjectByPathOperation = getOperation(
			GetSharepointObjectByPathOperation.class);
	}

	public void execute(String path) throws SharepointException {
		SharepointObject sharepointObject =
			_getSharepointObjectByPathOperation.execute(path);

		if (sharepointObject == null) {
			throw new SharepointException(
				"Unable to find Sharepoint object with path " + path);
		}

		_batchOperation.execute(
			new Batch(
				Batch.OnError.CONTINUE, null,
				new BatchMethod(
					SharepointConstants.BATCH_METHOD_ID_DEFAULT,
					BatchMethod.Command.DELETE,
					new BatchField(
						"ID", sharepointObject.getSharepointObjectId()),
					new BatchField(
						"FileRef",
						String.valueOf(
							toFullPath(sharepointObject.getPath()))))));
	}

	private BatchOperation _batchOperation;
	private GetSharepointObjectByPathOperation
		_getSharepointObjectByPathOperation;

}