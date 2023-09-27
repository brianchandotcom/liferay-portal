/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

import com.microsoft.schemas.sharepoint.soap.CheckOutFileDocument;
import com.microsoft.schemas.sharepoint.soap.CheckOutFileResponseDocument;

import java.rmi.RemoteException;

/**
 * @author Iván Zaera
 */
public final class CheckOutFileOperation extends BaseOperation {

	public boolean execute(String filePath) throws SharepointException {
		try {
			CheckOutFileResponseDocument checkOutFileResponseDocument =
				listsSoap12Stub.checkOutFile(
					_getCheckOutFileDocument(filePath));

			return _isCheckOutFile(checkOutFileResponseDocument);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}
	}

	private CheckOutFileDocument _getCheckOutFileDocument(String filePath) {
		CheckOutFileDocument checkOutFileDocument =
			CheckOutFileDocument.Factory.newInstance();

		CheckOutFileDocument.CheckOutFile checkOutFile =
			checkOutFileDocument.addNewCheckOutFile();

		checkOutFile.setCheckoutToLocal(Boolean.FALSE.toString());
		checkOutFile.setLastmodified(StringPool.BLANK);
		checkOutFile.setPageUrl(String.valueOf(toURL(filePath)));

		return checkOutFileDocument;
	}

	private boolean _isCheckOutFile(
		CheckOutFileResponseDocument checkOutFileResponseDocument) {

		CheckOutFileResponseDocument.CheckOutFileResponse checkOutFileResponse =
			checkOutFileResponseDocument.getCheckOutFileResponse();

		return checkOutFileResponse.getCheckOutFileResult();
	}

}