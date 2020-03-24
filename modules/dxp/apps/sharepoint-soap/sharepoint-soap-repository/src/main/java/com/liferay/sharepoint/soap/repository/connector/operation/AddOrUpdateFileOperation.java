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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.sharepoint.soap.repository.connector.SharepointConnection;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointResultException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

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

		CopyIntoItemsResponseDocument copyIntoItemsResponseDocument = null;

		try {
			copyIntoItemsResponseDocument = copySoap12Stub.copyIntoItems(
				getCopyIntoItemsDocument(filePath, inputStream));
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}

		processCopyIntoItemsResponseDocument(copyIntoItemsResponseDocument);

		if (changeLog != null) {
			_checkInFileOperation.execute(
				filePath, changeLog, SharepointConnection.CheckInType.MAJOR);
		}
	}

	protected CopyIntoItemsDocument getCopyIntoItemsDocument(
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

	protected Void processCopyIntoItemsResponseDocument(
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

		return null;
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

	private static final FieldInformation[] _EMPTY_FIELD_INFORMATIONS =
		new FieldInformation[0];

	private CheckInFileOperation _checkInFileOperation;

}