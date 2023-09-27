/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharepoint.soap.repository.connector.SharepointConnection;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;
import com.liferay.sharepoint.soap.repository.connector.operation.constants.SharepointConstants;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.Batch;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.BatchField;
import com.liferay.sharepoint.soap.repository.connector.schema.batch.BatchMethod;

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
			String oldExtension = PathUtil.getExtension(path);

			String newExtension = PathUtil.getExtension(newPath);

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
							PathUtil.getNameWithoutExtension(newPath)))));
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
		String parentFolderPath = PathUtil.getParentFolderPath(path);
		String newParentFolderPath = PathUtil.getParentFolderPath(newPath);

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