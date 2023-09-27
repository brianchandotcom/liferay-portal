/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.sharepoint.soap.repository.connector.SharepointConnection;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

import com.microsoft.schemas.sharepoint.soap.CheckInFileDocument;
import com.microsoft.schemas.sharepoint.soap.CheckInFileResponseDocument;

import java.rmi.RemoteException;

/**
 * @author Iván Zaera
 */
public final class CheckInFileOperation extends BaseOperation {

	public boolean execute(
			String filePath, String comment,
			SharepointConnection.CheckInType checkInType)
		throws SharepointException {

		try {
			CheckInFileResponseDocument checkInFileResponseDocument =
				listsSoap12Stub.checkInFile(
					_getCheckInFileDocument(filePath, comment, checkInType));

			return _isCheckInFile(checkInFileResponseDocument);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}
	}

	private CheckInFileDocument _getCheckInFileDocument(
		String filePath, String comment,
		SharepointConnection.CheckInType checkInType) {

		CheckInFileDocument checkInFileDocument =
			CheckInFileDocument.Factory.newInstance();

		CheckInFileDocument.CheckInFile checkInFile =
			checkInFileDocument.addNewCheckInFile();

		checkInFile.setCheckinType(
			String.valueOf(checkInType.getProtocolValue()));
		checkInFile.setComment(comment);
		checkInFile.setPageUrl(String.valueOf(toURL(filePath)));

		return checkInFileDocument;
	}

	private boolean _isCheckInFile(
		CheckInFileResponseDocument checkInFileResponseDocument) {

		CheckInFileResponseDocument.CheckInFileResponse checkInFileResponse =
			checkInFileResponseDocument.getCheckInFileResponse();

		return checkInFileResponse.getCheckInFileResult();
	}

}