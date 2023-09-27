/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.sharepoint.soap.repository.connector.SharepointConnection;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointResultException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;
import com.liferay.sharepoint.soap.repository.connector.operation.constants.SharepointConstants;

import com.microsoft.schemas.sharepoint.soap.CopyErrorCode;
import com.microsoft.schemas.sharepoint.soap.CopyIntoItemsDocument;
import com.microsoft.schemas.sharepoint.soap.CopyIntoItemsResponseDocument;
import com.microsoft.schemas.sharepoint.soap.CopyResult;
import com.microsoft.schemas.sharepoint.soap.CopyResultCollection;
import com.microsoft.schemas.sharepoint.soap.DestinationUrlCollection;
import com.microsoft.schemas.sharepoint.soap.FieldInformation;
import com.microsoft.schemas.sharepoint.soap.FieldInformationCollection;

import java.io.IOException;
import java.io.InputStream;

import java.rmi.RemoteException;

/**
 * @author Iván Zaera
 */
public final class AddOrUpdateFileOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_checkInFileOperation = getOperation(CheckInFileOperation.class);
	}

	public void execute(
			String filePath, String changeLog, InputStream inputStream)
		throws SharepointException {

		try {
			CopyIntoItemsResponseDocument copyIntoItemsResponseDocument =
				copySoap12Stub.copyIntoItems(
					_getCopyIntoItemsDocument(filePath, inputStream));

			_processCopyIntoItemsResponseDocument(
				copyIntoItemsResponseDocument);

			if (changeLog != null) {
				_checkInFileOperation.execute(
					filePath, changeLog,
					SharepointConnection.CheckInType.MAJOR);
			}
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}
	}

	private byte[] _getBytes(InputStream inputStream)
		throws SharepointException {

		try {
			return FileUtil.getBytes(inputStream);
		}
		catch (IOException ioException) {
			throw new SharepointException(
				"Unable to read input stream", ioException);
		}
	}

	private CopyIntoItemsDocument _getCopyIntoItemsDocument(
			String filePath, InputStream inputStream)
		throws SharepointException {

		CopyIntoItemsDocument copyIntoItemsDocument =
			CopyIntoItemsDocument.Factory.newInstance();

		CopyIntoItemsDocument.CopyIntoItems copyIntoItems =
			copyIntoItemsDocument.addNewCopyIntoItems();

		copyIntoItems.setDestinationUrls(
			_getDestinationUrlCollection(filePath));
		copyIntoItems.setFields(_getFieldInformationCollection());
		copyIntoItems.setSourceUrl(SharepointConstants.URL_SOURCE_NONE);
		copyIntoItems.setStream(_getBytes(inputStream));

		return copyIntoItemsDocument;
	}

	private DestinationUrlCollection _getDestinationUrlCollection(
		String filePath) {

		DestinationUrlCollection destinationUrlCollection =
			DestinationUrlCollection.Factory.newInstance();

		destinationUrlCollection.addString(String.valueOf(toURL(filePath)));

		return destinationUrlCollection;
	}

	private FieldInformationCollection _getFieldInformationCollection() {
		FieldInformationCollection fieldInformationCollection =
			FieldInformationCollection.Factory.newInstance();

		fieldInformationCollection.setFieldInformationArray(
			_EMPTY_FIELD_INFORMATIONS);

		return fieldInformationCollection;
	}

	private void _processCopyIntoItemsResponseDocument(
			CopyIntoItemsResponseDocument copyIntoItemsResponseDocument)
		throws SharepointException {

		CopyIntoItemsResponseDocument.CopyIntoItemsResponse
			copyIntoItemsResponse =
				copyIntoItemsResponseDocument.getCopyIntoItemsResponse();

		CopyResultCollection copyResultCollection =
			copyIntoItemsResponse.getResults();

		CopyResult copyResult = copyResultCollection.getCopyResultArray(0);

		if (copyResult.getErrorCode() != CopyErrorCode.SUCCESS) {
			throw new SharepointResultException(
				String.valueOf(copyResult.getErrorCode()),
				copyResult.getErrorMessage());
		}
	}

	private static final FieldInformation[] _EMPTY_FIELD_INFORMATIONS =
		new FieldInformation[0];

	private CheckInFileOperation _checkInFileOperation;

}