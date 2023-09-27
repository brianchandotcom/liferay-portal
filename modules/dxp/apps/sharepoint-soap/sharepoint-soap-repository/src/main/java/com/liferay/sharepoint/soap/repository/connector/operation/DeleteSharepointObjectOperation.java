/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;
import com.liferay.sharepoint.soap.repository.connector.operation.constants.SharepointConstants;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.Batch;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.BatchField;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.BatchMethod;

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
						"FileRef", toFullPath(sharepointObject.getPath())))));
	}

	private BatchOperation _batchOperation;
	private GetSharepointObjectByPathOperation
		_getSharepointObjectByPathOperation;

}