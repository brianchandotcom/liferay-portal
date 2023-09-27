/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

import com.microsoft.schemas.sharepoint.soap.UndoCheckOutDocument;
import com.microsoft.schemas.sharepoint.soap.UndoCheckOutResponseDocument;

import java.rmi.RemoteException;

/**
 * @author Iván Zaera
 */
public final class CancelCheckOutFileOperation extends BaseOperation {

	public boolean execute(String filePath) throws SharepointException {
		try {
			UndoCheckOutResponseDocument undoCheckOutResponseDocument =
				listsSoap12Stub.undoCheckOut(
					_getUndoCheckOutDocument(filePath));

			return _isUndoCheckOut(undoCheckOutResponseDocument);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}
	}

	private UndoCheckOutDocument _getUndoCheckOutDocument(String filePath) {
		UndoCheckOutDocument undoCheckOutDocument =
			UndoCheckOutDocument.Factory.newInstance();

		UndoCheckOutDocument.UndoCheckOut undoCheckOut =
			undoCheckOutDocument.addNewUndoCheckOut();

		undoCheckOut.setPageUrl(String.valueOf(toURL(filePath)));

		return undoCheckOutDocument;
	}

	private boolean _isUndoCheckOut(
		UndoCheckOutResponseDocument undoCheckOutResponseDocument) {

		UndoCheckOutResponseDocument.UndoCheckOutResponse undoCheckOutResponse =
			undoCheckOutResponseDocument.getUndoCheckOutResponse();

		return undoCheckOutResponse.getUndoCheckOutResult();
	}

}