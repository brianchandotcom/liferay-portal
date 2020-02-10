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

import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

import java.rmi.RemoteException;

/**
 * @author Iván Zaera
 */
public final class CheckOutFileOperation extends BaseOperation {

	public boolean execute(String filePath) throws SharepointException {
		try {
			return listsSoap.checkOutFile(
				String.valueOf(toURL(filePath)), Boolean.FALSE.toString(),
				StringPool.BLANK);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(remoteException);
		}
	}

}