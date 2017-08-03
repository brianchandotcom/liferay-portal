/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sharepoint.connector.SharepointException;
import com.liferay.sharepoint.connector.internal.util.RemoteExceptionUtil;

import java.net.URL;

import java.rmi.RemoteException;

/**
 * @author Iván Zaera
 */
public class CheckOutFileOperation extends BaseOperation {

	public boolean execute(String filePath) throws SharepointException {
		try {
			URL filePathURL = toURL(filePath);

			return listsSoap.checkOutFile(
				filePathURL.toString(), Boolean.FALSE.toString(),
				StringPool.BLANK);
		}
		catch (RemoteException re) {
			RemoteExceptionUtil.handleRemoteException(re);

			throw new IllegalStateException();
		}
	}

}