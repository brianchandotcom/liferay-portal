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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.sharepoint.soap.connector.SharepointConnection;
import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.SharepointResultException;
import com.liferay.sharepoint.soap.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

import com.microsoft.schemas.sharepoint.soap.CopyErrorCode;
import com.microsoft.schemas.sharepoint.soap.CopyResult;
import com.microsoft.schemas.sharepoint.soap.FieldInformation;
import com.microsoft.schemas.sharepoint.soap.holders.CopyResultCollectionHolder;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.rmi.RemoteException;

import org.apache.axis.holders.UnsignedIntHolder;

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

		URL filePathURL = toURL(filePath);

		CopyResultCollectionHolder copyResultCollectionHolder =
			new CopyResultCollectionHolder();

		try {
			copySoap.copyIntoItems(
				SharepointConstants.URL_SOURCE_NONE,
				new String[] {filePathURL.toString()},
				_EMPTY_FIELD_INFORMATIONS, _getBytes(inputStream),
				new UnsignedIntHolder(), copyResultCollectionHolder);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(remoteException);
		}

		CopyResult copyResult = copyResultCollectionHolder.value[0];

		CopyErrorCode copyErrorCode = copyResult.getErrorCode();

		if (copyErrorCode != CopyErrorCode.Success) {
			throw new SharepointResultException(
				copyErrorCode.toString(), copyResult.getErrorMessage());
		}

		if (changeLog != null) {
			_checkInFileOperation.execute(
				filePath, changeLog, SharepointConnection.CheckInType.MAJOR);
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

	private static final FieldInformation[] _EMPTY_FIELD_INFORMATIONS =
		new FieldInformation[0];

	private CheckInFileOperation _checkInFileOperation;

}