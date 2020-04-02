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

			return _getResponse(checkOutFileResponseDocument);
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

	private boolean _getResponse(
		CheckOutFileResponseDocument checkOutFileResponseDocument) {

		CheckOutFileResponseDocument.CheckOutFileResponse checkOutFileResponse =
			checkOutFileResponseDocument.getCheckOutFileResponse();

		return checkOutFileResponse.getCheckOutFileResult();
	}

}