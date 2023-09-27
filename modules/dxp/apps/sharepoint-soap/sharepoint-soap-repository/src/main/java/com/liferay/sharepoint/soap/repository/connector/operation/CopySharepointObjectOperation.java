/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			_copyFile(path, newPath);
		}
		else {
			_copyFolder(path, newPath);
		}
	}

	private void _copyFile(String path, String newPath)
		throws SharepointException {

		try {
			CopyIntoItemsLocalResponseDocument
				copyIntoItemsLocalResponseDocument =
					copySoap12Stub.copyIntoItemsLocal(
						_getCopyIntoItemsLocalDocument(path, newPath));

			_processCopyIntoItemsLocalResponseDocument(
				copyIntoItemsLocalResponseDocument);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}
	}

	private void _copyFolder(String path, String newPath)
		throws SharepointException {

		_createFolder(newPath);

		List<SharepointObject> sharepointObjects =
			_getSharepointObjectsByFolderOperation.execute(
				path, SharepointConnection.ObjectTypeFilter.ALL);

		for (SharepointObject sharepointObject : sharepointObjects) {
			String sharepointObjectPath = PathUtil.buildPath(
				path, sharepointObject.getName());

			String newSharepointObjectPath = PathUtil.buildPath(
				newPath, sharepointObject.getName());

			if (sharepointObject.isFile()) {
				_copyFile(sharepointObjectPath, newSharepointObjectPath);
			}
			else {
				_copyFolder(sharepointObjectPath, newSharepointObjectPath);
			}
		}
	}

	private void _createFolder(String folderPath) {
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

	private CopyIntoItemsLocalDocument _getCopyIntoItemsLocalDocument(
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

	private void _processCopyIntoItemsLocalResponseDocument(
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
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopySharepointObjectOperation.class);

	private AddFolderOperation _addFolderOperation;
	private GetSharepointObjectByPathOperation
		_getSharepointObjectByPathOperation;
	private GetSharepointObjectsByFolderOperation
		_getSharepointObjectsByFolderOperation;

}