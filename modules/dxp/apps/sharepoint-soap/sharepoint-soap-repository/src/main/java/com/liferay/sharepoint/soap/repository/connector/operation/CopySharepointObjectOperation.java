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

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.sharepoint.soap.repository.connector.SharepointConnection;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;
import com.liferay.sharepoint.soap.repository.connector.SharepointResultException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

import com.microsoft.schemas.sharepoint.soap.CopyErrorCode;
import com.microsoft.schemas.sharepoint.soap.CopyIntoItemsLocalDocument;
import com.microsoft.schemas.sharepoint.soap.CopyIntoItemsLocalResponseDocument;
import com.microsoft.schemas.sharepoint.soap.CopyResult;
import com.microsoft.schemas.sharepoint.soap.CopyResultCollection;
import com.microsoft.schemas.sharepoint.soap.DestinationUrlCollection;

import java.rmi.RemoteException;

import java.util.List;

/**
 * @author Iván Zaera
 */
public final class CopySharepointObjectOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_addFolderOperation = getOperation(AddFolderOperation.class);
		_getSharepointObjectByPathOperation = getOperation(
			GetSharepointObjectByPathOperation.class);
		_getSharepointObjectsByFolderOperation = getOperation(
			GetSharepointObjectsByFolderOperation.class);
	}

	public void execute(String path, String newPath)
		throws SharepointException {

		SharepointObject sharepointObject =
			_getSharepointObjectByPathOperation.execute(path);

		if (sharepointObject == null) {
			throw new SharepointException(
				"Unable to find Sharepoint object at " + path);
		}

		if (sharepointObject.isFile()) {
			copyFile(path, newPath);
		}
		else {
			copyFolder(path, newPath);
		}
	}

	protected void copyFile(String path, String newPath)
		throws SharepointException {

		CopyIntoItemsLocalResponseDocument copyIntoItemsLocalResponseDocument =
			null;

		try {
			copyIntoItemsLocalResponseDocument =
				copySoap12Stub.copyIntoItemsLocal(
					getCopyIntoItemsLocalDocument(path, newPath));
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}

		processCopyIntoItemsLocalResponseDocument(
			copyIntoItemsLocalResponseDocument);
	}

	protected void copyFolder(String path, String newPath)
		throws SharepointException {

		createFolder(newPath);

		List<SharepointObject> sharepointObjects =
			_getSharepointObjectsByFolderOperation.execute(
				path, SharepointConnection.ObjectTypeFilter.ALL);

		for (SharepointObject sharepointObject : sharepointObjects) {
			String sharepointObjectPath = PathUtil.buildPath(
				path, sharepointObject.getName());

			String newSharepointObjectPath = PathUtil.buildPath(
				newPath, sharepointObject.getName());

			if (sharepointObject.isFile()) {
				copyFile(sharepointObjectPath, newSharepointObjectPath);
			}
			else {
				copyFolder(sharepointObjectPath, newSharepointObjectPath);
			}
		}
	}

	protected void createFolder(String folderPath) {
		try {
			String parentFolderPath = PathUtil.getParentFolderPath(folderPath);

			String folderName = PathUtil.getName(folderPath);

			_addFolderOperation.execute(parentFolderPath, folderName);
		}
		catch (SharepointException sharepointException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to create folder at " + folderPath,
					sharepointException);
			}
		}
	}

	protected CopyIntoItemsLocalDocument getCopyIntoItemsLocalDocument(
		String path, String newPath) {

		CopyIntoItemsLocalDocument copyIntoItemsLocalDocument =
			CopyIntoItemsLocalDocument.Factory.newInstance();

		CopyIntoItemsLocalDocument.CopyIntoItemsLocal copyIntoItemsLocal =
			copyIntoItemsLocalDocument.addNewCopyIntoItemsLocal();

		DestinationUrlCollection destinationUrlCollection =
			DestinationUrlCollection.Factory.newInstance();

		destinationUrlCollection.addString(String.valueOf(toURL(newPath)));

		copyIntoItemsLocal.setDestinationUrls(destinationUrlCollection);

		copyIntoItemsLocal.setSourceUrl(String.valueOf(toURL(path)));

		return copyIntoItemsLocalDocument;
	}

	protected Void processCopyIntoItemsLocalResponseDocument(
			CopyIntoItemsLocalResponseDocument
				copyIntoItemsLocalResponseDocument)
		throws SharepointException {

		CopyIntoItemsLocalResponseDocument.CopyIntoItemsLocalResponse
			copyIntoItemsLocalResponse =
				copyIntoItemsLocalResponseDocument.
					getCopyIntoItemsLocalResponse();

		CopyResultCollection results = copyIntoItemsLocalResponse.getResults();

		CopyResult copyResult = results.getCopyResultArray(0);

		if (copyResult.getErrorCode() != CopyErrorCode.SUCCESS) {
			throw new SharepointResultException(
				String.valueOf(copyResult.getErrorCode()),
				copyResult.getErrorMessage());
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopySharepointObjectOperation.class);

	private AddFolderOperation _addFolderOperation;
	private GetSharepointObjectByPathOperation
		_getSharepointObjectByPathOperation;
	private GetSharepointObjectsByFolderOperation
		_getSharepointObjectsByFolderOperation;

}