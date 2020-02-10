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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharepoint.soap.connector.SharepointConnection;
import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.SharepointObject;
import com.liferay.sharepoint.soap.connector.schema.batch.Batch;
import com.liferay.sharepoint.soap.connector.schema.batch.BatchField;
import com.liferay.sharepoint.soap.connector.schema.batch.BatchMethod;

/**
 * @author Iván Zaera
 */
public final class MoveSharepointObjectOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_batchOperation = getOperation(BatchOperation.class);
		_checkInFileOperation = getOperation(CheckInFileOperation.class);
		_checkOutFileOperation = getOperation(CheckOutFileOperation.class);
		_copySharepointObjectOperation = getOperation(
			CopySharepointObjectOperation.class);
		_deleteSharepointObjectOperation = getOperation(
			DeleteSharepointObjectOperation.class);
		_getSharepointObjectByPathOperation = getOperation(
			GetSharepointObjectByPathOperation.class);
	}

	public void execute(String path, String newPath)
		throws SharepointException {

		SharepointObject sharepointObject =
			_getSharepointObjectByPathOperation.execute(path);

		if (_isRename(path, newPath)) {
			String oldExtension = pathHelper.getExtension(path);

			String newExtension = pathHelper.getExtension(newPath);

			if (!oldExtension.equals(newExtension)) {
				throw new SharepointException(
					"Sharepoint does not support changing file extensions");
			}

			_batchOperation.execute(
				new Batch(
					Batch.OnError.RETURN, null,
					new BatchMethod(
						SharepointConstants.BATCH_METHOD_ID_DEFAULT,
						BatchMethod.Command.UPDATE,
						new BatchField(
							"ID", sharepointObject.getSharepointObjectId()),
						new BatchField(
							"FileRef",
							String.valueOf(sharepointObject.getURL())),
						new BatchField(
							"BaseName",
							pathHelper.getNameWithoutExtension(newPath)))));
		}
		else {
			_copySharepointObjectOperation.execute(path, newPath);
			_deleteSharepointObjectOperation.execute(path);

			_checkInFileOperation.execute(
				newPath, StringPool.BLANK,
				SharepointConnection.CheckInType.MAJOR);

			if (Validator.isNotNull(sharepointObject.getCheckedOutBy())) {
				_checkOutFileOperation.execute(newPath);
			}
		}
	}

	private boolean _isRename(String path, String newPath) {
		String parentFolderPath = pathHelper.getParentFolderPath(path);
		String newParentFolderPath = pathHelper.getParentFolderPath(newPath);

		return parentFolderPath.equals(newParentFolderPath);
	}

	private BatchOperation _batchOperation;
	private CheckInFileOperation _checkInFileOperation;
	private CheckOutFileOperation _checkOutFileOperation;
	private CopySharepointObjectOperation _copySharepointObjectOperation;
	private DeleteSharepointObjectOperation _deleteSharepointObjectOperation;
	private GetSharepointObjectByPathOperation
		_getSharepointObjectByPathOperation;

}