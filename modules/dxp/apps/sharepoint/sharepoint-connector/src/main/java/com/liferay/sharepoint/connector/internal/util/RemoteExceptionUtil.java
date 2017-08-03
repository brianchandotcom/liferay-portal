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

package com.liferay.sharepoint.connector.internal.util;

import com.liferay.portal.kernel.repository.AuthenticationRepositoryException;
import com.liferay.sharepoint.connector.SharepointException;

import java.rmi.RemoteException;

import org.apache.axis.AxisFault;

/**
 * @author Adolfo Pérez
 */
public class RemoteExceptionUtil {

	public static void handleRemoteException(RemoteException re)
		throws SharepointException {

		if (re instanceof AxisFault) {
			AxisFault axisFault = (AxisFault)re;

			String faultString = axisFault.getFaultString();

			if (faultString.startsWith("(401)Unauthorized")) {
				throw new AuthenticationRepositoryException(re);
			}
		}

		throw new SharepointException(
			"Unable to communicate with the Sharepoint server", re);
	}

}