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

import com.liferay.sharepoint.soap.repository.connector.SharepointConnection;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.util.RemoteExceptionSharepointExceptionMapper;

import com.microsoft.schemas.sharepoint.soap.CheckInFileDocument;
import com.microsoft.schemas.sharepoint.soap.CheckInFileResponseDocument;

import java.rmi.RemoteException;

/**
 * @author Iván Zaera
 */
public class CheckInFileOperation extends BaseOperation {

	public boolean execute(
			String filePath, String comment,
			SharepointConnection.CheckInType checkInType)
		throws SharepointException {

		try {
			CheckInFileResponseDocument checkInFileResponseDocument =
				listsStub.checkInFile(
					_getCheckInFileDocument(filePath, comment, checkInType));

			CheckInFileResponseDocument.CheckInFileResponse
				checkInFileResponse =
					checkInFileResponseDocument.getCheckInFileResponse();

			return checkInFileResponse.getCheckInFileResult();
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(remoteException);
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

}