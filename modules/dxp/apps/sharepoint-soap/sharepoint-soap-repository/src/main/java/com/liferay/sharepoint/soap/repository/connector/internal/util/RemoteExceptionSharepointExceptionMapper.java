/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.internal.util;

import com.liferay.portal.kernel.repository.AuthenticationRepositoryException;
import com.liferay.sharepoint.soap.repository.connector.SharepointConnectionInfo;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

/**
 * @author Adolfo Pérez
 */
public class RemoteExceptionSharepointExceptionMapper {

	public static SharepointException map(
		RemoteException remoteException,
		SharepointConnectionInfo sharepointConnectionInfo) {

		if (remoteException instanceof AxisFault) {
			AxisFault axisFault = (AxisFault)remoteException;

			String faultMessage = axisFault.getMessage();

			if ((faultMessage != null) &&
				faultMessage.endsWith("401 Error: Unauthorized")) {

				throw new AuthenticationRepositoryException(remoteException);
			}
		}

		return new SharepointException(
			"Unable to communicate with the Sharepoint server " +
				sharepointConnectionInfo.getServiceURL(),
			remoteException);
	}

}